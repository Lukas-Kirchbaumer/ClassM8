using ClassM8_Client.Controls;
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
            btnSettings.Visibility = Visibility.Hidden;
            ControllerNavigator.Frame = mainFrame;
            ControllerHolder.TitleTextBox = lblName;
            ControllerHolder.TittleSettings = btnSettings;
            mainFrame.Navigate(new LoginControl());
        }

        private void btnSettings_Click(object sender, RoutedEventArgs e)
        {
            ControllerNavigator.NavigateTo(new UserSettingsControl());
        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            ControllerHolder.HomeControl.SetFinished();
        }
    }
}
