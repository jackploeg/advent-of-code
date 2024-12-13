package nl.jackploeg.aoc._2024.calendar.day12

import javax.inject.Inject

import nl.jackploeg.aoc._2024.DaggerTestDayComponent
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
    assertEquals(140, day12.partOne(DAY_12_TEST))
  }

  @Test
  fun testDay12PartOneTestaInput() {
    assertEquals(772, day12.partOne(DAY_12_TESTA))
  }

  @Test
  fun testDay12PartOneTestbInput() {
    assertEquals(1930, day12.partOne(DAY_12_TESTB))
  }

  @Test
  fun testDay12PartTwoTestInput() {
    assertEquals(80, day12.partTwo(DAY_12_TEST))
  }

  @Test
  fun testDay12PartTwoTestaInput() {
    assertEquals(436, day12.partTwo(DAY_12_TESTA))
  }

  @Test
  fun testDay12PartTwoTestbInput() {
    assertEquals(1206, day12.partTwo(DAY_12_TESTB))
  }

  @Test
  fun testDay12PartTwoTestcInput() {
    assertEquals(236, day12.partTwo(DAY_12_TESTC))
  }

  @Test
  fun testDay12PartTwoTestdInput() {
    assertEquals(368, day12.partTwo(DAY_12_TESTD))
  }

  @Test
  fun testDay12PartOne() {
    assertEquals(1304764, day12.partOne(DAY_12))
  }

  @Test
  fun testDay12PartTwo() {
    assertEquals(811148, day12.partTwo(DAY_12))
  }

  companion object {
    private const val DAY_12: String = "advent-of-code-input/2024/day12.input"

    private const val DAY_12_TEST: String = "advent-of-code-input/2024/day12.test"
    private const val DAY_12_TESTA: String = "advent-of-code-input/2024/day12.testa"
    private const val DAY_12_TESTB: String = "advent-of-code-input/2024/day12.testb"
    private const val DAY_12_TESTC: String = "advent-of-code-input/2024/day12.testc"
    private const val DAY_12_TESTD: String = "advent-of-code-input/2024/day12.testd"
  }
}
