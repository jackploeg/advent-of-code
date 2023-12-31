package nl.jackploeg.aoc._2023.calendar.day05

import javax.inject.Inject


import nl.jackploeg.aoc._2023.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day05Test {
  @Inject
  lateinit var day05: Day05

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay05PartOneTestInput() {
    assertEquals(35, day05.partOne(DAY_05_TEST).toInt())
  }

  @Test
  fun testDay05PartTwoTestInput() {
    assertEquals(46, day05.partTwo(DAY_05_TEST).toInt())
  }

  @Test
  fun testDay05PartOne() {
    assertEquals(175622908, day05.partOne(DAY_05).toInt())
  }

  @Test
  fun testDay05PartTwo() {
    assertEquals(5200543, day05.partTwo(DAY_05).toInt())
  }

  companion object {
    private const val DAY_05: String = "advent-of-code-input/2023/day05.input"
    private const val DAY_05_TEST: String = "advent-of-code-input/2023/day05.test"
  }
}
