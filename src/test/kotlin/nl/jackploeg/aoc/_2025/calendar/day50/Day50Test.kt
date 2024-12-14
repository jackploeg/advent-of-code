package nl.jackploeg.aoc._2025.calendar.day50

import javax.inject.Inject

import nl.jackploeg.aoc._2025.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day50Test {
  @Inject
  lateinit var day50: Day50

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay50PartOneTestInput() {
    assertEquals(59, day50.partOne(DAY_50_TEST))
  }

  @Test
  fun testDay50PartTwoTestInput() {
    assertEquals(-1, day50.partTwo(DAY_50_TEST))
  }

  companion object {
    private const val DAY_50: String = "advent-of-code-input/2025/day50.input"

    private const val DAY_50_TEST: String = "advent-of-code-input/2025/day50.test"
  }
}
