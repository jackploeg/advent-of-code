package nl.jackploeg.aoc._2023.calendar.day12

import javax.inject.Inject


import nl.jackploeg.aoc._2023.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day12Test {
  @Inject
  lateinit var day12: Day12

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay12PartOneTestInput() {
    assertEquals(21, day12.countValidPermutationsInFile(DAY_12_TEST,1))
  }

  @Test
  fun testDay12PartTwoTestInput() {
    assertEquals(525152, day12.countValidPermutationsInFile(DAY_12_TEST,5))
  }

  @Test
  fun testDay12PartOne() {
    assertEquals(7344, day12.countValidPermutationsInFile(DAY_12,1))
  }

  @Test
  fun testDay12PartTwo() {
    assertEquals(1088006519007, day12.countValidPermutationsInFile(DAY_12,5))
  }

  companion object {
    private const val DAY_12: String = "advent-of-code-input/2023/day12.input"
    private const val DAY_12_TEST: String = "advent-of-code-input/2023/day12.test"
  }
}
