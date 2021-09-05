import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {




private final static int boardWidth = 1000;
private final static int boardHeight = 980;
private int score =0;


private final static int pixelSize = 25;

private final static int totalPixels = (boardWidth * boardHeight)/(pixelSize * pixelSize);

private boolean inGame = true;

private Timer timer;

private static int speed = 70;                     

private Snake snake = new Snake();
private Food food = new Food();
private Image tayfun;
private Image mehmet;
private Image sinasi;
private Image engin;

private CmpeIns cmpe = new CmpeIns();
private Assistant asis = new Assistant();
private OtherIns other = new OtherIns();
private OtherIns2 other2 = new OtherIns2();

public Board() {

    addKeyListener(new Keys());
    setBackground(Color.BLACK);
    setFocusable(true);

    setPreferredSize(new Dimension(boardWidth, boardHeight));
    loadImages();
    initializeGame();
}

protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    draw(g);
}

private void loadImages() {

    ImageIcon first = new ImageIcon("C:\\Users\\User\\Desktop\\tayfun.png");
    tayfun = first.getImage();
    ImageIcon second = new ImageIcon("C:\\Users\\User\\Desktop\\mehmet.png");
    mehmet = second.getImage();
    ImageIcon third = new ImageIcon("C:\\Users\\User\\Desktop\\sinasi.png");
    sinasi = third.getImage();
    ImageIcon fourth = new ImageIcon("C:\\Users\\User\\Desktop\\engin.png");
    engin = fourth.getImage();
    
}

void draw(Graphics g) {
   
    if (inGame == true) {
        startGame(g);
        //draw scores
        g.setColor(Color.YELLOW);
        g.setFont(new Font("SANSSERIF",Font.BOLD,20));
        g.drawString("Score: " + score, 880, 50);
        score = snake.getParts();
        
      
        if(snake.getParts()%3 == 0 ){
         g.drawImage(tayfun, cmpe.getFoodX(), cmpe.getFoodY(), tayfun.getWidth(this)/10, tayfun.getHeight(this)/10, this);   
        }
        else if(snake.getParts()%5 == 0){
            g.drawImage(mehmet,asis.getFoodX(), asis.getFoodY(), mehmet.getWidth(this)/3, mehmet.getHeight(this)/3, this);
        }
        else if (snake.getParts() == 7) {
        	g.drawImage(sinasi, other.getFoodX(), other.getFoodY(), sinasi.getWidth(this)/3, sinasi.getHeight(this)/3, this);
        }
        else if (snake.getParts()== 8) {
        	g.drawImage(engin, other2.getFoodX(), other2.getFoodY(), engin.getWidth(this)/3, engin.getHeight(this)/3, this);
        }
        
        
        g.setColor(Color.red);
        g.fillOval(food.getFoodX(), food.getFoodY(), pixelSize, pixelSize); // food

        // Draw our snake.
        for (int i = 0; i < snake.getParts(); i++) {
            // Snake's head
            if (i == 0) {
                g.setColor(Color.GREEN);
                //g.fillRect(snake.getSnakeX(i), snake.getSnakeY(i),  pixelSize, pixelSize);
                g.fillOval(snake.getSnakeX(i), snake.getSnakeY(i),  pixelSize, pixelSize);
                // Body of snake
            } else {
                g.fillOval(snake.getSnakeX(i), snake.getSnakeY(i),pixelSize, pixelSize);
            }
        }
        Toolkit.getDefaultToolkit().sync();
    } else {
        // If we're not alive, then we end our game
        endGame(g);
    }
}



void initializeGame() {
    snake.setParts(3); // set our snake's initial size

    // Create our snake's body
    for (int i = 0; i < snake.getParts(); i++) {
        snake.setSnakeX(boardWidth / 2);
        snake.setSnakeY(boardHeight / 2);
    }
    // Start off our snake moving right
    snake.setMovingRight(true);

    // Generate our first 'food'
    food.createFood();
    cmpe.createFood();
    //asis.createFood();

    // set the timer to record our game's speed / make the game move
    timer = new Timer(speed, this);
    timer.start();
}

// if our snake is in the close proximity of the food..

    
void checkFoodCollisions() {

    if ((proximity(snake.getSnakeX(0), food.getFoodX(), 20)) && (proximity(snake.getSnakeY(0), food.getFoodY(), 20))) {
    	
        System.out.println("intersection");
        // Add a 'joint' to our snake
        snake.setParts(snake.getParts() + 1);
        // Create new food
     
        
        if (snake.getParts()==7)
        {
        	other2.createFood();
        }
        else if(snake.getParts()%3 == 0 )
        {
        	food.createFood();  
        	cmpe.createFood();
        }
        else if (snake.getParts()%5==0)
        {
        	food.createFood();  
        	asis.createFood();
        }
       
        	
 
    }
    else if ((proximity(snake.getSnakeX(0), cmpe.getFoodX(), 20)) && (proximity(snake.getSnakeY(0), cmpe.getFoodY(), 20)))
    {
    	        System.out.println("intersection with cmpe");
                snake.setParts(snake.getParts()+3);
                if (snake.getParts()==7)
                {
                	other2.createFood();
                }
                else if(snake.getParts()%3 == 0 )
                {
                	cmpe.createFood();
                }
                else if (snake.getParts()%5==0)
                {
                	asis.createFood();
                }
                else 
                	food.createFood();
         
                
    }
    else if ((proximity(snake.getSnakeX(0), other2.getFoodX(), 20)) && (proximity(snake.getSnakeY(0), other2.getFoodY(), 20)))
    {
    	System.out.println("intersection with engin");
        snake.setParts(snake.getParts()+1);
        Board.setSpeed(20);  
        other.createFood();
        
    }
    else if ((proximity(snake.getSnakeX(0), other.getFoodX(), 20)) && (proximity(snake.getSnakeY(0), other.getFoodY(), 20)))
    {
    	System.out.println("intersection with sinasi");
        snake.setParts(snake.getParts()+1);
        Board.setSpeed(70);  
        food.createFood();
        
    }
    else if ((proximity(snake.getSnakeX(0), asis.getFoodX(), 20)) && (proximity(snake.getSnakeY(0), asis.getFoodY(), 20)))
    {
    	System.out.println("intersection with assistant");
    	inGame = false;
    
    	 
    }
    if (!inGame) 
    timer.stop();
        
}

// Used to check collisions with snake's self and board edges
void checkCollisions() {

    // If the snake hits its' own joints..
    for (int i = snake.getParts(); i > 0; i--) {

        // Snake cant intersect with itself if it's not larger than 5
        if ((i > 5)
                && (snake.getSnakeX(0) == snake.getSnakeX(i) && (snake                                        //4ten sonraki yerleri kontrol et carpip carpmamasini
                        .getSnakeY(0) == snake.getSnakeY(i)))) {
            inGame = false; // then the game ends
        }
    }

    // If the snake intersects with the board edges..
    if (snake.getSnakeY(0) >= boardHeight) {
        inGame = false;
    }

    if (snake.getSnakeY(0) < 0) {
        inGame = false;
    }

    if (snake.getSnakeX(0) >= boardWidth) {
        inGame = false;
    }

    if (snake.getSnakeX(0) < 0) {
        inGame = false;
    }

    // If the game has ended, then we can stop our timer
    if (!inGame) {
        timer.stop();
    }
}

public void startGame(Graphics g){
    String message = "AlteredSnake";
    Font font = new Font (Font.SANS_SERIF,Font.BOLD,30);
    FontMetrics metrics = getFontMetrics(font);
    g.setColor(Color.YELLOW);
    g.setFont(font);
    g.drawString(message, (boardWidth- metrics.stringWidth(message)) / 2, boardHeight / 2);
    System.out.println("Starting screen ");
}

void endGame(Graphics g) {

    // Create a message telling the player the game is over
    String message = "Game over" ;                                       //use different variations
    String yourScore = "Your score: " +snake.getParts();
    // Create a new font instance
    //Font font = new Font("Times New Roman", Font.BOLD, 30);
    Font font = new Font(Font.SANS_SERIF, Font.BOLD, 40);
    FontMetrics metrics = getFontMetrics(font);

    // Set the color of the text to red, and set the font
    g.setColor(Color.yellow);
    g.setFont(font);

    // Draw the message to the board
    g.drawString(message, (boardWidth- metrics.stringWidth(message)) / 2, boardHeight / 2);
    font = new Font(Font.SANS_SERIF,Font.BOLD,20);
    g.setFont(font);
    g.drawString(yourScore, 430, 540);
    metrics = getFontMetrics(font);
    System.out.println("Game Ended");

}

// Run constantly as long as we're in game.
@Override
public void actionPerformed(ActionEvent e) {
    if (inGame == true) {

        checkFoodCollisions();
        checkCollisions();
        snake.move();

        //System.out.println(snake.getSnakeX(0) + " " + snake.getSnakeY(0)
               // + " " + food.getFoodX() + ", " + food.getFoodY());
        
        
        String out= (snake.getSnakeX(0) + " " + snake.getSnakeY(0)+ " " + food.getFoodX() + ", " + food.getFoodY());
        if(snake.getParts()%3==0)
        {
        	out += (" ," + cmpe.getFoodX()+ ", " + cmpe.getFoodY());
        }
        else if (snake.getParts()%5==0)
        {
        	out += (" ," + asis.getFoodX()+ ", " + asis.getFoodY());
        }
        System.out.println(out);
        
    }
    
    // Repaint or 'render' our screen
    repaint();
}

private class Keys extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT) && (!snake.isMovingRight())) {
            snake.setMovingLeft(true);
            snake.setMovingUp(false);
            snake.setMovingDown(false);
        }

        if ((key == KeyEvent.VK_RIGHT) && (!snake.isMovingLeft())) {
            snake.setMovingRight(true);																//!!! durdurma ve çapraz eklenebilir
            snake.setMovingUp(false);
            snake.setMovingDown(false);
        }

        if ((key == KeyEvent.VK_UP) && (!snake.isMovingDown())) {
            snake.setMovingUp(true);
            snake.setMovingRight(false);
            snake.setMovingLeft(false);
        }

        if ((key == KeyEvent.VK_DOWN) && (!snake.isMovingUp())) {
            snake.setMovingDown(true);
            snake.setMovingRight(false);
            snake.setMovingLeft(false);
        }

        if ((key == KeyEvent.VK_ENTER) && (inGame == false)) {

            
             inGame = true;
            snake.setMovingDown(false);
            snake.setMovingRight(false);
            snake.setMovingLeft(false);
            snake.setMovingUp(false);
            initializeGame();
        }
    }
}

private boolean proximity(int a, int b, int closeness) {
    return Math.abs((long) a - b) <= closeness;
}

public static int getAllDots() {
    return totalPixels;
}

public static int getDotSize() {
    return pixelSize;
}

public int getSpeed() {
	return speed;
}

public static void setSpeed(int speed) {
	Board.speed = speed;
}


}
