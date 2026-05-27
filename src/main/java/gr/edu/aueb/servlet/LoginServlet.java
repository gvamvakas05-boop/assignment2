package gr.edu.aueb.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import gr.edu.aueb.dao.UserDAO;
import mainpackage.Users;

// Servlet για τη διαχείριση της διαδικασίας αυθεντικοποίησης (login) των χρηστών (Απαίτηση 3.4.1).
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserDAO userDAO = new UserDAO();

    // Διαχείριση των αιτημάτων POST κατά την υποβολή της φόρμας σύνδεσης.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Ορισμός κωδικοποίησης χαρακτήρων για την ορθή υποστήριξη ελληνικών χαρακτήρων
        request.setCharacterEncoding("UTF-8");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Έλεγχος εγκυρότητας: Αν κάποιο πεδίο είναι κενό, επιστρέφεται κατάλληλο μήνυμα σφάλματος
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Παρακαλώ συμπληρώστε όλα τα πεδία!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // Αφαίρεση τυχόν κενών διαστημάτων από την αρχή και το τέλος των στοιχείων εισόδου
        username = username.trim();
        password = password.trim();

        // Έλεγχος των στοιχείων σύνδεσης στη βάση δεδομένων μέσω της κλάσης DAO
        Users user = userDAO.authenticate(username, password);

        if (user != null) {
            // Ακύρωση της προηγούμενης συνεδρίας αν υπάρχει, για την αποτροπή επιθέσεων τύπου session fixation
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            
            // Δημιουργία νέας ασφαλούς συνεδρίας (Session) για τον συνδεδεμένο χρήστη
            HttpSession session = request.getSession(true);
            session.setAttribute("loggedUser", user);
            
            // Ανακατεύθυνση του χρήστη στο dashboard της γραμματείας
            response.sendRedirect("secretary_dashboard.jsp");
        } else {
            // Σε περίπτωση αποτυχίας, επιστρέφεται μήνυμα σφάλματος και γίνεται προώθηση στη σελίδα σύνδεσης
            request.setAttribute("errorMessage", "Λάθος Username, Password ή μη εξουσιοδοτημένος ρόλος!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    // Ανακατεύθυνση των αιτημάτων GET απευθείας στη σελίδα σύνδεσης για λόγους ασφαλείας.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}