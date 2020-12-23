package com.Artoriasoft;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Map {
	public ArrayList<Area> areas;
	public ArrayList<Object> bridges;
	public Object hub, boss;
	public boolean pressed;
	public int selected;
	
	public Map(){
		areas = new ArrayList<>();
		bridges = new ArrayList<>();
		
		areas.add(new Area("src/mapAreaAsset.png", 562-70, 464-58, 0, 3, false));
		areas.get(areas.size()-1).setAnimate(false);
		
		hub = new Object(areas.get(0).x + 50, areas.get(0).y + 48);
		hub.loadImage("src/screenHub.png");
		
		boss = new Object(areas.get(0).x + 42, areas.get(0).y + 48);
		boss.loadImage("src/screenBoss.png");
		
		pressed = true;
		selected = 0;
	}
	
	public void checkSelected(){
		for(int i = 0; i < areas.size(); i++){
			if(areas.get(i).getX() == 492 && areas.get(i).getY() == 406)
				selected = i;
		}
	}
	
	public int getAreaCreator(int i){
		return areas.get(i).getCreator();
	}
	
	public Rectangle getAreaBounds(int i){
		return areas.get(i).getBounds();
	}
	
	public int getCurrentAreaFrame(int i){
		return areas.get(i).getCurrentFrame();
	}
	
	public void drawMap(Graphics2D g2d){
		for(int i = 0; i < areas.size(); i++)
			areas.get(i).drawAnimation(g2d);
		
		for(int i = 0; i < bridges.size(); i++)
			bridges.get(i).draw(g2d);
		
		hub.draw(g2d);
		if(areas.size() > 1)
			boss.draw(g2d);
		
	}
	
	public int getCurrentIndexL(){
		if(areas.isEmpty())
			return -1;
		
		return areas.get(selected).iL;
	}
	
	public int getCurrentIndexR(){
		if(areas.isEmpty())
			return -1;
		
		return areas.get(selected).iR;
	}
	
	public int getCurrentIndexU(){
		if(areas.isEmpty())
			return -1;
		
		return areas.get(selected).iU;
	}
	
	public int getCurrentIndexD(){
		if(areas.isEmpty())
			return -1;
		
		return areas.get(selected).iD;
	}
	
	public void resetMap(){
		areas.clear();
		bridges.clear();
		areas.add(new Area("src/mapAreaAsset.png", 562-70, 464-58, 0, 3, false));
		areas.get(areas.size()-1).setAnimate(false);
		selected = 0;
		hub.setX(areas.get(0).x + 50);
		hub.setY(areas.get(0).y + 48);
		pressed = true;
	}
	
	public void controlPositions(){
		for(int i = 0; i < areas.size(); i++){
			for(int j = 0; j < areas.size(); j++)
				areas.get(i).checkNearbyRooms(areas.get(j).getBounds());
		}
	}
	
	public void changeSkin(KeyEvent e){
		if(!(e.getKeyCode() == KeyEvent.VK_C) || selected == 0 || selected == areas.size()-1)
			return;
			
		areas.get(selected).setCurrentFrame(areas.get(selected).getCurrentFrame() + 1);
		
		if(areas.get(selected).getCurrentFrame() > 2)
			areas.get(selected).setCurrentFrame(0);
		
	}
	
	public void addArea(KeyEvent e){
		int direction = -1;
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT && areas.get(selected).iL == -1 && !pressed){
			direction = 0;
			pressed = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT && areas.get(selected).iR == -1 && !pressed){
			direction = 1;
			pressed = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP && areas.get(selected).iU == -1 && !pressed){
			direction = 2;
			pressed = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN && areas.get(selected).iD == -1 && !pressed){
			direction = 3;
			pressed = true;
		}
		
		if(direction == 0){
			areas.add(new Area("src/mapAreaAsset.png", areas.get(selected).x - (140+48), areas.get(selected).y, 0, 3, false));
			areas.get(areas.size()-1).setAnimate(false);
			areas.get(areas.size()-1).setCreator(selected);
			
			bridges.add(new Object(areas.get(areas.size()-1).x + (140) , areas.get(areas.size()-1).y + (116/2 - 14)));
			bridges.get(bridges.size()-1).loadImage("src/mapBridgeOAsset.png");
			
			areas.get(selected).setIndexL(bridges.size()-1);
			areas.get(areas.size()-1).setIndexR(bridges.size()-1);
			
			pressed = true;
		}
		
		if(direction == 1){
			areas.add(new Area("src/mapAreaAsset.png", areas.get(selected).x + (140+48), areas.get(selected).y, 0, 3, false));
			areas.get(areas.size()-1).setAnimate(false);
			areas.get(areas.size()-1).setCreator(selected);
			
			bridges.add(new Object(areas.get(areas.size()-1).x  - (48) , areas.get(areas.size()-1).y + (116/2 - 14)));
			bridges.get(bridges.size()-1).loadImage("src/mapBridgeOAsset.png");
			
			areas.get(selected).setIndexR(bridges.size()-1);
			areas.get(areas.size()-1).setIndexL(bridges.size()-1);
			
			pressed = true;
		}
		
		if(direction == 2){
			areas.add(new Area("src/mapAreaAsset.png", areas.get(selected).x, areas.get(selected).y - (116+48), 0, 3, false));
			areas.get(areas.size()-1).setAnimate(false);
			areas.get(areas.size()-1).setCreator(selected);
			
			bridges.add(new Object(areas.get(areas.size()-1).x + (140/2 - 14) , areas.get(areas.size()-1).y + (116)));
			bridges.get(bridges.size()-1).loadImage("src/mapBridgeVAsset.png");
			
			areas.get(selected).setIndexU(bridges.size()-1);
			areas.get(areas.size()-1).setIndexD(bridges.size()-1);
			
			pressed = true;
		}
		
		if(direction == 3){			
			areas.add(new Area("src/mapAreaAsset.png", areas.get(selected).x, areas.get(selected).y + (116+48), 0, 3, false));
			areas.get(areas.size()-1).setAnimate(false);
			areas.get(areas.size()-1).setCreator(selected);
			
			bridges.add(new Object(areas.get(areas.size()-1).x + (140/2 - 14) , areas.get(areas.size()-1).y - (48)));
			bridges.get(bridges.size()-1).loadImage("src/mapBridgeVAsset.png");
			
			areas.get(selected).setIndexD(bridges.size()-1);
			areas.get(areas.size()-1).setIndexU(bridges.size()-1);
			
			pressed = true;
		}
		
		controlPositions();
		
		boss.setX(areas.get(areas.size() - 1).x + 42);
		boss.setY(areas.get(areas.size() - 1).y + 48);
		
		//System.out.println(areas.size());
		
	}
	
	public void moveMap(KeyEvent e){
		if(pressed)
			return;
		
		int xValue = 0, yValue = 0;
		
		if(e.getKeyCode() == KeyEvent.VK_A && areas.get(selected).iL != -1){
			xValue = (188);
			pressed = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_D && areas.get(selected).iR != -1){
			xValue = -(188);
			pressed = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_W && areas.get(selected).iU != -1){
			yValue = (164);
			pressed = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_S && areas.get(selected).iD != -1){
			yValue = -(164);
			pressed = true;
		}
			
		
		for(int i = 0; i < areas.size(); i++){
			areas.get(i).setX(areas.get(i).getX() + xValue);
			areas.get(i).setY(areas.get(i).getY() + yValue);
		}
		
		for(int i = 0; i < bridges.size(); i++){
			bridges.get(i).setX(bridges.get(i).getX() + xValue);
			bridges.get(i).setY(bridges.get(i).getY() + yValue);
		}
		
		checkSelected();
		
		hub.setX(areas.get(0).x + 50);
		hub.setY(areas.get(0).y + 48);
		
		boss.setX(areas.get(areas.size() - 1).x + 42);
		boss.setY(areas.get(areas.size() - 1).y + 48);
			
	}
	
	private class Area extends AnimatedObject{

		int creator;
		int iR, iL, iU, iD;
		ArrayList<Integer> directions;
		
		public Area(String filePath, int x, int y, int d, int nf, boolean l) {
			super(filePath, x, y, d, nf, l);
			
			iL = iU = iR = iD = creator = -1;
			directions = new ArrayList<>();
		}
		
		public void setCreator(int value){
			creator = value;
		}
		
		public int getCreator(){
			return creator;
		}
		
		public void checkNearbyRooms(Rectangle r){
			if(r.x == (x - (140+48)) && r.y == y && iL == -1)
				iL = -2;
			
			if(r.x == (x + (140+48)) && r.y == y && iR == -1)
				iR = -2;
			
			if(r.x == x && r.y == (y - (116+48)) && iU == -1)
				iU = -2;
			
			if(r.x == x && r.y == (y + (116+48)) && iD == -1)
				iD = -2;
				
		}
		
		public void setIndexL(int value){
			iL = value;
			directions.add(0);
		}
		
		public void setIndexU(int value){
			iU = value;
			directions.add(1);
		}
		
		public void setIndexR(int value){
			iR = value;
			directions.add(2);
		}
		
		public void setIndexD(int value){
			iD = value;
			directions.add(3);
		}
		
	}
}
