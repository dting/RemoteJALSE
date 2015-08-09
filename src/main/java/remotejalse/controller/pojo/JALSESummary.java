package remotejalse.controller.pojo;

import java.util.UUID;

import jalse.tags.TreeMember;

public class JALSESummary extends EntityContainerSummary {

    private int entityLimit;
    private int totalEntityCount;

    public JALSESummary() {}

    public JALSESummary(final UUID id, final int entityLimit, final int totalEntityCount, final int entityCount,
	    final String created, final TreeMember member, final int treeDepth) {
	super(id, entityCount, created, member, treeDepth);
	this.entityLimit = entityLimit;
	this.totalEntityCount = totalEntityCount;
    }

    public int getEntityLimit() {
	return entityLimit;
    }

    public int getTotalEntityCount() {
	return totalEntityCount;
    }
}
