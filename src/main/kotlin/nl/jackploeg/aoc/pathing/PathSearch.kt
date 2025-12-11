package nl.jackploeg.aoc.pathing

fun bfs(nodes: Map<String, List<String>>, fromNode: String, toNode: String, maxDepth: Int): Int {
    var queue = mapOf(fromNode to 1)

    var routeCount = 0
    for (i in 0..maxDepth) {
        val nextQueue = mutableMapOf<String, Int>()
        for (entry in queue) {
            val connectedNodes = nodes.getOrDefault(entry.key, emptyList())
            for (targetNode in connectedNodes) {
                if (targetNode == toNode) {
                    routeCount += entry.value
                } else {
                    nextQueue[targetNode] = nextQueue.getOrDefault(targetNode, 0) + entry.value
                }
            }
            queue = nextQueue
        }
    }
    return routeCount
}

fun countPaths(nodes: Map<String, List<String>>, nodesOnPath: List<String>, maxDepth: Int): Long {
    var totalPaths: Long = 1
    for (i in 1..<nodesOnPath.size) {
        val subPathCount = bfs(nodes, nodesOnPath[i - 1], nodesOnPath[i], maxDepth)
        totalPaths *= subPathCount
    }
    return totalPaths
}
