package model;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
 
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
 
public class makeJson {
	public void createJsonFile(Table t) {
		for(int i=0;i<t.getLength();i++) {
			FileWriter writer=null;
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("text",t.getSent(i));
	    
			JsonObject voiceInfo = new JsonObject();
			voiceInfo.addProperty("name", t.getVoiceName(i));
			voiceInfo.addProperty("gender", t.getVoiceGender(i));
			voiceInfo.addProperty("age", t.getVoiceAge(i));
			jsonobject.add("voice", voiceInfo);
	        
			JsonObject emoInfo = new JsonObject();
			emoInfo.addProperty("name", t.getEmo1(i));
			emoInfo.addProperty("value", t.getEmo2(i));
			jsonobject.add("emotionInfo", emoInfo);
	    
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        	String json = gson.toJson(jsonobject);
	        	try {
	        		File file = new File("C:\\Users\\leejiwon\\Desktop\\jsonfile\\info"+i+1+".json");
	        		writer = new FileWriter(file);
	        		writer.write(json);
	        	} catch (IOException e) {
	        		// TODO Auto-generated catch block
	        		e.printStackTrace();
	        	}finally {
	        		try {
	        			writer.close();
	        		} catch (IOException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        			
	        		}
	        	}
		}
	 }
}