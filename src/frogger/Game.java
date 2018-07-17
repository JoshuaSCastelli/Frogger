/*Name:             Joshua Castelli
 *Date Created:     2/1/16
 *Date Modified:    3/6/16
 *Description:      Frogger Program
 */
package frogger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Game class creates the JFrame, Timer, and Pond object for the game. It contai
 * ns the Game() constructor that instantiates the JFrame called "window", the 
 * Timer called "timer" and the Pond object called "mainScreen". 
 * @author Joshua
 */
public class Game {
    public JFrame window;
    public Timer timer;
    public Pond mainScreen;
    
    /**
     * Game instantiates the JFrame, Timer, and Pond objects. Sets window size 
     * to a constant that includes the size of the frame. This makes the playing
     * field 600x600 pixels, making it easier to move around the board. The 
     * timer is set up so that each time it ticks, TimerListener is called which 
     * updates the Coordinates and Graphics of the board/frog/cars/logs.
     */
    public Game() {
        window = new JFrame ("Frogger");
        window.setSize(606,629);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        
        //create timer
        timer = new Timer(10, new TimerListener());
        
        mainScreen = new Pond();
        window.add(mainScreen);
        window.setVisible(true);
        
        //start timer
        timer.start();
        mainScreen.requestFocus();
    }
    /**
     * TimerListener listens for the timer and calls updateCoords() and repaint
     * () every time the timer ticks. This updates the coordinates of all the 
     * graphics on the board which includes: Cars, Logs, frog. 
     */
    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae){
            mainScreen.updateCoords();
            mainScreen.repaint();
        }
    }
}
