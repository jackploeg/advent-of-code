package nl.jackploeg.aoc._2023.calendar.day14

import javax.inject.Inject


import nl.jackploeg.aoc._2023.DaggerTestDayComponent
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
    assertEquals(136, day14.partOne(DAY_14_TEST))
  }

  @Test
  fun testDay14PartTwoTestInput() {
    assertEquals(64, day14.partTwo(DAY_14_TEST, 1000000000))
  }

  @Test
  fun testDay14PartOne() {
    assertEquals(109638, day14.partOne(DAY_14))
  }

  @Test
  fun testDay14PartTwo() {
    assertEquals(102657, day14.partTwo(DAY_14, 1000000000))
  }

  companion object {
    private const val DAY_14: String = "advent-of-code-input/2023/day14.input"
    private const val DAY_14_TEST: String = "advent-of-code-input/2023/day14.test"
  }
}
