import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.*;

public class EvaluationPage extends JFrame {

    // --- Style Constants ---
    private final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 12); 
    private final Font BOLD_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private final Color PRIMARY_COLOR = new Color(45, 52, 71); 
    private final Color BUTTON_COLOR = new Color(135, 206, 250); // Light Blue

    // Class-level variables for data access ---
    private ButtonGroup bgClarity = new ButtonGroup();
    private ButtonGroup bgMethod = new ButtonGroup();
    private ButtonGroup bgResults = new ButtonGroup();
    private ButtonGroup bgPresent = new ButtonGroup();
    private JTextArea txtComment; 
    
    // Store student name to create the Evaluation object later
    private String studentName; 

    public EvaluationPage(String evaluatorName, String studentName, String title, String supervisor, String file) {
        this.studentName = studentName; 
        
        // --- 1. Window Settings ---
        setTitle("Evaluation Form - " + studentName);
        setSize(1100, 850); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(250, 250, 250));
        setLayout(new BorderLayout(0, 0));

        // --- 2. Header ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(1100, 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));

        JLabel lblTitle = new JLabel("SEMINAR EVALUATION SYSTEM");
        lblTitle.setFont(HEADER_FONT);
        lblTitle.setForeground(Color.WHITE);
        
        JLabel lblInfo = new JLabel("Welcome, " + evaluatorName + " | Logged in");
        lblInfo.setForeground(new Color(220, 220, 220));
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 16));

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(lblInfo, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // --- 3. Main Content ---
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // =================================================================
        // Part 1: Student Details
        // =================================================================
        JPanel infoPanel = new JPanel(new GridLayout(1, 3, 20, 0)); 
        infoPanel.setOpaque(false);
        infoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80)); 
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(createDetailBox("Research Title:", title));
        infoPanel.add(createDetailBox("Supervisor Name:", supervisor));
        infoPanel.add(createDetailBox("Uploaded File:", file));

        contentPanel.add(infoPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // =================================================================
        // Part 2: Rubric Table
        // =================================================================
        JLabel lblRubric = new JLabel("Evaluation Rubric Reference:");
        lblRubric.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblRubric.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(lblRubric);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        String[] columns = {"Criteria", "Poor (1)", "Below Avg (2)", "Avg (3)", "Above Avg (4)", "Good (5)"};
        Object[][] data = {
            {"<html><b>Problem Clarity</b></html>", "<html>Vague or missing<br>problem definition.</html>", "<html>Unclearly stated;<br>lacks objective.</html>", "<html>Clear but lacks<br>depth.</html>", "<html>Well-defined &<br>specific.</html>", "<html>Highly precise &<br>insightful.</html>"},
            {"<html><b>Methodology</b></html>", "<html>Incorrect or<br>mismatched.</html>", "<html>Weak; major<br>flaws.</html>", "<html>Standard methods;<br>reasonable.</html>", "<html>Rigorous &<br>well-justified.</html>", "<html>Highly rigorous<br>& scientific.</html>"},
            {"<html><b>Results</b></html>", "<html>No results shown.</html>", "<html>Disorganized;<br>insufficient data.</html>", "<html>Accurate but<br>superficial.</html>", "<html>Clear &<br>well-analyzed.</html>", "<html>Extensive data<br>& deep analysis.</html>"},
            {"<html><b>Presentation</b></html>", "<html>Unprofessional;<br>unfamiliar.</html>", "<html>Scattered;<br>messy slides.</html>", "<html>Clear;<br>meets standards.</html>", "<html>Engaging &<br>confident.</html>", "<html>Professional &<br>persuasive.</html>"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable rubricTable = new JTable(model);
        rubricTable.setRowHeight(80); 
        rubricTable.setFont(TEXT_FONT);
        
        JTableHeader header = rubricTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false); // Disable column dragging
        
        rubricTable.setEnabled(false); 
        rubricTable.getColumnModel().getColumn(0).setPreferredWidth(120);

        JScrollPane tableScroll = new JScrollPane(rubricTable);
        tableScroll.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        tableScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        tableScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 360)); 
        
        contentPanel.add(tableScroll);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // =================================================================
        // Part 3: Scoring Section
        // =================================================================
        JLabel lblScore = new JLabel("Enter Scores & Comments:");
        lblScore.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblScore.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(lblScore);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // --- [MODIFIED] Pass specific ButtonGroup instances ---
        contentPanel.add(createScoreRow("1. Problem Clarity", bgClarity));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(createScoreRow("2. Methodology", bgMethod));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(createScoreRow("3. Results", bgResults));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(createScoreRow("4. Presentation", bgPresent));
        
        contentPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        JLabel lblComment = new JLabel("Comments:");
        lblComment.setFont(BOLD_FONT);
        lblComment.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(lblComment);
        
        txtComment = new JTextArea(4, 20); // Initialize the class variable
        txtComment.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtComment.setLineWrap(true);
        txtComment.setWrapStyleWord(true);
        txtComment.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        JScrollPane scrollComment = new JScrollPane(txtComment);
        scrollComment.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollComment.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        contentPanel.add(scrollComment);
        
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // =================================================================
        // Part 4: Submit Button
        // =================================================================
        JButton btnSubmit = new JButton("SUBMIT EVALUATION");
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnSubmit.setBackground(BUTTON_COLOR); 
        btnSubmit.setForeground(Color.BLACK);
        btnSubmit.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); 
        btnSubmit.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50)); 
        btnSubmit.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // --- [CRITICAL LOGIC] Submit Action ---
        btnSubmit.addActionListener(e -> {
            //  Retrieve Scores
            int s1 = getSelectedScore(bgClarity);
            int s2 = getSelectedScore(bgMethod);
            int s3 = getSelectedScore(bgResults);
            int s4 = getSelectedScore(bgPresent);
            String comments = txtComment.getText();

            // Validate input
            if (s1 == 0 || s2 == 0 || s3 == 0 || s4 == 0) {
                JOptionPane.showMessageDialog(this, "Please rate ALL criteria before submitting!", 
                                              "Incomplete Evaluation", JOptionPane.ERROR_MESSAGE);
                return;
            }

            

            // reate Evaluation Object (Data Packing)
            Evaluation eval = new Evaluation(this.studentName, s1, s2, s3, s4, comments);
 
            DataCenter.addEvaluation(eval);

            // Feedback and Close
            JOptionPane.showMessageDialog(this, 
                "Evaluation submitted successfully!\n" +
                "Total Score: " + eval.calculateTotal() + "/20");
            this.dispose();
        });

        contentPanel.add(btnSubmit);

        JScrollPane pageScrollPane = new JScrollPane(contentPanel);
        pageScrollPane.setBorder(null); 
        pageScrollPane.getVerticalScrollBar().setUnitIncrement(16); 
        
        add(pageScrollPane, BorderLayout.CENTER);
    }

    // --- Helper Methods ---

    private JPanel createDetailBox(String label, String value) {
        JPanel p = new JPanel(new BorderLayout(0, 5));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel l = new JLabel(label);
        l.setFont(BOLD_FONT);
        p.add(l, BorderLayout.NORTH);
        
        JTextField tf = new JTextField(value);
        tf.setEditable(false);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setBackground(Color.WHITE);
        tf.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); 
        tf.setPreferredSize(new Dimension(100, 35)); 
        p.add(tf, BorderLayout.CENTER);
        
        return p;
    }

    //Helper to create a row with a specific ButtonGroup
    private JPanel createScoreRow(String label, ButtonGroup bg) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); 
        panel.setAlignmentX(Component.LEFT_ALIGNMENT); 
        
        JLabel lbl = new JLabel(label);
        lbl.setFont(BOLD_FONT);
        lbl.setPreferredSize(new Dimension(200, 40)); 
        panel.add(lbl, BorderLayout.WEST);

        JPanel btnPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        btnPanel.setOpaque(false);
        
        for (int i = 1; i <= 5; i++) {
            JToggleButton btn = new JToggleButton(String.valueOf(i));
            btn.setBackground(Color.WHITE);
            btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); 
            btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            
            // [CRITICAL] Set ActionCommand so we can retrieve the value later
            btn.setActionCommand(String.valueOf(i));
            
            bg.add(btn);
            btnPanel.add(btn);
        }
        panel.add(btnPanel, BorderLayout.CENTER);
        return panel;
    }
    
    // Helper to get integer value from ButtonGroup
    private int getSelectedScore(ButtonGroup bg) {
        if (bg.getSelection() != null) {
            String val = bg.getSelection().getActionCommand();
            try {
                return Integer.parseInt(val);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0; // 0 means not selected
    }
}