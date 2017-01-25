﻿using ClassM8_Client.Data;
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
    /// Interaction logic for NewAccountControl.xaml
    /// </summary>
    public partial class NewAccountControl : UserControl
    {

        public NewAccountControl()
        {
            InitializeComponent();
        }
        private void btnNewAccCancle_Click(object sender, RoutedEventArgs e)
        {
            ControllerNavigator.NavigateTo(ControllerHolder.LoginControl);
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
                ControllerHolder.LoginControl.email.Text = Database.Instance.currM8.getEmail();
                ControllerNavigator.NavigateTo(ControllerHolder.LoginControl); 
            }
            else
            {
                txtError.Text = "Passwords don't match";
            }
        }

        public void createNewUser()
        {
            M8 mate = new M8();
            mate.setEmail(email.Text);
            mate.setFirstname(firstname.Text);
            mate.setLastname(lastname.Text);
            mate.setPassword(password.Password);
            Database.Instance.currM8 = mate;
            DataReader.Instance.createNewUser(mate);
        }
    }
}
