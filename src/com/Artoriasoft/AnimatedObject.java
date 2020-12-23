package com.Artoriasoft;

import java.awt.Graphics2D;
import java.util.ArrayList;


public class AnimatedObject extends Object{
	
	private ArrayList<Animation> animations = null;
	private int currentFrame, currentAnimation, numFrames;
	private long frameTimer;
	private boolean ended, animate;
	
	/* costruttore per oggetti con più di un animazione */
	public AnimatedObject(String filePath, int x, int y, int nf){
		super(x, y);
		this.loadImage(filePath);
		numFrames = nf;
		width /= nf;
		currentAnimation = currentFrame = 0;
		ended = false;
		animate = true;
		animations = new ArrayList<>();
	}
	
	public long getTimer(){
		return frameTimer;
	}
	
	public void setTimer(long t){
		frameTimer = t;
	}
	
	/* costruttore per oggetti a singola animazione */
	public AnimatedObject(String filePath, int x, int y, int d, int nf, boolean l){
		super(x, y);
		this.loadImage(filePath);
		numFrames = nf;
		width /= nf;
		currentAnimation = currentFrame = 0;
		ended = false;
		animate = true;
		
		animations = new ArrayList<>();
		animations.add(new Animation("Default", 0, nf-1, d, l));
	}
	
	public void setAnimate(boolean b){
		if(animate != b)
			animate = b;
	}
	
	public int getCurrentStartFrame(){
		return animations.get(currentAnimation).getStartFrame();
    }
    
    public int getCurrentEndFrame(){
    	return animations.get(currentAnimation).getEndFrame();
    }
	
	public void setCurrentFrame(int i){
		currentFrame = i;
	}
	
	public void setFrameTimer(int i){
		frameTimer = i;
	}
	
	public void addAnimation(String n, int s, int e, int d, boolean l){
		animations.add(new Animation(n, s, e, d, l));
	}
	
	public void setAnimation(int i){
        if(i >= 0 && i < animations.size() && i != currentAnimation) {
            frameTimer = 0;
            currentAnimation = i;
            currentFrame = animations.get(i).startFrame;
            ended = false;
        }
    }
	
	public void setDelay(int a,int d){
		animations.get(a).setDelay(d);
	}
	
	public int getDelay(){
		return animations.get(currentAnimation).getDelay();
	}
	
	public void drawAnimation(Graphics2D g2d){
		if(animations.isEmpty() || animations == null)
			return;
		if(animate)
			animate();
		
		int sx = currentFrame * width;
		int sw = sx + width;
		
    	g2d.drawImage(image, x, y, x+width, y+height, sx, 0, sw , height, null);
    }
	
	public void animate(){
		if(frameTimer == 0)
			frameTimer = System.currentTimeMillis();
		
		if(System.currentTimeMillis() > frameTimer + animations.get(currentAnimation).delay) {
            frameTimer = System.currentTimeMillis();
            
            	
            if (currentFrame == animations.get(currentAnimation).endFrame){
            	
            	/* se l'animazione NON è a singola esecuzione, il currentFrame viene resettato a 0 
            	   e viene richiamato il return per evitare che il frame venga aggiornato 
            	   immediatamente a 1*/
            	
            	if(animations.get(currentAnimation).loop){
            		currentFrame = animations.get(currentAnimation).startFrame;
                	return;
            	}
            	else
            		if(!ended)
            			ended = true;
            }
            
            if(currentFrame < animations.get(currentAnimation).endFrame)
            	currentFrame++;
            
		}
	}
	
	public int getCurrentFrame(){
		return currentFrame;
	}
	
	public int getCurrentAnimation(){
		return currentAnimation;
	}
	
	public boolean isEnded(){
		return ended;
	}
}
