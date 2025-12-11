package nl.jackploeg.aoc._2025.calendar.day11

import javax.inject.Inject

import nl.jackploeg.aoc._2025.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day11Test {
  @Inject
  lateinit var day11: Day11

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay11PartOneTestInput() {
    assertEquals(5, day11.partOne(DAY_11_TEST))
  }

  @Test
  fun testDay11PartTwoTestInput() {
    assertEquals(2, day11.partTwo(DAY_11_TEST2))
  }

  @Test
  fun testDay11PartOne() {
    assertEquals(470, day11.partOne(DAY_11))
  }

  @Test
  fun testDay11PartTwo() {
    assertEquals(384151614084875, day11.partTwo(DAY_11))
  }

  companion object {
    private const val DAY_11: String = "advent-of-code-input/2025/day11.input"

    private const val DAY_11_TEST: String = "advent-of-code-input/2025/day11.test"
    private const val DAY_11_TEST2: String = "advent-of-code-input/2025/day11.test2"
  }
}
