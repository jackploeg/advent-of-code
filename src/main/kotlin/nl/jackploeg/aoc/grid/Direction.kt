package nl.jackploeg.aoc.grid

enum class Direction {
    NORTH {
        override fun turnRightOrLeft() = mutableListOf(EAST, WEST)
        override fun turnRight() = EAST
        override fun turnLeft() = WEST
        override fun reverse() = SOUTH
    },
    SOUTH {
        override fun turnRightOrLeft() = mutableListOf(EAST, WEST)
        override fun turnRight() = WEST
        override fun turnLeft() = EAST
        override fun reverse() = NORTH
    },
    EAST {
        override fun turnRightOrLeft() = mutableListOf(NORTH, SOUTH)
        override fun turnRight() = SOUTH
        override fun turnLeft() = NORTH
        override fun reverse() = WEST
    },
    WEST {
        override fun turnRightOrLeft() = mutableListOf(NORTH, SOUTH)
        override fun turnRight() = NORTH
        override fun turnLeft() = SOUTH
        override fun reverse() = EAST
    };

    abstract fun turnRightOrLeft(): MutableList<Direction>
    abstract fun turnRight(): Direction
    abstract fun turnLeft(): Direction
    abstract fun reverse(): Direction
}
