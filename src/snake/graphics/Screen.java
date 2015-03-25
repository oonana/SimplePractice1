package snake.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import entities.Apple;
import entities.BodyPart;

public class Screen extends JPanel implements Runnable, KeyListener { //jpanel is used to group components together, the jpanel is then added to the jframe. a swing gui doesn't necessarily need a jpanel

	private static final long serialVersionUID = 1L; //i don't know what this does
	public static final int WIDTH = 600, HEIGHT = 600;
	private Thread thread; //we need a new thread for later
	private boolean running = false; 
	
	private BodyPart b;
	private ArrayList<BodyPart> snake;
	
	private Apple apple;
	private ArrayList<Apple> apples;
	
	private Random r;
	
	private int xCoor = 10, yCoor = 10;
	private int size = 5;
	
	private boolean right = true, left = false, up = false, down = false;
	
	private int ticks = 0;
	
	//private Key key;
	
	
	public Screen() {
		setFocusable(true);
		//key = new Key();
		addKeyListener(this);
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT)); //sets the jpanel's dimensions and makes that the preferred size
		
		snake = new ArrayList<BodyPart>(); //a list of snake body parts
		apples = new ArrayList<Apple>(); //a list of apples
		r = new Random();
		
		start(); //the game will start as soon as the game/screen is created
	}
	public void tick() { //used for updating
		if(snake.size() == 0) { //if the size of the snake is 0 (there is no snake) we create a new snake body part
			b = new BodyPart(xCoor, yCoor, 10);
			snake.add(b);
			
		}
		
		if(apples.size() == 0) { //creating a new apple
			int xCoor = r.nextInt(59);
			int yCoor = r.nextInt(59);
			
			apple = new Apple(xCoor, yCoor, 10);
			apples.add(apple);
			
		}
		
		/*for(int i = 0; i < snake.size(); i++) {
			if(xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor()) {
				if(i != snake.size() - 1) {
					stop();
				}
			}
		}*/
		
		if(xCoor < 0 || xCoor > 59 || yCoor < 0 || yCoor > 59) {
			stop();
		}
		
		for(int i = 0; i < apples.size(); i++) { //for removing the apple when it gets eaten?
				if(xCoor == apples.get(i).getxCoor() && yCoor == apples.get(i).getyCoor()) {
				size++;
				apples.remove(i);
				i--;
				}
		}
		
		
		ticks++;
		
		if(ticks > 0) {
			if(right) {
				xCoor++;
			}
			if(left) {
				xCoor--;
			}
			if(up) {
				yCoor--;
			}
			if(down) {
				yCoor++;
			}
			
			ticks = 0;
			
			b = new BodyPart(xCoor, yCoor, 10);
			snake.add(b);
			
			if(snake.size() > size) {
				snake.remove(0);
			}
		}
		
		
	}
	
	public void paint(Graphics g){
		g.clearRect(0, 0, WIDTH, HEIGHT); //drawing the grid
		
		g.setColor(Color.GRAY);
		
		for(int i = 0; i < WIDTH / 10; i++) {
			g.drawLine(i * 10, 0, i * 10, HEIGHT);
			
		}
		
		for(int i = 0; i < HEIGHT; i++) {
			g.drawLine(0, i * 10, WIDTH, i * 10);
			
		}
		
		for(int i = 0; i < snake.size(); i++) { //drawing the snake
			snake.get(i).draw(g);
			
		}
		
		for(int i = 0; i < apples.size(); i++) { //drawing the apple
			apples.get(i).draw(g);
		}
	}

	public void start() { //method for starting the game
		running = true;
		thread = new Thread(this, "Game Loop"); //creates a new thread which gets a screen object and a string
		thread.start(); //starts the thread (runs the method "run()")
		
	}
	
	public void stop() { //method for stopping the game
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		while(running) {
			//tick();
			try {
				tick();
				Thread.sleep(60); //makes the game wait for 60 ms between every tick
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			repaint();
			
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT && !left) {
			up = false;
			down = false;
			right = true;
		}
		
		if(key == KeyEvent.VK_LEFT && !right) {
			up = false;
			down = false;
			left = true;
		}
		
		if(key == KeyEvent.VK_UP && !down) {
			up = true;
			right = false;
			left = false;
		}
		
		if(key == KeyEvent.VK_DOWN && !up) {
			down = true;
			right = false;
			left = false;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
