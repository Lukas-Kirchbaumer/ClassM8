using ClassM8_Client.Data;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
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
    public partial class MainWindow : Window
    {
        TextBlock myCurrClass;
        TextBlock classSchool;
        TextBlock classRoom;
        Button btnNewClass;
        Button btnSettings;
        Button btnEditClass;
        static HttpClient client = new HttpClient();

        public MainWindow()
        {
            InitializeComponent();
        }

        private void newAcc_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            Console.WriteLine("Register...");
            NewAccountWindow nac = new NewAccountWindow();
            nac.Visibility = Visibility.Visible;
        }

        private void newAcc_MouseEnter(object sender, MouseEventArgs e)
        {
            newAcc.Foreground = Brushes.White;
        }

        private void newAcc_MouseLeave(object sender, MouseEventArgs e)
        {
            newAcc.Foreground = Brushes.Black;
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
                   
                    Database.Instance.currUserId =  (int)obj.getId();
                    Console.WriteLine("CurrUserId: " + Database.Instance.currUserId);
                }

                if (Database.Instance.currUserId > 0)
                {
                    loginAccepted();
                } else {
                    loginDenied();
                }

            }
            catch (FileNotFoundException ex)
            {
                Console.WriteLine("Login error: " + ex.Message.ToString());
            }
        }


        private void loginAccepted() {

            StreamReader mysr = new StreamReader("Home.xaml");
            DependencyObject rootObject = XamlReader.Load(mysr.BaseStream) as DependencyObject;
            this.Content = rootObject;

            myCurrClass = LogicalTreeHelper.FindLogicalNode(rootObject, "myClass") as TextBlock;
            classSchool = LogicalTreeHelper.FindLogicalNode(rootObject, "classSchool") as TextBlock;
            classRoom = LogicalTreeHelper.FindLogicalNode(rootObject, "classRoom") as TextBlock;
            btnNewClass = LogicalTreeHelper.FindLogicalNode(rootObject, "btnNewClass") as Button;
            btnSettings = LogicalTreeHelper.FindLogicalNode(rootObject, "btnSettings") as Button;
            btnEditClass = LogicalTreeHelper.FindLogicalNode(rootObject, "btnEditClass") as Button;
            btnNewClass.Click += new RoutedEventHandler(addNewClass);
            btnSettings.Click += new RoutedEventHandler(openSettings);
            btnEditClass.Click += new RoutedEventHandler(openEditClass);
            btnNewClass.Visibility = Visibility.Hidden;

            getCurrUser();
            getUserClass();

            if (Database.Instance.currSchoolclass.getId() == -1) {
                btnNewClass.Visibility = Visibility.Visible;
            }


            this.Title = "Grüßgott, " + Database.Instance.currM8.getFirstname() + " " + Database.Instance.currM8.getLastname();
        }

        private void openSettings(object sender, RoutedEventArgs e)
        {
            SettingsWindow sw = new SettingsWindow(Database.Instance.currUserId, Database.Instance.currM8);
            sw.Visibility = Visibility.Visible;
        }

        private void openEditClass(object sender, RoutedEventArgs e)
        {
            EditClassWindow ecw = new EditClassWindow(Database.Instance.currSchoolclass);
            ecw.Show();
            Console.WriteLine("Klasse bearbeitet");
        }

        public void getUserClass()
        {
            try {
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
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(obj.GetType());
                    obj = (SchoolclassResult)serializer.ReadObject(ms);
                    ms.Close();

                    Database.Instance.currSchoolclass = obj.getSchoolclasses().ElementAt(0);
                    myCurrClass.Text = obj.getSchoolclasses().ElementAt(0).getName();
                    classSchool.Text = obj.getSchoolclasses().ElementAt(0).getSchool();
                    classRoom.Text = obj.getSchoolclasses().ElementAt(0).getRoom();
                }
            }
            catch (Exception ex) {
                Console.WriteLine("GetUserClass " + ex.Message);
                Schoolclass sc = new Schoolclass();
                sc.setId(-1);
                Database.Instance.currSchoolclass = sc;
            }
        }

        public void getCurrUser() {

            string url = "http://localhost:8080/ClassM8Web/services/user/" + Database.Instance.currUserId;

            var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
            //httpWebRequest.ContentType = "application/json; charset=utf-8";
            httpWebRequest.Accept = "application/json";
            httpWebRequest.Method = "GET";

            var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();


            using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
            {
                var result = streamReader.ReadToEnd();
                result = result.Split('[')[1];
                result = result.Split(']')[0];
                Console.WriteLine("CurrM8 " + result);

                M8 obj = Activator.CreateInstance<M8>();
                MemoryStream ms = new MemoryStream(Encoding.Unicode.GetBytes(result));
                DataContractJsonSerializer serializer = new DataContractJsonSerializer(obj.GetType());
                obj = (M8)serializer.ReadObject(ms);
                ms.Close();
                Database.Instance.currM8 = obj;
            }
        }


        private void addNewClass(object sender, RoutedEventArgs e)
        {
            NewClassWindow ncw = new NewClassWindow();
            ncw.Show();
            Console.WriteLine("Neue Klasse erstellen"); 
        }

        private void loginDenied(){
            errorMsg.Text = "Username oder Password falsch";
        }

    }
}
