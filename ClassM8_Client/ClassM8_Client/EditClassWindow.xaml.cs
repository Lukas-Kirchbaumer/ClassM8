﻿using ClassM8_Client.Data;
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
            try
            {
                txtPres.Text = currClass.getPresident().getFirstname() + " " + currClass.getPresident().getLastname();
                txtPresDep.Text = currClass.getPresidentDeputy().getFirstname() + " " + currClass.getPresidentDeputy().getLastname();
            }catch(Exception ex)
            {
                txtPres.Text = "Noch niemand";
                txtPresDep.Text = "Noch niemand";
            }
        }

        private void btnCancle_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void btnUpdate_Click(object sender, RoutedEventArgs e)
        {
            if (txtName.Text != "" && txtRoom.Text != "" && txtSchool.Text != "" )
            {
                errorMsg.Visibility = Visibility.Visible;
            }
            else
            {
                errorMsg.Visibility = Visibility.Hidden;
                updateClass();
                this.Close();
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
            sc.setPresident(Database.Instance.currSchoolclass.getPresident());
            sc.setPresidentDeputy(Database.Instance.currSchoolclass.getPresidentDeputy());
            sc.setClassFiles(Database.Instance.currSchoolclass.getClassFiles());

            Database.Instance.currSchoolclass = sc;
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

            Database.Instance.currSchoolclass = null;

            using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
            {
                var result = streamReader.ReadToEnd();
                Console.WriteLine("Res: " + result);
            }
            txtInfo.Text = "Klasse gelöscht";
            this.Close();
        }
    }
}