<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="gr.edu.aueb.dao.CourseDAO" %>
<%@ page import="gr.edu.aueb.dao.UserDAO" %>
<%@ page import="mainpackage.Courses" %>
<%@ page import="mainpackage.Professors" %>
<%@ page import="mainpackage.Users" %>

<%
    // Έλεγχος ασφαλείας: Επιβεβαίωση ότι ο χρήστης έχει συνδεθεί πριν την εμφάνιση του dashboard
    Users loggedUser = (Users) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Αρχικοποίηση των κλάσεων DAO για την ανάκτηση δεδομένων
    CourseDAO courseDAO = new CourseDAO();
    UserDAO userDAO = new UserDAO();

    // Ανάκτηση των λιστών μαθημάτων και καθηγητών από τη βάση δεδομένων
    List<Courses> coursesList = courseDAO.getAllCourses();
    List<Professors> professorsList = userDAO.getAllProfessors();

    // Λήψη προσωρινών μηνυμάτων ενημέρωσης που ορίστηκαν από το servlet
    String successMsg = (String) session.getAttribute("successMessage");
    String errorMsg = (String) session.getAttribute("errorMessage");

    // Άμεση αφαίρεση των μηνυμάτων από τη συνεδρία ώστε να μην εμφανιστούν ξανά σε περίπτωση ανανέωσης της σελίδας
    if (successMsg != null) session.removeAttribute("successMessage");
    if (errorMsg != null) session.removeAttribute("errorMessage");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard Γραμματείας</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 30px; margin: 0; }
        .header { display: flex; justify-content: space-between; align-items: center; background: #0056b3; color: white; padding: 10px 20px; border-radius: 5px; }
        .header h1 { margin: 0; font-size: 24px; }
        .logout-btn { color: white; text-decoration: none; background: #dc3545; padding: 8px 15px; border-radius: 4px; font-weight: bold; }
        .logout-btn:hover { background: #c82333; }
        
        /* Στυλ για τα μηνύματα ενημέρωσης */
        .alert { padding: 15px; margin-top: 20px; border-radius: 4px; font-weight: bold; }
        .alert-success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-danger { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        
        table { width: 100%; border-collapse: collapse; margin-top: 20px; background: white; box-shadow: 0 4px 6px rgba(0,0,0,0.1); border-radius: 5px; overflow: hidden; }
        th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #f8f9fa; color: #333; border-bottom: 2px solid #ccc; }
        select { padding: 8px; border-radius: 4px; border: 1px solid #ccc; width: 100%; max-width: 250px; box-sizing: border-box; }
        .btn-submit { background: #28a745; color: white; border: none; padding: 8px 14px; border-radius: 4px; cursor: pointer; font-weight: bold; }
        .btn-submit:hover { background: #218838; }
    </style>
</head>
<body>

<div class="header">
    <h1>Σύστημα Γραμματείας (3-Tier)</h1>
    <a href="login.jsp" class="logout-btn">Αποσύνδεση</a>
</div>

<%-- Εμφάνιση του ονόματος του συνδεδεμένου χρήστη δυναμικά από το αντικείμενο της συνεδρίας --%>
<h2>Καλώς ορίσατε, <%= loggedUser.getName() + " " + loggedUser.getSurname() %>!</h2>
<p>Από αυτό το περιβάλλον μπορείτε να επιβλέπετε το πρόγραμμα σπουδών και να αναθέτετε υπεύθυνους καθηγητές στα αντίστοιχα μαθήματα.</p>

<%-- Εμφάνιση μηνυμάτων επιτυχίας ή σφάλματος ανάλογα με το αποτέλεσμα της ενέργειας --%>
<% if (successMsg != null) { %>
    <div class="alert alert-success"><%= successMsg %></div>
<% } %>

<% if (errorMsg != null) { %>
    <div class="alert alert-danger"><%= errorMsg %></div>
<% } %>

<table>
    <thead>
        <tr>
            <th>Κωδικός Μαθήματος</th>
            <th>Τίτλος Μαθήματος</th>
            <th>Υπεύθυνος Καθηγητής</th>
            <th>Ενέργεια Ανάθεσης</th>
        </tr>
    </thead>
    <tbody>
        <%
            if (coursesList != null && !coursesList.isEmpty()) {
                for (Courses c : coursesList) {
        %>
                <tr>
                    <td><strong><%= c.getCourseId() %></strong></td>
                    <td><%= c.getCourseName() %></td>
                    <td>
                        <% if (c.getProfessor() != null) { %>
                            <%= c.getProfessor().getName() + " " + c.getProfessor().getSurname() %>
                        <% } else { %>
                            <span style="color: gray; font-style: italic;">Δεν έχει ανατεθεί</span>
                        <% } %>
                    </td>
                    <td>
                        <form action="SecretaryServlet" method="POST" style="margin:0; display: flex; gap: 10px; align-items: center;">
                            <input type="hidden" name="action" value="assign">
                            <input type="hidden" name="courseCode" value="<%= c.getCourseId() %>">
         
                            <select name="professorId" required>
                                <option value="" disabled selected>Επιλέξτε Καθηγητή...</option>
                                <%
                                    if (professorsList != null && !professorsList.isEmpty()) {
                                        for (Professors p : professorsList) {
                                %>
                                        <option value="<%= p.getProfessorId() %>">
                                            <%= p.getName() + " " + p.getSurname() %> (ID: <%= p.getProfessorId() %>)
                                        </option>
                                <%
                                        }
                                    } else {
                                %>
                                        <option value="" disabled>Δεν βρέθηκαν καθηγητές</option>
                                <%
                                    }
                                %>
                            </select>
      
                            <button type="submit" class="btn-submit">Ανάθεση</button>
                        </form>
                    </td>
                </tr>
        <% 
                }
            } else {
        %>
            <tr>
                <td colspan="4" style="text-align:center; color:red; font-weight: bold; padding: 20px;">
                    Δεν βρέθηκαν διαθέσιμα μαθήματα στη βάση δεδομένων!
                </td>
            </tr>
        <% } %>
    </tbody>
</table>

</body>
</html>