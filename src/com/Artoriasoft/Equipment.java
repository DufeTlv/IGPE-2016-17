package com.Artoriasoft;

import java.awt.Graphics2D;

// tutto questo al momento mi sembra davvero troppo pesante
// ma al momento è necessario rimanga così, se poi dovessimo trovare qualche modo
// per ottimizzare gestiamo la cosa diversamente

public class Equipment {
	AnimatedObject head, arms, body, legs, cape, weapon, shield;
	
	public Equipment(){
		head = arms = body = legs = weapon = cape = shield = null;
	}
	
	public void syncTimers(long t){
		head.setTimer(t); 
		arms.setTimer(t);
		body.setTimer(t);
		legs.setTimer(t); 
		weapon.setTimer(t);
		shield.setTimer(t);
    }
	
	public void setHead(String filePath, int x, int y, int nf){
		head = new AnimatedObject(filePath, x, y, nf);
		
		head.addAnimation("idle_right", 0, 3, 250, true);
		head.addAnimation("idle_left", 4, 7, 250, true);
		head.addAnimation("walk_right", 8, 15, 83, true);
		head.addAnimation("walk_left", 16, 23, 83, true);
		head.addAnimation("first_attack_right", 24, 27, 65, false);
		head.addAnimation("first_attack_left", 28, 31, 65, false);
		head.addAnimation("second_attack_right", 32, 35, 65, false);
		head.addAnimation("second_attack_left", 36, 39, 65, false);
		head.addAnimation("shield_right", 40, 41, 60, false);
		head.addAnimation("shield_left", 42, 43, 60, false);
	}
	
	public void setArms(String filePath, int x, int y, int nf){
		arms = new AnimatedObject(filePath, x, y, nf);
		
		arms.addAnimation("idle_right", 0, 3, 250, true);
		arms.addAnimation("idle_left", 4, 7, 250, true);
		arms.addAnimation("walk_right", 8, 15, 83, true);
		arms.addAnimation("walk_left", 16, 23, 83, true);
		arms.addAnimation("first_attack_right", 24, 27, 65, false);
		arms.addAnimation("first_attack_left", 28, 31, 65, false);
		arms.addAnimation("second_attack_right", 32, 35, 65, false);
		arms.addAnimation("second_attack_left", 36, 39, 65, false);
		arms.addAnimation("shield_right", 40, 41, 60, false);
		arms.addAnimation("shield_left", 42, 43, 60, false);
	}
	
	public void setBody(String filePath, int x, int y, int nf){
		body = new AnimatedObject(filePath, x, y, nf);
		
		body.addAnimation("idle_right", 0, 3, 250, true);
		body.addAnimation("idle_left", 4, 7, 250, true);
		body.addAnimation("walk_right", 8, 15, 83, true);
		body.addAnimation("walk_left", 16, 23, 83, true);
		body.addAnimation("first_attack_right", 24, 27, 65, false);
		body.addAnimation("first_attack_left", 28, 31, 65, false);
		body.addAnimation("second_attack_right", 32, 35, 65, false);
		body.addAnimation("second_attack_left", 36, 39, 65, false);
		body.addAnimation("shield_right", 40, 41, 60, false);
		body.addAnimation("shield_left", 42, 43, 60, false);
	}
	
	public void setLegs(String filePath, int x, int y, int nf){
		legs = new AnimatedObject(filePath, x, y, nf);
		
		legs.addAnimation("idle_right", 0, 3, 250, true);
		legs.addAnimation("idle_left", 4, 7, 250, true);
		legs.addAnimation("walk_right", 8, 15, 83, true);
		legs.addAnimation("walk_left", 16, 23, 83, true);
		legs.addAnimation("first_attack_right", 24, 27, 65, false);
		legs.addAnimation("first_attack_left", 28, 31, 65, false);
		legs.addAnimation("second_attack_right", 32, 35, 65, false);
		legs.addAnimation("second_attack_left", 36, 39, 65, false);
		legs.addAnimation("shield_right", 40, 41, 60, false);
		legs.addAnimation("shield_left", 42, 43, 60, false);
	}

	public void setCape(String filePath, int x, int y, int nf){
		cape = new AnimatedObject(filePath, x, y, nf);
		
		cape.addAnimation("idle_right", 0, 9, 83, true);
		cape.addAnimation("idle_left", 10, 19, 83, true);
		cape.addAnimation("walk_right", 20, 23, 83, true);
		cape.addAnimation("walk_left", 24, 27, 83, true);
		cape.addAnimation("first_attack_right", 28, 32, 60, false);
    	cape.addAnimation("first_attack_left", 33, 37, 60, false);
	}
	
	public void setWeapon(String filePath, int x, int y, int nf){
		weapon = new AnimatedObject(filePath, x, y, nf);
		
		weapon.addAnimation("idle_right", 0, 3, 250, true);
		weapon.addAnimation("idle_left", 4, 7, 250, true);
		weapon.addAnimation("walk_right", 8, 15, 83, true);
		weapon.addAnimation("walk_left", 16, 23, 83, true);
		weapon.addAnimation("first_attack_right", 24, 27, 65, false);
		weapon.addAnimation("first_attack_left", 28, 31, 65, false);
		weapon.addAnimation("second_attack_right", 32, 35, 65, false);
		weapon.addAnimation("second_attack_left", 36, 39, 65, false);
		weapon.addAnimation("shield_right", 40, 41, 60, false);
		weapon.addAnimation("shield_left", 42, 43, 60, false);
	}
	
	public void setShield(String filePath, int x, int y, int nf){
		shield = new AnimatedObject(filePath, x, y, nf);
		
		shield.addAnimation("idle_right", 0, 3, 250, true);
		shield.addAnimation("idle_left", 4, 7, 250, true);
		shield.addAnimation("walk_right", 8, 15, 83, true);
		shield.addAnimation("walk_left", 16, 23, 83, true);
		shield.addAnimation("first_attack_right", 24, 27, 65, false);
		shield.addAnimation("first_attack_left", 28, 31, 65, false);
		shield.addAnimation("second_attack_right", 32, 35, 65, false);
		shield.addAnimation("second_attack_left", 36, 39, 65, false);
		shield.addAnimation("shield_right", 40, 41, 60, false);
		shield.addAnimation("shield_left", 42, 43, 60, false);
	}
	
	public void drawHead(Graphics2D g2d){
		if(head != null){
			head.animate();
			head.drawAnimation(g2d);
		}
	}
	
	public void drawCape(Graphics2D g2d){
		if(cape != null){
			cape.animate();
			cape.drawAnimation(g2d);
		}
	}
	
	public void drawLegs(Graphics2D g2d){
		if(legs != null){
			legs.animate();
			legs.drawAnimation(g2d);
		}
	}
	
	public void drawBody(Graphics2D g2d){
		if(body != null){
			body.animate();
			body.drawAnimation(g2d);
		}
	}
	
	public void drawArms(Graphics2D g2d){
		if(arms != null){
			arms.animate();
			arms.drawAnimation(g2d);
		}
	}
	
	public void drawShield(Graphics2D g2d){
		if(shield != null){
			shield.animate();
			shield.drawAnimation(g2d);
		}
	}
	
	public void drawWeapon(Graphics2D g2d){
		if(weapon != null){
			weapon.animate();
			weapon.drawAnimation(g2d);
		}
	}
	
	public void setPositions(int x, int y, Boolean l){
		if(cape != null){
			if(l){
				cape.x = x+20;
				cape.y = y;
			}
			else{
				cape.x = x+20;
				cape.y = y;
			}
		}
		if(legs != null){
			legs.x = x;
			legs.y = y;
		}
		if(body != null){
			body.x = x;
			body.y = y;
		}
		if(head != null){
			head.x = x;
			head.y = (y - 4);
		}
		if(arms != null){
			arms.x = x;
			arms.y = y;
		}
		if(shield != null){
			if(l){
				shield.x = (int)(x - (9 * 4));
				shield.y = y;
			}else{
				shield.x = (int)((x - (9 * 4)));
				shield.y = y;
			}
		}
		if(weapon != null){
			if(l){
				weapon.x = (int)(x - (9 * 4));
				weapon.y = y;
			}else{
				weapon.x = (int)((x - (9 * 4)));
				weapon.y = y;
			}
		}

	}

}
