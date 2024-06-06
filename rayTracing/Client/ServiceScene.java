package Client;
import java.rmi.RemoteException;
import raytracer.*;
import java.util.*;
import java.io.*;
public class ServiceScene implements ServiceRayTracer{
	private ServiceDistributeur serveur;

	 public ServiceScene(ServiceDistributeur server){
		this.serveur = server;
	 }
     public void genererImage(int x0, int y0, int w, int h,Scene c)throws RemoteException{
		serveur.recupererImage(c.compute(x0,y0,w,h),x0,y0);
	 }
}