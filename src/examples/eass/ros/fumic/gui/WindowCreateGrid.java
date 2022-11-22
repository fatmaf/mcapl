package eass.ros.fumic.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WindowCreateGrid extends WindowBase {
    private JPanel basePanel;

    private JPanel gridPanel;

    private JMenuBar menuBar;
    private JPanel buttonsPanel;
    private JButton startButton;
    private JButton moveButton;
    private JButton restartButton;
    private GridCell[][] cells;
    private Robot robot;
    int numX;
    int numY;

//    private ArrayList<DoorCell> doorsList;

    private GridCell.GridCellElementType currentElementType = GridCell.GridCellElementType.EMPTY;
    private GridCell.GridCellStateType currentStateType = GridCell.GridCellStateType.NONE;

    public enum CellType {
        NONE, ELEMENT, STATE
    }

    ;
    CellType currentCellType = CellType.NONE;

    private void move_robot_socket() {
        // first we need to remove the robot
        remove_robot_from_cell();
        //update parameters
        robot.updateParameters();
        // add robot to cell
        add_robot_to_cell();

    }

    private void add_robot_to_cell() {
        int rx = robot.x;
        int ry = robot.y;
        cells[ry][rx].add(robot);
        gridPanel.repaint();
        gridPanel.revalidate();
    }

    private void remove_robot_from_cell() {
        int rx = robot.x;
        int ry = robot.y;

//        String toprint = String.format("[%d,%d]",ry,rx);
        cells[ry][rx].remove(robot);
        gridPanel.repaint();
        gridPanel.revalidate();
    }

    private void move_robot_left() {
        robot.move_left(numX, numY);
    }

    private void move_robot_button() {

        // first we need to remove the robot
        remove_robot_from_cell();

        move_robot_left();
        // add robot to cell
        add_robot_to_cell();

    }


    void createMenu() {
        JMenu m1 = new JMenu("About");

        menuBar.add(m1);
        JMenuItem mitem = new JMenuItem("Gridworld robot simulator");
        m1.add(mitem);
    }

    private void initialise_robot()
    {
        if(robot!=null)
        {
            //remove robot
            this.remove_robot_from_cell();
        }
        robot = new Robot(Color.PINK, false);
        cells[0][0].add(robot);
        robot.x = 0;
        robot.y = 0;
    }

    private void reinitialise_robot()
    {
        if(robot!=null)
        {
            robot.close();
           robot = null;
        }
        initialise_robot();


    }
    public WindowCreateGrid(int numX, int numY) {
        this.numX = numX;
        this.numY = numY;
        int gsize = 10;
//        this.doorsList = new ArrayList<>();
        //create a grid of this size
        cells = new GridCell[numX][numY];
        gridPanel.setLayout(new GridLayout(numX + 1, numY + 1));
        createMenu();
        for (int row = 0; row < numX; row++) {
            //add the beginning add a row number
            gridPanel.add(new GridCellLabel("" + row));
            for (int col = 0; col < numY; col++) {
                cells[row][col] = new GridCell(row, col);
                gridPanel.add(cells[row][col]);
            }

        }
        //when done add column numbers
        gridPanel.add(new GridCellLabel(""));
        for (int col = 0; col < numY; col++) {
            gridPanel.add(new GridCellLabel("" + col));
        }

        gridPanel.setFocusable(true);
        gridPanel.requestFocusInWindow();

        initialise_robot();


        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move_robot_button();
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                robot.start();
                robot.set_controlled();

//                robot.updateParameters();
            }
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reinitialise_robot();
            }
        });



        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move_robot_socket();
            }
        });
        timer.start();

    }

    @Override
    Container getBasePanel() {
        return basePanel;
    }


    public static void main(String[] args) {
        WindowBase wb = new WindowCreateGrid(10, 10);
        wb.newWindow();
    }
}
