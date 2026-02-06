import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SessionManager extends JFrame {
    
    private JTable table;
    private DefaultTableModel model;
    
    // Constructor for the session management window
    public SessionManager() {
        setTitle("Session Management");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Top panel with create and delete buttons
        JPanel topPanel = new JPanel();
        JButton btnCreate = new JButton("Create New Session");
        JButton btnDelete = new JButton("Delete Selected");
        btnCreate.addActionListener(e -> createSession());
        btnDelete.addActionListener(e -> deleteSession());
        topPanel.add(btnCreate);
        topPanel.add(btnDelete);
        add(topPanel, BorderLayout.NORTH);
        
        // Table to show sessions
        String[] columns = {"ID", "Date", "Time", "Venue", "Type", "Students", "Evaluators"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        refreshTable();
    }
    
    // Dialog for creating session
    private void createSession() {
        JTextField txtID = new JTextField();
        JTextField txtDate = new JTextField();
        JTextField txtTime = new JTextField();
        JTextField txtVenue = new JTextField();
        String[] types = {"Oral", "Poster"};
        JComboBox<String> cmbType = new JComboBox<>(types);
        
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.add(new JLabel("Session ID:"));
        panel.add(txtID);
        panel.add(new JLabel("Date:"));
        panel.add(txtDate);
        panel.add(new JLabel("Time:"));
        panel.add(txtTime);
        panel.add(new JLabel("Venue:"));
        panel.add(txtVenue);
        panel.add(new JLabel("Type:"));
        panel.add(cmbType);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Create Session", 
                                                   JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String id = txtID.getText();
            String date = txtDate.getText();
            String time = txtTime.getText();
            String venue = txtVenue.getText();
            String type = (String) cmbType.getSelectedItem();
            
            if (!id.isEmpty() && !date.isEmpty() && !time.isEmpty() && !venue.isEmpty()) {
                Session session = new Session(id, date, time, venue, type);
                DataCenter.addSession(session);
                refreshTable();
                JOptionPane.showMessageDialog(this, "Session created!");
            }
        }
    }
    
    // Updates the table to show all the sessions
    private void refreshTable() {
        model.setRowCount(0);
        for (Session s : DataCenter.getSessions()) {
            model.addRow(new Object[]{
                s.getSessionID(),
                s.getDate(),
                s.getTime(),
                s.getVenue(),
                s.getType(),
                s.getAssignedStudents().size(),
                s.getAssignedEvaluators().size()
            });
        }
    }
    
    // Delete selected session
    private void deleteSession() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a session to delete!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this session?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            Session session = DataCenter.getSessions().get(selectedRow);
            DataCenter.getSessions().remove(session);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Session deleted!");
        }
    }
}