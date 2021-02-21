package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
}