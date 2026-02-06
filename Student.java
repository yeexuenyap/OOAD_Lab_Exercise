
public class Student {

    // 学生基础资料 (Member 3 负责填入)
    private String name;
    private String researchTitle;
    private String researchAbstract;
    private String supervisorName;
    private String presentationType; // "Oral" or "Poster"
    private String submissionFilePath; // 绝对路径，用于评估员打开文件

    // 评审资料 (Member 4 负责填入)
    private int clarityScore = 0;
    private int methodologyScore = 0;
    private int resultsScore = 0;
    private int presentationScore = 0;
    private String comments = "";
    private int totalScore = 0;

    // 场次安排 (Member 5 负责填入)
    private String sessionVenue = "Unassigned";
    private String sessionDate = "Unassigned";
    private String boardID = "N/A"; // 仅限 Poster

    // 构造函数
    public Student(String name, String title, String abs, String supervisor, String type, String path) {
        this.name = name;
        this.researchTitle = title;
        this.researchAbstract = abs;
        this.supervisorName = supervisor;
        this.presentationType = type;
        this.submissionFilePath = path;
    }

    // --- Getter 和 Setter (必须要有，方便其他模块读取和修改数据) ---
    public String getName() {
        return name;
    }

    public String getPresentationType() {
        return presentationType;
    }

    public String getSubmissionFilePath() {
        return submissionFilePath;
    }

    public void setScores(int c, int m, int r, int p, String comm) {
        this.clarityScore = c;
        this.methodologyScore = m;
        this.resultsScore = r;
        this.presentationScore = p;
        this.comments = comm;
        this.totalScore = c + m + r + p;
    }

    public int getTotalScore() {
        return totalScore;
    }

    // --- Additional Getters (Required for Reports & Evaluation Display) ---
    public String getResearchTitle() {
        return researchTitle;
    }

    public String getResearchAbstract() {
        return researchAbstract;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public int getClarityScore() {
        return clarityScore;
    }

    public int getMethodologyScore() {
        return methodologyScore;
    }

    public int getResultsScore() {
        return resultsScore;
    }

    public int getPresentationScore() {
        return presentationScore;
    }

    public String getComments() {
        return comments;
    }

    public String getSessionVenue() {
        return sessionVenue;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public String getBoardID() {
        return boardID;
    }

    public void setAssignment(String date, String venue, String boardID) {
        this.sessionDate = date;
        this.sessionVenue = venue;
        this.boardID = boardID;
    }
}
