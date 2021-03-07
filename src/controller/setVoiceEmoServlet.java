package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Table;
import model.TextInfo;
import controller.DBUtils;

/**
 * Servlet implementation class setVoiceEmo
 */
@WebServlet("/setVoiceEmoServlet")
public class setVoiceEmoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public setVoiceEmoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//for DB connection
		
		HttpSession session = request.getSession(true);
		ArrayList<TextInfo> textInfo = new ArrayList<TextInfo>();
		textInfo = (ArrayList<TextInfo>) session.getAttribute("textInfo");
		for (int i = 0; i < textInfo.size(); i++) {
			String n = Integer.toString(i);

			textInfo.get(i).setVoice(request.getParameter("voice" + n));
			textInfo.get(i).setEmotion(request.getParameter("emotion" + n));
			textInfo.get(i).setValue(request.getParameter("range" + n));

		}

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
