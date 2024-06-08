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

    /*
     * Méthode utilisé pour ping le service Calcul et verifié qu'il est toujours connecté au serveur
     */
    public boolean estConnecte() throws RemoteException;

    /*
     * Méthode pour savoir si le service Calcul est deja en train de réaliser un calcul
     */
    public boolean estOccupe() throws RemoteException;
}