package controller;

import java.io.IOException;
import java.sql.*;
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

import dao.SentenceDao;
import dto.Emotion;
import dto.Sentence;
import dto.Story;
import dto.Voice;
import model.DBUtils;

@WebServlet("/setVoiceEmotion")
public class SetVoiceEmotion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public SetVoiceEmotion() {
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// for DB connection
		ServletContext sc = getServletContext();
		Connection con = (Connection)sc.getAttribute("DBconnection");
		//DBUtils db = new DBUtils();

		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		//HttpSession session = request.getSession(true);
		
		//문장마다 받아온 화자, 감정 정보 설정
		//String book_title = (String) request.getAttribute("story_name");
		Story currStory = (Story) request.getAttribute("currStory");
		ArrayList<String> speak = (ArrayList<String>) request.getAttribute("speaker");
		ArrayList<String> speak_t=(ArrayList<String>) request.getAttribute("speaker_t");
		List<Voice> voiceSet = (List<Voice>) request.getAttribute("voiceSet");
		List<Emotion> emotionSet = (List<Emotion>) request.getAttribute("emotionSet");
		
		ArrayList<String> sent = new ArrayList<String>();
		ArrayList<String> voice_t = new ArrayList<String>();
		List<Sentence> sentenceSet = new ArrayList<Sentence>();
		String temp;
		
		//문장
		System.out.println(speak.size()); //염병 왜 안되는지 모르겟어
		for(int i=0; i<speak.size(); i++) {
			temp=(String)request.getParameter("sent"+i);
			sent.add(temp);
		}
		
		//문장 별 설정한 음성을 voice_t 배열에 저장한다.
		for(int i=0;i<speak_t.size();i++) {
			voice_t.add(request.getParameter("voice" + i));
		}
		int len = sent.size();
		
		//DB 등록
		try {
			String sentence, speaker, voiceVal, emotionVal;
			float intensity;
			
			//String voiceq = "SELECT voice_id FROM voice WHERE voice_name=?";
			//String emotionq = "SELECT emotion_id FROM emotion WHERE emotion_name=?";
			
			//PreparedStatement voiceps = con.prepareStatement(voiceq);
			//PreparedStatement emotionps = con.prepareStatement(emotionq);
			
			String n;
			int j_loc=0;
			//문장 별로 설정 값을 DB에 저장한다.
			for (int i = 0; i < len; i++) {
				n = Integer.toString(i);
				speaker = speak.get(i);
				sentence = sent.get(i);
				
				//상단에서 설정한 화자의 목소리를 문장 별 화자를 찾아서 적용한다.
				for(int j=0;j<speak_t.size();j++) {
					if(speaker.equals(speak_t.get(j))) {
						j_loc=j;
						break;
					}
				}
				
				//문장 별 설정 값들을 가져온다.
				voiceVal = voice_t.get(j_loc);
				emotionVal = request.getParameter("emotion" + n);
				intensity = Float.parseFloat(request.getParameter("range" + n));
				
				//Emotion, Voice의 id 값은 List에서 다시 구한다.
				int emotionId = 0;
				int voiceId = 0;
				for(int j=0; j<voiceSet.size(); j++) {
					if(voiceSet.get(i).getVoiceName().equals(voiceVal)) {
						voiceId = voiceSet.get(i).getVoiceId();
					}
				}
				for(int j=0; j<emotionSet.size(); j++) {
					if(emotionSet.get(i).getEmotionName().equals(emotionVal)) {
						emotionId = emotionSet.get(i).getEmotionId();
					}
				}
				
				//voiceps.setString(1, voiceVal);
				//emotionps.setString(1, emotionVal);
				
				
				//ResultSet rsVoice = voiceps.executeQuery();
				//rsVoice.next();
				
				//ResultSet rsEmotion = emotionps.executeQuery();
				//rsEmotion.next();
				
				//int story_id = (int) session.getAttribute("story_id");
				int story_id = currStory.getStoryId();
				
				// DB와 List 형태로 저장
				
				Sentence invidSent = SentenceDao.insertSent(con, sentence, speaker, emotionId, voiceId, intensity, story_id);
				invidSent.setEmotionName(emotionVal);
				invidSent.setVoiceName(voiceVal);
				sentenceSet.add(invidSent);
				
				//System.out.println(sentence + speaker + rsVoice.getInt(1) + rsEmotion.getInt(1) + intensity + story_id);
				
				
				//db.insertSent(con, sentence, speaker, rsVoice.getInt(1), rsEmotion.getInt(1), intensity, story_id);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		//session.setAttribute("isBegan", 1);
		//session.setAttribute("playAll","false");
		request.setAttribute("sentenceSet", sentenceSet);
		RequestDispatcher rd = request.getRequestDispatcher("/makeJsonServlet");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
