package dto;

public class Sentence {
	private String sent_txt;
	private String sent_speaker;
	private int story_id;
	private int voice_id;
	private int emotion_id;
	private float sent_intensity;
	
	private String voice_name;
	private String emotion_name;
	
	public Sentence() {}
	
	public Sentence(String sent_txt, String sent_speaker, int story_id, int voice_id, int emotion_id, 
			float sent_intensity) {
		this.sent_txt = sent_txt;
		this.sent_speaker = sent_speaker;
		this.story_id = story_id;
		this.voice_id = voice_id;
		this.emotion_id = emotion_id;
		this.sent_intensity = sent_intensity;
	}
	
	public void setVoiceName(String voice_name) {
		this.voice_name = voice_name;
	}
	
	public void setEmotionName(String emotion_name) {
		this.emotion_name = emotion_name;
	}
	
	public String getSentence() {
		return sent_txt;
	}
	
	public String getSpeaker() {
		return sent_speaker;
	}
	
	public int getStoryId() {
		return story_id;
	}
	
	public int getVoiceId() {
		return voice_id;
	}
	
	public int getEmotionId() {
		return emotion_id;
	}
	
	public float getIntensity() {
		return sent_intensity;
	}
	
	public String getVoiceName() {
		return voice_name;
	}
	
	public String getEmotionName() {
		return emotion_name;
	}
}
