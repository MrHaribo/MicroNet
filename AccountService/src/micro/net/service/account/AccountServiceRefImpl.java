package micro.net.service.account;

import micronet.activemq.AMQPeer;
import micronet.network.Request;
import micronet.service.Context;

public class AccountServiceRefImpl {

	public static void main(String[] args) {
		System.out.println("Starting AccountService...");

		AMQPeer peer = new AMQPeer();
		Context context = new Context(peer, "mn://account");

		AccountService service = new AccountService();
		peer.listen("/path", (Request request) -> service.onLogin(context, request));
		
		service.onStart();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("AccountService Shutdown Hook is running !");
				service.onStop();
			}
		});
	}
}
