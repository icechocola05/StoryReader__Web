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

		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(true);
		
		Story currStory = (Story) session.getAttribute("currStory");
		ArrayList<String> speaker = (ArrayList<String>) session.getAttribute("speaker");
		ArrayList<String> speakerType = (ArrayList<String>) session.getAttribute("speakerType");
		List<Voice> voiceSet = (List<Voice>) session.getAttribute("voiceSet");
		List<Emotion> emotionSet = (List<Emotion>) session.getAttribute("emotionSet");
		
		ArrayList<String> sentence = new ArrayList<String>();
		ArrayList<String> voiceType = new ArrayList<String>();
		List<Sentence> sentenceSet = new ArrayList<Sentence>();
		String temp;
		
		//문장 받아오기
		for(int i=0; i<speaker.size(); i++) {
			temp = (String)request.getParameter("sentence" + i);
			sentence.add(temp);
		}
		
		//문장 별 설정한 음성을 voiceType 배열에 저장한다.
		int speakerTypeSize = speakerType.size();
		for(int i=0; i<speakerTypeSize; i++) {
			voiceType.add(request.getParameter("voice" + i));
		}
		
		int sentenceSize = sentence.size();
		//DB 등록
		try {
			String sentenceInput, speakerInput, voiceVal, emotionVal;
			float intensity;
			String n;
			int index = 0;
			
			//문장 별로 설정 값을 DB에 저장한다.
			for (int i = 0; i < sentenceSize; i++) {
				n = Integer.toString(i);
				speakerInput = speaker.get(i);
				sentenceInput = sentence.get(i);
				
				//상단에서 설정한 화자의 목소리를 문장 별 화자를 찾아서 적용한다.
				for(int j=0; j<speakerTypeSize; j++) {
					if(speaker.equals(speakerType.get(j))) {
						index = j;
						break;
					}
				}
				
				//문장 별 설정 값들을 가져온다.
				voiceVal = voiceType.get(index);
				emotionVal = request.getParameter("emotion" + n);
				intensity = Float.parseFloat(request.getParameter("range" + n));
				
				//Emotion, Voice의 id 값은 List에서 다시 구한다.
				int emotionId = 0;
				int voiceId = 0;
				for(int j=0; j<voiceSet.size(); j++) {
					if(voiceSet.get(j).getVoiceName().equals(voiceVal)) {
						voiceId = voiceSet.get(j).getVoiceId();
					}
				}
				for(int j=0; j<emotionSet.size(); j++) {
					if(emotionSet.get(j).getEmotionName().equals(emotionVal)) {
						emotionId = emotionSet.get(j).getEmotionId();
					}
				}
				int story_id = currStory.getStoryId();
				
				// DB와 List 형태로 저장
				Sentence invidSent = SentenceDao.insertSent(con, sentenceInput, speakerInput, emotionId, voiceId, intensity, story_id);
				invidSent.setEmotionName(emotionVal);
				invidSent.setVoiceName(voiceVal);
				sentenceSet.add(invidSent);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("isBegan", 1);
		request.setAttribute("playAll","false");
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
