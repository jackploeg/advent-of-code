package nl.jackploeg.aoc._2022.calendar.day05

import javax.inject.Inject

import nl.jackploeg.aoc._2022.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day05Test {
  @Inject
  lateinit var day05: Day05

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay05PartOneTestInput() {
    assertEquals("CMZ", day05.partOne(DAY_05_TEST))
  }

  @Test
  fun testDay05PartTwoTestInput() {
    assertEquals("MCD", day05.partTwo(DAY_05_TEST))
  }

  @Test
  fun testDay05PartOne() {
    assertEquals("VQZNJMWTR", day05.partOne(DAY_05))
  }

  @Test
  fun testDay05PartTwo() {
    assertEquals("NLCDCLVMQ", day05.partTwo(DAY_05))
  }

  companion object {
    private const val DAY_05: String = "advent-of-code-input/2022/day05.input"

    private const val DAY_05_TEST: String = "advent-of-code-input/2022/day05.test"
  }
}
