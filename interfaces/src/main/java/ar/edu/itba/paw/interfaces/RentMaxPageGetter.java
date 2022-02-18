package ar.edu.itba.paw.interfaces;

@FunctionalInterface
public interface RentMaxPageGetter {
    long apply(long userId, int state, Long limit);
}
