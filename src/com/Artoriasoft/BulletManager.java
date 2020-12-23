package com.Artoriasoft;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.Artoriasoft.BossManager.Boss;
import com.Artoriasoft.BulletManager.Bullet;
import com.Artoriasoft.EnemyManager.Enemy;


public class BulletManager{
	
	ArrayList<Bullet> bullets;
	Bullet bullet;
	int velocity;
	
	public BulletManager(){
		
		bullet = new Bullet(0,0);
		bullets = new ArrayList();
		velocity = 8;
		
	}
	
	public void addbullet(Enemy e, Rectangle room){
		Bullet bullet1 = new Bullet(0,0);
		bullet1.setX(e.x+e.width/2);
		bullet1.setY(e.y+e.height/2);
		bullet1.spawnEnd = false;
		bullet1.loadImage("src/bullet.png");
		
		bullets.add(bullet1);
	}
	
	public void addBossBullets(Boss b, BulletManager bullets){
		Bullet b1, b2, b3, b4, b5, b6, b7, b8;
		
		b1 = new Bullet(b.x+b.width/2, b.y+b.height/2); b2 = new Bullet(b.x+b.width/2, b.y+b.height/2); b3 = new Bullet(b.x+b.width/2, b.y+b.height/2); b4 = new Bullet(b.x+b.width/2, b.y+b.height/2);
		b5 = new Bullet(b.x+b.width/2, b.y+b.height/2); b6 = new Bullet(b.x+b.width/2, b.y+b.height/2); b7 = new Bullet(b.x+b.width/2, b.y+b.height/2); b8 = new Bullet(b.x+b.width/2, b.y+b.height/2);
		b1.direction = "right"; b2.direction = "left"; b3.direction = "ldown"; b4.direction = "rdown"; b5.direction = "rup"; b6.direction = "lup";
		b7.direction = "up"; b8.direction = "down";
		bullets.bullets.add(b1); bullets.bullets.add(b2); bullets.bullets.add(b3); bullets.bullets.add(b4); bullets.bullets.add(b5);
		bullets.bullets.add(b6); bullets.bullets.add(b7); bullets.bullets.add(b8); 
		
		for(int i = 0; i < bullets.bullets.size(); i++)
			bullets.bullets.get(i).loadImage("src/bullet.png");
 	}
	
	public void addEnemy2Bullets(Enemy e, BulletManager bullets){
		Bullet b1, b2;
		
		b1 = new Bullet(e.x+e.width/2, e.y+e.height/2);
		b2 = new Bullet(e.x+e.width/2, e.y+e.height/2);
		
		b1.loadImage("src/bullet.png");
		b2.loadImage("src/bullet.png");
		
		b1.direction = "right";
		b2.direction = "left";
		
		bullets.bullets.add(b1); bullets.bullets.add(b2);
	}

	public void bulletfollowIA(Enemy e, Rectangle room, Player p){
		
			if (p.x == e.bulletmanager.bullet.x && p.y == e.bulletmanager.bullet.y){
				
				
				return;
			}
			
			
			if (p.x == e.bulletmanager.bullet.x && p.y < bullet.y){
				
					bullet.y-=2;
					return;
			}
			
			if (p.x == bullet.x && p.y > bullet.y){
					bullet.y+=2;
					return;
			}
			
			if (p.x  < e.bulletmanager.bullet.x && p.y == bullet.y){

					bullet.x+=2;
					return;
			}
			
			
			if (p.x > bullet.x && p.y > bullet.y){
					bullet.x+=2;
					bullet.y+=2;
					return;
			}
			
			if (p.x > bullet.x && p.y < bullet.y){
					bullet.x+=2;
					bullet.y-=2;
					return;
			}
		
			if (p.x < bullet.x && p.y > bullet.y){
					bullet.x-=2;
					bullet.y+=2;
					return;
			}
			
			if (p.x < bullet.x && p.y < bullet.y){
					bullet.x-=2;
					bullet.y-=2;
					return;
			}
			
	}
	
	public void normalBulletIAright(Enemy e, Rectangle room, Player p, Bullet bullet1){
		
	
		if ((bullet1.y >= p.y && bullet1.y + bullet1.height <= p.y + p.height) && !(p.x > bullet1.x + bullet1.width) && 
				(bullet1.x >= p.x && bullet1.x + bullet1.width <= p.x + p.width)){
				
			p.getDamaged(5);
				
			int index = 0;
				
			for (int i = 0; i < bullets.size(); i++){
				if (bullets.get(i) == bullet1){
					index = i;
				}
			}
			
			bullets.remove(index);	
			return;
		}
			
		else if (bullet1.x >  room.width || bullet1.y >  room.height){
				
			int index = 0;
				
			for (int i = 0; i < bullets.size(); i++){
				if (bullets.get(i) == bullet1){
					index = i;
				}
			}
				
			bullets.remove(index);	
			return;
		}
		
		else
			bullet1.x+=velocity;
		
		
		
		}
	
	public void normalBulletBossIAright(Boss e, Rectangle room, Player p, Bullet bullet1){
		
		
		if ((bullet1.y >= p.y && bullet1.y + bullet1.height <= p.y + p.height) && !(p.x > bullet1.x + bullet1.width) && 
				(bullet1.x >= p.x && bullet1.x + bullet1.width <= p.x + p.width)){
				
			p.getDamaged(10);
				
			int index = 0;
				
			for (int i = 0; i < bullets.size(); i++){
				if (bullets.get(i) == bullet1){
					index = i;
				}
			}
			
			bullets.remove(index);	
			return;
		}
			
		else if (bullet1.x > room.width || bullet1.y > room.height){
				
			int index = 0;
				
			for (int i = 0; i < bullets.size(); i++){
				if (bullets.get(i) == bullet1){
					index = i;
				}
			}
				
			bullets.remove(index);	
			return;
		}
		
		else
			bullet1.x+=velocity;
		
		
		
		}
	
	public void normalBulletIALeft(Enemy e, Rectangle room, Player p, Bullet bullet1){
		if ((bullet1.y >= p.y && bullet1.y + bullet1.height <= p.y + p.height) && 
				(bullet1.x <= p.x + p.width && bullet1.x + bullet1.width >= p.x)){
				
			p.getDamaged(5);
			
			int index = 0;
				
			for (int i = 0; i < bullets.size(); i++){
				if (bullets.get(i) == bullet1){
					index = i;
				}
			}
				
			bullets.remove(index);
				
			return;
		}
			
		else if (bullet1.x + bullet1.width < room.x || bullet1.y + bullet1.height < room.y){
				
			int index = 0;
			
			for (int i = 0; i < bullets.size(); i++){
				if (bullets.get(i) == bullet1){
					index = i;
				}
			}
				
			bullets.remove(index);
				
			return;
		}
			
		else
			bullet1.x-=velocity;
	}
	
	public void normalBulletBossIALeft(Boss e, Rectangle room, Player p, Bullet bullet1){
		if ((bullet1.y >= p.y && bullet1.y + bullet1.height <= p.y + p.height) && 
				(bullet1.x <= p.x + p.width && bullet1.x + bullet1.width >= p.x)){
				
			p.getDamaged(10);
			
			int index = 0;
				
			for (int i = 0; i < bullets.size(); i++){
				if (bullets.get(i) == bullet1){
					index = i;
				}
			}
				
			bullets.remove(index);
				
			return;
		}
			
		else if (bullet1.x + bullet1.width < room.x || bullet1.y + bullet1.height < room.y){
				
			int index = 0;
			
			for (int i = 0; i < bullets.size(); i++){
				if (bullets.get(i) == bullet1){
					index = i;
				}
			}
				
			bullets.remove(index);
				
			return;
		}
			
		else
			bullet1.x-=velocity;
	}
	
	public void normalBulletBossIArightdown(Boss e, Rectangle room, Player p, Bullet bullet1){
		if ((bullet1.y >= p.y && bullet1.y + bullet1.height <= p.y + p.height) && !(p.x > bullet1.x + bullet1.width) && 
				(bullet1.x >= p.x && bullet1.x + bullet1.width <= p.x + p.width)){
				
			p.getDamaged(10);
				
			int index = 0;
				
			for (int i = 0; i < bullets.size(); i++){
				if (bullets.get(i) == bullet1){
					index = i;
				}
			}
			
			bullets.remove(index);	
			return;
		}
			
		else if (bullet1.x > room.width || bullet1.y >  room.height){
				
			int index = 0;
				
			for (int i = 0; i < bullets.size(); i++){
				if (bullets.get(i) == bullet1){
					index = i;
				}
			}
				
			bullets.remove(index);	
			return;
		}
		
		else
			bullet1.x+=velocity;
			bullet1.y+=velocity;
	}
	
	public void normalBulletBossIAdown(Boss e, Rectangle room, Player p, Bullet bullet1){
		if ((bullet1.y >= p.y && bullet1.y + bullet1.height <= p.y + p.height) && !(p.x > bullet1.x + bullet1.width) && 
				(bullet1.x >= p.x && bullet1.x + bullet1.width <= p.x + p.width)){
				
			p.getDamaged(10);
				
			int index = 0;
				
			for (int i = 0; i < bullets.size(); i++){
				if (bullets.get(i) == bullet1){
					index = i;
				}
			}
			
			bullets.remove(index);	
			return;
		}
			
		else if (bullet1.x > room.width || bullet1.y >  room.height){
				
			int index = 0;
				
			for (int i = 0; i < bullets.size(); i++){
				if (bullets.get(i) == bullet1){
					index = i;
				}
			}
				
			bullets.remove(index);	
			return;
		}
		
		else
			bullet1.y+=velocity;
	}
	
	
	
	public void normalBulletBossIArightup(Boss e, Rectangle room, Player p, Bullet bullet1){
		if ((bullet1.y >= p.y && bullet1.y + bullet1.height <= p.y + p.height) && !(p.x > bullet1.x + bullet1.width) && 
				(bullet1.x >= p.x && bullet1.x + bullet1.width <= p.x + p.width)){
				
			p.getDamaged(10);
				
			int index = 0;
				
			for (int i = 0; i < bullets.size(); i++){
				if (bullets.get(i) == bullet1){
					index = i;
				}
			}
			
			bullets.remove(index);	
			return;
		}
			
		else if (bullet1.x > room.width || bullet1.y >  room.height || bullet1.y < room.y){
				
			int index = 0;
				
			for (int i = 0; i < bullets.size(); i++){
				if (bullets.get(i) == bullet1){
					index = i;
				}
			}
				
			bullets.remove(index);	
			return;
		}
		
		else
			bullet1.x+=velocity;
			bullet1.y-=velocity;
	}
	
	public void normalBulletBossIAup(Boss e, Rectangle room, Player p, Bullet bullet1){
		if ((bullet1.y >= p.y && bullet1.y + bullet1.height <= p.y + p.height) && !(p.x > bullet1.x + bullet1.width) && 
				(bullet1.x >= p.x && bullet1.x + bullet1.width <= p.x + p.width)){
				
			p.getDamaged(10);
				
			int index = 0;
				
			for (int i = 0; i < bullets.size(); i++){
				if (bullets.get(i) == bullet1){
					index = i;
				}
			}
			
			bullets.remove(index);	
			return;
		}
			
		else if (bullet1.x > room.width || bullet1.y > room.height || bullet1.y < room.y){
				
			int index = 0;
				
			for (int i = 0; i < bullets.size(); i++){
				if (bullets.get(i) == bullet1){
					index = i;
				}
			}
				
			bullets.remove(index);	
			return;
		}
		
		else
			bullet1.y-=velocity;
	}
	
	public void normalBulletBossIAlefttup(Boss e, Rectangle room, Player p, Bullet bullet1){
		if ((bullet1.y >= p.y && bullet1.y + bullet1.height <= p.y + p.height) && !(p.x > bullet1.x + bullet1.width) && 
				(bullet1.x >= p.x && bullet1.x + bullet1.width <= p.x + p.width)){
				
			p.getDamaged(10);
				
			int index = 0;
				
			for (int i = 0; i < bullets.size(); i++){
				if (bullets.get(i) == bullet1){
					index = i;
				}
			}
			
			bullets.remove(index);	
			return;
		}
			
		else if (bullet1.x > room.width || bullet1.y > room.height || bullet1.y < room.y){
				
			int index = 0;
				
			for (int i = 0; i < bullets.size(); i++){
				if (bullets.get(i) == bullet1){
					index = i;
				}
			}
				
			bullets.remove(index);	
			return;
		}
		
		else
			bullet1.x-=velocity;
			bullet1.y-=velocity;
	}
	
	public void normalBulletBossIAleftdown(Boss e, Rectangle room, Player p, Bullet bullet1){
		if ((bullet1.y >= p.y && bullet1.y + bullet1.height <= p.y + p.height) && 
				(bullet1.x <= p.x + p.width && bullet1.x + bullet1.width >= p.x)){
				
			p.getDamaged(10);
			
			int index = 0;
				
			for (int i = 0; i < bullets.size(); i++){
				if (bullets.get(i) == bullet1){
					index = i;
				}
			}
				
			bullets.remove(index);
				
			return;
		}
			
		else if (bullet1.x + bullet1.width < room.x || bullet1.y + bullet1.height < room.y){
				
			int index = 0;
			
			for (int i = 0; i < bullets.size(); i++){
				if (bullets.get(i) == bullet1){
					index = i;
				}
			}
				
			bullets.remove(index);
				
			return;
		}
		else
			bullet1.x-=velocity;
			bullet1.y+=velocity;
	}


	public class Bullet extends Object{
		
		String direction;
		Boolean spawnEnd;
		Boolean right, left;

		public Bullet(int x, int y) {
		
			super(x, y);
			spawnEnd = true;
			right = false;
			left = false;
			direction = "null";
		}
	
	}

}