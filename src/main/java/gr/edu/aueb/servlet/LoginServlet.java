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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Έλεγχος στη βάση μέσω του DAO
        Users user = userDAO.authenticate(username, password);

        if (user != null) {
            // Επιτυχία: Κρατάμε τον χρήστη στο Session
            HttpSession session = request.getSession();
            session.setAttribute("loggedUser", user);
            
            // Ανακατεύθυνση στο Dashboard της Γραμματείας
            response.sendRedirect("secretary_dashboard.jsp");
        } else {
            // Αποτυχία: Επιστροφή στο login με μήνυμα λάθους
            request.setAttribute("errorMessage", "Λάθος Username ή Password!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}