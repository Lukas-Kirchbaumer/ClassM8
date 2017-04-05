using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ClassM8_Client.Data;
using System.IO;
using System.Runtime.Serialization.Json;
using System.Net;
using System.Runtime.Serialization;

namespace ClassM8_Client
{
    class DataReader
    {

        private static DataReader instance;

        private DataReader()
        { }

        public static DataReader Instance
        {
            get
            {
                if (instance == null)
                    instance = new DataReader();
                return instance;
            }
        }


        public void loginM8(M8 mate)
        {

            string url = AppSettings.ConnectionString + "login";
            try
            {
                MemoryStream stream1 = new MemoryStream();
                DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(M8));
                ser.WriteObject(stream1, mate);
                stream1.Position = 0;
                StreamReader sr = new StreamReader(stream1);
                Console.Write("JSON form of M8 object: ");
                string jsonContent = sr.ReadToEnd();

                Console.WriteLine(jsonContent);

                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json; charset=utf-8";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "POST";

                using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
                {
                    streamWriter.Write(jsonContent);
                    streamWriter.Flush();
                }

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine("Result for M8: " + result);

                    LoginResult obj = Activator.CreateInstance<LoginResult>();
                    MemoryStream ms = new MemoryStream(Encoding.Unicode.GetBytes(result));
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(obj.GetType());
                    obj = (LoginResult)serializer.ReadObject(ms);
                    ms.Close();

                    Database.Instance.currUserId = (int)obj.getId();
                    Console.WriteLine("CurrUserId: " + Database.Instance.currUserId);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: DataReader.loginM8 - " + ex.Message);
                Database.Instance.currUserId = -1;
            }

        }

        internal void checkForNewEmotes()
        {

            string url = AppSettings.ConnectionString + "emote/check/" +
                Database.Instance.currSchoolclass.getId() + "/" + Database.Instance.currSchoolclass.getEmotesUnmapped().Count;
            List<Emote> emotes = new List<Emote>();

            try
            {
                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json; charset=utf-8";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "GET";
                EmoteResult er;
                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine(result);
                    er = Activator.CreateInstance<EmoteResult>();
                    MemoryStream ms = new MemoryStream(Encoding.Unicode.GetBytes(result));
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(er.GetType());
                    er = (EmoteResult)serializer.ReadObject(ms);
                    ms.Close();
                }

                Console.WriteLine(er.ToString());
                if(er != null)
                {
                    if (er.isSuccess())
                    {
                        foreach (Emote e in er.getEmotes()) {
                            getEmoteById(e);
                        }
                        Database.Instance.mapAdditionalEmotes(er.getEmotes());
                    }
                }

            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: DataReader.load all emotes - " + ex.Message);
                Console.WriteLine(ex.StackTrace);
            }
        }

        public void getM8Class()
        {

            string url = AppSettings.ConnectionString + "schoolclass/" + Database.Instance.currUserId;

            try
            {
                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json; charset=utf-8";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "GET";

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();


                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine(url + "  Res: " + result);

                    SchoolclassResult obj = Activator.CreateInstance<SchoolclassResult>();
                    MemoryStream ms = new MemoryStream(Encoding.Unicode.GetBytes(result));
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(obj.GetType(), new DataContractJsonSerializerSettings
                    {
                        DateTimeFormat = new DateTimeFormat("yyyy-MM-dd")
                    });
                    obj = (SchoolclassResult)serializer.ReadObject(ms);
                    ms.Close();

                    if (obj.isSuccess())
                    {
                        Database.Instance.currSchoolclass = obj.getSchoolclasses().ElementAt(0);
                        Console.WriteLine("Files in class: " + Database.Instance.currSchoolclass.getClassFiles().Count);
                    }
                    else
                    {
                        Schoolclass sc = new Schoolclass();
                        sc.setId(-1);
                        Database.Instance.currSchoolclass = sc;
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: DataReader.getM8Class: " + ex.Message);
            }
        }

        public void getM8()
        {
            try
            {
                string url = AppSettings.ConnectionString + "user/" + Database.Instance.currUserId;

                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json; charset=utf-8";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "GET";

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();


                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine("CurrM8 " + result);

                    M8Result obj = Activator.CreateInstance<M8Result>();
                    MemoryStream ms = new MemoryStream(Encoding.Unicode.GetBytes(result));
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(obj.GetType());
                    obj = (M8Result)serializer.ReadObject(ms);
                    ms.Close();

                    Console.WriteLine("m8s: " + obj.getM8s().Count);

                    Database.Instance.currM8 = obj.getM8s().ElementAt(0);

                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: DataReader.getM8 - " + ex.Message);
            }
        }

        public Boolean createNewUser(M8 mate)
        {
            Boolean success = false;
            try
            {
                string url = AppSettings.ConnectionString + "user";

                MemoryStream stream1 = new MemoryStream();
                DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(M8));
                ser.WriteObject(stream1, mate);
                stream1.Position = 0;
                StreamReader sr = new StreamReader(stream1);
                Console.Write("JSON form of M8 object: ");
                string jsonContent = sr.ReadToEnd();

                Console.WriteLine(jsonContent);

                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "POST";

                using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
                {
                    streamWriter.Write(jsonContent);
                    streamWriter.Flush();
                }

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    M8Result obj = Activator.CreateInstance<M8Result>();
                    MemoryStream ms = new MemoryStream(Encoding.Unicode.GetBytes(result));
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(obj.GetType());
                    obj = (M8Result)serializer.ReadObject(ms);
                    success = obj.isSuccess();
                    
                }


            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: DataReader.createNewUser - " + ex.Message);
                success = false;
            }

            return success;
        }

        public void deleteUser()
        {

            try
            {
                string url = AppSettings.ConnectionString + "user/?id=" + Database.Instance.currUserId;

                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json; charset=utf-8";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "DELETE";

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();


                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine("Res: " + result);

                }
                Database.Instance.currM8 = null;
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: DataReader.deleteUser - " + ex.Message);
            }

        }

        public void updateUser(M8 mate)
        {
            try
            {
                string url = AppSettings.ConnectionString + "user/?id=" + Database.Instance.currUserId;

                MemoryStream stream1 = new MemoryStream();
                DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(M8));
                ser.WriteObject(stream1, mate);
                stream1.Position = 0;
                StreamReader sr = new StreamReader(stream1);
                Console.Write("JSON form of M8 object: ");
                string jsonContent = sr.ReadToEnd();

                Console.WriteLine(jsonContent);

                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "PUT";

                using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
                {

                    streamWriter.Write(jsonContent);
                    streamWriter.Flush();
                }


                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine(result);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: DataReader.updateUser - " + ex.Message);
            }


        }

        public void updateSchoolclass(Schoolclass sc)
        {

            try
            {
                string url = AppSettings.ConnectionString + "schoolclass/?id=" + Database.Instance.currSchoolclass.getId();

                MemoryStream stream1 = new MemoryStream();
                DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(Schoolclass));
                ser.WriteObject(stream1, sc);
                stream1.Position = 0;
                StreamReader sr = new StreamReader(stream1);
                Console.Write("JSON form of Schoolclass object: ");
                string jsonContent = sr.ReadToEnd();

                Console.WriteLine(jsonContent);

                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "PUT";

                using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
                {
                    streamWriter.Write(jsonContent);
                    streamWriter.Flush();
                }

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine(result);
                }

            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: DataReader.updateSchoolclass - " + ex.Message);
            }
        }

        public void deleteSchoolclass()
        {
            try
            {
                string url = AppSettings.ConnectionString + "schoolclass/?id=" + Database.Instance.currSchoolclass.getId();

                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json; charset=utf-8";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "DELETE";

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();

                Database.Instance.currSchoolclass = null;

                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine("Res: " + result);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: DataReader.deleteSchoolclass - " + ex.Message);

            }

        }

        public void vote(M8 selected)
        {
            try
            {
                string url = AppSettings.ConnectionString + "election/?voterid=" + Database.Instance.currM8.getId() + "&votedid=" + selected.getId();

                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "PUT";

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine(result);
                }

                Database.Instance.currM8.setHasVoted(true);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: DataReader.vote - " + ex.Message);

            }
        }

        public void createNewSchoolclass(Schoolclass sc)
        {
            try
            {

                string url = AppSettings.ConnectionString + "schoolclass/?m8id=" + Database.Instance.currM8.getId();
                MemoryStream stream1 = new MemoryStream();
                DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(Schoolclass));
                ser.WriteObject(stream1, sc);
                stream1.Position = 0;
                StreamReader sr = new StreamReader(stream1);
                Console.Write("JSON form of M8 object: ");
                string jsonContent = sr.ReadToEnd();

                Console.WriteLine(jsonContent);

                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "POST";

                using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
                {
                    streamWriter.Write(jsonContent);
                    streamWriter.Flush();
                }


                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine(result);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: DataReader.createNewClass - " + ex.Message);
            }
        }

        public int sendMessage(String m)
        {
            int ok = 0;

            try
            {
                string url = AppSettings.ConnectionString + "schoolclass/chat?scid=" + Database.Instance.currSchoolclass.getId() + "&m8id=" + Database.Instance.currM8.getId();

                MemoryStream stream1 = new MemoryStream();
                DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(String));

                ser.WriteObject(stream1, m);
                stream1.Position = 0;
                StreamReader sr = new StreamReader(stream1);
                Console.Write("JSON form of Message: ");
                string jsonContent = sr.ReadToEnd();

                Console.WriteLine(jsonContent);
                
                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "POST";

                using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
                {
                    streamWriter.Write(m);
                    streamWriter.Flush();
                }

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine("sendMessage result: " + result);
                }
                ok = 1;
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: DataReader.sendMessage - " + ex.Message);
                ok = 0;
            }

            return ok;
        }

        public List<Message> loadChat(String date) {
            List<Message> msgs = new List<Message>();

            date= date.Replace("T", " ");
            date = date.Substring(0, date.Length - 5);
            if (Database.Instance.currSchoolclass != null && Database.Instance.currSchoolclass.getId() != -1) {
                try
                {
                    string url = AppSettings.ConnectionString + "schoolclass/chat?scid=" + Database.Instance.currSchoolclass.getId()
                        + "&limit=" + date;

                    Console.WriteLine(url);

                    var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                    httpWebRequest.ContentType = "application/json; charset=utf-8";
                    httpWebRequest.Accept = "application/json";
                    httpWebRequest.Method = "GET";

                    var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();


                    using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                    {
                        var result = streamReader.ReadToEnd();
                        Console.WriteLine("AllMessages: " + result);

                        ChatResult  obj = Activator.CreateInstance<ChatResult>();
                        MemoryStream ms = new MemoryStream(Encoding.Unicode.GetBytes(result));
                        DataContractJsonSerializer serializer = new DataContractJsonSerializer(obj.GetType());
                         
                        obj = (ChatResult)serializer.ReadObject(ms);
                        ms.Close();

                        Console.WriteLine(obj.isSuccess());

                        if (obj.isSuccess()) {
                            msgs = obj.getSchoolclassChat().getMessages();
                            Console.WriteLine("Messages count : " + obj.getSchoolclassChat().getMessages().Count);
                        }
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Error: DataReader.loadChat - " + ex.Message);
                }
            }
            Console.WriteLine("in method " + msgs.Count);
            return msgs;
        }

        public void getPresidents() {
            string url = AppSettings.ConnectionString + "election?scid=" + Database.Instance.currSchoolclass.getId();

            var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
            httpWebRequest.ContentType = "application/json; charset=utf-8";
            httpWebRequest.Accept = "application/json";
            httpWebRequest.Method = "GET";

            var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
        }

        public void getAllEmotesForClass()
        {
            List<Emote> emotes = getAllEmoteIdsForClass();
            Database.Instance.currSchoolclass.setEmotesUnmapped(emotes);
            if (emotes != null) { 
                foreach (Emote e in emotes) {
                    getEmoteById(e);
                }
            }

        }

        private void getEmoteById(Emote e)
        {
            if (e != null)
            {
                Console.WriteLine(e.getFileName() + " jerome");
                Stream stream = null;
                int bytesToRead = 10000;
                byte[] buffer = new Byte[bytesToRead];
                string url = AppSettings.ConnectionString + "emote/content/" + e.getId();
                Console.WriteLine(url);
                try
                {
                    HttpWebRequest fileReq = (HttpWebRequest)HttpWebRequest.Create(url);
                    HttpWebResponse fileResp = (HttpWebResponse)fileReq.GetResponse();
                    if (fileReq.ContentLength > 0)
                        fileResp.ContentLength = fileReq.ContentLength;
                    stream = fileResp.GetResponseStream();
                    Console.WriteLine(stream);
                    using (var fileStream = new FileStream(".\\emotes\\" + e.getFileName(), FileMode.Create, FileAccess.Write))
                    {
                        Console.WriteLine("fgt");
                        stream.CopyTo(fileStream);
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine(ex.Message);
                    Console.WriteLine(ex.StackTrace);
                }
                finally
                {
                    if (stream != null)
                    {
                        //Close the input stream
                        stream.Close();
                    }
                }
            }
            
        }

        private List<Emote> getAllEmoteIdsForClass() {
            string url = AppSettings.ConnectionString + "emote/all/" +
               Database.Instance.currSchoolclass.getId();
            List<Emote> emotes = new List<Emote>();
            Console.WriteLine(url + "       -----------------------");

            try
            {
                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json; charset=utf-8";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "GET";

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine(result);
                    EmoteResult er = Activator.CreateInstance<EmoteResult>();
                    MemoryStream ms = new MemoryStream(Encoding.Unicode.GetBytes(result));
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(er.GetType());
                    er = (EmoteResult)serializer.ReadObject(ms);
                    ms.Close();
                    emotes = er.getEmotes();
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: DataReader.load all emotes - " + ex.Message);
                Console.WriteLine(ex.StackTrace);
                Database.Instance.currUserId = -1;
            }
            return emotes;
        }

        public void getPositions()
        {

            string url = AppSettings.ConnectionString + "position/?scid=" + Database.Instance.currSchoolclass.getId();

            try
            {
                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json; charset=utf-8";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "GET";

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();


                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine(url + "  Res: " + result);

                    PositionResult obj = Activator.CreateInstance<PositionResult>();
                    MemoryStream ms = new MemoryStream(Encoding.Unicode.GetBytes(result));
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(obj.GetType());
                    obj = (PositionResult)serializer.ReadObject(ms);
                    ms.Close();

                    if (obj.isSuccess())
                    {
                        Console.WriteLine("Positions_Count: " + obj.content.Count);
                        Database.Instance.positions = obj.content;
                        
                    }
                    else
                    {
                        Database.Instance.positions = new List<Position>();
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: DataReader.getM8Class: " + ex.Message);
            }
        }

        public void updatePosition(int id, Point p)
        {
            try
            {
                string url = AppSettings.ConnectionString + "position/?mid=" + id;

                MemoryStream stream1 = new MemoryStream();
                DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(Point));
                ser.WriteObject(stream1, p);
                stream1.Position = 0;
                StreamReader sr = new StreamReader(stream1);
                Console.Write("JSON form of Point object: ");
                string jsonContent = sr.ReadToEnd();

                Console.WriteLine(jsonContent);

                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "PUT";

                using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
                {
                    streamWriter.Write(jsonContent);
                    streamWriter.Flush();
                }


                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine("UpdatePosition result: " + result);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: DataReader.updatePosition - " + ex.Message);
            }
        }

        public void deletePosition(int id)
        {
            try
            {
                string url = AppSettings.ConnectionString + "position/?mid=" + id;

                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json; charset=utf-8";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "DELETE";

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();


                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine("Res for deletePosition: " + result);
                }

                Database.Instance.currM8 = null;
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: DataReader.deletePosition - " + ex.Message);
            }

        }


        public void addNewPosition(int id, Point p)
        {
            try
            {
                string url = AppSettings.ConnectionString + "position/?mid=" + id;

                MemoryStream stream1 = new MemoryStream();
                DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(Point));
                ser.WriteObject(stream1, p);
                stream1.Position = 0;
                StreamReader sr = new StreamReader(stream1);
                Console.Write("JSON form of Point object: ");
                string jsonContent = sr.ReadToEnd();

                Console.WriteLine(jsonContent);

                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "POST";

                using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
                {
                    streamWriter.Write(jsonContent);
                    streamWriter.Flush();
                }

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine("Add new Position result: " + result);
                }

            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: DataReader.createNewUser - " + ex.Message);
            }

        }



    }
}
