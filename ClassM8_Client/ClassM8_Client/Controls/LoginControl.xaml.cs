using ClassM8_Client.Controls;
using ClassM8_Client.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
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
using System.Windows.Threading;

namespace ClassM8_Client
{
    /// <summary>
    /// Interaction logic for LoginControl.xaml
    /// </summary>
    public partial class LoginControl : UserControl
    {
        BackgroundWorker bw = new BackgroundWorker();
        M8 mate = new M8();
        Boolean isWorking = false;

        public LoginControl()
        {
            InitializeComponent();
            bw.WorkerReportsProgress = true;
            bw.WorkerSupportsCancellation = true;
            bw.DoWork += new DoWorkEventHandler(bw_DoWork);
            bw.ProgressChanged += new ProgressChangedEventHandler(bw_ProgressChanged);
            bw.RunWorkerCompleted += new RunWorkerCompletedEventHandler(bw_WorkCompleted);
        }

        private void bw_WorkCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            isWorking = false;
            if (!e.Cancelled)
            {
                if (Database.Instance.currSchoolclass.getId() == -1)
                {
                    InitTitleBar();
                    ControllerNavigator.NavigateTo(new NoFriendsControl());
                    
                }

                else
                {
                    ControllerHolder.HomeControl.txtClass.Text = Database.Instance.currSchoolclass.getName();
                    ControllerHolder.HomeControl.txtSchool.Text = Database.Instance.currSchoolclass.getSchool();
                    ControllerHolder.HomeControl.txtRoom.Text = Database.Instance.currSchoolclass.getRoom();
                    if (Database.Instance.currM8.isHasVoted())
                    {

                        ControllerHolder.HomeControl.btnVote.Visibility = Visibility.Hidden;
                    }
                    else
                    {
                        ControllerHolder.HomeControl.btnVote.Visibility = Visibility.Visible;
                    }
                    ControllerHolder.HomeControl.SetFinished(false);
                    ControllerHolder.HomeControl.loadChat();
                    ControllerHolder.HomeControl.lbAllM8s.ItemsSource = Database.Instance.currSchoolclass.getClassMembers();
                    InitTitleBar();
                    ControllerNavigator.NavigateTo(ControllerHolder.HomeControl);
                }
            }
        }

        private void InitTitleBar()
        {
            ControllerHolder.TitleTextBox.Text = Database.Instance.currM8.getFirstname() + " " + Database.Instance.currM8.getLastname();
            ControllerHolder.TitleSettings.Visibility = Visibility.Visible;
            ControllerHolder.TitleLogout.Visibility = Visibility.Visible;
            progressBar.Visibility = Visibility.Hidden;
        }

        private void bw_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {
            if (e.ProgressPercentage == 0)
            {
                progressBar.Visibility = Visibility.Visible;
            }
            else if (e.ProgressPercentage == 101)
            {
                progressBar.Value = e.ProgressPercentage;
                bw.CancelAsync();
            }
            else
            {
                progressBar.Value = e.ProgressPercentage;
            }
        }

        private void bw_DoWork(object sender, DoWorkEventArgs e)
        {
            try
            {
                (sender as BackgroundWorker).ReportProgress(0);
                DataReader.Instance.loginM8(mate);
                (sender as BackgroundWorker).ReportProgress(30);

                if (Database.Instance.currUserId > 0)
                {
                    loginAccepted(sender, e);
                }
                else
                {
                    loginDenied(sender, e);
                }

            }
            catch (FileNotFoundException ex)
            {
                Console.WriteLine("Login error: " + ex.Message.ToString());
            }
        }



        private void btnLogin_Click(object sender, RoutedEventArgs e)
        {

            if (!isWorking) {
                isWorking = true;
                mate = new M8();
                if (email.Text.Length != 0)
                {

                    mate.setEmail(email.Text);
                    mate.setPassword(password.Password);
                    bw.RunWorkerAsync();

                }
            }
            else
            {
                txtErrorMsg.Text = "Login already in Progress, wait for Completion";
            }

        }


        private void loginDenied(object sender, DoWorkEventArgs e)
        {
            txtErrorMsg.Dispatcher.Invoke(new Action(() => {
                txtErrorMsg.Text = "Username or Password was not correct";
            }));
            e.Cancel = true;
            (sender as BackgroundWorker).ReportProgress(101);
           
        }

        private void loginAccepted(object sender, DoWorkEventArgs e)
        {
            DataReader.Instance.getM8();
            (sender as BackgroundWorker).ReportProgress(50);
            DataReader.Instance.getM8Class();
            if (Database.Instance.currSchoolclass.getId() != -1) {
                DataReader.Instance.getAllEmotesForClass();
                Database.Instance.mapEmotes();
            }
            (sender as BackgroundWorker).ReportProgress(80);
            Console.WriteLine(Database.Instance.currSchoolclass.getId());
            (sender as BackgroundWorker).ReportProgress(100);
            

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

        bool IsValidEmail(string email)
        {
            
            var check = new EmailAddressAttribute();
            return check.IsValid(email);
        }


    }
}
