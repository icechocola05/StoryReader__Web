package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Table;
import model.TextInfo;
import model.makeJson;

/**
 * Servlet implementation class makeJsonServlet
 */
@WebServlet("/makeJsonServlet")
public class makeJsonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public makeJsonServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
	    ArrayList<TextInfo> t=(ArrayList<TextInfo>)session.getAttribute("textInfo");
	    
	   	makeJson jf=new makeJson();
<<<<<<< HEAD
		String str =jf.createJson(t);
		
		session.setAttribute("jsoncheck", str);
		
=======
		jf.createJson(t);
	   	
>>>>>>> feature/server
		RequestDispatcher rd = request.getRequestDispatcher("/result2.jsp");
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
