package model;

import java.io.Serializable;

public class TextInfo implements Serializable{
	String text;
	String speaker;
	String voice;
	int voiceid;
	String emotion;
	int value;
	
	public void setText(String s) {
		text=s;
	}
	public void setSpeaker(String s) {
		speaker=s;
	}
	//
	public void setVoiceEmo(String v, String e, int n) {
		voice=v;
		emotion=e;
		value=n;
	}
	//
	public void setVoice(String s) {
		voice=s;
	}
	public void setEmotion(String s) {
		emotion=s;
	}
	public void setValue(int n) {
		value=n;
	}
	//
	public String getText() {
		return text;
	}
	public String getSpeaker() {
		return speaker;
	}
	public String getVoice() {
		return voice;
	}
	public String getEmotion() {
		return emotion;
	}
	public int getValue() {
		return value;
	}
}
