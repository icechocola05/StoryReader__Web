package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.StoryDao;
import dto.Story;
import model.DBUtils;

@WebServlet("/ConfirmScript")
public class ConfirmScript extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ConfirmScript() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    
		//for DB connection
		ServletContext sc = getServletContext();
		Connection conn = (Connection)sc.getAttribute("DBconnection");		
		
		//get input value
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		
		//DB에 story 넣기
		Story currStory = new Story();
		try {
			currStory = StoryDao.insertStory(conn, title, author);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//story는 변화 없기 때문에 session으로 저장
		HttpSession session = request.getSession(true);
		session.setAttribute("currStory", currStory);
		
		//rawTxt 가공 후 저장
		String rawTxt = request.getParameter("mainTxt");		
		String[] tempTxt = rawTxt.split("\n"); //가공1_문장 단위로 나누기
		String[] splitTxt;

		ArrayList<String> sentence = new ArrayList<String>();
		ArrayList<String> speaker = new ArrayList<String>();
		int textLen = tempTxt.length;
		for(int i=0; i<textLen; i++) {
			if (tempTxt[i].contains(":")) { //가공2_텍스트에서 화자 제거
				splitTxt = tempTxt[i].split(":");
				speaker.add(splitTxt[0]);
				sentence.add(splitTxt[1]);
			} else {
				speaker.add("해설");
				sentence.add(tempTxt[i]);
			}
		}
		request.setAttribute("sentence", sentence); //모든 문장 -> setting에서 수정함
		session.setAttribute("speaker", speaker); //모든 화자 -> 더 이상 수정사항 없음
		
		//가공3_중복 화자 제거한, 화자 배열 구하기
		ArrayList<String> speakerType = new ArrayList<String>();
		LinkedHashSet<String> toRemoveOverlap = new LinkedHashSet<>(); //LinkedHashSet은 중복값 저장 불가능 
		for(String s : speaker)
			toRemoveOverlap.add(s);
		for(String s : toRemoveOverlap) //중복값 제외한 화자 speakerType에 저장
			speakerType.add(s);
		
		session.setAttribute("speakerType", speakerType); //중복 제외한 화자 -> 더 이상 수정사항 없음
		
		RequestDispatcher rd = request.getRequestDispatcher("/setting.jsp");
        rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
