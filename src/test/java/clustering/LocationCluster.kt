package clustering

import org.junit.Assert
import org.junit.Test

/**
 * Created by berezkin on 10/06/17.
 */

private val EarthRadius = 6371009.0
private val LinkageMaxDistanceMeters = 130.0

class LocationCluster {
    @Test
    fun test1() {
        Assert.assertTrue(clusterCount(
                latitudes = doubleArrayOf(35.367949, 35.367026),
                longitudes = doubleArrayOf(24.505020, 24.503913
                )) == 2)
    }

    @Test
    fun test2() {
        Assert.assertTrue(clusterCount(
                latitudes = doubleArrayOf(35.367057, 35.367070, 35.367949),
                longitudes = doubleArrayOf(24.503904, 24.505031, 24.505015
                )) == 1)
    }

    @Test
    fun test3() {
        Assert.assertTrue(clusterCount(
                latitudes = doubleArrayOf(35.367057, 35.367057, 35.368409),
                longitudes = doubleArrayOf(24.503899, 24.505567, 24.505556
                )) == 3)
    }

    private fun clusterCount(latitudes: DoubleArray, longitudes: DoubleArray): Int {
        val latCosines = DoubleArray(latitudes.size) {
            Math.cos(Math.toRadians(latitudes[it]))
        }
        val degreeStep = EarthRadius * Math.PI / 180
        val linkageDistanceDegree = LinkageMaxDistanceMeters / degreeStep
        val linkageDistanceDegree2 = linkageDistanceDegree * linkageDistanceDegree
        val pointClusters = SimpleClustering.apply(latitudes.size) { first, second ->
            val dx = (longitudes[first] - longitudes[second]) * (latCosines[first] + latCosines[second]) / 2.0
            val dy = latitudes[first] - latitudes[second]
            dx * dx + dy * dy < linkageDistanceDegree2
        }
        return pointClusters.size
    }
}
