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
    /// Interaction logic for UserSettingsControl.xaml
    /// </summary>
    public partial class UserSettingsControl : UserControl
    {
        static int currUserId;
        static M8 currM8;

        public UserSettingsControl()
        {
            InitializeComponent();
            currUserId = (int)Database.Instance.currM8.getId();
            currM8 = Database.Instance.currM8;

            firstname.Text = currM8.getFirstname();
            lastname.Text = currM8.getLastname();
            email.Text = currM8.getEmail();
            password.Text = currM8.getPassword();
        }




        private void btnCanle_Click(object sender, RoutedEventArgs e)
        {
            ControllerHolder.TitleTextBox.Text = Database.Instance.currM8.getFirstname() + " " + Database.Instance.currM8.getLastname();
            if (Database.Instance.currSchoolclass.getId() == -1)
            {
                ControllerNavigator.NavigateTo(new NoFriendsControl());
            }
            else
            {
                ControllerNavigator.NavigateTo(ControllerHolder.HomeControl);
            }
        }

        private void btnDeleteUser_Click(object sender, RoutedEventArgs e)
        {

            DataReader.Instance.deleteUser();
            txtInfo.Text = "Benutzer gelöscht";
            ControllerHolder.TitleTextBox.Visibility = Visibility.Hidden;
            ControllerHolder.LoginControl.txtErrorMsg.Text = "User Deleted";
            ControllerNavigator.NavigateTo(ControllerHolder.LoginControl);
        }

        private void btnUpdateUser_Click(object sender, RoutedEventArgs e)
        {

            M8 mate = new M8();
            mate.setEmail(email.Text);
            mate.setFirstname(firstname.Text);
            mate.setLastname(lastname.Text);
            mate.setPassword(password.Text);
            mate.setSchoolclass(Database.Instance.currM8.getSchoolclass());

            Database.Instance.currM8 = mate;

            DataReader.Instance.updateUser(mate);

            txtInfo.Text = "Benutzer bearbeitet";
            ControllerHolder.TitleTextBox.Text = Database.Instance.currM8.getFirstname() + " " + Database.Instance.currM8.getLastname();

            if (Database.Instance.currSchoolclass.getId() == -1)
            {
                ControllerNavigator.NavigateTo(new NoFriendsControl());
            }
            else
            {
                ControllerNavigator.NavigateTo(ControllerHolder.HomeControl);
            }
        }
    }
}
