import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class StudentDashboard extends JFrame {

    private JTextField txtTitle, txtSupervisor;
    private JRadioButton rbOral, rbPoster;
    private JTextArea txtAbstract;
    private JLabel lblFileStatus;
    private String selectedAbsolutePath = "";
    private String studentName;

    // Style Constants
    private final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private final Font INPUT_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    private final Color PRIMARY_COLOR = new Color(45, 52, 71);
    private final Color BUTTON_COLOR = new Color(135, 206, 250);

    public StudentDashboard(String nameFromMember2) {
        // Get student name passed from Member 2
        this.studentName = (nameFromMember2 == null || nameFromMember2.trim().isEmpty()) ? "Student" : nameFromMember2;

        setTitle("FCI Seminar - Student Portal");

        // --- Modification: Disable full screen, restore fixed size ---
        setSize(1000, 750);
        setResizable(true); // Allow manual resizing, but not full screen at startup

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center window
        setLayout(new BorderLayout());

        initHeader();
        initMainContent();
        initFooter();
    }

    private void initHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(1000, 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 25));

        JLabel lblTitle = new JLabel("SEMINAR REGISTRATION");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(Color.WHITE);

        // Top right area: Welcome info and Logout button
        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 25));
        rightHeader.setOpaque(false);

        JLabel lblUser = new JLabel("Welcome, " + studentName);
        lblUser.setForeground(new Color(200, 200, 200));
        lblUser.setFont(new Font("Segoe UI", Font.ITALIC, 17));

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(new Color(220, 53, 69)); // Set logout button to red
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(e -> {
            this.dispose();
            new RoleSelector().setVisible(true); // Return to role selection page
        });

        rightHeader.add(lblUser);
        rightHeader.add(btnLogout);

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(rightHeader, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);
    }

    private void initMainContent() {
        // Split horizontally
        JPanel mainContent = new JPanel(new GridLayout(1, 2, 40, 0));
        mainContent.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));

        // Left: Research details (use GridBagLayout for left-aligned labels)
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBorder(createStyledBorder("RESEARCH DETAILS"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 5, 15, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // 1. Research Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel l1 = new JLabel("Research Title:");
        l1.setFont(LABEL_FONT);
        leftPanel.add(l1, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtTitle = new JTextField();
        txtTitle.setFont(INPUT_FONT);
        txtTitle.setPreferredSize(new Dimension(250, 40));
        leftPanel.add(txtTitle, gbc);

        // 2. Supervisor Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel l2 = new JLabel("Supervisor Name:");
        l2.setFont(LABEL_FONT);
        leftPanel.add(l2, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtSupervisor = new JTextField();
        txtSupervisor.setFont(INPUT_FONT);
        txtSupervisor.setPreferredSize(new Dimension(250, 40));
        leftPanel.add(txtSupervisor, gbc);

        // 3. Presentation Type
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel l3 = new JLabel("Presentation Type:");
        l3.setFont(LABEL_FONT);
        leftPanel.add(l3, gbc);

        gbc.gridx = 1;
        rbOral = new JRadioButton("Oral Presentation", true);
        rbPoster = new JRadioButton("Poster Presentation");
        rbOral.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        rbPoster.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        ButtonGroup bg = new ButtonGroup();
        bg.add(rbOral);
        bg.add(rbPoster);

        // Arrange radio buttons vertically
        JPanel rbPanel = new JPanel();
        rbPanel.setLayout(new BoxLayout(rbPanel, BoxLayout.Y_AXIS));
        rbPanel.add(rbOral);
        rbPanel.add(Box.createVerticalStrut(5));
        rbPanel.add(rbPoster);
        leftPanel.add(rbPanel, gbc);

        // Right: Abstract area
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(createStyledBorder("ABSTRACT"));
        txtAbstract = new JTextArea();
        txtAbstract.setFont(new Font("Consolas", Font.PLAIN, 16));
        txtAbstract.setLineWrap(true);
        txtAbstract.setWrapStyleWord(true);
        rightPanel.add(new JScrollPane(txtAbstract), BorderLayout.CENTER);

        mainContent.add(leftPanel);
        mainContent.add(rightPanel);
        add(mainContent, BorderLayout.CENTER);
    }

    private void initFooter() {
        JPanel footer = new JPanel(new BorderLayout(0, 15));
        footer.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));

        // File selection area (Large button)
        JPanel uploadRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        lblFileStatus = new JLabel("No file selected ");
        lblFileStatus.setFont(new Font("Segoe UI", Font.ITALIC, 15));

        JButton btnUpload = new JButton("Select File");
        btnUpload.setPreferredSize(new Dimension(180, 50));
        btnUpload.setFont(new Font("Segoe UI", Font.BOLD, 16));

        btnUpload.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                selectedAbsolutePath = chooser.getSelectedFile().getAbsolutePath();
                lblFileStatus.setText("Selected: " + chooser.getSelectedFile().getName() + " ");
                lblFileStatus.setForeground(new Color(0, 150, 0));
            }
        });

        uploadRow.add(lblFileStatus);
        uploadRow.add(btnUpload);

        // Submit button
        JButton btnSubmit = new JButton("CONFIRM AND SUBMIT");
        btnSubmit.setPreferredSize(new Dimension(0, 65));
        btnSubmit.setBackground(BUTTON_COLOR);
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 22));
        btnSubmit.addActionListener(e -> {
            if (txtTitle.getText().trim().isEmpty() || selectedAbsolutePath.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please complete the form and upload a file!");
                return;
            }
            // Store to DataCenter list
            String type = rbOral.isSelected() ? "Oral" : "Poster";
            DataCenter.addStudent(new Student(studentName, txtTitle.getText(), txtAbstract.getText(), txtSupervisor.getText(), type, selectedAbsolutePath));

            JOptionPane.showMessageDialog(this, "Submission Success!");
            this.dispose();
            new RoleSelector().setVisible(true);
        });

        footer.add(uploadRow, BorderLayout.NORTH);
        footer.add(btnSubmit, BorderLayout.SOUTH);
        add(footer, BorderLayout.SOUTH);
    }

    private CompoundBorder createStyledBorder(String title) {
        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1), title);
        tb.setTitleFont(new Font("Segoe UI", Font.BOLD, 16));
        return BorderFactory.createCompoundBorder(tb, BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
}