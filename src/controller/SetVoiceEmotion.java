package controller;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		
		//문장마다 받아온 화자, 감정 정보 설정해서 DB 등록
		String book_title = (String) request.getAttribute("story_name");
		ArrayList<String> speak = (ArrayList<String>) request.getAttribute("speaker");
		ArrayList<String> speak_t=(ArrayList<String>) request.getAttribute("speaker_t");
		ArrayList<String> sent = new ArrayList<String>();
		ArrayList<String> voice_t = new ArrayList<String>();
		String temp;
		for(int i=0; i<speak.size(); i++) {
			temp=(String)request.getParameter("sent"+i);
			sent.add(temp);
		}
		
		for(int i=0;i<speak_t.size();i++) {
			voice_t.add(request.getParameter("voice" + i));
		}
		int len = sent.size();
		try {
			String sentence, speaker, voiceVal, emotionVal;
			float intensity;
			String voiceq = "SELECT voice_id FROM voice WHERE voice_name=?";
			String emotionq = "SELECT emotion_id FROM emotion WHERE emotion_name=?";
			
			PreparedStatement voiceps = con.prepareStatement(voiceq);
			PreparedStatement emotionps = con.prepareStatement(emotionq);
			
			String n;
			int j_loc=0;
			
			for (int i = 0; i < len; i++) {
				n = Integer.toString(i);
				
				speaker = speak.get(i);
				sentence = sent.get(i);
				for(int j=0;j<speak_t.size();j++) {
					if(speaker.equals(speak_t.get(j))) {
						j_loc=j;
					}
				}
				voiceVal = voice_t.get(j_loc);
				emotionVal = request.getParameter("emotion" + n);
				intensity = Float.parseFloat(request.getParameter("range" + n));
				
				voiceps.setString(1, voiceVal);
				emotionps.setString(1, emotionVal);
				
				
				ResultSet rsVoice = voiceps.executeQuery();
				rsVoice.next();
				
				ResultSet rsEmotion = emotionps.executeQuery();
				rsEmotion.next();
				
				int story_id = (int) session.getAttribute("story_id");
				
				
				System.out.println(sentence + speaker + rsVoice.getInt(1) + rsEmotion.getInt(1) + intensity + story_id);
				
				
				db.insertSent(con, sentence, speaker, rsVoice.getInt(1), rsEmotion.getInt(1), intensity, story_id);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		session.setAttribute("isBegan", 1);
		session.setAttribute("playAll","false");
		RequestDispatcher rd = request.getRequestDispatcher("/makeJsonServlet");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
