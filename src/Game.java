import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel {
	
	private JFrame gameFrame;
	private JLabel gameOverLabel,scoreBoard;
	private static final int WINDOW_WIDTH = 600;
	private static int SCORE = 0;
	private static final int WINDOW_HEIGHT = 322;
	private String imagePath = "src/res/";
    private final static int jattX = 80;
    private static int jattY = 230;
    private final static int JATT_MIN_HEIGHT = 230;
    private final static int JATT_MAX_HEIGHT = 160;
    private static int cubeY = 250;
    private static int cubeHeight = 27;
    private static int cubeWidth = 13;
    private static int cubeX = 0;
    private static int cube2X = 0;
    private static int cube3X = 0;
    private static int cube4X = 0;
    Image jatt;
    private static int CUBE_VELOCITY = 5;
    private static boolean out = false;
    
    private void initCubePositions(){
    	cubeX = WINDOW_WIDTH - cubeWidth;
    	Random cubeInterval = new Random();
    	cube2X = cubeX + 100 + cubeInterval.nextInt(100);
    	cube3X = cube2X + 100 + cubeInterval.nextInt(100);
    	cube4X = cube3X + 100 + cubeInterval.nextInt(100);
    }
    
    private void moveCubes() {
			cubeX -= 1;
	        cube2X -= 1;
	        cube3X -= 1;
	        cube4X -= 1;
	        Random cubeInterval = new Random();
	        if(cube2X < 5){
	        	cube2X = cubeX + 100 + cubeInterval.nextInt(100);
	        }
	        if(cubeX < 5){
	        	cubeX = WINDOW_WIDTH - cubeWidth;
	        }
	        if(cube3X < 5){
	        	cube3X = cube2X + 100 + cubeInterval.nextInt(100);
	        }
	        
	        if(cube4X < 5){
	        	cube4X = cube3X + 100 + cubeInterval.nextInt(100);
	        }
	        Game.this.repaint();
	        try {
				Thread.sleep(CUBE_VELOCITY);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    
    
    private void flyJatt(){
    	new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while(jattY >= JATT_MAX_HEIGHT){
						jattY -= 2;
						Game.this.repaint();
						Thread.sleep(9);
					}
					
					while(jattY <= JATT_MIN_HEIGHT){
						jattY += 2;
						Game.this.repaint();
						Thread.sleep(9);
					}
					SCORE += 5;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(!out){
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_ON);
	        g2d.drawImage(jatt, jattX, jattY, 40 , 50, this);
	        g2d.fillRect(cubeX, cubeY, cubeWidth, cubeHeight);
	        g2d.fillRect(cube2X, cubeY, cubeWidth, cubeHeight);
	        g2d.fillRect(cube3X, cubeY, cubeWidth, cubeHeight);
	        g2d.fillRect(cube4X, cubeY, cubeWidth, cubeHeight);
	        int jattYmargin = jattY + 50;
	        int jattXmargin = jattX + 35;
	        if(jattYmargin > cubeY){
	        	if(jattXmargin > (cubeX + 5) && jattX < (cubeX + cubeWidth - 10)){
	        		out = true;
	        	}
	        	
	        	if(jattX > (cube2X + 5) && jattX < (cube2X + cubeWidth - 10)){
	        		out = true;
	        	}
	        	
	        	if(jattX > (cube3X + 5) && jattX < (cube3X + cubeWidth - 10)){
	        		out = true;
	        	}
	        	
	        	if(jattX > (cube4X + 5) && jattX < (cube4X + cubeWidth - 10)){
	        		out = true;
	        	}
	        }
	       scoreBoard.setText("Total Score : "+SCORE);
        }
    }
    
    public Game(){
    	jatt = new ImageIcon(imagePath+"jatt.png").getImage();
    	gameFrame= new JFrame("Flying Jatt");
    	this.setBackground(Color.white);
	    this.setFocusable(true);
        this.requestFocus();
        
        gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setBounds(290,150,20,20);
        gameOverLabel.setVisible(false);
        
        scoreBoard = new JLabel("Total Score : 0");
        scoreBoard.setBounds(290,150,20,20);
        
        
        this.add(scoreBoard);
        this.add(gameOverLabel);
        
        gameFrame.add(this);
        gameFrame.setResizable(false);
        gameFrame.setBackground(Color.white);
        gameFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	Image image = new ImageIcon(imagePath+"bg.jpg").getImage();
    	if (image != null) {
            int imgWidth, imgHeight;
            double contRatio = (double) getWidth() / (double) getHeight();
            double imgRatio =  (double) image.getWidth(this) / (double) image.getHeight(this);

            //width limited
            if(contRatio < imgRatio){
                imgWidth = getWidth();
                imgHeight = (int) (getWidth() / imgRatio);

            //height limited
            }else{
                imgWidth = (int) (getHeight() * imgRatio);
                imgHeight = getHeight();
            }

            //to center
            int x = (int) (((double) getWidth() / 2) - ((double) imgWidth / 2));
            int y = (int) (((double) getHeight()/ 2) - ((double) imgHeight / 2));

            g.drawImage(image, x, y, imgWidth, imgHeight, this);
        }
    }

    public static void main(String[] args) {
    	final Game game = new Game();
    	game.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if(!out){
		            game.flyJatt();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
    	game.initCubePositions();
        while (!out) {
        	game.moveCubes();
        }
        
        game.gameOver();
    }
    
    private void gameOver(){
    	gameOverLabel.setVisible(true);
    }
}