package dto;

public class Voice {
	private int voice_id;
	private String voice_name;
	private String voice_kr_name;
	
	public Voice() {}
	
	public void setVoiceId(int voice_id) {
		this.voice_id = voice_id;
	}
	
	public void setVoiceName(String voice_name) {
		this.voice_name = voice_name;
	}
	
	public void setVoiceKrName(String voice_kr_name) {
		this.voice_kr_name = voice_kr_name;
	}
	
	public int getVoiceId() {
		return this.voice_id;
	}
	
	public String getVoiceName() {
		return this.voice_name;
	}
	
	public String getVoiceKrName() {
		return this.voice_kr_name;
	}
}
