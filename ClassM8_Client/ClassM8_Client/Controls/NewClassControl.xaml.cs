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
    /// Interaction logic for NewClassControl.xaml
    /// </summary>
    public partial class NewClassControl : UserControl
    {
        public NewClassControl()
        {
            InitializeComponent();
        }

        private void btnNewAccCreate_Click(object sender, RoutedEventArgs e)
        {
            txtError.Text = "";
            createNewSchoolclass();
            ControllerNavigator.NavigateTo(ControllerHolder.LoginControl);

        }

        private void btnNewAccCancle_Click(object sender, RoutedEventArgs e)
        {
            ControllerNavigator.NavigateTo(new NoFriendsControl());
        }

        public void createNewSchoolclass()
        {
            Schoolclass sc = new Schoolclass();
            sc.setName(txtName.Text);
            sc.setRoom(txtRoom.Text);
            sc.setSchool(txtSchool.Text);
            Database.Instance.currSchoolclass = sc;
            DataReader.Instance.createNewSchoolclass(sc);
        }
    }
}
