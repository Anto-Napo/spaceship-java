package com.anto;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public final int WIDTH = 700;
    public final int HEIGHT = 700;
    final int LINE_HEIGHT = 550;

    JLabel text;
    int points = 0;

    PlayerPanel playerPanel;

    GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);

        playerPanel = new PlayerPanel(this);

        // Text
        text = new JLabel("Points: 000");
        text.setForeground(Color.white);
        text.setFont(new Font("Arial", Font.PLAIN, 25));


        text.setBounds((WIDTH - text.getPreferredSize().width) / 2, 10, text.getPreferredSize().width, text.getPreferredSize().height);
        this.add(text);
        playerPanel.setBounds(0, 0, WIDTH, HEIGHT);
        this.add(playerPanel);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2D = (Graphics2D) g;

        // Delimitation
        g2D.setPaint(Color.white);
        g2D.setStroke(new BasicStroke(5));
        g2D.drawLine(0, LINE_HEIGHT, this.getWidth(), 550);
    }

    public void updatePoints(int amount) {
        points += amount;
        text.setText("Points: " + points);
        text.setBounds((WIDTH - text.getPreferredSize().width) / 2, 10, text.getPreferredSize().width, text.getPreferredSize().height);
    }
}