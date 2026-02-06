import java.util.ArrayList;

public class DataCenter {
    
    // Lists to store data
    private static ArrayList<Student> students = new ArrayList<>();
    private static ArrayList<Session> sessions = new ArrayList<>();
    private static ArrayList<Evaluation> evaluations = new ArrayList<>();
    private static ArrayList<Evaluator> evaluators = new ArrayList<>();

    // Seed default evaluators so the system has some to work with
    static {
        evaluators.add(new Evaluator("Dr. Ahmad", "Artificial Intelligence"));
        evaluators.add(new Evaluator("Dr. Sarah", "Cybersecurity"));
        evaluators.add(new Evaluator("Dr. Kumar", "Data Science"));
    }
    
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
    
    // Evaluator methods
    public static void addEvaluator(Evaluator ev) {
        evaluators.add(ev);
    }

    public static ArrayList<Evaluator> getEvaluators() {
        return evaluators;
    }

    // Find evaluator by name
    public static Evaluator findEvaluator(String name) {
        for (Evaluator ev : evaluators) {
            if (ev.getName().equals(name)) {
                return ev;
            }
        }
        return null;
    }

    // Find all sessions assigned to a specific evaluator
    public static ArrayList<Session> getSessionsForEvaluator(String evaluatorName) {
        ArrayList<Session> result = new ArrayList<>();
        for (Session s : sessions) {
            if (s.getAssignedEvaluators().contains(evaluatorName)) {
                result.add(s);
            }
        }
        return result;
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
