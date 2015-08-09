package remotejalse.tags;

import jalse.tags.AbstractValueTag;
import jalse.tags.SingletonTag;

@SingletonTag
public class EntityLimit extends AbstractValueTag<Integer> {

    public EntityLimit(final Integer value) {
	super(value);
	if (value <= 0) {
	    throw new IllegalArgumentException();
	}
    }
}
