package examples.eass.ros.fumic.gui;

import javax.annotation.Resources;
import javax.swing.*;
import java.awt.*;

public abstract class WindowBase {
    JFrame parent;
    void setParent(JFrame parent)
    {
        this.parent = parent;
    }
    public void close()
    {
        parent.dispose();
    }
    abstract Container getBasePanel();
    public void newWindow()
    {
        JFrame frame = new JFrame("JFrame");
        this.setParent(frame);
        frame.setContentPane(this.getBasePanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
