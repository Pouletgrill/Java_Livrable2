package  Clavardage;
//Connexion
//----------------
//Xavier Brosseau
//Charlie Laplante

import java.io.*;
import java.net.*;
import java.lang.Object;

public class Connexion implements Runnable
{
   //String de Message
   String E = null;
   //Instanciation des writers et readers
   PrintWriter writer = null;
   BufferedReader reader = null;
   //Cr�ation de string pour ip et pour Nom
   String Nom;
   String Ip;
   //Cr�ation d'une variable ServeurEcho pour chaque utilisateur
   ServeurEcho serveurEcho_;
   
   
   public Connexion(Socket client,  ServeurEcho serveurEcho) 
   {     
      try
      {
         //Cr�ation des writers et readers
         writer = new PrintWriter(
                     new OutputStreamWriter(
                     client.getOutputStream()));

         reader = new BufferedReader(
              new InputStreamReader(
                      client.getInputStream()));

         //Trouve et met ip addresse dans la string ip           
         Ip = client.getInetAddress().getHostAddress();
	     //Met les infos de notre serveur dans la variable serveur du client
         serveurEcho_ = serveurEcho;
      }
      catch(SocketTimeoutException ez)
      {
         System.out.println("Trop longtemps innactif");         
      }
      catch (IOException ioe)
      {
         System.err.println(ioe);
         System.exit(1);  
      }           
   }
   //Ecrit un message dans la connexion.
   public void Ecrire(String Message)
      {
        writer.println(Message);
        writer.flush();
      }
	
   public void run()
   {
     //Envoie le message de connection d'un client au serveur
     System.out.println("Client connecte");
     
     boolean vide = false;
      try
      {
         //Assure que le nom soit au maximum 8 charact�res
         if(Nom.length() > 8)
         {
            Nom  =  Nom.substring(0,8);        
         }    
         //Change le nom pour l'ip adresse si l'utilisateur ne rentre rien
         if(Nom.length() < 1)
         {
              Nom = Ip;         
         } 
	//Envoie le message de connexion a tout les usagers     
         serveurEcho_.Distribuer(Nom + " vient de se joindre a la conversation"); 
         do 
         {         
           //lit le message envoy� par le client et le met dans la variable E
            E = reader.readLine();
            //S'assure que le message ne d�passe pas 80
            if(E.length() > 80)
            {
               E = E.substring(0,80);
            }
	    //Si le message et vide met la variable qui met fin � la connexion � true
            if(E.length() == 0)
            {
               vide = true;               
            }
	    //Envoie le message au autre usager via le serveur echo
            else
            {
                E = Nom + ": " + E;
            
                serveurEcho_.Distribuer(E);                
            }            
         }while (!vide);         
      }
      catch (NullPointerException nue)
      {       
      }
      catch (IOException ez)
      {         
      }    
      finally
      {          
         try
         {
	    //Envoie le message de d�connexion � tout le monde
            serveurEcho_.Distribuer(Nom + "a quitte le chat room");
	    //Ferme le writer et le reader
            writer.close();
            reader.close();
	    //Enl�ve la connexion de la listeArray
            serveurEcho_.TuerConnexion(this);
            System.out.println("Client deconnecte"); 
         }
         catch(IOException ez)
         {
         }
            
      }
   }   
}
