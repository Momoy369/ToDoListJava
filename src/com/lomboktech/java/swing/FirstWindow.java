package com.lomboktech.java.swing;

import javax.swing.*;

public class FirstWindow {

    public static void main(String[] args){

        //Membuat objek jendela
        JFrame myWindow = new JFrame();

        //berikan judul pada jendela
        myWindow.setTitle("Jendela Program");

        //tentukan ukuran jendela
        myWindow.setSize(400, 200);

        //tampilkan jendela ke layar
        myWindow.setVisible(true);
    }
}
