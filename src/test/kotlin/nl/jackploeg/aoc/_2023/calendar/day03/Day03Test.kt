package nl.jackploeg.aoc._2023.calendar.day03

import javax.inject.Inject


import nl.jackploeg.aoc._2023.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day03Test {
  @Inject
  lateinit var day03: Day03

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay03PartOneTestInput() {
    assertEquals(4361, day03.partOne(DAY_03_TEST))
  }

  @Test
  fun testDay03PartTwoTestInput() {
    assertEquals(467835, day03.partTwo(DAY_03_TEST))
  }

  @Test
  fun testDay03PartOne() {
    assertEquals(532331, day03.partOne(DAY_03))
  }

  @Test
  fun testDay03PartTwo() {
    assertEquals(82301120, day03.partTwo(DAY_03))
  }

  companion object {
    private const val DAY_03: String = "advent-of-code-input/2023/day03.input"
    private const val DAY_03_TEST: String = "advent-of-code-input/2023/day03.test"
  }
}
