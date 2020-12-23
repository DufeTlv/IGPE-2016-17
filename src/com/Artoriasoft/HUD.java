package com.Artoriasoft;

import java.awt.Graphics2D;

public class HUD {
	
	int x, y;
	Object stateHUD, lifeBar, staminaBar, bossHUD, bossLifeBar;
	
	public HUD(int newX, int newY){
		x = newX;
		y = newY;
		
		stateHUD = new Object(newX, newY);
		stateHUD.loadImage("src/stateHUD.png");
		
		bossHUD = new Object(newX, newY);
		bossHUD.loadImage("src/bossHUD.png");
		
		lifeBar = new Object(newX, newY);
		lifeBar.loadImage("src/bar.png");
		
		bossLifeBar = new Object(newX, newY);
		bossLifeBar.loadImage("src/bar.png");
		
		staminaBar = new Object(newX, newY);
		staminaBar.loadImage("src/bar.png");
		
	}
	
	public void setPosition(int newX, int newY){
		x = newX + 20;
		y = newY + 20;
		
		stateHUD.x = x;
		stateHUD.y = y;
		
		lifeBar.x = x + 4;
		lifeBar.y = y + 28;
		
		staminaBar.x = x + 4;
		staminaBar.y = y + 92;
	}
	
	public void setBossHUDPosition(int newX, int newY){
		bossHUD.x = newX + mainclass.screenSize.width/2 - (70*4);
		bossHUD.y = newY + mainclass.screenSize.height - (17*8);
		
		bossLifeBar.x = bossHUD.x + 4;
		bossLifeBar.y = bossHUD.y + 40;
	}
	
	public void drawHUD(Graphics2D g2d, int h, int mH, int s, int mS){
		
		stateHUD.draw(g2d);
		
		// ogniuno di questi due interi calcola in percentuale quanto deve essere lunga la barra
		// prendendo come base quanto rappresenta in percentuale il valore in input rispetto al
		// valore massimo che puo raggiungere, e dal quel valore viene ricavato il valore in percentuale
		// rispetto al range presente nella sprite;
		
		int hw = (((int)(h*100/mH)*(80*4))/100);
		int sw = (((int)(s*100/mS)*(60*4))/100);
		
		g2d.drawImage(lifeBar.image, lifeBar.x, lifeBar.y, lifeBar.x+hw, lifeBar.y+lifeBar.height, 
				0, 0, lifeBar.width , lifeBar.height, null);
		g2d.drawImage(staminaBar.image, staminaBar.x, staminaBar.y, staminaBar.x+sw, staminaBar.y+staminaBar.height, 
				0, 0, staminaBar.width , staminaBar.height, null);
		
	}
	
	public void drawBossHUD(Graphics2D g2d, int h, int mH){
		bossHUD.draw(g2d);
		
		int hw = (((int)(h*100/mH)*(138*4))/100);
		g2d.drawImage(bossLifeBar.image, bossLifeBar.x, bossLifeBar.y, bossLifeBar.x+hw, bossLifeBar.y+bossLifeBar.height, 
				0, 0, bossLifeBar.width , bossLifeBar.height, null);
	}

}
