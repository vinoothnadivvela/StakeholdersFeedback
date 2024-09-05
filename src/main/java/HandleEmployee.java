import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HandleEmployee")
public class HandleEmployee extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter out1=response.getWriter();
    	String company_name = request.getParameter("companyName");
        String regulation = request.getParameter("regulation");
        String authorized_person = request.getParameter("Authorizedperson");
        String response1 = request.getParameter("response1");
        System.out.println(authorized_person+"hello");
       
        String comments1 = request.getParameter("comments1");
        String response2 = request.getParameter("response2");
        String comments2 = request.getParameter("comments2");
        String response3 = request.getParameter("response3");
        String comments3 = request.getParameter("comments3");
        String response4 = request.getParameter("response4");
        String comments4 = request.getParameter("comments4");
        String response5 = request.getParameter("response5");
        String comments5 = request.getParameter("comments5");
        String response6 = request.getParameter("response6");
        String comments6 = request.getParameter("comments6");
        String response7 = request.getParameter("response7");
        String comments7 = request.getParameter("comments7");
        String response8 = request.getParameter("response8");
        String comments8 = request.getParameter("comments8");
        String response9 = request.getParameter("response9");
        String comments9 = request.getParameter("comments9");
        String response10 = request.getParameter("response10");
        String comments10 = request.getParameter("comments10");
        String signature = request.getParameter("signature");

        String jdbcURL = "jdbc:mysql://localhost:3306/feedback_db";
        String dbUser = "root";
        String dbPassword = "root";
        PrintWriter out=response.getWriter();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        
            Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
            String sql = "INSERT INTO employee_feedback(company_name, regulation,authorized_person, response1, comments1, response2, comments2, response3, comments3, response4, comments4, response5, comments5, response6, comments6, response7, comments7, response8, comments8, response9, comments9, response10, comments10, signature) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, company_name);
            statement.setString(2, regulation);
            statement.setString(3, authorized_person);
            statement.setString(4, response1);
            statement.setString(5, comments1);
            statement.setString(6, response2);
            statement.setString(7, comments2);
            statement.setString(8, response3);
            statement.setString(9, comments3);
            statement.setString(10, response4);
            statement.setString(11, comments4);
            statement.setString(12, response5);
            statement.setString(13, comments5);
            statement.setString(14, response6);
            statement.setString(15, comments6);
            statement.setString(16, response7);
            statement.setString(17, comments7);
            statement.setString(18, response8);
            statement.setString(19, comments8);
            statement.setString(20, response9);
            statement.setString(21, comments9);
            statement.setString(22, response10);
            statement.setString(23, comments10);
            statement.setString(24, signature);
            statement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println(e);
        }

        response.sendRedirect("thankyou.html");
    }

    
}