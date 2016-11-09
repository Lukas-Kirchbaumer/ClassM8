package edu.classm8web.dao;

import java.util.HashMap;
import java.util.Vector;

import edu.classm8web.dto.M8;
import edu.classm8web.dto.School;
import edu.classm8web.dto.Schoolclass;
import edu.classm8web.exception.DatabaseException;
import edu.classm8web.services.SchoolService;
import edu.classm8web.services.SchoolclassService;

import java.sql.*;

/**
 * Created by laubi on 10/22/2016.
 */
public class Database {

    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@212.152.179.117:1521:ora11g";

    //  Database credentials
    private static final String USER = "d5b09";
    private static final String PASS = "d5b";

    private static Database ourInstance = new Database();

    public static Database getInstance() {
        return ourInstance;
    }

    private Database() {

    }

    private Connection OpenConnection()throws DatabaseException{
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.setAutoCommit(false);
        }catch (SQLException e){
            throw new DatabaseException(e.getMessage());
        }catch (ClassNotFoundException e){
            throw new DatabaseException(e.getMessage());
        }
        return conn;
    }

    private void isConnectionClosed(ResultSet resultSet, Connection connection)throws DatabaseException{
        try {
            if(resultSet!=null)
            {if (!resultSet.isClosed()) {
                resultSet.close();
            }}
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private void isConnectionClosed(Connection conn) throws DatabaseException {
        try {
        if (!conn.isClosed()) {
            conn.close();
        }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

   // M8
    public M8 getStudentById(int id) throws DatabaseException {
        Connection conn = this.OpenConnection();
        ResultSet rs = null;
        PreparedStatement selectStudent = null;
        M8 s = null;
        try {
            String updateString =
                    "select * from M8 where Id = ?";
            selectStudent = conn.prepareStatement(updateString);
            selectStudent.setInt(1,id);
            rs = selectStudent.executeQuery();

            while (rs.next()) {
            	int ID = rs.getInt("ID");
            	String firstname= rs.getString("VORNAME");
            	String lastname= rs.getString("NACHNAME");
            	String email= rs.getString("EMAIL");
            	String passwort= rs.getString("PASSWORD");
            	boolean hasVoted= rs.getBoolean("HASVOTED");
            	int votes= rs.getInt("VOTES");
               
            	s = new M8(ID, firstname, lastname, email, passwort, hasVoted, votes);
            }

            rs.close();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(rs, conn);
        }
        return s;
    }

    public String getStudentBySecondName(String name) throws DatabaseException{
        Connection conn = this.OpenConnection();
        ResultSet rs = null;
        PreparedStatement selectStudent = null;
        try {
            String updateString =
                    "SELECT * FROM M8 WHERE NACHNAME = ?";
            selectStudent = conn.prepareStatement(updateString);
            selectStudent.setString(1,name);
            rs = selectStudent.executeQuery();

            while (rs.next()) {
                //   int id  = rs.getInt("id");
            }
            rs.close();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(rs, conn);
        }
        return null;
    }
    
    public Schoolclass getSchoolclassOfStudentbyStudentId(int id) throws DatabaseException{
    	Connection conn = this.OpenConnection();
        ResultSet rs = null;
        PreparedStatement selectStudent = null;
        Schoolclass c = new Schoolclass();
        try {
            String updateString =
                    "select SCHOOLCLASS.ID,\n" +
                            "SCHOOLCLASS.SCHOOLID, \n" +
                            "SCHOOLCLASS.NAME,\n" +
                            "SCHOOLCLASS.PRESIDENT , \n" +
                            "SCHOOLCLASS.PRESIDENT_DEPUTY, \n" +
                            "SCHOOLCLASS.ROOM \n" +
                            "from M8 inner join \n" +
                            "SCHOOLCLASS on SCHOOLCLASS.ID = M8.SCHOOLCLASS where M8.ID = ?";

            selectStudent = conn.prepareStatement(updateString);
            selectStudent.setInt(1, id);

            rs = selectStudent.executeQuery();

            Integer idPresident = null;
            Integer idPresidentDeputy = null;
            Integer idSchool = null;
            int ids = 0;
            while (rs.next()) {
                int ID = rs.getInt("ID");
                ids = ID;
                String name = rs.getString("NAME");
                String room = rs.getString("ROOM");
                idPresident = rs.getInt("PRESIDENT");
                idPresidentDeputy = rs.getInt("PRESIDENT_DEPUTY");
                idSchool = rs.getInt("SCHOOLID");
                c.setId(ID);
                c.setName(name);
                c.setRoom(room);

            }

            rs.close();
            selectStudent.close();
            conn.close();

            c.setClassMembers(SchoolclassService.getInstance().getMades(ids));

            if (idPresident.intValue() != 0) {
                c.setPresident(c.getClassMembers().get((long)idPresident.intValue()));
            }
            if (idPresidentDeputy != 0) {
                System.out.println("president-deputy");
                c.setPresidentDeputy(c.getClassMembers().get((long)idPresidentDeputy.intValue()));
            }
            if (idSchool != 0) {
                c.setSchool(SchoolService.getInstance().getSchoolByID(idSchool));
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(rs, conn);
        }
        return c;
    }

    public String getStudentByFirstName(String name) throws DatabaseException{
        Connection conn = this.OpenConnection();
        ResultSet rs = null;
        PreparedStatement selectStudent = null;
        try {
            String updateString =
                    "SELECT * FROM M8 WHERE Vorname = ?";
            selectStudent = conn.prepareStatement(updateString);
            selectStudent.setString(1,name);
            rs = selectStudent.executeQuery();

            while (rs.next()) {
                //   int id  = rs.getInt("id");
            }
            rs.close();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(rs, conn);
        }
        return null;
    }

    public String getStudentByFirstAndSecondName(String firstName, String secondName)throws DatabaseException{
        Connection conn = this.OpenConnection();
        ResultSet rs = null;
        PreparedStatement selectStudent = null;
        try {
            String updateString =
                    "SELECT * FROM M8 WHERE VORNAME = ? AND Nachname = ?";
            selectStudent = conn.prepareStatement(updateString);
            selectStudent.setString(1,firstName);
            selectStudent.setString(2,secondName);
            rs = selectStudent.executeQuery();

            while (rs.next()) {
                //   int id  = rs.getInt("id");
            }
            rs.close();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(rs, conn);
        }
        return null;
    }

    public String getStudentByEMail(String eMail)throws DatabaseException{
        Connection conn = this.OpenConnection();
        ResultSet rs = null;
        PreparedStatement selectStudent = null;
        try {
            String updateString =
                    "SELECT * FROM M8 WHERE E-EMAIL = ?";
            selectStudent = conn.prepareStatement(updateString);
            selectStudent.setString(1,eMail);
            rs = selectStudent.executeQuery();

            while (rs.next()) {
                //   int id  = rs.getInt("id");
            }
            rs.close();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(rs, conn);
        }
        return null;
    }

    public M8 getStudentbyPasswordAndEMail(String eMail, String password) throws DatabaseException{
        Connection conn = this.OpenConnection();
        ResultSet rs = null;
        PreparedStatement selectStudent = null;
        M8 student = null;
        try {
            String updateString =
                    "SELECT * FROM M8 WHERE EMail =? AND Password = ?";
            selectStudent = conn.prepareStatement(updateString);
            selectStudent.setString(1,eMail);
            selectStudent.setString(2,password);
            rs = selectStudent.executeQuery();

            while (rs.next()) {
              	int ID = rs.getInt("ID");
            	String firstname= rs.getString("VORNAME");
            	String lastname= rs.getString("NACHNAME");
            	String email= rs.getString("EMAIL");
            	String passwort= rs.getString("PASSWORD");
            	boolean hasVoted= rs.getBoolean("HASVOTED");
            	int votes= rs.getInt("VOTES");

               student = new M8(ID, firstname, lastname, email, passwort, hasVoted,votes);
            }
            rs.close();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(rs, conn);
        }
        return student;
    }

    public boolean addStudent(M8 student)throws DatabaseException{
        Connection conn = this.OpenConnection();
        boolean rs = false;
        PreparedStatement selectStudent = null;
        try {
            String updateString =
                    "INSERT INTO M8 VALUES(?, null,?,?,?,?, ?, ?)";
            selectStudent = conn.prepareStatement(updateString);

            selectStudent.setInt(1, (int)student.getId());
            selectStudent.setString(2,student.getFirstname());
            selectStudent.setString(3,student.getLastname());
            selectStudent.setString(4,student.getEmail());
            selectStudent.setString(5,student.getPassword());

            if(student.isHasVoted())
            	selectStudent.setInt(6,1);
            else
            	selectStudent.setInt(6,0);		
            selectStudent.setInt(7,student.getVotes());

            rs = selectStudent.execute();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(conn);
        }
        return rs;
    }

    public boolean changeStudentPassword(String newPassword, int studentId)throws DatabaseException{
        Connection conn = this.OpenConnection();
        boolean rs = false;
        PreparedStatement selectStudent = null;
        try {
            String updateString =
                    "UPDATE M8 SET PASSWORT=? where ID = ?";
            selectStudent = conn.prepareStatement(updateString);
            selectStudent.setString(1,newPassword);
            selectStudent.setInt(2,studentId);
            rs = selectStudent.execute();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(conn);
        }
        return rs;
    }

    public boolean changeStudentEMail(int studentId,String eMail)throws DatabaseException{
        Connection conn = this.OpenConnection();
        boolean rs = false;
        PreparedStatement selectStudent = null;
        try {
            String updateString =
                    "UPDATE M8 SET EMAIL=? where ID = ?";
            selectStudent = conn.prepareStatement(updateString);
            selectStudent.setString(1,eMail);
            selectStudent.setInt(2,studentId);
            rs = selectStudent.execute();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(conn);
        }
        return rs;
    }

    public boolean changeStudentFirstName(int studentId,String firstName)throws DatabaseException{
        Connection conn = this.OpenConnection();
        boolean rs = false;
        PreparedStatement selectStudent = null;
        try {
            String updateString =
                    "UPDATE M8 SET VORNAME=? where ID = ?";
            selectStudent = conn.prepareStatement(updateString);
            selectStudent.setString(1,firstName);
            selectStudent.setInt(2,studentId);
            rs = selectStudent.execute();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(conn);
        }
        return rs;
    }

    public boolean changeStudentSecondName(int studentId,String lastName)throws DatabaseException{
        Connection conn = this.OpenConnection();
        boolean rs = false;
        PreparedStatement selectStudent = null;
        try {
            String updateString =
                    "UPDATE M8 SET NACHNAME=? where ID = ?";
            selectStudent = conn.prepareStatement(updateString);
            selectStudent.setString(1,lastName);
            selectStudent.setInt(2, studentId);
            rs = selectStudent.execute();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(conn);
        }
        return rs;
    }

    public boolean addStudentToClass(int StudentId,int ClassId)throws DatabaseException{
        Connection conn = this.OpenConnection();
        boolean rs = false;
        PreparedStatement selectStudent = null;
        try {
            String updateString =
                    "UPDATE M8 SET SCHOOLCLASS = ? where ID = ?";
            selectStudent = conn.prepareStatement(updateString);
            selectStudent.setInt(1,ClassId);
            selectStudent.setInt(2,StudentId);

            rs = selectStudent.execute();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(conn);
        }
        return rs;
    }

    //SchoolClass
    
    public boolean addClass(Schoolclass sclass)throws DatabaseException{
        Connection conn = this.OpenConnection();
        PreparedStatement selectStudent = null;
        boolean retVal;
        try {
            String updateString =
                    "INSERT INTO SCHOOLCLASS VALUES(?,?,?,?,?,?)";
            selectStudent = conn.prepareStatement(updateString);

            selectStudent.setInt(1,(int)sclass.getId());
            if(sclass.getSchool() != null)
                selectStudent.setInt(2,(int)sclass.getSchool().getId());
            else
                selectStudent.setString(2,null);

            if(sclass.getPresident() != null)
                selectStudent.setInt(3,(int)sclass.getPresident().getId());
            else
                selectStudent.setString(3,null);

            if(sclass.getPresidentDeputy() != null)
                selectStudent.setInt(4,(int)sclass.getPresidentDeputy().getId());
            else
                selectStudent.setString(4,null);

            selectStudent.setString(5,sclass.getName());
            selectStudent.setString(6,sclass.getRoom());

            retVal = selectStudent.execute();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(conn);
        }
        return retVal;
    }

    public HashMap<Long, M8>  getClassMatesOfStudent(int id)throws DatabaseException{
    	HashMap<Long,M8> map = new HashMap<>();
        Connection conn = this.OpenConnection();
        ResultSet rs = null;
        PreparedStatement selectStudent = null;
        try {
            String updateString =
                    "select * from M8 where SCHOOLCLASS = ?";
            selectStudent = conn.prepareStatement(updateString);
            selectStudent.setInt(1,id);
            rs = selectStudent.executeQuery();

            while (rs.next()) {
            	int ID = rs.getInt("ID");
            	String firstname= rs.getString("VORNAME");
            	String lastname= rs.getString("NACHNAME");
            	String email= rs.getString("EMAIL");
            	String password= rs.getString("PASSWORD");
            	boolean hasVoted= rs.getBoolean("HASVOTED");
            	int votes= rs.getInt("VOTES");
       
            	map.put((long) ID, new M8(ID, firstname, lastname, email, password, hasVoted, votes));
            }
            rs.close();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(rs, conn);
        }
        return map;
    }

    public boolean isStudentClassDeputy(int id)throws DatabaseException{
        Connection conn = this.OpenConnection();
        ResultSet rs = null;
        boolean retVal=false;
        PreparedStatement selectStudent = null;
        try {
            String updateString =
                    "select COUNT(*) from Schoolclass where President = ?";
            selectStudent = conn.prepareStatement(updateString);
            selectStudent.setInt(1,id);
            int c;
            c = selectStudent.executeQuery().getInt(1);

            if(c==1)
                retVal = true;
            rs.close();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(rs, conn);
        }
        return retVal;
    }

    public boolean isStudentViceClassDeputy(int id)throws DatabaseException{
        Connection conn = this.OpenConnection();
        ResultSet rs = null;
        boolean retVal;
        PreparedStatement selectStudent = null;
        try {
            String updateString =
                    "select COUNT(*) from Schoolclass where President_DEPUTY = ?";
            selectStudent = conn.prepareStatement(updateString);
            selectStudent.setInt(1,id);
            rs = selectStudent.executeQuery();

            retVal = rs.getBoolean(0);
            rs.close();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(rs, conn);
        }
        return retVal;
    }

    public boolean removeStudentById(int id)throws DatabaseException{
        Connection conn = this.OpenConnection();
        ResultSet rs = null;
        PreparedStatement selectStudent = null;
        boolean retVal;
        try {
            String updateString =
                    "DELETE M8 where ID = ?";
            selectStudent = conn.prepareStatement(updateString);
            selectStudent.setInt(1,id);
            rs = selectStudent.executeQuery();

            retVal = rs.getBoolean(0);
            rs.close();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(rs, conn);
        }
        return retVal;
    }

    public boolean removeEntryOfStudentInClass(int StudentId)throws DatabaseException{
        Connection conn = this.OpenConnection();
        PreparedStatement selectStudent = null;
        boolean retVal;
        try {
            String updateString =
                    "UPDATE M8 SET SCHOOLCLASS=null where ID = ?";
            selectStudent = conn.prepareStatement(updateString);
            selectStudent.setInt(1,StudentId);
            retVal = selectStudent.execute();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(conn);
        }
        return retVal;
    }

    //School
    public boolean addSchool(School s)throws DatabaseException{
        Connection conn = this.OpenConnection();
        boolean retVal;
        PreparedStatement selectStudent = null;
        try {
            String updateString =
                    "INSERT INTO SCHOOL VALUES( ? , ?)";
            selectStudent = conn.prepareStatement(updateString);

            selectStudent.setInt(1,(int)s.getId());
            selectStudent.setString(2,s.getIdentifier());

            retVal = selectStudent.execute();

            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(conn);
        }
        return retVal;
    }

	
    public boolean deleteSchoolClass(int id) throws DatabaseException {
		Connection conn = this.OpenConnection();
        PreparedStatement selectStudent = null;
        boolean retVal;
        try {
            String updateString =
                    "DELETE SCHOOLCLASS where ID = ?";
            selectStudent = conn.prepareStatement(updateString);
            selectStudent.setInt(1,id);
            retVal = selectStudent.execute();

            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(conn);
        }
        return retVal;
		
	}

	public Schoolclass getSchoolclassById(int id) throws DatabaseException {
		Schoolclass c = new Schoolclass();
        Connection conn = this.OpenConnection();
        ResultSet rs = null;
        PreparedStatement selectStudent = null;
        try {
            String updateString =
                    "select * from Schoolclass where ID = ?";
            selectStudent = conn.prepareStatement(updateString);
            selectStudent.setInt(1,id);
            rs = selectStudent.executeQuery();

            while (rs.next()) {
            	c.setId(rs.getInt("ID"));
            	c.setName(rs.getString("NAME"));
            	c.setRoom(rs.getString("ROOM"));
            	 }
            rs.close();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(rs, conn);
        }
        return c;
	}
	
	public int getMaxSchoolclassId() throws DatabaseException {
		
		int max = 0;
        Connection conn = this.OpenConnection();
        ResultSet rs = null;
        PreparedStatement selectStudent = null;
        try {
            String updateString =
                    "select MAX(id) as ID from Schoolclass";
            selectStudent = conn.prepareStatement(updateString);
            rs = selectStudent.executeQuery();

            while (rs.next()) {
            	max = rs.getInt("ID");
            	 }
            rs.close();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(rs, conn);
        }
        return max;
	}
public int getMaxM8Id() throws DatabaseException {
		
		int max = 0;
        Connection conn = this.OpenConnection();
        ResultSet rs = null;
        PreparedStatement selectStudent = null;
        try {
            String updateString =
                    "select MAX(id) as ID from M8";
            selectStudent = conn.prepareStatement(updateString);
            rs = selectStudent.executeQuery();

            while (rs.next()) {
            	max = rs.getInt("ID");
            	 }
            rs.close();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(rs, conn);
        }
        return max;
	}

	public boolean updateStudent(M8 student)throws DatabaseException{
    Connection conn = this.OpenConnection();
    boolean rs = false;
    PreparedStatement selectStudent = null;
    try {
        String updateString =
                "UPDATE M8 SET VORNAME=?,NACHNAME=?,EMAIL=?, PASSWORD = ?, HASVOTED = ?, VOTES = ? where ID = ?";
        selectStudent = conn.prepareStatement(updateString);
        selectStudent.setString(1,student.getFirstname());
        selectStudent.setString(2,student.getLastname());
        selectStudent.setString(3,student.getEmail());
        selectStudent.setString(4,student.getPassword());
        if(student.isHasVoted())
        	selectStudent.setInt(5,1);
        else
        	selectStudent.setInt(5,0);
        selectStudent.setInt(6,student.getVotes());
        selectStudent.setInt(7,(int)student.getId());
        rs = selectStudent.execute();
        selectStudent.close();
        conn.close();
    } catch (SQLException e) {
        throw new DatabaseException(e.getMessage());
    } finally {
        this.isConnectionClosed(conn);
    }
    return rs;
}

	public boolean updateClass(Schoolclass c) throws DatabaseException {
		Connection conn = this.OpenConnection();
	    boolean rs = false;
	    PreparedStatement selectStudent = null;
	    try {
	        String updateString =
	                "UPDATE SCHOOLCLASS SET PRESIDENT=?,PRESIDENT_DEPUTY=?,NAME=?, ROOM = ? where ID = ?";
	        selectStudent = conn.prepareStatement(updateString);

            if(c.getPresident() != null)
	            selectStudent.setInt(1,(int)c.getPresident().getId());
            else
                selectStudent.setString(1,null);

            if(c.getPresidentDeputy() != null)
                selectStudent.setInt(2,(int)c.getPresidentDeputy().getId());
            else
                selectStudent.setString(2,null);

	        selectStudent.setString(3,c.getName());
	        selectStudent.setString(4,c.getRoom());
	        selectStudent.setInt(5,(int)c.getId());

	        rs = selectStudent.execute();
	        selectStudent.close();
            conn.commit();
	        conn.close();
	    } catch (SQLException e) {
	        throw new DatabaseException(e.getMessage());
	    } finally {
	        this.isConnectionClosed(conn);
	    }
	    return rs;
		
	}

	public int getMaxSchoolId() throws DatabaseException {
		int max = 0;
        Connection conn = this.OpenConnection();
        ResultSet rs = null;
        PreparedStatement selectStudent = null;
        try {
            String updateString =
                    "select MAX(id) as ID from SCHOOL";
            selectStudent = conn.prepareStatement(updateString);
            rs = selectStudent.executeQuery();

            while (rs.next()) {
            	max = rs.getInt("ID");
            	 }
            rs.close();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(rs, conn);
        }
        return max;
	}


    public  School getSchoolById(Integer idSchool) throws DatabaseException {
        Connection conn = this.OpenConnection();
        ResultSet rs = null;
        PreparedStatement selectStudent = null;
        School s = new School();
        try {
            String updateString =
                    "select * from SCHOOL where Id = ?";
            selectStudent = conn.prepareStatement(updateString);
            selectStudent.setInt(1,idSchool);
            rs = selectStudent.executeQuery();

            while (rs.next()) {
                String identifier = rs.getString("IDENTIFIER");
                s = new School(idSchool, identifier);
            }

            rs.close();
            selectStudent.close();
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            this.isConnectionClosed(rs, conn);
        }
        return s;
    }
}
