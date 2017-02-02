using ClassM8_Client.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace ClassM8_Client.Controls
{
    /// <summary>
    /// Interaction logic for ClassSettingsControl.xaml
    /// </summary>
    public partial class ClassSettingsControl : UserControl
    {
        public ClassSettingsControl()
        {
            DataReader.Instance.getM8Class();
            Schoolclass currClass = Database.Instance.currSchoolclass;
            InitializeComponent();
            txtName.Text = currClass.getName();
            txtSchool.Text = currClass.getSchool();
            txtRoom.Text = currClass.getRoom();
            try
            {
                txtPres.Text = currClass.getPresident().getFirstname() + " " + currClass.getPresident().getLastname();
                txtPresDep.Text = currClass.getPresidentDeputy().getFirstname() + " " + currClass.getPresidentDeputy().getLastname();
            }
            catch (Exception ex)
            {
                txtPres.Text = "Noch niemand";
                txtPresDep.Text = "Noch niemand";
            }
        }

        private void btnCancle_Click(object sender, RoutedEventArgs e)
        {
            ControllerNavigator.NavigateTo(ControllerHolder.HomeControl);
        }

        private void btnUpdate_Click(object sender, RoutedEventArgs e)
        {
            if (txtName.Text != "" || txtRoom.Text != "" || txtSchool.Text != "")
            {
                updateClass();
                ControllerNavigator.NavigateTo(ControllerHolder.HomeControl);
            }
            else
            {
                if (String.IsNullOrEmpty(txtName.Text))
                {
                    txtName.Text = "Bitte ausfüllen";
                }
                if (String.IsNullOrEmpty(txtSchool.Text))
                {
                    txtSchool.Text = "Bitte ausfüllen";
                }
                if (String.IsNullOrEmpty(txtRoom.Text))
                {
                    txtRoom.Text = "Bitte ausfüllen";
                }

            }

        }

        private void updateClass()
        {
            Schoolclass sc = new Schoolclass();
            sc.setId(Database.Instance.currSchoolclass.getId());
            sc.setName(txtName.Text);
            sc.setSchool(txtSchool.Text);
            sc.setRoom(txtRoom.Text);
            sc.setClassMembers(Database.Instance.currSchoolclass.getClassMembers());
            sc.setPresident(Database.Instance.currSchoolclass.getPresident());
            sc.setPresidentDeputy(Database.Instance.currSchoolclass.getPresidentDeputy());
            sc.setClassFiles(Database.Instance.currSchoolclass.getClassFiles());

            Database.Instance.currSchoolclass = sc;
            DataReader.Instance.updateSchoolclass(sc);
            txtInfo.Text = "Klasse bearbeitet";
        }

        private void btnDelete_Click(object sender, RoutedEventArgs e)
        {
            DataReader.Instance.deleteSchoolclass();
            txtInfo.Text = "Klasse gelöscht";
            ControllerNavigator.NavigateTo(ControllerHolder.HomeControl);
        }
    }
}
