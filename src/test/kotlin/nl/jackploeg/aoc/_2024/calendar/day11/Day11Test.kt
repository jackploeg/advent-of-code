package nl.jackploeg.aoc._2024.calendar.day11

import javax.inject.Inject

import nl.jackploeg.aoc._2024.DaggerTestDayComponent
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
    assertEquals(55312, day11.partOne(DAY_11_TEST))
  }

  @Test
  fun testDay11PartTwoTestInput() {
    assertEquals(65601038650482, day11.partTwo(DAY_11_TEST))
  }

  @Test
  fun testDay11PartOne() {
    assertEquals(189547, day11.partOne(DAY_11))
  }

  @Test
  fun testDay11PartTwo() {
    assertEquals(224577979481346L, day11.partTwo(DAY_11))
  }

  companion object {
    private const val DAY_11: String = "advent-of-code-input/2024/day11.input"

    private const val DAY_11_TEST: String = "advent-of-code-input/2024/day11.test"
  }
}
