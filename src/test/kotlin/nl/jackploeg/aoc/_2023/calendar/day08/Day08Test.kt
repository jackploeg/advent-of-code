package nl.jackploeg.aoc._2023.calendar.day08

import javax.inject.Inject


import nl.jackploeg.aoc._2023.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day08Test {
  @Inject
  lateinit var day08: Day08

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay08PartOneTestInput() {
    assertEquals(2, day08.partOne(DAY_08_TEST))
  }

  @Test
  fun testDay08PartTwoTestInput() {
    assertEquals(6, day08.partTwo(DAY_08_TEST2).toLong())
  }

  @Test
  fun testDay08PartOne() {
    assertEquals(16409, day08.partOne(DAY_08))
  }

  @Test
  fun testDay08PartTwo() {
    assertEquals(11_795_205_644_011, day08.partTwo(DAY_08).toLong())
  }

  companion object {
    private const val DAY_08: String = "advent-of-code-input/2023/day08.input"
    private const val DAY_08_TEST: String = "advent-of-code-input/2023/day08.test"
    private const val DAY_08_TEST2: String = "advent-of-code-input/2023/day08.test.2"
  }
}
