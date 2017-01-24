using ClassM8_Client.Data;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Runtime.Serialization.Json;
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
    /// Interaktionslogik für NewClassWindow.xaml
    /// </summary>
    public partial class NewClassWindow : Window
    {
        public NewClassWindow()
        {
            InitializeComponent();
        }

        private void btnNewClassCancle_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void btnNewClass_Click(object sender, RoutedEventArgs e)
        {
            createNewClass();
        }

        private void createNewClass() {
           
            Schoolclass sc = new Schoolclass();
            sc.setName(txtClassname.Text);
            sc.setSchool(txtSchool.Text);
            sc.setRoom(txtRoom.Text);

            sc.setPresident(null);
            sc.setPresidentDeputy(null);

            Database.Instance.currSchoolclass = sc;
            DataReader.Instance.createNewSchoolclass(sc);

        }
    }
}
