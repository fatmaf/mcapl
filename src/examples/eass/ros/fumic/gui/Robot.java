package examples.eass.ros.fumic.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Ellipse2D;

public class Robot extends JPanel {


    Ellipse2D circle;
    private Color col;


    @Override
    protected void paintComponent(Graphics gh)
    {
        super.paintComponent(gh);
        doDrawing(gh);
    }
    public void redo_circle()
    {
        int size = get_size();
//        System.out.println("Robot size "+size);

        circle = new Ellipse2D.Float(getWidth()/4,getHeight()/4,size,size);


    }
    public Robot( Color col)
    {

        this.col = col;


        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
//                System.out.println("Resized");
                super.componentResized(e);

            }
        });
        setBackground(Color.WHITE);
    }
    private int get_size()
    {
//        System.out.println("Robot w "+getWidth()+" h "+getHeight());
        int minval = Math.min(getWidth(),getHeight());
        minval = minval/2;
        return minval;

    }
    private  void doDrawing(Graphics g)
    {
        redo_circle();
        Graphics2D g2d = (Graphics2D) g;
        Paint current_color = g2d.getPaint();
        g2d.setPaint(col);
        g2d.fill(circle);
        g2d.setPaint(current_color);
        g2d.dispose();
    }
}

