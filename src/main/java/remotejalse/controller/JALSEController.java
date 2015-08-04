package remotejalse.controller;

import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import remotejalse.controller.request.KillJALSERequest;
import remotejalse.controller.request.NewJALSERequest;
import remotejalse.controller.response.NewJALSEResponse;
import remotejalse.service.JALSEService;

@RestController
@RequestMapping("/jalse")
public class JALSEController {

    private static final Logger logger = LogManager.getLogger(JALSEController.class);

    @Autowired
    private JALSEService jalseService;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public NewJALSEResponse create(@RequestBody final NewJALSERequest request) {
	if (logger.isDebugEnabled()) {
	    logger.debug("New JALSE request: {}", ReflectionToStringBuilder.toString(request));
	}

	final UUID jID = request.getID() != null ? request.getID() : jalseService.getDefaultID();
	final int entityLimit = request.getMaxEntities() != null ? request.getMaxEntities()
		: jalseService.getDefaultEntityLimit();

	jalseService.createJALSE(jID, entityLimit);

	return new NewJALSEResponse(jID, entityLimit);
    }

    @RequestMapping(value = "/kill", method = RequestMethod.POST)
    public void delete(@RequestBody final KillJALSERequest request) {
	if (logger.isDebugEnabled()) {
	    logger.debug("Kill JALSE request: {}", ReflectionToStringBuilder.toString(request));
	}
	jalseService.deleteJALSE(request.getID());
    }

    @RequestMapping(method = RequestMethod.GET)
    public Set<UUID> instances() {
	return jalseService.getActiveIDs();
    }
}
