package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * Servlet implementation class makeJsonServlet
 */
@WebServlet("/makeJsonServlet")
public class MakeJson extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeJson() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		JSONArray jsonArray=new JSONArray();
	   
		//DB 정보로 json 만들기
		ServletContext sc = getServletContext();
		Connection con = (Connection)sc.getAttribute("DBconnection");
		
		int story_id = (int) session.getAttribute("story_id");
		
		try {
			String sentq = "SELECT * FROM sentence WHERE story_id=?";
			PreparedStatement sentps = con.prepareStatement(sentq);
			sentps.setInt(1, story_id);
			ResultSet rsSent = sentps.executeQuery();
			
			while(rsSent.next()) {
				String text = rsSent.getString(2);
				int voiceId = rsSent.getInt(6);
				int emotionId = rsSent.getInt(7);
				float intensityVal = rsSent.getFloat(8);
				
				//JSON 생성
				JSONObject jsonObject=new JSONObject(); 
				JSONObject voiceInfo=new JSONObject();
				JSONObject emoInfo=new JSONObject();
				
				jsonObject.put("text", text);
				jsonObject.put("lang", "ko");
	
				voiceInfo.put("name", voiceId);
				voiceInfo.put("gender", "");
				voiceInfo.put("age", ""); 
				voiceInfo.put("variant", "");
				voiceInfo.put("onvoicefailure", "priorityselect");
				jsonObject.put("voice", voiceInfo);
		        
				emoInfo.put("name", emotionId);
				emoInfo.put("value", intensityVal);
				jsonObject.put("emotionInfo", emoInfo);
				
		        jsonArray.add(jsonObject);
		        
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		session.setAttribute("resultJson", jsonArray);
		session.setAttribute("i", 0);
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
