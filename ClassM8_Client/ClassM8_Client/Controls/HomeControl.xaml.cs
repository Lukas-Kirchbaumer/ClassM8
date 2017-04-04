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
using System.Windows.Media.Effects;
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
            btnEmote.IsEnabled = false;
            lbChat.ItemsSource = null;
            lbChat.Items.Clear();
            lbChat.ItemsSource = new List<Message>();
            ThreadStart childref = new ThreadStart(poll);
            Console.WriteLine("Creating the Polling Thread");
            Thread chatPoller = ControllerHolder.PollingThread();
            chatPoller = new Thread(childref);
            chatPoller.Start();
            btnEmote.IsEnabled = true;
        }

        private void poll() {
            while (!finished) {
                try
                {
                    DataReader.Instance.checkForNewEmotes();
                    List<Message> newMsgs = new List<Message>();
                    String dt = "";
                    if (msgs.Count != 0)
                    {
                        dt = msgs[msgs.Count - 1].getDateTime();
                    }
                    else {
                        dt = "2011-10-02T18:48:05.123aaaaa";
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
            StackPanel sp = new StackPanel();
            TextBlock t = new TextBlock();
            TextBlock header = new TextBlock();
            string[] subStrings = m.getContent().Split('§');

            foreach (string s in subStrings)
            {
                if (Database.Instance.currSchoolclass.getEmotes().ContainsKey(s) && Database.Instance.currSchoolclass.getEmotes() != null)
                {
                    try
                    {
                        Emote e = Database.Instance.currSchoolclass.getEmotes()[s];
                        Console.WriteLine(e.getFileName());
                        Uri uri = new Uri((System.IO.Directory.GetCurrentDirectory() + "/emotes/") + e.getFileName());
                        Console.WriteLine(uri.AbsolutePath);

                        BitmapImage image = new BitmapImage();
                        image.BeginInit();
                        image.UriSource = uri;
                        image.EndInit();

                        Image emote = new Image();
                        emote.Width = 16;
                        emote.Height = 16;
                        emote.Source = image;

                        t.Inlines.Add(emote);
                    }
                    catch (Exception e)
                    {
                        t.Inlines.Add(s);
                    }
                }
                else {
                    t.Inlines.Add(s);
                }
            }

            String headerString =m.getSender() +
                " " + m.getFormattedDate();

            header.Inlines.Add(new Bold(new Italic(new Run(headerString))));
            header.FontSize = 18;
            header.Margin = new Thickness(0,0,0,10);
            header.Foreground = new SolidColorBrush((Color)ColorConverter.ConvertFromString("#ee7600"));
            
            b.Background = new SolidColorBrush((Color)ColorConverter.ConvertFromString("#7B7A7C"));
            b.Margin = new Thickness(5, 5, 5, 5);
            b.Padding = new Thickness(5, 5, 5, 5);
            b.MinWidth = 400;

            t.FontSize = 16;

            sp.Children.Add(header);
            sp.Children.Add(t);
            b.Child = sp;
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
            if (ed.SelectedItem != "")
            {
                txtMessage.Text = txtMessage.Text + "§" + ed.SelectedItem + "§";
            }
        }

        private void btnSeats_Click(object sender, RoutedEventArgs e)
        {
            ControllerNavigator.NavigateTo(new SeatingplanControl());
        }
    }
}
