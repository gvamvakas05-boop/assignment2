package gr.edu.aueb.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Κλάση για τη διαχείριση της σύνδεσης με τη βάση δεδομένων στο μοντέλο 3-tier (Απαίτηση 1.2).
public class DBconnection {

    // Προκαθορισμένα στοιχεία σύνδεσης για τη βάση δεδομένων university_db
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/university_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String DEFAULT_USER = "root"; 
    private static final String DEFAULT_PASSWORD = "zaq1xsw2cde3!A"; 

    // Στατικό block για την εξασφάλιση της φόρτωσης του Driver της MySQL κατά την εκκίνηση της εφαρμογής
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("--> DB Driver: Ο com.mysql.cj.jdbc.Driver φορτώθηκε επιτυχώς.");
        } catch (ClassNotFoundException e) {
            System.err.println("--> ΚΡΙΣΙΜΟ ΣΦΑΛΜΑ: Ο MySQL JDBC Driver δεν εντοπίστηκε στο WEB-INF/lib!");
            e.printStackTrace();
        }
    }

    // Μέθοδος για τη δημιουργία και την επιστροφή μιας ενεργής σύνδεσης με τη βάση δεδομένων.
    public static Connection getConnection() throws SQLException {
        // Έλεγχος για ύπαρξη μεταβλητών περιβάλλοντος, με εναλλακτική χρήση των προκαθορισμένων τοπικών τιμών
        String url = System.getenv("DB_URL") != null ? System.getenv("DB_URL") : DEFAULT_URL;
        String user = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : DEFAULT_USER;
        String password = System.getenv("DB_PASS") != null ? System.getenv("DB_PASS") : DEFAULT_PASSWORD;

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("--> ΣΦΑΛΜΑ ΣΥΝΔΕΣΗΣ: Αποτυχία σύνδεσης με τη βάση δεδομένων: " + e.getMessage());
            throw e;
        }
    }
}