package remotejalse.controller.response;

import java.util.UUID;

public class NewJALSEResponse extends IdentifiedResponse {

    private final int maxEntities;

    public NewJALSEResponse(final UUID id, final int maxEntities) {
	super(id);
	this.maxEntities = maxEntities;
    }

    public int getMaxEntities() {
	return maxEntities;
    }
}
