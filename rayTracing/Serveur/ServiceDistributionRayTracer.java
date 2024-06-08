package Serveur;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Client.ServiceClient;
import ServiceCalcul.ServiceRayTracer;
import raytracer.Scene;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
public class ServiceDistributionRayTracer implements ServiceDistributeur {
    private HashMap<ServiceRayTracer, String> servicesCalculs;

    public ServiceDistributionRayTracer(){
        this.servicesCalculs=new HashMap<ServiceRayTracer, String>();
    }
     
    /**
     * Permet aux esclaves de s'enregistrer sur le service central
     * 
     * @param r
     * @throws RemoteException
     */
    public void enregistrerEsclave(ServiceRayTracer r) throws RemoteException{
        try{
            synchronized(servicesCalculs) {
                servicesCalculs.put(r, RemoteServer.getClientHost());
                System.out.println("Le service de calcul "+RemoteServer.getClientHost()+" s'est enregistré");
            }
        }catch(ServerNotActiveException e){
            System.out.println("Erreur lors de l'enregistrement du service de calcul");
        }
    }

    /**
     * Méthode qui permet de répartir les fragments d'images
     */
    public void genererImage(ServiceClient client, int largeur, int hauteur) throws RemoteException {
        if(this.servicesCalculs.isEmpty()){
            throw new RemoteException();
        } else{
            int nbMachines = this.servicesCalculs.size() * 2;
            int l = largeur / nbMachines;
            int h = hauteur / nbMachines;

            Scene scene = client.getScene();
            int x0 = 0;
            int y0 = 0;

            while (y0 < hauteur && (y0 + h) <= hauteur) {
                for (ServiceRayTracer sc : this.servicesCalculs.keySet()) {

                    System.out.println("Envoie d'un fragement d'image à "+this.servicesCalculs.get(sc));

                    int actualL = l;
                    int actualH = h;
                    if (Math.abs((x0 + l) - largeur) < l) {
                        actualL += Math.abs((x0 + l) - largeur);
                    }
                    if (Math.abs((y0 + h) - hauteur) < h) {
                        actualH += Math.abs((y0 + h) - hauteur);
                    }

                    try{
                        sc.estConnecte();
                        // Créer et démarrer un thread pour chaque fragment
                        ThreadCalculs thread = new ThreadCalculs(sc, this.servicesCalculs, client, x0, y0, actualL, actualH, scene);
                        thread.start();
                        x0 += l;
                        if (x0 >= largeur || (x0 + l) > largeur) {
                            x0 = 0;
                            y0 += h;
                        }
                    } catch(RemoteException e){
                        synchronized (servicesCalculs) {
                            System.out.println("suppression de "+servicesCalculs.get(sc));
                            servicesCalculs.remove(sc);
                        }
                    } 
                }
            }
        }
    }


}
