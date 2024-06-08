package ServiceCalcul;
import java.rmi.Remote;
import java.rmi.RemoteException;
import raytracer.Image;
import raytracer.Scene;

public interface ServiceRayTracer extends Remote{
    /**
     * Méthode qui génère et retourne une image
     * @param x0
     * @param y0
     * @param w
     * @param h
     * @param c
     * @return
     * @throws RemoteException
     */
    public Image genererImage(int x0, int y0, int w, int h,Scene c)throws RemoteException;
}