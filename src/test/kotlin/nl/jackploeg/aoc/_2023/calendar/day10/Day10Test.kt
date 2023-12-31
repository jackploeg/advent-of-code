package nl.jackploeg.aoc._2023.calendar.day10

import javax.inject.Inject


import nl.jackploeg.aoc._2023.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day10Test {
  @Inject
  lateinit var day10: Day10

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay10PartOneTestInput() {
    assertEquals(4, day10.partOne(DAY_10_TEST))
  }

  @Test
  fun testDay10PartTwoTestInput() {
    assertEquals(1, day10.partTwo(DAY_10_TEST))
  }

  @Test
  fun testDay10PartTwoTestInput2() {
    assertEquals(4, day10.partTwo(DAY_10_TEST2))
  }

  @Test
  fun testDay10PartTwoTestInput3() {
    assertEquals(8, day10.partTwo(DAY_10_TEST3))
  }

  @Test
  fun testDay10PartTwoTestInput4() {
    assertEquals(10, day10.partTwo(DAY_10_TEST4))
  }

  @Test
  fun testDay10PartOne() {
    assertEquals(6979, day10.partOne(DAY_10))
  }

  @Test
  fun testDay10PartTwo() {
    assertEquals(443, day10.partTwo(DAY_10))
  }

  companion object {
    private const val DAY_10: String = "advent-of-code-input/2023/day10.input"
    private const val DAY_10_TEST: String = "advent-of-code-input/2023/day10.test"
    private const val DAY_10_TEST2: String = "advent-of-code-input/2023/day10.test.2"
    private const val DAY_10_TEST3: String = "advent-of-code-input/2023/day10.test.3"
    private const val DAY_10_TEST4: String = "advent-of-code-input/2023/day10.test.4"
  }
}
