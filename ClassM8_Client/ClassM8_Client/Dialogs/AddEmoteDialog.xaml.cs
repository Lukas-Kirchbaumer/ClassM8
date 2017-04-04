using ClassM8_Client.Data;
using Microsoft.Win32;
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
    /// Interaction logic for AddEmoteDialog.xaml
    /// </summary>
    public partial class AddEmoteDialog : Window
    {
        Emote emote = new Emote();
        String tempFile = "";
        public AddEmoteDialog()
        {
            InitializeComponent();
            emote.setShortString("");
        }

        private void btnAdd_Click(object sender, RoutedEventArgs e)
        {
            emote.setShortString(txtShorString.Text);
            if (emote.getShortString().Length != 0)
            {
                txtError.Text = "";
                createMetaData(emote);
            }
            else {
                txtError.Text = "Name must not be empty";
            }
            this.Close();
            
        }

        private void btnFile_Click(object sender, RoutedEventArgs e)
        {
            OpenFileDialog dlg = new OpenFileDialog();
            dlg.DefaultExt = ".png";
            Nullable<bool> result = dlg.ShowDialog();
            if (result == true)
            {
                string filename = dlg.FileName;
                tempFile = dlg.FileName;
                FileInfo oFileInfo = new FileInfo(filename);

                if (filename != null || filename.Length == 0)
                {
                    Console.WriteLine(oFileInfo.Extension);
                    Console.WriteLine(oFileInfo.Name);
                    Console.WriteLine(oFileInfo.Length);

                    emote.setContentSize(oFileInfo.Length);
                    emote.setFileName(oFileInfo.Name);

                    txtFile.Text = oFileInfo.Name;

                }

            }
        }


        private void createMetaData(Emote emote)
        {
            string url = AppSettings.ConnectionString + "emote/?schoolclassid=" + Database.Instance.currSchoolclass.getId();
            Console.WriteLine(url);
            try
            {
                MemoryStream stream1 = new MemoryStream();
                DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(Data.Emote));
                ser.WriteObject(stream1, emote);
                stream1.Position = 0;
                StreamReader sr = new StreamReader(stream1);
                Console.Write("JSON form of File object: ");
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
                    Console.WriteLine("Result for File: " + result);


                    LoginResult obj = Activator.CreateInstance<LoginResult>();
                    MemoryStream ms = new MemoryStream(Encoding.Unicode.GetBytes(result));
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(obj.GetType());
                    obj = (LoginResult)serializer.ReadObject(ms);


                    ms.Close();
                    emote.setId(obj.getId());
                    HttpUploadFile(AppSettings.ConnectionString + "emote/content/" + (int)obj.getId(),
                        tempFile, "file");
                }
            }
            catch (WebException ex)
            {
                Console.WriteLine(ex.Response);
                Console.WriteLine();
                Console.WriteLine();
                Console.WriteLine(ex.Message);
                Console.WriteLine();
                Console.WriteLine();
                Console.WriteLine(ex.Data);
                Console.WriteLine();
                Console.WriteLine();
            }

        }

        public static void HttpUploadFile(string url, string file, string paramName)
        {

            string contentType = "image/png";
            Console.WriteLine(string.Format("Uploading {0} to {1}", file, url));
            string boundary = "---------------------------" + DateTime.Now.Ticks.ToString("x");
            byte[] boundarybytes = System.Text.Encoding.ASCII.GetBytes("\r\n--" + boundary + "\r\n");

            HttpWebRequest wr = (HttpWebRequest)WebRequest.Create(url);
            wr.ContentType = "multipart/form-data; boundary=" + boundary;
            wr.Method = "POST";
            wr.KeepAlive = true;

            Stream rs = wr.GetRequestStream();

            string formdataTemplate = "Content-Disposition: form-data; name=\"{0}\"\r\n\r\n{1}";
            rs.Write(boundarybytes, 0, boundarybytes.Length);
            rs.Write(boundarybytes, 0, boundarybytes.Length);

            string headerTemplate = "Content-Disposition: form-data; name=\"{0}\"; filename=\"{1}\"\r\nContent-Type: {2}\r\n\r\n";
            string header = string.Format(headerTemplate, paramName, file, contentType);
            byte[] headerbytes = System.Text.Encoding.UTF8.GetBytes(header);
            rs.Write(headerbytes, 0, headerbytes.Length);

            FileStream fileStream = new FileStream(file, FileMode.Open, FileAccess.Read);
            byte[] buffer = new byte[4096];
            int bytesRead = 0;
            while ((bytesRead = fileStream.Read(buffer, 0, buffer.Length)) != 0)
            {
                rs.Write(buffer, 0, bytesRead);
            }
            fileStream.Close();

            byte[] trailer = System.Text.Encoding.ASCII.GetBytes("\r\n--" + boundary + "--\r\n");
            rs.Write(trailer, 0, trailer.Length);
            rs.Close();

            WebResponse wresp = null;
            try
            {
                wresp = wr.GetResponse();
                Stream stream2 = wresp.GetResponseStream();
                StreamReader reader2 = new StreamReader(stream2);
                Console.WriteLine(string.Format("File uploaded, server response is: {0}", reader2.ReadToEnd()));
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error uploading file", ex);
                if (wresp != null)
                {
                    wresp.Close();
                    wresp = null;
                }
            }
            finally
            {
                wr = null;
            }
        }
    }
}
