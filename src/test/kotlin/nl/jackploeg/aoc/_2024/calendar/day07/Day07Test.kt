package nl.jackploeg.aoc._2024.calendar.day07

import javax.inject.Inject

import nl.jackploeg.aoc._2024.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigInteger

internal class Day07Test {
  @Inject
  lateinit var day07: Day07

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay07PartOneTestInput() {
    assertEquals(BigInteger("3749"), day07.partOne(DAY_07_TEST))
  }

  @Test
  fun testDay07PartTwoTestInput() {
    assertEquals(BigInteger("11387"), day07.partTwo(DAY_07_TEST))
  }

  @Test
  fun testDay07PartOne() {
    assertEquals(BigInteger("2314935962622"), day07.partOne(DAY_07))
  }

  @Test
  fun testDay07PartTwo() {
    assertEquals(BigInteger("401477450831495"), day07.partTwo(DAY_07))
  }

  companion object {
    private const val DAY_07: String = "advent-of-code-input/2024/day07.input"

    private const val DAY_07_TEST: String = "advent-of-code-input/2024/day07.test"
  }
}
