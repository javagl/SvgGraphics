/*
 * www.javagl.de - SvgGraphics - A Graphics implementation that creates SVG
 *
 * Copyright (c) 2017-2017 Marco Hutter - http://www.javagl.de
 */
package de.javagl.svggraphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.batik.svggen.SVGGraphics2D;

/**
 * A basic test for the {@link SvgGraphics} and {@link SvgGraphicsWriter} 
 * classes.
 */
@SuppressWarnings("javadoc")
public class SvgGraphicsTest
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> createAndShowGui());
    }
    
    private static void createAndShowGui()
    {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel() 
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics gr)
            {
                super.paintComponent(gr);
                Graphics2D g = (Graphics2D)gr;
                paintStuffInto(g);
            }
        };
        f.getContentPane().add(panel, BorderLayout.CENTER);
        
        JPanel p = new JPanel(new FlowLayout());

        JButton saveDirectlyButton = new JButton("Save directly...");
        saveDirectlyButton.addActionListener(e -> 
        {
            // Directly call the painting operations on the SVGGraphics2D 
            SVGGraphics2D svgGraphics = SvgGraphics.create();
            paintStuffInto(svgGraphics);
            SvgGraphicsWriter.save(svgGraphics);
        });
        p.add(saveDirectlyButton);
        
        JButton saveComponentButton = new JButton("Save component...");
        saveComponentButton.addActionListener(e -> 
        {
            // Call "printAll" on a component, passing in the SVGGraphics2D,
            // to create an SVG image of the component
            SVGGraphics2D g = SvgGraphics.create();
            panel.printAll(g);
            SvgGraphicsWriter.save(g);
        });
        p.add(saveComponentButton);
        
        f.getContentPane().add(p, BorderLayout.SOUTH);
        
        f.setSize(800, 800);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
    private static void paintStuffInto(Graphics2D g)
    {
        int w = 100;
        int h = 100;

        BufferedImage image = createTestImage();
        Rectangle2D rectangle = new Rectangle2D.Double(0.0, 0.0, w, h);

        double scales[] = { 0.8, 1.0, 1.2 };
        double rotationsDeg[] = { -45, 0.0, 45 };
        for (int i = 0; i < 3; i++)
        {
            AffineTransform oldAt = g.getTransform();

            g.translate(100 + i * 200, 100);
            g.rotate(Math.toRadians(rotationsDeg[i]), w * 0.5, h * 0.5);
            g.scale(scales[i], scales[i]);
            g.drawImage(image, 0, 0, null);

            g.setTransform(oldAt);

            g.translate(100 + i * 200, 300);
            g.rotate(Math.toRadians(rotationsDeg[i]), w * 0.5, h * 0.5);
            g.scale(scales[i], scales[i]);
            g.drawImage(image, 0, 0, null);

            paintWithLinearGradient(g, rectangle);

            g.setTransform(oldAt);

            g.translate(100 + i * 200, 500);
            g.rotate(Math.toRadians(rotationsDeg[i]), w * 0.5, h * 0.5);
            g.scale(scales[i], scales[i]);
            g.drawImage(image, 0, 0, null);

            paintWithRadialGradient(g, rectangle);

            g.setTransform(oldAt);
        }
    }

    private static BufferedImage createTestImage()
    {
        int w = 100;
        int h = 100;
        BufferedImage image =
            new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, w, h);
        Random random = new Random(0);
        for (int i = 0; i < 100; i++)
        {
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            int red = random.nextInt(256);
            int green = random.nextInt(256);
            int blue = random.nextInt(256);
            g.setColor(new Color(red, green, blue));
            g.fillRect(x, y, 20, 20);
        }
        g.dispose();
        return image;
    }

    private static void paintWithRadialGradient(Graphics2D g,
        Rectangle2D rectangle)
    {
        float[] fractions = { 0.0f, 0.25f, 1.0f };
        Color[] colors = { Color.RED, Color.GREEN, Color.BLUE };
        Point2D center = new Point2D.Double(
            rectangle.getCenterX(), rectangle.getCenterY());
        float radius = (float)(rectangle.getWidth() * 0.5f);
        Paint paint = new RadialGradientPaint(
            center, radius, fractions, colors);
        g.setPaint(paint);
        g.fill(rectangle);
    }

    private static void paintWithLinearGradient(Graphics2D g,
        Rectangle2D rectangle)
    {
        float[] fractions = { 0.0f, 0.25f, 1.0f };
        Color[] colors = { Color.RED, Color.GREEN, Color.BLUE };

        Point2D start = new Point2D.Double(
            rectangle.getMinX(), rectangle.getMinY());
        Point2D end = new Point2D.Double(
            rectangle.getMaxX(), rectangle.getMaxY());
        Paint paint = new LinearGradientPaint(start, end, fractions, colors);
        g.setPaint(paint);
        g.fill(rectangle);
    }    
    
}
