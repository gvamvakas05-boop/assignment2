package gr.edu.aueb.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import gr.edu.aueb.dao.CourseDAO;
import mainpackage.Users;

@WebServlet("/SecretaryServlet")
public class SecretaryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CourseDAO courseDAO = new CourseDAO();

    /**
     * Handles processing requests for secretary operations (Requirement 3.4.4)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Enforce proper encoding constraints for Greek characters
        request.setCharacterEncoding("UTF-8");
        
        // Security Check: Verify that an active session context exists before processing
        HttpSession session = request.getSession();
        Users loggedUser = (Users) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        
        // Fulfills Requirement 3.4.4: Process the assignment logic securely through a backend Servlet
        if ("assign".equals(action)) {
            String courseCode = request.getParameter("courseCode");
            String professorIdStr = request.getParameter("professorId");
            
            if (courseCode != null && professorIdStr != null) {
                try {
                    int profId = Integer.parseInt(professorIdStr);
                    // Commit assignment change to the relational database
                    courseDAO.assignProfessor(courseCode, profId);
                } catch (NumberFormatException e) {
                    System.out.println("--> ΣΦΑΛΜΑ: Μη έγκυρο αναγνωριστικό μορφής καθηγητή.");
                    e.printStackTrace();
                }
            }
        }
        
        // Redirect back to the dashboard view to display refreshed datasets
        response.sendRedirect("secretary_dashboard.jsp");
    }

    /**
     * Prevents manual browser URL access requests from triggering errors
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("secretary_dashboard.jsp");
    }
}