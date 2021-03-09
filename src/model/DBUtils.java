package model;

import java.sql.*;

public class DBUtils {
	public int insertStory(Connection con, String title, String author) throws SQLException {
		PreparedStatement pstmt = null;
		int id = 0;
		try {
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement("INSERT INTO story (story_name, story_author) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, title);
			pstmt.setString(2, author);
			
			pstmt.executeUpdate();
			
			//넣은 데이터의 story_id 값 가져오기
			ResultSet rs = pstmt.getGeneratedKeys();  
			rs.next();  
			id = rs.getInt(1);
			
			con.commit();
			con.setAutoCommit(true);
			
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {pstmt.close(); }
		}
		return id;
	}
	
	public void insertSent(Connection con, String sentence, int voiceVal, int emotionVal, float intensity, int story_id) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement("INSERT INTO sentence (sent_txt, sent_intensity, voice_id, emotion_id, story_id) VALUES (?, ?, ?, ?, ?)");
			pstmt.setString(1, sentence);
			pstmt.setFloat(2, intensity);
			pstmt.setInt(3, voiceVal);
			pstmt.setInt(4, emotionVal);
			pstmt.setInt(5, story_id);
			
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
