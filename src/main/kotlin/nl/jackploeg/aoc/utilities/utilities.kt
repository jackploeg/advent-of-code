package nl.jackploeg.aoc.utilities

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.math.pow

fun readNumbersFile(fileName: String): List<Int> {
    return File(fileName)
        .readLines()
        .map { it.toInt() }
}

fun readStringFile(fileName: String): List<String> {
    return File(fileName)
        .readLines()
}

fun repeatString(input: String, repeat: Int, separator: Char) : String {
    if (repeat==1)
        return input
    val result= StringBuilder(input)
    repeat(( 2..repeat).count()) {
        result.append(separator)
        result.append(input)
    }
    return result.toString()
}

fun leastCommonMultiple(first: BigInteger, second: BigInteger): BigInteger {
    val larger = if (first > second) first else second
    val maxLcm = first * second
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm.mod(first) == BigInteger.ZERO && lcm.mod(second) == BigInteger.ZERO ) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

@Deprecated("Use direction in grid")
enum class Direction {
    UP, RIGHT, DOWN, LEFT;

    fun reversed() =
        when (this) {
            UP->DOWN
            DOWN->UP
            LEFT->RIGHT
            RIGHT->LEFT
        }
}

enum class Operator {
    PLUS, MINUS, MULTIPLY, DIVIDE, CONCAT;

    companion object {
        fun from(char: Char): Operator {
            return when (char) {
                '+' -> PLUS
                '-' -> MINUS
                '*' -> MULTIPLY
                '/' -> DIVIDE
                '|' -> CONCAT
                else -> throw RuntimeException()
            }
        }
    }
}

tailrec fun factorial(n: Int, accumulator: Int = 1): Int {
    return if (n <= 1) {
        accumulator
    } else {
        factorial(n - 1, n * accumulator)
    }
}

fun String.extractInts(): List<Int> = Regex("""-?\d+""").findAll(this).map { it.value.toInt() }.toList()
fun String.intsToSet(): Set<Int> = Regex("""-?\d+""").findAll(this).mapTo(mutableSetOf()) { it.value.toInt() }
fun String.extractLongs(): List<Long> = Regex("""-?\d+""").findAll(this).map { it.value.toLong() }.toList()
fun String.extractDoubles(): List<Double> = Regex("""-?\d+(\.\d+)?""").findAll(this).map { it.value.toDouble() }.toList()
fun String.extractWords(): List<String> = Regex("""\w+""").findAll(this).map { it.value }.toList()

fun List<String>.splitOnFirstEmptyLine(): List<List<String>> {
    val firstEmptyLine = this.indexOfFirst { it.isEmpty() }
    return if (firstEmptyLine == -1) {
        listOf(this)
    } else {
        listOf(this.subList(0, firstEmptyLine), this.subList(firstEmptyLine + 1, this.size))
    }
}

fun List<String>.splitOnEmptyLines(): List<List<String>> {
    val result: MutableList<List<String>> = mutableListOf()
    var tmpList = mutableListOf<String>()
    this.forEach { line ->
        if (line.isEmpty()) {
            result.add(tmpList)
            tmpList = mutableListOf()
        } else {
            tmpList.add(line)
        }
    }
    result.add(tmpList)
    return result
}

infix fun Long.`**`(exponent: Int): Long = toDouble().pow(exponent).toLong()

@OptIn(ExperimentalStdlibApi::class)
fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(this.toByteArray())
    return digest.toHexString()
}

