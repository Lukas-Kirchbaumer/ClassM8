using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace ClassM8_Client.Data
{
    [DataContract]
    class ChatResult : Result
    {
        [DataMember]
        private Chat schoolclassChat;

        public ChatResult() { }

        public Chat getSchoolclassChat()
        {
            return schoolclassChat;
        }

        public void setSchoolclassChat(Chat schoolclassChat)
        {
            this.schoolclassChat = schoolclassChat;
        }


    }
}
