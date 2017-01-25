using ClassM8_Client.Controls;
using ClassM8_Client.Dialogs;
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
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace ClassM8_Client
{
    /// <summary>
    /// Interaction logic for HomeControl.xaml
    /// </summary>
    public partial class HomeControl : UserControl
    {
        public HomeControl()
        {
            InitializeComponent();
            loadChat();
        }



        private void btnEditClass_Click(object sender, RoutedEventArgs e)
        {
            ControllerNavigator.NavigateTo(new ClassSettingsControl());
        }

        private void btnVote_Click(object sender, RoutedEventArgs e)
        {
            VoteDialog vd = new VoteDialog();
            vd.ShowDialog();
        }

        private void btnFiles_Click(object sender, RoutedEventArgs e)
        {

            ControllerNavigator.NavigateTo(new FileShareControl());
        }

        private void btnAddM8_Click(object sender, RoutedEventArgs e)
        {
            AddM8Dialog amd = new AddM8Dialog();
            amd.ShowDialog();
        }

        private void btnChat_Click(object sender, RoutedEventArgs e)
        {

        }

        private void loadChat()
        {

        }
    }
}
