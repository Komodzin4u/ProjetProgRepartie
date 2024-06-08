package Client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LancerServiceClient {

    public static void main(String[] args) throws Exception {

        // Ex : utilisation :
        // java LancerServiceClient localhost 1099 512 512 simple.txt

        // On récupère d'abord le service central
        Registry reg = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));
        ServiceDistributeur rdServiceCentral = (ServiceDistributeur) reg.lookup("DistributeurFragments");

        int largeur = Integer.parseInt(args[2]);
        int hauteur = Integer.parseInt(args[3]);
        String fichier = "simple.txt";

        if(args[4] != null){
            fichier = args[4];
        }


        ServiceClientImage serviceClient = new ServiceClientImage(fichier, largeur, hauteur);

        // Puis on exporte le client pour qu'il utilise la méthode du service central
        // le service central contactera les services de calculs à son tour, et renverra
        // au client les fragments d'image selon l'ordre dans lequel ils seront retournés
        ServiceClient rdServiceClient = (ServiceClient) UnicastRemoteObject.exportObject(serviceClient, 0);

        rdServiceCentral.genererImage(rdServiceClient, largeur, hauteur);
        // et à partir de cet appel, le service central va commencer à utiliser afficherFragments du client


    }
}
