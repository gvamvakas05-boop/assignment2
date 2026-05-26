package gr.edu.aueb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import gr.edu.aueb.db.DBconnection; 
import mainpackage.Courses; 

public class CourseDAO {

    public List<Courses> getAllCourses() {
        List<Courses> coursesList = new ArrayList<>();
        
        String query = "SELECT c.course_code, c.title " +
                       "FROM courses c";

        try (Connection conn = DBconnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String code = rs.getString("course_code");
                String title = rs.getString("title");
                
                // Καλείται ο constructor σου: πρώτα το ID (code) και μετά το Name (title)
                Courses course = new Courses(code, title);
                coursesList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coursesList;
    }

    public boolean assignProfessor(String courseCode, int professorId) {
        String query = "UPDATE courses SET professor_id = ? WHERE course_code = ?";
        
        try (Connection conn = DBconnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, professorId);
            ps.setString(2, courseCode);
            
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}