package remotejalse.controller.response;

import java.util.UUID;

public class JALSESummary extends IdentifiedResponse {

    private final int totalEntityCount;

    public JALSESummary(final UUID id, final int totalEntityCount) {
	super(id);
	this.totalEntityCount = totalEntityCount;
    }

    public int getTotalEntityCount() {
	return totalEntityCount;
    }
}