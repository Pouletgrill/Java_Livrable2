package Clavardage;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

/**
 * Created by 201356187 on 2015-04-22.
 */
public class ThreadRetourMessage implements Runnable {

    BufferedReader ReaderOfServer = null;
    JTextArea ChatField = null;
    PrintWriter Writer = null;
    Socket Client = null;
    JCheckBox Cb = null;

    public ThreadRetourMessage(BufferedReader readerOfServer, JTextArea chatField , PrintWriter writer , Socket client, JCheckBox cb) {
        ReaderOfServer = readerOfServer;
        ChatField = chatField;
        Writer = writer;
        Client =client;
        Cb=cb;
    }

    public synchronized  void run()
    {
        try
        {
            String E="";
            while(!Thread.interrupted() &&(E= ReaderOfServer.readLine())!=null)
            {
                ChatField.append(E + "\n");
            }
            Writer.close();
            Client.close();
            ReaderOfServer.close();
            Cb.setEnabled(false);
        }
        catch (IOException err)
        {

        }
    }
}
