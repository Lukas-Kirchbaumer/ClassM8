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
        List<Message> msgs = new List<Message>();
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
            chat();
        }

        public void loadChat()
        {
            lbChat.ItemsSource = null;
            lbChat.Items.Clear();
            lbChat.ItemsSource = new List<Message>();
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
                    List<Message> newMsgs = new List<Message>();
                    Console.WriteLine(msgs.Count);

                    if (msgs.Count != 0)
                    {
                        newMsgs = DataReader.Instance.loadChat(msgs[msgs.Count - 1].getDateTime());
                        Console.WriteLine(msgs[msgs.Count - 1].getDateTime());
                        foreach (Message m in newMsgs) {
                            msgs.Add(m);
                        }
                    }
                    else {
                        msgs = DataReader.Instance.loadChat("2011-10-02 18:48:05.123");
                    }

                    if (msgs.Count > 0)
                    {
                        btnVote.Dispatcher.BeginInvoke(DispatcherPriority.Background, new Action(() => lbChat.ItemsSource = msgs));
                        btnVote.Dispatcher.BeginInvoke(DispatcherPriority.Background, new Action(() => lbChat.Items.Refresh()));
                        lbChat.Dispatcher.BeginInvoke(DispatcherPriority.Background, new Action(() => lbChat.ScrollIntoView(msgs.ElementAt(msgs.Count-1))));
                    }
                    Console.WriteLine(msgs.Count);

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

        public void SetFinished(Boolean b) {
            finished = b;
        }

        private void txtMessage_TextChanged(object sender, TextChangedEventArgs e)
        {
            Console.WriteLine(txtMessage.Text);
        }

        private void txtMessage_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.Key == Key.Enter)
            {
                e.Handled = true;
                chat();
            }
        }

        private void chat() {
            DataReader.Instance.sendMessage(txtMessage.Text);
            txtMessage.Text = "";

            if (msgs.Count > 0)
            {
                btnVote.Dispatcher.BeginInvoke(DispatcherPriority.Background, new Action(() => lbChat.ItemsSource = msgs));
            }
        }

        private void btnEmote_Click(object sender, RoutedEventArgs e)
        {
            EmoteDialog ed = new EmoteDialog();
            ed.ShowDialog();
        }
    }
}
