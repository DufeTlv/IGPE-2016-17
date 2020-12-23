package com.Artoriasoft;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import com.Artoriasoft.BulletManager.Bullet;
import com.Artoriasoft.EnemyManager.Enemy;

public class BossManager{

	ArrayList<Boss> bosses;
	long timer2;
	int cont;
	boolean trovato;
	
	public BossManager(){
		bosses = new ArrayList<>();
		trovato = false;
		//Boss boss = new Boss("src/asset_boss.png", x+800/2, y+600/2, 16);
		//bosses.add(boss);
		timer2 = System.currentTimeMillis();
		cont = 0;
	}
	
	/* HO AGGIUNTO LE TRE FUNZIONI CHE SEGUONO */
	
	public void addBoss(int x, int y){
		Boss boss = new Boss("src/asset_boss.png", x+800/2, y+600/2, 16);
		bosses.add(boss);
	}
	
	public void drawBossUpper(Graphics2D g2d, Rectangle p){
		if(bosses.size() != 0 && bosses.get(0).activate	&& (p.height < bosses.get(0).feet.height))
			bosses.get(0).drawBoss(g2d);
	}
	
	public void drawBossLower(Graphics2D g2d, Rectangle p){
		if(bosses.size() != 0 && bosses.get(0).activate	&& (p.height >= bosses.get(0).feet.height))
			bosses.get(0).drawBoss(g2d);
	}
	

	public void BossIA(Player p, Rectangle room, Boss b){
	
		if (b.getCurrentFrame() < 7){
			b.timer = System.currentTimeMillis();
			return;
		}
		
		if (p.x > b.x)
			b.setAnimation(2);
		else 
			b.setAnimation(1);
		
		if (b.health < 1){
			b.activate = false;
			bosses.remove(0);
		}
		
		if (System.currentTimeMillis() - 1000 < b.timer){
			//timer2 = System.currentTimeMillis();
			return;
		}
		
		
		if (b.bulletmanager.bullets.size() != 0){
			trovato = true;
			for (int i = 0; i < b.bulletmanager.bullets.size(); i++){
				if (b.bulletmanager.bullets.get(i).direction == "right"){
					b.bulletmanager.normalBulletBossIAright(b, room, p, b.bulletmanager.bullets.get(i));
				}
				else if (b.bulletmanager.bullets.get(i).direction == "left"){
					b.bulletmanager.normalBulletBossIALeft(b, room, p, b.bulletmanager.bullets.get(i));
					
				}
				
				else if (b.bulletmanager.bullets.get(i).direction == "ldown"){
					b.bulletmanager.normalBulletBossIAleftdown(b, room, p, b.bulletmanager.bullets.get(i));
				}
				
				else if (b.bulletmanager.bullets.get(i).direction == "rdown"){
					b.bulletmanager.normalBulletBossIArightdown(b, room, p, b.bulletmanager.bullets.get(i));
				}
				
				else if (b.bulletmanager.bullets.get(i).direction == "rup"){
					b.bulletmanager.normalBulletBossIArightup(b, room, p, b.bulletmanager.bullets.get(i));
				}
				
				else if (b.bulletmanager.bullets.get(i).direction == "lup"){
					b.bulletmanager.normalBulletBossIAlefttup(b, room, p, b.bulletmanager.bullets.get(i));
				}
				
				else if (b.bulletmanager.bullets.get(i).direction == "up"){
					b.bulletmanager.normalBulletBossIAup(b, room, p, b.bulletmanager.bullets.get(i));
				}
				
				else if (b.bulletmanager.bullets.get(i).direction == "down"){
					b.bulletmanager.normalBulletBossIAdown(b, room, p, b.bulletmanager.bullets.get(i));
				}
			}
		}
		
		else if (b.bulletmanager.bullets.size() == 0 && trovato && cont == 0){
			cont++;
			timer2 = System.currentTimeMillis();
		}
		
		
		if (System.currentTimeMillis() - 500 > timer2 && trovato && cont > 0){
			generatePosition(b, p, room);
			b.setAnimation(0);
			trovato = false;
			cont = 0;
			
			return;
		}
		
		
		if (b.bulletmanager.bullets.size() == 0 && !trovato){
			b.bulletmanager.addBossBullets(b, b.bulletmanager);
		}
		
		b.shoot = true;
			
		//if (System.currentTimeMillis() - 5000 > timer2)
			//b.setAnimation(0);
		
		
		
	}
	
	public void generatePosition(Boss b, Player p, Rectangle r){
		int randomNumX = 0;
		int randomNumY = 0;
		int posX = 0, posY = 0;
		Random random = new Random();
		
		//randomNumX = Math.abs(random.nextInt(((r.width+r.x - (b.width)) - (r.x) + 1)))+ r.x;
		//randomNumY = Math.abs(random.nextInt(((r.y + r.height - (b.height)) - (r.y) + 1)))+ r.y;
		
		randomNumX = random.nextInt(Math.abs((r.width-b.width)-r.x + 1)) + r.x;
		randomNumY = random.nextInt(Math.abs((r.height-b.height)-r.y + 1)) + r.y;

		b.x = randomNumX;
		b.y = randomNumY;
		
		
		 
		/*if (((randomNumX + b.width <= p.x || randomNumX >= p.width) || randomNumX + b.width == p.x 
				|| randomNumX == p.width)){
			//System.out.println("eccomi2" + randomNumX + "x " + " ");
			b.x = randomNumX;
			b.y = randomNumY;
			
			return;
		}*/
		
		
		posX = randomNumX;
		posY = randomNumY;
		
		//System.out.println("X: " + posX + " Xs: " + r.x);
		//System.out.println("Y: " + posY + " Ys: " + r.y);
		
		bosses.get(0).feet.setBounds(bosses.get(0).x, bosses.get(0).y+bosses.get(0).height-40, bosses.get(0).x+bosses.get(0).width, bosses.get(0).y+bosses.get(0).height+8);
		
	}
	
	public class Boss extends AnimatedObject{
	
		Rectangle feet;
		int health, maxHealth, stamina, maxStamina;
		BulletManager bulletmanager;
		Boolean shoot, activate;
		long timer;

		public Boss(String filePath, int x, int y, int nf) {
			super(filePath, x, y, nf);
			
			shoot = false;
			activate = true;
			timer = System.currentTimeMillis();
			addAnimation("stand", 0, 7, 200 , true);
			addAnimation("left_movement", 8, 11, 200, true);
			addAnimation("right_movement", 12, 15, 200, true);
			bulletmanager = new BulletManager();
			feet = new Rectangle(x, y+height-(40), x+width, y+height+8);
			health = 500;
			maxHealth = 500;
			stamina = 30;
			maxStamina = 30;
			x = 800/2;
			y = 600/2;
		}
		
		public void bulletSpawn(Boss b){
			
			
		
		}
	
	
		public Boolean checkBossCollision(Boss e, String direction, int x, int y, int height, int width){
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
	
		public void drawBoss(Graphics2D g2d){
	
			super.drawAnimation(g2d);
			if (shoot){
				for (int i = 0; i < bulletmanager.bullets.size(); i++){
					bulletmanager.bullets.get(i).loadImage("src/bullet.png");
					bulletmanager.bullets.get(i).draw(g2d);
				}
			}	
		}
	
		public void decreaseHealt(int v){
			health -= v;
		}

	
	}
}