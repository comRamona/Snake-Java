import java.awt.EventQueue;

import javax.swing.JFrame;

public class Launcher extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4796392158436621989L;

	public Launcher() {

		setContentPane(new Snake());
		setResizable(false);
		setTitle("Snake");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new Launcher();
				frame.setSize(400, 500);
				frame.setVisible(true);

			}
		});

	}
}
