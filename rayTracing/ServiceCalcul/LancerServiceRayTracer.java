package ServiceCalcul;
import java.rmi.NotBoundException;
import java.rmi.RemoteException; 
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Serveur.ServiceDistributeur;

import java.rmi.registry.LocateRegistry;
public class LancerServiceRayTracer {
    

    public static void main(String[] args) {
        int port =0;
        if( args.length>1){
            port = Integer.parseInt(args[1]);
        }
        try {
            Registry reg = LocateRegistry.getRegistry(args[0],port);
            try {
                ServiceDistributeur distributeurNoeuds=(ServiceDistributeur)reg.lookup("DistributeurFragments");
                try {
                    ServiceScene sc = new ServiceScene();
                    ServiceRayTracer rc =(ServiceRayTracer)UnicastRemoteObject.exportObject(sc, 0);
                    distributeurNoeuds.enregistrerEsclave(rc);
                    System.out.println("Le client s'est bien enregistré");
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