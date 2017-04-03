﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace ClassM8_Client.Data
{   
    [DataContract]
    public class Schoolclass
    {
        [DataMember]
        private long id;
        [DataMember]
        private List<M8> classMembers = new List<M8>();
        [DataMember]
        private List<File> files = new List<File>();
        [DataMember]
        private List<Emote> emotes = new List<Emote>();
        [DataMember]
        private String name;
        [DataMember]
        private String room;
        [DataMember]
        private M8 president;
        [DataMember]
        private M8 presidentDeputy;
        [DataMember]
        private String school;

        private Dictionary<String, Emote> dicEmotes;

        public Schoolclass() { }

        public long getId()
        {
            return id;
        }

        public void setId(long id)
        {
            this.id = id;
        }
        public void setClassFiles(List<File> fs)
        {
            this.files = fs;
        }

        public List<File> getClassFiles()
        {
            return files;
        }

        public void addFile(File f) {
            files.Add(f);
        }

        public List<M8> getClassMembers()
        {
            return classMembers;
        }

        public void setClassMembers(List<M8> classMembers)
        {
            this.classMembers = classMembers;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getRoom()
        {
            return room;
        }

        internal void addEmote(Emote emote)
        {
            if (emotes == null) {
                emotes = new List<Emote>();
            }
            emotes.Add(emote);
            dicEmotes.Add(emote.getShortString(), emote);
        }

        public void setRoom(String room)
        {
            this.room = room;
        }

        public M8 getPresident()
        {
            return president;
        }

        public void setPresident(M8 president)
        {
            this.president = president;
        }

        public M8 getPresidentDeputy()
        {
            return presidentDeputy;
        }

        public void setPresidentDeputy(M8 presidentDeputy)
        {
            this.presidentDeputy = presidentDeputy;
        }

        public String getSchool()
        {
            return school;
        }

        public void setSchool(String school)
        {
            this.school = school;
        }
        public List<Emote> getEmotesUnmapped()
        {
            return emotes;
        }

        public Dictionary<String,Emote> getEmotes()
        {
            return dicEmotes;
        }

        public void setEmotes(Dictionary<String, Emote> e)
        {
            this.dicEmotes = e;
        }

        public void setNewClass(Schoolclass sc)
        {
            this.classMembers = sc.classMembers;
            this.name = sc.name;
            this.room = sc.room;
            this.president = sc.president;
            this.presidentDeputy = sc.presidentDeputy;
            this.school = sc.school;
            this.files = sc.files;
        }

        override public int GetHashCode()
        {
            int prime = 31;
            int result = 1;
            result = prime * result + ((classMembers == null) ? 0 : classMembers.GetHashCode());
            result = prime * result + (int)(id ^ (id >> 32));
            result = prime * result + ((name == null) ? 0 : name.GetHashCode());
            result = prime * result + ((president == null) ? 0 : president.GetHashCode());
            result = prime * result + ((presidentDeputy == null) ? 0 : presidentDeputy.GetHashCode());
            result = prime * result + ((room == null) ? 0 : room.GetHashCode());
            result = prime * result + ((school == null) ? 0 : school.GetHashCode());
            return result;
        }

        override public bool Equals(Object obj)
        {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (GetType() != obj.GetType())
                return false;
            Schoolclass other = (Schoolclass)obj;
            if (classMembers == null)
            {
                if (other.classMembers != null)
                    return false;
            }
            else if (!classMembers.Equals(other.classMembers))
                return false;
            if (id != other.id)
                return false;
            if (name == null)
            {
                if (other.name != null)
                    return false;
            }
            else if (!name.Equals(other.name))
                return false;
            if (president == null)
            {
                if (other.president != null)
                    return false;
            }
            else if (!president.Equals(other.president))
                return false;
            if (presidentDeputy == null)
            {
                if (other.presidentDeputy != null)
                    return false;
            }
            else if (!presidentDeputy.Equals(other.presidentDeputy))
                return false;
            if (room == null)
            {
                if (other.room != null)
                    return false;
            }
            else if (!room.Equals(other.room))
                return false;
            if (school == null)
            {
                if (other.school != null)
                    return false;
            }
            else if (!school.Equals(other.school))
                return false;
            return true;
        }

        internal void setEmotesUnmapped(List<Emote> emotes)
        {
            this.emotes = emotes;
        }
    }
}
