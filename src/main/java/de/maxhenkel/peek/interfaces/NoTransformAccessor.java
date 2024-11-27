package de.maxhenkel.peek.interfaces;

public interface NoTransformAccessor {

    boolean peek$shouldTransform();

    void peek$setShouldTransform(boolean shouldTransform);

}
