import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePannel extends JPanel implements KeyListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE);

    //SNAKE POSITION ARRAYS
    final int x[] =  new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;

    int delay = 150; //snake speed
char direction = 'R';
boolean running = false;
Timer timer;

int foodX, foodY;
int score = 0;

    GamePannel(){
        
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.addKeyListener(this); // keylistener to pannel
        this.setFocusable(true);
        this.requestFocusInWindow();

        //snake starting body
        for(int i =0; i<bodyParts;i++){
            x[i]= 100-(i*UNIT_SIZE);
            y[i] = 100;
        }

        startGame();

    }
       
    public void startGame(){
        running = true;
        generateFood();        

        timer = new Timer(delay , new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(running){
                move();
                checkFood();
                checkCollision();
            }
                repaint();
            }
        });
        timer.start();
    }


    //generation of food (Apple)

    public void generateFood(){
        foodX = (int)(Math.random()*((SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE));
        foodY = (int)(Math.random() * (SCREEN_HEIGHT - 100 / UNIT_SIZE));
    System.out.println("Food Position: X=" + foodX + " Y=" + foodY);
}

    // movement of snake 
    public void move() {
        for(int i = bodyParts ; i>0 ; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction){
            case 'U':
            y[0] -= UNIT_SIZE;
            break;
            case 'D':
            y[0] += UNIT_SIZE;
            break;
            case 'L':
            x[0] -= UNIT_SIZE;
            break;
            case 'R':
            x[0] += UNIT_SIZE;
            break;
        }
    }

            public void checkCollision() {
                // wall collison-left/right
                if (x[0]< 0 || x[0] >= SCREEN_WIDTH){
                    running = false;
                    
                }  
                //wall collison - top/bottom
                if(y[0]<0 || y[0] >= SCREEN_HEIGHT){
                    running = false;
                } 

                if (!running){
                    timer.stop();
                    System.out.println("GAME OVER ! SNAKE HIT THE WALL !!!!");
                }

             }

             public void checkFood(){    
                Rectangle snakeHead = new Rectangle(x[0], y[0], UNIT_SIZE, UNIT_SIZE);
                Rectangle food = new Rectangle(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
                
                
                if ( snakeHead.intersects(food)){
                    bodyParts++;
                    score += 10;
                    generateFood();
                    System.out.print("Snake : ( " + x[0] + "," + y[0] + ") | Food : (" + foodX + "," + foodY + ")");
                }
             }



    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }

    // added Graphics

    public void draw(Graphics g) {
        if (running){
        for(int i = 0; i< SCREEN_HEIGHT/UNIT_SIZE;i++){
            g.setColor(Color.gray);
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE,SCREEN_HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
        g.setColor(Color.red);
    g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.BOLD , 20));
    g.drawString("Score: " + score , 10 , 20);    
    System.out.println("Food Position: X=" + foodX + " Y=" + foodY);
 

}
        //Draw  Snake

         for (int i =0 ; i<bodyParts; i++){
            if(i==0){
                g.setColor(Color.green);

            }
            else{
                g.setColor(new Color(45,180,0));
            }
            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
         }

         
    } else {
        showGameOver(g);

    }
}

 public void showGameOver(Graphics g) {
    g.setColor(Color.red);
    g.setFont(new Font("Ink Free", Font.BOLD, 50));
    FontMetrics metrics = getFontMetrics(g.getFont());
    g.drawString("GAME OVER", (SCREEN_WIDTH - metrics.stringWidth("GAME OVER")) / 2, SCREEN_HEIGHT / 2);
} 

//KEYLISTENR METHODS

@Override
public void keyPressed(KeyEvent e){
     switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (direction != 'R') direction = 'L';
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L') direction = 'R';
                break;
            case KeyEvent.VK_UP:
                if (direction != 'D') direction = 'U';
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U') direction = 'D';
                break;

        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used but must be implemented
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used but must be implemented
    }


}
