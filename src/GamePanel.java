import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Random;
import java.util.random.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HIGHT = 600;
    static final int UNIT_SIZE    =  25;
    static final int GAME_UNITS   = (SCREEN_WIDTH*SCREEN_HIGHT)/UNIT_SIZE;
    static final int DELAY        =  75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;


    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HIGHT));
        this.setBackground(Color.blue);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running){
        /*
            for(int i=0;i<SCREEN_HIGHT/UNIT_SIZE; i++){
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HIGHT);
                g.drawLine(0,i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }
        */
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
        

            for (int i = 0; i < bodyParts; i++) {
                if (i==0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    if (applesEaten >= 5) {
                        g.setColor(Color.yellow);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    if (applesEaten >= 10) {
                        g.setColor(Color.pink);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    if (applesEaten >= 15) {
                        g.setColor(Color.black);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                }
            }
        }
                else {
                     g.setColor(new Color(45,180,0));
                     //g.setColor(new Color(random.nextInt(255),random.nextInt(255)));
                     g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }  
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        }
        else {
            gameOver(g);
        }
    }
    public void newApple(){
             appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
             appleY = random.nextInt((int)(SCREEN_HIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
        for(int i=bodyParts; i>0; i--)
        {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(direction){
           case 'U':
               y[0] = y[0]-UNIT_SIZE;
               break;
            case 'D':
               y[0] = y[0]+UNIT_SIZE;
               break;
            case 'L':
               x[0] = x[0]-UNIT_SIZE;
               break;
            case 'R':
               x[0] = x[0]+UNIT_SIZE;
               break;
        }
    }
    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void chechCollisions(){
       //this check if head colides with body
        for (int i = bodyParts; i>0; i--) {
           if((x[0] == x[i]) && (y[0]== y[i])){
               running = false;
           }
       }
       //check if head touches left border
       if(x[0] < 0){
           running = false;
       }
       //check if head touches rigt border
       if(x[0] > SCREEN_WIDTH){
           running = false;
       }
       //check if head touches top border
       if(y[0] < 0){
           running = false;
       }
       //check if head touches buttom border
       if (y[0] > SCREEN_HIGHT) {
           running = false;
       }
       if (!running) {
           timer.stop();
       }
    }
    public void gameOver(Graphics g){
        //Score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        //GameOver text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HIGHT/2);
        g.drawString("Your score is "+applesEaten, SCREEN_WIDTH, SCREEN_HIGHT);
    }
    @Override
    public void actionPerformed (ActionEvent e) {
        if(running){
            move();
            checkApple();
            chechCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed (KeyEvent e){
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                     if(direction != 'R'){
                     direction = 'L';
                }
                     break;
                case KeyEvent.VK_RIGHT:
                     if(direction != 'L'){
                     direction = 'R';
                }
                     break;
                case KeyEvent.VK_UP:
                     if(direction != 'D'){
                     direction = 'U';
                }
                     break;
                case KeyEvent.VK_DOWN:
                     if(direction != 'U'){
                     direction = 'D';
                }
                     break;
            }
        }
        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub
            
        }
        
    }
}
