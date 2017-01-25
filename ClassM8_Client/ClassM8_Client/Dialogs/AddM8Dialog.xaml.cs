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

namespace ClassM8_Client.Dialogs
{
    /// <summary>
    /// Interaction logic for AddM8Dialog.xaml
    /// </summary>
    public partial class AddM8Dialog : Window
    {
        private M8 newM8;
        public AddM8Dialog()
        {
            InitializeComponent();
        }

        private void btnAddM8_Click(object sender, RoutedEventArgs e)
        {

            if (txtAddM8Email.Text != "")
            {
                addM8(getM8IdByEmail());
                if (newM8 != null)
                {
                    ControllerHolder.HomeControl.lbAllM8s.ItemsSource = null;
                    ControllerHolder.HomeControl.lbAllM8s.ItemsSource = Database.Instance.currSchoolclass.getClassMembers();
                    this.Close();
                }
                else
                {
                    tbAddM8Error.Text = "M8 nicht gefunden";
                }
            }

        }

        private void addM8(int id)
        {
            string url = AppSettings.ConnectionString + "schoolclass/" + id + "?scid=" + Database.Instance.currSchoolclass.getId();

            var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
            httpWebRequest.ContentType = "application/json";
            httpWebRequest.Accept = "application/json";
            httpWebRequest.Method = "POST";

            var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
            using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
            {
                var result = streamReader.ReadToEnd();
                Console.WriteLine(result);
            }

        }

        private int getM8IdByEmail()
        {
            int id = -1;
            try
            {
                string url = AppSettings.ConnectionString + "user/byemail/" + txtAddM8Email.Text;

                Console.WriteLine(url);

                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json; charset=utf-8";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "GET";

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();

                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine("AddM8 got - " + result);

                    M8Result obj = Activator.CreateInstance<M8Result>();
                    MemoryStream ms = new MemoryStream(Encoding.Unicode.GetBytes(result));
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(obj.GetType());
                    obj = (M8Result)serializer.ReadObject(ms);
                    ms.Close();

                    id = (int)obj.getM8s().ElementAt(0).getId();
                    newM8 = obj.getM8s().ElementAt(0);
                    Database.Instance.currSchoolclass.getClassMembers().Add(obj.getM8s().ElementAt(0));

                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                tbAddM8Error.Text = "M8 nicht gefunden";
            }

            Console.WriteLine("id  " + id);

            return id;
        }
    }
}
