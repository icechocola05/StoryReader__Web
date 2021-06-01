package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class setImg
 */
@WebServlet("/setImg.do")
public class setImg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public setImg() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	private static final String ATTACHES_DIR = "C:\\attaches";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(true);
		
		int n=(int)session.getAttribute("story_id");//�쁽�옱 臾몄옣
		//int sentNum = 0;
		//session.setAttribute("sentNum", sentNum);
		ServletContext sc = getServletContext();
		Connection conn = (Connection)sc.getAttribute("DBconnection");
		
		try {
			
			if((int)session.getAttribute("isBegan")==1) {
				String query="SELECT sent_id,sent_txt,sent_speaker FROM sentence WHERE story_id=?";
				PreparedStatement sent_ps = conn.prepareStatement(query,
                        ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);
				sent_ps.setInt(1,n);
				ResultSet rsSent = sent_ps.executeQuery();
				
				rsSent.next();
				
				session.setAttribute("rsSent", rsSent);
				
				//이전 텍스트
				session.setAttribute("pre-sent_id", -1);
				session.setAttribute("pre-sentence",null);
				session.setAttribute("pre-speaker",null);
				
				//현재 텍스트
				session.setAttribute("sent_id", rsSent.getInt("sent_id"));
				session.setAttribute("sentence",rsSent.getString("sent_txt"));
				session.setAttribute("speaker",rsSent.getString("sent_speaker"));
				
				//다음 텍스트
				if(rsSent.next()==true) {
					session.setAttribute("next-sent_id", rsSent.getInt("sent_id"));
					session.setAttribute("next-sentence",rsSent.getString("sent_txt"));
					session.setAttribute("next-speaker",rsSent.getString("sent_speaker"));
					rsSent.previous();
				}
				
				session.setAttribute("sentNum", 0);
				session.setAttribute("isBegan", 0);
			}
			else {
				ResultSet rsSent=(ResultSet)session.getAttribute("rsSent");
				int sentNum=(int)session.getAttribute("sentNum");
				File sDir = new File(ATTACHES_DIR);
		    	if (!sDir.exists())
		    		sDir.mkdirs();

		    	int maxSize = 1024 * 1024 * 100;
		    	String encType = "UTF-8";
		    	
				
				String button=request.getParameter("move_btn");
				String button2=request.getParameter("replay");
				if(button==null&&button2!=null) {
					session.setAttribute("isBegan",1);
					rsSent.first();
					session.setAttribute("rsSent", rsSent);
					RequestDispatcher rd = request.getRequestDispatcher("/result.jsp");
					rd.forward(request, response);
					return;
				}
				if(button.equals("next")) {	
					if(!rsSent.next()) {//마지막 문장일 때
						rsSent.previous();
						RequestDispatcher rd = request.getRequestDispatcher("/lastPage.jsp");
						rd.forward(request, response);
						return;
						
					}else {
						session.setAttribute("sentNum", sentNum + 1);
					}
				}
				
				if(button.equals("pre")) {
					if(!rsSent.previous()) {
						rsSent.next();
						session.setAttribute("sentNum", 0);
					}else {
						//rsSent.previous();
						session.setAttribute("sentNum", sentNum - 1);
						}
				}
				
				session.setAttribute("rsSent", rsSent);
				
				//이전 텍스트
				if(rsSent.previous()==true) {
					session.setAttribute("pre-sent_id", rsSent.getInt("sent_id"));
					session.setAttribute("pre-sentence",rsSent.getString("sent_txt"));
					session.setAttribute("pre-speaker",rsSent.getString("sent_speaker"));
					rsSent.next();
				}else rsSent.next();
				
				//현재 텍스트
				session.setAttribute("sent_id", rsSent.getInt("sent_id"));
				session.setAttribute("sentence",rsSent.getString("sent_txt"));
				session.setAttribute("speaker",rsSent.getString("sent_speaker"));
				
				//다음 텍스트
				if(rsSent.next()==true) {
					session.setAttribute("next-sent_id", rsSent.getInt("sent_id"));
					session.setAttribute("next-sentence",rsSent.getString("sent_txt"));
					session.setAttribute("next-speaker",rsSent.getString("sent_speaker"));
					rsSent.previous();
				}else rsSent.previous();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//�떎�쓬 臾몄옣 or �씠�쟾 臾몄옣 諛쏆븘�삤湲�
			
		RequestDispatcher rd = request.getRequestDispatcher("/result.jsp");
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
