using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;

namespace ClassM8_Client.Controls
{
    public static class ControllerNavigator
    {
        private static Frame _mainContentFrame;

        public static Frame Frame {
            set { _mainContentFrame = value; }
            get { return _mainContentFrame; }
        }

        public static void NavigateTo(object target)
        {
            Console.WriteLine(_mainContentFrame);
            _mainContentFrame.Navigate(target);
        }
    }
}
