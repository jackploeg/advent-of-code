package nl.jackploeg.aoc._2023.calendar.day18

import javax.inject.Inject


import nl.jackploeg.aoc._2023.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day18Test {
  @Inject
  lateinit var day18: Day18

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay18PartOneTestInput() {
    assertEquals(62, day18.partOne(DAY_18_TEST))
  }

  @Test
  fun testDay18PartTwoTestInput() {
    assertEquals(952408144115, day18.partTwo(DAY_18_TEST))
  }

  @Test
  fun testDay18PartOne() {
    assertEquals(44436, day18.partOne(DAY_18))
  }

  @Test
  fun testDay18PartTwo() {
    assertEquals(106941819907437, day18.partTwo(DAY_18))
  }

  companion object {
    private const val DAY_18: String = "advent-of-code-input/2023/day18.input"
    private const val DAY_18_TEST: String = "advent-of-code-input/2023/day18.test"
  }
}
