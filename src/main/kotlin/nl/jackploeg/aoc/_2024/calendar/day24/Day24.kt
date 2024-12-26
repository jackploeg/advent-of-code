package nl.jackploeg.aoc._2024.calendar.day24

import nl.jackploeg.aoc.utilities.splitOnFirstEmptyLine
import java.io.File
import java.util.*
import javax.inject.Inject

class Day24 @Inject constructor() {
    val state: MutableMap<String, Int?> = mutableMapOf()
    var gates: MutableMap<String, Gate> = mutableMapOf()
    val swaps = mutableMapOf<String, String>()

    fun partOne(filename: String): Long {
        gates = initData(filename)

        while (state.any { it.key.startsWith('z') && it.value == null }) {
            gates.values.forEach {
                it.process(state)
            }
        }

        return getRegister('z')
    }

    private fun initData(filename: String): MutableMap<String, Gate> {
        val (initial, connections) = File(filename).readLines().splitOnFirstEmptyLine()
        initial.map { it.split(':') }.forEach { state[it[0]] = it[1].trim().toInt() }

        val gates: MutableMap<String, Gate> = mutableMapOf()
        connections.map { it.split(' ') }.forEach {
            if (!state.containsKey(it[0])) {
                state[it[0]] = null
            }
            if (!state.containsKey(it[1])) {
                state[it[1]] = null
            }
            if (!state.containsKey(it[4])) {
                state[it[4]] = null
            }
            gates[it[4]] = (Gate(it[0], it[2], it[4], it[1]))
        }
        return gates
    }

    fun getRegister(type: Char): Long {
        val result = state.filter { it.key.startsWith(type) }.keys.sortedDescending().map { state[it] }.joinToString("")
        println("convert $type : $result")
        return result.toLong(2)
    }

    data class Gate(val left: String, val right: String, val result: String, val operation: String) {
        fun process(state: MutableMap<String, Int?>) {
            if (state.containsKey(left) && state.containsKey(right) && state.containsKey(result) && state[left] != null && state[right] != null) {
                state[result] = when (operation) {
                    "AND" -> state[left]!!.and(state[right]!!)
                    "OR" -> state[left]!!.or(state[right]!!)
                    "XOR" -> state[left]!!.xor(state[right]!!)
                    else -> null
                }
            }
        }

        fun input() = setOf(left, right)
    }

    fun partTwo(filename: String): String {
        gates = initData(filename)

        while (state.any { it.key.startsWith('z') && it.value == null }) {
            this.gates.values.forEach { it.process(state) }
        }

        val correctGateConfig = mutableMapOf<String, List<Gate>>()

        (0..45).forEach loop@{ n ->
            val previousPreviousKey = "z${(n - 2).toString().padStart(2, '0')}"
            val previousKey = "z${(n - 1).toString().padStart(2, '0')}"
            val key = "z${n.toString().padStart(2, '0')}"
            when (n) {
                0 -> checkZero()?.also { swap(it) }
                1 -> checkOne()?.also { swap(it) }
                in (2..44) -> checkDefault(
                    key = n to key,
                    previousGateConfig = n - 1 to previousKey,
                    previousPreviousGateConfig = n - 2 to previousPreviousKey,
                    correctGateConfig = correctGateConfig
                )?.also { swap(it) }
            }
            correctGateConfig[key] = getGateAncestors(key)
        }

        if (swaps.size != 8) {
            throw IllegalStateException("Did not find all the swaps")
        }

        return swaps.keys.sorted().joinToString(",")
    }

    private fun swap(swap: Pair<String, String>) {
        val oldA = gates[swap.first]!!
        val oldB = gates[swap.second]!!

        val newB = oldB.copy(result = oldA.result)
        val newA = oldA.copy(result = oldB.result)

        gates[swap.first] = newB
        gates[swap.second] = newA

        newA.process(state)
        newB.process(state)

        swaps[swap.first] = swap.second
        swaps[swap.second] = swap.first
    }

    private fun checkZero(): Pair<String, String>? {
        val z00Wiring = gates["z00"]!!
        val expectedInput = setOf("x00", "y00")
        return if (z00Wiring.operation != "XOR" || expectedInput != z00Wiring.input()) {
            // find `x00 XOR y00 = ???`
            val itemToSwapTo = gates.entries.first { (_, w) ->
                val (a, b, _, op) = w
                op == "XOR" && expectedInput == setOf(a, b)
            }
            return "z00" to itemToSwapTo.key
        } else {
            null
        }
    }

    private fun checkOne(): Pair<String, String>? {
        val z01Wiring = gates["z01"]!!

        // aaa XOR bbb = z01
        // y01 XOR x01 = bbb
        // x00 AND y00 = aaa
        val aaaInput = setOf("x00", "y00")
        val bbbInput = setOf("x01", "y01")

        val aaa = gates.entries.first { (_, w) -> w.operation == "AND" && aaaInput == setOf(w.left, w.right) }
        val bbb = gates.entries.first { (_, w) -> w.operation == "XOR" && bbbInput == setOf(w.left, w.right) }

        val correctInput = setOf(aaa.key, bbb.key)
        if (z01Wiring.input() == correctInput) {
            return null
        } else {
            // is there a `aaa.key AND bbb.key` which we need to swap to z01?
            val maybeZSwap = gates.entries.find { (_, w) ->
                w.operation == "XOR" && setOf(w.left, w.right) == setOf(
                    aaa.key,
                    bbb.key
                )
            }
            if (maybeZSwap != null) {
                return "z01" to maybeZSwap.key
            } else {
                // our "z01" was correct - so the input needs swapping
                return if (!z01Wiring.input().contains(aaa.key) && !z01Wiring.input().contains(bbb.key)) {
                    // full disjointSet - this should not happen in the input
                    throw IllegalStateException("Input has full disjoint set!")
                } else {
                    // one of the items is missing
                    if (aaa.key in z01Wiring.input()) {
                        // bbb needs swap
                        bbb.key to z01Wiring.input().minus(aaa.key).first()
                    } else {
                        // aaa needs swap
                        aaa.key to z01Wiring.input().minus(bbb.key).first()
                    }
                }
            }
        }
    }

    fun checkDefault(
        key: Pair<Int, String>,
        previousGateConfig: Pair<Int, String>,
        previousPreviousGateConfig: Pair<Int, String>,
        correctGateConfig: Map<String, List<Gate>>,
    ): Pair<String, String>? {
        val myWirings = getGateAncestors(key.second)

        val currentWirings = myWirings.minus(correctGateConfig[previousGateConfig.second]!!)
        val previousNewWirings =
            correctGateConfig[previousGateConfig.second]!!.minus(correctGateConfig[previousPreviousGateConfig.second]!!)

        // the items we need ...
        // for zN
        // sum(N-1)         AND  carryChain(N-1)  = carryAfter(N-1)
        // y(N-1)           AND  x(N-1)           = carry(N-1)
        // carryAfter(N-1)  OR   carry(N-1)       = carryChainN
        // yN               XOR yN                = sumN
        // sumN             XOR  carryChainN      = zN

        // sum(N-1)
        val x1 = previousGateConfig.first.let { "x${it.toString().padStart(2, '0')}" }
        val y1 = previousGateConfig.first.let { "y${it.toString().padStart(2, '0')}" }
        val sumN1 = previousNewWirings.first { w -> w.operation == "XOR" && setOf(w.left, w.right) == setOf(x1, y1) }

        // carry(N-1)
        val carryN1 =
            gates.entries.first { (_, w) -> w.operation == "AND" && setOf(w.left, w.right) == setOf(x1, y1) }.value

        // sumN
        val x = key.first.let { "x${it.toString().padStart(2, '0')}" }
        val y = key.first.let { "y${it.toString().padStart(2, '0')}" }
        val sumN =
            gates.entries.first { (_, w) -> w.operation == "XOR" && setOf(w.left, w.right) == setOf(x, y) }.value

        // carryChain(N-1)
        val carryChainN1 = previousNewWirings.find { w -> w.operation == "OR" }
            ?: previousNewWirings.first { w -> w.operation == "AND" }
        // carryAfter(N-1)
        val carryAfterN1 = gates.entries.find { (_, w) ->
            w.operation == "AND" && setOf(w.left, w.right) == setOf(
                sumN1.result,
                carryChainN1.result
            )
        }?.value
        if (carryAfterN1 == null) {
            // doesn't happen on input
            throw IllegalArgumentException("Something Wrong with $carryChainN1 or $sumN1")
        }

        // carryChainN
        val carryChainN = gates.entries.find { (_, w) ->
            w.operation == "OR" && setOf(w.left, w.right) == setOf(
                carryAfterN1.result,
                carryN1.result
            )
        }?.value
        if (carryChainN == null) {
            // doesn't happen on input
            throw IllegalArgumentException("Something Wrong with $carryAfterN1 or $carryN1")
        }

        // zN
        val zN = gates.entries.find { (_, w) ->
            w.operation == "XOR" && setOf(w.left, w.right) == setOf(
                sumN.result,
                carryChainN.result
            )
        }?.value
        if (zN == null) {
            val correctZN = gates[key.second]!!
            return if (carryChainN.result in correctZN.input()) {
                // something bad with sumN
                sumN.result to correctZN.input().minus(carryChainN.result).first()
            } else {
                // something bad with carryChainN
                carryChainN.result to correctZN.input().minus(sumN.result).first()
            }
        }

        if (setOf(carryN1, sumN, carryAfterN1, carryChainN, zN) != currentWirings.toSet()) {
            // if we got this far - we need to swap out zN values
            val toSwapWith = currentWirings.first { it.result == key.second }
            return toSwapWith.result to zN.result
        }

        return null
    }

    private fun getGateAncestors(key: String): MutableList<Gate> {
        val ancestors = mutableListOf<Gate>()
        val toCheck = LinkedList<String>()
        toCheck.add(key)
        while (toCheck.isNotEmpty()) {
            val gate = gates[toCheck.poll()]!!
            val (a, b, _, _) = gate

            ancestors.add(gate)

            val aIsInput = a.startsWith('x') || a.startsWith('y')
            val bIsInput = b.startsWith('x') || b.startsWith('y')

            if (!aIsInput) {
                toCheck.add(a)
            }
            if (!bIsInput) {
                toCheck.add(b)
            }
        }

        return ancestors
    }
}
