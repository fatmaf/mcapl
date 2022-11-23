package eass.ros.fumic.agent;

import ail.syntax.Literal;
import eass.mas.socket.EASSSocketClientEnvironment;
import ail.mas.scheduling.NActionScheduler;
import ail.util.AILSocketClient;
import ajpf.util.AJPFLogger;
import ail.syntax.Unifier;
import ail.syntax.NumberTerm;
import ail.syntax.NumberTermImpl;
import ail.syntax.Predicate;


import java.util.Random;
import java.util.Scanner;

public class GridworldEnvironment extends EASSSocketClientEnvironment {

    String logname = "eass.ros.fumic.agent.GridworldEnvironment";

    double radiation=0;
    boolean received_started = false;
    String current_at_loc = null;
    public GridworldEnvironment() {
        super();
        super.scheduler_setup(this, new NActionScheduler(100));
    }

    @Override
    public void readPredicatesfromSocket() {

        if (AJPFLogger.ltFine(logname)) {
            AJPFLogger.fine(logname, "Reading Values from Socket");
        }

        try {
//            System.out.println("Reading stuff");
            AILSocketClient socket = getSocket();
            int mb_id = -1;
            int mb_res = -1;
            double rad_val = -1;
            int started = -1;
            int robot_x = -1;
            int robot_y = -1;
            boolean readvalues = false;
            if (socket.pendingInput()) {
                robot_x = socket.readInt();
                robot_y = socket.readInt();
                mb_id = socket.readInt();
                mb_res= socket.readInt();
                rad_val = socket.readDouble();
                started = socket.readInt();
                readvalues = true;
            }

            while (socket.pendingInput()) {
                robot_x = socket.readInt();
                robot_y = socket.readInt();
                mb_id = socket.readInt();
                mb_res= socket.readInt();
                rad_val = socket.readDouble();
                started = socket.readInt();
                readvalues = true;
            }
            if (readvalues) {
                String toprint = String.format("read: movebase_result(%d,%d), radiation: %f, started: %d, xy:%d,%d", mb_id,mb_res, rad_val, started,robot_x,robot_y);
                radiation=rad_val;
                System.out.println(toprint);
                if (started > 0) {
                    if (!received_started) {
                        addPercept(new Literal("started"));
                        received_started = true;
                    }
                }
                Literal mb = new Literal("movebase_result");
                mb.addTerm(new NumberTermImpl(mb_id));
                mb.addTerm(new NumberTermImpl(mb_res));
                if(mb_res != -1) {
                    addUniquePercept("movebase_result", mb);
                }
                String at_loc_string = String.format("l%d_%d",robot_x,robot_y);
                Literal at_loc = new Literal("at");
                at_loc.addTerm(new Literal(at_loc_string));
                // only add this new percept if it did not exist before
                if(current_at_loc==null || !current_at_loc.equals(at_loc_string)) {
                    current_at_loc = at_loc_string;
                    addUniquePercept("at", at_loc);
                }

            }

        } catch (Exception e) {
            AJPFLogger.warning(logname, e.getMessage());
        }


    }

    public Unifier executeAction(String agName, ail.syntax.Action act) throws ail.util.AILexception {
        Unifier u = new ail.syntax.Unifier();
        String actionname = act.getFunctor();
        int nterms = act.getTermsSize();
        if ((actionname.equals("move")) && nterms == 6) {
            NumberTerm lx = (NumberTerm) act.getTerm(0);
            NumberTerm ly = (NumberTerm) act.getTerm(1);
            NumberTerm lz = (NumberTerm) act.getTerm(2);
            NumberTerm ax = (NumberTerm) act.getTerm(3);
            NumberTerm ay = (NumberTerm) act.getTerm(4);
            NumberTerm az = (NumberTerm) act.getTerm(5);
            move(lx.solve(),ly.solve(),lz.solve(),ax.solve(),ay.solve(),az.solve());
        } else if ((actionname.equals("move")) && nterms == 3) {
            NumberTerm lx = (NumberTerm) act.getTerm(0);
            NumberTerm ly = (NumberTerm) act.getTerm(1);
            NumberTerm lz = (NumberTerm) act.getTerm(2);
            move(lx.solve(),ly.solve(),lz.solve());
        } else if (actionname.equals("keep_moving")) {
            NumberTerm period = (NumberTerm) act.getTerm(0);
            NumberTerm lx = (NumberTerm) act.getTerm(1);
            NumberTerm ly = (NumberTerm) act.getTerm(2);
            NumberTerm lz = (NumberTerm) act.getTerm(3);
            NumberTerm ax = (NumberTerm) act.getTerm(4);
            NumberTerm ay = (NumberTerm) act.getTerm(5);
            NumberTerm az = (NumberTerm) act.getTerm(6);
            keep_moving((int) period.solve(),lx.solve(),ly.solve(),lz.solve(),ax.solve(),ay.solve(),az.solve());
        } else if (actionname.equals("stop_moving")) {
            stop_moving();
        } else if (actionname.equals("inspect")) {
            try {
                Thread.sleep(3000);
                receive_inspect();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (actionname.equals("wait")) {
            NumberTerm period = (NumberTerm) act.getTerm(0);
            try {
                Thread.sleep((int) period.solve());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (actionname.equals("key_press")) {
            Scanner myObj = new Scanner(System.in);
            System.out.println(":::");
            myObj.nextLine();  // Read user input
        } else if (actionname.equals("get_random_coordinates")) {
            Random random = new Random();
            double x = random.nextDouble()*20 - 10;
            double y = random.nextDouble()*20 - 10;
            while (inaccessible(x, y)) {
                x = random.nextDouble()*20 - 10;
                y = random.nextDouble()*20 - 10;
            }
            NumberTerm lx = new NumberTermImpl(x);
            NumberTerm ly = new NumberTermImpl(y);
            act.getTerm(0).unifies(lx, u);
            act.getTerm(1).unifies(ly, u);
        }


        u.compose(super.executeAction(agName, act));
        return u;

    }

    private boolean inaccessible(double x, double y) {
        if (x > 0.6 && x < 4.2 && y > -7.3 && y < -3) {
            return true;
        } else if (x > -8 && x < -5 && y > -9 && y < -6.5 ) {
            return true;
        } else if (x > 7.5 && x < 8.6 && y < -4 && y > -7.2) {
            return true;
        } else if (x > -1.1 && x < 2.1 && y > 3 && y < 4.2 ) {
            return true;
        } else if (x > 8.7) {
            return true;
        } else if (y > 9) {
            return true;
        } else if (x < -9) {
            return true;
        } else if (y < -9) {
            return true;
        } else if (x > 2.9 && x < 3.4 && y > 2.8 && y < 6.4) {
            return true;
        } else if (x > -5.8 && x < -4.9 && y < -6.9 && y > -8.15) {
            return true;
        } else if (x > 4.6 && x < 5.1 && y > -3.8 && y < -3.4) {
            return true;
        } else if (x > -6.9 && x < -6.15 && y > -4.8 && y < -1.5) {
            return true;
        } else if (x > -2.6 && x < -1.9 && y < 0.8 && y < 6.6) {
            return true;
        }

        return false;
    }

    public void move(double lx, double ly, double lz, double ax, double ay, double az) {


        AILSocketClient socket = getSocket();
        socket.writeInt((int)lx);
        socket.writeInt((int)ly);
    }

    public void move(double lx, double ly, double lz) {


        AILSocketClient socket = getSocket();
        socket.writeInt((int)lx);
        socket.writeInt((int)ly);
    }

    public void keep_moving(int period, double lx, double ly, double lz, double ax, double ay, double az) {


        AILSocketClient socket = getSocket();
        socket.writeInt((int)lx);
        socket.writeInt((int)ly);
    }

    public void stop_moving() {

//
//        AILSocketClient socket = getSocket();
//        socket.writeInt((int)lx);
//        socket.writeInt((int)ly);
    }

    public void receive_inspect() {
        String status = null;
        System.out.println("Radiation: " + radiation);
        if (radiation >= 250 ) {
            status = "red";
            Literal rad = new Literal("danger_red");
            addUniquePercept("danger_red",rad);
        } else if (radiation >= 120) {
            status = "orange";
            Literal rad = new Literal("danger_orange");
            addUniquePercept("danger_orange",rad);
        } else {
            status = "green";
        }
//        publish("radiationStatus",new PrimitiveMsg<String>(status));
    }
    @Override
    public boolean done() {
        return false;
    }


}
