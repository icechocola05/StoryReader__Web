package controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
		
        try {
            String text = URLEncoder.encode("만나서 반갑습니다.", "UTF-8"); // 13자
            URL url = new URL("220.69.171.32:5000/tts"); //음성합성기 URL 넣기
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            
            // post request
            JSONObject postParams = (JSONObject) resultJson.get(0);
            String result = postParams.toString();
            con.setDoOutput(true); //출력 가능한 상태로 만들기
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(result); //json 보내야함
            wr.flush();
            wr.close();
            
            
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출되면 결과 받기
                InputStream is = con.getInputStream();
                int read = 0;
                byte[] bytes = new byte[1024];
                // 랜덤한 이름으로 mp3 파일 생성
                String tempname = Long.valueOf(new Date().getTime()).toString();
                File f = new File(tempname + ".wav");
                f.createNewFile();
                OutputStream outputStream = new FileOutputStream(f);
                while ((read =is.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read); //wav 파일 생성
                }
                is.close();
            } else {  // 오류 발생
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
        
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
