package remotejalse.controller;

import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jalse.JALSE;
import jalse.entities.Entity;
import jalse.entities.EntityContainer;
import remotejalse.controller.request.KillEntityRequest;
import remotejalse.controller.request.NewEntityRequest;
import remotejalse.controller.response.NewEntityResponse;
import remotejalse.service.JALSEService;

@RestController
@RequestMapping("/jalse/{jalseID}")
public class EntityController {

    private static final Logger logger = LogManager.getLogger(EntityController.class);

    @Autowired
    private JALSEService jalseService;

    @RequestMapping(value = "/entities/new", method = RequestMethod.POST)
    public NewEntityResponse create(@PathVariable final String jalseID, @RequestBody final NewEntityRequest request) {
	if (logger.isDebugEnabled()) {
	    logger.debug("New Entity request: {}", ReflectionToStringBuilder.toString(request));
	}

	final JALSE jalse = jalseService.getJALSE(UUID.fromString(jalseID));
	final UUID eID = newEntity(jalse, request);

	return new NewEntityResponse(eID);
    }

    @RequestMapping(value = "/{entityID}/entities/new", method = RequestMethod.POST)
    public NewEntityResponse create(@PathVariable final String jalseID, @PathVariable final String entityID,
	    @RequestBody final NewEntityRequest request) {
	if (logger.isDebugEnabled()) {
	    logger.debug("New Entity request: {}", ReflectionToStringBuilder.toString(request));
	}

	final Entity entity = jalseService.getEntity(UUID.fromString(jalseID), UUID.fromString(entityID));
	final UUID eID = newEntity(entity, request);

	return new NewEntityResponse(eID);
    }

    @RequestMapping(value = "/entities/kill", method = RequestMethod.POST)
    public void delete(@PathVariable final String jalseID, @RequestBody final KillEntityRequest request) {
	if (logger.isDebugEnabled()) {
	    logger.debug("Kill Entity request: {}", ReflectionToStringBuilder.toString(request));
	}
	jalseService.getEntity(UUID.fromString(jalseID), request.getID()).kill();
    }

    @RequestMapping(value = "/entities", method = RequestMethod.GET)
    public Set<UUID> entities(@PathVariable final String jalseID) {
	return jalseService.getJALSE(UUID.fromString(jalseID)).getEntityIDs();
    }

    @RequestMapping(value = "/{entityID}/entities", method = RequestMethod.GET)
    public Set<UUID> entities(@PathVariable final String jalseID, @PathVariable final String entityID) {
	return jalseService.getEntity(UUID.fromString(jalseID), UUID.fromString(entityID)).getEntityIDs();
    }

    private UUID newEntity(final EntityContainer container, final NewEntityRequest request) {
	if (request.getID() == null) {
	    return container.newEntity().getID();
	}
	final UUID eID = request.getID();
	container.newEntity(eID);
	return eID;
    }

}
