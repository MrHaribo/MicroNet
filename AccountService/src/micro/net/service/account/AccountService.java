package micro.net.service.account;


import java.net.URI;

import micronet.core.network.ParameterCode;
import micronet.core.network.Request;
import micronet.core.network.Response;
import micronet.core.network.StatusCode;
import micronet.core.serialization.Serialization;
import micronet.service.app.ServiceApp;
import micronet.service.model.CredentialValues;
import micronet.service.model.UserValues;

public class AccountService extends ServiceApp {

	AccountDatabase database;

	public static void main(String[] args) {
		System.out.println("Starting Account Service...");
		new AccountService().start(URI.create("mn://account"));
	}

	@Override
	protected void onStart() {
		database = new AccountDatabase();

		listen("/register", (Request request) -> {
			CredentialValues credentials = Serialization.deserialize(request.getData(), CredentialValues.class);
			UserValues existingUser = database.getUser(credentials.getUsername());

			if (existingUser != null) {
				System.out.println("User already registred! " + existingUser.getName());
				return new Response(StatusCode.CONFLICT, "User already registred...");
			} else {
				if (database.addUser(credentials)) {
					System.out.println("User added: " + credentials.getUsername());

					// TODO: Manage via Topics
					UserValues newUser = database.getUser(credentials.getUsername());
					Request createInventoryRequest = new Request();
					createInventoryRequest.getParameters().set(ParameterCode.USER_ID, newUser.getId());
					sendRequest(URI.create("mn://item/inventory/create"), createInventoryRequest);

					return new Response(StatusCode.OK, "User registred");
				} else {
					return new Response(StatusCode.INTERNAL_SERVER_ERROR, "Error registering User");
				}
			}
		});

		listen("/login", (Request request) -> {
			CredentialValues credentials = Serialization.deserialize(request.getData(), CredentialValues.class);
			UserValues user = database.getUser(credentials.getUsername());

			if (user == null)
				return new Response(StatusCode.NOT_FOUND);
			if (!credentials.getPassword().equals(user.getCredentials().getPassword()))
				return new Response(StatusCode.UNAUTHORIZED);
			return new Response(StatusCode.OK, Serialization.serialize(user));
		});
	}

	@Override
	public void onStop() {
		database.shutdown();
	}
}
