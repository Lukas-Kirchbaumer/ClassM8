using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace ClassM8_Client.Data
{

    //Vorläufiger Placeholder

    [DataContract]
    class Message
    {

        [DataMember]
        private string _message;

        [DataMember]
        private M8 _writer;

        [DataMember]
        private DateTime _timestamp;


        public Message() { }



    }
}
