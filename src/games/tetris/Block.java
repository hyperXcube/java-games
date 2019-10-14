package games.tetris;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

class Block {
    private Rectangle[] area = new Rectangle[4];
    private Color color;
    private final AffineTransform transform = new AffineTransform();
    private Game tetris;

    Block(Game parent) {
        tetris = parent;

        area[0] = new Rectangle(Constants.TILE, Constants.TILE);
        area[1] = new Rectangle(Constants.TILE, Constants.TILE);
        area[2] = new Rectangle(Constants.TILE, Constants.TILE);
        area[3] = new Rectangle(Constants.TILE, Constants.TILE);

        int blockType = (int) (Math.random() * 7);
        switch (blockType) {
            case 0 -> {
                // I block
                color = new Color(135, 231, 235);
                area[0].setLocation(80, 0);
                area[1].setLocation(120, 0);
                area[2].setLocation(160, 0);
                area[3].setLocation(200, 0);
            }
            case 1 -> {
                // J block
                color = new Color(0, 0, 55);
                area[0].setLocation(80, 0);
                area[1].setLocation(80, Constants.TILE);
                area[2].setLocation(120, Constants.TILE);
                area[3].setLocation(160, Constants.TILE);
            }
            case 2 -> {
                // L block
                color = new Color(255, 163, 51);
                area[0].setLocation(80, Constants.TILE);
                area[1].setLocation(120, Constants.TILE);
                area[2].setLocation(160, Constants.TILE);
                area[3].setLocation(160, 0);
            }
            case 3 -> {
                // O block
                color = new Color(253, 255, 0);
                area[0].setLocation(80, 0);
                area[1].setLocation(120, 0);
                area[2].setLocation(80, Constants.TILE);
                area[3].setLocation(120, Constants.TILE);
            }
            case 4 -> {
                // S block
                color = new Color(64, 255, 0);
                area[0].setLocation(80, Constants.TILE);
                area[1].setLocation(120, Constants.TILE);
                area[2].setLocation(120, 0);
                area[3].setLocation(160, 0);
            }
            case 5 -> {
                // T block
                color = new Color(128, 0, 128);
                area[0] = new Rectangle(80, 80, Constants.TILE, Constants.TILE);
                area[1] = new Rectangle(120, 80, Constants.TILE, Constants.TILE);
                area[2] = new Rectangle(160, 80, Constants.TILE, Constants.TILE);
                area[3] = new Rectangle(120, 120, Constants.TILE, Constants.TILE);
            }
            case 6 -> {
                // Z block
                color = new Color(220, 20, 60);
                area[0].setLocation(80, 0);
                area[1].setLocation(120, 0);
                area[2].setLocation(120, Constants.TILE);
                area[3].setLocation(160, Constants.TILE);
            }
            default -> throw new IllegalArgumentException("Unexpected random value: " + blockType);
        }
    }

    // Moves block one tile down
    synchronized void move() {
        // Checks to see if block can be moved one tile down
        for (int i = 0; i < 4; i++) {
            Rectangle rect = area[i];
            Rectangle newRect = new Rectangle(rect.x, rect.y + Constants.TILE, rect.width, rect.height);

            // If moved block will go out of the screen
            if (!new Rectangle(Constants.WIDTH, Constants.HEIGHT).contains(newRect)) {
                deactivate();
                return;
            }
            // If moved block has reached the bottom
            for (Rectangle r : tetris.bottomTiles.keySet()) {
                if (r.contains(newRect)) {
                    deactivate();
                    return;
                }
            }
        }
        // If the method doesn't return, the block is moved one tile down
        for (int i = 0; i < 4; i++) area[i].y += Constants.TILE;
    }

    // Moves area of block to bottomTiles and creates a new active block
    private void deactivate() {
        for (Rectangle r : area) tetris.bottomTiles.put(r, color);
        tetris.newActiveBlock();
    }

    void rotate() {
        // Add handling of rotation
    }

    void paint(Graphics2D g2d) {
        for (Rectangle r : area) {
            g2d.setColor(color);
            g2d.fill(r);
            g2d.setColor(Color.WHITE);
            g2d.draw(r);
        }
    }
}