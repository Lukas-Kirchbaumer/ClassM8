using ClassM8_Client.Data;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Runtime.Serialization.Json;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using System.Windows.Threading;

namespace ClassM8_Client
{
    /// <summary>
    /// Interaktionslogik für SettingsWindow.xaml
    /// </summary>
    public partial class SettingsWindow : Window
    {
        static int currUserId;
        static M8 currM8;

        public SettingsWindow(int id, M8 mate)
        {
            InitializeComponent();
            currUserId = id;
            currM8 = mate;
            
            firstname.Text = currM8.getFirstname();
            lastname.Text = currM8.getLastname();
            email.Text = currM8.getEmail();
            password.Text = currM8.getPassword();
        }

        private void btnCanle_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void btnDeleteUser_Click(object sender, RoutedEventArgs e)
        {

            DataReader.Instance.deleteUser();
            txtInfo.Text = "Benutzer gelöscht";

            //TODO: go back to initial Login
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
            this.Close();
        }
    }
}
