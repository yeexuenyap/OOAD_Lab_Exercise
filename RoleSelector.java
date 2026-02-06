import javax.swing.*;
import java.awt.*;

public class RoleSelector extends JFrame {

    // Constructor for the role selection window (main entry point)
    public RoleSelector() {
        // --- 1. Window Settings ---
        setTitle("FCI Seminar Management System");
        setSize(500, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // --- 2. Main Panel Layout ---
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Title label
        JLabel lblTitle = new JLabel("FCI Postgraduate Research Seminar");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- 3. Name Input Field (for Student and Coordinator) ---
        JPanel namePanel = new JPanel(new BorderLayout(10, 0));
        namePanel.setMaximumSize(new Dimension(400, 35));
        JLabel lblName = new JLabel("Your Name:");
        namePanel.add(lblName, BorderLayout.WEST);
        JTextField txtName = new JTextField();
        txtName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        namePanel.add(txtName, BorderLayout.CENTER);

        // --- 3b. Evaluator Dropdown (shown only when Evaluator role is selected) ---
        JPanel evalPanel = new JPanel(new BorderLayout(10, 0));
        evalPanel.setMaximumSize(new Dimension(400, 35));
        evalPanel.add(new JLabel("Select Evaluator:"), BorderLayout.WEST);
        JComboBox<String> cmbEvaluator = new JComboBox<>();
        cmbEvaluator.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        // Load evaluator names from DataCenter
        for (Evaluator ev : DataCenter.getEvaluators()) {
            cmbEvaluator.addItem(ev.getName());
        }
        evalPanel.add(cmbEvaluator, BorderLayout.CENTER);
        evalPanel.setVisible(false); // Hidden by default

        mainPanel.add(namePanel);
        mainPanel.add(evalPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // --- 4. Role Selector Dropdown ---
        JPanel rolePanel = new JPanel(new BorderLayout(10, 0));
        rolePanel.setMaximumSize(new Dimension(400, 35));
        rolePanel.add(new JLabel("Select Role:"), BorderLayout.WEST);
        String[] roles = {"Student", "Evaluator", "Coordinator"};
        JComboBox<String> cmbRole = new JComboBox<>(roles);
        cmbRole.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rolePanel.add(cmbRole, BorderLayout.CENTER);

        // Switch between name field and evaluator dropdown based on role
        cmbRole.addActionListener(e -> {
            String role = (String) cmbRole.getSelectedItem();
            if (role.equals("Evaluator")) {
                namePanel.setVisible(false);
                evalPanel.setVisible(true);
            } else {
                namePanel.setVisible(true);
                evalPanel.setVisible(false);
            }
        });

        mainPanel.add(rolePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- 5. Continue Button ---
        JButton btnContinue = new JButton("Continue");
        btnContinue.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnContinue.setPreferredSize(new Dimension(200, 45));
        btnContinue.setMaximumSize(new Dimension(200, 45));
        btnContinue.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Navigate to the correct dashboard based on selected role
        btnContinue.addActionListener(e -> {
            String role = (String) cmbRole.getSelectedItem();

            if (role.equals("Evaluator")) {
                // Use the selected evaluator name from the dropdown
                String name = (String) cmbEvaluator.getSelectedItem();
                if (name == null) {
                    JOptionPane.showMessageDialog(this, "No evaluators registered.");
                    return;
                }
                this.dispose();
                new EvaluatorDashboard(name).setVisible(true);

            } else {
                // Use the typed name for Student and Coordinator
                String name = txtName.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter your name.");
                    return;
                }
                this.dispose();

                if (role.equals("Student")) {
                    new StudentDashboard(name).setVisible(true);
                } else if (role.equals("Coordinator")) {
                    new CoordinatorDashboard(name).setVisible(true);
                }
            }
        });

        mainPanel.add(btnContinue);
        add(mainPanel);
    }

    // Main entry point for the whole application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RoleSelector().setVisible(true);
        });
    }
}
