package nl.jackploeg.aoc._2024.calendar.day17

import nl.jackploeg.aoc._2024.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

internal class Day17Test {
  @Inject
  lateinit var day17: Day17

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay17PartOneTestInput() {
    assertEquals("4,6,3,5,6,3,5,2,1,0", day17.partOne(DAY_17_TEST))
  }

  @Test
  fun testDay17PartOne() {
    assertEquals("2,0,1,3,4,0,2,1,7", day17.partOne(DAY_17))
  }

  @Test
  fun testDay17PartTwo() {
    assertEquals(236580836040301, day17.partTwo(DAY_17))
  }

  companion object {
    private const val DAY_17: String = "advent-of-code-input/2024/day17.input"
    private const val DAY_17_TEST: String = "advent-of-code-input/2024/day17.test"
  }
}
