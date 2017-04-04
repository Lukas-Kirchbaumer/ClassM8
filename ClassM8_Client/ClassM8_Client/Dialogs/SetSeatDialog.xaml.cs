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

namespace ClassM8_Client.Dialogs
{
    /// <summary>
    /// Interaktionslogik für SetSeatDialog.xaml
    /// </summary>
    public partial class SetSeatDialog : Window
    {
        public M8 selectedM8 { get; set; }
        private List<M8> M8sWithoutSeat = new List<M8>();

        public SetSeatDialog(List<M8> m8s)
        {
            InitializeComponent();
            selectedM8 = new M8(-1, "", "");
            M8sWithoutSeat = m8s;
        }


        private void ComboBox_Loaded(object sender, RoutedEventArgs e)
        {

            List<M8> data = M8sWithoutSeat;

            Console.WriteLine(data.Count);

            cbSelectedM8.ItemsSource = data;

            cbSelectedM8.SelectedIndex = 0;
        }

        private void btnSetSeat_Click(object sender, RoutedEventArgs e)
        {
            selectedM8 = cbSelectedM8.SelectedItem as M8;
            if (selectedM8 == null)
                selectedM8 = new M8(-1, "", "");
            this.Close();
        }

        private void btnCancle_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }
    }
}
