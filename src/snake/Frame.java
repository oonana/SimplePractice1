package snake;

import java.awt.GridLayout;

import javax.swing.JFrame;

import snake.graphics.Screen;

public class Frame extends JFrame { //jframe is a top level container used to represent the minimum requirements for a window. a swing gui needs a jframe

	private static final long serialVersionUID = 1L;
	
	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //program ends and frame closes when x is pressed
		setTitle("Snake"); //title of the frame
		setResizable(false); //user can't change the size of the frame
		
		
		init();
	}
	
	public void init() {
		setLayout(new GridLayout(1, 1, 0, 0)); //sets the layout of the frame to be a grid layout with 1 row and 1 column
		
		Screen s = new Screen(); //creates a new screen object
		add(s); //i guess this adds the screen to the frame
		
		pack(); //automatically sizes the JFrame based on the size of the components
		
		setLocationRelativeTo(null); //will put the frame in the middle of the screen
		setVisible(true); //makes the frame visible
		
	}
	
	public static void main(String args[]) {
		new Frame(); //creates a new frame when program starts
	}

}
