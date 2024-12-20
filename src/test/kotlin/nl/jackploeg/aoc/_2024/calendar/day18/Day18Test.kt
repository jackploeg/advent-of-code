package nl.jackploeg.aoc._2024.calendar.day18

import javax.inject.Inject

import nl.jackploeg.aoc._2024.DaggerTestDayComponent
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
    assertEquals(22, day18.partOne(DAY_18_TEST, 7, 12))
  }

  @Test
  fun testDay18PartTwoTestInput() {
    assertEquals(Day18.Byte(6,1), day18.partTwo(DAY_18_TEST, 7, 12))
  }

  @Test
  fun testDay18PartOne() {
    assertEquals(316, day18.partOne(DAY_18, 71,1024))
  }

  @Test
  fun testDay18PartTwo() {
    assertEquals(Day18.Byte(45,18), day18.partTwo(DAY_18,71,1024))
  }

  companion object {
    private const val DAY_18: String = "advent-of-code-input/2024/day18.input"

    private const val DAY_18_TEST: String = "advent-of-code-input/2024/day18.test"
  }
}
