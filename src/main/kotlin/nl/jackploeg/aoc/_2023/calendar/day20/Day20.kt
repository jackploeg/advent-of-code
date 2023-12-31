package nl.jackploeg.aoc._2023.calendar.day20

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile
import java.util.*
import javax.inject.Inject

class Day20 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun partOne(fileName: String): Long {
    val lines = readStringFile(fileName)
    val network: MutableList<CommunicationModule> = mutableListOf()
    lines.forEach { line -> parseModules(line, network) }
    lines.forEach { line -> parseOutputs(line, network) }

    val broadcaster = network.first { it.name == "broadcaster" }
    val pulseRelay = PulseRelay()
    val button = CommunicationModule("button", ModuleType.UNTYPED, outputs = mutableListOf(broadcaster))

    (0 until 1000).forEach {
      pulseRelay.relay(StateType.LOW, broadcaster, button)
      pulseRelay.handle()
    }

    return pulseRelay.total
  }

  fun partTwo(fileName: String): Long {
    val lines = readStringFile(fileName)
    val network: MutableList<CommunicationModule> = mutableListOf()
    lines.forEach { line -> parseModules(line, network) }
    lines.forEach { line -> parseOutputs(line, network) }

    val broadcaster = network.first { it.name == "broadcaster" }

    val startModulesOfGroups = broadcaster.outputs

    // find paths of the different groups of Modules, every group is a chain of flipflops, ending in a conjunction
    val paths = startModulesOfGroups.map { startModule ->
      val path = mutableListOf(startModule)

      var nextModule = startModule
      while (nextModule.type == ModuleType.FLIPFLOP) {
        nextModule = (nextModule.outputs.firstOrNull { it.type == ModuleType.FLIPFLOP }
          ?: nextModule.outputs.first())
        path.add(nextModule)
      }

      path
    }

//    paths.forEach {
//        it.forEach { print("${it.name} - ${it.type},") }
//        println()
//    }

    // the groups of flipflops make up 12 bit numbers that increment on every pulse sent to the first
    val pathBitmaps = paths.map { path ->
      val finalConjunctionModule = path.last()
      // mark flipflops that are inputs for the conjunction as 1, others as 0
      val pathBitmap = "0".repeat(path.size - 1).toCharArray()
      finalConjunctionModule.inputs.map { (source, _) -> pathBitmap[path.indexOf(source)] = '1' }
      pathBitmap.reversed().joinToString("")
    }

    // some flipflops are inputs for the conjunction, others are outputs. If all outputs are high then the conjunctions
    // flips the other flipflops and we have a cycle
    return pathBitmaps
      .map { it.toLong(2) }
      .fold(1L) { a, b -> a * b } // TODO: LCM
  }

  fun parseModules(line: String, network: MutableList<CommunicationModule>) {
    var modulename = line.substringBefore(" -> ")
    var type: ModuleType = ModuleType.UNTYPED
    if (modulename == "broadcaster") {
      type = ModuleType.BROADCASTER
    }
    if (modulename.startsWith('%')) {
      type = ModuleType.FLIPFLOP
      modulename = modulename.substring(1)
    }
    if (modulename.startsWith('&')) {
      type = ModuleType.CONJUNCTION
      modulename = modulename.substring(1)
    }
    val communicationModule = CommunicationModule(modulename, type)
    network.add(communicationModule)
  }

  fun parseOutputs(line: String, network: MutableList<CommunicationModule>) {
    var (modulename, outputs) = line.split(" -> ")
    modulename = modulename.trim('%', '&')
    val source = network.first { it.name == modulename }
    outputs.split(',').forEach { name ->
      val linkedModuleName = name.trim()
      if (network.firstOrNull { it.name == linkedModuleName } == null) {
        network.add(CommunicationModule(linkedModuleName, ModuleType.UNTYPED))
      }
      val linkedModule = network.first { it.name == linkedModuleName }
      source.outputs.add(linkedModule)
      if (linkedModule.type == ModuleType.CONJUNCTION) {
        linkedModule.inputs.add(Input(source, StateType.LOW))
      }
    }
  }

  class PulseRelay {
    var lowCounter = 0L
    var highCounter = 0L
    val total: Long
      get() = lowCounter * highCounter

    val todo: Deque<Work> = ArrayDeque()
    fun relay(pulse: StateType, destination: CommunicationModule, source: CommunicationModule) {
      when (pulse) {
        StateType.LOW -> lowCounter++
        StateType.HIGH -> highCounter++
      }
      todo.add(Work(pulse, destination, source))
    }

    fun handle() {
      while (!todo.isEmpty()) {
        val work = todo.removeFirst()
        work.destination.receivePulse(work.pulse, work.source, this)
      }
    }

    override fun toString(): String {
      return "PulseRelay(lowcounter=$lowCounter, highcounter=$highCounter, result=${lowCounter * highCounter}"
    }


  }

  data class CommunicationModule(
    val name: String,
    val type: ModuleType,
    var state: Boolean = false,
    val outputs: MutableList<CommunicationModule> = mutableListOf(),
    val inputs: MutableList<Input> = mutableListOf()
  ) {

    fun receivePulse(pulse: StateType, source: CommunicationModule, pulseRelay: PulseRelay) {
      //  println("Received pulse: $pulse from ${source.name} in module $name of type $type")
      when (type) {
        ModuleType.BROADCASTER -> {
          outputs.forEach { pulseRelay.relay(pulse, it, this) }
          pulseRelay.handle()
        }

        ModuleType.FLIPFLOP -> {
          if (pulse == StateType.LOW) {
            state = !state
            outputs.forEach { pulseRelay.relay(if (state) StateType.HIGH else StateType.LOW, it, this) }
            pulseRelay.handle()
          }
        }

        ModuleType.CONJUNCTION -> {
          inputs.first { it.communicationModule.name == source.name }.lastState = pulse
          val pulseToSend = if (inputs.all { it.lastState == StateType.HIGH }) StateType.LOW else StateType.HIGH
          outputs.forEach { pulseRelay.relay(pulseToSend, it, this) }
          pulseRelay.handle()
        }

        ModuleType.UNTYPED -> {
          outputs.forEach { pulseRelay.relay(pulse, it, this) }
          pulseRelay.handle()
        }
      }
    }

    override fun toString(): String {
      return "Module(name='$name', type=$type, state=$state, outputs=${outputs.map { it.name }}, inputs=${inputs.map { it.communicationModule.name }})"
    }

  }

  data class Input(val communicationModule: CommunicationModule, var lastState: StateType)

  data class Work(val pulse: StateType, val destination: CommunicationModule, val source: CommunicationModule)
  enum class ModuleType { BROADCASTER, FLIPFLOP, CONJUNCTION, UNTYPED }
  enum class StateType { HIGH, LOW }


}
