package Client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Serveur.ServiceDistributeur;

public class LancerServiceClient {

    public static void main(String[] args) throws Exception {

        // Ex : utilisation :
        // java LancerServiceClient localhost 1099 512 512 simple.txt
        
        try{
            // On récupère d'abord le service central
            Registry reg = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));
            try{
                ServiceDistributeur rdServiceCentral = (ServiceDistributeur) reg.lookup("DistributeurFragments");
                
                int largeur=Integer.parseInt(args[2]);
                int hauteur=Integer.parseInt(args[3]);

                String fichier = "simple.txt";
                if(args.length==5){
                    fichier = args[4];
                }
                try{
                    ServiceClientImage serviceClient = new ServiceClientImage(fichier, largeur, hauteur);

                    // Puis on exporte le client pour qu'il utilise la méthode du service central
                    // le service central contactera les services de calculs à son tour, et renverra
                    // au client les fragments d'image selon l'ordre dans lequel ils seront retournés
                    ServiceClient rdServiceClient = (ServiceClient) UnicastRemoteObject.exportObject(serviceClient, 0);

                    System.out.println("Moi, le client, veux générer une image à partir de ce fichier : "+ fichier);
                    rdServiceCentral.genererImage(rdServiceClient, largeur, hauteur);
                    // et à partir de cet appel, le service central va commencer à utiliser afficherFragments du client
                } catch (RemoteException e) {
                    System.out.println("Impossible d'enregistrer le client");
                }
            } catch (NotBoundException e) {
                System.out.println("Impossible de trouver le service distributeurNoeuds dans l'annuaire");
            }
        } catch (RemoteException e) {
            System.out.println("Problème de connexion à l'annuaire");
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Veuillez renseignez l'adresse ip de l'hôte du service et éventuellement le port");
        }
    }
}
