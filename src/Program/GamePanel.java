package Program;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Entities.Player;
import Tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	Thread gameThread;
	
	KeyHandler keyH = new KeyHandler();
	Player player = new Player(this,keyH);
	TileManager tile = new TileManager(this);
	
	final int originalTileSize = 16;
	final int scale = 3;
		
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	
	int FPS = 60;
	
	
	
	public GamePanel()
	{
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	public void startGameThread()
	{
		gameThread = new Thread(this);
		gameThread.start();
	}
	/*
	@Override
	public void run() 
	{
		double drawInterval = 1000000000/FPS;// 0.0166666
		double nextDrawTime = System.nanoTime() + drawInterval;
		while(gameThread != null) {		
			update();
			
			repaint();
		    
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				
				remainingTime /= 1000000;
				
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				Thread.sleep((long) remainingTime); <- Dont have a good precision
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}*/
	@Override
	public void run() 
	{
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {		
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
		   
		}
	}
	public void update()
	{
		player.update();
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		tile.draw(g2);//O metodo draw  precisa ser instanciado antes do player por conta das layers
		player.draw(g2);
		
		g2.dispose();
	}
	
}