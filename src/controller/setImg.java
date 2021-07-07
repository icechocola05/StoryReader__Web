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
		//HttpSession session = request.getSession(true);
        
        System.out.println("error here!!");
		
        int n = 10;
		//int n=(int)session.getAttribute("story_id");
		//int sentNum = 0;
		//session.setAttribute("sentNum", sentNum);
		ServletContext sc = getServletContext();
		Connection conn = (Connection)sc.getAttribute("DBconnection");
		
		try {
			
			if((int)request.getAttribute("isBegan")==1) {
				String query="SELECT sent_id,sent_txt,sent_speaker FROM sentence WHERE story_id=?";
				PreparedStatement sent_ps = conn.prepareStatement(query,
                        ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);
				sent_ps.setInt(1,n);
				ResultSet rsSent = sent_ps.executeQuery();
				
				rsSent.next();
				
				request.setAttribute("rsSent", rsSent);
				
				//이전 텍스트
				request.setAttribute("pre-sent_id", -1);
				request.setAttribute("pre-sentence",null);
				request.setAttribute("pre-speaker",null);
				
				//현재 텍스트
				request.setAttribute("sent_id", rsSent.getInt("sent_id"));
				request.setAttribute("sentence",rsSent.getString("sent_txt"));
				request.setAttribute("speaker",rsSent.getString("sent_speaker"));
				
				//다음 텍스트
				if(rsSent.next()==true) {
					request.setAttribute("next-sent_id", rsSent.getInt("sent_id"));
					request.setAttribute("next-sentence",rsSent.getString("sent_txt"));
					request.setAttribute("next-speaker",rsSent.getString("sent_speaker"));
					rsSent.previous();
				}
				
				request.setAttribute("sentNum", 0);
				request.setAttribute("isBegan", 0);
			}
			else {
				ResultSet rsSent=(ResultSet)request.getAttribute("rsSent");
				int sentNum=(int)request.getAttribute("sentNum");
				File sDir = new File(ATTACHES_DIR);
		    	if (!sDir.exists())
		    		sDir.mkdirs();

		    	int maxSize = 1024 * 1024 * 100;
		    	String encType = "UTF-8";
		    	
				
				String button=request.getParameter("move_btn");
				
				if(button.equals("next")) {	
					if(!rsSent.next()) {//마지막 문장일 때
						rsSent.previous();
					}else {
						request.setAttribute("sentNum", sentNum + 1);
					}
				}
				
				if(button.equals("pre")) {
					if(!rsSent.previous()) {
						rsSent.next();
						request.setAttribute("sentNum", 0);
					}else {
						//rsSent.previous();
						request.setAttribute("sentNum", sentNum - 1);
						}
				}
				if(button.equals("replay")) {
					request.setAttribute("sentNum", 0);
					request.setAttribute("isBegan", 1);
					RequestDispatcher rd = request.getRequestDispatcher("/setImg.do");
					rd.forward(request, response);
					return;
				}
				
				request.setAttribute("rsSent", rsSent);
				
				//이전 텍스트
				if(rsSent.previous()==true) {
					request.setAttribute("pre-sent_id", rsSent.getInt("sent_id"));
					request.setAttribute("pre-sentence",rsSent.getString("sent_txt"));
					request.setAttribute("pre-speaker",rsSent.getString("sent_speaker"));
					rsSent.next();
				}else rsSent.next();
				
				//현재 텍스트
				request.setAttribute("sent_id", rsSent.getInt("sent_id"));
				request.setAttribute("sentence",rsSent.getString("sent_txt"));
				request.setAttribute("speaker",rsSent.getString("sent_speaker"));
				
				//다음 텍스트
				if(rsSent.next()==true) {
					request.setAttribute("next-sent_id", rsSent.getInt("sent_id"));
					request.setAttribute("next-sentence",rsSent.getString("sent_txt"));
					request.setAttribute("next-speaker",rsSent.getString("sent_speaker"));
					rsSent.previous();
				}else rsSent.previous();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
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
