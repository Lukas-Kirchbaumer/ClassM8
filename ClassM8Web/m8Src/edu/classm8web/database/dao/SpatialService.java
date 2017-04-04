package edu.classm8web.database.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.classm8web.rs.result.Point;


public class SpatialService {
	
	
	private Connection conn = null;

	private String selectByIdStatement = "SELECT m.shape.SDO_POINT.X AS X, m.shape.SDO_POINT.Y AS Y from matepoint m WHERE mid = ?";
	private String insertByIdStatement = "INSERT INTO matepoint VALUES(?, SDO_GEOMETRY(2001, NULL, SDO_POINT_TYPE(?, ?, NULL), NULL, NULL))";
	private String deleteByIdStatement = "DELETE FROM matepoint WHERE mid = ?";
	private String updateByIdStatement = "UPDATE matepoint SET shape = SDO_GEOMETRY(2001, NULL, SDO_POINT_TYPE(?, ?, NULL), NULL, NULL) WHERE mid = ?";
	
	private static SpatialService instance;
	
	private SpatialService() {
	}
	
	public static SpatialService getInstance(){
		if(instance == null){
			instance = new SpatialService();
		}
		
		return instance;
	}
	
	public void createConnection(String cs, String user,String pw){
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			this.conn =  DriverManager.getConnection(cs, user, pw);
		} catch (SQLException e) {
			System.out.println("Problem with Sql Connection -> Spatial");
		}
	}
	
	public Point getPositionById(long id) throws SQLException{
		
		Point p = null;
		
		PreparedStatement state = conn.prepareStatement(selectByIdStatement , ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		state.setLong(1, id);
		
		ResultSet rs = state.executeQuery();
		while(rs.next()){
			p = new Point(rs.getDouble("X"), rs.getDouble("Y"));
		}
		
		return p;
	}

	public void setPointById(long mid, double x, double y) throws SQLException{
		PreparedStatement state = conn.prepareStatement(insertByIdStatement);
		
		state.setLong(1, mid);
		state.setDouble(2, x);
		state.setDouble(3, y);
		
		state.execute();
	}

	public void updatePointById(long mid, double x, double y) throws SQLException{
		PreparedStatement state = conn.prepareStatement(updateByIdStatement);
		
		
		state.setDouble(1, x);
		state.setDouble(2, y);
		state.setLong(3, mid);
		
		state.execute();
	}

	public void deletePointById(long id) throws SQLException {
		PreparedStatement state = conn.prepareStatement(deleteByIdStatement);
		
		state.setLong(1, id);
		
		state.execute();
	}
	
}
