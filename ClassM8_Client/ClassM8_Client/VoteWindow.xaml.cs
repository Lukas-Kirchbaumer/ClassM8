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
    /// Interaktionslogik für VoteWindow.xaml
    /// </summary>
    public partial class VoteWindow : Window
    {
        public VoteWindow()
        {
            InitializeComponent();

        }

        private void btnVote_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                M8 voted = cbVoteCandidate.SelectedItem as M8;
                Console.WriteLine(voted);

                string url = "http://localhost:8080/ClassM8Web/services/election/?voterid=" + Database.Instance.currM8.getId() + "&votedid=" + voted.getId();

                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "PUT";

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine(result);
                }

                Database.Instance.currM8.setHasVoted(true);
            }
            catch (Exception ex) {
                lblVoteError.Content = "No Connection";
            }
        }

        private void ComboBox_Loaded(object sender, RoutedEventArgs e)
        {

            List<M8> data = Database.Instance.currSchoolclass.getClassMembers();
      
            cbVoteCandidate.ItemsSource = data;

            cbVoteCandidate.SelectedIndex = 0;
        }


        private void btnCancle_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }
    }
}
