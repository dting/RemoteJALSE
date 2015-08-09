package remotejalse.controller.pojo;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentifiedEntity extends Identified {

    @JsonProperty("jalseID")
    private UUID jalseID;

    public IdentifiedEntity() {}

    public IdentifiedEntity(final UUID jalseID, final UUID id) {
	super(id);
	this.jalseID = jalseID;
    }

    @JsonIgnore
    public UUID getJALSEID() {
	return jalseID;
    }
}
