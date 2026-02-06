public class Evaluation {
    private String studentID;
    private int problemClarity;
    private int methodology;
    private int results;
    private int presentation;
    private String comments;

    // Constructor
    public Evaluation(String id, int s1, int s2, int s3, int s4, String comm) {
        this.studentID = id;
        this.problemClarity = s1;
        this.methodology = s2;
        this.results = s3;
        this.presentation = s4;
        this.comments = comm;
    }

    // Formula: Total = S1 + S2 + S3 + S4
    public int calculateTotal() {
        return problemClarity + methodology + results + presentation;
    }

    // Getters (Required for Database Integration) ---
    public String getStudentID() { return studentID; }
    public int getProblemClarity() { return problemClarity; }
    public int getMethodology() { return methodology; }
    public int getResults() { return results; }
    public int getPresentation() { return presentation; }
    public String getComments() { return comments; }

    //toString (For Debugging Purposes) ---
    @Override
    public String toString() {
        return "Evaluation [Student=" + studentID + ", Total=" + calculateTotal() + 
               ", Scores=(" + problemClarity + "," + methodology + "," + results + "," + presentation + ")]";
    }
}