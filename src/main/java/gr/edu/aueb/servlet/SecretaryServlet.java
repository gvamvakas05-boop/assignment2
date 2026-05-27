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

// Servlet για τη διαχείριση των ενεργειών της γραμματείας, όπως η ανάθεση καθηγητών σε μαθήματα (Απαίτηση 3.4.4).
@WebServlet("/SecretaryServlet")
public class SecretaryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CourseDAO courseDAO = new CourseDAO();

    // Διαχείριση των αιτημάτων POST για την εκτέλεση ενεργειών ανάθεσης.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Ορισμός κωδικοποίησης χαρακτήρων για την υποστήριξη ελληνικών χαρακτήρων στις φόρμες
        request.setCharacterEncoding("UTF-8");
        
        // Έλεγχος ασφαλείας: Επαλήθευση ότι υπάρχει ενεργή συνεδρία χρήστη πριν την εκτέλεση της ενέργειας
        HttpSession session = request.getSession(false);
        Users loggedUser = (session != null) ? (Users) session.getAttribute("loggedUser") : null;
        if (loggedUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        
        // Έλεγχος της επιλεγμένης ενέργειας ανάθεσης
        if ("assign".equals(action)) {
            String courseCode = request.getParameter("courseCode");
            String professorIdStr = request.getParameter("professorId");
            
            // Έλεγχος εγκυρότητας: Επιβεβαίωση ότι τα απαραίτητα στοιχεία δεν είναι κενά
            if (courseCode == null || courseCode.trim().isEmpty() || professorIdStr == null || professorIdStr.trim().isEmpty()) {
                session.setAttribute("errorMessage", "Σφάλμα: Ελλιπή στοιχεία για την ανάθεση μαθήματος.");
                response.sendRedirect("secretary_dashboard.jsp");
                return;
            }

            try {
                int profId = Integer.parseInt(professorIdStr.trim());
                
                // Εκτέλεση της ενημέρωσης στη βάση δεδομένων μέσω της κλάσης DAO
                boolean isSuccess = courseDAO.assignProfessor(courseCode.trim(), profId);
                
                if (isSuccess) {
                    session.setAttribute("successMessage", "Η ανάθεση του καθηγητή στο μάθημα (" + courseCode + ") ολοκληρώθηκε επιτυχώς!");
                } else {
                    session.setAttribute("errorMessage", "Αποτυχία: Η ανάθεση δεν αποθηκεύτηκε στη βάση δεδομένων.");
                }
                
            } catch (NumberFormatException e) {
                System.out.println("--> ΣΦΑΛΜΑ: Μη έγκυρο αναγνωριστικό ID καθηγητή: " + professorIdStr);
                session.setAttribute("errorMessage", "Σφάλμα: Το ID του καθηγητή πρέπει να είναι έγκυρος αριθμός.");
                e.printStackTrace();
            }
        }
        
        // Ανακατεύθυνση πίσω στη σελίδα του dashboard για την εμφάνιση των ανανεωμένων δεδομένων
        response.sendRedirect("secretary_dashboard.jsp");
    }

    // Ανακατεύθυνση των αιτημάτων GET απευθείας στο dashboard της γραμματείας.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("secretary_dashboard.jsp");
    }
}