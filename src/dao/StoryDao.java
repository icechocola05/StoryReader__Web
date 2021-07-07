package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dto.Story;

public class StoryDao {
	
	//Story 삽입
	private final static String SQLST_INSERT_STORY = "INSERT INTO story (story_name, story_author) VALUES (?, ?)";
	
	//Story 삽입 성공 시 story 객체 return
	public static Story insertStory(Connection con, String title, String author) throws SQLException {
		PreparedStatement pstmt = null;
		Story story = new Story();
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(SQLST_INSERT_STORY, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, title);
			pstmt.setString(2, author);
			
			pstmt.executeUpdate();
			
			//넣은 데이터의 story_id 값 가져오기
			ResultSet rs = pstmt.getGeneratedKeys();  
			rs.next();  
			int id = rs.getInt(1);
			
			con.commit();
			con.setAutoCommit(true);
			
			story.setStoryId(id);
			story.setStoryName(title);
			story.setStoryAuthor(author);
			
			return story;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {pstmt.close(); }
		}
		return story;
	}

}
