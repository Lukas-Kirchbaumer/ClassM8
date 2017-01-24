using ClassM8_Client.Data;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Json;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace ClassM8_Client
{
    /// <summary>
    /// Interaktionslogik für MainWindow.xaml
    /// </summary>
    public partial class LoginWindow : Window
    {
        TextBlock myCurrClass;
        TextBlock classSchool;
        TextBlock classRoom;
        Button btnNewClass;
        Button btnSettings;
        Button btnEditClass;
        Button btnVote;
        Button btnFiles;
        ListBox lbAllM8s;
        Button btnAddM8;
        static HttpClient client = new HttpClient();
        DataReader dr;

        public LoginWindow()
        {
            InitializeComponent();
            dr = DataReader.Instance;
        }


        private void btnLogin_Click(object sender, RoutedEventArgs e)
        {
            try
            {

                M8 mate = new M8();
                mate.setEmail(email.Text);
                mate.setPassword(password.Password);

                dr.loginM8(mate);

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

        private void loginDenied()
        {
            txtErrorMsg.Text = "Username oder Password falsch";
        }


        private void loginAccepted()
        {

            StreamReader mysr = new StreamReader("Home.xaml");
            DependencyObject rootObject = XamlReader.Load(mysr.BaseStream) as DependencyObject;
            this.Content = rootObject;

            myCurrClass = LogicalTreeHelper.FindLogicalNode(rootObject, "myClass") as TextBlock;
            classSchool = LogicalTreeHelper.FindLogicalNode(rootObject, "classSchool") as TextBlock;
            classRoom = LogicalTreeHelper.FindLogicalNode(rootObject, "classRoom") as TextBlock;
            btnNewClass = LogicalTreeHelper.FindLogicalNode(rootObject, "btnNewClass") as Button;
            btnSettings = LogicalTreeHelper.FindLogicalNode(rootObject, "btnSettings") as Button;
            btnEditClass = LogicalTreeHelper.FindLogicalNode(rootObject, "btnEditClass") as Button;
            btnVote = LogicalTreeHelper.FindLogicalNode(rootObject, "btnVote") as Button;
            btnFiles = LogicalTreeHelper.FindLogicalNode(rootObject, "btnFiles") as Button;
            lbAllM8s = LogicalTreeHelper.FindLogicalNode(rootObject, "lbAllM8s") as ListBox;
            btnAddM8 = LogicalTreeHelper.FindLogicalNode(rootObject, "btnAddM8") as Button;
            btnNewClass.Click += new RoutedEventHandler(addNewClass);
            btnSettings.Click += new RoutedEventHandler(openSettings);
            btnEditClass.Click += new RoutedEventHandler(openEditClass);
            btnVote.Click += new RoutedEventHandler(openVote);
            btnFiles.Click += new RoutedEventHandler(openFilesWindow);
            btnAddM8.Click += new RoutedEventHandler(openAddM8);
            btnNewClass.Visibility = Visibility.Hidden;
           
            
            dr.getM8();
            dr.getM8Class();
            showM8Class();

            lbAllM8s.ItemsSource = Database.Instance.currSchoolclass.getClassMembers();

            if (Database.Instance.currM8.isHasVoted()) {
                btnVote.Visibility = Visibility.Hidden;
            }

            if (Database.Instance.currSchoolclass.getId() == -1)
            {
                btnNewClass.Visibility = Visibility.Visible;
                btnEditClass.Visibility = Visibility.Hidden;
            }


            this.Title = "Grüßgott, " + Database.Instance.currM8.getFirstname() + " " + Database.Instance.currM8.getLastname();
        }


        private void showM8Class() {

            if (Database.Instance.currSchoolclass != null)
            {
                Schoolclass sc = Database.Instance.currSchoolclass;
                myCurrClass.Text = sc.getName();
                classSchool.Text = sc.getSchool();
                classRoom.Text = sc.getRoom();
            }
            else
            {
                myCurrClass.Text = "";
                classSchool.Text = "";
                classRoom.Text = "";
            }
        }

        private void openVote(object sender, RoutedEventArgs e)
        {
            VoteWindow vw = new VoteWindow();
            vw.ShowDialog();
            if (Database.Instance.currM8.isHasVoted()) {
                btnVote.Visibility = Visibility.Hidden;
            }
        }

        private void openAddM8(object sender, RoutedEventArgs e)
        {
            AddM8Window addM8w = new AddM8Window();
            addM8w.ShowDialog();

            lbAllM8s.ItemsSource = null;
            lbAllM8s.ItemsSource = Database.Instance.currSchoolclass.getClassMembers();

        }

        private void openFilesWindow(object sender, RoutedEventArgs e) {
            FilesWindow fw = new FilesWindow();
            fw.Show();
        }

        private void openSettings(object sender, RoutedEventArgs e)
        {
            SettingsWindow sw = new SettingsWindow(Database.Instance.currUserId, Database.Instance.currM8);
            sw.ShowDialog();
            if (Database.Instance.currM8 == null)
            {
                LoginWindow newLoginWindow = new LoginWindow();
                newLoginWindow.Show();
                this.Close();
            }
            else
            {
                this.Title = "Grüßgott, " + Database.Instance.currM8.getFirstname() + " " + Database.Instance.currM8.getLastname();
            }
        }

        private void openEditClass(object sender, RoutedEventArgs e)
        {
            EditClassWindow ecw = new EditClassWindow(Database.Instance.currSchoolclass);
            ecw.ShowDialog();
            Schoolclass sc = Database.Instance.currSchoolclass;
            if (sc != null)
            {
                myCurrClass.Text = sc.getName();
                classSchool.Text = sc.getSchool();
                classRoom.Text = sc.getRoom();
            }
            else {
                btnEditClass.Visibility = Visibility.Hidden;
                btnNewClass.Visibility = Visibility.Visible;

                myCurrClass.Text = "";
                classSchool.Text = "";
                classRoom.Text = "";
            }
        }

        private void addNewClass(object sender, RoutedEventArgs e)
        {
            NewClassWindow ncw = new NewClassWindow();
            ncw.ShowDialog();
            Schoolclass sc = Database.Instance.currSchoolclass;
            myCurrClass.Text = sc.getName();
            classSchool.Text = sc.getSchool();
            classRoom.Text = sc.getRoom();

            Console.WriteLine("Neue Klasse erstellen");
        }

        private void btnNewAcc_Click(object sender, RoutedEventArgs e)
        {
            Console.WriteLine("Register...");
            NewAccountWindow nac = new NewAccountWindow();
            nac.Visibility = Visibility.Visible;
        }


    }
}
