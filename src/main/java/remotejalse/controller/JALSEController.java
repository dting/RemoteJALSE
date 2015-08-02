package remotejalse.controller;

import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
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
import jalse.misc.AbstractIdentifiable;
import remotejalse.service.JALSEService;

@RestController
@RequestMapping("/jalse")
public final class JALSEController {

    private static final Logger logger = LogManager.getLogger(JALSEController.class);

    public class JALSECreationRequest {

	private String id;
	private Integer maxEntities;

	public String getID() {
	    return id;
	}

	public Integer getMaxEntities() {
	    return maxEntities;
	}
    }

    public class JALSEDeletionRequest {

	private String id;

	public String getID() {
	    return id;
	}
    }

    public class JALSESummary extends AbstractIdentifiable {

	private final int totalEntityCount;

	public JALSESummary(UUID id, int totalEntityCount) {
	    super(id);
	    this.totalEntityCount = totalEntityCount;
	}

	public int getTotalEntityCount() {
	    return totalEntityCount;
	}
    }

    @Autowired
    private JALSEService jalseService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public UUID create(@RequestBody final JALSECreationRequest request) {
	if (logger.isDebugEnabled()) {
	    logger.debug("Creation request: {}", ReflectionToStringBuilder.toString(request));
	}

	UUID jID = !StringUtils.isBlank(request.getID()) ? UUID.fromString(request.getID())
		: jalseService.getDefaultID();
	int entityLimit = request.getMaxEntities() != null ? request.getMaxEntities()
		: jalseService.getDefaultEntityLimit();

	jalseService.createJALSE(jID, entityLimit);

	return jID;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(@RequestBody final JALSEDeletionRequest request) {
	if (logger.isDebugEnabled()) {
	    logger.debug("Deletion request: {}", ReflectionToStringBuilder.toString(request));
	}
	jalseService.deleteJALSE(UUID.fromString(request.getID()));
    }

    @RequestMapping(method = RequestMethod.GET)
    public Set<UUID> instances() {
	return jalseService.getActiveIDs();
    }

    @RequestMapping(value = "/{jalseID}", method = RequestMethod.GET)
    public JALSESummary summary(@PathVariable String jalseID) {
	UUID jID = UUID.fromString(jalseID);
	JALSE jalse = jalseService.getJALSE(jID);
	return new JALSESummary(jID, jalse.getTreeCount());
    }
}
