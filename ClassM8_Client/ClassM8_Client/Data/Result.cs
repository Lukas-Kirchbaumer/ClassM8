using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace ClassM8_Client.Data
{
    [DataContract]
    abstract class Result
    {
        [DataMember]
        private bool success;

        public bool isSuccess()
        {
            return success;
        }

        public void setSuccess(bool success)
        {
            this.success = success;
        }

    }
}
