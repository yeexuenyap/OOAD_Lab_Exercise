import javax.swing.*;
import java.awt.*;

public class AwardViewer extends JFrame {
    
    // Constructor for creating the award display window
    public AwardViewer() {
        setTitle("Award Winners");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel title = new JLabel("Award Winners");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Best Oral 
        Student bestOral = DataCenter.getBestOral();
        JLabel lblOral = new JLabel("Best Oral Presentation: " + 
            (bestOral != null ? bestOral.getName() + " (" + bestOral.getTotalScore() + "/20)" : "N/A"));
        lblOral.setFont(new Font("Arial", Font.PLAIN, 14));
        lblOral.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblOral);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Best Poster
        Student bestPoster = DataCenter.getBestPoster();
        JLabel lblPoster = new JLabel("Best Poster Presentation: " + 
            (bestPoster != null ? bestPoster.getName() + " (" + bestPoster.getTotalScore() + "/20)" : "N/A"));
        lblPoster.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPoster.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblPoster);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // People's Choice (highest overall)
        Student peoplesChoice = null;
        int maxScore = 0;
        for (Student s : DataCenter.getStudents()) {
            if (s.getTotalScore() > maxScore) {
                maxScore = s.getTotalScore();
                peoplesChoice = s;
            }
        }
        JLabel lblPeoples = new JLabel("People's Choice: " + 
            (peoplesChoice != null ? peoplesChoice.getName() + " (" + peoplesChoice.getTotalScore() + "/20)" : "N/A"));
        lblPeoples.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPeoples.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblPeoples);
        
        add(panel, BorderLayout.CENTER);
    }
}
