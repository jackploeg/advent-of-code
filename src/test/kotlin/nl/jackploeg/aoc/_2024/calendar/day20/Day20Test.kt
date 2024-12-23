package nl.jackploeg.aoc._2024.calendar.day20

import javax.inject.Inject

import nl.jackploeg.aoc._2024.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day20Test {
  @Inject
  lateinit var day20: Day20

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay20PartOneTestInput() {
    assertEquals(44, day20.partOne(DAY_20_TEST, 1))
  }

  @Test
  fun testDay20PartTwoTestInput() {
    assertEquals(285, day20.partTwo(DAY_20_TEST, 50))
  }

  @Test
  fun testDay20PartOne() {
    assertEquals(1389, day20.partOne(DAY_20, 100))
  }

  @Test
  fun testDay20PartTwo() {
    assertEquals(1005068L, day20.partTwo(DAY_20, 100))
  }

  companion object {
    private const val DAY_20: String = "advent-of-code-input/2024/day20.input"

    private const val DAY_20_TEST: String = "advent-of-code-input/2024/day20.test"
  }
}
