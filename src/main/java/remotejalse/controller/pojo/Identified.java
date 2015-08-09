package remotejalse.controller.pojo;

import java.util.UUID;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Identified {

    private UUID id;

    public Identified() {}

    public Identified(final UUID id) {
	this.id = id;
    }

    public UUID getID() {
	return id;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
