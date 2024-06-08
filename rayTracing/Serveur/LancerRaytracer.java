package Serveur;

import java.time.Instant;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;

import raytracer.Disp;
import raytracer.Scene;
import raytracer.Image;

public class LancerRaytracer {

    public static String aide = "Raytracer : synthèse d'image par lancé de rayons (https://en.wikipedia.org/wiki/Ray_tracing_(graphics))\n\nUsage : java LancerRaytracer [fichier-scène] [largeur] [hauteur]\n\tfichier-scène : la description de la scène (par défaut simple.txt)\n\tlargeur : largeur de l'image calculée (par défaut 512)\n\thauteur : hauteur de l'image calculée (par défaut 512)\n";

    public static void main(String args[]) {
        try {
            Registry reg = LocateRegistry.getRegistry("localhost");
            try {

                ServiceDistributionRayTracer service = new ServiceDistributionRayTracer();
                ServiceDistributeur rd1 = (ServiceDistributeur) UnicastRemoteObject.exportObject(service, 0);

                reg.rebind("DistributeurFragments", rd1);

            } catch (RemoteException e) {
                System.out.println("Erreur lors de l'exportation de l'objet");
            }
        } catch (RemoteException e) {
            System.out.println("Problème de connexion à l'annuaire");
        }

    }

}
