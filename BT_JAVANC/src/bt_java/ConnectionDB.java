package bt_java;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
		private static String DB_URL = "jdbc:mysql://localhost:3306/javanc";
    	private static String USER_NAME = "root";
    	private static String PASSWORD = "";
    public static void main(String[] args) {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		Connection conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
    		System.out.println("connect successfully!");

    		} catch (ClassNotFoundException | SQLException e) {
    		// TODO Auto-generated catch lock
    		e.printStackTrace();

    		}
}
}
