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

import dao.StoryDao;
import model.DBUtils;

@WebServlet("/ConfirmScript")
public class ConfirmScript extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ConfirmScript() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//HttpSession session = request.getSession(true);
		response.setContentType("text/html; charset=UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    
		//for DB connection
		ServletContext sc = getServletContext();
		Connection conn = (Connection)sc.getAttribute("DBconnection");
		
		//get input value
		String book_title = request.getParameter("bookname");
		String book_author = request.getParameter("bookauthor");
		
		//DB에 story 넣기
		int story_id = 0;
		try {
			story_id = StoryDao.insertStory(conn, book_title, book_author);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("story_id", story_id);
		
		//rawTxt 가공 후 저장
		String rawTxt = request.getParameter("booktext");		
		String[] tempTxt = rawTxt.split("\n"); //가공1_문장 단위로 나누기
		String[] splitTxt;

		ArrayList<String> sent = new ArrayList<String>();
		ArrayList<String> speaker = new ArrayList<String>();
		int textLen = tempTxt.length;
		
		for(int i=0; i<textLen; i++) {
			if (tempTxt[i].contains(":")) {//가공2_텍스트에서 화자 제거
				splitTxt = tempTxt[i].split(":");
				speaker.add(splitTxt[0]);
				sent.add(splitTxt[1]);
			} else {
				speaker.add("해설");
				sent.add(tempTxt[i]);
			}
		}
		
		request.setAttribute("story_name", book_title);
		request.setAttribute("sent", sent);
		request.setAttribute("speaker", speaker);
		
		RequestDispatcher rd = request.getRequestDispatcher("/setting.jsp");
        rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
