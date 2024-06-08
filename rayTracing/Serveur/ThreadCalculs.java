package Serveur;


import ServiceCalcul.ServiceRayTracer;
import raytracer.Image;
import raytracer.Scene;
import java.rmi.RemoteException;

import Client.ServiceClient;

public class ThreadCalculs extends Thread {

    private ServiceRayTracer serviceCalcul;
    private ServiceClient client;
    private int x0, y0, l, h,id;
    private Scene scene;
    private ServiceDistributeur distributeur;
    private static int THREADID=1;

    public ThreadCalculs(ServiceRayTracer serviceCalcul, ServiceDistributeur distributeur, ServiceClient client, int x0, int y0, int l, int h, Scene scene) {
        this.serviceCalcul = serviceCalcul;
        this.distributeur = distributeur;
        this.client = client;
        this.x0 = x0;
        this.y0 = y0;
        this.l = l;
        this.h = h;
        this.scene = scene;
        this.id = THREADID;
        THREADID++;
    }

    @Override
    public void run() {
        try {
            Image image = serviceCalcul.genererImage(x0, y0, l, h, scene);
            System.out.println("Thread("+id+"): Fragment généré, envoie vers le client");
            client.afficherFragment(image, x0, y0);
        } catch (RemoteException e) {
            /*
             * Si on catch une erreur dans le thread c'est que le ServiceRayTracer n'est plus disponible
             * 
             */
            try{
            //On supprime donc le noeud de la liste des noeuds du distributeur
            synchronized (distributeur.getServicesRayTracer()) {
                System.out.println("Thread("+id+"): suppression de "+distributeur.getServicesRayTracer().get(serviceCalcul));
                distributeur.getServicesRayTracer().remove(serviceCalcul);
            }
            //On récupère un nouveau noeud disponible
            ServiceRayTracer newSlave = distributeur.distribuerNoeud();
            //On lance un nouveau thread pour faire le calcul avec le nouveau noeuds
            ThreadCalculs newThread = new ThreadCalculs(newSlave, distributeur, client, x0, y0, l, h, scene);
            newThread.start();
        }catch(RemoteException error){
            System.out.println("Thread("+id+"): Connexion perdu avec le Serveur");
        }
        }
    }
}
