package remotejalse.controller.pojo;

import java.util.UUID;

public class CreateEntity extends IdentifiedEntity {

    private UUID parentID;

    public CreateEntity() {}

    public CreateEntity(final UUID jalseID, final UUID parentID, final UUID id) {
	super(jalseID, id);
	this.parentID = parentID;
    }

    public UUID getParentID() {
	return parentID;
    }
}
