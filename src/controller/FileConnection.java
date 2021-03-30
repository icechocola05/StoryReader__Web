package controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
public class FileConnection extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileConnection() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext sc = getServletContext();
		Connection conn = (Connection)sc.getAttribute("DBconnection");
		DBUtils db = new DBUtils();
		
		ArrayList<File> audioFiles=new ArrayList<File>();
		
		//json 형식의 text table data(session의 attribute) 가져오기
		HttpSession session = request.getSession(true);
		JSONArray resultJson = new JSONArray();
		resultJson = (JSONArray) session.getAttribute("resultJson");
		System.out.println((int) session.getAttribute("i"));
		int index = (int) session.getAttribute("i");
		int story_id=(int)session.getAttribute("story_id");
		System.out.println("hello");
		System.out.println(index);

		if (index== resultJson.size()) {
			RequestDispatcher rd = request.getRequestDispatcher("/setImg.do");
			rd.forward(request, response);
			return;
		} else {
			session.setAttribute("i", index + 1);
		}

		try {
			URL url = new URL("http://220.69.171.32:5000/tts"); // 음성합성기 URL 넣기
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; UTF-8");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true); // 출력 가능한 상태로 만들기

			// post request
			JSONObject postParams = (JSONObject) resultJson.get(index);
			String result = postParams.toString();
			System.out.println(result);
			
			OutputStream wr = con.getOutputStream();
			byte[] input = result.getBytes("UTF-8");
			wr.write(input, 0, input.length);//request json 전송


			int responseCode = con.getResponseCode();
			System.out.println(responseCode);
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출되면 결과 받기
				InputStream is = con.getInputStream();
				int read = 0;
				byte[] bytes = new byte[1024];
				
				String path = getServletContext().getRealPath("output/");
	            System.out.println(path);
	            session.setAttribute("path", path);

	            File fileSaveDir = new File(path);
	            // 파일 경로 없으면 생성
	            if (!fileSaveDir.exists()) {
	               fileSaveDir.mkdirs();
	            }

	            File audioFile = new File(path, index + ".wav");
	            audioFile.createNewFile();

				OutputStream outputStream = new FileOutputStream(audioFile);
				while ((read = is.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read); // wav 파일에 작성 
				}
				db.updateWav(conn, story_id, index, audioFile);
				
				System.out.println("생성!");
				outputStream.close();
				is.close();	
			}

			else { // 오류 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				String inputLine;
				StringBuffer response1 = new StringBuffer();
				while ((inputLine = br.readLine()) != null) {
					response1.append(inputLine);
				}
				br.close();
				//System.out.println(response1.toString());
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/fileConnection");
		rd.include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
