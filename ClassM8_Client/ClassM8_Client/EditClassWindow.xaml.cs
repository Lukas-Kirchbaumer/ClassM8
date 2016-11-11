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
    /// Interaktionslogik für EditClassWindow.xaml
    /// </summary>
    public partial class EditClassWindow : Window
    {
        public EditClassWindow(Schoolclass currClass)
        {
            InitializeComponent();
            this.Title = currClass.getName() + " bearbeiten";
            txtName.Text = currClass.getName();
            txtSchool.Text = currClass.getSchool();
            txtRoom.Text = currClass.getRoom();


            foreach(M8 m in currClass.getClassMembers()){
                cbPres.Items.Add(m.getFirstname() + " " + m.getLastname());
                cbPresDep.Items.Add(m.getFirstname() + " " + m.getLastname());
            }
            if (Database.Instance.currSchoolclass.getPresident() != null) {
                cbPres.SelectedValue = currClass.getPresident().getFirstname() + " " + currClass.getPresident().getLastname();        
            }
            if (Database.Instance.currSchoolclass.getPresidentDeputy() != null) {
                cbPresDep.SelectedValue = currClass.getPresidentDeputy().getFirstname() + " " + currClass.getPresident().getLastname();
            }


        }

        private void btnCancle_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void btnUpdate_Click(object sender, RoutedEventArgs e)
        {
            if (/* cbPres.SelectedItem.Equals(cbPresDep.SelectedItem) || txtName.Text != "" || txtRoom.Text != "" || txtSchool.Text != ""*/ 1 == 2)
            {
                errorMsg.Visibility = Visibility.Visible;
            }
            else
            {
                errorMsg.Visibility = Visibility.Hidden;
                updateClass();
            }
        }

        private void updateClass()
        {

            string url = "http://localhost:8080/ClassM8Web/services/schoolclass/?id=" + Database.Instance.currSchoolclass.getId();

            Schoolclass sc = new Schoolclass();
            sc.setId(Database.Instance.currSchoolclass.getId());
            sc.setName(txtName.Text);
            sc.setSchool(txtSchool.Text);
            sc.setRoom(txtRoom.Text);
            sc.setClassMembers(Database.Instance.currSchoolclass.getClassMembers());

            M8 pres = null;
            M8 deputy = null;

            string presFN = cbPres.SelectedItem.ToString().Split(' ')[0];
            string presNN = cbPres.SelectedItem.ToString().Split(' ')[1];
            string depFN = cbPresDep.SelectedItem.ToString().Split(' ')[0];
            string depNN = cbPresDep.SelectedItem.ToString().Split(' ')[1];


            foreach(M8 m in Database.Instance.currSchoolclass.getClassMembers()) {

                if (m.getFirstname().Equals(presFN) && m.getLastname().Equals(presNN))
                {
                    pres = m;
                }
                if (m.getFirstname().Equals(depFN) && m.getLastname().Equals(depNN))
                {
                    deputy = m;
                }

            }
                                           
            sc.setPresident(pres);
            sc.setPresidentDeputy(deputy);


            MemoryStream stream1 = new MemoryStream();
            DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(Schoolclass));
            ser.WriteObject(stream1, sc);
            stream1.Position = 0;
            StreamReader sr = new StreamReader(stream1);
            Console.Write("JSON form of Schoolclass object: ");
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

            Database.Instance.currSchoolclass = sc;
            txtInfo.Text = "Klasse bearbeitet";
        }

        private void btnDelete_Click(object sender, RoutedEventArgs e)
        {
            string url = "http://localhost:8080/ClassM8Web/services/schoolclass/?id=" + Database.Instance.currSchoolclass.getId();

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
            txtInfo.Text = "Klasse gelöscht";
        }
    }
}
