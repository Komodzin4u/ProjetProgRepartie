package Client;

import raytracer.Image;

import java.rmi.RemoteException;

/**
 * Interface du Client. Le client c'est celui qui appelle le service central en lui demandant telle image
 */
public interface ServiceClient {

    /**
     * Permet au service central d'appeler le client pour que ce dernier affiche le fragment
     * @param image
     * @param x0
     * @param y0
     * @throws RemoteException
     */
    public void afficherFragment(Image image, int x0, int y0) throws RemoteException;
}
