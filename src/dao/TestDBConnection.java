package dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDBConnection {

	public static void main(String[] args) {
		Connection conn = null;

		try {

//			String dbURL = "jdbc:sqlserver://localhost;"
//					+ "databaseName=CrawlerDB;user=sa;password=Abcd1234";
			String dbURL = "jdbc:sqlserver://10.220.75.51;"
					+ "databaseName=CrawlerDB;user=adayroi.net;password=adayroi@123"; 
			conn = DriverManager.getConnection(dbURL);
			if (conn != null) {
				DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
				System.out.println("Driver name: " + dm.getDriverName());
				System.out.println("Driver version: " + dm.getDriverVersion());
				System.out.println("Product name: " + dm.getDatabaseProductName());
				System.out.println("Product version: " + dm.getDatabaseProductVersion());
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
}
