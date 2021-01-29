package Controle;

import java.sql.DriverManager;
import java.sql.Connection;

public class DAO {
	public static Connection conectar() {
		Connection con = null;
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/estoque?useSSL=false";
		String user = "root";
		String password = "";
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
