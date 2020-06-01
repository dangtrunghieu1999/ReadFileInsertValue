package read_file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadFileXLSX {
	public static void main(String[] args) throws IOException, ClassNotFoundException {

		String jdbcURL = "jdbc:mysql://localhost:3306/DataTemp";
		String username = "root";
		String password = "trunghieu230899";
		String excelFilePath = "DataSinhVien.xlsx";

		int batchSize = 20;

		Connection connection = null;

		try {
			long start = System.currentTimeMillis();

			FileInputStream inputStream = new FileInputStream(excelFilePath);
			Workbook workbook = new XSSFWorkbook(inputStream);

			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = firstSheet.iterator();

			Class.forName("com.mysql.jdbc.Driver");

			connection = DriverManager.getConnection(jdbcURL, username, password);
			connection.setAutoCommit(false);

			String sql = "INSERT INTO Student (id_student, full_name, id_class, gender, hobby) VALUES  (?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);

			int count = 0;
			rowIterator.next();

			while (rowIterator.hasNext()) {
				Row nextRow = rowIterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();

				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();

					int columnIndex = nextCell.getColumnIndex();

					switch (columnIndex) {
					case 0:
						int id_student = (int) nextCell.getNumericCellValue();
						statement.setInt(1, id_student);
						break;
					case 1:
						String full_name = nextCell.getStringCellValue();
						statement.setString(2,full_name);
						break;
					case 2:
						String id_class = nextCell.getStringCellValue();
						statement.setString(3, id_class);
						break;
					case 3:
						String gender = nextCell.getStringCellValue();
						statement.setString(4, gender);
						break;
					case 4:
						String hobby = nextCell.getStringCellValue();
						statement.setString(5, hobby);
						break;
					}

				}

				statement.addBatch();

				if (count % batchSize == 0) {
					statement.executeBatch();
				}
			}

			workbook.close();

			// execute the remaining queries
			statement.executeBatch();

			connection.commit();
			connection.close();

			long end = System.currentTimeMillis();
			System.out.printf("Import done in %d ms\n", (end - start));

		} catch (FileNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
