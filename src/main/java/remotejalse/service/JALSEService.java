package remotejalse.service;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import jalse.JALSE;

@Service
public class JALSEService {

    private static final Logger logger = LogManager.getLogger(JALSEService.class);

    public Set<UUID> getActiveIDs() {
	return Collections.singleton(getDefaultID());
    }

    public int getDefaultEntityLimit() {
	return 100;
    }

    public UUID getDefaultID() {
	return UUID.randomUUID();
    }

    public JALSE createJALSE(UUID id, int entityLimit) {
	logger.info("Creating JALSE instance with ID {} and limit {}", id, entityLimit);
	return null;
    }
}
