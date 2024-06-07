package Serveur;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import raytracer.Image;
import raytracer.Scene;

public class ServiceDistributionRayTracer implements ServiceDistributeur {
    private ArrayList<ServiceRayTracer> serviceCalcul;
     
    /**
     * Permet aux esclaves de s'enregistrer sur le service central
     * 
     * @param r
     * @throws RemoteException
     */
    public void enregistrerEsclave(ServiceRayTracer r) throws RemoteException{
        serviceCalcul.add(r);
    }

    /**
     * Méthode qui permet de répartir les fragments d'images
     */
    public void genererImage(Scene s, int largeur, int hauteur) throws RemoteException{
        int nbMachines=this.serviceCalcul.size()*2;
        int l=largeur/nbMachines;
        int h=hauteur/nbMachines;
        
    }

}
