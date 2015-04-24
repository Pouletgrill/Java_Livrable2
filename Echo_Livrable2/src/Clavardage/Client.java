package Clavardage;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class Client {
    static Panneau panneau;

    private static void creerEtAfficherIug() {
        JFrame frame = new JFrame("Client de clavardage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = frame.getContentPane();
        panneau = new Panneau();
        c.add(panneau);
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                creerEtAfficherIug();
            }
        });
    }

}