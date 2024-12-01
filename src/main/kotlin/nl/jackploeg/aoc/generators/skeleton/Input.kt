package nl.jackploeg.aoc.generators.skeleton

import nl.jackploeg.aoc.generators.skeleton.SkeletonGenerator.Companion.INPUT_DIRECTORY
import java.nio.file.Files
import java.nio.file.Paths

object Input {
  fun generateInput(year: String, day: String) {
    val directory = "$INPUT_DIRECTORY/$year"
    val parentDirectory = Paths.get(directory)
    val path = Paths.get("$directory/day$day.input")
    if (Files.notExists(parentDirectory)) {
      Files.createDirectory(parentDirectory)
    }
    Files.write(path, "".toByteArray())
    val testpath = Paths.get("$directory/day$day.test")
    Files.write(testpath, "".toByteArray())
    val part1path = Paths.get("$directory/day$day.part1.txt")
    Files.write(part1path, "".toByteArray())
    val part2path = Paths.get("$directory/day$day.part2.txt")
    Files.write(part2path, "".toByteArray())
  }
}
