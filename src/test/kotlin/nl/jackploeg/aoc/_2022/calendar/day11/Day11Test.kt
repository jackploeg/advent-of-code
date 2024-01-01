package nl.jackploeg.aoc._2022.calendar.day11

import javax.inject.Inject

import nl.jackploeg.aoc._2022.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigInteger

internal class Day11Test {
  @Inject
  lateinit var day11: Day11

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay11PartOneTestInput() {
    assertEquals(BigInteger.valueOf(10605), day11.partOne(DAY_11_TEST))
  }

  @Test
  fun testDay11PartTwoTestInput() {
    assertEquals(BigInteger.valueOf(2713310158), day11.partTwo(DAY_11_TEST))
  }

  @Test
  fun testDay11PartOne() {
    assertEquals(BigInteger.valueOf(78960), day11.partOne(DAY_11))
  }

  @Test
  fun testDay11PartTwo() {
    assertEquals(BigInteger.valueOf(14561971968), day11.partTwo(DAY_11))
  }

  companion object {
    private const val DAY_11: String = "advent-of-code-input/2022/day11.input"

    private const val DAY_11_TEST: String = "advent-of-code-input/2022/day11.test"
  }
}
