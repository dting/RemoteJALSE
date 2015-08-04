package remotejalse.controller.response;

import java.util.UUID;

public class EntitySummary extends IdentifiedResponse {

    private final UUID jalseID;
    private final UUID parentID;
    private final int entityCount;

    public EntitySummary(final UUID id, final UUID jalseID, final UUID parentID, final int entityCount) {
	super(id);
	this.jalseID = jalseID;
	this.parentID = parentID;
	this.entityCount = entityCount;
    }

    public int getEntityCount() {
	return entityCount;
    }

    public UUID getJalseID() {
	return jalseID;
    }

    public UUID getParentID() {
	return parentID;
    }
}
