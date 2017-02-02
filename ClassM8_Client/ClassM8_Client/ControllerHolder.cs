using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Controls;

namespace ClassM8_Client
{
    public static class ControllerHolder
    {
        private static HomeControl homeControl = new HomeControl();
        private static LoginControl loginControl = new LoginControl();
        private static TextBlock titleTextBox;
        private static Button titleSettings;
        private static Thread pollingThread;

        public static HomeControl HomeControl
        {
            set { homeControl = value; }
            get { return homeControl; }
        }

        public static LoginControl LoginControl
        {
            set { loginControl = value; }
            get { return loginControl; }
        }

        public static TextBlock TitleTextBox
        {
            set { titleTextBox = value; }
            get { return titleTextBox; }
        }

        public static Button TittleSettings
        {
            set { titleSettings = value; }
            get { return titleSettings; }
        }

        public static Thread PollingThread() {
            return pollingThread;
        }
    }
}
