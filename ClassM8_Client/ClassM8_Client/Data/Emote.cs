using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace ClassM8_Client.Data
{
    [DataContract]
    class Emote
    {
        [DataMember]
        private long id;
        [DataMember]
        private String fileName;
        [DataMember]
        private String shortString;
        [DataMember]
        private long contentSize;
        [DataMember]
        private Schoolclass referencedSchoolclass;

        public long getId()
        {
            return id;
        }

        public void setId(long id)
        {
            this.id = id;
        }

        public String getFileName()
        {
            return fileName;
        }

        public void setFileName(String fileName)
        {
            this.fileName = fileName;
        }

        public String getShortString()
        {
            return shortString;
        }

        public void setShortString(String shortString)
        {
            this.shortString = shortString;
        }

        public long getContentSize()
        {
            return contentSize;
        }

        public void setContentSize(long contentSize)
        {
            this.contentSize = contentSize;
        }

        public Schoolclass getReferencedSchoolclass()
        {
            return referencedSchoolclass;
        }

        public void setReferencedSchoolclass(Schoolclass referencedSchoolclass)
        {
            this.referencedSchoolclass = referencedSchoolclass;
        }
    }
}
