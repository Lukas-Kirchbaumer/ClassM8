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

        public List<Message> getAllMessages()
        {
            List<Message> msgs = new List<Message>();

            try
            {
                string url = AppSettings.ConnectionString + "messages/" + Database.Instance.currSchoolclass;

                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json; charset=utf-8";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "GET";

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();


                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine("AllMessages: " + result);

                    List<Message> obj = Activator.CreateInstance<List<Message>>();
                    MemoryStream ms = new MemoryStream(Encoding.Unicode.GetBytes(result));
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(obj.GetType());
                    obj = (List<Message>)serializer.ReadObject(ms);
                    ms.Close();

                    Console.WriteLine("m8s: " + obj.Count);

                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: DataReader.getAllMessages - " + ex.Message);
            }

            return msgs;

        }

        public int sendMessage(Message m)
        {
            int ok = 0;

            try
            {
                string url = AppSettings.ConnectionString + "messages";

                MemoryStream stream1 = new MemoryStream();
                DataContractJsonSerializer ser = new DataContractJsonSerializer(typeof(Message));
                ser.WriteObject(stream1, m);
                stream1.Position = 0;
                StreamReader sr = new StreamReader(stream1);
                Console.Write("JSON form of Message object: ");
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

        public void createNewUser(M8 mate)
        {
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
                    Console.WriteLine("Create new Account result: " + result);
                }

            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: DataReader.createNewUser - " + ex.Message);
            }

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

    }
}
