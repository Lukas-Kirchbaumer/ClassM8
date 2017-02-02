using ClassM8_Client.Controls;
using ClassM8_Client.Data;
using ClassM8_Client.Dialogs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
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
using System.Windows.Threading;

namespace ClassM8_Client
{
    /// <summary>
    /// Interaction logic for HomeControl.xaml
    /// </summary>
    public partial class HomeControl : UserControl
    {
        Boolean finished = false;

        public HomeControl()
        {
            InitializeComponent();
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
            DataReader.Instance.sendMessage(txtMessage.Text);
            txtMessage.Text = "";
            List<Message> msgs = new List<Message>();

            msgs = DataReader.Instance.loadChat();

            if (msgs.Count > 0)
            {
                btnVote.Dispatcher.BeginInvoke(DispatcherPriority.Background, new Action(() => lbChat.ItemsSource = msgs));
            }
        }

        public void loadChat() 
        {
            ThreadStart childref = new ThreadStart(poll);
            Console.WriteLine("Creating the Polling Thread");
            Thread chatPoller = ControllerHolder.PollingThread();
            chatPoller = new Thread(childref);
            chatPoller.Start();
        }

        private void poll() {
            while (!finished) {
                try
                {
                    List<Message> msgs = new List<Message>();
                    msgs = DataReader.Instance.loadChat();

                    if (msgs.Count > 0)
                    {
                        btnVote.Dispatcher.BeginInvoke(DispatcherPriority.Background, new Action(() => lbChat.ItemsSource = msgs));
                    }

                }
                catch (Exception ex)
                {
                    Console.WriteLine("Error: poll - " + ex.Message);
                }
                Thread.Sleep(5000);
            }
        }

        public Boolean IsFinished() {
            return finished;
        }

        public void SetFinished() {
            finished = true;
        }

        private void txtMessage_TextChanged(object sender, TextChangedEventArgs e)
        {
            Console.WriteLine(txtMessage.Text);
        }
    }
}
