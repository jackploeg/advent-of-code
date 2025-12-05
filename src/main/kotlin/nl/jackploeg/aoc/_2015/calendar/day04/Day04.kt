package nl.jackploeg.aoc._2015.calendar.day04

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.md5
import javax.inject.Inject

class Day04 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String): Int =
        generatorFactory.forFile(filename).readOne { input ->
            var num = 0
            var strNum = "$input$num"
            while (!strNum.md5().startsWith("00000")) {
                num++
                strNum = "$input$num"
            }
            num
        }

    fun partTwo(filename: String): Int =
        generatorFactory.forFile(filename).readOne { input ->
            var num = 0
            var strNum = "$input$num"
            while (!strNum.md5().startsWith("000000")) {
                num++
                strNum = "$input$num"
            }
            num
        }

}
