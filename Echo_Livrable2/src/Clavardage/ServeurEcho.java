//ServeurEcho
//----------------
//Xavier Brosseau
//Charlie Laplante
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.ListIterator;

public class ServeurEcho
{ 
   //Création de l'array liste
   ArrayList<Connexion> ListeConnexion = new ArrayList<>();
   final int MAXCONNECT = 5;
   
   public void Serveur()
   {
   //Instanciation du serveur socket
      ServerSocket clientServer;
      
      try
      {
	 
         clientServer = new ServerSocket(50000);
        
         Terminateur QStop = new Terminateur();
         Thread threadLiseur = new Thread(QStop);
         threadLiseur.setDaemon(true);
         threadLiseur.start(); 
	
	 //Boucle de serveur. Est en fonction tant que "q" n'est pas entrer.
         while (threadLiseur.isAlive())
         {
            try
            {
	       //Empêche la connection de plus de 5 usager.
               if (ListeConnexion.size() < MAXCONNECT)
               {  
		  //Timeout sur le serveur                
                  clientServer.setSoTimeout(500);                  
                  Socket client = clientServer.accept();
                  //Timeout sur le client
                  client.setSoTimeout(90000);
		  //Creation de la connexion                  
                  Connexion connexion = new Connexion(client,this);
                  Thread t = new Thread(connexion);
                  t.setDaemon(true);
                  t.start();
		  //Ajouter la connexion a notre array list
                  ListeConnexion.add(connexion);
               }
            }
            catch (IOException ey)
            {            
                        
            }                   
         }        
      }
      catch(IOException ez)
      {
        System.err.println(ez);        
        ez.printStackTrace();        
        System.exit(1);  
      }
   }
   //Fonction qui distribue les messages a tout les usagers.
   public synchronized void Distribuer(String Message)
   {
   	for(int i= 0 ;  i < ListeConnexion.size(); i ++)
      {
         ListeConnexion.get(i).Ecrire(Message);
      }
   }
   //Enlève la connexion de la array list
   public void TuerConnexion(Connexion conn)   
   {
      ListeConnexion.remove(conn);
   }
   public static void main ( String args[]) throws IOException
   { 
      new ServeurEcho().Serveur();
   }  
}