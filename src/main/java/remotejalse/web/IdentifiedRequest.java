package remotejalse.web;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class IdentifiedRequest {

    private String id;

    public String getID() {
	return id;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
