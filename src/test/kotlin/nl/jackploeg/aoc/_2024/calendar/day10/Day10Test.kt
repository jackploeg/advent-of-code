package nl.jackploeg.aoc._2024.calendar.day10

import javax.inject.Inject

import nl.jackploeg.aoc._2024.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day10Test {
  @Inject
  lateinit var day10: Day10

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay10PartOneTestInput() {
    assertEquals(36, day10.partOne(DAY_10_TEST))
  }

  @Test
  fun testDay10PartTwoTestInput() {
    assertEquals(81, day10.partTwo(DAY_10_TEST))
  }

  @Test
  fun testDay10PartOne() {
    assertEquals(574, day10.partOne(DAY_10))
  }

  @Test
  fun testDay10PartTwo() {
    assertEquals(1238, day10.partTwo(DAY_10))
  }

  companion object {
    private const val DAY_10: String = "advent-of-code-input/2024/day10.input"

    private const val DAY_10_TEST: String = "advent-of-code-input/2024/day10.test"
  }
}
