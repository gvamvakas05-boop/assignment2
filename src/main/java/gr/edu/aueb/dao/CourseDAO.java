package gr.edu.aueb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import gr.edu.aueb.db.DBconnection; 
import mainpackage.Courses; 
import mainpackage.Professors;

public class CourseDAO {

    public List<Courses> getAllCourses() {
        List<Courses> coursesList = new ArrayList<>();
        
        // Fulfills Requirement 3.4.3: SQL query with a LEFT JOIN to fetch 
        // the course code, title, and the corresponding professor's name and surname
        String query = "SELECT c.course_code, c.title, u.name AS prof_name, u.surname AS prof_surname " +
                       "FROM courses c " +
                       "LEFT JOIN users u ON c.professor_id = u.id";

        try (Connection conn = DBconnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String code = rs.getString("course_code");
                String title = rs.getString("title");
                String profName = rs.getString("prof_name");
                String profSurname = rs.getString("prof_surname");
                
                Courses course = new Courses(code, title);
                
                // If a professor is assigned to this course, instantiate a Professors object 
                // and bind it to the course
                if (profName != null && profSurname != null) {
                    Professors prof = new Professors(null, profName, profSurname, null, null);
                    course.setProfessor(prof);
                }
                
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