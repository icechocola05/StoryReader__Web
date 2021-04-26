package controller;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/uploadFile")
public class UploadFile extends HttpServlet {

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
    	
    	MultipartRequest multipartRequest = new MultipartRequest(request, ATTACHES_DIR, maxSize, encType,
    	    	new DefaultFileRenamePolicy());

    	File file = multipartRequest.getFile("file");
    	
    	int len=0;
		
    	String str="";
		String title = "";
		String mainTxt="";
		
		System.out.println(request.getParameter("write"));
	
		
		try {
			FileInputStream ins = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
			
			while(true) {
				str = reader.readLine();
				
				if (str == null) break;
				if(len == 0) // 제목 뽑기
				{
					title = str;
				}else {
					mainTxt+=str+"\n";
				}
				
				len++;
			}
			
			reader.close();
			ins.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		

		session.setAttribute("bookname", title);
		session.setAttribute("mainTxt", mainTxt);
		
    	RequestDispatcher rd = request.getRequestDispatcher("/confirm.jsp");
        rd.forward(request, response);
    }
}