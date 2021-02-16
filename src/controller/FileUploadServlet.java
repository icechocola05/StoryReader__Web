package controller;
 
import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

import model.Table;
import model.loadText; 
 
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

    	File sDir = new File(ATTACHES_DIR);
    	if (!sDir.exists())
    		sDir.mkdirs();

    	int maxSize = 1024 * 1024 * 100;
    	String encType = "UTF-8";
    	Table t=new Table();
    	
    	MultipartRequest multipartRequest = new MultipartRequest(request, ATTACHES_DIR, maxSize, encType,
    	    	new DefaultFileRenamePolicy());

    	File file = multipartRequest.getFile("file");

    	Table t_res = new loadText().FileRead(file,t);
    	
    	HttpSession session = request.getSession(true);
    	session.setAttribute("table", t_res);
    	
    	
    	RequestDispatcher rd = request.getRequestDispatcher("/setting.jsp");
        rd.forward(request, response);
    }
}