using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace ClassM8_Client.Data
{
    [DataContract]
    class SchoolclassResult : Result
    {
        [DataMember]
        private List<Schoolclass> schoolclasses { get; set; }

        public SchoolclassResult() { }

        public List<Schoolclass> getSchoolclasses() {
            return this.schoolclasses;
        }
    }
}
