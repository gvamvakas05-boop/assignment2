package mainpackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Αυτη η κλαση ειναι υπευθυνη για να διαβαζει μαζικα τους φοιτητες απο ενα εξωτερικο αρχειο
public class FileHandler {
    
    // Διαβαζουμε το αρχειο γραμμη γραμμη και περιμενουμε τα στοιχεια χωρισμενα με ερωτηματικο
    public static List<Students> loadStudentsFromFile(String filename) {
        List<Students> students = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            
            while ((line = br.readLine()) != null) {
                lineNumber++;
                
                // Αν πετυχουμε αδεια γραμμη απλα την προσπερναμε και παμε στην επομενη
                if (line.trim().isEmpty()) continue;
                
                try {
                    Students student = parseStudentLine(line);
                    students.add(student);
                    System.out.println("Φορτώθηκε: " + student.getName() + " από τη γραμμή " + lineNumber);
                    
                } catch (InvalidRegistrationException e) {
                    System.err.println("Σφάλμα στη γραμμή " + lineNumber + ": " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Σφάλμα μορφοποίησης στη γραμμή " + lineNumber + ": " + line);
                }
            }
            
        } catch (IOException e) {
            System.err.println("Σφάλμα αρχείου: " + e.getMessage());
        }
        
        return students;
    }
    
    // Εδω κοβουμε τη γραμμη στα κομματια της για να τραβηξουμε τα στοιχεια του φοιτητη
    private static Students parseStudentLine(String line) throws InvalidRegistrationException {
        String[] parts = line.split(";"); // Χωριζουμε τα δεδομενα ακριβως εκει που εχει το ερωτηματικο
        
        if (parts.length != 5) {
            throw new IllegalArgumentException("Αναμένονταν 5 πεδία, βρέθηκαν " + parts.length);
        }
        
        String username = parts[0].trim();
        String name = parts[1].trim();
        String surname = parts[2].trim();
        String dept = parts[3].trim();
        
        // Ελεγχουμε παλι αν ο αριθμος μητρωου ειναι σωστος αριθμος και οχι γραμματα
        int regNum;
        try {
            regNum = Integer.parseInt(parts[4].trim());
        } catch (NumberFormatException e) {
            throw new InvalidRegistrationException("Μη έγκυρος αριθμός μητρώου: " + parts[4]);
        }
        
        return new Students(username, name, surname, dept, regNum);
    }
}