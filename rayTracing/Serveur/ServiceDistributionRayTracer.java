package Serveur;

import java.rmi.RemoteException;
import java.util.ArrayList;

import Client.ServiceClient;
import ServiceCalcul.ServiceRayTracer;
import raytracer.Scene;
public class ServiceDistributionRayTracer implements ServiceDistributeur {
    private ArrayList<ServiceRayTracer> servicesCalculs;

    public ServiceDistributionRayTracer(){
        this.servicesCalculs=new ArrayList<ServiceRayTracer>();
    }
     
    /**
     * Permet aux esclaves de s'enregistrer sur le service central
     * 
     * @param r
     * @throws RemoteException
     */
    public void enregistrerEsclave(ServiceRayTracer r) throws RemoteException{
        synchronized(servicesCalculs) {
            servicesCalculs.add(r);
        }
    }

    /**
     * Méthode qui permet de répartir les fragments d'images
     */
    public void genererImage(ServiceClient client, int largeur, int hauteur) throws RemoteException {
        int nbMachines = this.servicesCalculs.size() * 2;
        int l = largeur / nbMachines;
        int h = hauteur / nbMachines;

        Scene scene = client.getScene();
        int x0 = 0;
        int y0 = 0;

        while (y0 < hauteur && (y0 + h) <= hauteur) {
            for (ServiceRayTracer sc : this.servicesCalculs) {
                int actualL = l;
                int actualH = h;
                if (Math.abs((x0 + l) - largeur) < l) {
                    actualL += Math.abs((x0 + l) - largeur);
                }
                if (Math.abs((y0 + h) - hauteur) < h) {
                    actualH += Math.abs((y0 + h) - hauteur);
                }

                // Créer et démarrer un thread pour chaque fragment
                ThreadCalculs thread = new ThreadCalculs(sc, this.servicesCalculs, client, x0, y0, actualL, actualH, scene);
                thread.start();

                x0 += l;
                if (x0 >= largeur || (x0 + l) > largeur) {
                    x0 = 0;
                    y0 += h;
                }
            }
        }
    }


}
