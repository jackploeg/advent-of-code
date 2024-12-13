package nl.jackploeg.aoc._2024.calendar.day13

import javax.inject.Inject

import nl.jackploeg.aoc._2024.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day13Test {
  @Inject
  lateinit var day13: Day13

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay13PartOneTestInput() {
    assertEquals(480, day13.partOne(DAY_13_TEST))
  }

  @Test
  fun testDay13PartTwoTestInput() {
    assertEquals(875318608908, day13.partTwo(DAY_13_TEST))
  }

  @Test
  fun testDay13PartOne() {
    assertEquals(31589, day13.partOne(DAY_13))
  }

  @Test
  fun testDay13PartTwo() {
    assertEquals(98080815200063L, day13.partTwo(DAY_13))
  }

  companion object {
    private const val DAY_13: String = "advent-of-code-input/2024/day13.input"

    private const val DAY_13_TEST: String = "advent-of-code-input/2024/day13.test"
  }
}
