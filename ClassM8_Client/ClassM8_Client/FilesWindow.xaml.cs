using ClassM8_Client.Data;
using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Runtime.Serialization.Json;
using System.Text;
using System.Threading.Tasks;
using System.Web;
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
    /// Interaktionslogik für FilesWindow.xaml
    /// </summary>
    public partial class FilesWindow : Window
    {
        String tempFile = "";

        public FilesWindow()
        {
            InitializeComponent();

            fillLb();
        }

        private void fillLb() {
            List<Data.File> items = Database.Instance.currSchoolclass.getClassFiles();

            Console.WriteLine(Database.Instance.currSchoolclass.getClassFiles());

            btnDownload.IsEnabled = false;
            if (items.Count > 0)
            {
                Console.WriteLine(items.Count);
                lbDownloadableFiles.ItemsSource = null;
                lbDownloadableFiles.ItemsSource = items;
            }
            else {
                lbDownloadableFiles.Items.Add("Keine geteilten Inhalte");
            }
        }

        private void btnDownload_Click(object sender, RoutedEventArgs e)
        {
            Data.File file = lbDownloadableFiles.SelectedItem as Data.File;

            if (file != null) {

                Console.WriteLine("File to download - " + file.getFileName() + "." + file.getContentType());

                Stream stream = null;
                //This controls how many bytes to read at a time and send to the client
                int bytesToRead = 10000;
                // Buffer to read bytes in chunk size specified above
                byte[] buffer = new Byte[bytesToRead];
                // The number of bytes read
                string url = "http://localhost:8080/classm8web/services/file/content/" + file.getId();
                try
                {
                    //Create a WebRequest to get the file
                    HttpWebRequest fileReq = (HttpWebRequest)HttpWebRequest.Create(url);
                    //Create a response for this request
                    HttpWebResponse fileResp = (HttpWebResponse)fileReq.GetResponse();
                    if (fileReq.ContentLength > 0)
                        fileResp.ContentLength = fileReq.ContentLength;
                    //Get the Stream returned from the response
                    stream = fileResp.GetResponseStream();
                    // prepare the response to the client. resp is the client Response
                    var resp = HttpContext.Current.Response;
                    //Indicate the type of data being sent
                    resp.ContentType = "application/octet-stream";
                    //Name the file 
                    resp.AddHeader("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"");
                    resp.AddHeader("Content-Length", fileResp.ContentLength.ToString());

                    int length;
                    do
                    {
                        // Verify that the client is connected.
                        if (resp.IsClientConnected)
                        {
                            // Read data into the buffer.
                            length = stream.Read(buffer, 0, bytesToRead);
                            // and write it out to the response's output stream
                            resp.OutputStream.Write(buffer, 0, length);
                            // Flush the data
                            resp.Flush();
                            //Clear the buffer
                            buffer = new Byte[bytesToRead];
                        }
                        else
                        {
                            // cancel the download if client has disconnected
                            length = -1;
                        }
                    } while (length > 0); //Repeat until no data is read
                    string currDir = Directory.GetCurrentDirectory();
                    string newFile = currDir + "\\" + file.getFileName();
                    System.IO.File.WriteAllBytes(newFile, buffer);
                }
                catch (Exception ex) {
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
            else {

            }

        }

        private void lbDownloadableFiles_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (lbDownloadableFiles.SelectedItem != null && Database.Instance.currSchoolclass.getClassFiles().Count > 0) {
                btnDownload.IsEnabled = true;
            }
        }

        private void btnUpload_Click(object sender, RoutedEventArgs e)
        {
            OpenFileDialog dlg = new OpenFileDialog();



            // Set filter for file extension and default file extension 
            dlg.DefaultExt = ".png";
            dlg.Filter = "JPEG Files (*.jpeg)|*.jpeg|PNG Files (*.png)|*.png|JPG Files (*.jpg)|*.jpg|GIF Files (*.gif)|*.gif";


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

        private void UploadFile(int id, Data.File file)
        {
            Console.WriteLine("1337 Über Haxxor ");
            FileStream objfilestream = new FileStream(tempFile, FileMode.Open, FileAccess.Read);
            int len = (int)objfilestream.Length;
            Byte[] mybytearray = new Byte[len];
            objfilestream.Read(mybytearray, 0, len);
            string url = "http://localhost:8080/ClassM8Web/services/file/content/" + id;
            objfilestream.Close();

            var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
            httpWebRequest.ContentType = "application/json; charset=utf-8";
            httpWebRequest.MediaType = "multipart/data";
            httpWebRequest.Method = "POST";
            
           

            using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
            {
                streamWriter.Write(mybytearray);
                streamWriter.Flush();
            }

            var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
            using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
            {
                var result = streamReader.ReadToEnd();
                Console.WriteLine(result);
            }

        }


        private void createMetaData(Data.File file) {
            string url = "http://localhost:8080/ClassM8Web/services/file/?schoolclassid=" + Database.Instance.currSchoolclass.getId();
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


                    UploadFile((int)obj.getId(), file);
                }
            } catch (WebException ex)
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

    }
}
