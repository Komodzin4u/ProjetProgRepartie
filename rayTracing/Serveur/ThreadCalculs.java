package Serveur;

import ServiceCalcul.ServiceRayTracer;
import raytracer.Image;
import raytracer.Scene;
import java.rmi.RemoteException;
import java.util.ArrayList;

import Client.ServiceClient;

public class ThreadCalculs extends Thread {

    private ServiceRayTracer serviceCalcul;
    private ArrayList<ServiceRayTracer> servicesCalculs;
    private ServiceClient client;
    private int x0, y0, l, h;
    private Scene scene;

    public ThreadCalculs(ServiceRayTracer serviceCalcul, ArrayList<ServiceRayTracer> servicesCalculs, ServiceClient client, int x0, int y0, int l, int h, Scene scene) {
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
            client.afficherFragment(image, x0, y0);
        } catch (RemoteException e) {
            synchronized (servicesCalculs) {
                servicesCalculs.remove(serviceCalcul);
                System.out.println("suppression");
            }
        }
    }
}
