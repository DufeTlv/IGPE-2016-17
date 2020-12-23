package com.Artoriasoft;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends AnimatedObject{

    private int dx, dy;
    protected int health, stamina, maxHealth, maxStamina;
    Rectangle feet;
    Boolean left, right, down, up, isMoving, isLeft, isAttacking, secondAttack, isShieldOn, attackPressed;
    Equipment equipment = null;
    long timer;
    ArrayList<AnimatedObject> dust = null;

    public Player(String filePath, int x, int y, int nf) {
    	super(filePath, x, y, nf);
    	
    	addAnimation("idle_right", 0, 3, 250, true);
    	addAnimation("idle_left", 4, 7, 250, true);
    	addAnimation("walk_right", 8, 15, 83, true);
    	addAnimation("walk_left", 16, 23, 83, true);
    	addAnimation("first_attack_right", 24, 27, 65, false);
    	addAnimation("first_attack_left", 28, 31, 65, false);
    	addAnimation("second_attack_right", 32, 35, 65, false);
    	addAnimation("second_attack_left", 36, 39, 65, false);
    	addAnimation("shield_right", 40, 41, 60, false);
    	addAnimation("shield_left", 42, 43, 60, false);
    	
    	feet = new Rectangle(x + 12, y + height - 16, x + width - 12, y + height + 4);
    	left = right = down = up = isMoving = isLeft = isAttacking = secondAttack = isShieldOn = attackPressed = false;
    	
    	/* un rettangolo 4x4 rappresenta la dimensione attuale di un singolo pixel
    	 * in futuro potrebbe essere una variabile mutata dalla risoluzione dello schermo */
    	dx = dy = 4;
    	
    	/* le variabili "max" potranno essere successivamente utilizzate per  eventuali livellamenti*/
    	health = 70;
    	maxHealth = 70;
    	stamina = 30;
    	maxStamina = 30;
    	timer = 0;
    	
    	equipment = new Equipment();
    	equipment.setCape("src/cape_spritesheet.png", x+16, y-8, 38);
    	equipment.setArms("src/arms_spritesheet.png", x, y, 44);
    	equipment.setHead("src/head_spritesheet.png", x, (y - (4)), 44);
    	equipment.setBody("src/body_spritesheet.png", x, y, 44);
    	equipment.setLegs("src/legs_spritesheet.png", x, y, 44);
    	equipment.setShield("src/new_asset_shield.png", (x - (9 * 4)), y, 44);
    	equipment.setWeapon("src/new_asset_weapon.png", (x - (9 * 4)), y, 44);
    	
    	dust = new ArrayList<>();
    	
    }
    
    public void recallPositions(){
    	feet.setBounds(x + 12, y + height - 16, x + width - 12, y + height + 4);
    	equipment.setPositions(x, y, isLeft);
    }
    
    public void stopMovements(){
    	left = right = up = down = isAttacking = isShieldOn = false;
    	isMoving = false;
    	
    	if(isLeft){
    		setAnimations(1);
    		equipment.cape.setAnimation(1);
    	}
    	else{
    		setAnimations(0);
    		equipment.cape.setAnimation(0);
    	}
    }

    public void move(Rectangle room, Rectangle offset, EnemyManager enemymanager, Rectangle bridgeL, Rectangle bridgeU, Rectangle bridgeR, Rectangle bridgeD, BossManager boss) {
    	
    	/* ogni controllo gestisce il movimenti del personaggio all'interno dell'area
    	 * delimitata dai margini del pavimento e da quelli del rettangolo di offset(vedere classe DungeonManager) */
    	
    	if(!isAttacking && !isShieldOn){
    		if(left){
    			
    			if((/**/(feet.y >= room.y && feet.height <= room.height) && ((offset == null || (offset != null && (!(((feet.y > offset.y && feet.y < offset.height) || (feet.height > offset.y && feet.height < offset.height)) && (feet.x >= offset.width))))) &&
    				(((bridgeL == null || (bridgeL != null && (feet.y < bridgeL.y || feet.height > bridgeL.height))) && feet.x-dx >= room.x) || (bridgeL != null && ((feet.y >= bridgeL.y && feet.y <= bridgeL.height) && (feet.height >= bridgeL.y && feet.height <= bridgeL.height)))) 
        			) || ( offset != null && (((feet.y > offset.y && feet.y < offset.height) || (feet.height > offset.y && feet.height < offset.height)) && (feet.x-dx >= offset.width))) /**/)
        			|| ((feet.y < room.y && feet.x-dx >= bridgeU.x) || (feet.height > room.height&& feet.x-dx >= bridgeD.x))){
    				/* aggiungere qui ulteriori controlli con if separati */
    				
    				/*if(enemymanager.getEnemies() != null && !enemymanager.getEnemies().isEmpty() || boss.bosses.size() != 0 ){
    					boolean b = true;
    					if (enemymanager.getEnemies() != null && !enemymanager.getEnemies().isEmpty()){
    						for (int i = 0; i < enemymanager.getEnemies().size(); i++){
    	    					if (!(enemymanager.checkEnemyCollision(enemymanager.getEnemies().get(i), "left", x, y, height, width)))
    	    						b = false;
    	    				}
    					}
    					
    					if (boss != null && boss.bosses.size() != 0 && boss.bosses.get(0).activate)
    						if (!(boss.bosses.get(0).checkBossCollision(boss.bosses.get(0), "left", x, y, height, width)) && boss.bosses.get(0).activate)
        						b = false;
    					if(b)
    						x -= dx;
    		
    				
    				}
    				else*/
    					x -= dx;
    				
    			}
    			
    			feet.setBounds(x + 12, y + height - 16, x + width - 12, y + height + 4);
    	    	equipment.setPositions(x, y, isLeft);
    		}
    		
    		else if(right){
    			if((/**/(feet.y >= room.y && feet.height <= room.height) && ((offset == null || (offset != null && (!(((feet.y > offset.y && feet.y < offset.height) || (feet.height > offset.y && feet.height < offset.height)) && (feet.width <= offset.x))))) &&
    				(((bridgeR == null || (bridgeR != null && (feet.y < bridgeR.y || feet.height > bridgeR.height))) && feet.width+dx <= room.width) || (bridgeR != null && ((feet.y >= bridgeR.y && feet.y <= bridgeR.height) && (feet.height >= bridgeR.y && feet.height <= bridgeR.height)))) 
    				) || ( offset != null && (((feet.y > offset.y && feet.y < offset.height) || (feet.height > offset.y && feet.height < offset.height)) && (feet.width+dx <= offset.x))) /**/)
    				|| ((feet.y < room.y && feet.width+dx <= bridgeU.width) || (feet.height > room.height && feet.width+dx <= bridgeD.width))){
    				/* aggiungere qui ulteriori controlli con if separati */
    				
    				/*if(enemymanager.getEnemies() != null && !enemymanager.getEnemies().isEmpty() || boss.bosses.size() != 0 ){
    					boolean b = true;
    					if (enemymanager.getEnemies() != null && !enemymanager.getEnemies().isEmpty()){
    						for (int i = 0; i < enemymanager.getEnemies().size(); i++){
    	    					if (!(enemymanager.checkEnemyCollision(enemymanager.getEnemies().get(i), "right", x, y, height, width)))
    	    						b = false;
    	    				}
    					}
    					
    					if (boss != null && boss.bosses.size() != 0 && boss.bosses.get(0).activate)
    						if (!(boss.bosses.get(0).checkBossCollision(boss.bosses.get(0), "right", x, y, height, width)) && boss.bosses.get(0).activate)
        						b = false;
    					if(b)
    						x += dx;
    		
    				
    				}
    				else*/
    					x += dx;
    				
    			}
    			
    			feet.setBounds(x + 12, y + height - 16, x + width - 12, y + height + 4);
    	    	equipment.setPositions(x, y, isLeft);
    		}
    		
    		if(up){
    			if((/**/(feet.x >= room.x && feet.width <= room.width) && ((offset == null || (offset != null && (!(((feet.x > offset.x && feet.x < offset.width) || (feet.width > offset.x && feet.width < offset.width)) && (feet.y >= offset.height))))) &&
    				(((bridgeU == null || (bridgeU != null && (feet.x < bridgeU.x || feet.width > bridgeU.width))) && feet.y-dx >= room.y) || (bridgeU != null && ((feet.x >= bridgeU.x && feet.x <= bridgeU.width) && (feet.width >= bridgeU.x && feet.width <= bridgeU.width)))) 
    				) || ( offset != null && (((feet.x > offset.x && feet.x < offset.width) || (feet.width > offset.x && feet.width < offset.width)) && (feet.y-dx >= offset.height))) /**/)
    				|| ((feet.x < room.x && feet.y-dx >= bridgeL.y) || (feet.width > room.width && feet.y-dx >= bridgeR.y))){
    				/* aggiungere qui ulteriori controlli con if separati */
    				
    				/*if(enemymanager.getEnemies() != null && !enemymanager.getEnemies().isEmpty() || boss.bosses.size() != 0 ){
    					boolean b = true;
    					if (enemymanager.getEnemies() != null && !enemymanager.getEnemies().isEmpty()){
    						for (int i = 0; i < enemymanager.getEnemies().size(); i++){
    	    					if (!(enemymanager.checkEnemyCollision(enemymanager.getEnemies().get(i), "up", x, y, height, width)))
    	    						b = false;
    	    				}
    					}
    					
    					if (boss != null && boss.bosses.size() != 0 && boss.bosses.get(0).activate)
    						if (!(boss.bosses.get(0).checkBossCollision(boss.bosses.get(0), "up", x, y, height, width)) && boss.bosses.get(0).activate)
        						b = false;
    					if(b)
    						y -= dx;
    		
    				
    				}
    				else*/
    					y -= dx;
    				
    			}
    			
    			feet.setBounds(x + 12, y + height - 16, x + width - 12, y + height + 4);
    	    	equipment.setPositions(x, y, isLeft);
    		}
    		
    		else if(down){
    			
    			if((/**/(feet.x >= room.x && feet.width <= room.width) && ((offset == null || (offset != null && (!(((feet.x > offset.x && feet.x < offset.width) || (feet.width > offset.x && feet.width < offset.width)) && (feet.height <= offset.y))))) &&
    				(((bridgeD == null || (bridgeD != null && (feet.x < bridgeD.x || feet.width > bridgeD.width))) && feet.height+dx <= room.height) || (bridgeD != null && ((feet.x >= bridgeD.x && feet.x <= bridgeD.width) && (feet.width >= bridgeD.x && feet.width <= bridgeD.width)))) 
        			) || ( offset != null && (((feet.x > offset.x && feet.x < offset.width) || (feet.width > offset.x && feet.width < offset.width)) && (feet.height+dx <= offset.y))) /**/)
        			|| ((feet.x < room.x && feet.height+dx <= bridgeL.height) || (feet.width > room.width && feet.height+dx <= bridgeR.height))){
    				/* aggiungere qui ulteriori controlli con if separati */
    				
    				/*if(enemymanager.getEnemies() != null && !enemymanager.getEnemies().isEmpty() || boss.bosses.size() != 0 ){
    					boolean b = true;
    					if (enemymanager.getEnemies() != null && !enemymanager.getEnemies().isEmpty()){
    						for (int i = 0; i < enemymanager.getEnemies().size(); i++){
    	    					if (!(enemymanager.checkEnemyCollision(enemymanager.getEnemies().get(i), "down", x, y, height, width)))
    	    						b = false;
    	    				}
    					}
    					
    					if (boss != null && boss.bosses.size() != 0 && boss.bosses.get(0).activate)
    						if (!(boss.bosses.get(0).checkBossCollision(boss.bosses.get(0), "down", x, y, height, width)) && boss.bosses.get(0).activate)
        						b = false;
    					if(b)
    						y += dx;
    		
    				
    				}
    				else*/
    					y += dx;
    				
    			}
    			
    			feet.setBounds(x + 12, y + height - 16, x + width - 12, y + height + 4);
    	    	equipment.setPositions(x, y, isLeft);
    		}    		
    	}
    	
    }
    
    @Override
    public void drawAnimation(Graphics2D g2d){
    	equipment.syncTimers(getTimer());
    	
    	dustManager(g2d);
    	
    	equipment.drawCape(g2d);
    	super.drawAnimation(g2d);
    	equipment.drawLegs(g2d);
    	equipment.drawBody(g2d);
    	equipment.drawHead(g2d);
    	equipment.drawArms(g2d);
    	if(isLeft){
    		equipment.drawWeapon(g2d);
    		equipment.drawShield(g2d);
    	}
    	else{
    		equipment.drawShield(g2d);
        	equipment.drawWeapon(g2d);
    	}
    	
    	//g2d.drawRect(feet.x,feet.y, feet.width - feet.x - 1,feet.height-feet.y - 1);
    	
    	setSecondAttack();
    	setIdleAfterAction();
    	
    	Rectangle rectP, rectWP;
		rectWP = equipment.weapon.getBounds();
	
		/*if(isLeft)
			rectP = new Rectangle(rectWP.x, rectWP.y+((rectWP.height-rectWP.y)/3), rectWP.width-((rectWP.width-rectWP.x)/2), rectWP.height);
		else
			rectP = new Rectangle(rectWP.x+((rectWP.width-rectWP.x)/2), rectWP.y+((rectWP.height-rectWP.y)/3), rectWP.width, rectWP.height);
		
		g2d.drawRect(rectP.x, rectP.y, rectP.width-rectP.x, rectP.height-rectP.y);
		*/
    	
    	
    }
    
    public void setAnimations(int i){
    	setAnimation(i);
    	
    	if(isLeft){
    		if(i == 5 || i == 7)
    			equipment.cape.setAnimation(5);
    		else if(i == 1 || i == 9)
    			equipment.cape.setAnimation(1);
    		else
    			equipment.cape.setAnimation(i);
    	}
    	else{
    		if(i == 4 || i == 6)
    			equipment.cape.setAnimation(4);
    		else if(i == 0 || i == 8)
    			equipment.cape.setAnimation(0);
    		else
    			equipment.cape.setAnimation(i);
    	}
    	
    	equipment.legs.setAnimation(i);
    	equipment.body.setAnimation(i);
    	equipment.head.setAnimation(i);
    	equipment.arms.setAnimation(i);
    	equipment.shield.setAnimation(i);
    	equipment.weapon.setAnimation(i);
    	
    }
    
    public void update(){
    	staminaManager();
    }
    
    public void dustManager(Graphics2D g2d){

    	for(int i = 0; i < dust.size(); i++){
    		boolean del = false;
    		if(dust.get(i).isEnded())
    			del = true;
    		else
    			dust.get(i).drawAnimation(g2d);
    		
    		if(del)
    			dust.remove(i);
    		
    	}
    	
    	if((getCurrentAnimation() == 2 || getCurrentAnimation() == 3) && getCurrentFrame() == getCurrentStartFrame() + 3 && dust.isEmpty()){
    		if(isLeft)
    			dust.add(new AnimatedObject("src/dustR.png", feet.width - 24, feet.y - 20, 70, 5, false));
    		else
    			dust.add(new AnimatedObject("src/dustL.png", feet.x - 20, feet.y - 20, 70, 5, false));
    	}
    	
    	if((getCurrentAnimation() == 2 || getCurrentAnimation() == 3) && getCurrentFrame() == getCurrentStartFrame() + 7 && dust.isEmpty()){
    		if(isLeft)
    			dust.add(new AnimatedObject("src/dustR.png", feet.width - 24, feet.y - 16, 70, 5, false));
    		else
    			dust.add(new AnimatedObject("src/dustL.png", feet.x - 20, feet.y - 16, 70, 5, false));
    	}
    	
    }
    
    public boolean generateDamage(Rectangle rect){
    	
    		
    	
    	if(isAttacking && (getCurrentFrame() == 27 || getCurrentFrame() == 31 || getCurrentFrame() == 35 || getCurrentFrame() == 39 )){
    		Rectangle rectP, rectWP;
    		rectWP = equipment.weapon.getBounds();
    	
    		if(isLeft)
    			rectP = new Rectangle(rectWP.x, rectWP.y+((rectWP.height-rectWP.y)/3), rectWP.width-((rectWP.width-rectWP.x)/2), rectWP.height);
    		else
    			rectP = new Rectangle(rectWP.x+((rectWP.width-rectWP.x)/2), rectWP.y+((rectWP.height-rectWP.y)/3), rectWP.width, rectWP.height);
    		
    		//System.out.println(" Wboss: " +  (rect.width-rect.x) + " Wplayer: " + (rectP.width-rectP.x) );
    		
    		/* se il rettangolo del nemico è piu grande */
    		if(rect.width-rect.x > rectP.width-rectP.x && rect.height-rect.y > rectP.height-rectP.y)
    			return (/*blocco x*/ ( (rectP.x >= rect.x && rectP.x <= rect.width) || (rectP.width >= rect.x && rectP.width <= rect.width) ) /**/ && /*blocco y*/ ((rectP.y >= rect.y && rectP.y <= rect.height) || (rectP.height >= rect.y && rectP.height <= rect.height)) /**/);
    		
    		/* se il rettangolo del nemico è piu largo ma più basso */
    		else if(rect.width-rect.x > rectP.width-rectP.x && rect.height-rect.y < rectP.height-rectP.y)
    			return (/*blocco x*/ ( (rectP.x >= rect.x && rectP.x <= rect.width) || (rectP.width >= rect.x && rectP.width <= rect.width) ) /**/ && /*blocco y*/ ((rect.y >= rectP.y && rect.y <= rectP.height) || (rect.height >= rectP.y && rect.height <= rectP.height)) /**/);
    		
    		/* se il rettangolo del nemico è piu alto ma più stretto */
    		else if(rect.width-rect.x < rectP.width-rectP.x && rect.height-rect.y > rectP.height-rectP.y)
    			return (/*blocco x*/ ( (rect.x >= rectP.x && rect.x <= rectP.width) || (rect.width >= rectP.x && rect.width <= rectP.width) ) /**/ && /*blocco y*/ ((rect.y >= rectP.y && rect.y <= rectP.height) || (rect.height >= rectP.y && rect.height <= rectP.height)) /**/);	
    			
    		/* se il rettangolo del nemico è piu piccolo */
    		return (/*blocco x*/ ( (rect.x >= rectP.x && rect.x <= rectP.width) || (rect.width >= rectP.x && rect.width <= rectP.width) ) /**/ && /*blocco y*/ ((rectP.y >= rect.y && rectP.y <= rect.height) || (rectP.height >= rect.y && rectP.height <= rect.height)) /**/);
    		
    	}
    	else
    		return false;
	}
    
    public void getDamaged(int value){
    	if(!isShieldOn){
    		if(health >= value)
    			health-=value;
    		else
    			health = 0;
    	}
    	else{
    		if(stamina >= 1)
    			stamina-=1;
    	}
    		
    }
    public void staminaManager(){
    	if(System.currentTimeMillis() > timer + 50){
    		timer = System.currentTimeMillis();
    		
    		if(stamina < maxStamina)
    			stamina++;
    	}
    }
    
    /* verifica se è possibile eseguire la seconda animazione di attacco */
    public void setSecondAttack(){
    	if(getCurrentFrame() == 27 || getCurrentFrame() == 31)
    		secondAttack = true;
    }
    
    /* Questa funziona controlla se l'attacco è terminato 
     * (a prescindere dal fatto che se sia primario o secondario) */
 	public boolean isAttackEnded(){
 		return ((getCurrentAnimation() == 4 || getCurrentAnimation() == 5) || (getCurrentAnimation() == 6 || getCurrentAnimation() == 7)) && isEnded(); 
 				
 	}
    
    /* resetta l'animazione del personaggio una volta terminato l'attacco */
    public void setIdleAfterAction(){
    	if(isAttackEnded() || (!isShieldOn && (getCurrentAnimation() == 8 || getCurrentAnimation() == 9))){
    		isAttacking = false;
    		secondAttack = false;
    		
    		if(!up && !down && !left && !right || isShieldOn){
    			if(isLeft){
    				if(isShieldOn)
    					setAnimations(9);    	        		
    				else
    					setAnimations(1);
    			}
    			else{
    				if(isShieldOn)
    					setAnimations(8);
        			else
        				setAnimations(0);
    			}
    		}
    		else if(isLeft && !isShieldOn){
    			setAnimations(3);
    			isMoving = true;
    		}else if(!isLeft && !isShieldOn){
    			setAnimations(2);
    			isMoving = true;
    		}
    	}
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT && !left && !isAttacking && !isShieldOn) {
            left = true;
            right = false;
            
            isLeft = true;
            isMoving = true;
            
            setAnimations(3);
        }

        if (key == KeyEvent.VK_RIGHT && !right && !isAttacking && !isShieldOn) {
            right = true;
            left = false;
            
            isLeft = false;
            isMoving = true;
            
            setAnimations(2);
        }

        if (key == KeyEvent.VK_UP && !up && !isAttacking && !isShieldOn) {
            up = true;
            down = false;
            
            isMoving = true;
            
            if(isLeft)
            	setAnimations(3);
            else
            	setAnimations(2);
        }

        if (key == KeyEvent.VK_DOWN && !down && !isAttacking && !isShieldOn) {
            down = true;
            up = false;
            
            isMoving = true;
            
            if(isLeft)
            	setAnimations(3);
            else
            	setAnimations(2);
        }
        
        /* aggiunto il controllo per verificare se è presente la stamina necessaria 
         * a compiere l'azione richiesta*/
        if(key == KeyEvent.VK_X && (!isAttacking || (isAttacking && secondAttack)) && stamina > 10 && !attackPressed){
        	attackPressed = true;
        	isAttacking = true;
        	isMoving = false;
        	stamina -= 10;
        	
        	if(isLeft){
        		if(!secondAttack)
        			setAnimations(5);
        		else
        			setAnimations(7);
        	}
        	else{
        		if(!secondAttack)
        			setAnimations(4);
        		else
        			setAnimations(6);
        	}
        }
        
        if(key == KeyEvent.VK_Z && !isAttacking && !isShieldOn){
        	isShieldOn = true;
        	isMoving = false;
        	
        	if(isLeft)
        		setAnimations(9);
        	else
        		setAnimations(8);
        }
        	
        
    }

    public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            left = false;
        }

        if (key == KeyEvent.VK_RIGHT) {
            right = false;
        }

        if (key == KeyEvent.VK_UP) {
            up = false;
        }

        if (key == KeyEvent.VK_DOWN) {
            down = false;
        }
        
        if (key == KeyEvent.VK_X) {
            attackPressed = false;
        }
        	
        if (key == KeyEvent.VK_Z) {
            isShieldOn = false;
        }
        
        if(!up && !down && !left && !right && isMoving){
        	isMoving = false;
        	
        	if(isLeft){
        		setAnimations(1);
        		equipment.cape.setAnimation(1);
        	}
        	else{
        		setAnimations(0);
        		equipment.cape.setAnimation(0);
        	}
        }
        	
    }
}