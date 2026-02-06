import javax.swing.*;
import java.awt.*;

public class ReportGenerator extends JFrame {
    
    private JTextArea txtReport;
    
    // Constructor for creating the report generation window
    public ReportGenerator() {
        setTitle("Generate Reports");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel();
        JButton btnSchedule = new JButton("Session Schedule");
        JButton btnEvaluations = new JButton("Evaluation Summary");
        
        btnSchedule.addActionListener(e -> generateSchedule());
        btnEvaluations.addActionListener(e -> generateEvaluations());
        
        topPanel.add(btnSchedule);
        topPanel.add(btnEvaluations);
        add(topPanel, BorderLayout.NORTH);
        
        txtReport = new JTextArea();
        txtReport.setEditable(false);
        txtReport.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(txtReport), BorderLayout.CENTER);
    }
    
    // Generates a report showing all session schedules
    private void generateSchedule() {
        StringBuilder report = new StringBuilder();
        report.append("===== SEMINAR SESSION SCHEDULE =====\n\n");
        
        for (Session s : DataCenter.getSessions()) {
            report.append("Session: ").append(s.getSessionID()).append("\n");
            report.append("Date: ").append(s.getDate()).append(" ").append(s.getTime()).append("\n");
            report.append("Venue: ").append(s.getVenue()).append("\n");
            report.append("Type: ").append(s.getType()).append("\n");
            report.append("Students: ").append(s.getAssignedStudents().size()).append("\n");
            report.append("Evaluators: ").append(s.getAssignedEvaluators().size()).append("\n");
            report.append("\n");
        }
        
        txtReport.setText(report.toString());
    }
    
    // Generates a report showing all evaluation results
    private void generateEvaluations() {
        StringBuilder report = new StringBuilder();
        report.append("===== EVALUATION SUMMARY =====\n\n");
        
        for (Student s : DataCenter.getStudents()) {
            if (s.getTotalScore() > 0) {
                report.append("Student: ").append(s.getName()).append("\n");
                report.append("Type: ").append(s.getPresentationType()).append("\n");
                report.append("Score: ").append(s.getTotalScore()).append("/20\n");
                report.append("\n");
            }
        }
        
        if (DataCenter.getEvaluations().isEmpty()) {
            report.append("No evaluations submitted yet.\n");
        }
        
        txtReport.setText(report.toString());
    }
}
