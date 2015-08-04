package remotejalse.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jalse.JALSE;
import jalse.entities.Entity;
import jalse.misc.Identifiable;
import remotejalse.controller.response.EntitySummary;
import remotejalse.controller.response.JALSESummary;
import remotejalse.service.JALSEService;

@RestController
@RequestMapping(value = "/jalse/{jalseID}")
public class SummaryController {

    @Autowired
    private JALSEService jalseService;

    @RequestMapping(method = RequestMethod.GET)
    public JALSESummary summary(@PathVariable final String jalseID) {
	final UUID jID = UUID.fromString(jalseID);
	final JALSE jalse = jalseService.getJALSE(jID);
	return new JALSESummary(jID, jalse.getTreeCount());
    }

    @RequestMapping(value = "/{entityID}", method = RequestMethod.GET)
    public EntitySummary summary(@PathVariable final String jalseID, @PathVariable final String entityID) {
	final UUID jID = UUID.fromString(jalseID), eID = UUID.fromString(entityID);
	final Entity e = jalseService.getEntity(jID, eID);
	return new EntitySummary(eID, jID, Identifiable.getID(e.getContainer()), e.getEntityCount());
    }
}
