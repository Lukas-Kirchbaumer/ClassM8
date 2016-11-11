using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace ClassM8_Client.Data
{
    [DataContract]
    public class M8
    {
        private static long serialVersionUID = 7576437934172296816L;
        [DataMember]
        private long id;
        [DataMember]
        private String firstname;
        [DataMember]
        private String lastname;
        [DataMember]
        private String email;
        [DataMember]
        private String password;
        [DataMember]
        private bool hasVoted;
        [DataMember]
        private int votes;

        public M8() { }

        public long getId()
        {
            return id;
        }

        public void setId(long id)
        {
            this.id = id;
        }

        public String getFirstname()
        {
            return firstname;
        }

        public void setFirstname(String firstname)
        {
            this.firstname = firstname;
        }

        public String getLastname()
        {
            return lastname;
        }

        public void setLastname(String lastname)
        {
            this.lastname = lastname;
        }

        public String getEmail()
        {
            return email;
        }

        public void setEmail(String email)
        {
            this.email = email;
        }

        public String getPassword()
        {
            return password;
        }

        public void setPassword(String password)
        {
            this.password = password;
        }

        public bool isHasVoted()
        {
            return hasVoted;
        }

        public void setHasVoted(bool hasVoted)
        {
            this.hasVoted = hasVoted;
        }

        public int getVotes()
        {
            return votes;
        }

        public void setVotes(int votes)
        {
            this.votes = votes;
        }

        public void setNewM8(M8 newm8)
        {
            this.firstname = newm8.firstname;
            this.lastname = newm8.lastname;
            this.email = newm8.email;
            this.password = newm8.password;
            this.hasVoted = newm8.hasVoted;
            this.votes = newm8.votes;
        }




        public int GetHashCode()
        {
            int prime = 31;
            int result = 1;
            result = prime * result + ((email == null) ? 0 : email.GetHashCode());
            result = prime * result + ((firstname == null) ? 0 : firstname.GetHashCode());
            result = prime * result + (hasVoted ? 1231 : 1237);
            result = prime * result + (int)(id ^ (id >> 32));
            result = prime * result + ((lastname == null) ? 0 : lastname.GetHashCode());
            result = prime * result + ((password == null) ? 0 : password.GetHashCode());
            result = prime * result + votes;
            return result;
        }


        public String toString()
        {
            return "M8 [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
                    + ", password=" + password + ", hasVoted=" + hasVoted + ", votes=" + votes + "]";
        }



        public bool equals(Object obj)
        {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (this.GetType() != obj.GetType())
                return false;
            M8 other = (M8)obj;
            if (email == null)
            {
                if (other.email != null)
                    return false;
            }
            else if (!email.Equals(other.email))
                return false;
            if (firstname == null)
            {
                if (other.firstname != null)
                    return false;
            }
            else if (!firstname.Equals(other.firstname))
                return false;
            if (hasVoted != other.hasVoted)
                return false;
            if (id != other.id)
                return false;
            if (lastname == null)
            {
                if (other.lastname != null)
                    return false;
            }
            else if (!lastname.Equals(other.lastname))
                return false;
            if (password == null)
            {
                if (other.password != null)
                    return false;
            }
            else if (!password.Equals(other.password))
                return false;
            if (votes != other.votes)
                return false;
            return true;
        }

    }
}
