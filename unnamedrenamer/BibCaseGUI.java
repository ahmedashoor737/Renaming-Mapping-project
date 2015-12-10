import javax.swing.*;

public class BibCaseGUI extends JFrame {
	private JTextArea status;

	public BibCaseGUI() {
		super("BibCase");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);

		status = new JTextArea("Monitoring: " + Settings.getFolderPath() + "\nBibTeX Database: " + Settings.getBibFilePath() + "\nTo exit, close the window.");
		status.setEditable(false);
		this.add(status);

		this.setVisible(true);
	}
}