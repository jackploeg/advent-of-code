package nl.jackploeg.aoc._2024.calendar.day17

import nl.jackploeg.aoc.utilities.extractInts
import nl.jackploeg.aoc.utilities.extractLongs
import org.apache.commons.math3.util.ArithmeticUtils.pow
import java.io.File
import javax.inject.Inject
import kotlin.math.pow

class Day17 @Inject constructor() {

    val registers = mutableMapOf<Char, Long>()

    fun partOne(filename: String): String {
        val input = File(filename).readLines()
        registers['A'] = input[0].extractLongs().first()
        registers['B'] = input[1].extractLongs().first()
        registers['C'] = input[2].extractLongs().first()

        val program = input[4].extractInts()

        var instructionPointer = 0
        var output = ""

        while (instructionPointer < program.size) {
            val instruction = Instruction.entries.find { it.opcode == program[instructionPointer] }!!
            val operand = program[instructionPointer + 1]
            val result = instruction.execute(operand.toLong(), registers)
            if (result.first != null) {
                output += result.first!!
            }
            if (result.second != null) {
                instructionPointer = result.second!!.toInt()
            } else instructionPointer += 2
        }
        return output.trimEnd(',')
    }

    fun partTwo(filename: String): Long {
        val input = File(filename).readLines()
        registers['A'] = input[0].extractLongs().first()
        val program = input[4].extractInts()
        val combinations = (0L..7L).map { listOf(it) }.toMutableList()
        while (combinations.isNotEmpty()) {
            val next = combinations.removeFirst()
            val a = next.reversed().reduceIndexed { index, acc, i -> acc + i * (8.0).pow(index).toLong() }
            val output = program(a)
            val matches = output.size == next.size && program.takeLast(output.size) == output
            if (matches) {
                if (output == program) return a
                combinations += (0L..7L).map { next + listOf(it) }
            }
        }
        return -1
    }

    enum class Instruction(val opcode: Int) {
        ADV(0),
        BXL(1),
        BST(2),
        JNZ(3),
        BXC(4),
        OUT(5),
        BDV(6),
        CDV(7);

        fun execute(operand: Long, registers: MutableMap<Char, Long>): Pair<String?, Long?> {
            val comboOperand = when (operand) {
                in 0L..3L -> operand
                4L -> registers['A'] ?: 0L
                5L -> registers['B'] ?: 0L
                6L -> registers['C'] ?: 0L
                else -> throw IllegalArgumentException("Operand $operand out of range")
            }
            when (this) {
                ADV -> {
                    val a = registers['A'] ?: 0L
                    val r = a / pow(2, comboOperand)
                    registers['A'] = r
                }

                BXL -> {
                    val b = registers['B'] ?: 0
                    val r = b xor operand
                    println("BXL:  $b xor $operand = $r")
                    registers['B'] = r
                }

                BST -> {
                    val r = comboOperand % 8
                    registers['B'] = r
                }

                JNZ -> {
                    if (registers['A'] != 0L) {
                        return Pair(null, operand)
                    }
                }

                BXC -> {
                    val b = registers['B'] ?: 0
                    val c = registers['C'] ?: 0
                    val r = b xor c
                    registers['B'] = r
                }

                OUT -> {
                    println("${comboOperand % 8},")
                    return Pair("${comboOperand % 8},", null)
                }

                BDV -> {
                    val a = registers['A'] ?: 0
                    val r = a / pow(2, comboOperand)
                    registers['B'] = r
                }

                CDV -> {
                    val a = registers['A'] ?: 0
                    val r = a / pow(2, comboOperand)
                    registers['C'] = r
                }
            }
            return Pair(null, null)
        }
    }

    fun program(registerA: Long): List<Int> {
        var a = registerA
        var b: Long
        var c: Long
        val output = mutableListOf<Int>()

        /** program for 2,4,1,3,7,5,0,3,4,3,1,5,5,5,3,0
         * bst 4
         * bxl 3
         * cdv 5
         * adv 3
         * bxc 3
         * bxl 5
         * out 5
         * jnz 0
         */
        while (true) {
            b = a % 8L xor 3L          // bst 4 bxl 3
            c = a.shr(b.toInt())       // cdv 5
            a = a / 8                  // adv 3
            b = b xor c                // bxc 3
            b = b xor 5L               // bxl 5
            output += (b % 8).toInt()  // out 5
            if (a == 0L) {             // jnz 0
                break
            }
        }
        return output.map { it }
    }
}
