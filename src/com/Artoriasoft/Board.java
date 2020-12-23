package com.Artoriasoft;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.w3c.dom.css.RGBColor;

public class Board extends JLayeredPane implements ActionListener, Runnable {

    private Timer timer;
    private Player player;
    private DungeonManager dungeonManager;
    private HUD hud;
    private Menu menu;
    private Map map;
    private BossManager boss;
    private final int DELAY = 10;
    static int camX, camY; /* coordinate telecamera (vengono aggiornate nel doDrawing()) */
    private int rectAlpha;
    private EnemyManager enemymanager;
    private boolean isRunning, reset;
    
    //Thread locale per la gestione degli update;
    private Thread gameThread = null;
    private long threadTimer;

    public Board() {
    	
    	addKeyListener(new TAdapter());
    	
        setFocusable(true);
        setBackground(new Color(97, 129, 143));
        setOpaque(true);
        
        JPanel HUD = new JPanel();
        this.add(HUD, 1);

        menu = new Menu();
        dungeonManager = new DungeonManager();
        enemymanager = new EnemyManager();
        player = new Player("src/new_asset_player.png", 500,300, 44);
        hud = new HUD(0, 0);
        map = new Map();
        boss = new BossManager();
        
        camX = (player.getX() - mainclass.screenSize.width/2 + player.width/2);
        camY = (player.getY()- mainclass.screenSize.height/2 + player.height/2);
        rectAlpha = 0;
        
        isRunning = true;
        reset = false;

        timer = new Timer(DELAY, this);
        timer.start();
        
        /* Inizializzazione del thread al metodo run() della classe */
        //gameThread = new Thread(this);
        //gameThread.start();
        
        threadTimer = System.currentTimeMillis();

    }

	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
         
        reset();
        
        doLogic();
        
        doDrawing(g);
        
        Toolkit.getDefaultToolkit().sync();
    }
    
    public void reset(){
    	if(!reset)
    		return;
    	
    	player.health = player.maxHealth;
    	player.setX(500);
    	player.setY(300);
    	player.recallPositions();
    	
    	camX = (player.getX() - mainclass.screenSize.width/2 + player.width/2);
        camY = (player.getY()- mainclass.screenSize.height/2 + player.height/2);
        
    	enemymanager.protocolloTabulaRasa();
    	enemymanager.removeDead();
    	if(boss.bosses.size() != 0){
    		boss.bosses.clear();
    	}
    	
    	map.resetMap();
        menu.reset();
        
        dungeonManager.reset();
        
        reset = false;
    }
    
    public void doLogic(){    	
    	player.update();
		
		if(dungeonManager.setIndex(player.feet)){
			if(dungeonManager.index != dungeonManager.path.size()-1 && dungeonManager.index != 0 && !dungeonManager.getCurrentVisited()){
				addEnemies(3);
			}
			else if(dungeonManager.index == dungeonManager.path.size()-1 && !dungeonManager.getCurrentVisited()){
				boss.addBoss(dungeonManager.getCurrentX(), dungeonManager.getCurrentY());
			}
			dungeonManager.setCurrentVisited();
		}
	
		dungeonManager.setAllClear((enemymanager.getEnemies() == null || enemymanager.getEnemies().isEmpty()) && (boss.bosses == null || boss.bosses.isEmpty()));
    	buttonManager();
    	
    	
        check_damaged();
        check_enemy();
        enemymanager.removeDead(); 
        
        if(!menu.inEditorMode && map.areas.size() == 1 && menu.buttons.get(0).getCurrentFrame() != 1)
        	menu.isNotOnButton(1, player.feet);
	
        if(player.health <= 0){
        	menu.inDeathScreen = true;
        	if(!boss.bosses.isEmpty())
        		boss.bosses.get(0).activate = false;
        	player.stopMovements();
        }    
        
        if(dungeonManager.path.size() > 1 && dungeonManager.index == dungeonManager.path.size()-1 && boss.bosses.isEmpty()){
        	menu.inVictoryScreen = true;
        	player.stopMovements();
        }
        
    }
	
	private void doDrawing(Graphics g) {
    	
    	Graphics2D g2d = (Graphics2D) g;
        
        g.translate(-camX,-camY);
        hud.setPosition(camX, camY);
        hud.setBossHUDPosition(camX, camY);
        dungeonManager.drawDungeon(g2d);
        
        menu.draw(g2d);
        
        /* disegno oggetti stanza */
        dungeonManager.drawDungeonTorchesLower(g2d, player.feet);
        
        if(!enemymanager.enemies.isEmpty()){
        	for(int i = 0; i < enemymanager.enemies.size(); i++)
        		if(enemymanager.enemies.get(i).feet.height < player.feet.height)
        			dungeonManager.drawDungeonLower(g2d, enemymanager.enemies.get(i).feet);
        }
        enemymanager.draw_enemy_lower(g2d, player.feet);
        
        if(!boss.bosses.isEmpty() && boss.bosses.get(0).feet.height <= player.feet.height)
        	dungeonManager.drawDungeonLower(g2d, boss.bosses.get(0).feet);
        boss.drawBossLower(g2d, player.feet);
        
        dungeonManager.drawDungeonLower(g2d, player.feet);
        player.drawAnimation(g2d);
        
        if(!boss.bosses.isEmpty() && boss.bosses.get(0).feet.height > player.feet.height)
        	dungeonManager.drawDungeonLower(g2d, boss.bosses.get(0).feet);
        boss.drawBossUpper(g2d, player.feet);
        
        if(!enemymanager.enemies.isEmpty()){
        	for(int i = 0; i < enemymanager.enemies.size(); i++)
        		if(enemymanager.enemies.get(i).feet.height >= player.feet.height)
        			dungeonManager.drawDungeonLower(g2d, enemymanager.enemies.get(i).feet);
        }
        enemymanager.draw_enemy_upper(g2d, player.feet);
        
        dungeonManager.drawDungeonUpper(g2d, player.feet);
        dungeonManager.drawDungeonTorchesUpper(g2d, player.feet);
        /* fine disegno oggetti stanza */
        
        hud.drawHUD(g2d, player.health, player.maxHealth, player.stamina, player.maxStamina);
               
        if (boss.bosses.size() != 0 && boss.bosses.get(0).activate){
        	//g2d.drawRect(boss.bosses.get(0).feet.x, boss.bosses.get(0).feet.y, boss.bosses.get(0).feet.width-boss.bosses.get(0).feet.x, boss.bosses.get(0).feet.height-boss.bosses.get(0).feet.y);
        	hud.drawBossHUD(g2d, boss.bosses.get(0).health, boss.bosses.get(0).maxHealth);
        }
        
        dungeonManager.setAllVisible();
        //dungeonManager.resetIndexRoom();
        
        if(boss.bosses.isEmpty() && !menu.inEditorMode)
    		cameraManager(player.x + player.width/2, player.y + player.height/2, 120);
    	else if(!boss.bosses.isEmpty())
    		cameraManager(((player.x + player.width/2) + (boss.bosses.get(0).x + boss.bosses.get(0).width/2))/2, ((player.y + player.height/2) + (boss.bosses.get(0).y + boss.bosses.get(0).height/2))/2, 0);
    	else if(menu.inEditorMode){
    		cameraManager(dungeonManager.getCurrentBounds().width/2, dungeonManager.getCurrentBounds().height/2, 0);
    		
            menu.drawDarkScreen(g2d);
            map.drawMap(g2d);
            menu.drawEditorLegends(g2d, map.getCurrentIndexL() == -1, map.getCurrentIndexR() == -1,	map.getCurrentIndexU() == -1, map.getCurrentIndexD() == -1);
    	}
        
        
        menu.drawEndGameScreen(g2d);
        
        menu.drawSplashScreen(g2d);
        
    }
	
	/* metodo run() gestito dal gameThread */
	
	@Override
	public void run() {	
		while(isRunning){
			if(System.currentTimeMillis() > threadTimer + DELAY){
				threadTimer = System.currentTimeMillis();
				
				player.update();
			
				if(dungeonManager.setIndex(player.feet)){
					if(dungeonManager.index != dungeonManager.path.size()-1 && dungeonManager.index != 0 && !dungeonManager.getCurrentVisited()){
						addEnemies(3);
					}
					else if(dungeonManager.index == dungeonManager.path.size()-1 && !dungeonManager.getCurrentVisited()){
						boss.addBoss(dungeonManager.getCurrentX(), dungeonManager.getCurrentY());
					}
					dungeonManager.setCurrentVisited();
				}
			
				dungeonManager.setAllClear((enemymanager.getEnemies() == null || enemymanager.getEnemies().isEmpty()) && (boss.bosses == null || boss.bosses.isEmpty()));
	        	buttonManager();
	        	
	        	check_enemy();
		        check_damaged();
		        //enemymanager.removeDead(); 
		        
		        if(!menu.inEditorMode && map.areas.size() == 1 && menu.buttons.get(0).getCurrentFrame() != 1)
		        	menu.isNotOnButton(1, player.feet);
			
		        if(player.health <= 0)
		        	menu.inDeathScreen = true;
		        
			} 
		}     
	}
	
	public void buttonManager(){
		
		if(!menu.inExitSubMenu){
			if(menu.isOnButton(0, player.feet)){
        		player.y += 4;
        		player.equipment.setPositions(player.x, player.y, player.isLeft);
        		menu.buttons.get(1).setCurrentFrame(1); 
        		dungeonManager.generatePath(10);
        	}
        	if(menu.isOnButton(1, player.feet)){
        		player.y += 4;
        		player.equipment.setPositions(player.x, player.y, player.isLeft);
        		player.stopMovements();
        		menu.inEditorMode = true;
        	}
        
        	if(menu.isOnButton(2, player.feet)){
        		player.y += 4;
        		player.equipment.setPositions(player.x, player.y, player.isLeft);
        		menu.setInExitSubMenu();
        	}
        }
		else{
			if(menu.isOnButton(3, player.feet)){
        		player.y += 4;
        		player.equipment.setPositions(player.x, player.y, player.isLeft);
        		System.exit(0);
        	}
			
			if(menu.isOnButton(4, player.feet)){
        		player.y += 4;
        		player.equipment.setPositions(player.x, player.y, player.isLeft);
        		menu.setInExitSubMenu();
        	}
		}
        
	}
	
	public void check_damaged(){
		if(enemymanager.getEnemies() != null && !enemymanager.getEnemies().isEmpty()){
			if (enemymanager.getEnemies() != null){
    			for (int i = 0; i < enemymanager.getEnemies().size(); i++){
    				if(player.generateDamage(enemymanager.getEnemies().get(i).getBounds()))
    					enemymanager.getEnemies().get(i).decreaseHealt(2);
    				if(enemymanager.getEnemies().get(i).generateDamage(player.getBounds()))
    					player.getDamaged(1);
    			}   		
    		}
		}
		
		if (boss.bosses.size() != 0 && boss.bosses.get(0).activate ){
			if(player.generateDamage(boss.bosses.get(0).feet))
					boss.bosses.get(0).decreaseHealt(2);
		}
	}
	
	public void addEnemies(int n){
		if(enemymanager.getRoom()){
			int cont = n;
			while (cont > 0){
				enemymanager.add_enemy(dungeonManager.getCurrentBounds().x, dungeonManager.getCurrentBounds().y, dungeonManager.getCurrentBounds().width, dungeonManager.getCurrentBounds().height);
				cont--;
			}
		    
			//enemymanager.setRoomFalse(false);
		}
	}
    
    public void check_enemy(){
    	if (enemymanager.getEnemies() != null && !enemymanager.getEnemies().isEmpty()){
    		for (int i = 0; i < enemymanager.getEnemies().size(); i++){
    			if (dungeonManager.getCurrentOffset() != null)
    				enemymanager.move_enemy(player, enemymanager.getEnemies().get(i), dungeonManager.getCurrentBounds(), dungeonManager.getCurrentOffset());
    			else 
    				enemymanager.move_enemy2(player, dungeonManager.getCurrentBounds(), enemymanager.getEnemies().get(i));
    			enemymanager.getEnemies().get(i).staminaManager();
    		}
    	}
    	
    	if (boss.bosses.size() != 0 && boss.bosses.get(0).activate)
    		boss.BossIA(player, dungeonManager.getCurrentBounds(), boss.bosses.get(0));
    }
    
    public void cameraManager(int xPos, int yPos, int offsetSide){
    	//int timeX = Math.abs(camX-xPos)/4, timeY = Math.abs(camY-yPos)/4;
    	//int speedX = Math.abs(camX-xPos)/40, speedY = Math.abs(camY-yPos)/40;
    	int speedX = 4, speedY = 4;
    	
    	if(offsetSide == 0){
    		if(xPos > camX + mainclass.screenSize.width/2)
    			camX += speedX/2;
    		if(xPos <= camX + mainclass.screenSize.width/2)
    			camX -= speedX/2;
    		if(yPos > camY + mainclass.screenSize.height/2)
    			camY += speedY/2;
    		if(yPos <= camY + mainclass.screenSize.height/2)
    			camY -= speedY/2;
    		return;
    	}
    	
    	if(player.isMoving){
    		if(xPos > camX + mainclass.screenSize.width/2 + offsetSide )
    			camX += speedX;
    		if(xPos <= camX + mainclass.screenSize.width/2 - offsetSide )
    			camX -= speedX;
    		if(yPos > camY + mainclass.screenSize.height/2 + offsetSide )
    			camY += speedY;
    		if(yPos <= camY + mainclass.screenSize.height/2 - offsetSide )
    			camY -= speedY;
    	}
    	
    	if(!player.isMoving){
    		if((xPos > camX + mainclass.screenSize.width/2))
    			camX += speedX/2;
    		if((xPos <= camX + mainclass.screenSize.width/2))
    			camX -= speedX/2;
    		if((yPos > camY + mainclass.screenSize.height/2))
    			camY += speedY/2;
    		if((yPos <= camY + mainclass.screenSize.height/2))
    			camY -= speedY/2;
    	}
    	
    	//System.out.println(camY);
    }

    public void actionPerformed(ActionEvent e) {
    	
    	if(!menu.inEditorMode){
    		player.move(dungeonManager.getCurrentBounds(), dungeonManager.getCurrentOffset(), enemymanager,
        		dungeonManager.currentLeftBridge(), dungeonManager.currentUpBridge(), dungeonManager.currentRightBridge(), dungeonManager.currentDownBridge(), boss);
    	}
    	
        repaint();  
    }

    private class TAdapter extends KeyAdapter {
    	
        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
            
            if(menu.inEditorMode)
            	map.pressed = false;
        }

        @Override
        public void keyPressed(KeyEvent e) {
        	
        	if(menu.inDeathScreen || menu.inVictoryScreen){
        		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        			System.exit(0);
        		if(e.getKeyCode() == KeyEvent.VK_ENTER){
        			reset = true;
        		}
        			
        	}
        	
        	if(!menu.inEditorMode && !menu.inDeathScreen && !menu.inSplashScreen && !menu.inVictoryScreen)
        		player.keyPressed(e);
        	else{
        		map.moveMap(e);
        		map.addArea(e);
        		map.changeSkin(e);
        			
        		if(e.getKeyCode() == KeyEvent.VK_ESCAPE || (e.getKeyCode() == KeyEvent.VK_ENTER && map.areas.size() == 1)){
        			menu.inEditorMode = false;
        			menu.rectAlpha = 0;
        			map.resetMap();
        		}
        		if(e.getKeyCode() == KeyEvent.VK_ENTER && map.areas.size() > 1){
        			menu.inEditorMode = false;
        			menu.rectAlpha = 0;
        			menu.buttons.get(0).setCurrentFrame(1); 
        			dungeonManager.generatePathFormMap(map);
        		}
        	}
        	
        	

        }
    }
    
}
