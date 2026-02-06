import javax.swing.*;
import java.awt.*;

public class CoordinatorDashboard extends JFrame {

    private String coordinatorName;

    // Constructor for the coordinator main menu
    public CoordinatorDashboard(String name) {
        this.coordinatorName = (name == null || name.isEmpty()) ? "Coordinator" : name;

        setTitle("Coordinator Dashboard");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Simple layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JLabel header = new JLabel("Coordinator Portal - " + coordinatorName);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(header);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Buttons for different functions
        JButton btnSessions = new JButton("Manage Sessions");
        JButton btnAssignments = new JButton("Manage Assignments");
        JButton btnAwards = new JButton("View Awards");
        JButton btnReports = new JButton("Generate Reports");
        JButton btnLogout = new JButton("Logout");

        Dimension btnSize = new Dimension(300, 40);
        btnSessions.setMaximumSize(btnSize);
        btnAssignments.setMaximumSize(btnSize);
        btnAwards.setMaximumSize(btnSize);
        btnReports.setMaximumSize(btnSize);
        btnLogout.setMaximumSize(btnSize);

        btnSessions.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAssignments.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAwards.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnReports.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Set logout button to red
        btnLogout.setBackground(new Color(220, 53, 69));
        btnLogout.setForeground(Color.WHITE);

        // Button actions
        btnSessions.addActionListener(e -> new SessionManager().setVisible(true));
        btnAssignments.addActionListener(e -> new AssignmentManager().setVisible(true));
        btnAwards.addActionListener(e -> new AwardViewer().setVisible(true));
        btnReports.addActionListener(e -> new ReportGenerator().setVisible(true));
        btnLogout.addActionListener(e -> {
            this.dispose();
            new RoleSelector().setVisible(true); // Return to role selection page
        });

        mainPanel.add(btnSessions);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(btnAssignments);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(btnAwards);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(btnReports);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(btnLogout);

        add(mainPanel);
    }

    // Coordinator Test Code
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CoordinatorDashboard("Prof. Hassan").setVisible(true);
        });
    }
}
