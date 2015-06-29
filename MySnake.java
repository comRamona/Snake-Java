import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MySnake extends Applet implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int velx = 10;
	private int vely = 0;
	private int size = 400;
	public int speed = 10;



	private int joints = 3;
	private int dotx[] = new int[size * size / 10];
	private int doty[] = new int[size * size / 10];
	private int rx = (size / 10) - 1, ry = (size / 10) - 1;
	private boolean inGame = true;
	private boolean started = false;;
	private boolean left = false, right = true, up = false, down = false;
	Thread t;

	public void run() {

		while (true) {
			if (started)
				changecomp();
			else
				changecomp2();
			checkApple();
			checkCollision();
			repaint();
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
			}

		}
	}

	public void init() {
		this.setSize(size, size+50);
		addKeyListener(new myListener());
		addMouseListener(new mouse());
		setFocusable(true);
		setBackground(Color.pink);
		updateApple();
		joints = 8;
		for (int i = 0; i < joints; i++) {
			dotx[i] = 100 - 10 * i;
			doty[i] = 100;
		}
		t = new Thread(this);

		t.start();
	}

	public void checkApple() {
		if (rx == dotx[0] && ry == doty[0]) {
			joints++;
			updateApple();
		}

	}

	public void updateApple() {
		rx = 5+(int) (Math.random() * (size / 10 - 10));
		rx = rx * 10;
		ry = 5+(int) (Math.random() * (size / 10 - 10));
		ry = ry * 10;
	}

	public void paint(Graphics g) {
		doDrawing(g);

	}

	public void doDrawing(Graphics g) {
		if (inGame && !started) {
           
			start(g);

		}

		if (inGame && started) {

			g.setColor(Color.BLUE);
			g.fillRect(0, 0, size, size);
			g.setColor(Color.GREEN);
			g.fillOval(rx, ry, 10, 10);
			for (int i = 0; i < joints; i++) {
				if (i == 0)
					g.setColor(Color.RED);
				else
					g.setColor(Color.YELLOW);
				g.fillOval(dotx[i], doty[i], 10, 10);
				
		}  
			String msg = "Score:"+(joints-8)*10;
			Font small = new Font("Helvetica", Font.BOLD, 14);
			FontMetrics metr = getFontMetrics(small);
			g.setColor(Color.red);
			g.setFont(small);
			g.drawString(msg, (size - metr.stringWidth(msg)) , size+30 );
		}
		if (!inGame && started)
			gameOver(g);

	}

	public void changecomp() {

		for (int i = joints; i > 0; i--) {
			dotx[i] = dotx[i - 1];
			doty[i] = doty[i - 1];
		}
		dotx[0] = dotx[0] + velx;
		doty[0] = doty[0] + vely;
		Toolkit.getDefaultToolkit().sync();

	}

	public void changecomp2() {
		Toolkit.getDefaultToolkit().sync();
	};

	private class mouse extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			started = true;
			if (inGame == false) {
				inGame = true;
				re();
			}
		}
	}

	public void re() {
		setBackground(Color.pink);
		updateApple();
		joints = 8;
		for (int i = 0; i < joints; i++) {
			dotx[i] = 100 - 10 * i;
			doty[i] = 100;
		}
		 velx = 10;
		 vely = 0;
	}

	private class myListener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			int code = e.getKeyCode();
			if (code == KeyEvent.VK_UP && down == false) {
				up();
				up = true;
				left = false;
				right = false;
			}
			if (code == KeyEvent.VK_DOWN && up == false) {
				down();
				down = true;
				left = false;
				right = false;
			}
			if (code == KeyEvent.VK_RIGHT && left == false) {
				right();
				right = true;
				up = false;
				down = false;
			}
			if (code == KeyEvent.VK_LEFT && right == false) {
				left();
				left = false;
				up = false;
				down = false;
			}

		}
	}

	private void checkCollision() {

		if (started) {
			for (int i = joints; i > 0; i--) {

				if ((i > 4) && (dotx[0] == dotx[i]) && (doty[0] == doty[i])) {
					inGame = false;
				}
			}

			if (doty[0] >= size) {
				inGame = false;
			}

			if (doty[0] < 0) {
				inGame = false;
			}

			if (dotx[0] >= size) {
				inGame = false;
			}

			if (dotx[0] < 0) {
				inGame = false;
			}
		}
	}

	private void gameOver(final Graphics g) {
        setBackground(Color.black);
		String msg = "Game Over. Click anywhere to restart";
		String msg2 = "Score:"+(joints-8)*10;
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = getFontMetrics(small);

		g.setColor(Color.red);
		
		g.setFont(small);
		g.drawString(msg, (size - metr.stringWidth(msg)) / 2, size / 2);
		g.drawString(msg2, (size - metr.stringWidth(msg)) / 2, size / 2+100);

	}

	private void start(Graphics g) {
		g.setColor(Color.pink);
        g.fillRect(0, 0, size, size+50);
		String msg = "SNAKE";
		String msg2 = "Click anywhere to start";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = getFontMetrics(small);
		Font small2= new Font("Helvetica", Font.BOLD, 28);
		g.setFont(small);
		g.setColor(Color.black);
		g.drawString(msg2, (size - metr.stringWidth(msg2)) / 2, size / 2+50);
		g.setColor(Color.white);
		metr = getFontMetrics(small2);
		g.setFont(small2);
		g.drawString(msg, (size - metr.stringWidth(msg)) / 2, size / 2);
		
	}
	public void up() {
		vely = 0 - speed;
		velx = 0;

	}

	public void down() {
		vely = speed;
		velx = 0;
	}

	public void left() {
		velx = 0 - speed;
		vely = 0;
	}

	public void right() {
		velx = speed;
		vely = 0;
	}

}