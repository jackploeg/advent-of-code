package nl.jackploeg.aoc._2024

import dagger.Component
import nl.jackploeg.aoc._2024.calendar.day03.Day03Test
import nl.jackploeg.aoc._2024.calendar.day02.Day02Test
import nl.jackploeg.aoc._2024.calendar.day04.Day04Test
import nl.jackploeg.aoc._2024.calendar.day06.Day06Test
import nl.jackploeg.aoc._2024.calendar.day07.Day07Test
import nl.jackploeg.aoc._2024.calendar.day08.Day08Test
import nl.jackploeg.aoc._2024.calendar.day09.Day09Test
import nl.jackploeg.aoc._2024.calendar.day10.Day10Test
import nl.jackploeg.aoc._2024.calendar.day11.Day11Test
import javax.inject.Singleton
import nl.jackploeg.aoc.DayComponent
import nl.jackploeg.aoc.InputModule
import nl.jackploeg.aoc._2024.calendar.day01.Day01Test
import nl.jackploeg.aoc._2024.calendar.day05.Day05Test

@Singleton
@Component(modules = [InputModule::class])
internal interface TestDayComponent : DayComponent {
  fun inject(day01Test: Day01Test)
  fun inject(day03Test: Day03Test)
  fun inject(day02Test: Day02Test)
  fun inject(day04Test: Day04Test)
  fun inject(day05Test: Day05Test)
  fun inject(day06Test: Day06Test)
  fun inject(day07Test: Day07Test)
  fun inject(day08Test: Day08Test)
  fun inject(day09Test: Day09Test)
  fun inject(day10Test: Day10Test)
  fun inject(day11Test: Day11Test)
}
