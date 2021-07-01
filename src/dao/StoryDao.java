package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StoryDao {
	
	//Story 삽입
	private final static String SQLST_INSERT_STORY = "INSERT INTO story (story_name, story_author) VALUES (?, ?)";
	
	//Story 삽입 성공 시 id 값 return
	public static int insertStory(Connection con, String title, String author) throws SQLException {
		PreparedStatement pstmt = null;
		int id = 0;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(SQLST_INSERT_STORY, Statement.RETURN_GENERATED_KEYS);
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

}
