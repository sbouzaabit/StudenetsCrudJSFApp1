package fr.wa.autoFormation.jsf.jdbc;


import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Said B on 30/05/2017.
 */


@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {

    @Resource(name="jdbc/studentsdb")
    private DataSource ds;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            con = ds.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from student");


            out.print("<html><body><h2>Student Details</h2>");
            out.print("<table border=\"1\" cellspacing=10 cellpadding=5>");
            out.print("<th>Employee ID</th>");
            out.print("<th>First Name</th>");
            out.print("<th>Last Name</th>");
            out.print("<th>Email Name</th>");

            while(rs.next())
            {
                out.print("<tr>");
                out.print("<td>" + rs.getInt("stu_id") + "</td>");
                out.print("<td>" + rs.getString("first_name") + "</td>");
                out.print("<td>" + rs.getString("last_name") + "</td>");
                out.print("<td>" + rs.getString("email") + "</td>");
                out.print("</tr>");
            }
            out.print("</table></body><br/>");

            //lets print some DB information
            out.print("<h3>Database Details</h3>");
            out.print("Database Product: "+con.getMetaData().getDatabaseProductName()+"<br/>");
            out.print("Database Driver: "+con.getMetaData().getDriverName());
            out.print("</html>");

        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                stmt.close();
                con.close();
            } catch (SQLException e) {
                System.out.println("Exception in closing DB resources");
            }

        }
    }

}

