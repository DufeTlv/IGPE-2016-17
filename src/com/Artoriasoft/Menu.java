package com.Artoriasoft;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Menu {
	
	ArrayList<AnimatedObject> buttons;
	Object splashScreen, screenIGPE, screenGameName, screenDesignedBy, screenNames, screenAYS, screenDeath, screenVictory, keysLegendWASD, keysLegendArrow, keysLegendCCC;
	AnimatedObject aL, aR, aU, aD;
	
	int rectAlpha;
	long timer;
	
	boolean inSplashScreen, inExitSubMenu, inEditorMode, inDeathScreen, inVictoryScreen;
	
	public Menu(){
		
		/* Legenda
		 * 0 start
		 * 1 editor
		 * 2 exit
		 * 3 yes
		 * 4 no */
		
		
		rectAlpha = 250;
		timer = 0;
		
		buttons = new ArrayList<>();
		
		splashScreen = new Object(0, 0);
		splashScreen.loadImage("src/splashScreen.png");
		
		screenDesignedBy = new Object(0, 0);
		screenDesignedBy.loadImage("src/designedBy.png");
		
		screenNames = new Object(0, 0);
		screenNames.loadImage("src/screenNames.png");
		
		screenGameName = new Object(0, 0);
		screenGameName.loadImage("src/screenGameName.png");
		
		screenIGPE = new Object(0, 0);
		screenIGPE.loadImage("src/screenIGPE.png");
		
		screenAYS = new Object(374, 500);
		screenAYS.loadImage("src/screenAYS.png");
		
		screenDeath = new Object(0, 0);
		screenDeath.loadImage("src/screenDeath.png");
		
		screenVictory = new Object(0, 0);
		screenVictory.loadImage("src/screenVictory.png");
		
		keysLegendWASD = new Object(-80, 752);
		keysLegendWASD.loadImage("src/keysLegendWASD.png");
		
		keysLegendArrow = new Object(780, 752);
		keysLegendArrow.loadImage("src/keysLegendArrow.png");
		
		keysLegendCCC = new Object(-80, 250);
		keysLegendCCC.loadImage("src/keysLegendCCC.png");
		
		aL = new AnimatedObject("src/arrowLeft.png", 448, 446, 500, 4, true);
		aR = new AnimatedObject("src/arrowRight.png", 648, 446, 500, 4, true);
		aU = new AnimatedObject("src/arrowUp.png", 546, 362, 500, 4, true);
		aD = new AnimatedObject("src/arrowDown.png", 546, 546, 500, 4, true);
		
        buttons.add(new AnimatedObject("src/buttonStart.png", 180, 400, 0, 2, false));
        buttons.add(new AnimatedObject("src/buttonEditor.png", 800, 400, 0, 2, false));
        buttons.add(new AnimatedObject("src/buttonExit.png", 500, 550, 0, 2, false));
        buttons.add(new AnimatedObject("src/buttonYes.png", 200, 650, 0, 2, false));
        buttons.add(new AnimatedObject("src/buttonNo.png", 800, 650, 0, 2, false));
        
        buttons.get(0).setAnimate(false);
        buttons.get(1).setAnimate(false);
        buttons.get(2).setAnimate(false);
        buttons.get(3).setAnimate(false);
        buttons.get(4).setAnimate(false);
        
        inSplashScreen = true;
        inExitSubMenu = inEditorMode = inDeathScreen = inVictoryScreen = false;
	}
	
	public void reset(){
		inExitSubMenu = inEditorMode = inDeathScreen = inVictoryScreen = false;
		buttons.get(0).setCurrentFrame(0);
        buttons.get(1).setCurrentFrame(0);
        buttons.get(2).setCurrentFrame(0);
        buttons.get(3).setCurrentFrame(0);
        buttons.get(4).setCurrentFrame(0);
        rectAlpha = 0;
	}
	
	public void setInExitSubMenu(){
		inExitSubMenu = !inExitSubMenu;
		
		if(!inExitSubMenu && buttons.get(2).getCurrentEndFrame() != 0){
			buttons.get(2).setCurrentFrame(0);
			buttons.get(4).setCurrentFrame(0);
		}
		
	}
	
	public void draw(Graphics2D g2d){
		
		if(!inExitSubMenu && !inEditorMode){
			buttons.get(0).drawAnimation(g2d);
			buttons.get(1).drawAnimation(g2d);
			buttons.get(2).drawAnimation(g2d);
		}
		else if(inExitSubMenu){
			screenAYS.draw(g2d);
			buttons.get(3).drawAnimation(g2d);
			buttons.get(4).drawAnimation(g2d);
		}
		
	}
	
	public void drawSplashScreen(Graphics2D g2d){
		if(!inSplashScreen)
			return;
		
		if(timer == 0){
			timer = System.currentTimeMillis();
			
			splashScreen.setX(Board.camX + (mainclass.screenSize.width/2) - splashScreen.width/2);
			splashScreen.setY(Board.camY + (mainclass.screenSize.height/2) - splashScreen.height/2);
			
			screenIGPE.setX(Board.camX + (mainclass.screenSize.width/8) - screenDesignedBy.width/2);
			screenIGPE.setY(Board.camY + (mainclass.screenSize.height) - screenDesignedBy.height);
			
			screenDesignedBy.setX(Board.camX + (mainclass.screenSize.width/3) - screenDesignedBy.width);
			screenDesignedBy.setY(Board.camY + (mainclass.screenSize.height/3) - screenDesignedBy.height);
			
			screenNames.setX(Board.camX + (mainclass.screenSize.width/2) - screenNames.width/2);
			screenNames.setY(Board.camY + (mainclass.screenSize.height/2) - screenNames.height/2);
			
			screenGameName.setX(Board.camX + (mainclass.screenSize.width/2) - screenGameName.width/2);
			screenGameName.setY(Board.camY + (mainclass.screenSize.height/2) - screenGameName.height/2);
			
		}
		
		if(rectAlpha > 0)
			drawDarkScreen(g2d);
		
		if(System.currentTimeMillis() > timer + 1000 && System.currentTimeMillis() < timer + 4000){
			splashScreen.draw(g2d);
			screenIGPE.draw(g2d);
		}
		
		if(System.currentTimeMillis() > timer + 4000 && System.currentTimeMillis() < timer + 7000){
			screenDesignedBy.draw(g2d);
			screenNames.draw(g2d);
			screenIGPE.draw(g2d);
		}
		
		if(System.currentTimeMillis() > timer + 7000 && System.currentTimeMillis() < timer + 10000){
			screenGameName.draw(g2d);
			screenIGPE.draw(g2d);
		}
		
		if(System.currentTimeMillis() > timer + 10000 && System.currentTimeMillis() < timer + 11500){
			if(inSplashScreen && rectAlpha > 0){
				rectAlpha-=2;
			}
		}
		
		if(System.currentTimeMillis() > timer + 11500 && inSplashScreen){
			rectAlpha = 0;
			inSplashScreen = false;
		}
	}
	
	public void drawDarkScreen(Graphics2D g2d){
		
		if((inEditorMode && rectAlpha < 200) || ((inDeathScreen || inVictoryScreen) && rectAlpha < 250))
			rectAlpha+=2;
			
		g2d.setColor(new Color(0, 0, 0, rectAlpha));
        g2d.fillRect(Board.camX, Board.camY, mainclass.screenSize.width, mainclass.screenSize.height);
		
	}
	
	public void drawEndGameScreen(Graphics2D g2d){
		if(!inDeathScreen && !inVictoryScreen)
			return;
		
		drawDarkScreen(g2d);
		
		if(rectAlpha >= 250){
			if(inDeathScreen){
				screenDeath.setX(Board.camX + (mainclass.screenSize.width/2) - screenDeath.width/2);
				screenDeath.setY(Board.camY + (mainclass.screenSize.height/2) - screenDeath.height/2);
				screenDeath.draw(g2d);
			}
			else if(inVictoryScreen){
				screenVictory.setX(Board.camX + (mainclass.screenSize.width/2) - screenVictory.width/2);
				screenVictory.setY(Board.camY + (mainclass.screenSize.height/2) - screenVictory.height/2);
				screenVictory.draw(g2d);
			}
		}
	}
	
	public void drawEditorLegends(Graphics2D g2d, boolean l, boolean r ,boolean u, boolean d){
		
		keysLegendWASD.draw(g2d);
		keysLegendArrow.draw(g2d);
		keysLegendCCC.draw(g2d);
		
		if(l){
			aL.drawAnimation(g2d);
		}
		else{
			aL.animate();
		}
		
		if(r){
			aR.drawAnimation(g2d);
		}else{
			aR.animate();
		}
		
		if(u){
			aU.drawAnimation(g2d);
		}else{
			aU.animate();
		}
		
		if(d){
			aD.drawAnimation(g2d);
		}else{
			aD.animate();
		}
		
	}
	
	public boolean isNotOnButton(int i, Rectangle r){
		
		Rectangle buttonBounds = new Rectangle(buttons.get(i).getBounds().x, buttons.get(i).getBounds().y, buttons.get(i).getBounds().width-buttons.get(i).getBounds().x, buttons.get(i).getBounds().height-buttons.get(i).getBounds().y);
		if ( !(buttonBounds.contains(r.x+12, r.y) && buttonBounds.contains(r.width-12, r.y) && buttonBounds.contains(r.x+12, r.height) && buttonBounds.contains(r.width-12, r.height))
				&& buttons.get(i).getCurrentFrame() == 1){
			buttons.get(i).setCurrentFrame(0);
        	return true;
		}
		
		return false;		
		
	}
	
	public boolean isOnButton(int i, Rectangle r){
			
		Rectangle buttonBounds = new Rectangle(buttons.get(i).getBounds().x, buttons.get(i).getBounds().y, buttons.get(i).getBounds().width-buttons.get(i).getBounds().x, buttons.get(i).getBounds().height-buttons.get(i).getBounds().y);
		
        if(buttonBounds.contains(r.x+12, r.y) && buttonBounds.contains(r.width-12, r.y) && buttonBounds.contains(r.x+12, r.height) && buttonBounds.contains(r.width-12, r.height) 
        			&& buttons.get(i).getCurrentFrame() == 0){
        	buttons.get(i).setCurrentFrame(1);
        	return true;
        }
        return false;
        
	}
	
}
