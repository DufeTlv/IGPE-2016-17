package com.Artoriasoft;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.tree.DefaultTreeCellEditor.EditorContainer;

import com.Artoriasoft.BossManager.Boss;

public class EnemyManager {

	long timer;
	private Boolean new_room, is_moving;
	ArrayList<Enemy> enemies;
	
	//BulletManager bulletmanager;
	int vector;
	int orientation;
	
	public EnemyManager(){
		
		orientation = 0;
		new_room = true;
		enemies = new ArrayList<>();
		//bulletmanager = new BulletManager();
		vector = 4;
		//setEnemies();
	}
	
	public void add_enemy(int x, int y, int width, int height){
		Enemy e = new Enemy("src/enemy_1.png", 0, 0, 12);
		
		int random = new Random().nextInt(3);
		
		//System.out.println(path);
		
		if (enemies.size() == 1){
				e.x = width - 150;
				e.y = y;
		}
		
		if (enemies.size() == 0){
			e.x = width - 200;
			e.y = y + 250;
	}
		
		if (enemies.size() == 2){
			e.x = width - 250;
			e.y = y + 700;
	}
		
		
	
			//e.x = new Random().nextInt(Math.abs(width - owidth)) + (x);
			//e.y = new Random().nextInt(Math.abs((height-88) - oheight) + 1) + oheight;
	
		is_moving = false;
		e.setAnimation(0);
		long second = System.currentTimeMillis();
		e.timer = second;
		enemies.add(e);
		
		Collections.sort(enemies, new Comparator<AnimatedObject>() {
			public int compare(AnimatedObject o1, AnimatedObject o2) {
				return o1.y <= o2.y ? -1 : o1.y == o2.y ? 0 : 1;
			}
		}
		);
		
	}
	
	public void protocolloTabulaRasa(){
		for(int i = 0; i < enemies.size();i++){
			enemies.get(i).health = 0;
		}
	}
	
	public void draw_enemy_upper(Graphics2D g, Rectangle p){
		
		if(enemies.isEmpty() || enemies == null)
			return;
		
		for (int i = 0; i < enemies.size(); i++){
			if(p.height <= (enemies.get(i).y + enemies.get(i).height - 12))
				enemies.get(i).drawEnemy(g);
		}
		
	}
	
	public void draw_enemy_lower(Graphics2D g, Rectangle p){
		
		if(enemies.isEmpty() || enemies == null)
			return;
		
		for (int i = 0; i < enemies.size(); i++){
			if(p.height > (enemies.get(i).y + enemies.get(i).height - 12))
				enemies.get(i).drawEnemy(g);
		}
	}
	
	public void removeDead(){
		int index = -1;
		for (int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).health <= 0)
				index = i;
		}
		
		if(index != -1){
			enemies.get(index).bulletmanager.bullet.vis = false;
			enemies.remove(index);
			
		}
	}
	
	public void move_enemy2(Player p, Rectangle room, Enemy b){
			
		
		if (b.getCurrentFrame() < 8){
			b.timer = System.currentTimeMillis();
			return;
		}
		
		//System.out.println("eccomi");
	
	
		b.setAnimation(1);
	
		if (System.currentTimeMillis() - 1000 < b.timer2){
			//timer2 = System.currentTimeMillis();
			return;
		}
		
		
		
		
		if (b.bulletmanager.bullets.size() != 0){
			b.shoot = true;
			for (int i = 0; i < b.bulletmanager.bullets.size(); i++){
				if (b.bulletmanager.bullets.get(i).direction == "right"){
					b.bulletmanager.normalBulletIAright(b, room, p, b.bulletmanager.bullets.get(i));
				}
				else if (b.bulletmanager.bullets.get(i).direction == "left"){
					b.bulletmanager.normalBulletIALeft(b, room, p, b.bulletmanager.bullets.get(i));
					
				}
			}
		}
		
		else if (b.bulletmanager.bullets.size() == 0 && b.shoot && b.cont == 0){
			b.cont++;
			b.timer3 = System.currentTimeMillis();
		}
		
		
		if (System.currentTimeMillis() - 500 > b.timer3 && b.shoot && b.cont > 0){
			generatePosition(b, p, room);
			b.setAnimation(0);
			b.shoot = false;
			b.cont = 0;
			
			return;
		}
		
		
		if (b.bulletmanager.bullets.size() == 0 && !b.shoot){
			b.bulletmanager.addEnemy2Bullets(b, b.bulletmanager);	
			//System.out.println("eccomi");
		}
		
		b.shoot = true;
			
		//if (System.currentTimeMillis() - 5000 > timer2)
			//b.setAnimation(0);
		
		
		
	}
	

	public void generatePosition(Enemy b, Player p, Rectangle r){
		int randomNumX = 0;
		int randomNumY = 0;
	
		Random random = new Random();
		
		//ran
		randomNumX = random.nextInt(Math.abs((r.width-b.width)-r.x + 1)) + r.x;
		randomNumY = random.nextInt(Math.abs((r.height-b.height)-r.y + 1)) + r.y;

		b.x = randomNumX;
		b.y = randomNumY;
				

		b.feet.setBounds(b.x + 8, (int)(b.y+b.height-(3 * 4)), b.x+b.width-8, b.y+b.height);
		
		
	}
	
	
	public void move_enemy(Player p, Enemy e, Rectangle room, Rectangle offset){

		/* 
		 * sx stand = 1
		 * dx stand = 0
		 * right movement = 2
		 * left movement = 3
		 *  */
		
		if (e.getCurrentFrame() < 8)
			return;
		
		
		
		if (e.shoot && e.bulletmanager.bullets.size() > 0){
			for (int i = 0; i < e.bulletmanager.bullets.size(); i++){
				if (e.bulletmanager.bullets.get(i).right)
					e.bulletmanager.normalBulletIAright(e, room, p, e.bulletmanager.bullets.get(i));
				else if (e.bulletmanager.bullets.get(i).left)
					e.bulletmanager.normalBulletIALeft(e, room, p, e.bulletmanager.bullets.get(i));
			}
		}
		
		long second = System.currentTimeMillis();
	
		
		if (e.saveAnimation != 0){
			

			
			if (e.totalRange < e.dx){
				
				if (!e.left_right && !e.vertical){
					if ((e.x - e.width - 4 >= p.x + p.width) || (e.x < p.x) || (p.y > e.y + e.height) || (e.y > p.y + p.height)){
						e.x -= 4;
						e.totalRange+=4;
						//System.out.println("movimento a sinistra");
						e.feet.setBounds(e.x + 8, (int)(e.y+e.height-(3 * 4)), e.x+e.width-8, e.y+e.height);
						
						return;
					}
					
					e.totalRange = 4;
					e.saveAnimation = 0;
					e.feet.setBounds(e.x + 8, (int)(e.y+e.height-(3 * 4)), e.x+e.width-8, e.y+e.height);
					
					return;
					
				}
				
				else if (e.left_right && !e.vertical){
					if ((e.x + e.width + 4 < p.x || p.x < e.x) || (p.y > e.y + e.height) || (e.y > p.y + p.height)){
						e.x += 4;
						e.totalRange += 4;
						//System.out.println("movimento a destra");
						e.feet.setBounds(e.x + 8, (int)(e.y+e.height-(3 * 4)), e.x+e.width-8, e.y+e.height);
						
						return;
					}
					e.totalRange = 4;
					e.saveAnimation = 0;
					e.feet.setBounds(e.x + 8, (int)(e.y+e.height-(3 * 4)), e.x+e.width-8, e.y+e.height);
					e.timer2 = System.currentTimeMillis();
					return;
				}
				
			}
			if (e.totalRange >= e.dx){
				e.totalRange = 4;
				e.saveAnimation = 0;
				e.feet.setBounds(e.x + 8, (int)(e.y+e.height-(3 * 4)), e.x+e.width-8, e.y+e.height);
				e.timer2 = System.currentTimeMillis();
			}
			return;
		}
		
		if (second > e.timer2 + 1000){
			
		e.left_right = randomic_movement();
		e.setAnimation(1);
		
		if (e.x  + 800 > p.x && (p.x > e.x) && (e.y + 800 > p.y && p.y > e.y || e.y - 800 < p.y && p.y < e.y)
				|| (e.x  - 800 < p.x) && (p.x < e.x) && (e.y + 800 > p.y && p.y > e.y || e.y - 800 < p.y && p.y < e.y)){
			if (e.bulletmanager.bullets.size() == 0)
				attacking(p, e, room);
			else if(e.bulletmanager.bullets.get(0).spawnEnd)
				attacking(p, e, room);
			e.shoot = true;
		}
		
	
		if (!e.left_right){					//left movement
		
			if ((e.feet.x - e.width - e.dx >= room.x)){
				if (!((e.y > offset.height) || (offset.y > e.y + e.height))){
					if (e.x - e.dx >= offset.width || offset.x > e.x){
						e.saveAnimation = 2;
						e.feet.setBounds(e.x + 8, (int)(e.y+e.height-(3 * 4)), e.x+e.width-8, e.y+e.height);
						e.timer2 = System.currentTimeMillis();
						return;
					}
				else 
					return;
			}
				
				e.saveAnimation = 2;
				//e.x -= 4;
				
			}
		}
		
		if (e.left_right){					//right movement
				
			//System.out.println(offset.x + " offset di x " +  (e.x + e.dx) + " proiezione enemy");
			if((e.feet.x + e.width + e.dx < room.width)){
				if (!((e.y > offset.height) || (offset.y > e.y + e.height))){
					if (offset.x > e.x + e.width + e.dx || offset.width < e.x){
						e.saveAnimation = 2;
						e.feet.setBounds(e.x + 8, (int)(e.y+e.height-(3 * 4)), e.x+e.width-8, e.y+e.height);
						e.timer2 = System.currentTimeMillis();
						return;
					}
					else 
						return;
				}
				
				e.saveAnimation = 2;
				
					//e.x += e.dx;			
			}
			
		}

		
		e.feet.setBounds(e.x + 8, (int)(e.y+e.height-(3 * 4)), e.x+e.width-8, e.y+e.height);
		e.timer2 = System.currentTimeMillis();
		}
		
	}
	
	public Boolean randomic_movement(){
		
		Random random = new Random();
		Boolean answer = random.nextBoolean();
		return answer;		
	}
	
	
	public void attacking(Player p, Enemy e, Rectangle room){
		for (int i = 0; i < 2; i++){
				if (p.x > e.x){
					e.bulletmanager.addbullet(e, room);
					e.bulletmanager.bullets.get(i).right = true;
					e.bulletmanager.bullets.get(i).left = false;
				}
				else if (p.x < e.x){
					e.bulletmanager.addbullet(e, room);
					e.bulletmanager.bullets.get(i).left = true;
					e.bulletmanager.bullets.get(i).right = false;
				}
		}
	}
	
	static public Boolean checkEnemyCollision(Enemy e, String direction, int x, int y, int height, int width){
		if (direction.contains("left") && !((y > e.y + e.height) || (e.y > y + height))){
		 
			if (x > e.x + e.width || e.x > x){
				return true;
			}
			else
				return false;
		}
		
		else if (direction.contains("right") && !((y > e.y + e.height) || (e.y > y + height))){
			if (e.x > x + width || e.x < x){
				return true;
			}
			else 
				return false;
		}
			
		else if (direction.contains("up")){
			//System.out.println("eccomi");
			
			if (e.y + e.height >= y && e.y + e.height <= y + height && (x + width > e.x && x < e.x 
					|| x + width > e.x + e.width && x < e.x + e.width))
				return false;
			else 
				return true;
		}
		
		else if (direction.contains("down")){
			
		}
		
		return true;
	}
	
	public ArrayList<Enemy> getEnemies(){
		if (enemies.isEmpty())
			return null;
		return enemies;
	}
	
	public void setRoomTrue(Boolean b){
		new_room = b;
	}
	
	public void setRoomFalse(Boolean b){
		new_room = b;
	}
	
	public Boolean getRoom(){
		return new_room;
	}
	

	
	public class Enemy extends AnimatedObject {

		private int dx, dy;
		private int saveAnimation, totalRange;
	    private int health, stamina, maxStamina;
	    private int cont;
	    Rectangle feet;
	    BulletManager bulletmanager;
	    Boolean left_right, vertical, plusminus, isMoving, isLeft, isAttacking;
	    long timer, timer2, timer3;
	    Equipment equipment = null;
	    Boolean shoot;
		
		public Enemy(String filePath, int x, int y, int nf) {
			super(filePath, x, y, nf);
			
			addAnimation("stand", 0, 9, 150 , true);
			addAnimation("movement", 8, 11, 150, true);
	    	
	    	feet = new Rectangle(x, (int)(y+height-(3 * 4)), x+width, y+height);
	    	left_right = vertical = plusminus = isMoving = isLeft = isAttacking = false;
	    	dx = dy = 50;
	    	health = 30;
	    	stamina = 30;
	    	maxStamina = 30;
	    	cont = 0;
	    	long second = System.currentTimeMillis();
	    	timer = second;
	    	timer2 = second;
	    	timer3 = second;
	    	saveAnimation = 0;
	    	shoot = false;
	    	totalRange = 4;
	    	bulletmanager = new BulletManager();
	    	equipment = new Equipment();
	    	equipment.setCape("src/cape_spritesheet.png", x, y, 38);
	    	equipment.setArms("src/arms_spritesheet.png", x, y, 34);
	    	equipment.setHead("src/head_spritesheet.png", x, (y - (4)), 34);
	    	equipment.setBody("src/body_spritesheet.png", x, y, 34);
	    	equipment.setLegs("src/legs_spritesheet.png", x, y, 34);
	    	equipment.setWeapon("src/weapon_spritesheet.png", (x - (3 * 4)), (y - (3 * 4)), 26);
		}
		
		public int setEnemiesPositionX(int x, int width, int enemyX, int enemyWidth){
			int rand = ThreadLocalRandom.current().nextInt(0, (x+(width - enemyWidth)));
			//System.out.println(enemyWidth + "enemy Width" + " ");
			
			
			return rand;
		}
		
		
		public int setEnemiesPositionY (int y, int height, int enemyY, int enemyHeight){
			int rand = ThreadLocalRandom.current().nextInt(0,y+(height + enemyHeight));
			//System.out.println(enemyHeight + "enemy Height" + " ");
			return rand;
		}
		
		/* AGGIUNTO SOLO PER TEST */ 
		public void decreaseHealt(int v){
			health -= v;
		}
		
		public int getStamina(){
			return stamina;
		}
		
		public void staminaManager(){
	    	if(System.currentTimeMillis() > timer + 50){
	    		timer = System.currentTimeMillis();
	    		
	    		if(stamina < maxStamina)
	    			stamina++;
	    	}
	    }
		
		public boolean generateDamage(Rectangle rect){
	    	if(isAttacking){
	    		Rectangle rectP, rectWP;
	    		rectWP = equipment.weapon.getBounds();
	    	
	    		if(isLeft)
	    			rectP = new Rectangle(rectWP.x, rectWP.y, rectWP.width-((rectWP.width-rectWP.x)/2), rectWP.height);
	    		else
	    			rectP = new Rectangle(rectWP.x+((rectWP.width-rectWP.x)/2), rectWP.y, rectWP.width, rectWP.height);
	    			
	    		return ((((rect.x >= rectP.x && rect.x <= rectP.width) && ((rect.y >= rectP.y && rect.y <= rectP.height) || (rect.height >= rectP.y && rect.height <= rectP.height))) || 
	    			((rect.width >= rectP.x && rect.width <= rectP.width) && ((rect.y >= rectP.y && rect.y <= rectP.height) || (rect.height >= rectP.y && rect.height <= rectP.height)))) &&
	    			((isAttacking && getCurrentFrame() > 3))); 
	    	}
	    	else
	    		return false;
		}
		
		
		public void drawEnemy(Graphics2D g2d){
			
			equipment.setPositions(x, y, isLeft);
			
			//equipment.drawCape(g2d);
	    	super.drawAnimation(g2d);
	    	//equipment.drawLegs(g2d);
	    	//equipment.drawBody(g2d);
	    	//equipment.drawHead(g2d);
	    	//equipment.drawArms(g2d);
	    	//equipment.drawWeapon(g2d);
	    	
	    	//g2d.drawRect(x, y, width, height);
	    	
	    	if (shoot){
	    		if (bulletmanager.bullets.size() != 0 && !bulletmanager.bullets.isEmpty() && bulletmanager.bullets != null){
	    			for (int i = 0; i < bulletmanager.bullets.size(); i++){
	    				//bulletmanager.bullets.get(i).loadImage("src/bullet.png");
	    				bulletmanager.bullets.get(i).draw(g2d);
	    			}
	    		}
	    	}
		}
		
		public void setAnimations(int i){
	    	setAnimation(i);	
	    	equipment.cape.setAnimation(i);
	    	equipment.legs.setAnimation(i);
	    	equipment.body.setAnimation(i);
	    	equipment.head.setAnimation(i);
	    	equipment.arms.setAnimation(i);
	    	equipment.weapon.setAnimation(i);
	    	
	    }
		
		 public boolean isAttackEnded(){
				return (getCurrentAnimation() == 4 || getCurrentAnimation() == 5) && isEnded(); 
						
		}
		 
	}
	
}