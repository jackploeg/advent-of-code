package nl.jackploeg.aoc._2022.calendar.day11

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile
import java.math.BigInteger

class Day11 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String) =
        watchThrows(20, 3L, filename)

    fun partTwo(filename: String) =
        watchThrows(10000, 1L, filename)

    fun watchThrows(numberOfThrows: Int, reliefFactor: Long, fileName: String): BigInteger {
        val input = readStringFile(fileName)
        val monkeys: List<Monkey> = readMonkeys(input, reliefFactor)

        for (i in 0 until numberOfThrows) {
            monkeys.forEach { it.inspectAndThrowItems() }
        }
        println(monkeys.sortedByDescending { it.inspectCount }.map { it.inspectCount })
        return monkeys.sortedByDescending { it.inspectCount }.map { it.inspectCount }.take(2)
            .reduce { sum, x -> sum * x }

    }

    fun readMonkeys(input: List<String>, reliefFactor: Long): List<Monkey> {
        val monkeys: MutableList<Monkey> = mutableListOf()
        var monkey = Monkey(index = -1, reliefFactor = reliefFactor, monkeys = monkeys)
        for (line in input) {
            if (line.startsWith("Monkey")) {
                if (monkey.index > -1) {
                    monkey.init()
                    monkeys.add(monkey)
                }
                val index = Integer.parseInt(line.substringAfterLast(" ").substringBefore(":"))
                monkey = Monkey(index = index, reliefFactor = reliefFactor, monkeys = monkeys)
            }
            if (line.trim().startsWith("Starting items")) {
                line.substringAfter(":").split(",").forEach {
                    monkey.items.add(Item(Integer.parseInt(it.trim()).toBigInteger()))
                }
            }
            if (line.trim().startsWith("Operation")) {
                if (line.contains("+")) {
                    monkey.worryAdder = Integer.parseInt(line.substringAfterLast(" ")).toLong()
                } else if (line.contains("*")) {
                    if (line.substringAfterLast(" ") == "old") {
                        monkey.worryPower = 2
                    } else {
                        monkey.worryMultiplier = Integer.parseInt(line.substringAfterLast(" ")).toLong()
                    }
                }
            }
            if (line.trim().startsWith("Test: divisible")) {
                monkey.worryDivisor = Integer.parseInt(line.substringAfterLast(" ")).toLong()
            }
            if (line.trim().startsWith("If true:")) {
                monkey.receiverTrue = Integer.parseInt(line.substringAfterLast(" "))
            }
            if (line.trim().startsWith("If false:")) {
                monkey.receiverFalse = Integer.parseInt(line.substringAfterLast(" "))
            }
        }
        monkey.init()
        monkeys.add(monkey)

        val interestingPart: Long = monkeys.map { it.worryDivisor }.reduce { acc, l -> acc * l }
        monkeys.forEach { it.interestingPartBI = BigInteger.valueOf(interestingPart) }
        return monkeys
    }

    data class Item(var worryLevel: BigInteger)

    class Monkey(
        var index: Int,
        var worryAdder: Long = 0,
        var worryMultiplier: Long = 1L,
        var worryDivisor: Long = 1,
        var worryPower: Int = 1,
        var reliefFactor: Long,
        var interestingPartBI: BigInteger = BigInteger.valueOf(Long.MAX_VALUE),
        var receiverFalse: Int = 0,
        var receiverTrue: Int = 0,
        val monkeys: List<Monkey>,
        val items: ArrayDeque<Item> = ArrayDeque()
    ) {
        var inspectCount = BigInteger.ZERO
        private lateinit var worryMultiplierBI: BigInteger
        private lateinit var worryAdderBI: BigInteger
        private lateinit var reliefFactorBI: BigInteger
        private lateinit var worryDivisorBI: BigInteger

        fun init() {
            worryMultiplierBI = BigInteger.valueOf(worryMultiplier)
            worryAdderBI = BigInteger.valueOf(worryAdder)
            reliefFactorBI = BigInteger.valueOf(reliefFactor)
            worryDivisorBI = BigInteger.valueOf(worryDivisor)
        }

        fun inspectAndThrowItems() {
            items.forEach {
                if (worryMultiplier > 1)
                    it.worryLevel = it.worryLevel.multiply(worryMultiplierBI)
                if (worryAdder != 0L)
                    it.worryLevel = it.worryLevel.add(worryAdderBI)
                if (worryPower == 2)
                    it.worryLevel = it.worryLevel * it.worryLevel
                inspectCount++
                if (reliefFactor > 1)
                    it.worryLevel = it.worryLevel.divide(reliefFactorBI)
                it.worryLevel = it.worryLevel.mod(interestingPartBI)
                if (it.worryLevel.mod(worryDivisorBI) == BigInteger.ZERO) {
                    monkeys[receiverTrue].items.add(it)
                } else {
                    monkeys[receiverFalse].items.add(it)
                }
            }
            items.clear()
        }
    }
}
