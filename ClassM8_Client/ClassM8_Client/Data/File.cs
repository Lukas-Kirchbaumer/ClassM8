using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace ClassM8_Client.Data
{
    [DataContract]
    public class File
    {
        [DataMember]
        private long id;
        [DataMember]
        private String fileName;
        [DataMember]
        private DateTime uploadDate;
        [DataMember]
        private long contentSize;
        [DataMember]
        private String contentType;


        private Schoolclass referencedSchoolclass;

        public File() { }

        public long getId()
        {
            return id;
        }

        public void setId(long id)
        {
            this.id = id;
        }

        public string getFileName()
        {
            return fileName;
        }

        public void setFileName(string fileName)
        {
            this.fileName = fileName;
        }

        public DateTime getUploadDate()
        {
            return uploadDate;
        }

        public void setUploadDate(DateTime uploadDate)
        {
            this.uploadDate = uploadDate;
        }

        public long getContentSize()
        {
            return contentSize;
        }

        public void setContentSize(long size)
        {
            this.contentSize = size;
        }

        public string getContentType()
        {
            return contentType;
        }

        public void setContentType(string contentType)
        {
            this.contentType = contentType;
        }

        public Schoolclass getReferencedSchoolclass()
        {
            return referencedSchoolclass;
        }

        public void setReferencedSchoolclass(Schoolclass referencedSchoolclass)
        {
            this.referencedSchoolclass = referencedSchoolclass;
        }

        override
        public String ToString()
        {
            return fileName;
        }
    }
}
