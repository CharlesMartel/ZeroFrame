package ZeroFrame;

public class Application extends Thread {

	public void run(){
		while(true){
			try {
				sleep(5000);
				//cleanMessageClients(); TODO: FIX -> Method does not work
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//TODO: Fix this method
	//private void cleanMessageClients(){
	//	for(int index = 0; index < ZeroFrame.Networking.ClientManager.messageClients.size(); index++){
	//		if(ZeroFrame.Networking.ClientManager.messageClients.get(index).get(0).ClientSocket.isClosed()){
	//			ZeroFrame.Networking.ClientManager.clientSockets.remove(index);
	//			ZeroFrame.Networking.ClientManager.messageClients.remove(index);
	//		}
	//	}
	//}
}
