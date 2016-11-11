using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClassM8_Client.Data
{
    class Database
    {
        private static Database instance;

        public M8 currM8;
        public int currUserId;
        public Schoolclass currSchoolclass;

        private Database()
        { }

        public static Database Instance
        {
            get
            {
                if (instance == null)
                    instance = new Database();
                return instance;
            }
        }
    }
}
