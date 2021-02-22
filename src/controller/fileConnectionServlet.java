package controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import model.*;

@WebServlet("/fileConnection")
public class fileConnectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public fileConnectionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		JSONArray resultJson = new JSONArray();
		resultJson = (JSONArray) session.getAttribute("resultJson");
		int index = (int) session.getAttribute("i");
		
		if(index == resultJson.size() - 1) {
			RequestDispatcher rd = request.getRequestDispatcher("/final.jsp");
	        rd.forward(request, response);
	        return;
		}
		else {
			session.setAttribute("i", index + 1);
		}
		
        try {
            URL url = new URL("http://220.69.171.32:5000/tts"); //음성합성기 URL 넣기
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true); //출력 가능한 상태로 만들기
            
            	
            	// post request
                JSONObject postParams = (JSONObject) resultJson.get(index);
                String result = postParams.toString();
                System.out.println(result);
                OutputStream wr = con.getOutputStream();
                byte[] input = result.getBytes("UTF-8");
                wr.write(input, 0, input.length);
                
                int responseCode = con.getResponseCode();
                System.out.println(responseCode);
                BufferedReader br;
                if(responseCode==200) { // 정상 호출되면 결과 받기
                    InputStream is = con.getInputStream();
                    int read = 0;
                    byte[] bytes = new byte[1024];
                    String path = getServletContext().getRealPath("/");
                    System.out.println(path);
                    
            		File fileSaveDir = new File(path+"/output/");
            		String finalPath = fileSaveDir.getPath();
            		session.setAttribute("path", finalPath);
            		// 파일 경로 없으면 생성
            		if (!fileSaveDir.exists()) {
            			fileSaveDir.mkdirs();
            		}
            		
            		File f = new File(fileSaveDir, index + ".wav");
                    f.createNewFile();
                    
                    OutputStream outputStream = new FileOutputStream(f);
                    while ((read =is.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, read); //wav 파일 생성
                    }
                    System.out.println("생성!");
                    is.close();
                } 
                
                else {  // 오류 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    String inputLine;
                    StringBuffer response1 = new StringBuffer();
                    while ((inputLine = br.readLine()) != null) {
                        response1.append(inputLine);
                    }
                    br.close();
                    System.out.println(response1.toString());
                }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        RequestDispatcher rd = request.getRequestDispatcher("/fileConnection");
        rd.include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
