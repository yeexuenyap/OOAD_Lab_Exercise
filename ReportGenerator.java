import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class ReportGenerator extends JFrame {

    private JTextArea txtReport;

    // Constructor for creating the report generation window
    public ReportGenerator() {
        setTitle("Generate Reports");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top panel with report buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton btnSchedule = new JButton("Session Schedule");
        JButton btnEvaluations = new JButton("Evaluation Summary");
        JButton btnAnalytics = new JButton("Analytics");
        JButton btnExport = new JButton("Save to File");

        btnSchedule.addActionListener(e -> generateSchedule());
        btnEvaluations.addActionListener(e -> generateEvaluations());
        btnAnalytics.addActionListener(e -> generateAnalytics());
        btnExport.addActionListener(e -> exportToFile());

        topPanel.add(btnSchedule);
        topPanel.add(btnEvaluations);
        topPanel.add(btnAnalytics);
        topPanel.add(btnExport);
        add(topPanel, BorderLayout.NORTH);

        // Text area to display report output
        txtReport = new JTextArea();
        txtReport.setEditable(false);
        txtReport.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(txtReport), BorderLayout.CENTER);
    }

    // Generates a report showing all session schedules
    private void generateSchedule() {
        StringBuilder report = new StringBuilder();
        report.append("===== SEMINAR SESSION SCHEDULE =====\n\n");

        if (DataCenter.getSessions().isEmpty()) {
            report.append("No sessions created yet.\n");
        }

        for (Session s : DataCenter.getSessions()) {
            report.append("Session: ").append(s.getSessionID()).append("\n");
            report.append("Date: ").append(s.getDate()).append(" ").append(s.getTime()).append("\n");
            report.append("Venue: ").append(s.getVenue()).append("\n");
            report.append("Type: ").append(s.getType()).append("\n");

            // List assigned students (show Board ID for poster presenters)
            report.append("Students: ");
            if (s.getAssignedStudents().isEmpty()) {
                report.append("None\n");
            } else {
                report.append("\n");
                for (String name : s.getAssignedStudents()) {
                    Student st = DataCenter.findStudent(name);
                    if (st != null && st.getPresentationType().equals("Poster")) {
                        report.append("  - ").append(name).append(" [Board: ").append(st.getBoardID()).append("]\n");
                    } else {
                        report.append("  - ").append(name).append("\n");
                    }
                }
            }

            // List assigned evaluators
            report.append("Evaluators: ");
            if (s.getAssignedEvaluators().isEmpty()) {
                report.append("None\n");
            } else {
                report.append("\n");
                for (String ev : s.getAssignedEvaluators()) {
                    report.append("  - ").append(ev).append("\n");
                }
            }
            report.append("\n-----------------------------------\n\n");
        }

        txtReport.setText(report.toString());
    }

    // Generates a detailed evaluation report with per-rubric scores
    private void generateEvaluations() {
        StringBuilder report = new StringBuilder();
        report.append("===== EVALUATION SUMMARY =====\n\n");

        boolean hasEvaluations = false;

        for (Student s : DataCenter.getStudents()) {
            if (s.getTotalScore() > 0) {
                hasEvaluations = true;
                report.append("Student: ").append(s.getName()).append("\n");
                report.append("Research: ").append(s.getResearchTitle()).append("\n");
                report.append("Type: ").append(s.getPresentationType()).append("\n");
                report.append("Supervisor: ").append(s.getSupervisorName()).append("\n");
                // Per-rubric score breakdown
                report.append("Scores:\n");
                report.append("  Problem Clarity : ").append(s.getClarityScore()).append("/5\n");
                report.append("  Methodology     : ").append(s.getMethodologyScore()).append("/5\n");
                report.append("  Results          : ").append(s.getResultsScore()).append("/5\n");
                report.append("  Presentation     : ").append(s.getPresentationScore()).append("/5\n");
                report.append("  TOTAL            : ").append(s.getTotalScore()).append("/20\n");
                // Show evaluator comments if available
                if (!s.getComments().isEmpty()) {
                    report.append("Comments: ").append(s.getComments()).append("\n");
                }
                report.append("\n-----------------------------------\n\n");
            }
        }

        if (!hasEvaluations) {
            report.append("No evaluations submitted yet.\n");
        }

        txtReport.setText(report.toString());
    }

    // Generates analytics summary with statistics and per-rubric averages
    private void generateAnalytics() {
        StringBuilder report = new StringBuilder();
        report.append("===== DATA ANALYTICS =====\n\n");

        // Count totals
        int totalStudents = DataCenter.getStudents().size();
        int totalEvaluations = DataCenter.getEvaluations().size();
        int totalSessions = DataCenter.getSessions().size();
        int oralCount = 0;
        int posterCount = 0;
        int evaluatedCount = 0;
        int totalScore = 0;
        int highestScore = 0;
        int lowestScore = 20;
        String highestStudent = "N/A";
        String lowestStudent = "N/A";

        // Loop through students to gather statistics
        for (Student s : DataCenter.getStudents()) {
            if (s.getPresentationType().equals("Oral")) oralCount++;
            else posterCount++;

            if (s.getTotalScore() > 0) {
                evaluatedCount++;
                totalScore += s.getTotalScore();

                if (s.getTotalScore() > highestScore) {
                    highestScore = s.getTotalScore();
                    highestStudent = s.getName();
                }
                if (s.getTotalScore() < lowestScore) {
                    lowestScore = s.getTotalScore();
                    lowestStudent = s.getName();
                }
            }
        }

        // General statistics
        report.append("General Statistics:\n");
        report.append("  Total Students    : ").append(totalStudents).append("\n");
        report.append("  Oral Presenters   : ").append(oralCount).append("\n");
        report.append("  Poster Presenters : ").append(posterCount).append("\n");
        report.append("  Total Sessions    : ").append(totalSessions).append("\n");
        report.append("  Total Evaluations : ").append(totalEvaluations).append("\n\n");

        if (evaluatedCount > 0) {
            // Score statistics
            double avgScore = (double) totalScore / evaluatedCount;
            report.append("Score Statistics:\n");
            report.append("  Students Evaluated : ").append(evaluatedCount).append("\n");
            report.append("  Average Score      : ").append(String.format("%.1f", avgScore)).append("/20\n");
            report.append("  Highest Score      : ").append(highestScore).append("/20 (").append(highestStudent).append(")\n");
            report.append("  Lowest Score       : ").append(lowestScore).append("/20 (").append(lowestStudent).append(")\n\n");

            // Per-rubric averages
            int totalClarity = 0, totalMethod = 0, totalResults = 0, totalPresent = 0;
            for (Student s : DataCenter.getStudents()) {
                if (s.getTotalScore() > 0) {
                    totalClarity += s.getClarityScore();
                    totalMethod += s.getMethodologyScore();
                    totalResults += s.getResultsScore();
                    totalPresent += s.getPresentationScore();
                }
            }
            report.append("Average by Rubric:\n");
            report.append("  Problem Clarity : ").append(String.format("%.1f", (double) totalClarity / evaluatedCount)).append("/5\n");
            report.append("  Methodology     : ").append(String.format("%.1f", (double) totalMethod / evaluatedCount)).append("/5\n");
            report.append("  Results          : ").append(String.format("%.1f", (double) totalResults / evaluatedCount)).append("/5\n");
            report.append("  Presentation     : ").append(String.format("%.1f", (double) totalPresent / evaluatedCount)).append("/5\n");
        } else {
            report.append("No evaluation data available for analytics.\n");
        }

        txtReport.setText(report.toString());
    }

    // Export the current report to a text file using JFileChooser
    private void exportToFile() {
        if (txtReport.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Generate a report first before exporting.");
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File("seminar_report.txt"));
        int result = chooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                FileWriter writer = new FileWriter(chooser.getSelectedFile());
                writer.write(txtReport.getText());
                writer.close();
                JOptionPane.showMessageDialog(this, "Report saved successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
            }
        }
    }
}
