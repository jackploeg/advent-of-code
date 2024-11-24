package nl.jackploeg.aoc._2022

import dagger.Component
import nl.jackploeg.aoc.DayComponent
import nl.jackploeg.aoc.InputModule
import nl.jackploeg.aoc._2022.calendar.day01.Day01Test
import nl.jackploeg.aoc._2022.calendar.day02.Day02Test
import nl.jackploeg.aoc._2022.calendar.day03.Day03Test
import nl.jackploeg.aoc._2022.calendar.day04.Day04Test
import nl.jackploeg.aoc._2022.calendar.day05.Day05Test
import nl.jackploeg.aoc._2022.calendar.day06.Day06Test
import nl.jackploeg.aoc._2022.calendar.day07.Day07Test
import nl.jackploeg.aoc._2022.calendar.day08.Day08Test
import nl.jackploeg.aoc._2022.calendar.day09.Day09Test
import nl.jackploeg.aoc._2022.calendar.day10.Day10Test
import nl.jackploeg.aoc._2022.calendar.day11.Day11Test
import nl.jackploeg.aoc._2022.calendar.day12.Day12Test
import nl.jackploeg.aoc._2022.calendar.day13.Day13Test
import nl.jackploeg.aoc._2022.calendar.day14.Day14Test
import nl.jackploeg.aoc._2022.calendar.day15.Day15Test
import nl.jackploeg.aoc._2022.calendar.day16.Day16Test
import nl.jackploeg.aoc._2022.calendar.day17.Day17Test
import nl.jackploeg.aoc._2022.calendar.day18.Day18Test
import nl.jackploeg.aoc._2022.calendar.day19.Day19Test
import nl.jackploeg.aoc._2022.calendar.day20.Day20Test
import nl.jackploeg.aoc._2022.calendar.day21.Day21Test
import nl.jackploeg.aoc._2022.calendar.day22.Day22Test
import nl.jackploeg.aoc._2022.calendar.day23.Day23Test
import nl.jackploeg.aoc._2022.calendar.day24.Day24Test
import nl.jackploeg.aoc._2022.calendar.day25.Day25Test
import javax.inject.Singleton

@Singleton
@Component(modules = [InputModule::class])
internal interface TestDayComponent : DayComponent {
  fun inject(day01Test: Day01Test)
  fun inject(day02Test: Day02Test)
  fun inject(day03Test: Day03Test)
  fun inject(day04Test: Day04Test)
  fun inject(day05Test: Day05Test)
  fun inject(day06Test: Day06Test)
  fun inject(day07Test: Day07Test)
  fun inject(day08Test: Day08Test)
  fun inject(day09Test: Day09Test)
  fun inject(day10Test: Day10Test)
  fun inject(day11Test: Day11Test)
  fun inject(day12Test: Day12Test)
  fun inject(day13Test: Day13Test)
  fun inject(day14Test: Day14Test)
  fun inject(day15Test: Day15Test)
  fun inject(day16Test: Day16Test)
  fun inject(day17Test: Day17Test)
  fun inject(day18Test: Day18Test)
  fun inject(day19Test: Day19Test)
  fun inject(day20Test: Day20Test)
  fun inject(day21Test: Day21Test)
  fun inject(day22Test: Day22Test)
  fun inject(day23Test: Day23Test)
  fun inject(day24Test: Day24Test)
  fun inject(day25Test: Day25Test)
}