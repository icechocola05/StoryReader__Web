package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SettingDao {
	
	public static List<String> getVoice(Connection con) {
		try {
			Statement voiceSt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, Statement.RETURN_GENERATED_KEYS);
			voiceSt.execute("SELECT * FROM voice");
			ResultSet voiceRS = voiceSt.getResultSet();
			List<String> voice = null;
			return voice;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
