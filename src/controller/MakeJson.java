package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dto.Sentence;
import dto.Story;

@WebServlet("/makeJsonServlet")
public class MakeJson extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public MakeJson() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONArray jsonArray=new JSONArray();
	   
		//DB 정보로 json 만들기
		ServletContext sc = getServletContext();
		//Connection con = (Connection)sc.getAttribute("DBconnection");
		HttpSession session = request.getSession(true);
		
		Story currStory = (Story) session.getAttribute("currStory");
		int story_id = currStory.getStoryId();
		
		List<Sentence> sentenceSet = (List<Sentence>) request.getAttribute("sentenceSet");
		
		//현재 스토리에 대한 문장 정보를 JSON 파일로 생성
		try {
			
			//String sentq = "SELECT * FROM sentence WHERE story_id=?";
			//PreparedStatement sentps = con.prepareStatement(sentq);
			//sentps.setInt(1, story_id);
			//ResultSet rsSent = sentps.executeQuery();
			
			for(int i = 0; i<sentenceSet.size(); i++) {
				Sentence sent = sentenceSet.get(i);
				
				String text = sent.getSentence();
				String voice_name = sent.getVoiceName();
				String emotion_name = sent.getEmotionName();
				float intensityVal = sent.getIntensity();
				
				//String text = rsSent.getString(2);
				//int voiceId = rsSent.getInt(5);
				//int emotionId = rsSent.getInt(6);
				//float intensityVal = rsSent.getFloat(8);
				
				//voice_name, emotion_name 받아오기
				/*
				String voiceq = "SELECT voice_name FROM voice WHERE voice_id=?";
				String emotionq= "SELECT emotion_name FROM emotion WHERE emotion_id=?";
				PreparedStatement voiceps = con.prepareStatement(voiceq);
				PreparedStatement emotionps = con.prepareStatement(emotionq);
				voiceps.setInt(1,voiceId);
				emotionps.setInt(1,emotionId);
				ResultSet rsVoice = voiceps.executeQuery();
				ResultSet rsEmotion = emotionps.executeQuery();
				String voice_name="";
				String emotion_name="";
				while(rsVoice.next()) {voice_name=rsVoice.getString(1);}
				while(rsEmotion.next()) {emotion_name=rsEmotion.getString(1);}
				*/
				
				//JSON 생성
				JSONObject jsonObject=new JSONObject(); 
				JSONObject voiceInfo=new JSONObject();
				JSONObject emoInfo=new JSONObject();
				
				jsonObject.put("text", text);
				jsonObject.put("lang", "ko");
	
				voiceInfo.put("name", voice_name);
				voiceInfo.put("gender", "");
				voiceInfo.put("age", ""); 
				voiceInfo.put("variant", "");
				voiceInfo.put("onvoicefailure", "priorityselect");
				jsonObject.put("voice", voiceInfo);
		        
				emoInfo.put("name", emotion_name);
				emoInfo.put("value", intensityVal);
				jsonObject.put("emotionInfo", emoInfo);
				
		        jsonArray.add(jsonObject);
		        
			}
		}
		catch(Exception e) { //try - catch 필요 없음
			e.printStackTrace();
		}
		
		request.setAttribute("resultJson", jsonArray);
		session.setAttribute("i", 0);//0으로 초기화?
		RequestDispatcher rd = request.getRequestDispatcher("/fileConnection");
        rd.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
