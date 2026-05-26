package mainpackage;

import java.util.List;
import java.util.Scanner;

// Η κεντρικη μας κλαση για να τρεξουμε και να δοκιμασουμε αν παιζει σωστα το συστημα βαθμολογησης
public class CreateUsers {
    
    public static void main(String[] args) {
        System.out.println("=== Σύστημα Βαθμολόγησης Πανεπιστημίου - Λειτουργία Δοκιμής ===\n");
        
        // Φτιαχνουμε μερικα αντικειμενα απο καθε τυπο για αρχη
        System.out.println("Δημιουργία αντικειμένων...");
        
     // Ενας απλος χρηστης 
        Users genericUser = new Users("user001", "Γιάννης", "Παπαδόπουλος", "Πληροφορική");
        System.out.println("Δημιουργήθηκε: " + genericUser.getUserInfo());
        System.out.println("Σύνολο χρηστών: " + Users.getUsersCount());
        
        // Φτιαχνουμε εναν φοιτητη
        Students student1 = new Students("stu001", "Αλίκη", "Κωδτοπουλου", 
                                        "Επιστήμη Υπολογιστών", 2023001);
        System.out.println("\nΔημιουργήθηκε Φοιτητής/τρια: " + student1.getName() + 
            " " + student1.getSurname() + 
            " (Αρ. Μητρώου: " + student1.getRegistrationNumber() + ")");
        
        // Εδω εχουμε εναν καθηγητη
        Professors prof1 = new Professors("prof001", "Δρ. Βασίλης", "Ιωάννου", 
                                         "Επιστήμη Υπολογιστών", "P1001");
        System.out.println("Δημιουργήθηκε Καθηγητής: " + prof1.getName() + 
            " " + prof1.getSurname());
        
        // Και τελος εναν γραμματεα
        Secretaries sec1 = new Secretaries("sec001", "Κατερίνα", "Βασιλείου", 
                                          "Διοίκηση", "S5001");
        System.out.println("Δημιουργήθηκε Γραμματέας: " + sec1.getName() + 
            " " + sec1.getSurname());
        
        System.out.println("\nΣυνολικοί χρήστες που δημιουργήθηκαν: " + Users.getUsersCount());
        
        // Παιρνουμε τα στοιχεια ενος φοιτητη διαδραστικα απο το πληκτρολογιο
        createStudentInteractively();
        
        // Παμε να δουμε αν δουλευουν ολα μαζι σωστα
        demonstrateFunctionality(student1, prof1, sec1);
        
        // Και εδω φορτωνουμε εξτρα φοιτητες απο το εξωτερικο text αρχειο
        System.out.println("\n=== Φόρτωση από Αρχείο ===");
        List<Students> fileStudents = FileHandler.loadStudentsFromFile("students.txt");
        System.out.println("Σύνολο που φορτώθηκαν από το αρχείο: " + fileStudents.size());
    }
    
    // Μεθοδος που ζηταει απο τον χρηστη να βαλει τα στοιχεια ενος νεου φοιτητη και κανει και τον απαραιτητο ελεγχο
    @SuppressWarnings("resource")
    public static void createStudentInteractively() {
        System.out.println("\n=== Διαδραστική Δημιουργία Φοιτητή ===");
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.print("Εισάγετε όνομα χρήστη (username): ");
            String username = scanner.nextLine();
            
            System.out.print("Εισάγετε όνομα: ");
            String name = scanner.nextLine();
            
            System.out.print("Εισάγετε επώνυμο: ");
            String surname = scanner.nextLine();
            
            System.out.print("Εισάγετε τμήμα: ");
            String dept = scanner.nextLine();
            
            System.out.print("Εισάγετε αριθμό μητρώου (μόνο ακέραιος αριθμός): ");
            String regInput = scanner.nextLine();
            
            // Κανουμε ελεγχο εδω για να δουμε αν το μητρωο ειναι οντως αριθμος
            int regNumber = validateRegistrationNumber(regInput);
            
            Students newStudent = new Students(username, name, surname, dept, regNumber);
            System.out.println("\n✓ Ο/Η φοιτητής/τρια δημιουργήθηκε επιτυχώς!");
            System.out.println("  Όνομα: " + newStudent.getName() + " " + newStudent.getSurname());
            System.out.println("  Αρ. Μητρώου: " + newStudent.getRegistrationNumber());
            
        } catch (InvalidRegistrationException e) {
            System.err.println("✗ Σφάλμα: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("✗ Μη αναμενόμενο σφάλμα: " + e.getMessage());
        }
    }
    
    // Αυτη η μεθοδος αναλαμβανει να τσεκαρει αποκλειστικα αν ο αριθμος μητρωου ειναι εγκυρος
    public static int validateRegistrationNumber(String input) throws InvalidRegistrationException {
        try {
            // Αν δεν εγραψε τιποτα πεταμε το δικο μας exception
            if (input == null || input.trim().isEmpty()) {
                throw new InvalidRegistrationException("Ο αριθμός μητρώου δεν μπορεί να είναι κενός");
            }
            
            // Δοκιμαζουμε να μετατρεψουμε το κειμενο σε ακεραιο αριθμο
            int regNum = Integer.parseInt(input);
            
            // Εννοειται πως πρεπει να ειναι θετικος αριθμος το μητρωο
            if (regNum <= 0) {
                throw new InvalidRegistrationException("Ο αριθμός μητρώου πρέπει να είναι θετικός");
            }
            
            return regNum;
            
        } catch (NumberFormatException e) {
            throw new InvalidRegistrationException(
                "Ο αριθμός μητρώου πρέπει να είναι έγκυρος ακέραιος. Βρέθηκε: '" + input + "'", e);
        }
    }
    
    // Μια γρηγορη επιδειξη για το πως συνεργαζονται τα αντικειμενα μεταξυ τους
    public static void demonstrateFunctionality(Students s, Professors p, Secretaries sec) {
        System.out.println("\n=== Επίδειξη Λειτουργικότητας ===");
        
        // Η γραμματεια περναει ενα νεο μαθημα στο συστημα
        Courses course1 = sec.registerCourse("CS101", "Introduction to Programming");
        
        // Βαζουμε τον καθηγητη να το διδαξει
        sec.assignProfessorToCourse(p, course1);
        
        // Ο καθηγητης βαζει τον βαθμο στον φοιτητη
        p.setGrades(s, course1, 85.5);
        
        // Και τελος ο φοιτητης βλεπει τι βαθμο πηρε
        s.viewGrades();
    }
}