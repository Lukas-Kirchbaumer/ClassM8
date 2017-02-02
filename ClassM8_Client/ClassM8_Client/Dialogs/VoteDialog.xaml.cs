using ClassM8_Client.Data;
using System;
using System.Collections.Generic;
using System.Linq;
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
    /// Interaction logic for VoteDialog.xaml
    /// </summary>
    public partial class VoteDialog : Window
    {
        public VoteDialog()
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
                ControllerHolder.HomeControl.btnVote.Visibility = Visibility.Hidden;
                this.Close();
            }
            catch (Exception ex)
            {
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
