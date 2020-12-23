package com.Artoriasoft;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import javax.swing.ImageIcon;

public class DungeonManager {
	
	ArrayList<Area> path = null;
	ArrayList<Area> areaPalette = null;
	ArrayList<Bridge> bridges = null;
	boolean allClear;
	int index, startPos, arrivePos;
	
	public DungeonManager(){
		bridges = new ArrayList<>();
		path = new ArrayList<>();
		index = startPos = arrivePos;
		allClear = true;
		
		if(path.isEmpty())
		{
			path.add(new Area(0, 0, "src/floor.png"));
			path.get(path.size()-1).setBackground("src/area_bg.png");
			path.get(path.size()-1).setVisited();
		}
		
	}
	
	public void reset(){
		int index = path.size()-1;
		
		while(index > 0){
			path.remove(index);
			index--;
		}
		
		bridges.clear();
		
		path.get(0).rightBridge = -1;
		path.get(0).leftBridge = -1;
		path.get(0).upBridge = -1;
		path.get(0).downBridge = -1;
	}
	
	public void setAllVisible(){
		for(int i = 0; i < path.size(); i++){
			path.get(i).setAllVis();
		}
	}
	
	public void generatePathFormMap(Map m){
		
		for(int i = 1; i < m.areas.size(); i++){
			generateAreaFromCreator(m.getCurrentAreaFrame(i), m.getAreaCreator(i), m.getAreaBounds(i), m.getAreaBounds(m.getAreaCreator(i)));
		}
		
		for(int i = 1; i < path.size() - 1; i++)
			path.get(i).fillRoom();
		
		path.get(path.size() - 1).loadImage("src/floor_boss.png");
		path.get(path.size() - 1).offset = null;
		path.get(path.size() - 1).fillRoom();
		path.get(path.size() - 1).setTorchesYellow();
		
	}
	
	public void generateAreaFromCreator(int frame, int indexCreator,Rectangle next, Rectangle creator){
		
		/* Left */
		if(next.x < creator.x && next.y == creator.y){
			path.add(new Area(path.get(indexCreator).x - 400, path.get(indexCreator).y, "src/floor.png"));
			path.get(path.size()-1).x -= path.get(path.size()-1).width;
			
			if(frame == 1){
				path.get(path.size()-1).loadImage("src/floor_1.png");
				path.get(path.size()-1).setBackground("src/area_bg.png");
				path.get(path.size() - 1).addOffset(308, 232, 136, 440);
			}
			else if(frame == 2){
				path.get(path.size()-1).loadImage("src/floor_2.png");
				path.get(path.size()-1).setBackground("src/area_bg.png");
				path.get(path.size() - 1).addOffset(344, 240, 440, 440);
			}
			else{
				path.get(path.size()-1).loadImage("src/floor_3.png");
				path.get(path.size()-1).setBackground("src/area_bg.png");
			}
			
			bridges.add(new Bridge(path.get(indexCreator).x - 400,
					(path.get(path.size()-1).y + (path.get(path.size()-1).height/2) - 120), "src/bridgeO.png", "src/bridgeO_bg.png"));
			
			path.get(indexCreator).leftBridge = bridges.size()-1;
			path.get(path.size()-1).rightBridge = bridges.size()-1;
			
		}
		
		/* Up */
		if(next.x == creator.x && next.y < creator.y){
			path.add(new Area(path.get(indexCreator).x, path.get(indexCreator).y - 400, "src/floor.png"));
			path.get(path.size() - 1).y -= path.get(path.size() - 1).height;
			
			if(frame == 1){
				path.get(path.size()-1).loadImage("src/floor_1.png");
				path.get(path.size()-1).setBackground("src/area_bg.png");
				path.get(path.size() - 1).addOffset(308, 232, 136, 440);
			}
			else if(frame == 2){
				path.get(path.size()-1).loadImage("src/floor_2.png");
				path.get(path.size()-1).setBackground("src/area_bg.png");
				path.get(path.size() - 1).addOffset(344, 240, 440, 440);
			}
			else{
				path.get(path.size()-1).loadImage("src/floor_3.png");
				path.get(path.size()-1).setBackground("src/area_bg.png");
			}
			
			bridges.add(new Bridge((path.get(indexCreator).x + (path.get(indexCreator).width/2) - 120),
					path.get(indexCreator).y - 400 , "src/bridgeV.png", null));
			
			path.get(indexCreator).upBridge = bridges.size()-1;
			path.get(path.size()-1).downBridge = bridges.size()-1;
			
		}
		
		/* Right */
		if(next.x > creator.x && next.y == creator.y){
			path.add(new Area(path.get(indexCreator).x + path.get(indexCreator).width + 400, path.get(indexCreator).y, "src/floor.png"));
			
			if(frame == 1){
				path.get(path.size()-1).loadImage("src/floor_1.png");
				path.get(path.size()-1).setBackground("src/area_bg.png");
				path.get(path.size() - 1).addOffset(308, 232, 136, 440);
			}
			else if(frame == 2){
				path.get(path.size()-1).loadImage("src/floor_2.png");
				path.get(path.size()-1).setBackground("src/area_bg.png");
				path.get(path.size() - 1).addOffset(344, 240, 440, 440);
			}
			else{
				path.get(path.size()-1).loadImage("src/floor_3.png");
				path.get(path.size()-1).setBackground("src/area_bg.png");
			}
			
			bridges.add(new Bridge(path.get(path.size()-1).x - 400,
					(path.get(path.size()-1).y + (path.get(path.size()-1).height/2) - 120), "src/bridgeO.png", "src/bridgeO_bg.png"));
			
			path.get(indexCreator).rightBridge = bridges.size()-1;
			path.get(path.size()-1).leftBridge = bridges.size()-1;
		}
		
		
		/* Down */
		if(next.x == creator.x && next.y > creator.y){
			path.add(new Area(path.get(indexCreator).x, path.get(indexCreator).y + path.get(indexCreator).height + 400, "src/floor.png"));
			
			if(frame == 1){
				path.get(path.size()-1).loadImage("src/floor_1.png");
				path.get(path.size()-1).setBackground("src/area_bg.png");
				path.get(path.size() - 1).addOffset(308, 232, 136, 440);
			}
			else if(frame == 2){
				path.get(path.size()-1).loadImage("src/floor_2.png");
				path.get(path.size()-1).setBackground("src/area_bg.png");
				path.get(path.size() - 1).addOffset(344, 240, 440, 440);
			}
			else{
				path.get(path.size()-1).loadImage("src/floor_3.png");
				path.get(path.size()-1).setBackground("src/area_bg.png");
			}
			
			bridges.add(new Bridge((path.get(path.size()-1).x + (path.get(path.size()-1).width/2) - 120),
					path.get(path.size()-1).y - 400 , "src/bridgeV.png", null));
			
			path.get(indexCreator).downBridge = bridges.size()-1;
			path.get(path.size()-1).upBridge = bridges.size()-1;
		}
	}
	
	
	/* questo metodo dovrà generare il percorso in base al numero di "stanze" 
	 * che vogliamo ci siano tra un hub e un altro */
	public void generatePath(int i){
		i--;
		
		while(path.size() < i){
			addArea(path.size() - 1);
		}
		
		path.get(path.size() - 1).loadImage("src/floor_boss.png");
		path.get(path.size() - 1).offset = null;
		path.get(path.size() - 1).fillRoom();
		path.get(path.size() - 1).setTorchesYellow();
		
	}
	
	/* metodo in "fase di allestimento", una volta finito dovrebbe decidere autonomamente in che
	 * direzione generare la stanza successiva */
	public void addArea(int i){
		boolean l = false,  u = false, r = false, d = false;
		int random, stanzeMax = 0;
		
		if(path.get(i).leftBridge == -1){
			boolean b = true;
			for(int j = 0; j < path.size(); j++){
				if(path.get(j).x == path.get(i).x - path.get(i).width - 400 && path.get(j).y == path.get(i).y)
					b = false;
			}
			if(b){
				l = true;
				stanzeMax++;
			}
		}
		if(path.get(i).upBridge == -1){
			boolean b = true;
			for(int j = 0; j < path.size(); j++){
				if(path.get(j).y == path.get(i).y - path.get(i).height - 400 && path.get(j).x == path.get(i).x)
					b = false;
			}
			if(b){
				u = true;
				stanzeMax++;
			}
		}
		if(path.get(i).rightBridge == -1){
			boolean b = true;
			for(int j = 0; j < path.size(); j++){
				if(path.get(j).x ==  path.get(i).x + path.get(i).width + 400 && path.get(j).y == path.get(i).y)
					b = false;
			}
			if(b){
				r = true;
				stanzeMax++;
			}
		}
		if(path.get(i).downBridge == -1){
			boolean b = true;
			for(int j = 0; j < path.size(); j++){
				if(path.get(j).y == path.get(i).y + path.get(i).height + 400 && path.get(j).x == path.get(i).x)
					b = false;
			}
			if(b){
				d = true;
				stanzeMax++;
			}
		}
		
		int numStanze = new Random().nextInt(stanzeMax)+1;
		
		/* ricordati che ogni volta che aggiungi una stanza devi mettere
		 * un nuovo numero randomico e fare in modo che quel numero dipenda da quante stanze vuoi creare 
		 * stanzeMax = 3 e che eviti il numero che rappresenta una stanza gia presente */
		for(;numStanze > 0; numStanze--){
			random = new Random().nextInt(4);
			
			if(l && random == 0){
				path.add(new Area(path.get(i).x - 400, path.get(i).y, "src/floor.png"));
				path.get(path.size()-1).x -= path.get(path.size()-1).width;
				
				int randomRoom = new Random().nextInt(3);
				
				if(randomRoom == 0){
					path.get(path.size()-1).loadImage("src/floor_1.png");
					path.get(path.size()-1).setBackground("src/area_bg.png");
					path.get(path.size() - 1).addOffset(308, 232, 136, 440);
				}
				else if(randomRoom == 1){
					path.get(path.size()-1).loadImage("src/floor_2.png");
					path.get(path.size()-1).setBackground("src/area_bg.png");
					path.get(path.size() - 1).addOffset(344, 240, 440, 440);
				}
				else{
					path.get(path.size()-1).loadImage("src/floor_3.png");
					path.get(path.size()-1).setBackground("src/area_bg.png");
				}
				
				int upperY = 0, lowerY = 0;
				
				if(path.get(i).y <= path.get(path.size()-1).y){
					upperY = path.get(path.size()-1).y;
					lowerY = path.get(i).y + path.get(i).height;
				}
				else{
					upperY = path.get(i).y;
					lowerY = path.get(path.size()-1).y + path.get(path.size()-1).height;
				}
						
				int positionY = new Random().nextInt(lowerY - upperY - 240) + upperY;
			
				bridges.add(new Bridge(path.get(path.size()-1).x + path.get(path.size()-1).width,
						positionY, "src/bridgeO.png", "src/bridgeO_bg.png"));
			
				path.get(i).leftBridge = bridges.size()-1;
				path.get(path.size()-1).rightBridge = bridges.size()-1;
				
				l = false;
				
				path.get(path.size()-1).fillRoom();
			}
		
			if(u && random == 1){
				path.add(new Area(path.get(i).x, path.get(i).y - 400, "src/floor.png"));
				path.get(path.size() - 1).y -= path.get(path.size() - 1).height;
				
				int randomRoom = new Random().nextInt(3);
				
				if(randomRoom == 0){
					path.get(path.size()-1).loadImage("src/floor_1.png");
					path.get(path.size()-1).setBackground("src/area_bg.png");
					path.get(path.size() - 1).addOffset(308, 232, 136, 440);
				}
				else if(randomRoom == 1){
					path.get(path.size()-1).loadImage("src/floor_2.png");
					path.get(path.size()-1).setBackground("src/area_bg.png");
					path.get(path.size() - 1).addOffset(344, 240, 440, 440);
				}
				else{
					path.get(path.size()-1).loadImage("src/floor_3.png");
					path.get(path.size()-1).setBackground("src/area_bg.png");
				}
				
				int upperX = 0, lowerX = 0;
				
				if(path.get(i).x <= path.get(path.size()-1).x){
					upperX = path.get(path.size()-1).x;
					lowerX = path.get(i).x + path.get(i).width;
				}
				else{
					upperX = path.get(i).x;
					lowerX = path.get(path.size()-1).x + path.get(path.size()-1).width;
				}
				
				int positionX = new Random().nextInt(lowerX - upperX - 240) + upperX;
				
				bridges.add(new Bridge(positionX,
						path.get(path.size()-1).y + path.get(path.size()-1).height, "src/bridgeV.png", null));
				
				path.get(i).upBridge = bridges.size()-1;
				path.get(path.size()-1).downBridge = bridges.size()-1;
				
				u = false;

				path.get(path.size()-1).fillRoom();
			}
		
			if(r && random == 2){
				path.add(new Area(path.get(i).x + path.get(i).width + 400, path.get(i).y, "src/floor.png"));
				
				int randomRoom = new Random().nextInt(3);
				
				if(randomRoom == 0){
					path.get(path.size()-1).loadImage("src/floor_1.png");
					path.get(path.size()-1).setBackground("src/area_bg.png");
					path.get(path.size()-1).addOffset(308, 232, 136, 440);
				}
				else if(randomRoom == 1){
					path.get(path.size()-1).loadImage("src/floor_2.png");
					path.get(path.size()-1).setBackground("src/area_bg.png");
					path.get(path.size()-1).addOffset(344, 240, 440, 440);
				}
				else{
					path.get(path.size()-1).loadImage("src/floor_3.png");
					path.get(path.size()-1).setBackground("src/area_bg.png");
				}
				
				int upperY = 0, lowerY = 0;
				
				if(path.get(i).y <= path.get(path.size()-1).y){
					upperY = path.get(path.size()-1).y;
					lowerY = path.get(i).y + path.get(i).height;
				}
				else{
					upperY = path.get(i).y;
					lowerY = path.get(path.size()-1).y + path.get(path.size()-1).height;
				}
					
					
				 int positionY = new Random().nextInt(lowerY - upperY - 240) + upperY;
				  
		
				bridges.add(new Bridge(path.get(i).x + path.get(i).width,
						positionY, "src/bridgeO.png", "src/bridgeO_bg.png"));
			
				path.get(i).rightBridge = bridges.size()-1;
				path.get(path.size()-1).leftBridge = bridges.size()-1;
				
				r = false;

				path.get(path.size()-1).fillRoom();
			}
		
			if(d && random == 3){
				path.add(new Area(path.get(i).x, path.get(i).y + path.get(i).height + 400, "src/floor.png"));

				int randomRoom = new Random().nextInt(3);
				
				if(randomRoom == 0){
					path.get(path.size()-1).loadImage("src/floor_1.png");
					path.get(path.size()-1).setBackground("src/area_bg.png");
					path.get(path.size()-1).addOffset(308, 232, 136, 440);
				}
				else if(randomRoom == 1){
					path.get(path.size()-1).loadImage("src/floor_2.png");
					path.get(path.size()-1).setBackground("src/area_bg.png");
					path.get(path.size()-1).addOffset(344, 240, 440, 440);
				}
				else{
					path.get(path.size()-1).loadImage("src/floor_3.png");
					path.get(path.size()-1).setBackground("src/area_bg.png");
				}
				
				int upperX = 0, lowerX = 0;
				
				if(path.get(i).x <= path.get(path.size()-1).x){
					upperX = path.get(path.size()-1).x;
					lowerX = path.get(i).x + path.get(i).width;
				}
				else{
					upperX = path.get(i).x;
					lowerX = path.get(path.size()-1).x + path.get(path.size()-1).width;
				}
				
				int positionX = new Random().nextInt(lowerX - upperX - 240) + upperX;
			
				bridges.add(new Bridge(positionX,
						path.get(i).y + path.get(i).height, "src/bridgeV.png", null));
			
				path.get(i).downBridge = bridges.size()-1;
				path.get(path.size()-1).upBridge = bridges.size()-1;
				
				d = false;

				path.get(path.size()-1).fillRoom();
			}
		}
	}
	
	/* disegna l'intero dungeon */
	public void drawDungeon(Graphics2D g2d){
		
		//int cont = 0;
		for(int i = 0; i < path.size(); i++){
			if(((path.get(i).x > Board.camX - 1000 && path.get(i).x < Board.camX + mainclass.screenSize.width + 1000) || (path.get(i).x + path.get(i).width > Board.camX - 1000 && path.get(i).x + path.get(i).width < Board.camX + mainclass.screenSize.width + 1000)) 
			&& ((path.get(i).y > Board.camY - 1000 && path.get(i).y < Board.camY + mainclass.screenSize.height + 1000) || (path.get(i).y + path.get(i).height > Board.camY - 1000 && path.get(i).y + path.get(i).height <  Board.camY + mainclass.screenSize.height + 1000))){
				path.get(i).drawArea(g2d);
				//cont++;
			}	
		}
		
		if(allClear){
			//System.out.println(bridges.get(path.get(0).leftBridge).height);
			for(int i = 0; i < bridges.size(); i++){
				bridges.get(i).drawBridge(g2d);
			}
		}
		//System.out.println(cont);
	}
	
	public void drawDungeonTorchesUpper(Graphics2D g2d, Rectangle p){
		for(int i = 0; i < path.size(); i++){
			if(((path.get(i).x > Board.camX - 1000 && path.get(i).x < Board.camX + mainclass.screenSize.width + 1000) || (path.get(i).x + path.get(i).width > Board.camX - 1000 && path.get(i).x + path.get(i).width < Board.camX + mainclass.screenSize.width + 1000)) 
			&& ((path.get(i).y > Board.camY - 1000 && path.get(i).y < Board.camY + mainclass.screenSize.height + 1000) || (path.get(i).y + path.get(i).height > Board.camY - 1000 && path.get(i).y + path.get(i).height <  Board.camY + mainclass.screenSize.height + 1000))){
				path.get(i).drawTorchesUpper(g2d, p);
			}	
		}
	}
	
	public void drawDungeonTorchesLower(Graphics2D g2d, Rectangle p){
		for(int i = 0; i < path.size(); i++){
			if(((path.get(i).x > Board.camX - 1000 && path.get(i).x < Board.camX + mainclass.screenSize.width + 1000) || (path.get(i).x + path.get(i).width > Board.camX - 1000 && path.get(i).x + path.get(i).width < Board.camX + mainclass.screenSize.width + 1000)) 
			&& ((path.get(i).y > Board.camY - 1000 && path.get(i).y < Board.camY + mainclass.screenSize.height + 1000) || (path.get(i).y + path.get(i).height > Board.camY - 1000 && path.get(i).y + path.get(i).height <  Board.camY + mainclass.screenSize.height + 1000))){
				path.get(i).drawTorchesLower(g2d, p);
			}
		}
	}
	
	public void drawDungeonUpper(Graphics2D g2d, Rectangle p){
		for(int i = 0; i < path.size(); i++){
			if(((path.get(i).x > Board.camX - 1000 && path.get(i).x < Board.camX + mainclass.screenSize.width + 1000) || (path.get(i).x + path.get(i).width > Board.camX - 1000 && path.get(i).x + path.get(i).width < Board.camX + mainclass.screenSize.width + 1000)) 
			&& ((path.get(i).y > Board.camY - 1000 && path.get(i).y < Board.camY + mainclass.screenSize.height + 1000) || (path.get(i).y + path.get(i).height > Board.camY - 1000 && path.get(i).y + path.get(i).height <  Board.camY + mainclass.screenSize.height + 1000))){
				path.get(i).drawUpper(g2d, p);
			}	
		}
	}
	
	public void drawDungeonLower(Graphics2D g2d, Rectangle p){
		for(int i = 0; i < path.size(); i++){
			if(((path.get(i).x > Board.camX - 1000 && path.get(i).x < Board.camX + mainclass.screenSize.width + 1000) || (path.get(i).x + path.get(i).width > Board.camX - 1000 && path.get(i).x + path.get(i).width < Board.camX + mainclass.screenSize.width + 1000)) 
			&& ((path.get(i).y > Board.camY - 1000 && path.get(i).y < Board.camY + mainclass.screenSize.height + 1000) || (path.get(i).y + path.get(i).height > Board.camY - 1000 && path.get(i).y + path.get(i).height <  Board.camY + mainclass.screenSize.height + 1000))){
				path.get(i).drawLower(g2d, p);
			}
		}
	}
	
	/*public void resetIndexRoom(){
		for(int i = 0; i < path.size(); i++)
			path.get(i).roomObjectIndex = 0;
	}*/
	
	/* setta la variabile che definisce se la stanza è priva di nemici o meno */
	public void setAllClear(boolean b){
		if(b != allClear)
			allClear = b;
	}
	
	public void setCurrentVisited(){
		path.get(index).setVisited();
	}
	
	public boolean getCurrentVisited(){
		return path.get(index).visited;
	}
	
	public Rectangle currentLeftBridge(){
		if(path.get(index).leftBridge == -1 || !allClear)
			return null;
		
		return bridges.get(path.get(index).leftBridge).getBounds();
	}
	
	public Rectangle currentUpBridge(){
		if(path.get(index).upBridge == -1 || !allClear)
			return null;
		
		return bridges.get(path.get(index).upBridge).getBounds();
	}
	
	public Rectangle currentRightBridge(){
		if(path.get(index).rightBridge == -1 || !allClear)
			return null;
		
		return bridges.get(path.get(index).rightBridge).getBounds();
	}
	
	public Rectangle currentDownBridge(){
		if(path.get(index).downBridge == -1 || !allClear)
			return null;
		
		return bridges.get(path.get(index).downBridge).getBounds();
	}
	
	/* la variabile index rappresenta la posizione nel ArrayList della stanza 
	 * nella quale è presente il giocatore */
	public boolean setIndex(Rectangle p){
		int remember = index;
		for(int i = 0; i < path.size(); i++){
			if(p.x > path.get(i).x && p.x < path.get(i).x + path.get(i).width && 
					p.y > path.get(i).y && p.y < path.get(i).y + path.get(i).height &&
						p.width > path.get(i).x && p.width < path.get(i).x + path.get(i).width &&
							p.height > path.get(i).y && p.height < path.get(i).y + path.get(i).height)
				index = i;
		}
		
		return index != remember;
	}
	
	/* ritorna i margini del pavimento */
	public Rectangle getCurrentBounds(){
		return path.get(index).getBounds();
	}
	
	/* ritorna i margini della zona inaccessibile */
	public Rectangle getCurrentOffset(){
		return path.get(index).offset;
	}
	
	public int getCurrentX(){
		return path.get(index).getX();
	}
	
	public int getCurrentY(){
		return path.get(index).getY();
	}
	
	/* class Area*/
	private class Area extends Object{
		
		/* il rettangolo di offset rappresenta un area della attuale "stanza" all'interno della
		 * quale il giocatore non può entrare(nel caso di una "stanza" priva di zone inaccessibili
		 * il rettangolo di offset viene lasciato allo stato di null)*/
		Rectangle offset = null;
		Object background = null;
		int leftBridge, upBridge, rightBridge, downBridge;
		//int roomObjectIndex;
		boolean visited;
		ArrayList<AnimatedObject> torches = null;
		ArrayList<AnimatedObject> roomObjects = null;
		ArrayList<AnimatedObject> riverObjects = null;
		
		/* costruttore per "stanze" prive di zone inacessibili */
		public Area(int x, int y, String filePath){
			super(x, y);
			super.loadImage(filePath);
			
			leftBridge = upBridge = rightBridge = downBridge = -1;
			//roomObjectIndex = 0;
			visited = false;
			torches = new ArrayList<>();
			roomObjects = new ArrayList<>();
			riverObjects = new ArrayList<>();
		}
		
		/* costruttore per "stanze" munite di zone inaccessibili */
		public Area(int x, int y, String filePath , int xOs, int yOs, int wOs, int hOs){
			super(x, y);
			super.loadImage(filePath);
			
			
			leftBridge = upBridge = rightBridge = downBridge = -1;
			//roomObjectIndex = 0;
			offset = new Rectangle(x+xOs, y+yOs, x+(xOs+wOs), y+(yOs+hOs));	
			
			torches = new ArrayList<>();
			roomObjects = new ArrayList<>();
		}
		
		public void setVisited(){
			if(!visited)
				visited = true;
		}
		
		public void setAllVis(){
			for(int i = 0; i < roomObjects.size(); i++)
				roomObjects.get(i).vis = true;
		}
		
		public void fillRoom(){
			
			if(!roomObjects.isEmpty())
				roomObjects.clear();
			
			if(!torches.isEmpty())
				torches.clear();
			
			
			if(torches.isEmpty()){
				int numTorches = new Random().nextInt(4)+1;
				boolean t0 = false, t1 = false, t2 = false, t3 = false;
				
				while(torches.size() < numTorches){
					int random = new Random().nextInt(4);
				
					if(random == 0 && !t0){
						torches.add(new AnimatedObject("src/torchBlue.png", 0, 0, 150, 4, true));
						torches.get(torches.size()-1).setX(x - 88);
						torches.get(torches.size()-1).setY(y - torches.get(torches.size()-1).height + 32);
						t0 = true;
					}
					else if(random == 1 && !t1){
						torches.add(new AnimatedObject("src/torchBlue.png", 0, 0, 150, 4, true));
						torches.get(torches.size()-1).setX(x + width - torches.get(torches.size()-1).width + 88);
						torches.get(torches.size()-1).setY(y - torches.get(torches.size()-1).height + 32);
						t1 = true;
					}
					else if(random == 2 && !t2){
						torches.add(new AnimatedObject("src/torchBlue.png", 0, 0, 150, 4, true));
						torches.get(torches.size()-1).setX(x - 88);
						torches.get(torches.size()-1).setY(y + height - torches.get(torches.size()-1).height - 32);
						t2 = true;
					}
					else if(random == 3 && !t3){
						torches.add(new AnimatedObject("src/torchBlue.png", 0, 0, 150, 4, true));
						torches.get(torches.size()-1).setX(x + width - torches.get(torches.size()-1).width + 88);
						torches.get(torches.size()-1).setY(y + height - torches.get(torches.size()-1).height - 32);
						t3 = true;
					}
				
				}
			}
			
			if(offset == null){
				int numObjects = new Random().nextInt(5)+1;
				while(roomObjects.size() < numObjects){
					roomObjects.add(new AnimatedObject("src/rocks.png", 0, 0, 0, 7, false));
					roomObjects.get(roomObjects.size()-1).setAnimate(false);
					roomObjects.get(roomObjects.size()-1).setCurrentFrame(new Random().nextInt(7));
					roomObjects.get(roomObjects.size()-1).setX(new Random().nextInt(Math.abs(width-120)) + (x+60));
					roomObjects.get(roomObjects.size()-1).setY(new Random().nextInt(Math.abs(height-100))+ (y+40));
				}
				while(roomObjects.size() < numObjects*2){
					roomObjects.add(new AnimatedObject("src/candleBlue.png", 0, 0, 100, 4, true));
					roomObjects.get(roomObjects.size()-1).setX(new Random().nextInt(Math.abs(width-120)) + (x+30));
					roomObjects.get(roomObjects.size()-1).setY(new Random().nextInt(Math.abs(height-100))+ (y+20));
				}
			}
			
			int numRiver = new Random().nextInt(2)+1;
			
			while(riverObjects.size() < numRiver){
				int choiceSide = 0;
				
				if(choiceSide == 0){
					int randNum = new Random().nextInt(4)+riverObjects.size();
					while(riverObjects.size() < randNum){
						int randomX = new Random().nextInt(120) + (x-140);
						int randomY = new Random().nextInt(height - 88) + (y);
						int randomImage = new Random().nextInt(3);
					
						if(randomImage == 0){
							riverObjects.add(new AnimatedObject("src/pilars.png", randomX, randomY, 0, 4, false));
							riverObjects.get(riverObjects.size()-1).setAnimate(false);
							riverObjects.get(riverObjects.size()-1).setCurrentFrame(new Random().nextInt(4));
						
						}
						if(randomImage == 1){
							riverObjects.add(new AnimatedObject("src/plant1.png", randomX, randomY, 150, 4, true));
						}
						if(randomImage == 2){
							riverObjects.add(new AnimatedObject("src/plant2.png", randomX, randomY, 150, 4, true));
						}
					}
				}
				choiceSide++;
				
				if(choiceSide == 1){
					int randNum = new Random().nextInt(4)+riverObjects.size();
					while(riverObjects.size() < randNum){
						int randomX = new Random().nextInt(120) + (x+width+136);
						int randomY = new Random().nextInt(height - 88) + (y);
						int randomImage = new Random().nextInt(3);
					
						if(randomImage == 0){
							riverObjects.add(new AnimatedObject("src/pilars.png", randomX, randomY, 0, 4, false));
							riverObjects.get(riverObjects.size()-1).setAnimate(false);
							riverObjects.get(riverObjects.size()-1).setCurrentFrame(new Random().nextInt(4));
						
						}
						if(randomImage == 1){
							riverObjects.add(new AnimatedObject("src/plant1.png", randomX, randomY, 150, 4, true));
						}
						if(randomImage == 2){
							riverObjects.add(new AnimatedObject("src/plant2.png", randomX, randomY, 150, 4, true));
						}
					}
				}
				
				choiceSide++;
				
				if(choiceSide == 2){
					int randNum = new Random().nextInt(4)+riverObjects.size();
					while(riverObjects.size() < randNum){
						int randomX = new Random().nextInt(width) + (x);
						int randomY = new Random().nextInt(98) + (y - 88);
						int randomImage = new Random().nextInt(3);
					
						if(randomImage == 0){
							riverObjects.add(new AnimatedObject("src/pilars.png", randomX, randomY, 0, 4, false));
							riverObjects.get(riverObjects.size()-1).setAnimate(false);
							riverObjects.get(riverObjects.size()-1).setCurrentFrame(new Random().nextInt(4));
						
						}
						if(randomImage == 1){
							riverObjects.add(new AnimatedObject("src/plant1.png", randomX, randomY, 150, 4, true));
						}
						if(randomImage == 2){
							riverObjects.add(new AnimatedObject("src/plant2.png", randomX, randomY, 150, 4, true));
						}
					}
				}
			}
			
			
			/* Ordinamento */
			Collections.sort(roomObjects, new Comparator<AnimatedObject>() {
				public int compare(AnimatedObject o1, AnimatedObject o2) {
					return o1.y <= o2.y ? -1 : o1.y == o2.y ? 0 : 1;
				}
			}
			);
			
			Collections.sort(riverObjects, new Comparator<AnimatedObject>() {
				public int compare(AnimatedObject o1, AnimatedObject o2) {
					return o1.y <= o2.y ? -1 : o1.y == o2.y ? 0 : 1;
				}
			}
			);
			
			/* fine ordinamento */
			
			/*System.out.println("nuova stanza");
			for(int i = 0; i < roomObjects.size(); i++)
				System.out.println("roomObject " + roomObjects.get(i).y);
		
			for(int i = 0; i < riverObjects.size(); i++)
				System.out.println("riverObjects " + riverObjects.get(i).y);
			*/
		}
		
		public void setBackground(String bgPath){
			background = new Object(x, y + height);
			background.loadImage(bgPath);
		}
		
		public void setLeftBridge(int l){
			leftBridge = l;
		}
		
		public void setUpBridge(int u){
			upBridge = u;
		}
		
		public void setRightBridge(int r){
			rightBridge = r;
		}
		
		public void setDownBridge(int d){
			downBridge = d;
		}
		
		/* permette di settare il rettangolo di offset qualora si voglia debuggare senza
		 * intaccare il codice o nel caso in cui si voglia aggiungere un evento che ne crei
		 * una zona inaccessibile prima inesistente*/
		public void addOffset(int xOs, int yOs, int wOs, int hOs){
			offset = new Rectangle(x+xOs, y+yOs, x+(xOs+wOs), y+(yOs+hOs));
		}
		
		/* il seguente metodo deve essere munito di un controllo(da fare possibilmente nel'update
		 * e quindi in un metodo separato) che verifichi se l'oggetto è su schermo */
		public void drawArea(Graphics2D g2d){
			if(!riverObjects.isEmpty()){
				for(int i = 0; i < riverObjects.size();i++)
					riverObjects.get(i).drawAnimation(g2d);
			}
			
			draw(g2d);		
					
			if(background != null)
				background.draw(g2d);		
				
		}
		
		public void drawUpper(Graphics2D g2d, Rectangle p){
			
			if(!roomObjects.isEmpty() && roomObjects != null){
				for(int i = 0; i < roomObjects.size();i++){
					if(p.height <= (roomObjects.get(i).y + roomObjects.get(i).height - 12) && roomObjects.get(i).vis){
						roomObjects.get(i).drawAnimation(g2d);
						roomObjects.get(i).vis  = false;
						//roomObjectIndex++;
					}
				}
			}
			
			/*if(roomObjectIndex > roomObjects.size())
				roomObjectIndex = 0;*/
		}
		
		public void drawLower(Graphics2D g2d, Rectangle p){
			
			if(!roomObjects.isEmpty() && roomObjects != null){
				for(int i = 0; i < roomObjects.size();i++){
					if(p.height > (roomObjects.get(i).y + roomObjects.get(i).height - 12) && roomObjects.get(i).vis){
						roomObjects.get(i).drawAnimation(g2d);
						roomObjects.get(i).vis = false;
						//roomObjectIndex++;
					}
				}
			}
			
			/*if(roomObjectIndex >= roomObjects.size())
				roomObjectIndex = 0;*/
		}
		
		public void drawTorchesUpper(Graphics2D g2d, Rectangle p){
			
			if(!torches.isEmpty() && torches != null){
				for(int i = 0; i < torches.size();i++){
					if(p.height <= (torches.get(i).y + torches.get(i).height - 12))
						torches.get(i).drawAnimation(g2d);
				}
			}
		}
		
		public void drawTorchesLower(Graphics2D g2d, Rectangle p){
			
			if(!torches.isEmpty() && torches != null){
				for(int i = 0; i < torches.size();i++){
					if(p.height > (torches.get(i).y + torches.get(i).height - 12))
						torches.get(i).drawAnimation(g2d);
				}
			}
		}
		
		public void setTorchesYellow(){
			for(int i = 0; i < torches.size();i++){
				torches.get(i).loadImage("src/torchYellow.png");
				torches.get(i).width /= 4;
			}
			
			for(int i = 0; i < roomObjects.size(); i++){
				if(roomObjects.get(i).getDelay() != 0){
					roomObjects.get(i).loadImage("src/candleYellow.png");
					roomObjects.get(i).width /= 4;
				}
			}
		}
		
	}
	/* end of class Area  */
	
	/* class Bridge */
	private class Bridge extends Object{
		
		Object background = null;
		
		public Bridge(int x, int y, String filePath, String bgPath){
			super(x, y);
			super.loadImage(filePath);
			
			if(bgPath != null){
				background = new Object(x, y + height);
				background.loadImage(bgPath);
			}
			
		}
		
		public void drawBridge(Graphics2D g2d){
			draw(g2d);
			if(background != null)
				background.draw(g2d);
		}
		
	}
	/* end of class Bridge */
	
}
