package mainpackage;

import java.util.ArrayList;
import java.util.List;

// Η κλαση του φοιτητη που παταει πανω στη βασικη κλαση Users παιρνοντας ολα τα χαρακτηριστικα της
public class Students extends Users {
    // Εξτρα πραματακια που χρειαζεται αποκλειστικα ενας φοιτητης
    private final int registrationNumber; // Το καναμε final γιατι το μητρωο δεν πρεπει να αλλαξει ποτε μετα την εγγραφη
    private List<Grades> grades;
    
    // Ο constructor του φοιτητη που στην ουσια σεταρει και τα στοιχεια του απλου χρηστη
    public Students(String username, String name, String surname, 
                   String department, int registrationNumber) {
        // Φωναζουμε τον constructor της μαμας κλασης για να φτιαξει τα βασικα
        super(username, name, surname, department);
        this.registrationNumber = registrationNumber;
        this.grades = new ArrayList<>();
    }
    
    // Βαλαμε μονο getter εδω επειδη δεν θελουμε να μπορει κανεις να του αλλαξει το μητρωο
    public int getRegistrationNumber() {
        return registrationNumber;
    }
    
    // Τυπωνουμε ολους τους βαθμους που εχει παρει μεχρι τωρα ο φοιτητης
    public void viewGrades() {
        System.out.println("Βαθμολογίες για τον/την " + getName() + " " + getSurname() + ":");
        if (grades.isEmpty()) {
            System.out.println("Δεν έχουν καταχωρηθεί βαθμολογίες ακόμα.");
        } else {
            for (Grades grade : grades) {
                System.out.println("  " + grade.getCourse().getCourseName() + 
                    ": " + grade.getGrade());
            }
        }
    }
    
    public void addGrade(Grades grade) {
        this.grades.add(grade);
    }
    
    public List<Grades> getGrades() {
        return grades;
    }
}