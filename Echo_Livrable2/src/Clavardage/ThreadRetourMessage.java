package Clavardage;
import javax.swing.*;
import java.io.*;

/**
 * Created by 201356187 on 2015-04-22.
 */
public class ThreadRetourMessage implements Runnable {

    BufferedReader ReaderOfServer = null;
    JTextArea ChatField = null;

    public ThreadRetourMessage(BufferedReader readerOfServer, JTextArea chatField) {
        ReaderOfServer = readerOfServer;
        ChatField = chatField;
    }

    public synchronized  void run()
    {
        try
        {
            String E="";
            while(ReaderOfServer.readLine()!=null)
            {
                E = ReaderOfServer.readLine();
                ChatField.append(E);
            }
        }
        catch (IOException err)
        {

        }
    }
}
