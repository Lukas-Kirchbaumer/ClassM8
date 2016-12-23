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

        public LoginWindow()
        {
            InitializeComponent();
        }


        private void btnLogin_Click(object sender, RoutedEventArgs e)
        {
            try
            {

                string url = "http://localhost:8080/ClassM8Web/services/login";

                M8 mate = new M8();
                mate.setEmail(email.Text);
                mate.setPassword(password.Password);

                MemoryStream stream1 = new MemoryStream();
                DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(M8));
                ser.WriteObject(stream1, mate);
                stream1.Position = 0;
                StreamReader sr = new StreamReader(stream1);
                Console.Write("JSON form of M8 object: ");
                string jsonContent = sr.ReadToEnd();

                Console.WriteLine(jsonContent);

                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json; charset=utf-8";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "POST";

                using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
                {
                    streamWriter.Write(jsonContent);
                    streamWriter.Flush();
                }

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine("Result for M8: " + result);

                    LoginResult obj = Activator.CreateInstance<LoginResult>();
                    MemoryStream ms = new MemoryStream(Encoding.Unicode.GetBytes(result));
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(obj.GetType());
                    obj = (LoginResult)serializer.ReadObject(ms);
                    ms.Close();

                    Database.Instance.currUserId = (int)obj.getId();
                    Console.WriteLine("CurrUserId: " + Database.Instance.currUserId);
                }

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
            

            getCurrUser();
            getUserClass();

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
            }
        }

        public void getUserClass()
        {
            try
            {
                string url = "http://localhost:8080/ClassM8Web/services/schoolclass/" + Database.Instance.currUserId;

                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json; charset=utf-8";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "GET";

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();


                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine(url + "  Res: " + result);

                    SchoolclassResult obj = Activator.CreateInstance<SchoolclassResult>();
                    MemoryStream ms = new MemoryStream(Encoding.Unicode.GetBytes(result));
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(obj.GetType(), new DataContractJsonSerializerSettings
                    {
                        DateTimeFormat = new DateTimeFormat("yyyy-MM-dd")
                    });
                    obj = (SchoolclassResult)serializer.ReadObject(ms);
                    ms.Close();


                    Database.Instance.currSchoolclass = obj.getSchoolclasses().ElementAt(0);
                    Console.WriteLine("Files in class: " + Database.Instance.currSchoolclass.getClassFiles().Count);

                    if (Database.Instance.currSchoolclass != null) {

                        myCurrClass.Text = obj.getSchoolclasses().ElementAt(0).getName();
                        classSchool.Text = obj.getSchoolclasses().ElementAt(0).getSchool();
                        classRoom.Text = obj.getSchoolclasses().ElementAt(0).getRoom();
                    }
                    else
                    {
                        myCurrClass.Text = "";
                        classSchool.Text = "";
                        classRoom.Text = "";
                    }
                    
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("GetUserClass " + ex.Message);
                Schoolclass sc = new Schoolclass();
                sc.setId(-1);
                Database.Instance.currSchoolclass = sc;
            }
        }

        public void getCurrUser()
        {

            string url = "http://localhost:8080/ClassM8Web/services/user/" + Database.Instance.currUserId;

            var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
            httpWebRequest.ContentType = "application/json; charset=utf-8";
            httpWebRequest.Accept = "application/json";
            httpWebRequest.Method = "GET";

            var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();


            using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
            {
                var result = streamReader.ReadToEnd();
                Console.WriteLine("CurrM8 " + result);

                M8Result obj = Activator.CreateInstance<M8Result>();
                MemoryStream ms = new MemoryStream(Encoding.Unicode.GetBytes(result));
                DataContractJsonSerializer serializer = new DataContractJsonSerializer(obj.GetType());
                obj = (M8Result)serializer.ReadObject(ms);
                ms.Close();
         
                Console.WriteLine("m8s: " + obj.getM8s().Count);

                Database.Instance.currM8 = obj.getM8s().ElementAt(0);

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

        private void loginDenied()
        {
            txtErrorMsg.Text = "Username oder Password falsch";
        }

        private void btnNewAcc_Click(object sender, RoutedEventArgs e)
        {
            Console.WriteLine("Register...");
            NewAccountWindow nac = new NewAccountWindow();
            nac.Visibility = Visibility.Visible;
        }
    }
}
