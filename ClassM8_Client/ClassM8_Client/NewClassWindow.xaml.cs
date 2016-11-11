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
    /// Interaktionslogik für NewClassWindow.xaml
    /// </summary>
    public partial class NewClassWindow : Window
    {
        public NewClassWindow()
        {
            InitializeComponent();
        }

        private void btnNewClassCancle_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void btnNewClass_Click(object sender, RoutedEventArgs e)
        {
            createNewClass();
        }

        private void createNewClass() {
            string url = "http://localhost:8080/ClassM8Web/services/schoolclass/?m8id=" + Database.Instance.currM8.getId();

            Schoolclass sc = new Schoolclass();
            sc.setName(txtClassname.Text);
            sc.setSchool(txtSchool.Text);
            sc.setRoom(txtRoom.Text);

            sc.setPresident(null);
            sc.setPresidentDeputy(null);

            MemoryStream stream1 = new MemoryStream();
            DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(Schoolclass));
            ser.WriteObject(stream1, sc);
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
        }
    }
}
