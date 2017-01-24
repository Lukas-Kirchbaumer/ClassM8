using ClassM8_Client.Data;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Runtime.Serialization.Json;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace ClassM8_Client
{
    /// <summary>
    /// Interaktionslogik für NewAccountWindow.xaml
    /// </summary>
    public partial class NewAccountWindow : Window
    {
        public NewAccountWindow()
        {
            InitializeComponent();
        }

        private void btnNewAccCancle_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void btnNewAccCreate_Click(object sender, RoutedEventArgs e)
        {
            
            string pw = password.Password;
            String pwv = passwordverify.Password;

            if (pw.Equals(pwv) == true)
            {
                Console.WriteLine("User: " + firstname.Text + " " + lastname.Text);
                txtError.Text = "";
                createNewUser();
            }
            else {
                txtError.Text = "Passwörter stimme  nicht überein";
            }
        }

        public void createNewUser()
        {
            M8 mate = new M8();
            mate.setEmail(email.Text);
            mate.setFirstname(firstname.Text);
            mate.setLastname(lastname.Text);
            mate.setPassword(password.Password);

            DataReader.Instance.createNewUser(mate);
        }

    }
}
