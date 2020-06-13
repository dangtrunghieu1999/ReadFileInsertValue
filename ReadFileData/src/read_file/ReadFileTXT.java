package read_file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class ReadFileTXT {
	
	public static void main(String[] args) {
		String jdbcURL = "jdbc:mysql://localhost:3306/DataTemp";
		String username = "root";
		String password = "trunghieu230899";
		String filePath = "Student.txt";
		String dilimeted = "	";
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, username, password);
			connection.setAutoCommit(false);
			String sql = "INSERT INTO Student (id, full_name, id_class, gender, hobby) VALUES  (?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader bReader = null;
			int START_LINE = 1;
			int counter = START_LINE;

			try {
				fis = new FileInputStream(filePath);
				isr = new InputStreamReader(fis);
				bReader = new BufferedReader(isr);

				String line = null;
				String arrayData[] = null;

				while (true) {
					line = bReader.readLine();
					if (line == null) {
						break;
					} else {
						if (counter > START_LINE) {
							arrayData = line.split(dilimeted);
							statement.setString(1, arrayData[0]);
							statement.setString(2, arrayData[1]);
							statement.setString(3, arrayData[2]);
							statement.setString(4, arrayData[3]);
							statement.setString(5, arrayData[4]);
							statement.executeUpdate();
							
						}
						counter++;

					}
				}
				System.out.println("Insert success record");

			} catch (FileNotFoundException e) {
				System.out.println("Read file error");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {

				try {
					bReader.close();
					isr.close();
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			connection.commit();
			connection.close();
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
