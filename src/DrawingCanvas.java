/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrawingCanvas extends JPanel {
    private Color currentColor = Color.BLACK;
    private boolean isDrawing = false;
    private Point pointEnd = null;
    private List<List<Point>> pointsPath = new ArrayList<>();
    private List<Color> pathColors = new ArrayList<>();
    private boolean isPenSelected = false;
    private boolean isEraserSelected = false;
    private final int strokeWidth = 3;
   

    public DrawingCanvas() {
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isPenSelected || isEraserSelected) {
                    isDrawing = true;
                    pointEnd = e.getPoint();
                    pointsPath.add(new ArrayList<>());
                    pathColors.add(isEraserSelected ? getBackground() : currentColor);
                    pointsPath.get(pointsPath.size() - 1).add(pointEnd);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDrawing = false;
                pointEnd = null;
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDrawing && (isPenSelected || isEraserSelected)) {
                    Point currentPoint = e.getPoint();
                    if (!pointsPath.isEmpty() && pointEnd != null) {
                        pointsPath.get(pointsPath.size() - 1).add(currentPoint);
                        repaint();
                    }
                    pointEnd = currentPoint;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int i = 0; i < pointsPath.size(); i++) {
            g2d.setColor(pathColors.get(i));
            List<Point> path = pointsPath.get(i);
            for (int j = 0; j < path.size() - 1; j++) {
                Point p1 = path.get(j);
                Point p2 = path.get(j + 1);
                if (p1 != null && p2 != null) {
                    g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }
    }
    public void clearDrawing() {
        pointsPath.clear();
        pathColors.clear();
        repaint(); 
    }

  

    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    public void setPenSelected(boolean selected) {
        this.isPenSelected = selected;
    }

    public void setEraserSelected(boolean selected) {
        this.isEraserSelected = selected;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public boolean isPenSelected() {
        return isPenSelected;
    }

    public boolean isEraserSelected() {
        return isEraserSelected;
    }
     public BufferedImage getCanvasImage() {
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        this.paint(g2d);
        g2d.dispose();
        return image;
    }

    public void saveDrawing() {
        BufferedImage image = getCanvasImage();
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".png")) {
                filePath += ".png";
                selectedFile = new File(filePath);
            }
            try {
                ImageIO.write(image, "png", selectedFile);
                System.out.println("Drawing saved to: " + selectedFile.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error saving image: " + e.getMessage());
            }
        }
    }
}
