package clustering

import clustering.SimpleClustering
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
        val latitudes = doubleArrayOf(0.0, 1.0, 2.0)
        val longitudes = doubleArrayOf(0.0, 1.0, 2.0)
        val normLongitudes = Array(longitudes.size) { i ->
            val latCos = Math.cos(Math.toRadians(latitudes[i]))
            longitudes[i] * latCos
        }
        val degreeStep = EarthRadius * Math.PI / 180
        val linkageDistanceDegree = LinkageMaxDistanceMeters / degreeStep
        val linkageDistanceDegree2 = linkageDistanceDegree * linkageDistanceDegree
        val pointClusters = SimpleClustering.apply(latitudes.size) { first, second ->
            val dx = normLongitudes[first] - normLongitudes[second]
            val dy = latitudes[first] - latitudes[second]
            dx * dx + dy * dy < linkageDistanceDegree2
        }
        Assert.assertTrue(pointClusters.size == 3)
    }
}
