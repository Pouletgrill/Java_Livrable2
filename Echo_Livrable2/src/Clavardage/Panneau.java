package Clavardage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Panneau extends JPanel {

   static PrintWriter writer =null;
   static BufferedReader reader = null;

    BufferedReader clavier = new BufferedReader(
            new InputStreamReader(System.in));

    boolean fini = false;
    String Texte = null;


    public Panneau() {
        setLayout(new GridLayout(0, 1)); // une seule colonne

        // rangée 0
        JLabel labelAdresse = new JLabel("Adresse IP");
        final JTextField fieldAdresse = new JTextField(16);
        JLabel labelPseudo = new JLabel("Pseudo");
        final JTextField fieldPseudo = new JTextField(16);
        JCheckBox cboxResterConnecte = new JCheckBox("Rester connecté");
        JPanel pan0 = new JPanel();
        add(pan0);
        pan0.add(labelAdresse);
        pan0.add(fieldAdresse);
        pan0.add(labelPseudo);
        pan0.add(fieldPseudo);
        pan0.add(cboxResterConnecte);

        // rangée 1
        final JTextArea zoneMessages = new JTextArea(20,40);
        JScrollPane zoneDefilement = new JScrollPane(zoneMessages);
        JPanel pan1 = new JPanel();
        add(pan0);
        pan1.add(zoneDefilement);
        add(pan1);

        // rangée 2
        JLabel labelTexte = new JLabel("Votre texte");
        JTextField fieldTexte = new JTextField(40);

        JButton boutonEnvoyer  = new JButton("Envoyer");
        boutonEnvoyer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Envoyer();
            }
        });

        JPanel pan2 = new JPanel();
        pan2.add(labelTexte);
        pan2.add(fieldTexte);
        pan2.add(boutonEnvoyer);
        add(pan2);

        // rangée 3
        JButton boutonConnexion  = new JButton("Connecter");
        boutonConnexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                LancerClient(fieldAdresse.getText(), 50000);
                zoneMessages.append("Vous êtes connecter");

            }
        });

        JButton boutonQuitter = new JButton("Quitter");
        boutonQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel pan3 = new JPanel();
        pan3.add(boutonConnexion);
        pan3.add(boutonQuitter);
        add(pan3);
    }

    public void Envoyer()
    {
        try
        {
         System.out.println(reader.readLine());

         while (!fini) {
             Texte = clavier.readLine();

             if (Texte != null) {
                 writer.println(Texte);
                 writer.flush();

                 if (Texte.trim().equalsIgnoreCase("Q")) ;
                 {
                     fini = true;
                 }
                 System.out.println(reader.readLine());

             } else {
                 fini = true;
             }

             writer.close();
             reader.close();
             clavier.close();
         }
         }catch(IOException exc)
         {

         }
    }


    public static void LancerClient(String Ip, int port)
    {

        InetSocketAddress IpSocket = null;
        Socket socket = null;

        try
        {
           IpSocket = new InetSocketAddress(Ip,port);
                socket = new Socket();

                socket.connect(IpSocket);

                writer = new PrintWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream()));

                reader = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));


        }catch(IOException ex)
        {
            System.err.println(ex);
            System.exit(1);
        }
    }

}