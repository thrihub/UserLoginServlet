 

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertUserServlet")
public class InsertUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>Insert User</h2>");
        out.println("<form action='InsertUserServlet' method='post'>");
        out.println("Name: <input type='text' name='name'><br>");
        out.println("Email: <input type='text' name='email'><br>");
        out.println("Password: <input type='password' name='password'><br>");
        out.println("Phone: <input type='text' name='phone'><br>");
        out.println("Date of Birth: <input type='date' name='dob'><br>");
        out.println("Aadhaar: <input type='text' name='aadhar'><br>");
        out.println("<input type='submit' value='Submit'>");
        out.println("</form>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String dob = request.getParameter("dob");
        String aadhar = request.getParameter("aadhar");

        Connection conn = null;
        PreparedStatement stmt = null;

        String url = "jdbc:mysql://localhost:3306/exampleDB";
        String user = "root";
        String dbPassword = "admin";  // Replace with your MySQL password

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, dbPassword);
            String sql = "INSERT INTO Users (name, email, password, phone, dob, aadhar) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, phone);
            stmt.setDate(5, java.sql.Date.valueOf(dob));
            stmt.setString(6, aadhar);
            stmt.executeUpdate();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body align=\"center\" bgcolor=\"violet\">");
            out.println("<h2>Record inserted successfully</h2>");
            out.println("<a href='InsertUserServlet'>Go back</a>");
            out.println("</body></html>");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
