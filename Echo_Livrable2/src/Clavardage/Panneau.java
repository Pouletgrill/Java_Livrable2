package Clavardage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by 201356187 on 2015-04-20.
 */
public class Panneau extends JPanel {
    private JTextField TB_IP;
    private JTextField TB_Pseudo;
    private JCheckBox resterConnecterCheckBox;
    private JTextArea TA_Chat;
    private JTextField TB_Message;
    private JButton envoyerButton;
    private JButton connecterButton;
    private JButton quitterButton;
    private JPanel RootPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clavadage");
        frame.setContentPane(new Panneau().RootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Panneau(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public Panneau(LayoutManager layout) {
        super(layout);
    }

    public Panneau(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public Panneau()
     {

         connecterButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 Connecter();
             }
         });
     }

    private void Connecter()
    {

    }

}
