package nl.jackploeg.aoc._2023.calendar.day19

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile
import java.util.*
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

class Day19 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun partOne(fileName: String): Int {
    val parts: MutableList<Part> = mutableListOf()
    val workflows: MutableList<Workflow> = mutableListOf()

    val acceptedParts = mutableListOf<Part>()
    val rejectedParts = mutableListOf<Part>()

    readStringFile(fileName).forEach { line ->
      if (line.startsWith('{')) {
        parsePart(line, parts)
      } else if (line != "") {
        parseWorkflow(line, workflows)
      }
    }

    workflows.first { it.name == "in" }.queue.addAll(parts)

    while (!workflows.all { it.queue.isEmpty() }) {
      workflows.forEach { workflow ->
        while (!workflow.queue.isEmpty()) {
          val part = workflow.queue.removeFirst()
          workflow.processPart(part, workflows, acceptedParts, rejectedParts)
        }
      }
    }
    return acceptedParts.sumOf { it.total }
  }

  fun partTwo(fileName: String): Long {

    val workflows = readStringFile(fileName)
      .filter{ !it.startsWith('{')}
      .filter { it!="" }
      .plus(listOf("A{}", "R{}"))
      .map(::parseWorkflow2)
      .associateBy { it.name }

//    println(workflows)
    return countAccepted(workflows, workflows["in"]!!)
  }

  fun parseWorkflow(line: String, workflows: MutableList<Workflow>) {
    val name = line.substringBefore('{')
    val stepConfig = line.substringAfter('{').trim('}')
    val steps = mutableListOf<Step>()
    stepConfig.split(',')
      .forEach {
//            println(it)
        steps.add(
          if (it.contains(':')) {
            ConditionalStep(
              it[0],
              it[1],
              it.substring(2).substringBefore(':').toInt(),
              it.substringAfter(':')
            )
          } else {
            UnconditionalStep(it)
          }
        )
      }
    workflows.add(Workflow(name, steps))
  }

  fun parsePart(line: String, parts: MutableList<Part>) {
    val attributes = line.trim('{', '}').split(',')
    val x = attributes.filter { it.startsWith('x') }.first().substringAfter('=').toInt()
    val m = attributes.filter { it.startsWith('m') }.first().substringAfter('=').toInt()
    val a = attributes.filter { it.startsWith('a') }.first().substringAfter('=').toInt()
    val s = attributes.filter { it.startsWith('s') }.first().substringAfter('=').toInt()
    parts.add(Part(x, m, a, s))
  }

  data class Part(val x: Int, val m: Int, val a: Int, val s: Int) {
    val total: Int = x + m + a + s
  }

  private fun countAccepted(
    workflows: Map<String, Workflow2>,
    workflow: Workflow2,
    acceptedPart: AcceptedPart = AcceptedPart(),
  ): Long {
    return when (workflow.name) {
      "A" -> acceptedPart.allowedValues()
      "R" -> 0
      else -> {
        var remainingPart = acceptedPart

        return workflow.rules.sumOf { rule ->
          when (rule) {
            is GreaterThan -> {
              val limitedPart = remainingPart.adjustGreaterThan(rule.attribute, rule.value + 1)
              remainingPart = remainingPart.adjustLessThan(rule.attribute, rule.value)

              countAccepted(workflows, workflows[rule.ifTrue]!!, limitedPart)
            }

            is LessThan -> {
              val limitedXmas = remainingPart.adjustLessThan(rule.attribute, rule.value - 1)
              remainingPart = remainingPart.adjustGreaterThan(rule.attribute, rule.value)

              countAccepted(workflows, workflows[rule.ifTrue]!!, limitedXmas)
            }

            is UnconditionalRule -> {
              countAccepted(workflows, workflows[rule.result]!!, remainingPart)
            }

            else -> throw RuntimeException("Invalid rule")
          }
        }
      }
    }
  }

  data class Workflow2(val name: String, val rules: List<Rule>)
  data class Workflow(val name: String, val steps: List<Step>, val queue: Deque<Part> = ArrayDeque()) {
    fun processPart(
      part: Part,
      workflows: MutableList<Workflow>,
      acceptedParts: MutableList<Part>,
      rejectedParts: MutableList<Part>
    ) {
      outer@ for (step in steps) {
        if (step is UnconditionalStep) {
          processStep(part, step.action, workflows, acceptedParts, rejectedParts)
          break@outer
        } else if (step is ConditionalStep) {
          when (step.attribute) {
            'x' -> {
              when (step.condition) {
                '<' -> if (part.x < step.value) {
                  processStep(part, step.action, workflows, acceptedParts, rejectedParts)
                  break@outer
                }

                '>' -> if (part.x > step.value) {
                  processStep(part, step.action, workflows, acceptedParts, rejectedParts)
                  break@outer
                }
              }
            }

            'm' -> {
              when (step.condition) {
                '<' -> if (part.m < step.value) {
                  processStep(part, step.action, workflows, acceptedParts, rejectedParts)
                  break@outer
                }

                '>' -> if (part.m > step.value) {
                  processStep(part, step.action, workflows, acceptedParts, rejectedParts)
                  break@outer
                }
              }
            }

            'a' -> {
              when (step.condition) {
                '<' -> if (part.a < step.value) {
                  processStep(part, step.action, workflows, acceptedParts, rejectedParts)
                  break@outer
                }

                '>' -> if (part.a > step.value) {
                  processStep(part, step.action, workflows, acceptedParts, rejectedParts)
                  break@outer
                }
              }
            }

            's' -> {
              when (step.condition) {
                '<' -> if (part.s < step.value) {
                  processStep(part, step.action, workflows, acceptedParts, rejectedParts)
                  break@outer
                }

                '>' -> if (part.s > step.value) {
                  processStep(part, step.action, workflows, acceptedParts, rejectedParts)
                  break@outer
                }
              }
            }
          }
        }
      }
    }

    private fun processStep(
      part: Part,
      action: String,
      workflows: MutableList<Workflow>,
      acceptedParts: MutableList<Part>,
      rejectedParts: MutableList<Part>
    ) {
      if (action == "A")
        acceptedParts.add(part)
      else if (action == "R")
        rejectedParts.add(part)
      else workflows.first { it.name == action }.queue.add(part)
    }
  }

  private fun parseWorkflow2(line: String): Workflow2 {
    // ex{x>10:one,m<20:two,a>30:R,A}

    val name = line.substringBefore('{')
    val rules = line.substringAfter('{')
      .trim('}')
      .split(",")
      .map { ruleString ->
        if (ruleString.contains(':')) {
          val (check, ifTrue) = ruleString.split(':')
          when (val operation = check[1]) {
            '>' -> GreaterThan(check[0], check.substring(2).toInt(), ifTrue)
            '<' -> LessThan(check[0], check.substring(2).toInt(), ifTrue)
            else -> throw IllegalArgumentException("Unknown operation $operation")
          }
        } else {
          UnconditionalRule(ruleString)
        }
      }
    return Workflow2(name, rules)
  }

  open class Step
  data class ConditionalStep(val attribute: Char, val condition: Char, val value: Int, val action: String) : Step()

  data class UnconditionalStep(val action: String) : Step()

  open class Rule
  class GreaterThan(val attribute: Char, val value: Int, val ifTrue: String) : Rule()
  class LessThan(val attribute: Char, val value: Int, val ifTrue: String) : Rule()
  class UnconditionalRule(val result: String) : Rule()

  data class AcceptedPart(
    val xMin: Int = 1, val xMax: Int = 4000,
    val mMin: Int = 1, val mMax: Int = 4000,
    val aMin: Int = 1, val aMax: Int = 4000,
    val sMin: Int = 1, val sMax: Int = 4000,
  ) {
    fun adjustGreaterThan(attribute: Char, value: Int): AcceptedPart {
      return when (attribute) {
        'x' -> copy(xMin = max(xMin, value))
        'm' -> copy(mMin = max(mMin, value))
        'a' -> copy(aMin = max(aMin, value))
        's' -> copy(sMin = max(sMin, value))
        else -> throw IllegalArgumentException("Unknown attribute: $attribute")
      }
    }

    fun adjustLessThan(attribute: Char, value: Int): AcceptedPart {
      return when (attribute) {
        'x' -> copy(xMax = min(xMax, value))
        'm' -> copy(mMax = min(mMax, value))
        'a' -> copy(aMax = min(aMax, value))
        's' -> copy(sMax = min(sMax, value))
        else -> throw IllegalArgumentException("Unknown attribute: $attribute")
      }
    }

    fun allowedValues(): Long {
      return (xMin..xMax).count().toLong() *
              (mMin..mMax).count().toLong() *
              (aMin..aMax).count().toLong() *
              (sMin..sMax).count().toLong()
    }
  }
}
