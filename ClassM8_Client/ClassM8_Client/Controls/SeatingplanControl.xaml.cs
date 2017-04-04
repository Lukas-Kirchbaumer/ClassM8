using ClassM8_Client.Data;
using ClassM8_Client.Dialogs;
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
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace ClassM8_Client.Controls
{
    /// <summary>
    /// Interaktionslogik für SeatingplanControl.xaml
    /// </summary>
    public partial class SeatingplanControl : UserControl
    {
        private bool _isRectDragInProg;
        private List<Position> Positions = new List<Position>();
        private List<M8> M8sWithoutSeat = new List<M8>(Database.Instance.currSchoolclass.getClassMembers());
        private int lastX;
        private int lastY;

        public SeatingplanControl()
        {
            InitializeComponent();
          
        }

        private void btnAddSeat_Click(object sender, RoutedEventArgs e)
        {

            drawDesk(new Position(new M8(-1, "", ""), new Data.Point()), true);
        }

        private void btnLoadSeats_Click(object sender, RoutedEventArgs e)
        {
            canvasSeats.Children.Clear();
            DataReader.Instance.getPositions();
            Positions = Database.Instance.positions;

            foreach (Position p in Positions)
            {
                if (p.coordinate != null)
                {
                    M8sWithoutSeat.Remove(M8sWithoutSeat.Find(x => x.getId() == p.owner.getId()));
                    drawDesk(p, false);
                }
                else {
                    Console.WriteLine("User " + p.owner.getLastname() + " has no seat");
                }
            }
        }

        private void btnSaveSeats_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                Positions.Clear();
                foreach (var child in canvasSeats.Children)
                {
                    Border b = (Border)child;

                    System.Windows.Point relativePoint = b.TransformToAncestor(canvasSeats)
                                  .Transform(new System.Windows.Point(0, 0));

                    //Get the M8 to update  --hahahah (End)Reim.
                    Grid g = b.Child as Grid;
                    TextBlock tb = g.Children[1] as TextBlock;
                    M8 m8 = Database.Instance.getM8ById(Int32.Parse(tb.Text));
                    Console.WriteLine("Save: " + m8);

                    Position d = new Position(m8, new Data.Point((int)relativePoint.X, (int)relativePoint.Y) );
                    Positions.Add(d);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.StackTrace);
            }
        }

        private void tb_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            _isRectDragInProg = true;
            Border b = (Border)((Grid)((TextBlock)sender).Parent).Parent;
            System.Windows.Point borderPos = b.TransformToAncestor(canvasSeats)
                    .Transform(new System.Windows.Point(0, 0));
            lastX = (int)borderPos.X;
            lastY = (int)borderPos.Y;
            b.CaptureMouse();
        }

        private void rect_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            _isRectDragInProg = true;
            var mousePos = e.GetPosition(canvasSeats);
            Border b = (Border)sender;
            System.Windows.Point borderPos = b.TransformToAncestor(canvasSeats)
                    .Transform(new System.Windows.Point(0, 0));
            lastX = (int)borderPos.X;
            lastY = (int)borderPos.Y;
            double left = mousePos.X - (b.ActualWidth / 2);
            double top = mousePos.Y - (b.ActualHeight / 2);
            Canvas.SetLeft(b, left);
            Canvas.SetTop(b, top);
            b.CaptureMouse();
        }

        private void rect_MouseLeftButtonUp(object sender, MouseButtonEventArgs e)
        {
            _isRectDragInProg = false;
            Border b = (Border)sender;
            var mousePos = e.GetPosition(canvasSeats);

            System.Windows.Point canvasPos = canvasSeats.TransformToAncestor(canvasSeats)
                          .Transform(new System.Windows.Point(0, 0));
            System.Windows.Point borderPos = b.TransformToAncestor(canvasSeats)
              .Transform(new System.Windows.Point(0, 0));

            Grid g = b.Child as Grid;
            TextBlock m8Id = g.Children[1] as TextBlock;
            Console.WriteLine("M8_Id: " + m8Id.Text);

            // Nicht außerhalb des Canvas
            if (!(mousePos.X > (canvasPos.X + canvasSeats.ActualWidth)) && !(mousePos.X < canvasPos.X) && !(mousePos.Y > (canvasPos.Y + canvasSeats.ActualHeight)) && !(mousePos.Y < canvasPos.Y))
            {

                List<Border> elements = new List<Border>();
                foreach (Border tempb in canvasSeats.Children)
                {
                    elements.Add(tempb);
                }

                IEnumerable<Rect> coordinates = elements.Select(r => new Rect(Canvas.GetLeft(r), Canvas.GetTop(r), r.ActualWidth - 10, r.ActualHeight - 10));

                Rect rect = new Rect(borderPos.X, borderPos.Y, b.ActualWidth, b.ActualHeight);
                int intersects = 0;
                foreach (Rect re in coordinates)
                {
                    if (rect.IntersectsWith(re))
                        intersects++;
                }

                // Nicht überlappen
                // 1x is immer, weil unter den Children die untersucht werden auch das gerade Verschobene ist ... oder so ähnlich
                if (intersects < 2)
                {                               // Um die linke obere Ecke als Ankerpunkt zu setzten
                    double left = mousePos.X;  // - (b.ActualWidth / 2));
                    left = left - (left % 50);
                    double top = mousePos.Y;  // - (b.ActualHeight / 2));
                    top = top - (top % 50);
                    Canvas.SetLeft(b, left);
                    Canvas.SetTop(b, top);
                    b.ReleaseMouseCapture();
                    int id = int.Parse(m8Id.Text);
                    if (id != -1)
                    {
                        Data.Point p = new Data.Point(left, top);
                        Console.WriteLine("Update Position for " + id + ": " + p.ToString());
                        DataReader.Instance.updatePosition(id, p);
                    }
                    else {
                        Console.WriteLine("No M8 assigned to Seat");
                    }
                }
                else
                {
                    resetPosition(b);
                }
            }
            else
            {
                resetPosition(b);
            }
        }

        private void resetPosition(Border b)
        {

            double left = (lastX - (b.ActualWidth / 2));
            left = left - (left % 50) + 50;
            double top = (lastY - (b.ActualHeight / 2));
            top = top - (top % 50) + 50;
            Canvas.SetLeft(b, left);
            Canvas.SetTop(b, top);
            b.ReleaseMouseCapture();
        }

        private void rect_MouseMove(object sender, MouseEventArgs e)
        {
            if (_isRectDragInProg)
            {
                // get the position of the mouse relative to the Canvas
                var mousePos = e.GetPosition(canvasSeats);

                // center the rect on the mouse
                Border b = (Border)sender;
                double left = mousePos.X - (b.ActualWidth / 2);
                double top = mousePos.Y - (b.ActualHeight / 2);
                Canvas.SetLeft(b, left);
                Canvas.SetTop(b, top);
            }
        }

        private void drawDesk(Position p, Boolean isNew)
        {

            Border border = new Border
            {
                BorderBrush = Brushes.SaddleBrown,
                Background = Brushes.SandyBrown,
                BorderThickness = new Thickness(2),
                CornerRadius = new CornerRadius(5),
                Height = 50,
                Width = 100
            };

            ContextMenu context = new ContextMenu();
            MenuItem item = new MenuItem { Header = "Löschen" };
            item.Click += new RoutedEventHandler(contextMenuItem_Click);
            context.Items.Add(item);
            if (isNew)
            {
                MenuItem item2 = new MenuItem { Header = "Zuweisen" };
                item2.Click += new RoutedEventHandler(contextMenuItem_Click);
                context.Items.Add(item2);
            }
            border.ContextMenu = context;

            border.MouseLeftButtonDown += rect_MouseLeftButtonDown;
            border.MouseLeftButtonUp += rect_MouseLeftButtonUp;
            border.MouseMove += rect_MouseMove;

            Grid g = new Grid();
            RowDefinition rd = new RowDefinition();
            ColumnDefinition cd = new ColumnDefinition();
            g.RowDefinitions.Add(rd);
            g.ColumnDefinitions.Add(cd);

            TextBlock t = new TextBlock
            {
                Width = 80,
                Foreground = Brushes.White,
                Height = 16,
                Text = p.owner.getLastname(),
                VerticalAlignment = VerticalAlignment.Center,
                Background = Brushes.SaddleBrown,
                Focusable = false,
                TextAlignment = TextAlignment.Center
            };

            t.MouseLeftButtonDown += tb_MouseLeftButtonDown;

            g.Children.Add(t);

            TextBlock M8sId = new TextBlock { Visibility = Visibility.Hidden };
            //Todo M8s id einfügen
            M8sId.Text = p.owner.getId().ToString();
            g.Children.Add(M8sId);

            border.Child = g;

            double left = p.coordinate.x;  // - (b.ActualWidth / 2));
            left = left - (left % 50);
            double top = p.coordinate.y;  // - (b.ActualHeight / 2));
            top = top - (top % 50);


            canvasSeats.Children.Add(border);
            Canvas.SetLeft(border, left);
            Canvas.SetTop(border, top);

        }

        public void contextMenuItem_Click(Object sender, RoutedEventArgs e)
        {
            MenuItem mi = sender as MenuItem;
            ContextMenu cm = (ContextMenu)mi.Parent;
            Border b = (Border)cm.PlacementTarget;

            if (mi.Header.Equals("Löschen"))
            {
                Grid g = b.Child as Grid;
                TextBlock tb = g.Children[1] as TextBlock;
                Console.WriteLine("Deleted Id: " + tb.Text);
                int id = int.Parse(tb.Text);

                DataReader.Instance.deletePosition(id);
                Console.WriteLine("m8s: " + Database.Instance.currSchoolclass.getClassMembers().Count);

                if (id != -1) {
                    M8 m8 = Database.Instance.getM8ById(id);
                    Console.WriteLine(m8);
                    M8sWithoutSeat.Add(m8);
                }
                canvasSeats.Children.Remove(b);
            }
            else
            {
                if (mi.Header.Equals("Zuweisen"))
                {
                    SetSeatDialog w = new SetSeatDialog(M8sWithoutSeat);
                    w.ShowDialog();
                    M8 m8 = w.selectedM8;
                    Grid g = b.Child as Grid;
                    TextBlock tb = g.Children[0] as TextBlock;
                    tb.Text = m8.getLastname();

                    tb.Focusable = false;

                    if (m8.getId() != -1)
                    {
                        M8sWithoutSeat.Remove(m8);
                        TextBlock M8sId = g.Children[1] as TextBlock;
                        M8sId.Text = m8.getId().ToString();

                        int id = int.Parse(M8sId.Text);

                        System.Windows.Point borderPos = b.TransformToAncestor(canvasSeats)
                            .Transform(new System.Windows.Point(0, 0));
                        double left = borderPos.X; 
                        left = left - (left % 50);
                        double top = borderPos.Y;
                        top = top - (top % 50);
                        Data.Point p = new Data.Point(left, top);
                        Console.WriteLine("Add Position for " + id + ": " + p.ToString());
                        DataReader.Instance.addNewPosition(id, p);

                        b.ContextMenu = null;
                        ContextMenu context = new ContextMenu();
                        MenuItem item = new MenuItem { Header = "Löschen" };
                        item.Click += new RoutedEventHandler(contextMenuItem_Click);
                        context.Items.Add(item);
                        b.ContextMenu = context;
                    }
                }
            }


        }


        private void btnBack_Click(object sender, RoutedEventArgs e)
        {
            ControllerNavigator.NavigateTo(ControllerHolder.HomeControl);
        }

        private void btnClear_Click(object sender, RoutedEventArgs e)
        {
            canvasSeats.Children.Clear();
        }
    }
}
