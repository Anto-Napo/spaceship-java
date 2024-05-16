package com.anto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class PlayerPanel extends JPanel {
    Image player;

    int x = 325;
    int y = 600;

    boolean shoot = false;
    boolean keepShoot = false;
    final int bulletSize = 10;
    int shootX = 0;
    int shootY = 0;
    final int shootVelocity = 3;
    Action shootAction;

    Action upAction;
    Action downAction;
    Action leftAction;
    Action rightAction;
    int playerVelocity = 5;

    GamePanel gamePanel;

    PlayerPanel(GamePanel gamePanel) {
        this.setOpaque(false);
        this.gamePanel = gamePanel;

        player = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/player.png"))).getImage();

        shootAction = new ShootAction();
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

        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "shootAction");
        this.getActionMap().put("shootAction", shootAction);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        g2D.drawImage(player, x, y, null);

        // Bullet
        g2D.setPaint(Color.white);
        if (shoot) {
            shoot = false;
            shootX = (x + player.getWidth(null) / 2) - bulletSize / 2;
            shootY = y - bulletSize;
            g2D.fillOval(shootX, shootY, bulletSize, bulletSize);
            keepShoot = true;
        }
        if (keepShoot) {
            shootY -= shootVelocity;
            if (shootY > 0) {
                g2D.fillOval(shootX, shootY - bulletSize, bulletSize, bulletSize);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    repaint();
                }
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
            gamePanel.updatePoints(100);
        }
    }

    public class UpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (y > gamePanel.LINE_HEIGHT) {
                y -= playerVelocity;
                repaint();
            }
        }
    }

    public class DownAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (y < gamePanel.HEIGHT - player.getHeight(null)) {
                y += playerVelocity;
                repaint();
            }
        }
    }

    public class LeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (x > 0) {
                x -= playerVelocity;
                repaint();
            }
        }
    }

    public class RightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (x < gamePanel.WIDTH - player.getWidth(null)) {
                x += playerVelocity;
                repaint();
            }
        }
    }
}
