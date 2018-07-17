/*Name:             Joshua Castelli
 *Date Created:     2/1/16
 *Date Modified:    3/6/16
 *Description:      Frogger Program
 */
package frogger;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * Pond is a class that creates the Points that will serve as the placeholders 
 * for the locations of all the moving graphics. It also, creates a Random
 * object, Color object, integer array for the speed of the cars, boolean values
 * to keep track of which direction the frog is moving, and an integer value for 
 * the number of lives remaining. Pond also contains the following methods: 
 * -Pond()
 * -paintComponent - paints the graphics to the board
 * -updateCoords - updates the coordinates of the graphics pieces
 * -actionPerformed - unused
 * -keyTyped - unused
 * -keyPressed - Listens for which arrow key is pressed to move the frog.
 * -keyReleased - unused
 * -Reset - Resets the game board and subtracts a life if mistakes are made.
 * -winner - Detects when you reach the opposite end of the board. Win message.
 * -carCollision - Detects when the frog collides with a car.
 * -pondCollision - Detects when the frog collides with the pond.
 * -edgeCollision - Detects if the frog moves too far off the screen.
 * -leftEdgeCollision - Detects when the frog is in an invalid location close to
 * the left edge of the screen and resets the frog in a valid location.
 * -rightEdgeCollision - Detects when the frog is in an invalid location close 
 * to the right edge of the screen and resets the frog in a valid location.
 * @author Joshua
 */
public class Pond extends JPanel implements ActionListener, KeyListener{
    private Point[][] logLoc;
    private Point[][] carLoc;
    private Point[] frogLoc;
    private Point frogCenter;
    private Random rand;
    private Color[] randColor;
    private int[] randSpeed;
    boolean down,left,right;
    int lives = 3;
        
    /**
     * Pond sets up the point locations for all of graphics. Each moving graphic
     * has many pieces that are moved together as a group. Pond instantiates the 
     * Point location placeholder for each of these pieces: 
     * -locLoc - Each piece of the log graphic.
     * -carLoc - Each piece of the car graphic.
     * -frogLoc - Each piece of the frog graphic.
     * 
     * Pond also instantiates rand, randColor, randSpeed, xLog, and xCar:
     * -rand - A new Random object.
     * -randColor - An array of random colors for each car. 
     * -randSpeed - An array of random speeds for each car.
     * -xLog - An integer to keep track of the x-coordinate of the logs.
     * -xCar - An integer to keep track of the x-coordinate of the cars.
     */
    public Pond(){
        setBackground(Color.BLUE);
        logLoc = new Point[8][2];
        carLoc = new Point[12][16];
        frogLoc = new Point[10];
        rand = new Random();
        randColor = new Color[12];
        randSpeed = new int[6];
        int xLog = 0;
        int xCar = 0;
        
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        
        //random speed for cars
        for(int i=0;i<=5;i++){
            randSpeed[i] = rand.nextInt(6)+2;
        }
        
        
        //random car colors
        for(int i=0;i<=11;i++){
            randColor[i] = new Color(rand.nextInt(255),rand.nextInt(255),
            rand.nextInt(255));
        }

        //frog location of pieces
        frogLoc[0] = new Point(280,550);
        frogCenter = new Point(310,580);
        frogLoc[1] = new Point(280,550);
        frogLoc[2] = new Point(310,550);
        frogLoc[3] = new Point(280,580);
        frogLoc[4] = new Point(310,580);
        frogLoc[5] = new Point(290,560);
        frogLoc[6] = new Point(302,560);
        frogLoc[7] = new Point(295,540);
        
        
        //log locations of pieces
        for(int i = 0; i <= 3; i++){
            for(int j=0;j<=1;j++){
                logLoc[i][j] = new Point(xLog,70);//ending log locations
            }
            xLog+=85;
        }
        
        xLog=535;
        for(int i=4;i<=7;i++){
            for(int j=0;j<=1;j++){
                    logLoc[i][j]= new Point(xLog,490);//starting log locations
                }
            xLog-=85;
        }

        //car location of pieces
        for(int i=0; i<=1; i++){
            for(int j=0;j<=15;j++){
                carLoc[i][j] = new Point(xCar,120);//cars 1 and 2
            }
            xCar+=85;
            }
        
        xCar = 300;
        for(int i=2;i<=3;i++){
            for(int j=0;j<=15;j++){
                carLoc[i][j] = new Point(xCar,180);//3 and 4
            }
            xCar+=85;
        }
        
        xCar=450;
        for(int i=4;i<=5;i++){
            for(int j=0;j<=15;j++){
                carLoc[i][j] = new Point(xCar, 240);// 5 and 6
            }
            xCar+=85;
        }
        
        xCar=50;
        for(int i=6;i<=7;i++){
            for(int j=0;j<=15;j++){
                carLoc[i][j] = new Point(xCar, 300);// 7 and 8
            }
            xCar+=85;
        }
        
        xCar=150;
        for(int i=8;i<=9;i++){
            for(int j=0;j<=15;j++){
                carLoc[i][j] = new Point(xCar, 360);// 9 and 10
            }
            xCar+=85;
        }
        
        xCar=450;
        for(int i=10;i<=11;i++){
            for(int j=0;j<=15;j++){
                carLoc[i][j] = new Point(xCar, 420);// 11 and 12
            }
            xCar+=85;
        }
        
    }
    /**
     * paintComponent Draws all the graphics onto the JFrame. The board is drawn
     * first. Followed by the road, yellow lines, median, logs, cars, and 
     * finally the frog. Depending on the last arrow key pressed, the frog is 
     * drawn facing that direction.
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        //Draw Board
        g.setColor(Color.GREEN.darker().darker());
        g.fillRect(0,0,600,60);
        g.fillRect(0,540,600,60);
        
        //Draw Road
        g.setColor(Color.DARK_GRAY.darker().darker());
        g.fillRect(0, 120, 600, 360);
        
        //Draw yellow lines
        g.setColor(Color.YELLOW);
        for(int line = 5;line<600;line+=30)
            g.fillRect(line, 179, 20, 3);
        for(int line = 5;line<600;line+=30)
            g.fillRect(line, 239, 20, 3);
        for(int line = 5;line<600;line+=30)
            g.fillRect(line, 359, 20, 3);
        for(int line = 5;line<600;line+=30)
            g.fillRect(line, 419, 20, 3);
        
        //Draw Median
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 298, 600, 5);
        
        //Draw Logs/Log Components
        Color brownColor = new Color(164,66,10);
        for(int i=0;i<=7;i++){
            for(int j=0;j<=2;j++){
                switch(j){
                    case 0:
                        g.setColor(brownColor);
                        g.fillRect(logLoc[i][j].x, logLoc[i][j].y, 65, 40);
                        g.setColor(brownColor.darker().darker());
                        g.fillRect(logLoc[i][j].x + 5, logLoc[i][j].y+10, 55,3);
                        break;
                    case 1:
                        g.setColor(brownColor.darker().darker());
                        g.fillRect(logLoc[i][j].x + 5, logLoc[i][j].y+30, 55,3);
                        break;
                }
            }
        }

        //Draw Cars/Car Components
        for(int i=0;i<=11;i++){
            for(int j=0;j<=15;j++){
                switch(j){
                    case 0: 
                        g.setColor(randColor[i]);
                        g.fillRect(carLoc[i][j].x+5, carLoc[i][j].y+15, 55, 5);
                        break;
                    case 1: 
                        g.fillRect(carLoc[i][j].x+5, carLoc[i][j].y+40, 55,5);
                        break;
                    case 2: 
                        g.fillRect(carLoc[i][j].x,carLoc[i][j].y+20,65,20);
                        break;
                    case 3: 
                        g.setColor(randColor[i].darker().darker());
                        g.fillRect(carLoc[i][j].x+25,carLoc[i][j].y+25,
                                20,10);
                        break;
                    case 4:
                        g.drawLine(carLoc[i][j].x+20,carLoc[i][j].y+40,
                                carLoc[i][j].x+25,carLoc[i][j].y+35);
                        break;
                    case 5:
                        g.drawLine(carLoc[i][j].x+20,carLoc[i][j].y+20,
                                carLoc[i][j].x+25,carLoc[i][j].y+25);
                        break;
                    case 6:
                        g.drawLine(carLoc[i][j].x+45,carLoc[i][j].y+35,
                                carLoc[i][j].x+50,carLoc[i][j].y+40);
                        break;
                    case 7:
                        g.drawLine(carLoc[i][j].x+45, carLoc[i][j].y+25,
                                carLoc[i][j].x+50,carLoc[i][j].y+20);
                        break;
                    case 8:
                        g.drawLine(carLoc[i][j].x+20,carLoc[i][j].y+20,
                                carLoc[i][j].x+20,carLoc[i][j].y+40);
                        break;
                    case 9:
                        g.drawLine(carLoc[i][j].x+20,carLoc[i][j].y+20,
                                carLoc[i][j].x+50,carLoc[i][j].y+20);
                        break;
                    case 10:
                        g.drawLine(carLoc[i][j].x+20,carLoc[i][j].y+40,
                                carLoc[i][j].x+50,carLoc[i][j].y+40);
                        break;
                    case 11:
                        g.drawLine(carLoc[i][j].x+50,carLoc[i][j].y+20,
                                carLoc[i][j].x+50,carLoc[i][j].y+40);
                        break;
                    case 12:
                        g.setColor(Color.BLACK);
                        g.fillRect(carLoc[i][j].x+10,carLoc[i][j].y+15,15,5);
                        break;
                    case 13:
                        g.fillRect(carLoc[i][j].x+10,carLoc[i][j].y+40,15,5);
                        break;
                    case 14:
                        g.fillRect(carLoc[i][j].x+40,carLoc[i][j].y+15,15,5);
                        break;
                    case 15:
                        g.fillRect(carLoc[i][j].x+40,carLoc[i][j].y+40,15,5);
                        break;
                }
            }
        }
        
        g.setColor(Color.BLACK);
        g.drawString("Lives: "+lives, 10, 20);
        
        
        //Draw Frog
        if(left==true){
            g2.rotate(-3.14/2,frogLoc[0].x+20,frogLoc[0].y+20);
        }
        if(right==true){
            g2.rotate(3.14/2,frogLoc[0].x+20,frogLoc[0].y+20);
        }
        if(down==true){
            g2.rotate(3.14,frogLoc[0].x+20,frogLoc[0].y+20);
        }
        
        g2.setColor(Color.RED);
        g2.fillRoundRect(frogLoc[7].x,frogLoc[7].y, 10,20,10,20);
        g2.setColor(Color.GREEN);
        g2.fillRoundRect(frogLoc[0].x, frogLoc[0].y, 40, 40, 40, 40);
        g2.fillRoundRect(frogLoc[1].x, frogLoc[1].y, 10, 10, 10, 10);
        g2.fillRoundRect(frogLoc[2].x, frogLoc[2].y, 10, 10, 10, 10);
        g2.fillRoundRect(frogLoc[3].x, frogLoc[3].y, 10, 10, 10, 10);
        g2.fillRoundRect(frogLoc[4].x, frogLoc[4].y, 10, 10, 10, 10);
        g2.setColor(Color.BLACK);
        g2.fillRoundRect(frogLoc[5].x, frogLoc[5].y, 8, 8, 8, 8);
        g2.fillRoundRect(frogLoc[6].x,frogLoc[6].y, 8, 8, 8, 8);
        
    }
    /**
     * updateCoords updates the point locations of each graphic. Each time the 
     * timer ticks, the coordinates are updated for the logs, cars, and frog. 
     * The logs move at a constant speed. Logs at the beginning move -2 pixels
     * per tick. Logs at the end move +5 pixels per tick. The cars move at 
     * random speeds between 1-6 pixels per tick. Whenever an object moves off
     * the screen it's location is reset to the opposite of the screen out of 
     * view so that it can enter the frame cleanly. Each time a car resets this
     * way, it gets a new random speed. Each time updateCoords is called it 
     * calls carCollision, pondCollision, and edgeCollision.
     * -carCollision - checks if the frog is colliding with a car and resets if 
     * true
     * -pondCollision - checks if the frog is colliding with the pond and resets
     * if true.
     * -edgeCollision - checks if the frog moves too far off the screen on a log
     * and resets if true.
     */
    public void updateCoords(){
        
        //logs direction and speed.
        for(int i = 0; i<= 7; i++){//logs at end
            if(i<=3){
                for(int j=0;j<=1;j++){
                    if(logLoc[i][j].x+10 <= 600){
                        logLoc[i][j].x += 5;
                    }
                    else{
                        logLoc[i][j].x = -65;
                    }
                }   
            }
            
            else{//logs at start
                for(int j=0;j<=1;j++){
                    if(logLoc[i][j].x+65 >= 0){
                        logLoc[i][j].x -= 2;
                    }
                    else{
                        logLoc[i][j].x = 600;
                    }
                }
            }
        } 
        //cars direction and speed and collision
        for(int i = 0; i<= 12; i++){
            if(i>=0&&i<=1){
                for(int j=0;j<=15;j++){
                    if(carLoc[i][j].x+10 <= 750){//cars 1 and 2
                        carLoc[i][j].x += randSpeed[0];
                    }
                    else{
                        randSpeed[0]= rand.nextInt(6)+1;
                        carLoc[i][j].x = -150;
                    }
                }
            }
            if(i>=2&&i<=3){
                for(int j=0;j<=15;j++){
                    if(carLoc[i][j].x >= -150){//cars 3 and 4
                        carLoc[i][j].x -= randSpeed[1];
                    }
                    else{
                        randSpeed[1]= rand.nextInt(6)+1;
                        carLoc[i][j].x = 750;
                    }
                }
            }
            if(i>=4&&i<=5){
                for(int j=0;j<=15;j++){
                    if(carLoc[i][j].x+10 <=750){//cars 5 and 6
                        carLoc[i][j].x += randSpeed[2];
                    }
                    else{
                        randSpeed[2]= rand.nextInt(6)+1;
                        carLoc[i][j].x = -150;
                    }
                }
            }
            if(i>=6&&i<=7){
                for(int j=0;j<=15;j++){
                    if(carLoc[i][j].x+10 >= -150){//cars 7 and 8
                        carLoc[i][j].x -= randSpeed[3];
                    }
                    else{
                        randSpeed[3]= rand.nextInt(6)+1;
                        carLoc[i][j].x = 750;
                    }
                }
            }
            if(i>=8&&i<=9){
                for(int j=0;j<=15;j++){
                    if(carLoc[i][j].x+10 <= 750){//cars 9 and 10
                        carLoc[i][j].x += randSpeed[4];
                    }
                    else{
                        randSpeed[4]= rand.nextInt(6)+1;
                        carLoc[i][j].x = -150;
                    }
                }
            }
            if(i>=10&&i<=11){
                for(int j=0;j<=15;j++){
                    if(carLoc[i][j].x+10 >= -150){//cars 11 and 12
                        carLoc[i][j].x -= randSpeed[5];
                    }
                    else{
                        randSpeed[5]= rand.nextInt(6)+1;
                        carLoc[i][j].x = 750;
                    }
                }
            }
        }
        carCollision();
        pondCollision();
        edgeCollision();
    }
    /**
     * unused
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
       
    }
    /**
     * unused
     * @param e 
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }
    /**
     * keyTyped listens for arrow keys to be pressed and updates frogLoc 
     * accordingly. Each movement of the frog is incremented by 60 pixels(1/10
     * of the game board size). Each time the up arrow key is pressed the 
     * y-coordinate of the frog is checked; if it has reached the opposite side
     * of the board Winner is called and the game is won. Each time the left or
     * right arrow key is pressed leftEdgeCollision or rightEdgeCollision is 
     * called accordingly. If the frogCenter is in an invalid location on the 
     * board the frog is turned away from the edge and reset to a valid location
     * 
     * @param e 
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            down=false;left=false;right=false;
            for(int i=0;i<=7;i++){
                if(frogLoc[i].y>40){
                    frogLoc[i].y-=60;
                    frogCenter.y-=60;
                    if(frogLoc[7].y<60){
                        Winner();
                    }
                }
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down=true;left=false;right=false;
            for(int i=0;i<=7;i++){
                if(frogLoc[i].y<540){
                    frogLoc[i].y+=60;
                    frogCenter.y+=60;
                }
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left=true;right=false;down=false;
            if(frogCenter.x>550){
                left=true;right=false;
                rightEdgeReset();
            }
            
            else if(frogCenter.x<70){
                right=true;left=false;
                leftEdgeReset();
            }
            else if(frogCenter.x>70){
                for(int i=0;i<=7;i++){    
                    frogLoc[i].x-=60;
                }
                frogCenter.x-=60;
            }
            
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right=true;left=false;down=false;
            if(frogCenter.x<70){
                right=true;left=false;
                leftEdgeReset();
            }
            else if(frogCenter.x>550){
                left=true;right=false;
                rightEdgeReset();
            }
            else if(frogCenter.x<540){
                for(int i=0;i<=7;i++){
                    frogLoc[i].x+=60;
                }
                frogCenter.x+=60;
            }
        }
    }
    /**
     * unused
     * @param e 
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }
    /**
     * Reset is called when a mistake in the game is made (falls in water, hit 
     * by car, edge collision) This reduces the remaining lives by 1 and resets
     * the frogLoc/frogCenter to the initial position. When lives = 0 the game 
     * is over and user is prompted to play again. Otherwise the game closes.
     * If user plays again frogLoc, frogCenter, lives, and direction are reset.
     */
    public void Reset(){
        int decide;
        lives--;
        if(lives==0){
            decide = JOptionPane.showConfirmDialog(null, "GAME OVER!! Play agai"
                    + "n?");
            switch(decide){
                case -1:
                    System.exit(0);
                case 0:
                    lives=3;
                    down=false;
                    left=false;
                    right=false;
                    frogCenter = new Point(310,580);
                    frogLoc[0] = new Point(280,550);
                    frogLoc[1] = new Point(280,550);
                    frogLoc[2] = new Point(310,550);
                    frogLoc[3] = new Point(280,580);
                    frogLoc[4] = new Point(310,580);
                    frogLoc[5] = new Point(290,560);
                    frogLoc[6] = new Point(302,560);
                    frogLoc[7] = new Point(295,540);
                    repaint();
                    break;
                case 1:
                    System.exit(0);
                    break;
                case 2: 
                    System.exit(0);
                    break;    
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Ouch!\nLives left: "+lives);
            down=false;
            left=false;
            right=false;
            frogCenter = new Point(310,580);
            frogLoc[0] = new Point(280,550);
            frogLoc[1] = new Point(280,550);
            frogLoc[2] = new Point(310,550);
            frogLoc[3] = new Point(280,580);
            frogLoc[4] = new Point(310,580);
            frogLoc[5] = new Point(290,560);
            frogLoc[6] = new Point(302,560);
            frogLoc[7] = new Point(295,540);
            repaint();
        }
    }
    /**
     * Winner is a dialog for when the frog reaches the opposite side of the 
     * board unharmed. User is prompted to play again or the game closes. If 
     * user plays again the frogLoc, frogCenter, lives, and direction are reset 
     * to initial conditions.
     */
    public void Winner(){
        int decide;
        decide = JOptionPane.showConfirmDialog(null,"Congrats you Won!\nPlay ag"
                + "ain?");
        switch(decide){
            case -1:
                System.exit(0);
            case 0:
                down=false;
                left=false;
                right=false;
                lives=3;
                frogCenter = new Point(310,580);
                frogLoc[0] = new Point(280,550);
                frogLoc[1] = new Point(280,550);
                frogLoc[2] = new Point(310,550);
                frogLoc[3] = new Point(280,580);
                frogLoc[4] = new Point(310,580);
                frogLoc[5] = new Point(290,560);
                frogLoc[6] = new Point(302,560);
                frogLoc[7] = new Point(295,540);
                break;
            case 1:
                System.exit(0);
                break;
            case 2: 
                System.exit(0);
                break;    
        }
    }
    /**
     * carCollision is called every time coordinates are updated to check if the
     * frog is colliding with a car. If so, Reset() is called.
     */
    public void carCollision(){
        for(int i=0;i<=12;i++){
            if(i<=1){
                for(int j=0;j<=15;j++){
                    for(int y=0;y<=7;y++){
                        if(frogLoc[y].y<180&&frogLoc[y].y>120){
                            if(carLoc[i][j].x <= frogLoc[y].x && 
                                    frogLoc[y].x <= carLoc[i][j].x+65){
                                Reset();
                            }
                        }
                    }
                }
            }
            if(i>1&&i<=3){
                for(int j=0;j<=15;j++){
                    for(int y=0;y<=7;y++){
                        if(frogLoc[y].y<240&&frogLoc[y].y>180){
                            if(carLoc[i][j].x <= frogLoc[y].x && 
                                    frogLoc[y].x <= carLoc[i][j].x+65){
                                Reset();
                            }
                        }
                    }
                }
            }
            if(i>3&&i<=5){
                for(int j=0;j<=15;j++){
                    for(int y=0;y<=7;y++){
                        if(frogLoc[y].y<300&&frogLoc[y].y>240){
                            if(carLoc[i][j].x <= frogLoc[y].x && 
                                    frogLoc[y].x <= carLoc[i][j].x+65){
                                Reset();
                            }
                        }
                    }
                }
            }
            if(i>5&&i<=7){
                for(int j=0;j<=15;j++){
                    for(int y=0;y<=7;y++){
                        if(frogLoc[y].y<360&&frogLoc[y].y>300){
                            if(carLoc[i][j].x <= frogLoc[y].x && 
                                    frogLoc[y].x <= carLoc[i][j].x+65){
                                Reset();
                            }
                        }
                    }
                }
            }
            if(i>7&&i<=9){
                for(int j=0;j<=15;j++){
                    for(int y=0;y<=7;y++){
                        if(frogLoc[y].y<420&&frogLoc[y].y>360){
                            if(carLoc[i][j].x <= frogLoc[y].x && 
                                    frogLoc[y].x <= carLoc[i][j].x+65){
                                Reset();
                            }
                        }
                    }
                }
            }
            if(i>9&&i<=11){
                for(int j=0;j<=15;j++){
                    for(int y=0;y<=7;y++){
                        if(frogLoc[y].y<480&&frogLoc[y].y>420){
                            if(carLoc[i][j].x <= frogLoc[y].x && 
                                    frogLoc[y].x <= carLoc[i][j].x+65){
                                Reset();
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * pondCollision is called every time coordinates are updated to check if th
     * e frog is in water. If the frog jumps in water rather than on a log Reset
     * () is called.
     */
    public void pondCollision(){
        if(frogLoc[0].y<120&&frogLoc[0].y>30){
            if(frogCenter.x>logLoc[0][0].x&&frogCenter.x<logLoc[0][0].x+85){
                for(int i=0;i<=7;i++){
                    frogLoc[i].x += 5;
                }
                frogCenter.x+= 5;
            }
            else if(frogCenter.x>logLoc[1][0].x&&frogCenter.x<logLoc[1][0].x+85){
                for(int i=0;i<=7;i++){
                    frogLoc[i].x += 5;
                }
                frogCenter.x+= 5;
            }
            else if(frogCenter.x>logLoc[2][0].x&&frogCenter.x<logLoc[2][0].x+85){
                for(int i=0;i<=7;i++){
                    frogLoc[i].x += 5;
                }
                frogCenter.x+= 5;
            }
            else if(frogCenter.x>logLoc[3][0].x&&frogCenter.x<logLoc[3][0].x+85){
                for(int i=0;i<=7;i++){
                    frogLoc[i].x += 5;
                }
                frogCenter.x+= 5;
            }
            else{
                repaint();
                Reset();
            }   
        }
        
        if(frogLoc[0].y>=480&&frogLoc[0].y<540){
            if(frogCenter.x>logLoc[7][0].x&&frogCenter.x<logLoc[7][0].x+85){
                for(int i=0;i<=7;i++){
                    frogLoc[i].x -= 2;
                }
                frogCenter.x-=2;
            }
            else if(frogCenter.x>logLoc[6][0].x&&frogCenter.x<logLoc[6][0].x+85){
                for(int i=0;i<=7;i++){
                    frogLoc[i].x -= 2;
                }
                frogCenter.x-=2;
            }
            else if(frogCenter.x>logLoc[5][0].x&&frogCenter.x<logLoc[5][0].x+85){
                for(int i=0;i<=7;i++){
                    frogLoc[i].x -= 2;
                }
                frogCenter.x-=2;
            }
            else if(frogCenter.x>logLoc[4][0].x&&frogCenter.x<logLoc[4][0].x+85){
                for(int i=0;i<=7;i++){
                    frogLoc[i].x -= 2;
                }
                frogCenter.x-=2;
            }
            else{
                repaint();
                Reset();
            }  
        }
    }
    /**
     * edgeCollision is called to check if the frog hits the edge of the screen.
     * If so, Reset() is called.
     */
    public void edgeCollision(){
        if(frogLoc[0].x+20>600||frogLoc[0].x+20<0){
            Reset();
        }
    }
    /**
     * leftEdgeReset is called when the frog is in a location outside the left b
     * oundry of the playing field. It turns the frog around and resets him insi
     * de the boundries.
     */
    public void leftEdgeReset(){
        frogLoc[0].x=40;
        frogLoc[1].x=40;
        frogLoc[2].x=70;
        frogLoc[3].x=40;
        frogLoc[4].x=70;
        frogLoc[5].x=50;
        frogLoc[6].x=62;
        frogLoc[7].x=55;
        frogCenter.x=70;
    }
     /**
     * rightEdgeReset is called when the frog is in a location outside the right
     * boundry of the playing field. It turns the frog around and resets him ins
     * ide the boundry.
     */
    public void rightEdgeReset(){
        frogLoc[0].x=520;
        frogLoc[1].x=520;
        frogLoc[2].x=550;
        frogLoc[3].x=520;
        frogLoc[4].x=550;
        frogLoc[5].x=530;
        frogLoc[6].x=542;
        frogLoc[7].x=535;
        frogCenter.x=550;
    }
}