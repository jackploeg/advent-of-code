package nl.jackploeg.aoc._2023.calendar.day15

import javax.inject.Inject


import nl.jackploeg.aoc._2023.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day15Test {
  @Inject
  lateinit var day15: Day15

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay15PartOneTestInput() {
    assertEquals(1320, day15.partOne(DAY_15_TEST))
  }

  @Test
  fun testDay15PartTwoTestInput() {
    assertEquals(145, day15.partTwo(DAY_15_TEST))
  }

  @Test
  fun testDay15PartOne() {
    assertEquals(495972, day15.partOne(DAY_15))
  }

  @Test
  fun testDay15PartTwo() {
    assertEquals(245223, day15.partTwo(DAY_15))
  }

  companion object {
    private const val DAY_15: String = "advent-of-code-input/2023/day15.input"
    private const val DAY_15_TEST: String = "advent-of-code-input/2023/day15.test"
  }
}
