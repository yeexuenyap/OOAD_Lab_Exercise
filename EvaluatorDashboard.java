import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EvaluatorDashboard extends JFrame {

    private final Font LARGE_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    private final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 28);
    private final Color PRIMARY_COLOR = new Color(45, 52, 71);
    private final Color BACKGROUND_COLOR = new Color(245, 245, 245);

    // Store current evaluator's name to pass to the next page
    private String currentEvaluatorName;
    private DefaultTableModel model;

    /**
     * Constructor
     */
    public EvaluatorDashboard(String nameFromMember2) {
        // --- 1. Handle Login Name ---
        String displayName;
        if (nameFromMember2 == null || nameFromMember2.trim().isEmpty()) {
            displayName = "Evaluator";
        } else {
            displayName = nameFromMember2;
        }
        this.currentEvaluatorName = displayName;

        // --- 2. Window Settings ---
        setTitle("FCI Seminar Management System - Evaluator Portal");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(0, 0));

        // --- 3. Header ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(1000, 90));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 25));

        JLabel lblTitle = new JLabel("SEMINAR EVALUATION SYSTEM");
        lblTitle.setFont(HEADER_FONT);
        lblTitle.setForeground(Color.WHITE);

        // Top right area: Welcome info and Logout button
        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 25));
        rightHeader.setOpaque(false);

        JLabel lblLoginInfo = new JLabel("Welcome, " + displayName);
        lblLoginInfo.setForeground(new Color(220, 220, 220));
        lblLoginInfo.setFont(new Font("Segoe UI", Font.ITALIC, 18));

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(new Color(220, 53, 69));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(e -> {
            this.dispose();
            new RoleSelector().setVisible(true); // Return to role selection page
        });

        rightHeader.add(lblLoginInfo);
        rightHeader.add(btnLogout);

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(rightHeader, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // --- 4. Main Content: Student List ---
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBorder(createStyledBorder("ASSIGNED STUDENTS LIST"));

        // Table Settings
        String[] columnNames = {"Student Name", "Research Title", "Supervisor", "Type", "Date & Time", "Venue"};
        model = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(LARGE_FONT);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(230, 230, 230));

        // Double-click Navigation Logic
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Double click
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        String name = (String) model.getValueAt(row, 0);
                        String title = (String) model.getValueAt(row, 1);
                        String supervisor = (String) model.getValueAt(row, 2);

                        // Find the actual student to get the real file path
                        Student s = DataCenter.findStudent(name);
                        String file = (s != null) ? s.getSubmissionFilePath() : "N/A";

                        // Open EvaluationPage and pass the current Evaluator's name
                        new EvaluationPage(currentEvaluatorName, name, title, supervisor, file).setVisible(true);
                    }
                }
            }
        });

        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        container.add(mainPanel);
        add(container, BorderLayout.CENTER);

        // --- 5. Bottom Area: Hint and Refresh ---
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));

        JLabel lblHint = new JLabel("* Double click on a student to start evaluation");
        lblHint.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblHint.setForeground(Color.GRAY);

        JButton btnRefresh = new JButton("Refresh List");
        btnRefresh.addActionListener(e -> loadStudentData());

        bottomPanel.add(lblHint, BorderLayout.WEST);
        bottomPanel.add(btnRefresh, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load assigned students from DataCenter
        loadStudentData();
    }

    // Loads students assigned to this evaluator's sessions from DataCenter
    private void loadStudentData() {
        model.setRowCount(0);

        // Find all sessions where this evaluator is assigned
        ArrayList<Session> mySessions = DataCenter.getSessionsForEvaluator(currentEvaluatorName);

        // Loop through each session and add assigned students to the table
        for (Session session : mySessions) {
            for (String studentName : session.getAssignedStudents()) {
                Student s = DataCenter.findStudent(studentName);
                if (s != null) {
                    model.addRow(new Object[]{
                        s.getName(),
                        s.getResearchTitle(),
                        s.getSupervisorName(),
                        s.getPresentationType(),
                        session.getDate() + " " + session.getTime(),
                        session.getVenue()
                    });
                }
            }
        }

        // Show a message if no students are assigned yet
        if (model.getRowCount() == 0) {
            model.addRow(new Object[]{"No students assigned yet", "", "", "", "", ""});
        }
    }

    private CompoundBorder createStyledBorder(String title) {
        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1), title);
        tb.setTitleFont(new Font("Segoe UI", Font.BOLD, 16));
        tb.setTitleColor(PRIMARY_COLOR);
        return BorderFactory.createCompoundBorder(tb, BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    // Main method for testing
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> {
            new EvaluatorDashboard(null).setVisible(true);
        });
    }
}
