using ClassM8_Client.Controls;
using ClassM8_Client.Data;
using System;
using System.Collections.Generic;
using System.IO;
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

namespace ClassM8_Client
{
    /// <summary>
    /// Interaction logic for LoginControl.xaml
    /// </summary>
    public partial class LoginControl : UserControl
    {
        HomeControl homeControl = new HomeControl();

        public LoginControl()
        {
            InitializeComponent();
        }

        private void btnLogin_Click(object sender, RoutedEventArgs e)
        {   
            try
            {
                M8 mate = new M8();
                mate.setEmail(email.Text);
                mate.setPassword(password.Password);

                DataReader.Instance.loginM8(mate);

                if (Database.Instance.currUserId > 0)
                {
                    loginAccepted();
                }
                else
                {
                    loginDenied();
                }

            }
            catch (FileNotFoundException ex)
            {
                Console.WriteLine("Login error: " + ex.Message.ToString());
            }
            
        }


        private void btnNewAcc_Click(object sender, RoutedEventArgs e)
        {
            
            Console.WriteLine("Register...");
            ControllerNavigator.NavigateTo(new NewAccountControl());

            if (Database.Instance.currM8 != null)
            {
                email.Text = Database.Instance.currM8.getEmail();
            }
            

        }

        private void loginDenied()
        {
            txtErrorMsg.Text = "Username or Password was not correct";
        }

        private void loginAccepted()
        {
            DataReader.Instance.getM8();
            DataReader.Instance.getM8Class();


            Console.WriteLine(Database.Instance.currSchoolclass.getId());
            if (Database.Instance.currSchoolclass.getId() == -1)
            {
                ControllerNavigator.NavigateTo(new NoFriendsControl());
                ControllerHolder.TitleTextBox.Text = Database.Instance.currM8.getFirstname() + " " + Database.Instance.currM8.getLastname();
                ControllerHolder.TittleSettings.Visibility = Visibility.Visible;
            }
            else {
                ControllerHolder.HomeControl.lbAllM8s.ItemsSource = Database.Instance.currSchoolclass.getClassMembers();
                ControllerHolder.TitleTextBox.Text = Database.Instance.currM8.getFirstname() + " " + Database.Instance.currM8.getLastname();
                ControllerHolder.TittleSettings.Visibility = Visibility.Visible;
                ControllerNavigator.NavigateTo(ControllerHolder.HomeControl);
            }
            
        }




    }
}
