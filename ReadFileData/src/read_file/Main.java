package read_file;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Main {
	private static String JDBC_CONNECTION_URL = 
			"jdbc:mysql://localhost:3306/DataTemp";
	static String username = "root";
	static String password = "trunghieu230899";
	static String filePath = "Student.txt";
	public static void main(String[] args) {
		try {
			ReadFileData loader = new ReadFileData(getConnection());
			loader.setSeprator("	");
			loader.loadFileData(filePath);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(JDBC_CONNECTION_URL, username, password);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}
}
