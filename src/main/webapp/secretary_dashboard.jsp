<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="gr.edu.aueb.dao.CourseDAO" %>
<%@ page import="mainpackage.Courses" %>
<%@ page import="mainpackage.Users" %>

<%
    Users loggedUser = (Users) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    CourseDAO courseDAO = new CourseDAO();
    
    String action = request.getParameter("action");
    if ("assign".equals(action)) {
        String courseCode = request.getParameter("courseCode");
        int profId = Integer.parseInt(request.getParameter("professorId"));
        
        courseDAO.assignProfessor(courseCode, profId);
        
        response.sendRedirect("secretary_dashboard.jsp");
        return;
    }

    List<Courses> coursesList = courseDAO.getAllCourses();
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
        select { padding: 6px; border-radius: 4px; border: 1px solid #ccc; }
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
                        <form action="secretary_dashboard.jsp" method="POST" style="margin:0;">
                            <input type="hidden" name="action" value="assign">
                            <input type="hidden" name="courseCode" value="<%= c.getCourseId() %>">
                            
                            <select name="professorId">
                                <option value="1">Νίκος Οικονόμου (ID: 1)</option>
                                <option value="2">Μαρία Γεωργίου (ID: 2)</option>
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
                <td colspan="3" style="text-align:center; color:red;">Δεν βρέθηκαν μαθήματα στη βάση δεδομένων!</td>
            </tr>
        <% } %>
    </tbody>
</table>

</body>
</html>