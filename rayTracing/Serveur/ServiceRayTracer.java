package Serveur;
import java.rmi.Remote;
import java.rmi.RemoteException;
import raytracer.Image;
import raytracer.Scene;
public interface ServiceRayTracer extends Remote{
    public void genererImage(int x0, int y0, int w, int h,Scene c)throws RemoteException;
}