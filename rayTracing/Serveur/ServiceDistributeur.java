package Serveur;

import java.rmi.RemoteException;

import Client.ServiceClient;
import ServiceCalcul.ServiceRayTracer;

import java.rmi.Remote;

public interface ServiceDistributeur extends Remote {

    /**
     * Permet aux esclaves de s'enregistrer sur le service central
     * 
     * @param r
     * @throws RemoteException
     */
    public void enregistrerEsclave(ServiceRayTracer r) throws RemoteException;

    /**
     * Méthode qui permet de construire l'image pour le client
     */
    public void genererImage(ServiceClient client, int largeur, int hauteur) throws RemoteException;
}
