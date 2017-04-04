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
        public List<Position> positions;

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

        public M8 getM8ById(int id) {
            M8 m8 = new M8(-1, "", "");
            try
            {
                Console.WriteLine("loking for id: " + id);
                Console.WriteLine("cms " + currSchoolclass.getClassMembers().Count);
                foreach (M8 m in currSchoolclass.getClassMembers())
                {
                    Console.WriteLine("mate: " + m);
                }
                m8 = currSchoolclass.getClassMembers().Find(x => x.getId() == id);
                if (m8 == null && id == Database.Instance.currM8.getId())
                    m8 = Database.Instance.currM8;
            }
            catch (NullReferenceException nre) {
                Console.WriteLine(nre.StackTrace);
            }
            return m8;
        }

        internal void mapEmotes()
        {
            Dictionary<String, Emote> mapped = new Dictionary<string, Emote>();
            Console.WriteLine(currSchoolclass.getEmotesUnmapped().Count);
            if (currSchoolclass.getEmotesUnmapped() != null)
            {
                foreach (Emote e in currSchoolclass.getEmotesUnmapped())
                {
                    Console.WriteLine(e.getShortString());
                    mapped.Add(e.getShortString(), e);
                }
                currSchoolclass.setEmotes(mapped);
            }
            else {
                currSchoolclass.setEmotes(new Dictionary<string, Emote>());
            }
        }

        internal void mapAdditionalEmotes(List<Emote> list)
        {
            foreach(Emote e in list)
            {
                Console.WriteLine("---------------------- " + e.getShortString());
                if (!currSchoolclass.getEmotesUnmapped().Contains(e))
                {
                    currSchoolclass.getEmotesUnmapped().Add(e);
                    currSchoolclass.getEmotes().Add(e.getShortString(), e);
                }
            }
        }
    }
}
