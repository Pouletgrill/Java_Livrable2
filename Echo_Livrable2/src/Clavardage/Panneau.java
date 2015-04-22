package Clavardage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Panneau extends JPanel {



    PrintWriter writer = null;
    Thread RetourMessage = null;
    Socket client =null;
    BufferedReader reader = null;

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
        final JTextField fieldTexte = new JTextField(40);

        JButton boutonEnvoyer  = new JButton("Envoyer");
        boutonEnvoyer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writer.println(fieldTexte.getText());
                writer.flush();
                fieldTexte.setText("");
            }
        });

        JPanel pan2 = new JPanel();
        pan2.add(labelTexte);
        pan2.add(fieldTexte);
        pan2.add(boutonEnvoyer);
        add(pan2);

        // rangée 3
        JButton boutonConnexion  = new JButton("Connecter/Déconnecter");
        boutonConnexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(client == null)
                {
                    try
                    {
                        InetSocketAddress Ipsocket = new InetSocketAddress(fieldAdresse.getText(),50000);
                        client = new Socket();
                        client.connect(Ipsocket);

                        writer = new PrintWriter(
                                new OutputStreamWriter(
                                        client.getOutputStream()));
                        reader = new BufferedReader(
                                new InputStreamReader(
                                        client.getInputStream()));

                        ThreadRetourMessage RetourMess = new ThreadRetourMessage(reader,zoneMessages);
                        RetourMessage = new Thread(RetourMess);
                        RetourMessage.setDaemon(true);
                        RetourMessage.start();

                        writer.println("L'utilisateur " + fieldPseudo.getText()+" vient de ce connecter");
                        writer.flush();

                    }
                    catch(IOException ex)
                    {
                        System.out.println(ex);
                    }

                }else
                {
                    try
                    {
                        writer.close();
                        reader.close();
                        client.close();
                        zoneMessages.append("Vous êtes Maintenant déconnecter");
                    }catch(IOException ex)
                    {

                    }
                }
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

}