package remotejalse.web;

import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import remotejalse.service.JALSEService;

@RestController
@RequestMapping("/jalse")
public class JALSEController {

    private static final Logger logger = LogManager.getLogger(JALSEController.class);

    @Autowired
    private JALSEService jalseService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public UUID create(@RequestBody final JALSECreationRequest creationRequest) {
	if (logger.isDebugEnabled()) {
	    logger.debug("Creation request: {}", creationRequest);
	}

	final String id = creationRequest.getID();
	final Integer maxEntities = creationRequest.getMaxEntities();

	final UUID uid = !StringUtils.isBlank(id) ? UUID.fromString(id) : jalseService.getDefaultID();
	final int entityLimit = maxEntities != null ? maxEntities : jalseService.getDefaultEntityLimit();

	jalseService.createJALSE(uid, entityLimit);

	return uid;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(@RequestBody final IdentifiedRequest identifiedRequest) {
	if (logger.isDebugEnabled()) {
	    logger.debug("Deletion request: {}", identifiedRequest);
	}

	final String id = identifiedRequest.getID();
	if (StringUtils.isBlank(id)) {
	    throw new IllegalArgumentException();
	}

	jalseService.deleteJALSE(UUID.fromString(id));
    }

    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public Set<UUID> getActiveIDs() {
	return jalseService.getActiveIDs();
    }
}
