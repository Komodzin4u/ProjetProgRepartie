package Serveur;

import raytracer.Scene;
import raytracer.Image;
import java.rmi.RemoteException;

import ServiceCalcul.ServiceRayTracer;

import java.rmi.Remote;

public interface ServiceDistributeur extends Remote {

    /**
     * Permet au client de s'enregistrer sur le service central
     * 
     * @param r
     * @throws RemoteException
     */
    public void enregistrerClient(ServiceRayTracer r) throws RemoteException;

    /**
     * Méthode qui appelle
     * 
     * @param x0
     * @param y0
     * @param largeur
     * @param hauteur
     * @param scene
     * @throws RemoteException
     * @throws java.rmi.ConnectException
     */
    public void computeImage(int x0, int y0, int largeur, int hauteur, Scene scene)
            throws RemoteException, java.rmi.ConnectException;

    public void recupererImage(Image image, int x0, int y0) throws RemoteException;
}
