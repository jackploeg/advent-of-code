package nl.jackploeg.aoc._2022.calendar.day21

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile
import java.lang.RuntimeException
import kotlin.math.absoluteValue

class Day21 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
    // get root monkey number
    fun partOne(fileName: String): Long? {
        val input = readStringFile(fileName)
        val monkeys: MutableMap<String, Monkey> = mutableMapOf()
        input.map { val monkey = mapMonkey(it); monkeys.put(monkey.name, monkey) }
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

    // try root equality numbers
    fun partTwo(fileName: String, start: Long, end: Long): Long {
        val input = readStringFile(fileName)
        val monkeys: MutableMap<String, Monkey> = mutableMapOf()

        for (guess in start..end) {
            input.map { val monkey = mapMonkey(it); monkeys.put(monkey.name, monkey) }

            val rootMonkey = monkeys.get("root")!!
            rootMonkey.operation = Operation.MINUS

            val humnMonkey = monkeys.get("humn")!!

            humnMonkey.number = guess

            while (rootMonkey.number == null) {
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

            if (rootMonkey.number == 0L) {
                println("Human says: $guess, rootmonkey numbers match!")
                println("      ${rootMonkey.number}")
                return guess
            }
        }
        return -1
    }

    fun partTwoV2(fileName: String): Long {
        val input = readStringFile(fileName)
        val monkeys: MutableMap<String, Monkey> = mutableMapOf()

        var guess = 100000000000000L
        for (i in 1..8000) {
            input.map { val monkey = mapMonkey(it); monkeys.put(monkey.name, monkey) }

            val rootMonkey = monkeys.get("root")!!
            rootMonkey.operation = Operation.MINUS

            val humnMonkey = monkeys.get("humn")!!

            humnMonkey.number = guess

            while (rootMonkey.number == null) {
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

            if (rootMonkey.number!!.absoluteValue < 20L) {
                guess = guess + if (rootMonkey.number!! < 0) 1 else -1
            } else if (rootMonkey.number!!.absoluteValue < 200L) {
                guess = guess + rootMonkey.number!! / 10
            } else {
                guess = guess + rootMonkey.number!! / 100
            }

            if (rootMonkey.number == 0L) {
                println("Human says: $guess, rootmonkey numbers match!")
                println("      ${rootMonkey.number}")
                return guess
            }
        }
        return -1
    }

    fun getMonkeySources(level: Int, monkeys: MutableMap<String, Monkey>, monkey: Monkey?) {
        if (monkey?.number != null) {
            println(" ".repeat(level) + "${monkey.name} has number: ${monkey.number}")
            return
        } else {
            println(" ".repeat(level) + "${monkey?.name} has reference  ${monkey?.monkey1} and ${monkey?.monkey2}")
            getMonkeySources(level + 1, monkeys, monkeys.get(monkey?.monkey1))
            getMonkeySources(level + 1, monkeys, monkeys.get(monkey?.monkey2))
        }

    }

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
    }}
