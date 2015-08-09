package remotejalse.controller;

import java.util.Set;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import remotejalse.controller.pojo.CreateJALSE;
import remotejalse.controller.pojo.Identified;
import remotejalse.service.JALSEService;

@RestController
@RequestMapping("/jalse")
public class JALSEController {

    private static final Logger logger = LogManager.getLogger(JALSEController.class);

    @Autowired
    private JALSEService jalseService;

    @RequestMapping(method = RequestMethod.GET)
    public Set<UUID> active() {
	return jalseService.getActiveIDs();
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public CreateJALSE create(@RequestBody final CreateJALSE request) {
	if (logger.isDebugEnabled()) {
	    logger.debug("New JALSE request: {}", request);
	}

	final UUID jID = request.getID() != null ? request.getID() : jalseService.getDefaultID();
	final int entityLimit = request.getEntityLimit() != null ? request.getEntityLimit()
		: jalseService.getDefaultEntityLimit();

	jalseService.createJALSE(jID, entityLimit);

	return new CreateJALSE(jID, entityLimit);
    }

    @RequestMapping(value = "/kill", method = RequestMethod.POST)
    public void delete(@RequestBody final Identified request) {
	if (logger.isDebugEnabled()) {
	    logger.debug("Kill JALSE request: {}", request);
	}
	jalseService.deleteJALSE(request.getID());
    }
}
