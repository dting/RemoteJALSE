package remotejalse.controller.response;

import java.util.UUID;

public abstract class IdentifiedResponse {

    private final UUID id;

    public IdentifiedResponse(final UUID id) {
	this.id = id;
    }

    public UUID getID() {
	return id;
    }
}
