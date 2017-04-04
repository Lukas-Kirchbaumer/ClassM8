using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace ClassM8_Client.Data
{
    [DataContract]
    class PositionResult : Result
    {
        [DataMember]
        public List<Position> content { get; set; }

        public PositionResult() { }


    }
}
