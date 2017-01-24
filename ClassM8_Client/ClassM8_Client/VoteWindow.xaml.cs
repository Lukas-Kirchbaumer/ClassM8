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
                M8 selected = cbVoteCandidate.SelectedItem as M8;
                Console.WriteLine("Chosen one: " + selected);

                DataReader.Instance.vote(selected);
            }
            catch (Exception ex) {
                lblVoteError.Content = "No Connection";
            }
        }

        private void ComboBox_Loaded(object sender, RoutedEventArgs e)
        {

            List<M8> data = Database.Instance.currSchoolclass.getClassMembers();

            Console.WriteLine(data.Count);

            cbVoteCandidate.ItemsSource = data;

            cbVoteCandidate.SelectedIndex = 0;
        }


        private void btnCancle_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }
    }
}
