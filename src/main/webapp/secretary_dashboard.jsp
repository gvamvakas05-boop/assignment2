<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="gr.edu.aueb.dao.CourseDAO" %>
<%@ page import="gr.edu.aueb.dao.UserDAO" %>
<%@ page import="mainpackage.Courses" %>
<%@ page import="mainpackage.Professors" %>
<%@ page import="mainpackage.Users" %>

<%
    // Ensure the user is authenticated before allowing dashboard access
    Users loggedUser = (Users) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    /* * ARCHITECTURAL REFACTOR (Requirements 3.3 & 3.4): 
     * The scriptlet handling the database execution for the "assign" action 
     * has been completely removed from this view file. Writing actions are now 
     * securely processed through the controller servlet: SecretaryServlet.java.
     */

    CourseDAO courseDAO = new CourseDAO();
    UserDAO userDAO = new UserDAO();
    
    // Retrieve the courses collection (Fulfills 3.4.2 and 3.4.3 via the JOIN query)
    List<Courses> coursesList = courseDAO.getAllCourses();
    
    // Retrieve the professors list dynamically from the database to replace hardcoded options
    List<Professors> professorsList = userDAO.getAllProfessors();
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
        .logout-btn { color: white; text-decoration: none; background: #dc3545; padding: 8px 15px; border-radius: 4px; }
        .logout-btn:hover { background: #c82333; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; background: white; box-shadow: 0 4px 6px rgba(0,0,0,0.1); border-radius: 5px; overflow: hidden; }
        th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #f8f9fa; color: #333; }
        select { padding: 6px; border-radius: 4px; border: 1px solid #ccc; max-width: 250px; }
        .btn-submit { background: #28a745; color: white; border: none; padding: 6px 12px; border-radius: 4px; cursor: pointer; }
        .btn-submit:hover { background: #218838; }
    </style>
</head>
<body>

<div class="header">
    <h1>Σύστημα Γραμματείας (3-Tier)</h1>
    <a href="login.jsp" class="logout-btn">Αποσύνδεση</a>
</div>

<h2>Καλώς ορίσατε!</h2>
<p>Από εδώ μπορείτε να βλέπετε τα μαθήματα και να αναθέτετε καθηγητές.</p>

<table>
    <thead>
        <tr>
            <th>Κωδικός Μαθήματος</th>
            <th>Τίτλος Μαθήματος</th>
            <th>Υπεύθυνος Καθηγητής</th> <th>Ενέργεια Ανάθεσης</th>
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
                        <form action="SecretaryServlet" method="POST" style="margin:0;">
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
                <td colspan="4" style="text-align:center; color:red;">Δεν βρέθηκαν μαθήματα στη βάση δεδομένων!</td>
            </tr>
        <% } %>
    </tbody>
</table>

</body>
</html>