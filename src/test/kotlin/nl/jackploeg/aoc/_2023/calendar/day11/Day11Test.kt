package nl.jackploeg.aoc._2023.calendar.day11

import javax.inject.Inject


import nl.jackploeg.aoc._2023.DaggerTestDayComponent
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
    assertEquals(374, day11.findShortestPaths(DAY_11_TEST, 2))
  }

  @Test
  fun testDay11PartTwoTestInput10() {
    assertEquals(1030, day11.findShortestPaths(DAY_11_TEST,10))
  }

  @Test
  fun testDay11PartTwoTestInput100() {
    assertEquals(8410, day11.findShortestPaths(DAY_11_TEST,100))
  }

  @Test
  fun testDay11PartOne() {
    assertEquals(10077850, day11.findShortestPaths(DAY_11, 2))
  }

  @Test
  fun testDay11PartTwo() {
    assertEquals(504715068438, day11.findShortestPaths(DAY_11, 1000000))
  }

  companion object {
    private const val DAY_11: String = "advent-of-code-input/2023/day11.input"
    private const val DAY_11_TEST: String = "advent-of-code-input/2023/day11.test"
  }
}
