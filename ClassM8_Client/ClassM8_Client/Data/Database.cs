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

        internal void mapEmotes()
        {
            Dictionary<String, Emote> mapped = new Dictionary<string, Emote>();
            if (currSchoolclass.getEmotesUnmapped() != null)
            {
                foreach (Emote e in currSchoolclass.getEmotesUnmapped())
                {
                    mapped.Add(e.getShortString(), e);
                }
                currSchoolclass.setEmotes(mapped);
            }
            else {
                currSchoolclass.setEmotes(new Dictionary<string, Emote>());
            }
        }
    }
}
