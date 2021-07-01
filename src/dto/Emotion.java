package dto;

public class Emotion {
	private int emotion_id;
	private String emotion_name;
	private String emotion_kr_name;
	
	public Emotion() {}
	
	public void setEmotionId(int emotion_id) {
		this.emotion_id = emotion_id;
	}
	
	public void setEmotionName(String emotion_name) {
		this.emotion_name = emotion_name;
	}
	
	public void setEmotionKrName(String emotion_kr_name) {
		this.emotion_kr_name = emotion_kr_name;
	}
	
	public int getEmotionId() {
		return this.emotion_id;
	}
	
	public String getEmotionName() {
		return this.emotion_name;
	}
	
	public String getEmotionKrName() {
		return this.emotion_kr_name;
	}
}
