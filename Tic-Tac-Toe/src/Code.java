import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

public class Code extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static int SCREEN_WIDTH = 600;
	final static int SCREEN_HEIGHT = 600;
	final static int UNIT_SIZE = SCREEN_HEIGHT / 4;
	int x, y;
	char turn = 'X';
	char grid[] = {' ', ' ', ' ',
				   ' ', ' ', ' ',
				   ' ', ' ', ' '};
	boolean gameOver = false;
	int position;
	
	JButton button = new JButton("Clear");
	JLabel label = new JLabel("Label");
	JLabel turnLabel = new JLabel("Now Turn:");
	JLabel nowTurn = new JLabel("X");
	
	Code(){
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		this.setLayout(null);
		this.add(button);
		this.add(label);
		this.add(turnLabel);
		this.add(nowTurn);
		button.setBounds(UNIT_SIZE * 3, (UNIT_SIZE/2)-25, UNIT_SIZE, 50);
		button.setFocusable(false);
		button.setBackground(Color.CYAN);
		label.setBounds(UNIT_SIZE * 3, (UNIT_SIZE/2)+25, UNIT_SIZE, 50);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setOpaque(true);
		label.setBackground(Color.GREEN);
		turnLabel.setBounds(UNIT_SIZE * 3, UNIT_SIZE*2-50, UNIT_SIZE, 50);
		turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		turnLabel.setOpaque(true);
		turnLabel.setBackground(Color.GREEN);
		nowTurn.setBounds(UNIT_SIZE * 3, UNIT_SIZE*2, UNIT_SIZE, 50);
		nowTurn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		nowTurn.setHorizontalAlignment(SwingConstants.CENTER);
		nowTurn.setOpaque(true);
		button.addActionListener(this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	private void draw(Graphics g) {
		for(int i = UNIT_SIZE; i <= UNIT_SIZE * 3; i += UNIT_SIZE) {
			g.setColor(Color.WHITE);
			g.drawLine(i, 0, i, (int) (UNIT_SIZE * 3));
		}
		for(int i = UNIT_SIZE; i <= UNIT_SIZE * 3; i += UNIT_SIZE) {
			g.setColor(Color.WHITE);
			g.drawLine(0, i, (int) (UNIT_SIZE * 3), i);
		}
	}
	
	private void checkSlot(int slot) {
		if(slot > 8 || slot < 0) return;
		if(grid[slot] == ' ') {
			grid[slot] = turn;
			drawFigure(slot, turn);
			if(turn == 'X') turn = 'O';
			else turn = 'X';
			nowTurn.setText("" + turn);
		}else
			label.setText("This shell is not empty " + slot);
	}
	
	private void drawFigure(int slot, char turn) {
        Graphics g = this.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5.0F));
        g.setColor(Color.WHITE);
        int multiple = slot;
		if(turn == 'X') {
			if(slot >= 0 && slot <= 2) {
				g.drawLine(UNIT_SIZE*multiple+10, 10, UNIT_SIZE*(multiple+1)-10, UNIT_SIZE-10);
				g.drawLine(UNIT_SIZE*(multiple+1)-10, 10, UNIT_SIZE*multiple+10, UNIT_SIZE-10);
			}
			if(slot >= 3 && slot <= 5) {
				g.drawLine(UNIT_SIZE*(multiple-3)+10, UNIT_SIZE+10, UNIT_SIZE*((multiple-3)+1)-10, UNIT_SIZE*2-10);
				g.drawLine(UNIT_SIZE*(multiple-3+1)-10, UNIT_SIZE+10, UNIT_SIZE*(multiple-3)+10, UNIT_SIZE*2-10);
			}
			if(slot >= 6 && slot <= 8) {
				g.drawLine(UNIT_SIZE*(multiple-6)+10, UNIT_SIZE*2+10, UNIT_SIZE*((multiple-6)+1)-10, UNIT_SIZE*3-10);
				g.drawLine(UNIT_SIZE*(multiple-6+1)-10, UNIT_SIZE*2+10, UNIT_SIZE*(multiple-6)+10, UNIT_SIZE*3-10);
			}
		}
		if(turn == 'O') {
			if(slot >= 0 && slot <= 2) g.drawOval(UNIT_SIZE*multiple+5, 5, UNIT_SIZE-10, UNIT_SIZE-10);
			if(slot >= 3 && slot <= 5) g.drawOval(UNIT_SIZE*(multiple-3)+5, UNIT_SIZE+5, UNIT_SIZE-10, UNIT_SIZE-10);
			if(slot >= 6 && slot <= 8) g.drawOval(UNIT_SIZE*(multiple-6)+5, UNIT_SIZE*2+5, UNIT_SIZE-10, UNIT_SIZE-10);
		}
		
		gameLogic();
		
	}
	
	private void gameLogic() {
		int pos;
		//Rows
		pos = 0;
		for(int i = 0; i < 3; i++) {
			if(grid[pos] == grid[pos+1] && grid[pos+1] == grid[pos+2] && grid[pos] != ' ') winner(grid[pos]);
			pos += 3;
		}
		//Columns
		pos = 0;
		for(int i = 0; i < 3; i++) {
			if(grid[pos] == grid[pos+3] && grid[pos+3] == grid[pos+6] && grid[pos] != ' ') winner(grid[pos]);
			pos += 1;
		}
		//Diagonal
		if(grid[0] == grid[4] && grid[4] == grid[8] && grid[0] != ' ') winner(grid[0]);
		if(grid[2] == grid[4] && grid[4] == grid[6] && grid[2] != ' ') winner(grid[2]);
		if(gameOver != true) gameOver();
	}
	
	private void winner(char winner) {
		Graphics2D g = (Graphics2D) this.getGraphics();
		System.out.println("Winner is: " + winner);
		g.setColor(Color.CYAN);
		g.setFont( new Font("Monaco",Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Winner - " + winner, (SCREEN_WIDTH - metrics.stringWidth("Winner - " + winner))/2, SCREEN_HEIGHT - 50);
		gameOver = true;
	}
	
	private void gameOver() {
		Graphics g = this.getGraphics();
		int count = 0;
		for(int i = 0; i < 9; i++) {
			if(grid[i] == ' ') count++;
		}
		if(count != 0) return;
		
		g.setColor(Color.CYAN);
		g.setFont( new Font("Monaco",Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Tie", (SCREEN_WIDTH - metrics.stringWidth("Tie"))/2, SCREEN_HEIGHT - 50);
		gameOver = true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < 9; i++) {
			grid[i] = ' ';
		}
		repaint();
		gameOver = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(gameOver == true) return;
        x = e.getX();
        y = e.getY();
        
        if(x < UNIT_SIZE && y < UNIT_SIZE) position = 0;
        if((x > UNIT_SIZE && y < UNIT_SIZE) && x < UNIT_SIZE*2) position = 1;
        if((x > UNIT_SIZE*2 && y < UNIT_SIZE) && x < UNIT_SIZE*3) position = 2;
        
        if((x < UNIT_SIZE && y < UNIT_SIZE*2) && y > UNIT_SIZE) position = 3;
        if((x < UNIT_SIZE*2 && y < UNIT_SIZE*2) && (y > UNIT_SIZE && x > UNIT_SIZE)) position = 4;
        if((x < UNIT_SIZE*3 && y < UNIT_SIZE*2) && (y > UNIT_SIZE && x > UNIT_SIZE*2)) position = 5;
        
        if(x < UNIT_SIZE && y > UNIT_SIZE*2 && y < UNIT_SIZE*3) position = 6;
        if(x > UNIT_SIZE && x < UNIT_SIZE*2 && y > UNIT_SIZE*2 && y < UNIT_SIZE*3) position = 7;
        if(x > UNIT_SIZE*2 && x < UNIT_SIZE*3 && y > UNIT_SIZE*2 && y < UNIT_SIZE*3) position = 8;
        
        label.setText("" + position);
        
        checkSlot(position);
        
        position = -1;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
