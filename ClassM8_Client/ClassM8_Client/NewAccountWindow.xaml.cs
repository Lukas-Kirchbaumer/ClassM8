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
            string url = "http://localhost:8080/ClassM8Web/services/user";

            M8 mate = new M8();
            mate.setEmail(email.Text);
            mate.setFirstname(firstname.Text);
            mate.setLastname(lastname.Text);
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
            httpWebRequest.ContentType = "application/json";
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
                Console.WriteLine(result);
            }

            this.Close();

        }
    }
}
