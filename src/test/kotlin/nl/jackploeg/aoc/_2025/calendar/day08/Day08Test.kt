package nl.jackploeg.aoc._2025.calendar.day08

import javax.inject.Inject

import nl.jackploeg.aoc._2025.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day08Test {
  @Inject
  lateinit var day08: Day08

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay08PartOneTestInput() {
    assertEquals(40, day08.partOne(DAY_08_TEST, 10))
  }

  @Test
  fun testDay08PartTwoTestInput() {
    assertEquals(25272, day08.partTwo(DAY_08_TEST))
  }

  @Test
  fun testDay08PartOne() {
    assertEquals(117000, day08.partOne(DAY_08, 1000))
  }

  @Test
  fun testDay08PartTwo() {
    assertEquals(8368033065, day08.partTwo(DAY_08))
  }

  companion object {
    private const val DAY_08: String = "advent-of-code-input/2025/day08.input"

    private const val DAY_08_TEST: String = "advent-of-code-input/2025/day08.test"
  }
}
