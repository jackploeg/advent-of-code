package nl.jackploeg.aoc._2022.calendar.day21

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile
import javax.inject.Inject
import kotlin.math.abs

class Day21 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    // get root monkey number
    fun partOne(fileName: String): Long? {
        val monkeys = readStringFile(fileName).map { mapMonkey(it) }.associate { it.name to it }
        val rootMonkey = monkeys.get("root")
        while (rootMonkey!!.number == null) {
            for (monkey in monkeys.values.filter { it.number == null }) {
                val monkey1 = monkeys.get(monkey.monkey1)
                val monkey2 = monkeys.get(monkey.monkey2)
                if (monkey1!!.number != null && monkey2!!.number != null) {
                    when (monkey.operation) {
                        Operation.PLUS -> monkey.number = monkey1.number?.plus(monkey2.number!!)
                        Operation.MINUS -> monkey.number = monkey1.number?.minus(monkey2.number!!)
                        Operation.MULTIPLY -> monkey.number = monkey1.number?.times(monkey2.number!!)
                        Operation.DIVIDE -> monkey.number = monkey1.number?.div(monkey2.number!!)
                        else -> {}
                    }
                }

            }
        }
        return rootMonkey.number
    }

    fun partTwo(fileName: String): Long = generatorFactory.forFile(fileName).readAs(::monkeyJob) { input ->
        val monkeyJobs = mutableMapOf<String, MonkeyJob>()
        input.forEach { monkeyJobs[it.name] = it }

        var max = Long.MAX_VALUE
        var min = 0L
        var answer: PartTwoResult? = null

        while (answer == null) {
            val mid = max - (max - min) / 2

            val results = listOf(min, mid, max).map { yellValue ->
                //print("Yell: $yellValue: ")
                monkeyJobs[HUMAN_NAME] = MonkeyJob.YellNumber(HUMAN_NAME, yellValue.toDouble())
                (monkeyJobs[ROOT_NAME] as MonkeyJob.ResultOperation).let { resultOp ->
                    val xx = monkeyJobs[resultOp.first]!!.getData(monkeyJobs)
                    val yy = monkeyJobs[resultOp.second]!!.getData(monkeyJobs)
                    //println("distance: ${abs(xx - yy)}")
                    PartTwoResult(yellValue, abs(xx - yy))
                }
            }.sortedBy { it.distanceFromAnswer }

            val maybeResult = results.firstOrNull { it.distanceFromAnswer == 0.0 }
            if (maybeResult != null) {
                answer = maybeResult
            } else {
                val bestTwo = results.take(2)
                min = kotlin.math.min(bestTwo.first().yellNumber, bestTwo.last().yellNumber)
                max = kotlin.math.max(bestTwo.first().yellNumber, bestTwo.last().yellNumber)
            }
        }

        answer.yellNumber
    }

    private fun monkeyJob(line: String): MonkeyJob {
        val mainParts = line.split(": ")
        val monkeyName = mainParts[0]
        val remainingParts = mainParts[1].split(" ")
        return if (remainingParts.size == 3) {
            val firstMonkey = remainingParts[0]
            val secondMonkey = remainingParts[2]
            val operation: (Double, Double) -> Double = when (remainingParts[1]) {
                "+" -> Double::plus
                "*" -> Double::times
                "-" -> Double::minus
                "/" -> Double::div
                else -> throw IllegalArgumentException("Unknown operation ${remainingParts[1]}")
            }

            MonkeyJob.ResultOperation(monkeyName, firstMonkey, secondMonkey, operation)
        } else {
            MonkeyJob.YellNumber(monkeyName, remainingParts[0].toDouble())
        }
    }

    sealed class MonkeyJob(val name: String) {
        abstract fun getData(monkeyJobs: MutableMap<String, MonkeyJob>): Double

        class YellNumber(name: String, val number: Double) : MonkeyJob(name) {
            override fun getData(monkeyJobs: MutableMap<String, MonkeyJob>): Double = number
        }

        class ResultOperation(
            name: String,
            val first: String,
            val second: String,
            val operation: (Double, Double) -> Double
        ) : MonkeyJob(name) {
            override fun getData(monkeyJobs: MutableMap<String, MonkeyJob>): Double = operation(
                monkeyJobs[first]!!.getData(monkeyJobs),
                monkeyJobs[second]!!.getData(monkeyJobs)
            )
        }
    }

    data class PartTwoResult(val yellNumber: Long, val distanceFromAnswer: Double)

    fun mapMonkey(line: String): Monkey {
        val parts = line.split(" ")
        if (parts.size == 2) {
            return Monkey(parts[0].dropLast(1), parts[1].toLong())
        } else {
            return Monkey(parts[0].dropLast(1), null, Operation.from(parts[2][0]), parts[1], parts[3])
        }
    }

    enum class Operation {
        PLUS, MINUS, MULTIPLY, DIVIDE;

        companion object {
            fun from(char: Char): Operation {
                return when (char) {
                    '+' -> PLUS
                    '-' -> MINUS
                    '*' -> MULTIPLY
                    '/' -> DIVIDE
                    else -> throw RuntimeException()
                }
            }
        }
    }

    data class Monkey(
        val name: String,
        var number: Long?,
        var operation: Operation? = null,
        val monkey1: String? = null,
        val monkey2: String? = null
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Monkey

            if (name != other.name) return false

            return true
        }

        override fun hashCode(): Int {
            return name.hashCode()
        }
    }

    companion object {
        private const val HUMAN_NAME = "humn"
        private const val ROOT_NAME = "root"
    }

}
