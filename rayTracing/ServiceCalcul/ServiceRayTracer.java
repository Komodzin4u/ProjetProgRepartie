package ServiceCalcul;
import java.rmi.Remote;
import java.rmi.RemoteException;
import raytracer.Image;
import raytracer.Scene;

public interface ServiceRayTracer extends Remote{
    /**
     * Méthode qui génère et retourne une image
     * @param x0 coordonnée x de départ
     * @param y0 coordonnée y de départ
     * @param w largeur de l'image généré
     * @param h hauteur de l'image généré
     * @param c la Scene avec laquelle généré l'image
     * @return Image generé
     * @throws RemoteException
     */
    public Image genererImage(int x0, int y0, int w, int h,Scene c)throws RemoteException;

    public boolean estConnecte() throws RemoteException;

    public boolean estOccupe() throws RemoteException;
}