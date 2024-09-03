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

@WebServlet("/HandleFaculty")
public class HandleFaculty extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String faculty_name = request.getParameter("facultyName");
        String academic_year = request.getParameter("academicYear");
        String regulation = request.getParameter("regulation");
        String designation = request.getParameter("Designation");
        String course_code_name = request.getParameter("CourseCode");
        String teaching_experience = request.getParameter("Teachingexperience");

        String response1 = request.getParameter("response1");
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
        PrintWriter out = response.getWriter();
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
            
            String sql = "INSERT INTO faculty_feedback(faculty_name, academic_year, regulation, designation, course_code_name, teaching_experience, response1, comments1, response2, comments2, response3, comments3, response4, comments4, response5, comments5, response6, comments6, response7, comments7, response8, comments8, response9, comments9, response10, comments10, signature) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, faculty_name);
            statement.setString(2, academic_year);
            statement.setString(3, regulation);
            statement.setString(4, designation);
            statement.setString(5, course_code_name);
            statement.setString(6, teaching_experience);
            statement.setString(7, response1);
            statement.setString(8, comments1);
            statement.setString(9, response2);
            statement.setString(10, comments2);
            statement.setString(11, response3);
            statement.setString(12, comments3);
            statement.setString(13, response4);
            statement.setString(14, comments4);
            statement.setString(15, response5);
            statement.setString(16, comments5);
            statement.setString(17, response6);
            statement.setString(18, comments6);
            statement.setString(19, response7);
            statement.setString(20, comments7);
            statement.setString(21, response8);
            statement.setString(22, comments8);
            statement.setString(23, response9);
            statement.setString(24, comments9);
            statement.setString(25, response10);
            statement.setString(26, comments10);
            statement.setString(27, signature);
            statement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println(e);
        }

        response.sendRedirect("thankyou.html");
    }
}