package controller;

import java.io.File;
import java.io.IOException;
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
		int n=(int)session.getAttribute("story_id");//현재 문장
		int sentNum = 0;
		session.setAttribute("sentNum", sentNum);
		ServletContext sc = getServletContext();
		Connection conn = (Connection)sc.getAttribute("DBconnection");
		
		try {
			if((int)session.getAttribute("isBegan")==1) {
				System.out.println("isBegan");
				String query="SELECT sent_id,sent_txt FROM sentence WHERE story_id=?";
				PreparedStatement sent_ps = conn.prepareStatement(query,
                        ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);
				sent_ps.setInt(1,n);
				ResultSet rsSent = sent_ps.executeQuery();
				rsSent.next();
				session.setAttribute("rsSent", rsSent);
				System.out.println(rsSent.getInt("sent_id"));
				System.out.println(rsSent.getString("sent_txt"));
				session.setAttribute("sent_id", rsSent.getInt("sent_id"));
				session.setAttribute("sent",rsSent.getString("sent_txt"));
				session.setAttribute("isBegan", 0);
			}
			else {
				ResultSet rsSent=(ResultSet)session.getAttribute("rsSent");
				
				File sDir = new File(ATTACHES_DIR);
		    	if (!sDir.exists())
		    		sDir.mkdirs();

		    	int maxSize = 1024 * 1024 * 100;
		    	String encType = "UTF-8";
		    	
				MultipartRequest multipartRequest = new MultipartRequest(request, ATTACHES_DIR, maxSize, encType,
		    	    	new DefaultFileRenamePolicy());
				String button=multipartRequest.getParameter("move_btn");
				System.out.println("//"+button+" Clicked//");
				if(button.equals("next")) {
					System.out.println("nextBegan");	
					rsSent.next();
					if(!rsSent.next()) {
						rsSent.previous();
					}else {
					rsSent.next();
					session.setAttribute("sentNum", sentNum + 1);}
				}
				if(button.equals("pre")) {
					System.out.println("previousBegan");
					if(!rsSent.previous()) {
						rsSent.next();
						session.setAttribute("sentNum", 0);
					}else {
						rsSent.previous();
						session.setAttribute("sentNum", sentNum - 1);
						}
				}
				
				session.setAttribute("rsSent", rsSent);
				session.setAttribute("sent_id", rsSent.getInt("sent_id"));
				session.setAttribute("sent",rsSent.getString("sent_txt"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//다음 문장 or 이전 문장 받아오기
			
		RequestDispatcher rd = request.getRequestDispatcher("/setImg.jsp");
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
