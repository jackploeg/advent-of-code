package nl.jackploeg.aoc._2024.calendar.day07

import nl.jackploeg.aoc.utilities.Operator
import java.io.File
import java.math.BigInteger
import javax.inject.Inject

class Day07 @Inject constructor() {

    fun partOne(filename: String): BigInteger {
        val input = File(filename).readLines()
        val equations: MutableList<Equation> = mutableListOf()
        input.forEach {
            val numbers = it.split(" ")
            equations.add(
                Equation(
                    numbers[0].substringBefore(':').toBigInteger(),
                    numbers.subList(1, numbers.size).map { it.toBigInteger() })
            )
        }
        return equations.filter { it.canBeValidWithoutOperatorPrecedence() }.sumOf { it.result }
    }

    fun partTwo(filename: String): BigInteger {
        val input = File(filename).readLines()
        val equations: MutableList<Equation> = mutableListOf()
        input.forEach {
            val numbers = it.split(" ")
            equations.add(
                Equation(
                    numbers[0].substringBefore(':').toBigInteger(),
                    numbers.subList(1, numbers.size).map { it.toBigInteger() })
            )
        }
        return equations.filter { it.canBeValidWith3Operators() }.sumOf { it.result }
    }

    data class Equation(val result: BigInteger, val operands: List<BigInteger>) {
        fun canBeValidWithoutOperatorPrecedence(): Boolean {
            // all combinations of + and *
            val operatorCombinations: List<List<Operator>> = combineOperators(operands.size - 1)
            operatorCombinations.forEach {
                if (isValidWithoutOperatorPrecedence(result, operands, it)) {
                    return true
                }
            }
            return false
        }

        fun canBeValidWith3Operators(): Boolean {
            // all combinations of + and * and ||
            val operatorCombinations: List<List<Operator>> = combine3Operators(operands.size - 1)
            operatorCombinations.forEach {
                if (isValidWith3Operators(result, operands, it)) {
                    return true
                }
            }
            return false
        }

        fun canBeValid(): Boolean {
            // all combinations of + and *
            val operatorCombinations: List<List<Operator>> = combineOperators(operands.size - 1)
            operatorCombinations.forEach {
                if (isValid(result, operands, it)) {
                    return true
                }
            }
            return false
        }

        fun isValidWithoutOperatorPrecedence(
            result: BigInteger,
            operands: List<BigInteger>,
            operators: List<Operator>
        ): Boolean {

            var resultOfCalculation = operands[0]

            (0..operators.size - 1).forEach { i ->
                if (operators[i] == Operator.MULTIPLY) {
                    resultOfCalculation *= operands[i + 1]
                } else {
                    resultOfCalculation += operands[i + 1]
                }
            }
            return result == resultOfCalculation
        }

        fun isValidWith3Operators(
            result: BigInteger,
            operands: List<BigInteger>,
            operators: List<Operator>
        ): Boolean {
            var resultOfCalculation = operands[0]

            (0..operators.size - 1).forEach { i ->
                when (operators[i]) {
                    Operator.MULTIPLY -> resultOfCalculation *= operands[i + 1]
                    Operator.PLUS -> resultOfCalculation += operands[i + 1]
                    Operator.CONCAT -> resultOfCalculation = "$resultOfCalculation${operands[i + 1]}".toBigInteger()
                    else -> throw RuntimeException()
                }
            }
            return result == resultOfCalculation
        }

        fun isValid(result: BigInteger, operands: List<BigInteger>, operators: List<Operator>): Boolean {

            val numbers: MutableList<BigInteger> = mutableListOf()
            val ops: MutableList<Operator> = mutableListOf()

            numbers.addAll(operands)
            ops.addAll(operators)
            var onlyAdditions = false

            while (!onlyAdditions) {
                // first multiplication
                var multiplication = -1
                (0..ops.size - 1).forEach() {
                    if (ops[it] == Operator.MULTIPLY) {
                        multiplication = it
                    }
                }
                if (multiplication != -1) {
                    val multiple = numbers[multiplication] * numbers[multiplication + 1]
                    // replace 2 operands with product
                    numbers.add(multiplication, multiple)
                    numbers.removeAt(multiplication + 1)
                    numbers.removeAt(multiplication + 1)
                    ops.removeAt(multiplication)
                    continue
                } else {
                    onlyAdditions = true
                }
            }
            return result == numbers.sumOf { it }
        }

        fun combineOperators(size: Int): List<List<Operator>> =
            (0..Math.pow(2.0, size.toDouble()).toInt() - 1).map {
                var s = it.toString(2).padStart(size, '0')
                s.toCharArray().map {
                    if (it == '0') Operator.PLUS else Operator.MULTIPLY
                }
            }

        fun combine3Operators(size: Int): List<List<Operator>> =
            (0..Math.pow(3.0, size.toDouble()).toInt() - 1).map {
                var s = it.toString(3).padStart(size, '0')
                s.toCharArray().map {
                    when (it) {
                        '0' -> Operator.PLUS
                        '1' -> Operator.MULTIPLY
                        '2' -> Operator.CONCAT
                        else -> throw RuntimeException()
                    }
                }
            }
    }
}