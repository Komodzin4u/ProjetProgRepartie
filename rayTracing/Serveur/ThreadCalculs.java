package Serveur;

import ServiceCalcul.ServiceRayTracer;
import raytracer.Image;
import raytracer.Scene;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import Client.ServiceClient;

public class ThreadCalculs extends Thread {

    private ServiceRayTracer serviceCalcul;
    private HashMap<ServiceRayTracer, String> servicesCalculs;
    private ServiceClient client;
    private int x0, y0, l, h;
    private Scene scene;

    public ThreadCalculs(ServiceRayTracer serviceCalcul, HashMap<ServiceRayTracer, String> servicesCalculs, ServiceClient client, int x0, int y0, int l, int h, Scene scene) {
        this.serviceCalcul = serviceCalcul;
        this.servicesCalculs = servicesCalculs;
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
            synchronized (servicesCalculs) {
                System.out.println("suppression de "+servicesCalculs.get(serviceCalcul));
                servicesCalculs.remove(serviceCalcul);
            }
        }
    }
}
