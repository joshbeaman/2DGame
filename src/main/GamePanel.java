package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	//SCREEN SETTINGS
	
	final int originalTileSize = 16; //means 16 x 16 tile(player character)
	final int scale = 3; 
	
	public final int tileSize = originalTileSize * scale; //48 x 48 (Actual tile size)
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // (768 pixels)
	public final int screenHeight = tileSize * maxScreenRow; // (576 pixels)
	
	// WORLD SETTINGS
	
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;

	
	// FPS
	int FPS = 60;
	
	
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler(); 
	Thread gameThread;
	public CollisionChecker cChecker = new CollisionChecker(this);
	
	public Player player = new Player(this,keyH); 
	
	//Set a player's default position
	
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
		
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
		
		
	}


	@Override
	public void run() { //create the game loop in this which is the core of the game
		
		
		double drawInterval = 1000000000/FPS; //This is called the "Sleep method", every 0.01666 seconds
		double nextDrawTime = System.nanoTime() + drawInterval; 
		
		
		while(gameThread != null) {
			 
			
			// 1 Update: Update information such as character position
			update();
			
			
			// 2 Draw: Draw the screen for the updated information
			repaint(); //This calls paint component method
			
			
			try {
				
				double remainingTime = nextDrawTime - System.nanoTime(); // Need to "Sleep" for the remaining time
				remainingTime = remainingTime / 1000000;
				
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime); //This sleep is what pauses the game loop
			 
				nextDrawTime += drawInterval;
				
				
				
			} catch (InterruptedException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void update() {
		
		player.update();
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g; //This changes the graphics g to 2D graphics g2
		
		
		tileM.draw(g2);
		
		player.draw(g2);
		
		g2.dispose(); //good practice to save memory
		
		
		
	}
	
	
}
