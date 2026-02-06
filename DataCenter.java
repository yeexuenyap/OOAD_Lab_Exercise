import java.util.ArrayList;

public class DataCenter {
    
    // Lists to store data
    private static ArrayList<Student> students = new ArrayList<>();
    private static ArrayList<Session> sessions = new ArrayList<>();
    private static ArrayList<Evaluation> evaluations = new ArrayList<>();
    
    // Student methods
    public static void addStudent(Student s) {
        students.add(s);
    }
    
    public static ArrayList<Student> getStudents() {
        return students;
    }
    
    public static Student findStudent(String name) {
        for (Student s : students) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }
    
    // Session methods
    public static void addSession(Session session) {
        sessions.add(session);
    }
    
    public static ArrayList<Session> getSessions() {
        return sessions;
    }
    
    // Evaluation methods
    public static void addEvaluation(Evaluation eval) {
        evaluations.add(eval);
        // Update student score
        Student s = findStudent(eval.getStudentID());
        if (s != null) {
            s.setScores(eval.getProblemClarity(), eval.getMethodology(), 
                       eval.getResults(), eval.getPresentation(), eval.getComments());
        }
    }
    
    public static ArrayList<Evaluation> getEvaluations() {
        return evaluations;
    }
    
    // Find best oral presenter
    public static Student getBestOral() {
        Student best = null;
        int highScore = 0;
        for (Student s : students) {
            if (s.getPresentationType().equals("Oral") && s.getTotalScore() > highScore) {
                highScore = s.getTotalScore();
                best = s;
            }
        }
        return best;
    }
    
    // Find best poster presenter
    public static Student getBestPoster() {
        Student best = null;
        int highScore = 0;
        for (Student s : students) {
            if (s.getPresentationType().equals("Poster") && s.getTotalScore() > highScore) {
                highScore = s.getTotalScore();
                best = s;
            }
        }
        return best;
    }
}
