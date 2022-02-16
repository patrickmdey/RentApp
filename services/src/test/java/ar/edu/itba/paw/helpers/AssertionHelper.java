package ar.edu.itba.paw.helpers;

import org.junit.Assert;
import java.util.Collection;

public class AssertionHelper {
    public static void AssertCollectionEquals(Collection expected, Collection actual) {

        Assert.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }
}
