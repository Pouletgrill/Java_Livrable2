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
    Thread ResterConnecter = null;
    Socket client =null;
    BufferedReader reader = null;

    public Panneau() {
        setLayout(new GridLayout(0, 1)); // une seule colonne

        // rangée 0
        JLabel labelAdresse = new JLabel("Adresse IP");
        final JTextField fieldAdresse = new JTextField(16);
        JLabel labelPseudo = new JLabel("Pseudo");
        final JTextField fieldPseudo = new JTextField(16);
        final JCheckBox cboxResterConnecte = new JCheckBox("Rester connecté");


        JPanel pan0 = new JPanel();
        add(pan0);
        pan0.add(labelAdresse);
        pan0.add(fieldAdresse);
        pan0.add(labelPseudo);
        pan0.add(fieldPseudo);
        pan0.add(cboxResterConnecte);
        cboxResterConnecte.setEnabled(false);


        // rangée 1
        final JTextArea zoneMessages = new JTextArea(8,40);
        JScrollPane zoneDefilement = new JScrollPane(zoneMessages);
        JPanel pan1 = new JPanel();
        add(pan0);
        pan1.add(zoneDefilement);
        add(pan1);

        // rangée 2
        JLabel labelTexte = new JLabel("Votre texte");
        final JTextField fieldTexte = new JTextField(40);
        fieldTexte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    if(fieldTexte.getText().length()!=0)
					{
                        writer.println(fieldTexte.getText());
                        writer.flush();
                        fieldTexte.setText("");
                    }
                }catch(Exception ey)
                {
                    zoneMessages.append("Vous n'êtes pas connecter! \n");
                    client=null;
                    reader=null;
                    writer=null;
                    RetourMessage.interrupt();
                    cboxResterConnecte.setEnabled(false);


                }
            }
        });

        JButton boutonEnvoyer  = new JButton("Envoyer");
        boutonEnvoyer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    if(fieldTexte.getText().length()!=0)
                    {
                        writer.println(fieldTexte.getText());
                        writer.flush();
                        fieldTexte.setText("");
                    }
                }catch(Exception ey)
                {
                    zoneMessages.append("Vous n'êtes pas encore connecter! \n");
                    client=null;
                    reader=null;
                    writer=null;
                    RetourMessage.interrupt();
                    cboxResterConnecte.setEnabled(false);
                }

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
                try {
                    if ( client == null || client.isClosed() )
                    {
                        zoneMessages.setText("");

                        InetSocketAddress Ipsocket = new InetSocketAddress(fieldAdresse.getText(), 50000);
                        client = new Socket();
                        client.connect(Ipsocket);

                        writer = new PrintWriter(
                                new OutputStreamWriter(
                                        client.getOutputStream()));
                        reader = new BufferedReader(
                                new InputStreamReader(
                                        client.getInputStream()));

                        writer.println(fieldPseudo.getText());
                        writer.flush();

                        ThreadRetourMessage RetourMess = new ThreadRetourMessage(reader, zoneMessages,writer,client,cboxResterConnecte);
                        RetourMessage = new Thread(RetourMess);
                        RetourMessage.setDaemon(true);
                        RetourMessage.start();
                        cboxResterConnecte.setEnabled(true);
                    }
                    else
                    {

                        writer.println("");
                        writer.flush();
                        RetourMessage.interrupt();
                        client = null;
                        reader = null;
                        writer = null;
                        cboxResterConnecte.setEnabled(false);
                    }
                } catch (IOException ex)
                {
                    zoneMessages.append("Invalide Ip adresse \n");
                    client = null;
					reader = null;
                    writer = null;
                }
            }
        });

        JButton boutonQuitter = new JButton("Quitter");
        boutonQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try
                {
                    System.exit(0);
                    RetourMessage.interrupt();
                    writer.close();
                    reader.close();
                    client.close();

                } catch (IOException ex) {

                }

            }
        });

        JPanel pan3 = new JPanel();
        pan3.add(boutonConnexion);
        pan3.add(boutonQuitter);
        add(pan3);

        cboxResterConnecte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                       if (cboxResterConnecte.isSelected())
                       {

                           ThreadResterConnecter ResterConn = new ThreadResterConnecter(writer);
                           ResterConnecter = new Thread(ResterConn);
                           ResterConnecter.setDaemon(true);
                           ResterConnecter.start();
                       }
                       else
                       {
                           ResterConnecter.interrupt();
                       }

            }
        });
    }

}