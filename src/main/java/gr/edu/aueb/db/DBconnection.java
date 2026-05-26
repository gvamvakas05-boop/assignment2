package gr.edu.aueb.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {

    // Στοιχεία σύνδεσης (Το university_db είναι η βάση που φτιάξαμε στο Workbench)
    private static final String URL = "jdbc:mysql://localhost:3306/university_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root"; 
    private static final String PASSWORD = "zaq1xsw2cde3!A"; 

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Φόρτωση του Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Άνοιγμα σύνδεσης
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Ο Driver δεν βρέθηκε στο WEB-INF/lib!");
            e.printStackTrace();
            throw new SQLException(e);
        }
        return connection;
    }
}