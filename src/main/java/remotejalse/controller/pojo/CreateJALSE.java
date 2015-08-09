package remotejalse.controller.pojo;

import java.util.UUID;

public class CreateJALSE extends Identified {

    private Integer entityLimit;

    public CreateJALSE() {}

    public CreateJALSE(final UUID id, final Integer entityLimit) {
	super(id);
	this.entityLimit = entityLimit;
    }

    public Integer getEntityLimit() {
	return entityLimit;
    }
}