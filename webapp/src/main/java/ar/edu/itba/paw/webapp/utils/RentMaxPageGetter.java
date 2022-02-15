package ar.edu.itba.paw.webapp.utils;

@FunctionalInterface
public interface RentMaxPageGetter {
    long apply(long ownerId, int state, Long limit);
}
