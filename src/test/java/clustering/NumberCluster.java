package clustering;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * Created by berezkin on 24/12/16.
 */

public class NumberCluster {
    @Test()
    public void test1() {
        final int values[] = {1, 2, 3, 5, 6, 2};
        List<Set<Integer>> itemClusters = SimpleClustering.apply(values.length,
                (first, second) -> Math.abs(values[first] - values[second]) <= 1
        );
        Assert.assertTrue(itemClusters.size() == 2);
    }

    @Test()
    public void test2() {
        final int values[] = {1, 2, -2, 3, 5, 8, 6, 2};
        List<Set<Integer>> itemClusters = SimpleClustering.apply(values.length,
                (first, second) -> Math.abs(values[first] - values[second]) <= 1
        );
        Assert.assertTrue(itemClusters.size() == 4);
    }
}
