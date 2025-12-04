package nl.jackploeg.aoc._2025.calendar.day04

import javax.inject.Inject

import nl.jackploeg.aoc._2025.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day04Test {
  @Inject
  lateinit var day04: Day04

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay04PartOneTestInput() {
    assertEquals(13, day04.partOne(DAY_04_TEST))
  }

  @Test
  fun testDay04PartTwoTestInput() {
    assertEquals(43, day04.partTwo(DAY_04_TEST))
  }

  @Test
  fun testDay04PartOne() {
    assertEquals(1349, day04.partOne(DAY_04))
  }

  @Test
  fun testDay04PartTwo() {
    assertEquals(8277, day04.partTwo(DAY_04))
  }

  companion object {
    private const val DAY_04: String = "advent-of-code-input/2025/day04.input"

    private const val DAY_04_TEST: String = "advent-of-code-input/2025/day04.test"
  }
}
