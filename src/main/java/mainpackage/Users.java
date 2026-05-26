package mainpackage;

// Η βασικη κλαση του συστηματος που εχει τα κοινα στοιχεια ολων των χρηστων
public class Users {
    // Κρυβουμε τα πεδια μας εδω για ασφαλεια (το γνωστο encapsulation)
    private String username;
    private String name;
    private String surname;
    private String department;
    
    // Ενας κοινος μετρητης για να ξερουμε ποσους χρηστες φτιαξαμε συνολικα στο συστημα
    private static int usersCounter = 0;
    
    // Ο απλος constructor που το μονο που κανει ειναι να ανεβαζει τον μετρητη
    public Users() {
        usersCounter++;
    }
    
    // Ο constructor που του περναμε ολα τα στοιχεια του χρηστη με τη μια
    public Users(String username, String name, String surname, String department) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.department = department;
        usersCounter++;
    }
    
    // Κλασικα πραγματα βαζουμε getters και setters για να μπορουμε να διαβαζουμε και να αλλαζουμε τα στοιχεια
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    // Επιστρεφει το ποσοι χρηστες εχουν δημιουργηθει μεχρι στιγμης
    public static int getUsersCount() {
        return usersCounter;
    }
    
    // Μας δινει πισω ενα ωραιο string με τις πληροφοριες του χρηστη για να τις τυπωνουμε ευκολα
    public String getUserInfo() {
        return String.format("Χρήστης: %s %s (%s), Τμήμα: %s", 
            name, surname, username, department);
    }
}