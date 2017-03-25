import micronet.activemq.AMQPeer;
import micronet.network.Request;
import micronet.service.Context;

import micro.net.service.account.AccountService;



public class AccountServiceImpl {

	public static void main(String[] args) {
		try {
			System.out.println("Starting AccountService...");
	
			AMQPeer peer = new AMQPeer();
			Context context = new Context(peer, "mn://account");
	
			AccountService service = new AccountService();
	
			peer.listen("/register", (Request request) -> service.onRegister(context, request));
peer.listen("/login", (Request request) -> service.onLogin(context, request));
peer.listen("/some/extra/method", (Request request) -> service.someMethod(context, request));

			
			System.out.println("AccountService started...");
service.onStart();

			
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					System.out.println("AccountService stopped...");
service.onStop();

				}
			});
		} catch (Exception e) {
			System.err.print("AccountService crushed...\n\n");
			e.printStackTrace();
		}
	}
}
