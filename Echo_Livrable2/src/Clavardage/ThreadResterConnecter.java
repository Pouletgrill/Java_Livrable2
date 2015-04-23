package Clavardage;

import javax.swing.*;
import java.io.*;

/**
 * Created by 201356187 on 2015-04-23.
 */
public class ThreadResterConnecter implements Runnable {

   PrintWriter Writer = null;

   public ThreadResterConnecter(PrintWriter writer) {

      Writer = writer;
   }

   public synchronized void run()
   {
      boolean Flag = false;

      while(!Thread.interrupted() && !Flag)
      {
         try {

            Writer.println(" ");
            Writer.flush();
            Thread.sleep(5000);
         }
         catch (InterruptedException e)
         {
            Flag =true;
         }
      }

   }

}
