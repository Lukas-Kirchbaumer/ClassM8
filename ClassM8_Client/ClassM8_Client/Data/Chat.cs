using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace ClassM8_Client.Data
{
    [DataContract]
    class Chat
    {
        [DataMember]
        private long id;

        [DataMember]
        private List<Message> messages;

        public Chat() { }

        public long getId()
        {
            return id;
        }

        public void setId(long id)
        {
            this.id = id;
        }

        public List<Message> getMessages()
        {
            return messages;
        }

        public void setMessages(List<Message> messages)
        {
            this.messages = messages;
        }


    }

}
