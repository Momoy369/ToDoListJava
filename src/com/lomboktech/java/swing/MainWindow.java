package com.lomboktech.java.swing;

import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow(){
        this.setTitle("Jendela Utama");
        this.setSize(600, 320);
    }

    public static void main(String[] args){
        MainWindow mWindow = new MainWindow();
        mWindow.setVisible(true);
    }
}
