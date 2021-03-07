package controller;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class DBconnection
 *
 */
@WebListener
public class DBconnection implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public DBconnection() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
    	Connection conn=(Connection) sce.getServletContext().getAttribute("DBconnection");
    	try {
    	conn.close();
    	} catch (SQLException e) {
    	e.printStackTrace();
    	}
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	//시작 시 DB 연결
    	Connection conn = null;
		Properties connectionProps = new Properties();
		
		ServletContext sc = sce.getServletContext();
		String DBUrl = sc.getInitParameter("JDBCUrl");
		String DBuser = sc.getInitParameter("DBuser");
		String DBpasswd = sc.getInitParameter("DBpasswd");
		String DBTimeZone = sc.getInitParameter("DBTimeZone");
		
		
		connectionProps.put("user", DBuser);
		connectionProps.put("password", DBpasswd);
		connectionProps.put("serverTimezone", DBTimeZone);
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DBUrl, connectionProps);
			System.out.println("success!");
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sc.setAttribute("DBconnection", conn);
    }
	
}
