package remotejalse.controller;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jalse.entities.Entity;
import jalse.misc.AbstractIdentifiable;
import jalse.misc.Identifiable;
import remotejalse.service.JALSEService;

@RestController
@RequestMapping("/jalse/{jalseID}")
public final class EntityController {

    public class EntitySummary extends AbstractIdentifiable {

	private final UUID jalseID;
	private final UUID parentID;
	private final int entityCount;

	public EntitySummary(UUID id, UUID jalseID, UUID parentID, int entityCount) {
	    super(id);
	    this.jalseID = Objects.requireNonNull(jalseID);
	    this.parentID = Objects.requireNonNull(parentID);
	    this.entityCount = entityCount;
	}

	public UUID getJALSEID() {
	    return jalseID;
	}

	public UUID getParentID() {
	    return parentID;
	}

	public int getTotalEntityCount() {
	    return entityCount;
	}
    }

    @Autowired
    private JALSEService jalseService;

    @RequestMapping(value = "/entities", method = RequestMethod.GET)
    public Set<UUID> entities(@PathVariable String jalseID) {
	return jalseService.getJALSE(UUID.fromString(jalseID)).getEntityIDs();
    }

    @RequestMapping(value = "/{entityID}/entities", method = RequestMethod.GET)
    public Set<UUID> entities(@PathVariable String jalseID, @PathVariable String entityID) {
	return jalseService.getEntity(UUID.fromString(jalseID), UUID.fromString(entityID)).getEntityIDs();
    }

    @RequestMapping(value = "/{entityID}", method = RequestMethod.GET)
    public EntitySummary summary(@PathVariable String jalseID, @PathVariable String entityID) {
	UUID jID = UUID.fromString(jalseID), eID = UUID.fromString(entityID);
	Entity e = jalseService.getEntity(jID, eID);
	return new EntitySummary(eID, jID, Identifiable.getID(e.getContainer()), e.getEntityCount());
    }
}
