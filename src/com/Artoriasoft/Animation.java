package com.Artoriasoft;

public class Animation {
	String name;	//principalmente per debug
    int startFrame, endFrame;
    int delay;
    boolean loop;

    public Animation(String n, int s, int e, int d, boolean l){
        name = n;
        startFrame = s;
        endFrame = e;
        delay = d;
        loop = l;
    }
    
    public int getStartFrame(){
    	return startFrame;
    }
    
    public int getEndFrame(){
    	return endFrame;
    }
    
    public void setDelay(int d){
    	delay = d;
    }
    
    public int getDelay(){
    	return delay;
    }
    
    public void setLoop(Boolean l){
    	loop = l;
    }
}
