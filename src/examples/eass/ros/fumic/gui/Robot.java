package examples.eass.ros.fumic.gui;

import ail.util.AILSocketServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Ellipse2D;

public class Robot extends JPanel {

    public static final String ANSI_RESET = "\u001B[0m";

    // Declaring the color
    // Custom declaration
    public static final String ANSI_YELLOW = "\u001B[33m";

    Ellipse2D circle;
    private Color col;

    int x;
    int y;

    double radiation_value = 0.0;
    int movebase_result = 3;
    int movebase_id=0;
    boolean controlled;

    private int started = 0;
    protected ail.util.AILSocketServer socketServer;

    @Override
    protected void paintComponent(Graphics gh) {
        super.paintComponent(gh);
        doDrawing(gh);
    }

    public void redo_circle() {
        int size = get_size();
//        System.out.println("Robot size "+size);

        circle = new Ellipse2D.Float(getWidth() / 4, getHeight() / 4, size, size);


    }

    private void colorPrint(String s)
    {
        System.out.println(ANSI_YELLOW
                + s
                + ANSI_RESET);
    }
    public Robot(Color col) {
        initialiseRobot(col, true);
    }

    public Robot(Color col, boolean controlled) {
        initialiseRobot(col, controlled);
    }

    public void initialiseRobot(Color col, boolean controlled) {

        this.controlled = controlled;
        this.col = col;
        x = 0;
        y = 0;
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
//                System.out.println("Resized");
                super.componentResized(e);

            }
        });
        setBackground(Color.WHITE);
        start_server();
    }

    private void start_server() {
        if (socketServer == null) {
            if (controlled) {
                colorPrint("Robot waiting for socket connection");
                socketServer = new AILSocketServer();
                colorPrint("Robot got socket connection");
            }

        }
    }

    public void set_controlled() {

        controlled = true;
        start_server();
        updateParameters();
    }

    public void move_left(int xbound, int ybound) {

        x++;

        if (x >= xbound) {
            x = 0;
            y++;
        }
        if (y >= ybound) {
            y = 0;
        }


    }

    private void readValues() {
        if (controlled) {
            try {
                colorPrint("reading stuff");
                x = socketServer.readInt();
                y = socketServer.readInt();
                String toprint = String.format("read x: %d, y: %d", x, y);
                colorPrint(toprint);
            } catch (Exception e) {
                System.err.println("READ ERROR: Closing socket");
                close();
            }
        } else {
            colorPrint("Robot not controlled");
        }
    }

    public void close() {
        if (controlled) {
            socketServer.close();
        }
    }

    public void updateParameters() {
        if (controlled) {
            if (socketServer.allok()) {
                try {
                    if (socketServer.pendingInput()) {
                        readValues();
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                writeValues();
            } else {
                System.err.println("something wrong with socket server");
            }
        }
    }

    //write values
    public void writeValues() {
        if (controlled) {
            try {
//                colorPrint("Robot Writing stuff ");
                String toprint = String.format("Robot writing: movebase_result(%d,%d), radiation:%f, started:%d",movebase_id,movebase_result,radiation_value,started);
                colorPrint(toprint);
                //writing movebase result
                socketServer.writeInt(movebase_id);
                socketServer.writeInt(movebase_result);
//                socketServer.write("movebase_result(0,3)"); //TODO change this?
                // writing radiation
//                socketServer.write("radiation=0");
                socketServer.writeDouble(radiation_value);
                socketServer.writeInt(started);

                colorPrint("Robot done writing stuff");
            } catch (Exception e) {
                System.err.println("WRITE ERROR: Closing socket");
                close();
            }
        }
    }

    public void start() {
        started = 1;
    }

    private int get_size() {
//        System.out.println("Robot w "+getWidth()+" h "+getHeight());
        int minval = Math.min(getWidth(), getHeight());
        minval = minval / 2;
        return minval;

    }

    private void doDrawing(Graphics g) {
        redo_circle();
        Graphics2D g2d = (Graphics2D) g;
        Paint current_color = g2d.getPaint();
        g2d.setPaint(col);
        g2d.fill(circle);
        g2d.setPaint(current_color);
        g2d.dispose();
    }
}

