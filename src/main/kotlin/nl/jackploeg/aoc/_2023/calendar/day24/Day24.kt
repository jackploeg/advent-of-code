package nl.jackploeg.aoc._2023.calendar.day24

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile
import org.ejml.simple.SimpleMatrix
import java.io.File
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import javax.inject.Inject

class Day24 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun partOne(fileName: String, areaMin: Long, areaMax: Long): Long {
    val input = readStringFile(fileName)
    val hailstones = input.map { Hailstone.new(it) }
    return pathsCrossing(hailstones, areaMin, areaMax)
  }

  fun pathsCrossing(hailstones: List<Hailstone>, areaMin: Long, areaMax: Long): Long =
    hailstones.take(hailstones.size - 1).mapIndexed { i1, h1 ->
      hailstones.subList(i1 + 1, hailstones.size).map { h2 ->
        if (pathsXYCrossInRegionInTheFuture(h1, h2, areaMin, areaMax)) 1L else 0L
      }.sum()
    }.sum()


  fun pathsXYCrossInRegionInTheFuture(
    h1: Hailstone,
    h2: Hailstone,
    areaMin: Long? = null,
    areaMax: Long? = null
  ): Boolean {
    // paths cross if there are s and t where
    // (h1.x, h1.y) + s*(h1.sx, h1.sy) == (h2.x, h2.y) + t*(h2.sx, h2.sy)
    // t = (h1.y * h1.sx + h2.x*h1.sy - h1.x * h1.sy - h2.y*h1.sx) / (h2.sy * h1.sx - h1.sy * h2.sx)

    val crossNumerator = (h1.y * h1.sx + h2.x * h1.sy - h1.x * h1.sy - h2.y * h1.sx).toDouble()
    val crossDenominator = (h2.sy * h1.sx - h1.sy * h2.sx).toDouble()

    val cross = crossNumerator / crossDenominator

    val crossX = h2.x + cross * h2.sx
    val crossY = h2.y + cross * h2.sy

    if ((h1.x < crossX && h1.sx < 0) ||
      (h1.x > crossX && h1.sx > 0) ||
      (h1.y < crossY && h1.sy < 0) ||
      (h1.y > crossY && h1.sy > 0) ||
      (h2.x < crossX && h2.sx < 0) ||
      (h2.x > crossX && h2.sx > 0) ||
      (h2.y < crossY && h2.sy < 0) ||
      (h2.y > crossY && h2.sy > 0)
    ) {
      // cross in the past
      return false
    }

    //println("at $cross :$h1 's path crosses $h2 's at $crossX, $crossY")

    // check if it's in the area, if requested
    return (areaMax == null || areaMin == null) ||
            crossX.compareTo(areaMax.toDouble()) <= 0 &&
            crossX.compareTo(areaMin.toDouble()) >= 0 &&
            crossY.compareTo(areaMax.toDouble()) <= 0 &&
            crossY.compareTo(areaMin.toDouble()) >= 0

  }

  data class Hailstone(val x: Long, val y: Long, val z: Long, val sx: Long, val sy: Long, val sz: Long) {

    lateinit var start: Point1
    val trajectory = mutableListOf<Point1>()

    init {
      start = Point1(x, y, z)
    }

    fun move(steps: Int) =
      Point1(x + steps * sx, y + steps * sy, z + steps * sz)

    fun fillTrajectory(steps: Int) {
      (0..steps).forEach { step ->
        trajectory.add(Point1(start.x + step * sx, start.y + step * sy, start.z + step * sz))
      }
    }

    companion object {
      fun new(string: String): Hailstone {
        val (pos, speed) = string.split('@').map { it.trim() }
        val (x, y, z) = pos.split(',').map { it.trim().toLong() }
        val (sx, sy, sz) = speed.split(',').map { it.trim().toLong() }
        return Hailstone(x, y, z, sx, sy, sz)
      }
    }
  }

  data class Point1(val x: Long, val y: Long, val z: Long) {
    fun add(p2: Point1): Point1 = Point1(x + p2.x, y + p2.y, z + p2.z)
  }

  data class Point(val x: BigDecimal, val y: BigDecimal, val z: BigDecimal) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y, z + other.z)

    operator fun minus(other: Point) = Point(x - other.x, y - other.y, z - other.z)

    fun inLimit() = x in BOTTOM_LIMIT..UPPER_LIMIT && y in BOTTOM_LIMIT..UPPER_LIMIT

    companion object {
      val BOTTOM_LIMIT = BigDecimal("200000000000000")
      val UPPER_LIMIT = BigDecimal("400000000000000")
    }
  }

  fun distance(p: Point, q: Point): BigDecimal =
    ((p.x - q.x) * (p.x - q.x) + (p.y - q.y) * (p.y - q.y)).sqrt(MathContext(10))

  fun det(a: BigDecimal, b: BigDecimal, c: BigDecimal, d: BigDecimal) = a * d - b * c

  data class Direction(val x: Int, val y: Int)

  fun direction(p: Point, q: Point) = Direction((p.x - q.x).signum(), (p.y - q.y).signum())

  class Line(val p: Point, val velocityPoint: Point) {
    private val a: BigDecimal
    private val b: BigDecimal
    private val c: BigDecimal
    private val direction: Direction
    private val velocity: BigDecimal

    init {
      val q = p + velocityPoint
      a = p.y - q.y
      b = q.x - p.x
      c = -a * p.x - b * p.y
      direction = direction(p, q)
      velocity = distance(p, velocityPoint)
    }

    fun direction(p: Point, q: Point) = Direction((p.x - q.x).signum(), (p.y - q.y).signum())
    fun det(a: BigDecimal, b: BigDecimal, c: BigDecimal, d: BigDecimal) = a * d - b * c
    fun distance(p: Point, q: Point): BigDecimal =
      ((p.x - q.x) * (p.x - q.x) + (p.y - q.y) * (p.y - q.y)).sqrt(MathContext(10))

    private infix fun parallel(other: Line) = det(a, b, other.a, other.b).abs() < EPS

    private fun intersect(other: Line): Point {
      val zn = det(a, b, other.a, other.b)
      return Point(-det(c, b, other.c, other.b) / zn, -det(a, c, other.a, other.c) / zn, BigDecimal.ZERO)
    }

    infix fun collide(other: Line): Boolean {
      if (this parallel other) return false
      val intersection = intersect(other)
      return intersection.inLimit() &&
              direction == direction(p, intersection) && other.direction == direction(other.p, intersection)
    }

    override fun toString(): String {
      return "p = $p, a = $a, b = $b, c = $c"
    }

    companion object {
      val EPS = BigDecimal("0.000000000001")
    }
  }

  fun intersect(line0: Line, line1: Line, line2: Line): Pair<Point, BigDecimal> {
    val p1 = line1.p - line0.p
    val p2 = line2.p - line0.p
    val v1 = line1.velocityPoint - line0.velocityPoint
    val v2 = line2.velocityPoint - line0.velocityPoint
    val matrix = SimpleMatrix(
      arrayOf(
        doubleArrayOf(p1.x.toDouble(), v1.x.toDouble(), -v2.x.toDouble()),
        doubleArrayOf(p1.y.toDouble(), v1.y.toDouble(), -v2.y.toDouble()),
        doubleArrayOf(p1.z.toDouble(), v1.z.toDouble(), -v2.z.toDouble())
      )
    )
    val vector = SimpleMatrix(
      arrayOf(
        doubleArrayOf(p2.x.toDouble()),
        doubleArrayOf(p2.y.toDouble()),
        doubleArrayOf(p2.z.toDouble())
      )
    )
    val result = matrix.invert().mult(vector)
    val t2 = BigDecimal.valueOf(result.get(2, 0))
    return Point(
      (line2.p.x + t2 * line2.velocityPoint.x).setScale(0, RoundingMode.HALF_UP),
      (line2.p.y + t2 * line2.velocityPoint.y).setScale(0, RoundingMode.HALF_UP),
      (line2.p.z + t2 * line2.velocityPoint.z).setScale(0, RoundingMode.HALF_UP)
    ) to t2.setScale(0, RoundingMode.HALF_UP)
  }

  fun partTwo(fileName: String): BigDecimal {
    val lines = File(fileName).useLines { it.toList() }.map { parseLine(it) }
    val (p1, t1) = intersect(lines[0], lines[1], lines[2])
    val (p2, t2) = intersect(lines[0], lines[1], lines[3])
    val vx = (p2.x - p1.x) / (t2 - t1)
    val vy = (p2.y - p1.y) / (t2 - t1)
    val vz = (p2.z - p1.z) / (t2 - t1)
    val x = p1.x - t1 * vx
    val y = p1.y - t1 * vy
    val z = p1.z - t1 * vz

    println("Rock start: $x, $y, $z, velocity: $vx, $vy, $vz")
    return (x + y + z)
  }

  fun parseLine(line: String): Line {
    fun parsePoint(pointString: String) =
      pointString.split(", ").map { BigDecimal.valueOf(it.trim().toLong()) }.let { (x, y, z) -> Point(x, y, z) }
    return line.split(" @ ").map { parsePoint(it) }.let { (p1, velocity) -> Line(p1, velocity) }
  }

}
