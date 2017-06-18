package clustering;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by berezkin on 22/12/16.
 */

public final class SimpleClustering {
    public interface Criteria {
        boolean call(int first, int second);
    }

    public static List<Set<Integer>> apply(int count, Criteria criteria) {
        SimpleClustering clustering = new SimpleClustering(count, criteria);
        clustering.run();
        return clustering.getClusters();
    }

    private SimpleClustering(int count, Criteria criteria) {
        this.count = count;
        this.criteria = criteria;
        itemClusters = new Set[count];
    }

    private void run() {
        buildClusters();
        makeUnique();
    }

    private List<Set<Integer>> getClusters() {
        return clusters;
    }

    private void buildClusters() {
        for (int item = 0; item < count; ++item) {
            if (itemClusters[item] == null) {
                itemClusters[item] = new HashSet<>();
                itemClusters[item].add(item);
            }
            for (int other = item + 1; other < count; ++other) {
                if (criteria.call(item, other)) {
                    joinClusters(item, other);
                }
            }
        }
    }

    private void joinClusters(int first, int second) {
        Set<Integer> cluster = itemClusters[first];
        if (itemClusters[second] == null) {
            cluster.add(second);
            itemClusters[second] = cluster;
        } else {
            Set<Integer> secondCluster = itemClusters[second];
            cluster.addAll(secondCluster);
            for (int c : secondCluster) {
                itemClusters[c] = cluster;
            }
        }
    }

    private void makeUnique() {
        for (Set<Integer> s : itemClusters) {
            boolean found = false;
            for (Set<Integer> c : clusters) {
                if (c == s) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                clusters.add(s);
            }
        }
    }

    private final int count;
    private final Criteria criteria;

    private final Set<Integer>[] itemClusters;
    private final List<Set<Integer>> clusters = new ArrayList<>();
}
