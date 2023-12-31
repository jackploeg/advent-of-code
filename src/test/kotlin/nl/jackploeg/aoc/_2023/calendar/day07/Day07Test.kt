package nl.jackploeg.aoc._2023.calendar.day07

import javax.inject.Inject


import nl.jackploeg.aoc._2023.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day07Test {
  @Inject
  lateinit var day07: Day07

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay07PartOneTestInput() {
    assertEquals(6440, day07.partOne(DAY_07_TEST))
  }

  @Test
  fun testDay07PartTwoTestInput() {
  assertEquals(5905, day07.partTwo(DAY_07_TEST))
  }

  @Test
  fun testDay07PartOne() {
    assertEquals(248836197, day07.partOne(DAY_07))
  }

  @Test
  fun testDay07PartTwo() {
    assertEquals(251195607, day07.partTwo(DAY_07))
  }

  companion object {
    private const val DAY_07: String = "advent-of-code-input/2023/day07.input"
    private const val DAY_07_TEST: String = "advent-of-code-input/2023/day07.test"
  }
}
