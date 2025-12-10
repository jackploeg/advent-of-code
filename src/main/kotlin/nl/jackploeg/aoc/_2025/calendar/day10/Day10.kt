package nl.jackploeg.aoc._2025.calendar.day10

import com.microsoft.z3.Context
import com.microsoft.z3.IntNum
import javax.inject.Inject
import kotlin.Int
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.extractInts
import nl.jackploeg.aoc.utilities.readStringFile

class Day10 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String): Int =
        generatorFactory.forFile(filename).readAs(::day10) { machine ->
            machine
        }.map { machine ->
            var initialState = Pair(".".repeat(machine.targetIndicators.length), 0)
            val queue = ArrayDeque(listOf(initialState))
            var minimalStep = 0
            while (!queue.isEmpty() && minimalStep == 0) {
                val (state, step) = queue.removeFirst()
                machine.buttons.forEachIndexed() { index, button ->
                    var newState = state.toCharArray()
                    button.flips.forEach { pos ->
                        newState[pos] = when (newState[pos]) {
                            '.' -> '#'
                            else -> '.'
                        }
                    }
                    val result = newState.map { it.toString() }.reduce(String::plus)
                    if (result == machine.targetIndicators) {
                        minimalStep = step + 1
                    }
                    queue.add(Pair(result, step + 1))
                }
            }
            minimalStep
        }.sum()

    fun partTwo(filename: String): Int {
        val lines = readStringFile(filename)

        return lines.sumOf { line ->
            Context().use { ctx ->
                val buttons = line.substringAfter("] ").substringBefore(" {")
                    .split(" ").mapIndexed { index, button -> button.extractInts() to ctx.mkIntConst("$index") }
                val joltageTarget = line.substringAfter("{").substringBefore("}").extractInts()
                val buttonConstants = buttons.map { (_, constant) -> constant }
                val optimize = ctx.mkOptimize()
                optimize.MkMinimize(ctx.mkAdd(*buttonConstants.toTypedArray()))
                joltageTarget.withIndex().forEach { (index, target) ->
                    val matchingButtons = buttons.filter { index in it.first }.map { it.second }
                    optimize.Add(ctx.mkEq(ctx.mkAdd(*matchingButtons.toTypedArray()), ctx.mkInt(target)))
                }
                buttonConstants.forEach { optimize.Add(ctx.mkGe(it, ctx.mkInt(0))) }
                optimize.Check()
                optimize.model.decls.sumOf { (optimize.model.eval(it.apply(), true) as IntNum).int }
            }
        }

    }

    private fun day10(line: String): Machine {
        val parts = line.split(" ")
        val targetIndicators = parts[0].drop(1).dropLast(1)

        val buttons = mutableListOf<Button>()
        var joltageRequirements = mutableListOf<Int>()

        for (part in parts.drop(1)) {
            if (part.startsWith("(")) {
                val lights = part.drop(1).dropLast(1).split(",").map { it.toInt() }
                buttons.add(Button(lights))
            }
            if (part.startsWith("{")) {
                val values = part.drop(1).dropLast(1).split(",").map { it.toInt() }
                joltageRequirements.addAll(values)
            }
        }

        return Machine(targetIndicators, buttons, joltageRequirements)

    }

    data class Button(val flips: List<Int>)

    data class Machine(val targetIndicators: String, val buttons: List<Button>, val joltageRequirements: List<Int>)
}
