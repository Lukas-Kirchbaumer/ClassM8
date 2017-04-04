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
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace ClassM8_Client.Controls
{
    /// <summary>
    /// Interaction logic for FileShareControl.xaml
    /// </summary>
    public partial class FileShareControl : UserControl
    {
        String tempFile = "";

        public FileShareControl()
        {
            InitializeComponent();

            fillLb();
        }

        private void fillLb()
        {
            List<Data.File> items = Database.Instance.currSchoolclass.getClassFiles();

            Console.WriteLine(Database.Instance.currSchoolclass.getClassFiles());

            btnDownload.IsEnabled = false;
            if (items.Count > 0)
            {
                Console.WriteLine(items.Count);
                lbDownloadableFiles.ItemsSource = null;
                lbDownloadableFiles.Items.Clear();
                lbDownloadableFiles.ItemsSource = items;
            }
            else
            {
                lbDownloadableFiles.Items.Add("Keine geteilten Inhalte");
            }
        }

        private void btnDownload_Click(object sender, RoutedEventArgs e)
        {
            Data.File file = lbDownloadableFiles.SelectedItem as Data.File;

            if (file != null)
            {

                Console.WriteLine("File to download - " + file.getFileName() + "." + file.getContentType());

                Stream stream = null;
                //This controls how many bytes to read at a time and send to the client
                int bytesToRead = 10000;
                // Buffer to read bytes in chunk size specified above
                byte[] buffer = new Byte[bytesToRead];
                // The number of bytes read
                string url = AppSettings.ConnectionString + "file/content/" + file.getId();
                Console.WriteLine(url);
                try
                {
                    HttpWebRequest fileReq = (HttpWebRequest)HttpWebRequest.Create(url);
                    HttpWebResponse fileResp = (HttpWebResponse)fileReq.GetResponse();
                    if (fileReq.ContentLength > 0)
                        fileResp.ContentLength = fileReq.ContentLength;
                    stream = fileResp.GetResponseStream();
                    Console.WriteLine(stream);


                    using (var fileStream = new FileStream((System.IO.Directory.GetCurrentDirectory() + "/downloads/") + file.getFileName(), FileMode.Create, FileAccess.Write))
                    {
                        stream.CopyTo(fileStream);
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine(ex.Message);
                }
                finally
                {
                    if (stream != null)
                    {
                        //Close the input stream
                        stream.Close();
                    }
                }
            }
            else
            {

            }

        }

        private void lbDownloadableFiles_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (lbDownloadableFiles.SelectedItem != null && Database.Instance.currSchoolclass.getClassFiles().Count > 0)
            {
                btnDownload.IsEnabled = true;
            }
        }

        private void btnUpload_Click(object sender, RoutedEventArgs e)
        {
            OpenFileDialog dlg = new OpenFileDialog();



            // Set filter for file extension and default file extension 
            dlg.DefaultExt = ".png";
            //dlg.Filter = "JPEG Files (*.jpeg)|*.jpeg|PNG Files (*.png)|*.png|JPG Files (*.jpg)|*.jpg|GIF Files (*.gif)|*.gif";


            // Display OpenFileDialog by calling ShowDialog method 
            Nullable<bool> result = dlg.ShowDialog();


            // Get the selected file name and display in a TextBox 
            if (result == true)
            {
                // Open document 
                string filename = dlg.FileName;
                tempFile = dlg.FileName;
                FileInfo oFileInfo = new FileInfo(filename);

                if (filename != null || filename.Length == 0)
                {
                    Console.WriteLine(oFileInfo.Extension);
                    Console.WriteLine(oFileInfo.Name);
                    Console.WriteLine(oFileInfo.Length);

                    Data.File file = new Data.File();
                    file.setContentSize(oFileInfo.Length);
                    file.setFileName(oFileInfo.Name);
                    file.setContentType("image/" + oFileInfo.Extension.Split('.')[1]);
                    file.setUploadDate(DateTime.Now.Date);

                    Database.Instance.currSchoolclass.addFile(file);
                    createMetaData(file);
                    fillLb();

                }

            }
        }

        public static void HttpUploadFile(string url, string file, string paramName, string contentType)
        {


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


        private void createMetaData(Data.File file)
        {
            string url = AppSettings.ConnectionString + "file/?schoolclassid=" + Database.Instance.currSchoolclass.getId();
            try
            {
                MemoryStream stream1 = new MemoryStream();
                DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(Data.File));
                ser.WriteObject(stream1, file);
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
                    file.setId(obj.getId());
                    HttpUploadFile(AppSettings.ConnectionString + "file/content/" + (int)obj.getId(),
                        tempFile, "file", file.getContentType());
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

        private void btnCancel_Click(object sender, RoutedEventArgs e)
        {
            ControllerNavigator.NavigateTo(ControllerHolder.HomeControl);
        }
    }
}
