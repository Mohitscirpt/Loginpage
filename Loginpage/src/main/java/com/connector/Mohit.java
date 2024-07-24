package com.connector;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class Mohit extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mysql?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "mohit@77";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
       
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        PrintWriter pw = response.getWriter();
        if (authenticate(username, password)) {
            pw.write("<html>");
            pw.write("<body>");
            pw.write("<h1>Login Successful</h1>");
            pw.write("<p>Welcome " + username + "!</p>");
            pw.write("</body>");
            pw.write("</html>");
        } else {
            pw.write("<html>");
            pw.write("<body>");
            pw.write("<h1>Login Failed</h1>");
            pw.write("<p>Invalid Username/Password</p>");
            pw.write("</body>");
            pw.write("</html>");
        }
    }

    private boolean authenticate(String username, String password) {
        boolean isAuthenticated = false;
        String query = "insert into users values(?,?)";
        
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, username);
            statement.setString(2, password);
            int resultSet = statement.executeUpdate();
            
            if(resultSet > 0 ) {
            	isAuthenticated=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAuthenticated;
    }
}
