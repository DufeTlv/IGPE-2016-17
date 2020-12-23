package com.Artoriasoft;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class mainclass extends JFrame{
	
	static Dimension screenSize;

    public mainclass() {
        
        initUI();
    }
    
    private void initUI() {
        
        //setSize(800, 600);
        //setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        add(new Board());
        setTitle("Ash");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }    

    public static void main(String[] args) {
    	
        EventQueue.invokeLater(new Runnable() {
            public void run() {
            	mainclass ex = new mainclass();
                ex.setVisible(true);
            }
        });
    }
}