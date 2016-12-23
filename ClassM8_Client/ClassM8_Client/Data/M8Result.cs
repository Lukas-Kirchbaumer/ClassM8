using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace ClassM8_Client.Data
{
    [DataContract]
    class M8Result : Result
    {
        [DataMember]
        private List<M8> content { get; set; }

        public M8Result() { }

        public List<M8> getM8s()
        {
            return this.content;
        }

    }
}
