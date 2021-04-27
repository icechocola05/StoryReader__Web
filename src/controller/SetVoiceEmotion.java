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

/**
 * Servlet implementation class setVoiceEmo
 */
@WebServlet("/setVoiceEmotion")
public class SetVoiceEmotion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SetVoiceEmotion() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// for DB connection
		ServletContext sc = getServletContext();
		Connection con = (Connection)sc.getAttribute("DBconnection");
		DBUtils db = new DBUtils();

		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(true);
		
		//문장마다 받아온 화자, 감정 정보 설정해서 DB 등록
		ArrayList<String> sent = (ArrayList<String>) session.getAttribute("sent");
		int len = sent.size();
		
		try {
			String sentence, voiceVal, emotionVal;
			float intensity;
			String voiceq = "SELECT voice_id FROM voice WHERE voice_name=?";
			String emotionq = "SELECT emotion_id FROM emotion WHERE emotion_name=?";
			
			PreparedStatement voiceps = con.prepareStatement(voiceq);
			PreparedStatement emotionps = con.prepareStatement(emotionq);
			
			for (int i = 0; i < len; i++) {
				String n = Integer.toString(i);
				sentence = sent.get(i);
				voiceVal = request.getParameter("voice" + n);
				emotionVal = request.getParameter("emotion" + n);
				intensity = Float.parseFloat(request.getParameter("range" + n));
				
				voiceps.setString(1, voiceVal);
				emotionps.setString(1, emotionVal);
				
				ResultSet rsVoice = voiceps.executeQuery();
				rsVoice.next();
				ResultSet rsEmotion = emotionps.executeQuery();
				rsEmotion.next();
				
				int story_id = (int) session.getAttribute("story_id");
				
				db.insertSent(con, sentence, rsVoice.getInt(1), rsEmotion.getInt(1), intensity, story_id);
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
