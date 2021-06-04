package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

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
 * Servlet implementation class ConfirmScript
 */
@WebServlet("/ConfirmScript")
public class ConfirmScript extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmScript() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		response.setContentType("text/html; charset=UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    
		//for DB connection
		ServletContext sc = getServletContext();
		Connection conn = (Connection)sc.getAttribute("DBconnection");
		DBUtils db = new DBUtils();
				
		String book_title = request.getParameter("bookname");
		String book_author = request.getParameter("bookauthor");
		
		int story_id = 0;
		try {
			story_id = db.insertStory(conn, book_title, book_author);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		session.setAttribute("story_id", story_id);
		
		//mainTxt 가공해서 저장해두기 -> 테이블에 띄워야 함. DB 연결은 setting 후에
		String mainTxt = request.getParameter("booktext");		
		String[] tempTxt = mainTxt.split("\n");
		String[] splitTxt;

		ArrayList<String> sent = new ArrayList<String>();
		ArrayList<String> speaker = new ArrayList<String>();
		int textLen = tempTxt.length;
		
		for(int i=0; i<textLen; i++) {
			if (tempTxt[i].contains(":")) {// 텍스트에서 화자 제거
				splitTxt = tempTxt[i].split(":");
				speaker.add(splitTxt[0]);
				sent.add(splitTxt[1]);
			} else {
				speaker.add("해설");
				sent.add(tempTxt[i]);
			}
		}
		session.setAttribute("story_name", book_title);
		session.setAttribute("sent", sent);
		session.setAttribute("speaker", speaker);//sessionAttribute: ArrayList로 본문 내용 저장
		
		RequestDispatcher rd = request.getRequestDispatcher("/setting.jsp");
        rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
