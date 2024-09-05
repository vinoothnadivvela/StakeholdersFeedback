import java.awt.Color;
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
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

@WebServlet("/Question49")
public class Question49 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private int a1 = 0, a5 = 0, b1 = 0, b5 = 0;

    private JFreeChart createBarChart(int a, int b) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(a, "Responses", "Yes");
        dataset.addValue(b, "Responses", "No");
        return ChartFactory.createBarChart(
                "Response Analysis",
                "Category",
                "Count",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );
    }

    private byte[] chartToBytes(JFreeChart chart) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ChartUtilities.writeChartAsPNG(outputStream, chart, 800, 400);
        return outputStream.toByteArray();
    }

    private DefaultPieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("YES", a5);
        dataset.setValue("NO", b5);
        return dataset;
    }

    private String encodeToString(BufferedImage image) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bos);
        byte[] imageBytes = bos.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }


protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    out.println("<html><body><center><h3>Faculty responses</h3></center></body></html>");
    String jdbcURL = "jdbc:mysql://localhost:3306/feedback_db";
    String dbUser = "root";
    String dbPassword = "root";

    Connection con = null;
    PreparedStatement ps1 = null;
    ResultSet rs1 = null;

    try {
        response.setContentType("text/html");
        Class.forName("com.mysql.jdbc.Driver");  // Ensure you have the correct driver class name
        con = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

        String question1 = "SELECT SUM(CASE WHEN Category = 'YES' THEN 1 ELSE 0 END) AS Yes, "
                + "SUM(CASE WHEN Category = 'NO' THEN 1 ELSE 0 END) AS No "
                + "FROM (SELECT response9 AS Category FROM parent_feedback ) AS Responses";
        ps1 = con.prepareStatement(question1);
        rs1 = ps1.executeQuery();
        if (rs1.next()) {
            a1 = rs1.getInt("Yes");
            b1 = rs1.getInt("No");
        }

        // Log dataset values
        out.println("<p>Bar Chart Data - Yes: " + a1 + ", No: " + b1 + "</p>");

        out.println("<div style=\"width: 70%; float: left;\">"); // Add this line
        JFreeChart chart1 = createBarChart(a1, b1);
        CategoryPlot plot = (CategoryPlot) chart1.getPlot();
        plot.getRenderer().setSeriesPaint(0, new Color(0, 128, 0)); // Green for "Yes"
        plot.getRenderer().setSeriesPaint(1, new Color(255, 0, 0)); // Red for "No"
        if (chart1 != null) {
            chart1.setTitle("Are there specific ways to infuse research and innovation opportunities into the curriculum?");
            byte[] imageBytes1 = chartToBytes(chart1);
            String base64Image1 = Base64.getEncoder().encodeToString(imageBytes1);
            out.println("<img src='data:image/png;base64," + base64Image1 + "' />");
            out.println("<br><br>");
        } else {
            out.println("<p>Error: Bar chart creation failed.</p>");
        }
        out.println("</div>"); // Add this line

        out.println("<div style=\"width: 30%; float: right;\">"); // Add this line
        out.println("<h3>Comments:</h3>");
        
        String commentQuery = "SELECT comments9 FROM parent_feedback";
        PreparedStatement psComments = con.prepareStatement(commentQuery);
        ResultSet rsComments = psComments.executeQuery();
        while (rsComments.next()) {
            String comment = rsComments.getString("comments9");
            out.println("<p>" + comment + "</p>");
        }
        
        out.println("</div>"); // Add this line

        out.println("<br style=\"clear: both;\">"); // Add this line

    } catch (Exception e) {
        e.printStackTrace();
        out.println("<p>Error: " + e.getMessage() + "</p>");
    } finally {
        try {
            if (rs1 != null) rs1.close();
            if (ps1 != null) ps1.close();
            if (con != null) con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
}

