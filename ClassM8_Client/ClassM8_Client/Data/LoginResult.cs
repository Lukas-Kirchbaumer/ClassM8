using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.Serialization;


namespace ClassM8_Client.Data
{
    [DataContract]
    class LoginResult : Result
    {
        [DataMember]
        private long id;

       
        public LoginResult()
        {
            // TODO Auto-generated constructor stub
        }

        public long getId()
        {
            return id;
        }

        public void setId(long id)
        {
            this.id = id;
        }

    }
}
