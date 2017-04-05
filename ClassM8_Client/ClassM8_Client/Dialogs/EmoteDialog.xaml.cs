using ClassM8_Client.Data;
using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.IO;
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
    /// Interaction logic for EmoteDialog.xaml
    /// </summary>
    public partial class EmoteDialog : Window
    {
        public string SelectedItem
        {
            get;
            set;
        }

        public EmoteDialog()
        {
            SelectedItem = "";
            DataReader.Instance.checkForNewEmotes();
            InitializeComponent();
            foreach (String s in Database.Instance.currSchoolclass.getEmotes().Keys){
                TextBlock t = new TextBlock();
                Emote e = Database.Instance.currSchoolclass.getEmotes()[s];

                try
                {
                    Uri uri = new Uri((System.IO.Directory.GetCurrentDirectory() + "/emotes/") + e.getFileName());
                    BitmapImage image = new BitmapImage();
                    image.BeginInit();
                    image.UriSource = uri;
                    image.EndInit();

                    Image emote = new Image();
                    emote.Width = 16;
                    emote.Height = 16;
                    emote.Source = image;

                    t.Inlines.Add(emote);
                    t.Inlines.Add(" " + e.getId() + " ");
                    t.Inlines.Add(e.getShortString());
                    listBox.Items.Add(t);
                }
                catch (Exception ex) {
                    t.Inlines.Add(" " + e.getId() + " ");
                    t.Inlines.Add(e.getShortString());
                    listBox.Items.Add(t);
                }

            }
        }

        private void btnAdd_Click(object sender, RoutedEventArgs e)
        {
            AddEmoteDialog aed = new AddEmoteDialog();
            aed.ShowDialog();
            this.Close();
        }

        private void btnCancel_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void listBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            Inline il = ((TextBlock)listBox.SelectedItem).Inlines.ElementAt(2);
            SelectedItem = ((Run)il).Text;
            this.Close();
        }
    }
}
