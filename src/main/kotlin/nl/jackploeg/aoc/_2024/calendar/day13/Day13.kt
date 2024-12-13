package nl.jackploeg.aoc._2024.calendar.day13

import java.io.File
import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory

class Day13 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String): Long {
        val clawMachines: MutableList<ClawMachine> = readClawMachines(filename)
        return clawMachines.map { it.clickButtonsToWin()?.let { it.first * 3 + it.second} }.mapNotNull { it }.sum()
    }

    fun partTwo(filename: String): Long {
        val clawMachines: MutableList<ClawMachine> = readClawMachines(filename, 10000000000000L)
        return clawMachines.map { it.clickButtonsToWin()?.let { it.first * 3 + it.second} }.mapNotNull { it }.sum()
    }

    private fun readClawMachines(filename: String, addToPrize: Long = 0L): MutableList<ClawMachine> {
        val input = File(filename).readLines()
        val clawMachines: MutableList<ClawMachine> = mutableListOf()
        var i = 0
        while (i < input.size) {
            val buttonA = processLine(input[i])
            val buttonB = processLine(input[i + 1])
            val prize = processLine(input[i + 2])
            clawMachines.add(ClawMachine(buttonA, buttonB, Position(prize.x + addToPrize, prize.y + addToPrize)))
            i += 4
        }
        return clawMachines
    }

    fun processLine(line: String): Position {
        val (name, numbers) = line.split(':')
        val (xval, yval) = numbers.split(',')
        if (name.startsWith("Button")) {
            val x = xval.split("+")[1].toLong()
            val y = yval.split("+")[1].toLong()
            return Position(x, y)
        }
        val x = xval.split("=")[1].toLong()
        val y = yval.split("=")[1].toLong()
        return Position(x, y)
    }

    data class Position(val x: Long, val y: Long)

    data class ClawMachine(val buttonA: Position, val buttonB: Position, val prize: Position) {
        fun clickButtonsToWin(): Pair<Long, Long>? {
            /**
             * a * buttonA.x + b * buttonB.x = prize.x
             * a * buttonA.y + b * buttonB.y = prize.y
             * -> a = (prize.x - b * buttonB.x) / buttonA.x
             * -> ((prize.x - b * buttonB.x) / buttonA.x) * buttonA.y + b * buttonB.y = prize.y
             * -> buttonA.y * prize.x - b * buttonA.y * buttonB.x + b * buttonB.y * buttonA.x = prize.y * buttonA.x
             * -> b = (buttonA.x * prize.y - buttonA.y * prize.x) / (buttonA.x * buttonB.y - buttonA.y * buttonB.x)
             */
            val b = (buttonA.x * prize.y - buttonA.y * prize.x) / (buttonA.x * buttonB.y - buttonA.y * buttonB.x)
            val a = (prize.x - b * buttonB.x) / buttonA.x
            val check = buttonA.x * a + buttonB.x * b == prize.x && buttonA.y * a + buttonB.y * b == prize.y
            return if(check) Pair(a, b) else null
        }
    }
}
