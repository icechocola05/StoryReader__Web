package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
<<<<<<< HEAD
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
 
public class makeJson {
	public String createJson(ArrayList<TextInfo> t) {
		JSONArray jsonArray=new JSONArray();
		
		for(int i=0;i<t.size();i++) {
			JSONObject jsonObject=new JSONObject(); 
			JSONObject voiceInfo=new JSONObject();
			JSONObject emoInfo=new JSONObject();
			
			jsonObject.put("text",t.get(i).getText());
			
			voiceInfo.put("name", t.get(i).getVoice());
			voiceInfo.put("gender", "");
			voiceInfo.put("age", ""); 
			jsonObject.put("voice", voiceInfo);
	        
			emoInfo.put("name", t.get(i).getEmotion());
			emoInfo.put("value", t.get(i).getValue());
			jsonObject.put("emotionInfo", emoInfo);
			
	        jsonArray.add(jsonObject);
	        
		}
		return jsonArray.toJSONString();
			
	 }
=======

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class makeJson {
	public void createJson(Table t) {
		JSONArray jsonArray = new JSONArray();

		LinkedHashMap<String, Object> myHashMap = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> voice = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> emotion = new LinkedHashMap<String, Object>();

		JSONObject jsonObject;
		JSONObject voiceInfo;
		JSONObject emoInfo;

		myHashMap.put("text", t.getSent(0));
		myHashMap.put("lang", "ko");

		voice.put("name", t.getVoiceName(0));
		voice.put("gender", "");
		voice.put("age", "");
		voice.put("variant", "");
		voice.put("onvoicefailure", "priorityselect");
		voiceInfo = new JSONObject(voice);
		myHashMap.put("voice", voiceInfo);

		emotion.put("name", t.getEmo1(0));
		emotion.put("value", t.getEmo2(0));
		emoInfo = new JSONObject(emotion);
		myHashMap.put("emotionInfo", emoInfo);

		jsonObject = new JSONObject(myHashMap);
		
		jsonArray.add(jsonObject);

		System.out.println(jsonObject.toJSONString());

	}
>>>>>>> feature/server
}