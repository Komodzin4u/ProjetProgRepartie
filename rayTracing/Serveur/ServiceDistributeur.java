package Serveur;

import raytracer.Scene;
import raytracer.Image;
import java.rmi.RemoteException;

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
     * Méthode qui permet d'enregistrer le client
     */
    public void genererImage(Scene s, int largeur, int hauteur) throws RemoteException;
}
