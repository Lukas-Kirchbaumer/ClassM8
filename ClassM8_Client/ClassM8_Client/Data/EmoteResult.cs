using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace ClassM8_Client.Data
{
    [DataContract]
    class EmoteResult : Result
    {

        [DataMember]
        private List<Emote> emotes;

        public EmoteResult() { }

        public List<Emote> getEmotes()
        {
            return emotes;
        }

        public void setEmotes(List<Emote> ids)
        {
            this.emotes = ids;
        }

    }

}
