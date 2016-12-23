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
            string url = "http://localhost:8080/ClassM8Web/services/user/?id=" + Database.Instance.currUserId;

            var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
            httpWebRequest.ContentType = "application/json; charset=utf-8";
            httpWebRequest.Accept = "application/json";
            httpWebRequest.Method = "DELETE";

            var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();


            using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
            {
                var result = streamReader.ReadToEnd();
                Console.WriteLine("Res: " + result);
            
            }
            txtInfo.Text = "Benutzer gelöscht";
            Database.Instance.currM8 = null;

            //TODO: go back to initial Login
        }

        private void btnUpdateUser_Click(object sender, RoutedEventArgs e)
        {
            string url = "http://localhost:8080/ClassM8Web/services/user/?id=" + Database.Instance.currUserId;

            M8 mate = new M8();
            mate.setEmail(email.Text);
            mate.setFirstname(firstname.Text);
            mate.setLastname(lastname.Text);
            mate.setPassword(password.Text);

            Database.Instance.currM8 = mate;
            MemoryStream stream1 = new MemoryStream();
            DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(M8));
            ser.WriteObject(stream1, mate);
            stream1.Position = 0;
            StreamReader sr = new StreamReader(stream1);
            Console.Write("JSON form of M8 object: ");
            string jsonContent = sr.ReadToEnd();

            Console.WriteLine(jsonContent);

            var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
            httpWebRequest.ContentType = "application/json";
            httpWebRequest.Accept = "application/json";
            httpWebRequest.Method = "PUT";

            using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
            {

                streamWriter.Write(jsonContent);
                streamWriter.Flush();
            }


            var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
            using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
            {
                var result = streamReader.ReadToEnd();
                Console.WriteLine(result);
            }

            txtInfo.Text = "Benutzer bearbeitet";
            this.Close();
        }
    }
}
