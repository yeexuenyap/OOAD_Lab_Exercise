import javax.swing.*;
import java.awt.*;

public class CoordinatorDashboard extends JFrame {
    
    private String coordinatorName;
    
    // Constructor for the coordinator main menu
    public CoordinatorDashboard(String name) {
        this.coordinatorName = (name == null || name.isEmpty()) ? "Coordinator" : name;
        
        setTitle("Coordinator Dashboard");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Simple layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel header = new JLabel("Coordinator Portal - " + coordinatorName);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(header);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Buttons for different functions
        JButton btnSessions = new JButton("Manage Sessions");
        JButton btnAssignments = new JButton("Manage Assignments");
        JButton btnAwards = new JButton("View Awards");
        JButton btnReports = new JButton("Generate Reports");
        
        btnSessions.setMaximumSize(new Dimension(300, 40));
        btnAssignments.setMaximumSize(new Dimension(300, 40));
        btnAwards.setMaximumSize(new Dimension(300, 40));
        btnReports.setMaximumSize(new Dimension(300, 40));
        
        btnSessions.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAssignments.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAwards.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnReports.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Button actions
        btnSessions.addActionListener(e -> new SessionManager().setVisible(true));
        btnAssignments.addActionListener(e -> new AssignmentManager().setVisible(true));
        btnAwards.addActionListener(e -> new AwardViewer().setVisible(true));
        btnReports.addActionListener(e -> new ReportGenerator().setVisible(true));
        
        mainPanel.add(btnSessions);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(btnAssignments);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(btnAwards);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(btnReports);
        
        add(mainPanel);
    }
    
    // Coordinator Test Code
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CoordinatorDashboard("Prof. Hassan").setVisible(true);
        });
    }
}
