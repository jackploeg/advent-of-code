package nl.jackploeg.aoc._2024.calendar.day14

import javax.inject.Inject

import nl.jackploeg.aoc._2024.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day14Test {
  @Inject
  lateinit var day14: Day14

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay14PartOneTestInput() {
    assertEquals(12, day14.partOne(DAY_14_TEST, 11,7))
  }

  @Test
  fun testDay14PartTwo() {
    assertEquals(7344, day14.partTwo(DAY_14, 101, 103))
  }

  companion object {
    private const val DAY_14: String = "advent-of-code-input/2024/day14.input"

    private const val DAY_14_TEST: String = "advent-of-code-input/2024/day14.test"
  }
}
