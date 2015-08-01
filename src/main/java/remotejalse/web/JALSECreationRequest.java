package remotejalse.web;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JALSECreationRequest {

    private final String id;
    private final Integer maxEntities;

    @JsonCreator
    public JALSECreationRequest(@JsonProperty("id") final String id,
	    @JsonProperty("maxEntities") final Integer maxEntities) {
	this.id = id;
	this.maxEntities = maxEntities;
    }

    public String getID() {
	return id;
    }

    public Integer getMaxEntities() {
	return maxEntities;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}