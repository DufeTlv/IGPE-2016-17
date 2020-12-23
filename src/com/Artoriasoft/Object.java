package com.Artoriasoft;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Object {
	protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean vis;
    protected Image image;

    public Object(int x, int y) {

        this.x = x;
        this.y = y;
        vis = true;
        image = null;
        
    }

    protected void getImageDimensions() {

        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    protected void loadImage(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
        
        getImageDimensions();
    }
    
    public void setVis(Boolean b){
    	vis = b;
    }

    public Image getImage() {
        return image;
    }
    
    public void draw(Graphics2D g){
    	if(vis)
    		g.drawImage(image, x, y, null);
    }
    
    public void setX(int x){
    	this.x = x;
    }
    
    public void setY(int y){
    	this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return vis;
    }

    public void setVisible(Boolean visible) {
        vis = visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, x+width, y+height);
    }
}
