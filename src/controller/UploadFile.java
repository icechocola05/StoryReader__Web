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

	private static final long serialVersionUID = 1L;
	private static final String ATTACHES_DIR = "C:\\attaches";
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        int maxSize = 1024 * 1024 * 100;
    	String encType = "UTF-8";

        //텍스트 파일 서버에 저장
    	File sDir = new File(ATTACHES_DIR);
    	if (!sDir.exists())
    		sDir.mkdirs();
    	
    	//저장한 파일 업로드
    	MultipartRequest multipartRequest = new MultipartRequest(request, ATTACHES_DIR, maxSize, encType,
    	    	new DefaultFileRenamePolicy());

    	File file = multipartRequest.getFile("file"); //업로드한 파일 file 객체로 받기
    	
    	String str="";
		String title = "";
		String mainTxt="";
	
		try {
			//텍스트 파일 문장으로 읽기
			FileInputStream ins = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
			
			int len=0; //첫 문장인지 판별
			while(true) {
				str = reader.readLine();
				
				if (str == null) break;
				
				if(len == 0) // 첫 문장은 제목
					title = str;
				else
					mainTxt+=str+"\n";
				len++;
			}
			
			reader.close();
			ins.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		request.setAttribute("title", title); //책 제목
		request.setAttribute("mainTxt", mainTxt); //책 본문
		
    	RequestDispatcher rd = request.getRequestDispatcher("/fileConfirm.jsp");
        rd.forward(request, response);
    }
}