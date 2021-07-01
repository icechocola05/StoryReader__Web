package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Emotion;
import dto.Voice;

public class SettingModel {
	
	// ResultSet -> List<String>
	public static List<Voice> changeResultSetToListVoice(ResultSet rs) {
		List<Voice> voiceList = new ArrayList<Voice>();
		
		try {
			// ResultSet의 내용을 카테고리 ArrayList로 변환
			while (rs.next()) {
				Voice voice = new Voice();
				voice.setVoiceId(rs.getInt("voice_id"));
				voice.setVoiceName(rs.getString("voice_name"));
				voice.setVoiceKrName(rs.getString("voice_kr_name"));
				voiceList.add(voice);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return voiceList;
	}
	
	// ResultSet -> List<String>
	public static List<Emotion> changeResultSetToListEmotion(ResultSet rs) {
		List<Emotion> emotionList = new ArrayList<Emotion>();
		
		try {
			// ResultSet의 내용을 카테고리 ArrayList로 변환
			while (rs.next()) {
				Emotion emotion = new Emotion();
				emotion.setEmotionId(rs.getInt("emotion_id"));
				emotion.setEmotionName(rs.getString("emotion_name"));
				emotion.setEmotionKrName(rs.getString("emotion_kr_name"));
				emotionList.add(emotion);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return emotionList;
	}
}
