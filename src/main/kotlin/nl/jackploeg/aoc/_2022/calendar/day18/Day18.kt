package nl.jackploeg.aoc._2022.calendar.day18

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile

class Day18 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
    // count uncovered faces
    fun partOne(fileName: String): Int {
        val input = readStringFile(fileName)

        val droplet: ArrayList<ArrayList<BooleanArray>> = arrayListOf()

        for (x in 0..26) {
            val row: ArrayList<BooleanArray> = arrayListOf()
            for (y in 0..26) {
                row.add(BooleanArray(27))
            }
            droplet.add(row)
        }

        for (line in input) {
            val coords = line.split(',')
            droplet[Integer.parseInt(coords[0]) + 1][Integer.parseInt(coords[1]) + 1][Integer.parseInt(coords[2]) + 1] =
                true
        }

        var faces = 0
        for (x in 1..25) {
            for (y in 1..25) {
                for (z in 1..25) {
                    if (droplet[x][y][z]) {
                        if (!droplet[x - 1][y][z]) {
                            faces++
                        }
                        if (!droplet[x + 1][y][z]) {
                            faces++
                        }
                        if (!droplet[x][y - 1][z]) {
                            faces++
                        }
                        if (!droplet[x][y + 1][z]) {
                            faces++
                        }
                        if (!droplet[x][y][z - 1]) {
                            faces++
                        }
                        if (!droplet[x][y][z + 1]) {
                            faces++
                        }
                    }
                }
            }
        }
        return faces
    }

    // count outside faces
    fun partTwo(fileName: String): Int {
        val input = readStringFile(fileName)

        val droplet: ArrayList<ArrayList<MutableList<Material>>> = arrayListOf()

        for (x in 0..26) {
            val row: ArrayList<MutableList<Material>> = arrayListOf()
            for (y in 0..26) {
                val column: ArrayList<Material> = arrayListOf()
                for (z in 0..26)
                    column.add(Material.AIR)
                row.add(column)
            }
            droplet.add(row)
        }

        for (line in input) {
            val coords = line.split(',')
            droplet[Integer.parseInt(coords[0]) + 1][Integer.parseInt(coords[1]) + 1][Integer.parseInt(coords[2]) + 1] =
                Material.ROCK
        }

        // fill outside with water
        val start = Cube(0, 0, 0)
        val queue = ArrayDeque<Cube>()
        queue.add(start)
        while (!queue.isEmpty()) {
            val current = queue.removeFirst()
            if (droplet[current.x][current.y][current.z] == Material.AIR) {
                droplet[current.x][current.y][current.z] = Material.WATER
                if (current.x > 0 && droplet[current.x - 1][current.y][current.z] == Material.AIR) {
                    queue.add(Cube(current.x - 1, current.y, current.z))
                }
                if (current.x < 26 && droplet[current.x + 1][current.y][current.z] == Material.AIR) {
                    queue.add(Cube(current.x + 1, current.y, current.z))
                }
                if (current.y > 0 && droplet[current.x][current.y - 1][current.z] == Material.AIR) {
                    queue.add(Cube(current.x, current.y - 1, current.z))
                }
                if (current.y < 26 && droplet[current.x][current.y + 1][current.z] == Material.AIR) {
                    queue.add(Cube(current.x, current.y + 1, current.z))
                }
                if (current.z > 0 && droplet[current.x][current.y][current.z - 1] == Material.AIR) {
                    queue.add(Cube(current.x, current.y, current.z - 1))
                }
                if (current.z < 26 && droplet[current.x][current.y][current.z + 1] == Material.AIR) {
                    queue.add(Cube(current.x, current.y, current.z + 1))
                }
            }
        }

       // printDroplet(droplet)

        var faces = 0
        for (x in 1..25) {
            for (y in 1..25) {
                for (z in 1..25) {
                    if (droplet[x][y][z] == Material.ROCK) {
                        if (droplet[x - 1][y][z] == Material.WATER) {
                            faces++
                        }
                        if (droplet[x + 1][y][z] == Material.WATER) {
                            faces++
                        }
                        if (droplet[x][y - 1][z] == Material.WATER) {
                            faces++
                        }
                        if (droplet[x][y + 1][z] == Material.WATER) {
                            faces++
                        }
                        if (droplet[x][y][z - 1] == Material.WATER) {
                            faces++
                        }
                        if (droplet[x][y][z + 1] == Material.WATER) {
                            faces++
                        }
                    }
                }
            }
        }
        return faces
    }

    fun printDroplet(droplet: ArrayList<ArrayList<MutableList<Material>>>) {
        for (x in 1..25) {
            println("---------------------------------------------------------")
            for (y in 1..25) {
                for (z in 1..25) {
                    when (droplet[x][y][z]) {
                        Material.ROCK -> print("#")
                        Material.AIR -> print(" ")
                        Material.WATER -> print("~")
                    }
                }
                println()
            }
        }
    }

    enum class Material { AIR, ROCK, WATER }
    class Cube(val x: Int, val y: Int, val z: Int)
}
