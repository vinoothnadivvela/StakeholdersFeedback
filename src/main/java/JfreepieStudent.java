import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

@WebServlet("/JfreepieStudent")
public class JfreepieStudent extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private int a1 = 0, a5 = 0, b1 = 0, b5 = 0;

   

    private byte[] chartToBytes(JFreeChart chart) throws IOException {
        // Convert chart to byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ChartUtilities.writeChartAsPNG(outputStream, chart, 800, 400);
        return outputStream.toByteArray();
    }

    private DefaultPieDataset createDataset() {
        // Create a dataset for the pie chart
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("YES", a5);
        dataset.setValue("NO", b5);
        return dataset;
    }

    private String encodeToString(BufferedImage image) throws IOException {
        // Convert BufferedImage to Base64 string
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bos);
        byte[] imageBytes = bos.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<html><body><center><h3>Student Responses</h3></center></body></html>");
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<style>");
        out.println(".dropbtn {");
        out.println("margin-right:200px;");
        out.println("  background-color: #4CAF50;");
        out.println("  color: white;");
        out.println("  padding: 16px;");
        out.println("  font-size: 16px;");
        out.println("  border: none;");
        out.println("  cursor: pointer;");
        out.println("}");
        
        out.println(".dropdown {");
        out.println("  position: relative;");
        out.println("  display: inline-block;");
        out.println("}");
        
        out.println(".dropdown-content {");
        out.println("margin-right:200px;");
        out.println("  display: none;");
        out.println("  position: absolute;");
        out.println("  right: 0;");
        out.println("  background-color: #f9f9f9;");
        out.println("  min-width: 160px;");
        out.println("  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);");
        out.println("  z-index: 1;");
        out.println("}");
        
        out.println(".dropdown-content a {");
        out.println("  color: black;");
        out.println("  padding: 16px 16px;");
        out.println("  text-decoration: none;");
        out.println("  display: block;");
        out.println("}");
        
        out.println(".dropdown-content a:hover {background-color: #f1f1f1;}");
        
        out.println(".dropdown:hover .dropdown-content {");
        out.println("  display: block;");
        out.println("}");
        
        out.println(".dropdown:hover .dropbtn {");
        out.println("  background-color: #3e8e41;");
        out.println("}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        
        out.println("<div class=\"dropdown\" style=\"float:right;\">");
        out.println("  <button class=\"dropbtn\">Choose Questions to get the Responses</button>");
        out.println("  <div class=\"dropdown-content\">");
        out.println("    <a href=\"Question11\">Do you support the proposed initiative to revise the Curriculum?</a>");
        out.println("    <a href=\"Question12\">Are there any specific topics that you believe should be added to the curriculum? If yes, please specify</a>");
        out.println("    <a href=\"Question13\">Are there any specific topics that you believe should be removed from the curriculum? If yes, please specify</a>");
        out.println("    <a href=\"Question14\">Do you recommend introducing new courses? If yes, please provide details</a>");
        out.println("    <a href=\"Question15\">Is it important to incorporate practical experiences or hands on projects into the curriculum?</a>");
        out.println("    <a href=\"Question16\">Are there particular soft skills (communication, teamwork, etc.) that you believe should be emphasized the curriculum?</a>");
        out.println("    <a href=\"Question17\">Should the Curriculum include courses that encourage interdisciplinary learning? if yes, please provide suggestions for potential interdisciplinary integration</a>");
        out.println("    <a href=\"Question18\">Does the curriculum foster stronger industry-academia collaboration?</a>");
        out.println("    <a href=\"Question19\">Are there specific ways to infuse research and innovation opportunities into the curriculum?</a>");
        out.println("    <a href=\"Question20\">Any additional suggestions, ideas or comments regarding the curriculum revision?</a>");
      
        out.println("  </div>");
        out.println("</div>");
        
        out.println("</body>");
        out.println("</html>");
        String jdbcURL = "jdbc:mysql://localhost:3306/feedback_db";
        String dbUser = "root";
        String dbPassword = "root";
        

        Connection con = null;
        PreparedStatement ps1 = null, ps5 = null;
        ResultSet rs1 = null, rs5 = null;

        try {
            response.setContentType("text/html");
            Class.forName("com.mysql.jdbc.Driver"); // Ensure you have the correct driver class name
            con = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            // Query to get the count of 'YES' and 'NO' responses for the bar chart
            String question1 = "SELECT SUM(CASE WHEN Category = 'YES' THEN 1 ELSE 0 END) AS Yes, "
                    + "SUM(CASE WHEN Category = 'NO' THEN 1 ELSE 0 END) AS No "
                    + "FROM (SELECT response1 AS Category FROM student_feedback UNION ALL "
                    + "SELECT response2 AS Category FROM student_feedback UNION ALL "
                    + "SELECT response3 AS Category FROM student_feedback UNION ALL "
                    + "SELECT response4 AS Category FROM student_feedback UNION ALL "
                    + "SELECT response5 AS Category FROM student_feedback UNION ALL "
                    + "SELECT response6 AS Category FROM student_feedback UNION ALL "
                    + "SELECT response7 AS Category FROM student_feedback UNION ALL "
                    + "SELECT response8 AS Category FROM student_feedback UNION ALL "
                    + "SELECT response9 AS Category FROM student_feedback UNION ALL "
                    + "SELECT response10 FROM student_feedback) AS Responses";
            ps1 = con.prepareStatement(question1);
            rs1 = ps1.executeQuery();
            if (rs1.next()) {
                a1 = rs1.getInt("Yes");
                b1 = rs1.getInt("No");
            }

           
            

            // Query to get the count of 'YES' and 'NO' responses for the pie chart
            String question2 = "SELECT SUM(CASE WHEN Category = 'YES' THEN 1 ELSE 0 END) AS Yes, "
                    + "SUM(CASE WHEN Category = 'NO' THEN 1 ELSE 0 END) AS No "
                    + "FROM (SELECT response1 AS Category FROM student_feedback UNION ALL "
                    + "SELECT response2 AS Category FROM student_feedback UNION ALL "
                    + "SELECT response3 AS Category FROM student_feedback UNION ALL "
                    + "SELECT response4 AS Category FROM student_feedback UNION ALL "
                    + "SELECT response5 AS Category FROM student_feedback UNION ALL "
                    + "SELECT response6 AS Category FROM student_feedback UNION ALL "
                    + "SELECT response7 AS Category FROM student_feedback UNION ALL "
                    + "SELECT response8 AS Category FROM student_feedback UNION ALL "
                    + "SELECT response9 AS Category FROM student_feedback UNION ALL "
                    + "SELECT response10 FROM student_feedback) AS Responses";
            ps5 = con.prepareStatement(question2);
            rs5 = ps5.executeQuery();
            if (rs5.next()) {
                a5 = rs5.getInt("Yes");
                b5 = rs5.getInt("No");
            }

            // Log dataset values for the pie chart
            out.println("<p>Pie Chart Data - Yes: " + a5 + ", No: " + b5 + "</p>");

            DefaultPieDataset dataset = createDataset();
            JFreeChart chart = ChartFactory.createPieChart("Development at VVIT", dataset, true, true, false);
            if (chart != null) {
                BufferedImage chartImage = chart.createBufferedImage(800, 400);
                out.println("<img src='data:image/png;base64," + encodeToString(chartImage) + "' />");
                out.println("<br><br>");
            } else {
                out.println("<p>Error: Pie chart creation failed.</p>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error: " + e.getMessage() + "</p>");
        } finally {
            try {
                if (rs1 != null) rs1.close();
                if (rs5 != null) rs5.close();
                if (ps1 != null) ps1.close();
                if (ps5 != null) ps5.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

       

        out.println("<br><br>");
        out.println("<p>Comments:</p>");
        out.println("<ul>");
        out.println("<li>Bar chart displays the count of 'YES' and 'NO' responses from the feedback data.</li>");
        out.println("<li>Pie chart visualizes the proportion of 'YES' and 'NO' responses for development feedback.</li>");
        out.println("<li>Ensure the database connection details are correct and the necessary driver is included in the project.</li>");
        out.println("<li>Check for any SQL exceptions and handle them appropriately to avoid runtime errors.</li>");
        out.println("</ul>");
    }
}