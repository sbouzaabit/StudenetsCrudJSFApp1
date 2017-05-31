package fr.wa.autoFormation.jsf.DAO;

import fr.wa.autoFormation.jsf.models.Student;
import fr.wa.autoFormation.jsf.studentController.StudentController;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Said B on 30/05/2017.
 */
public class StudentDAO {

    private static StudentDAO instanceStudentDAO;
    private DataSource dataSource;
    private String jndiName = "java:comp/env/jdbc/studentsdb";

    public static StudentDAO getInstance() throws Exception {
        if (instanceStudentDAO == null){
            instanceStudentDAO = new StudentDAO();
        }
        return instanceStudentDAO;
    }


    private StudentDAO() throws Exception {
        dataSource = getDataSource();
    }

    private DataSource getDataSource() throws NamingException {
        Context context = new InitialContext();
        DataSource theDataSource = (DataSource) context.lookup(jndiName);
        return theDataSource;
    }


    public List<Student> getStudents() throws  Exception{
        List<Student> students = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rst = null;

        try{
            con = dataSource.getConnection();
            st = con.createStatement();
            rst = st.executeQuery("select * from student");

            while(rst.next())
            {
                int id = rst.getInt("stu_id");
                String firstName = rst.getString("first_name");
                String lastName = rst.getString("last_name");
                String email = rst.getString("email");

                Student s = new Student();
                s.setId(id);
                s.setFirstName(firstName);
                s.setLastName(lastName);
                s.setEmail(email);
                students.add(s);
                //System.out.println("student recupéré : "+ s.getId());
            }
            return students;

        }

        finally {
            con.close();

        }

    }


    public Student getStudentById(int id) throws  Exception{
        Student student = null;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rst = null;

        try{
            con = dataSource.getConnection();
            pst = con.prepareStatement("select * from student where stu_id=?");
            pst.setInt(1,id);
            rst = pst.executeQuery();

            if(rst.next())
            {

                String firstName = rst.getString("first_name");
                String lastName = rst.getString("last_name");
                String email = rst.getString("email");

                student = new Student();
                student.setId(id);
                student.setFirstName(firstName);
                student.setLastName(lastName);
                student.setEmail(email);

                //System.out.println("student recupéré : "+ s.getId());
            }
            else {
                throw new Exception("Could not find student with id "+ id);
            }
            return student;
        }

        finally {
            con.close();

        }

    }


    public void add (Student s) throws Exception {
        Connection myConn = null;
        PreparedStatement prSt = null;

        try{
            myConn = dataSource.getConnection();
            prSt = myConn.prepareStatement("Insert into student (first_name,last_name,email) VALUES (?,?,?)");
            prSt.setString(1,s.getFirstName());
            prSt.setString(2,s.getLastName());
            prSt.setString(3,s.getEmail());

            prSt.execute();
        }
        finally {
            myConn.close();

        }

    }

    public void update (Student s) throws Exception {
        Connection myConn = null;
        PreparedStatement prSt = null;

        try{
            myConn = dataSource.getConnection();
            prSt = myConn.prepareStatement("update student set first_name = ?,  last_name = ?, email=? where stu_id=?");
            prSt.setString(1,s.getFirstName());
            prSt.setString(2,s.getLastName());
            prSt.setString(3,s.getEmail());
            prSt.setInt(4, s.getId());

            prSt.execute();
        }
        finally {
            myConn.close();

        }

    }
    public void delete (int id) throws Exception {
        Connection myConn = null;
        PreparedStatement prSt = null;

        try{
            myConn = dataSource.getConnection();
            prSt = myConn.prepareStatement("delete from student where stu_id=?");
            prSt.setInt(1,id );
            prSt.execute();
        }
        finally {
            myConn.close();

        }

    }
}
