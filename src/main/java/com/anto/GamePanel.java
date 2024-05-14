package com.anto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;
public class GamePanel extends JPanel {
    Image player;
    Image enemy;

    Action upAction;
    Action downAction;
    Action leftAction;
    Action rightAction;
    int x = 325;
    int y = 600;
    int playerVelocity = 5;

    final int WIDTH = 700;
    final int HEIGHT = 700;
    final int LINE_HEIGHT = 550;

    JLabel text;
    int points = 0;

    boolean shoot = false;
    boolean keepShoot = false;
    final int bulletSize = 10;
    int shootX = 0;
    int shootY = 0;
    final int shootVelocity = 3;
    Action shootAction;

    GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);

        player = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/player.png"))).getImage();
        enemy = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/enemy.png"))).getImage();

        // Text
        text = new JLabel("Points: 000");
        text.setForeground(Color.white);
        text.setFont(new Font("Arial", Font.PLAIN, 25));

        // Key binding
        upAction = new UpAction();
        downAction = new DownAction();
        leftAction = new LeftAction();
        rightAction = new RightAction();

        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "upAction");
        this.getActionMap().put("upAction", upAction);

        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "downAction");
        this.getActionMap().put("downAction", downAction);

        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
        this.getActionMap().put("leftAction", leftAction);

        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
        this.getActionMap().put("rightAction", rightAction);

        shootAction = new ShootAction();

        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "shootAction");
        this.getActionMap().put("shootAction", shootAction);

        this.add(text);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2D = (Graphics2D) g;

        // Delimitation
        g2D.setPaint(Color.white);
        g2D.setStroke(new BasicStroke(5));
        g2D.drawLine(0, 550, this.getWidth(), 550);

        // Player
        g2D.drawImage(player, x, y, null);

        // Enemy
        g2D.drawImage(enemy, 0, 0, null);

        // Bullet
        if(shoot) {
            shoot = false;
            shootX = (x + player.getWidth(null) / 2) - bulletSize / 2;
            shootY = y - bulletSize;
            g2D.fillOval(shootX, shootY, bulletSize, bulletSize);
            keepShoot = true;
        }
        if(keepShoot) {
            shootY -= shootVelocity;
            if(shootY > 0) {
                g2D.fillOval(shootX, shootY - bulletSize, bulletSize, bulletSize);
                try {
                    Thread.sleep(1); //? Less hazardous
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                repaint();
            } else {
                keepShoot = false;
            }
        }
    }

    public class ShootAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            shoot = true;
            repaint();
            updatePoints(100);
        }
    }

    public class UpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(y > LINE_HEIGHT) {
                y -= playerVelocity;
                repaint();
            }
        }
    }

    public class DownAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(y <= HEIGHT - player.getHeight(null)) {
                y += playerVelocity;
                repaint();
            }
        }
    }

    public class LeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(x >= 0) {
                x -= playerVelocity;
                repaint();
            }
        }
    }

    public class RightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(x <= WIDTH - player.getWidth(null)) {
                x += playerVelocity;
                repaint();
            }
        }
    }

    public void updatePoints(int amount) {
        points += amount;
        text.setText("Points: " + String.valueOf(points));
    }
}