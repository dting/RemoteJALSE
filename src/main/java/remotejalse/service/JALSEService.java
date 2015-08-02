package remotejalse.service;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jalse.DefaultJALSE;
import jalse.JALSE;
import jalse.actions.ActionEngine;
import jalse.actions.ThreadPoolActionEngine;
import jalse.entities.DefaultEntityFactory;
import jalse.entities.Entity;

@Service
public class JALSEService {

    private static final Logger logger = LogManager.getLogger(JALSEService.class);

    private static final String RANDOM_ID = "random";

    private final int defaultEntityLimit;
    private final int actionEngineThreads;
    private final Supplier<UUID> idSupplier;
    private final Map<UUID, JALSE> instances;
    private ActionEngine sharedEngine;

    @Autowired
    public JALSEService(@Value("${defaultID}") final String defaultID,
	    @Value("${defaultEntityLimit}") final int defaultEntityLimit,
	    @Value("${actionEngineThreads}") final int actionEngineThreads) {
	if (defaultEntityLimit <= 0) {
	    throw new IllegalArgumentException("Default entity limit must be positive");
	}
	if (actionEngineThreads <= 0) {
	    throw new IllegalArgumentException("Action engine threads must be positive");
	}
	if (RANDOM_ID.equalsIgnoreCase(defaultID)) {
	    idSupplier = UUID::randomUUID;
	} else {
	    final UUID id = UUID.fromString(defaultID);
	    idSupplier = () -> id;
	}
	this.defaultEntityLimit = defaultEntityLimit;
	this.actionEngineThreads = actionEngineThreads;
	instances = new ConcurrentHashMap<>();
    }

    public JALSE createJALSE(final UUID id, final int entityLimit) {
	logger.info("Creating JALSE instance: {} ({})", id, entityLimit);

	// Check existing instances
	if (instances.containsKey(id)) {
	    throw new IllegalArgumentException(String.format("JALSE instance exists with ID: %s", id));
	}

	// Create instance that cannot stop the shared engine.
	final JALSE jalse = new DefaultJALSE(id, sharedEngine, new DefaultEntityFactory(entityLimit)) {

	    @Override
	    public void pause() {}

	    @Override
	    public void stop() {}
	};
	instances.put(id, jalse);
	return jalse;
    }

    public void deleteJALSE(final UUID id) {
	logger.info("Deleting JALSE instance: {}", id);

	JALSE jalse = instances.remove(Objects.requireNonNull(id));

	// Check existing instances
	if (jalse == null) {
	    throw new IllegalArgumentException(String.format("No JALSE instance exists with ID: %s", id));
	}

	// Kill top level entities
	jalse.getEntities().forEach(Entity::kill);
    }

    public Set<UUID> getActiveIDs() {
	return Collections.unmodifiableSet(instances.keySet());
    }

    public int getDefaultEntityLimit() {
	return defaultEntityLimit;
    }

    public UUID getDefaultID() {
	return idSupplier.get();
    }

    public synchronized void initSharedEngine() {
	if (sharedEngine == null) {
	    logger.info("Initialising shared engine with {} threads", actionEngineThreads);
	    sharedEngine = new ThreadPoolActionEngine(actionEngineThreads);
	}
    }

    public JALSE getJALSE(UUID id) {
	JALSE jalse = instances.get(Objects.requireNonNull(id));
	// Check existing instances
	if (jalse == null) {
	    throw new IllegalArgumentException(String.format("No JALSE instance exists with ID: %s", id));
	}
	return jalse;
    }

    public Entity getEntity(UUID jalseID, UUID entityID) {
	JALSE jalse = getJALSE(jalseID);
	Optional<Entity> optEntity = jalse.streamEntityTree().filter(e -> entityID.equals(e.getID())).findAny();
	return optEntity.orElseThrow(
		() -> new IllegalArgumentException(String.format("No Entity found with ID: %s", entityID)));
    }
}
