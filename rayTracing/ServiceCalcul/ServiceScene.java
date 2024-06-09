package ServiceCalcul;
import java.rmi.RemoteException;
import raytracer.*;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
public class ServiceScene implements ServiceRayTracer{
	private boolean occupe=false;
     public Image genererImage(int x0, int y0, int w, int h,Scene c)throws RemoteException{
		occupe=true;
		String host ="";
		try{
			host = RemoteServer.getClientHost();
			System.out.println("Demande de calcul d'image de la part de : "+host);
		}catch(ServerNotActiveException e){

		}
		occupe=false;
		return c.compute(x0,y0,w,h);
	 }

	 public boolean estConnecte() throws RemoteException{
		return true;
	 }
	 public boolean estOccupe()throws RemoteException{
		return occupe;
	 }
}