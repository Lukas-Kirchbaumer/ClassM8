using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace ClassM8_Client.Data
{
    [DataContract]
    public class Point
    {
        [DataMember]
        public double x { get; set; }
        [DataMember]
        public double y { get; set; }

        public Point() { }

        public Point(double x, double y)
        {
            this.x = x;
            this.y = y;
        }

        override
        public String ToString()
        {
            return x + " x " + y;
        }


    }
}
