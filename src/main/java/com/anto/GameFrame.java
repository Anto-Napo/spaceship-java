package com.anto;

import javax.swing.*;

public class GameFrame extends JFrame {
    GamePanel gamePanel;
    GameFrame() {
        gamePanel = new GamePanel();

        this.setTitle("Spaceship");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.add(gamePanel);
        this.pack();
        this.setVisible(true);
    }
}
