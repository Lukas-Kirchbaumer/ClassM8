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
        public EmoteDialog()
        {
            InitializeComponent();
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
    }
}
