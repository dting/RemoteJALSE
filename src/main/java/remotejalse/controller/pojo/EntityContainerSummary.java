package remotejalse.controller.pojo;

import java.util.UUID;

import jalse.tags.TreeMember;

public class EntityContainerSummary extends Identified {

    private String created;
    private int treeDepth;
    private TreeMember member;
    private int entityCount;

    public EntityContainerSummary() {}

    public EntityContainerSummary(final UUID id, final int entityCount, final String created, final TreeMember member,
	    final int treeDepth) {
	super(id);
	this.created = created;
	this.member = member;
	this.treeDepth = treeDepth;
	this.entityCount = entityCount;
    }

    public String getCreated() {
	return created;
    }

    public int getEntityCount() {
	return entityCount;
    }

    public TreeMember getMember() {
	return member;
    }

    public int getTreeDepth() {
	return treeDepth;
    }
}
