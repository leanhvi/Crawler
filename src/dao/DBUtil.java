package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.LinkedList;

import urlpool.HashURL;

public class DBUtil {

	public static void insertHashUrl(LinkedList<HashURL> hashUrls) {

		try {

			// String dbURL =
			// "jdbc:sqlserver://localhost;databaseName=CrawlerDB;user=sa;password=Abcd1234";
			String dbURL = "jdbc:sqlserver://10.220.75.51;databaseName=CrawlerDB;user=adayroi.net;password=adayroi@123";

			Connection conn = DriverManager.getConnection(dbURL);

			conn.setAutoCommit(false);

			String INSERT_RECORD = "insert into HashUrl(HashCode, Url) values(?,?)";
			PreparedStatement pstmt = conn.prepareStatement(INSERT_RECORD);

			for (HashURL hashUrl : hashUrls) {
				pstmt.setLong(1, hashUrl.hash);
				pstmt.setString(2, hashUrl.url);
				pstmt.addBatch();
			}

			// execute the batch
			int[] updateCounts = pstmt.executeBatch();

			checkUpdateCounts(updateCounts);

			// since there were no errors, commit
			conn.commit();
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void checkUpdateCounts(int[] updateCounts) {
		for (int i = 0; i < updateCounts.length; i++) {
			if (updateCounts[i] >= 0) {
				System.out.println("OK; updateCount=" + updateCounts[i]);
			} else if (updateCounts[i] == Statement.SUCCESS_NO_INFO) {
				System.out.println("OK; updateCount=Statement.SUCCESS_NO_INFO");
			} else if (updateCounts[i] == Statement.EXECUTE_FAILED) {
				System.out.println("Failure; updateCount=Statement.EXECUTE_FAILED");
			}
		}
	}

}
