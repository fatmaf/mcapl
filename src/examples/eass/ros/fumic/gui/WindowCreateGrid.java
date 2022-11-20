package examples.eass.ros.fumic.gui;

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
    private GridCell[][] cells;
    private Robot robot;
    int rx = 0;
    int ry=0;
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

    private void move_robot()
    {
        System.out.println("Moving robot");
        cells[rx][ry].remove(robot);
        rx++;

        if(rx >= numX)
        {
            rx =0;
            ry++;
        }
        if(ry >= numY)
        {
            ry= 0;
        }
        cells[rx][ry].add(robot);

    }

    public class TestListener implements MouseListener {

         @Override
        public void mouseClicked(MouseEvent e) {
            // System.out.println("Clicked");
            if (e.getSource() instanceof GridCell) {
                if (currentCellType != CellType.NONE) {
                    if (currentCellType == CellType.ELEMENT) {
                        if(currentElementType == GridCell.GridCellElementType.DOOR)
                        {
                            try {
//                                doDoor((GridCell)e.getSource());
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        ((GridCell) e.getSource()).updateType(currentElementType);
                    }
                    else
                        ((GridCell) e.getSource()).updateType(currentStateType);
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //   System.out.println("Pressed");

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //   System.out.println("Released");
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //    System.out.println("Entered");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //     System.out.println("Exited");
        }

    }


    void createMenu() {
        JMenu m1 = new JMenu("Hello");

        menuBar.add(m1);
        JMenuItem mitem = new JMenuItem("World");
        m1.add(mitem);
    }

    public WindowCreateGrid(int numX, int numY) {
        this.numX = numX;
        this.numY = numY;
        int gsize = 10;
//        this.doorsList = new ArrayList<>();
        //create a grid of this size
        cells = new GridCell[numX][numY];
        gridPanel.setLayout(new GridLayout(numX+1, numY+1));
        createMenu();
        for (int row = 0; row < numX; row++) {
            //add the beginning add a row number
            gridPanel.add(new GridCellLabel(""+row));
            for (int col = 0; col < numY; col++) {
                cells[row][col] = new GridCell(row, col);
                cells[row][col].addMouseListener(new TestListener());
                gridPanel.add(cells[row][col]);
            }

        }
        //when done add column numbers
        gridPanel.add(new GridCellLabel(""));
        for (int col = 0; col < numY; col++) {
            gridPanel.add(new GridCellLabel(""+col));
        }

        gridPanel.setFocusable(true);
        gridPanel.requestFocusInWindow();
        robot = new Robot(Color.PINK);

        cells[0][0].add(robot);


        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move_robot();
            }
        });

    }

    @Override
    Container getBasePanel() {
        return basePanel;
    }


    public static void main(String[] args) {
        WindowBase wb = new WindowCreateGrid(10,10);
        wb.newWindow();
    }
}
