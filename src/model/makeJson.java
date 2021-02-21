package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
}