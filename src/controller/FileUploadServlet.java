package controller;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

import model.TextInfo;
import controller.DBUtils;

@WebServlet("/fileUploadServlet")
public class FileUploadServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ATTACHES_DIR = "C:\\attaches";
 
    @Override
    protected void doPost(HttpServletRequest request,  HttpServletResponse response)
            throws ServletException, IOException {
    	
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(true);

    	File sDir = new File(ATTACHES_DIR);
    	if (!sDir.exists())
    		sDir.mkdirs();

    	int maxSize = 1024 * 1024 * 100;
    	String encType = "UTF-8";
    	ArrayList<TextInfo> textArr=new ArrayList<TextInfo>();
    	
    	MultipartRequest multipartRequest = new MultipartRequest(request, ATTACHES_DIR, maxSize, encType,
    	    	new DefaultFileRenamePolicy());

    	File file = multipartRequest.getFile("file");
    	
    	int len=0;
		String str="";
		String mainTxt="";
		String tempTxt[]=new String[3]; 
		

		//for DB connection
		ServletContext sc = getServletContext();
		Connection conn = (Connection)sc.getAttribute("DBconnection");
		
    	try {
			FileInputStream ins = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
			DBUtils db = new DBUtils();
			String title = "";
			String author = "";
			int id;
			
			while(true) {
				TextInfo nt=new TextInfo();
				str = reader.readLine();
				if (str == null) break;
				mainTxt+=str+"\n";
				
				if(str.contains(":")) {//텍스트에서 화자 제거
					tempTxt=str.split(":");
					nt.setSpeaker(tempTxt[0]);
					nt.setText(tempTxt[1]);
				}
				else {
					nt.setSpeaker("해설");
					nt.setText(str);
					if(len == 0) // 제목 뽑기
					{
						title = str;
					}
					if(len == 1) // 작가 뽑기
					{
						author = str;
						id = db.insertStory(conn, title, author);
						session.setAttribute("story_id", id);
					}
				}
				textArr.add(nt);
				len++;
			}
			
			reader.close();
			ins.close();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
    	   
    	session.setAttribute("mainTxt", mainTxt);
    	session.setAttribute("textInfo", textArr);
    	
    	
    	RequestDispatcher rd = request.getRequestDispatcher("/setting.jsp");
        rd.forward(request, response);
    }
}