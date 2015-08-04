package remotejalse.controller.request;

import java.util.UUID;

public abstract class IdentifiedRequest {

    private UUID id;

    public UUID getID() {
	return id;
    }
}
