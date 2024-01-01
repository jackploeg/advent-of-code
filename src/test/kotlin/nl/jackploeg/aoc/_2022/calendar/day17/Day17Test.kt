package nl.jackploeg.aoc._2022.calendar.day17

import javax.inject.Inject

import nl.jackploeg.aoc._2022.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day17Test {
  @Inject
  lateinit var day17: Day17

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay17PartOneTestInput() {
    assertEquals(3068, day17.partOne(DAY_17_TEST))
  }

  @Test
  fun testDay17PartTwoTestInput() {
    assertEquals(1514285714288, day17.partTwo(DAY_17_TEST))
  }

  @Test
  fun testDay17PartOne() {
    assertEquals(3227, day17.partOne(DAY_17))
  }

  @Test
  fun testDay17PartTwo() {
    assertEquals(1597714285698, day17.partTwo(DAY_17))
  }

  companion object {
    private const val DAY_17: String = "advent-of-code-input/2022/day17.input"

    private const val DAY_17_TEST: String = "advent-of-code-input/2022/day17.test"
  }
}
