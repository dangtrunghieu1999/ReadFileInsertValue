package read_file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ReadFileData {

	private Connection connection;
	private String seprator;
	
	public ReadFileData(Connection connection) {
		this.connection = connection;
		this.seprator = ",";
	}
	
	public void loadFileData(String file) throws Exception {
		
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader bReader = null;
		int START_LINE = 1;
		int counter = START_LINE;
		
		String tableName = "Student ";
		String parameter = "(id, full_name, id_class, gender, hobby)";
		String value = " VALUES";
		String parameterValues = " (?,?,?,?,?)";
		
//		String sql = "INSERT INTO Student (id, full_name, id_class, gender, hobby) VALUES  (?,?,?,?,?)";
		String sql = "INSERT INTO " + tableName + parameter + value + parameterValues ;
		System.out.println(sql);
		PreparedStatement statement = connection.prepareStatement(sql);
		
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			bReader = new BufferedReader(isr);

			String line = null;
			String arrayData[] = null;
			int countElement = 1;
			while (true) {
				line = bReader.readLine();
				if (line == null) {
					break;
				} else {
					if (counter > START_LINE) {
						
						arrayData = line.split(seprator);
						for (int i = 0; i < arrayData.length; i++) {
							statement.setString(countElement, arrayData[i]);
							countElement++;
						}
						statement.executeUpdate();
						countElement = 1;
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
	}
	
	public String getSeprator() {
		return seprator;
	}

	public void setSeprator(String seprator) {
		this.seprator = seprator;
	}

}
