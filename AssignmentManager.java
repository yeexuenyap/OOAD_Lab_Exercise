import javax.swing.*;
import java.awt.*;

public class AssignmentManager extends JFrame {

    private JComboBox<String> cmbSession;
    private JComboBox<String> cmbStudent;
    private JComboBox<String> cmbEvaluator;
    private JTextArea txtAssignments;

    // Constructor for creating assignment management window
    public AssignmentManager() {
        setTitle("Assignment Management");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Session selector
        formPanel.add(new JLabel("Select Session:"));
        cmbSession = new JComboBox<>();
        cmbSession.addActionListener(e -> updateLists());
        formPanel.add(cmbSession);

        // Student assignment
        formPanel.add(new JLabel("Assign Student:"));
        cmbStudent = new JComboBox<>();
        formPanel.add(cmbStudent);

        formPanel.add(new JLabel(""));
        JButton btnAssignStudent = new JButton("Assign Student");
        btnAssignStudent.addActionListener(e -> assignStudent());
        formPanel.add(btnAssignStudent);

        // Evaluator assignment
        formPanel.add(new JLabel("Assign Evaluator:"));
        cmbEvaluator = new JComboBox<>();
        formPanel.add(cmbEvaluator);

        formPanel.add(new JLabel(""));
        JButton btnAssignEval = new JButton("Assign Evaluator");
        btnAssignEval.addActionListener(e -> assignEvaluator());
        formPanel.add(btnAssignEval);

        add(formPanel, BorderLayout.NORTH);

        // Text area to show assignments
        txtAssignments = new JTextArea();
        txtAssignments.setEditable(false);
        add(new JScrollPane(txtAssignments), BorderLayout.CENTER);

        loadSessions();
    }

    // Load sessions into drop down menu
    private void loadSessions() {
        cmbSession.removeAllItems();
        for (Session s : DataCenter.getSessions()) {
            cmbSession.addItem(s.getSessionID() + " - " + s.getType());
        }
        if (cmbSession.getItemCount() > 0) {
            updateLists();
        }
    }

    // Updates student and evaluator lists when a session is selected
    private void updateLists() {
        if (cmbSession.getSelectedIndex() == -1) return;

        Session session = DataCenter.getSessions().get(cmbSession.getSelectedIndex());

        // Load students that match the session type
        cmbStudent.removeAllItems();
        for (Student s : DataCenter.getStudents()) {
            if (s.getPresentationType().equals(session.getType())) {
                cmbStudent.addItem(s.getName());
            }
        }

        // Load evaluators from DataCenter
        cmbEvaluator.removeAllItems();
        for (Evaluator ev : DataCenter.getEvaluators()) {
            cmbEvaluator.addItem(ev.getName());
        }

        showAssignments(session);
    }

    // Assigns selected student to selected session
    private void assignStudent() {
        if (cmbSession.getSelectedIndex() == -1 || cmbStudent.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select session and student");
            return;
        }

        Session session = DataCenter.getSessions().get(cmbSession.getSelectedIndex());
        String studentName = (String) cmbStudent.getSelectedItem();

        // If this is a Poster session, ask for Board ID
        if (session.getType().equals("Poster")) {
            String boardID = JOptionPane.showInputDialog(this,
                "Enter Board ID for poster presentation:",
                "Board ID Assignment",
                JOptionPane.QUESTION_MESSAGE);

            if (boardID == null || boardID.trim().isEmpty()) {
                boardID = "N/A";
            }

            session.assignStudent(studentName);

            // Update the student's board ID record
            Student s = DataCenter.findStudent(studentName);
            if (s != null) {
                s.setAssignment(session.getDate() + " " + session.getTime(),
                               session.getVenue(), boardID.trim());
            }
        } else {
            session.assignStudent(studentName);
        }

        JOptionPane.showMessageDialog(this, "Student assigned!");
        showAssignments(session);
    }

    // Assigns selected evaluator to selected session
    private void assignEvaluator() {
        if (cmbSession.getSelectedIndex() == -1 || cmbEvaluator.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select session and evaluator");
            return;
        }

        Session session = DataCenter.getSessions().get(cmbSession.getSelectedIndex());
        String evaluator = (String) cmbEvaluator.getSelectedItem();
        session.assignEvaluator(evaluator);

        JOptionPane.showMessageDialog(this, "Evaluator assigned!");
        showAssignments(session);
    }

    // Displays all current assignments for the selected session
    private void showAssignments(Session session) {
        StringBuilder sb = new StringBuilder();
        sb.append("Current Assignments for ").append(session.getSessionID()).append(":\n\n");

        sb.append("Students:\n");
        for (String name : session.getAssignedStudents()) {
            Student s = DataCenter.findStudent(name);
            // Show Board ID for poster presenters
            if (s != null && s.getPresentationType().equals("Poster")) {
                sb.append("  - ").append(name).append(" [Board: ").append(s.getBoardID()).append("]\n");
            } else {
                sb.append("  - ").append(name).append("\n");
            }
        }

        sb.append("\nEvaluators:\n");
        for (String e : session.getAssignedEvaluators()) {
            sb.append("  - ").append(e).append("\n");
        }

        txtAssignments.setText(sb.toString());
    }
}
