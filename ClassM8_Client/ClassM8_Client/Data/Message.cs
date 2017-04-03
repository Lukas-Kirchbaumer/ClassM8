using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace ClassM8_Client.Data
{
    [DataContract]
    class Message
    {

        [DataMember]
        private String content;

        [DataMember]
        private String sender;

        [DataMember]
        public String dateTime;

        public Message() { }

        public Message(String c, String s, String dt)
        {
            this.content = c;
            this.sender = s;
            this.dateTime = dt;
        }

        public String getContent()
        {
            return content;
        }

        public void setContent(String content)
        {
            this.content = content;
        }

        public String getSender()
        {
            return sender;
        }

        public void setSender(String sender)
        {
            this.sender = sender;
        }

        public String getDateTime()
        {
            return dateTime;
        }

        public void setDateTime(String dateTime)
        {
            this.dateTime = dateTime;
        }

        public bool Equals(Message m)
        {
            if (m == null)
            {
                return false;
            }
            return (this.content.Equals(m.content)) && (this.sender.Equals(m.sender)) && (this.dateTime == m.dateTime);
        }

        public override string ToString()
        {
            String time = this.getDateTime().Split('T')[1];
            time = time.Split('.')[0];
            return this.getSender() + "   " + time + "\r\n" + this.getContent(); ;
        }

        internal string getFormattedDate()
        {
            String ret = "";
            ret = this.dateTime.Replace("T", " ");
            ret = ret.Substring(0, ret.Length - 13);
            return ret;
        }
    }
}
