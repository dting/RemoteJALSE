package remotejalse.controller;

import java.text.SimpleDateFormat;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jalse.JALSE;
import jalse.entities.Entity;
import jalse.misc.Identifiable;
import jalse.tags.Created;
import jalse.tags.OriginContainer;
import jalse.tags.TreeDepth;
import jalse.tags.TreeMember;
import remotejalse.controller.pojo.EntityContainerSummary;
import remotejalse.controller.pojo.EntitySummary;
import remotejalse.controller.pojo.JALSESummary;
import remotejalse.service.JALSEService;
import remotejalse.tags.EntityLimit;

@RestController
@RequestMapping("/jalse/summary")
public class SummaryController {

    private static final ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {

	@Override
	protected SimpleDateFormat initialValue() {
	    return new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
	};
    };

    @Autowired
    private JALSEService jalseService;

    private EntitySummary entitySummary(final UUID jID, final UUID eID) {
	final Entity entity = jalseService.getEntity(jID, eID);
	return new EntitySummary(jID, Identifiable.getID(entity.getContainer()), eID,
		entity.getSingletonTag(OriginContainer.class).getValue(), entity.getEntityCount(),
		sdf.get().format(entity.getSingletonTag(Created.class).getValue()),
		entity.getSingletonTag(TreeMember.class), entity.getSingletonTag(TreeDepth.class).getValue());
    }

    private JALSESummary jalseSummary(final UUID jID) {
	final JALSE jalse = jalseService.getJALSE(jID);
	return new JALSESummary(jID, jalse.getSingletonTag(EntityLimit.class).getValue(), jalse.getTreeCount(),
		jalse.getEntityCount(), sdf.get().format(jalse.getSingletonTag(Created.class).getValue()),
		jalse.getSingletonTag(TreeMember.class), jalse.getSingletonTag(TreeDepth.class).getValue());
    }

    @RequestMapping(method = RequestMethod.GET)
    public EntityContainerSummary summary(@RequestParam(value = "jalseID") final String jalseID,
	    @RequestParam(value = "entityID", required = false) final String entityID) {
	final UUID jID = UUID.fromString(jalseID);
	if (StringUtils.isBlank(entityID)) {
	    return jalseSummary(jID);
	} else {
	    return entitySummary(jID, UUID.fromString(entityID));
	}
    }
}
