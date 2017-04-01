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
        List<Border> borders = new List<Border>();
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
                    String dt = "";
                    if (msgs.Count != 0)
                    {
                        dt = msgs[msgs.Count - 1].getDateTime();
                    }
                    else {
                        dt = "2011-10-02 18:48:05.123";
                    }
                    newMsgs = DataReader.Instance.loadChat(dt);

                    foreach (Message m in newMsgs)
                    {
                        msgs.Add(m);
                        this.Dispatcher.Invoke(DispatcherPriority.Background, new Action(() => 
                            borders.Add(generateBorderForMessage(m))));
                        
                    }


                    if (msgs.Count > 0)
                    {
                        this.Dispatcher.BeginInvoke(DispatcherPriority.Background, new Action(() => lbChat.ItemsSource = borders));
                        this.Dispatcher.BeginInvoke(DispatcherPriority.Background, new Action(() => lbChat.Items.Refresh()));
                        this.Dispatcher.BeginInvoke(DispatcherPriority.Background, new Action(() => lbChat.ScrollIntoView(borders.ElementAt(borders.Count-1))));
                    }
                    Console.WriteLine(msgs.Count);

                }
                catch (Exception ex)
                {
                    
                    Console.WriteLine("Error: poll - " + ex.Message + "\n" + ex.StackTrace);
                }
                Thread.Sleep(5000);
            }
        }

        private Border generateBorderForMessage(Message m)
        {
            Border b = new Border();
            TextBlock t = new TextBlock();
            string[] subStrings = m.getContent().Split('§');
            foreach (string s in subStrings)
            {
                if (Database.Instance.currSchoolclass.getEmotes().ContainsKey(s))
                {
                    Emote e = Database.Instance.currSchoolclass.getEmotes()[s];
                    Console.WriteLine(e.getFileName());

                    BitmapImage image = new BitmapImage();
                    image.BeginInit();
                    image.UriSource = new Uri("E:\\Bilder\\Emotes\\awe.png");
                    image.EndInit();

                    Image emote = new Image();
                    emote.Width = 16;
                    emote.Height = 16;
                    emote.Source = image;


                    t.Inlines.Add(emote);
                }
                else {
                    /*
                    BitmapImage image = new BitmapImage();
                    image.BeginInit();
                    image.UriSource = new Uri("E:\\Bilder\\Emotes\\awe.png");
                    image.EndInit();

                    Image emote = new Image();
                    emote.Width = 16;
                    emote.Height = 16;
                    emote.Source = image;
                    */

                    t.Inlines.Add(s);
                    //t.Inlines.Add(emote);
                }
            }

            b.Child = t;
            return b;
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
        }

        private void btnEmote_Click(object sender, RoutedEventArgs e)
        {
            EmoteDialog ed = new EmoteDialog();
            ed.ShowDialog();
        }
    }
}
