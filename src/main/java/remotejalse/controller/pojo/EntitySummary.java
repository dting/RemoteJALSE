package remotejalse.controller.pojo;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jalse.tags.TreeMember;

public class EntitySummary extends EntityContainerSummary {

    @JsonProperty("jalseID")
    private UUID jalseID;
    private UUID parentID;
    private UUID originID;

    public EntitySummary() {}

    public EntitySummary(final UUID jalseID, final UUID parentID, final UUID id, final UUID originID,
	    final int entityCount, final String created, final TreeMember member, final int treeDepth) {
	super(id, entityCount, created, member, treeDepth);
	this.jalseID = jalseID;
	this.parentID = parentID;
	this.originID = originID;
    }

    @JsonIgnore
    public UUID getJALSEID() {
	return jalseID;
    }

    public UUID getOriginID() {
	return originID;
    }

    public UUID getParentID() {
	return parentID;
    }
}