package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

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
	
	public void updateWav(Connection con, int story_id, int sent_id, File audioFile) throws SQLException, FileNotFoundException {		
		
		PreparedStatement psmnt = con.prepareStatement("UPDATE sentence SET sent_wav = ? where sent_id = ? AND story_id = ?");
		
		psmnt.setInt(2, sent_id);
		psmnt.setInt(3, story_id);
		

		FileInputStream fis = new FileInputStream(audioFile); 
		psmnt.setBinaryStream(1, (InputStream) fis, (int) (audioFile.length()));

		int s = psmnt.executeUpdate();
	}
	public void updateImg(Connection con, int story_id, int sent_id, File ImgFile) throws SQLException, FileNotFoundException {
		PreparedStatement psmnt = con.prepareStatement("UPDATE sentence SET sent_img = ? where sent_id = ? AND story_id = ?");
		
		psmnt.setInt(2, sent_id);
		psmnt.setInt(3, story_id);
		
		FileInputStream fis = new FileInputStream(ImgFile); 
		psmnt.setBinaryStream(1, (InputStream) fis, (int) (ImgFile.length()));

		int s = psmnt.executeUpdate();
	}

}
