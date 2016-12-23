using ClassM8_Client.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
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
    /// Interaktionslogik für FilesWindow.xaml
    /// </summary>
    public partial class FilesWindow : Window
    {
        public FilesWindow()
        {
            InitializeComponent();

            fillLb();
        }

        private void lbDownloadableFiles_Selected(object sender, RoutedEventArgs e)
        {
            File file = lbDownloadableFiles.SelectedItem as File;

            string remoteUri = "http://localhost:8080/ClassM8Web/services/file/" + file.getId();
            string fileName =   file.getFileName(), myStringWebResource = null;
            // Create a new WebClient instance.
            WebClient myWebClient = new WebClient();
            // Concatenate the domain with the Web resource filename.
            myStringWebResource = remoteUri + fileName;
            Console.WriteLine("Downloading File \"{0}\" from \"{1}\" .......\n\n", fileName, myStringWebResource);
            // Download the Web resource and save it into the current filesystem folder.
            myWebClient.DownloadFile(myStringWebResource, fileName);



        }

        private void fillLb() {
            List<File> items = Database.Instance.currSchoolclass.getClassFiles();

            lbDownloadableFiles.ItemsSource = items;

        }
    }
}
