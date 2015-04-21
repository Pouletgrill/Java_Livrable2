//Terminateur
//----------------
//Xavier Brosseau
//Charlie Laplante

import java.io.*;
//Thread
public class Terminateur implements Runnable
{
   public void run()   
   {
      try
      {      
         BufferedReader reader = new BufferedReader(
                                    new InputStreamReader( System.in));
         String E="";
         while(!E.trim().equals("Q") && !E.trim().equals("q")) //Si un Q (ignore les espaces)
         {
            E = reader.readLine();
         }  
         reader.close();
      }
      catch(IOException ey)
      {
        System.err.println(ey);
        System.exit(1);  
      }
   }
}