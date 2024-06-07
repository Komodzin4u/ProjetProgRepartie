package Client;

import raytracer.Scene;
import raytracer.Image;
import java.rmi.RemoteException;
import java.rmi.ConnectException;
import java.rmi.Remote;

public interface ServiceDistributeur extends Remote {

    /**
     * Permet aux esclaves de s'enregistrer sur le service central
     * 
     * @param r
     * @throws RemoteException
     */
    public void enregistrerEsclaves(ServiceRayTracer r) throws RemoteException;

    public void enregistrerClient(ServiceClient c) throws RemoteException;

    public int getNoeud() throws RemoteException, ConnectException;
}
