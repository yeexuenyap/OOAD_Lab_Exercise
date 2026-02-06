import javax.swing.*;
import java.awt.*;

public class AwardViewer extends JFrame {

    // Constructor for creating the award display window
    public AwardViewer() {
        setTitle("Award Winners & Ceremony Agenda");
        setSize(600, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // --- Award Winners Section ---
        JLabel title = new JLabel("Award Winners");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Best Oral
        Student bestOral = DataCenter.getBestOral();
        JLabel lblOral = new JLabel("Best Oral Presentation: " +
            (bestOral != null ? bestOral.getName() + " (" + bestOral.getTotalScore() + "/20)" : "N/A"));
        lblOral.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblOral.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblOral);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));

        // Best Poster
        Student bestPoster = DataCenter.getBestPoster();
        JLabel lblPoster = new JLabel("Best Poster Presentation: " +
            (bestPoster != null ? bestPoster.getName() + " (" + bestPoster.getTotalScore() + "/20)" : "N/A"));
        lblPoster.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblPoster.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblPoster);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));

        // People's Choice (highest overall score)
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
        lblPeoples.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblPeoples.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblPeoples);

        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        panel.add(new JSeparator());
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        // --- Ceremony Agenda Section ---
        JLabel lblAgenda = new JLabel("Ceremony Agenda");
        lblAgenda.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblAgenda.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblAgenda);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Build the ceremony agenda text
        JTextArea txtAgenda = new JTextArea();
        txtAgenda.setEditable(false);
        txtAgenda.setFont(new Font("Monospaced", Font.PLAIN, 13));

        StringBuilder agenda = new StringBuilder();
        agenda.append("===========================================\n");
        agenda.append("  FCI POSTGRADUATE RESEARCH SEMINAR\n");
        agenda.append("  AWARD CEREMONY AGENDA\n");
        agenda.append("===========================================\n\n");
        agenda.append("1. Opening Remarks by Coordinator\n\n");
        agenda.append("2. Summary of Seminar Sessions\n");
        agenda.append("   - Total Presenters: ").append(DataCenter.getStudents().size()).append("\n");
        agenda.append("   - Total Sessions: ").append(DataCenter.getSessions().size()).append("\n");
        agenda.append("   - Total Evaluations: ").append(DataCenter.getEvaluations().size()).append("\n\n");
        agenda.append("3. Award Presentations\n");
        agenda.append("   a) Best Oral Presentation\n");
        agenda.append("      Winner: ").append(bestOral != null ? bestOral.getName() : "TBD").append("\n\n");
        agenda.append("   b) Best Poster Presentation\n");
        agenda.append("      Winner: ").append(bestPoster != null ? bestPoster.getName() : "TBD").append("\n\n");
        agenda.append("   c) People's Choice Award\n");
        agenda.append("      Winner: ").append(peoplesChoice != null ? peoplesChoice.getName() : "TBD").append("\n\n");
        agenda.append("4. Closing Remarks\n\n");
        agenda.append("===========================================\n");

        txtAgenda.setText(agenda.toString());

        JScrollPane scrollAgenda = new JScrollPane(txtAgenda);
        scrollAgenda.setPreferredSize(new Dimension(500, 200));
        scrollAgenda.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(scrollAgenda);

        add(panel, BorderLayout.CENTER);
    }
}
