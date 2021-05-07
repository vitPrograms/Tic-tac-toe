import javax.swing.JFrame;

public class MainFrame extends JFrame {

	MainFrame(){
		this.setTitle("Tic-Tac-Toe");
		this.add(new Code());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
