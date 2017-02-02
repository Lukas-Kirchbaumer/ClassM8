using ClassM8_Client.Controls;
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

namespace ClassM8_Client
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {

        public MainWindow()
        {
            InitializeComponent();
            ControllerHolder.TitleTextBox = lblName;
            ControllerHolder.TitleSettings = btnSettings;
            ControllerHolder.TitleLogout = btnLogout;
            ControllerNavigator.Frame = mainFrame;
            init();
        }

        private void btnSettings_Click(object sender, RoutedEventArgs e)
        {
            ControllerNavigator.NavigateTo(new UserSettingsControl());
        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            ControllerHolder.HomeControl.SetFinished();
        }

        private void btnLogout_Click(object sender, RoutedEventArgs e)
        {
            
            ControllerHolder.HomeControl.SetFinished();
            Database.Instance.currM8 = null;
            Database.Instance.currM8 = null;
            Database.Instance.currUserId = -1;
            init();
        }

        private void init() {
            btnSettings.Visibility = Visibility.Hidden;
            btnLogout.Visibility = Visibility.Hidden;
            lblName.Text = "";
            mainFrame.Navigate(new LoginControl());
        }
    }
}
