package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SentenceDao {
	public void insertSent(Connection con, String sentence, String speaker, int voiceVal, int emotionVal, float intensity, int story_id) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement("INSERT INTO sentence (sent_txt, sent_speaker, sent_intensity, voice_id, emotion_id, story_id) VALUES (?, ?, ?, ?, ?, ?)");
			pstmt.setString(1, sentence);
			pstmt.setString(2, speaker);
			pstmt.setFloat(3, intensity);
			pstmt.setInt(4, voiceVal);
			pstmt.setInt(5, emotionVal);
			pstmt.setInt(6, story_id);
			
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
