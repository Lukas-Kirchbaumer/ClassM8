using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace ClassM8_Client.Data
{
    [DataContract]
    class Position
    {

        [DataMember]
        public M8 owner { get; set; }
        [DataMember]
        public Point coordinate { get; set; }

        public Position() { }

        public Position(M8 m, Point p)
        {
            this.owner = m;
            this.coordinate = p;
        }

    }
}