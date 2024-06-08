package Serveur;


import ServiceCalcul.ServiceRayTracer;
import raytracer.Image;
import raytracer.Scene;
import java.rmi.RemoteException;

import Client.ServiceClient;

public class ThreadCalculs extends Thread {

    private ServiceRayTracer serviceCalcul;
    private ServiceClient client;
    private int x0, y0, l, h;
    private Scene scene;
    private ServiceDistributeur distributeur;

    public ThreadCalculs(ServiceRayTracer serviceCalcul, ServiceDistributeur distributeur, ServiceClient client, int x0, int y0, int l, int h, Scene scene) {
        this.serviceCalcul = serviceCalcul;
        this.distributeur = distributeur;
        this.client = client;
        this.x0 = x0;
        this.y0 = y0;
        this.l = l;
        this.h = h;
        this.scene = scene;
    }

    @Override
    public void run() {
        try {
            Image image = serviceCalcul.genererImage(x0, y0, l, h, scene);
            System.out.println("Fragment généré, envoie vers le client");
            client.afficherFragment(image, x0, y0);
        } catch (RemoteException e) {
            try{
            synchronized (distributeur.getServicesRayTracer()) {
                System.out.println("suppression de "+distributeur.getServicesRayTracer().get(serviceCalcul));
                distributeur.getServicesRayTracer().remove(serviceCalcul);
            }
            ServiceRayTracer newSlave = distributeur.distribuerNoeud();
            ThreadCalculs newThread = new ThreadCalculs(newSlave, distributeur, client, x0, y0, l, h, scene);
            newThread.start();
        }catch(RemoteException error){
            System.out.println("Connexion perdu avec le Serveur");
        }
        }
    }
}
