/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstyle;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author butwhole
 */

public class TurnThread extends Thread {

    private Socket s;
    private Counter c;
    private Random ran = new Random();
    private int localCount = 0;
    private PrintWriter pw;
    private Scanner scan;

    public TurnThread(Socket s, Counter c) {
        this.s = s;
        this.c = c;
    }

    private void Still() {

        pw.println("");
        pw.println("TurnStill nr.: " + c.getNoStill());
        pw.println("Waiting for people to come");
        while (true) {
            try {
                Thread.sleep(1000 + ran.nextInt(2000));
                localCount++;
                c.incr();
                pw.println("was turned (" + localCount + ")");
            } catch (InterruptedException ex) {
                Logger.getLogger(TurnThread.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void Monitor() {
        pw.println("");
        pw.println("Monitor");

        while (true) {
            try {
                Thread.sleep(2000);
                pw.println("total count: " + c.getValue());
            } catch (InterruptedException ex) {
                Logger.getLogger(TurnThread.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void ManualStill() {

        pw.println("");
        pw.println("TurnStill nr.: " + c.getNoStill());
        pw.println("Waiting for people to come (write \"turn\" to add a count)");
        while (true) {
    
                String line = scan.nextLine();
                if(line.equals("turn")){
                localCount++;
                c.incr();
                pw.println("was turned (" + localCount + ")");
                }
                
            

        }

    }
    
    public void run() {
        try {
            
            pw = new PrintWriter(s.getOutputStream(), true);
            scan = new Scanner(s.getInputStream());
            pw.println("");
            pw.println("Are you a manual turnstill, auto turnstill or a monitor: (1/3)");
            while (true) {
                String line = scan.nextLine();
                if (line.equals("1")) {
                    c.incrNoStill();
                    ManualStill();
                } else if (line.equals("2")) {
                    c.incrNoStill();
                    Still();
                }else if (line.equals("3")) {
                    Monitor();
                }
                else {
                    pw.println("Please type either 1, 2 or 3");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(TurnThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
