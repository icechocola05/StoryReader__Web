package model;

import java.sql.*;

public class DBUtils {
	public void insertStory(Connection con, String title, String author) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement("INSERT INTO story (story_name,story_author) VALUES (?,?)");
			pstmt.setString(1, title);
			pstmt.setString(2, author);
			
			pstmt.executeUpdate();
			
			con.commit();
			
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {pstmt.close(); }
		}
	}
	
	public void insertSent(Connection con, String mainTxt) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement("INSERT INTO sentence (sent_id, sent_txt) VALUES (?, ?)");
			pstmt.setInt(1, 0);
			pstmt.setString(2, mainTxt);
			
			pstmt.executeUpdate();
			
			con.commit();
			
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {pstmt.close(); }
		}
	}
	
	

}
