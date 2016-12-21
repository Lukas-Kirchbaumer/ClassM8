using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Runtime.Serialization.Json;
using System.Text;
using System.Threading.Tasks;

namespace ClassM8_Client.Data
{ 
    class WebCall
    {

        public void authentificateUser(M8 mate)
        {
            try
            {
                string url = "http://localhost:8080/ClassM8Web/services/login";

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
                //Todo Besseres Handling für keine Netzwerkverbindung/Server down überlegen
                //Zurzeit -1 kein valider User / -2 Fehler beim Connectionaufbau 
                Database.Instance.currUserId = -2;
                Console.WriteLine("Login error: " + ex.Message.ToString());
            }
        }

        public void getUserClass()
        {
            try
            {
                string url = "http://localhost:8080/ClassM8Web/services/schoolclass/" + Database.Instance.currUserId;

                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json; charset=utf-8";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "GET";

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();


                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine("getUserClass: " + result);

                    SchoolclassResult obj = Activator.CreateInstance<SchoolclassResult>();
                    MemoryStream ms = new MemoryStream(Encoding.Unicode.GetBytes(result));
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(obj.GetType());
                    obj = (SchoolclassResult)serializer.ReadObject(ms);
                    ms.Close();

                    Database.Instance.currSchoolclass = obj.getSchoolclasses().ElementAt(0);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("GetUserClass " + ex.Message);
                Schoolclass sc = new Schoolclass();
                sc.setId(-1);
                Database.Instance.currSchoolclass = sc;
            }
        }

        public void getCurrUser()
        {
            try
            {
                string url = "http://localhost:8080/ClassM8Web/services/user/" + Database.Instance.currUserId;

                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "GET";

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();

                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    result = result.Split('[')[1];
                    result = result.Split(']')[0];
                    Console.WriteLine("CurrM8 " + result);

                    M8 obj = Activator.CreateInstance<M8>();
                    MemoryStream ms = new MemoryStream(Encoding.Unicode.GetBytes(result));
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(obj.GetType());
                    obj = (M8)serializer.ReadObject(ms);
                    ms.Close();
                    Database.Instance.currM8 = obj;
                }
            }
            catch (Exception ex)
            {
                M8 m8 = new M8();
                m8.setId(-1);
                Database.Instance.currM8 = m8;
            }
        }

        public void createNewUser(M8 mate)
        {
            string url = "http://localhost:8080/ClassM8Web/services/user";

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
                Database.Instance.currUserId = -1;
                Console.WriteLine(ex.Message);
            }
        }

        public void updateClass(Schoolclass sc)
        {
            try
            {
                string url = "http://localhost:8080/ClassM8Web/services/schoolclass/?id=" + Database.Instance.currSchoolclass.getId();

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

                Database.Instance.currSchoolclass = sc;
            } catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        public void deleteClass() {
            try
            {
                string url = "http://localhost:8080/ClassM8Web/services/schoolclass/?id=" + Database.Instance.currSchoolclass.getId();

                var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
                httpWebRequest.ContentType = "application/json; charset=utf-8";
                httpWebRequest.Accept = "application/json";
                httpWebRequest.Method = "DELETE";

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();


                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    var result = streamReader.ReadToEnd();
                    Console.WriteLine("deleteClass: " + result);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        public void deleteUser() {
            string url = "http://localhost:8080/ClassM8Web/services/user/?id=" + Database.Instance.currUserId;

            try
            {
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
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        public void updateUser(M8 mate)
        {
            string url = "http://localhost:8080/ClassM8Web/services/user/?id=" + Database.Instance.currUserId;

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
            Database.Instance.currM8 = mate;
        }
    


    }
}
