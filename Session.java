import java.util.ArrayList;

public class Session {
    
    private String sessionID;
    private String date;
    private String time;
    private String venue;
    private String type;
    private ArrayList<String> assignedStudents;
    private ArrayList<String> assignedEvaluators;
    
    // Constructor - creates a new session with the given details
    public Session(String id, String date, String time, String venue, String type) {
        this.sessionID = id;
        this.date = date;
        this.time = time;
        this.venue = venue;
        this.type = type;
        this.assignedStudents = new ArrayList<>();
        this.assignedEvaluators = new ArrayList<>();
    }
    
    // Assigns a student to this session and updates their record
    public void assignStudent(String name) {
        if (!assignedStudents.contains(name)) {
            assignedStudents.add(name);
            // Update student record
            Student s = DataCenter.findStudent(name);
            if (s != null) {
                s.setAssignment(date + " " + time, venue, "N/A");
            }
        }
    }
    
    // Assigns an evaluator to this session
    public void assignEvaluator(String name) {
        if (!assignedEvaluators.contains(name)) {
            assignedEvaluators.add(name);
        }
    }
    
    // Getters
    public String getSessionID() { return sessionID; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getVenue() { return venue; }
    public String getType() { return type; }
    public ArrayList<String> getAssignedStudents() { return assignedStudents; }
    public ArrayList<String> getAssignedEvaluators() { return assignedEvaluators; }
}
