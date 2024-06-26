package Client;

import raytracer.Disp;
import raytracer.Image;
import raytracer.Scene;

import java.rmi.RemoteException;

/**
 * Classe qui correspond au service client, c'est à dire le client qui contacte le service central
 * pour lui demander de réaliser un calcul pour son image
 */
public class ServiceClientImage implements ServiceClient {

    private String fichier;
    private Scene scene;

    private Disp fenetre;

    /**
     * On crée un objet client qui crée la scène et la fenêtre pour ensuite recevoir les fragments
     * d'image du serveur
     * On lui donne le nom du fichier qui contient la config de l'image
     * La largeur et longueur attendues.
     * @param f
     * @param largeur
     * @param hauteur
     */
    public ServiceClientImage(String f, int largeur, int hauteur) {
        this.fichier = f;
        // On crée notre fenêtre

        this.fenetre = new Disp("Projet Prog Répartie", largeur, hauteur);

        this.scene = new Scene(this.fichier, largeur, hauteur);
    }

    @Override
    public void afficherFragment(Image image, int x0, int y0) throws RemoteException {
        System.out.println("Réception d'un fragment d'image. Placement du fragment en x: "+x0+", y: "+y0);
        try{
        this.fenetre.setImage(image, x0, y0);
        }catch(Exception e){
            
        }
    }


    public Scene getScene() throws RemoteException{
        return this.scene;
    }

}
