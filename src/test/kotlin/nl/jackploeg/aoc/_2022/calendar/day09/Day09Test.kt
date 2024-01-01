package nl.jackploeg.aoc._2022.calendar.day09

import javax.inject.Inject

import nl.jackploeg.aoc._2022.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day09Test {
  @Inject
  lateinit var day09: Day09

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay09PartOneTestInput() {
    assertEquals(13, day09.partOne(DAY_09_TEST))
  }

  @Test
  fun testDay09PartTwoTestInput() {
    assertEquals(1, day09.partTwo(DAY_09_TEST))
  }

  @Test
  fun testDay09PartOne() {
    assertEquals(6256, day09.partOne(DAY_09))
  }

  @Test
  fun testDay09PartTwo() {
    assertEquals(2665, day09.partTwo(DAY_09))
  }

  companion object {
    private const val DAY_09: String = "advent-of-code-input/2022/day09.input"

    private const val DAY_09_TEST: String = "advent-of-code-input/2022/day09.test"
  }
}
