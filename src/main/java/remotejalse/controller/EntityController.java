package remotejalse.controller;

import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jalse.entities.EntityContainer;
import remotejalse.controller.pojo.CreateEntity;
import remotejalse.controller.pojo.IdentifiedEntity;
import remotejalse.service.JALSEService;

@RestController
@RequestMapping("/jalse/entities")
public class EntityController {

    private static final Logger logger = LogManager.getLogger(EntityController.class);

    @Autowired
    private JALSEService jalseService;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public IdentifiedEntity create(@RequestBody final CreateEntity request) {
	if (logger.isDebugEnabled()) {
	    logger.debug("New Entity request: {}", request);
	}

	final UUID jalseID = request.getJALSEID(), parentID = request.getParentID();
	final EntityContainer parent = parentID == null || jalseID.equals(parentID) ? jalseService.getJALSE(jalseID)
		: jalseService.getEntity(jalseID, parentID);

	UUID eID = request.getID();
	if (eID == null) {
	    eID = parent.newEntity().getID();
	} else {
	    parent.newEntity(eID);
	}

	return new IdentifiedEntity(jalseID, eID);
    }

    @RequestMapping(value = "/kill", method = RequestMethod.POST)
    public void delete(@RequestBody final IdentifiedEntity request) {
	if (logger.isDebugEnabled()) {
	    logger.debug("Kill Entity request: {}", request);
	}
	jalseService.getEntity(request.getJALSEID(), request.getID()).kill();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Set<UUID> entities(@RequestParam(value = "jalseID") final String jalseID,
	    @RequestParam(value = "entityID", required = false) final String entityID) {
	final UUID jID = UUID.fromString(jalseID);
	final EntityContainer parent = StringUtils.isBlank(entityID) ? jalseService.getJALSE(jID)
		: jalseService.getEntity(jID, UUID.fromString(entityID));
	return parent.getEntityIDs();
    }
}
