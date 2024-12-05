fun main() = Day05.main()

object Day05 {
    fun main() {
        val testInput = readInput("Day05_test")
        val input = readInput("Day05")

        part1(testInput).also { println("Part 1, test input: $it"); check(it == 143) }
        part1(input).also { println("Part 1, real input: $it"); /* check(it == 2) */ }

        part2(testInput).also { println("Part 2, test input: $it");  check(it == 123) }
        part2(input).also { println("Part 2, real input: $it"); check(it == 6456) }
    }

    private fun part1(input: List<String>): Int {
        val (rules, lists) = parseInput(input)
        return lists.filter { isInCorrectOrder(rules, it) }.sumOf { it[it.size / 2] }
    }

    private fun part2(input: List<String>): Int {
        val (rules, lists) = parseInput(input)

        return lists.mapNotNull {
            if (!isInCorrectOrder(rules, it)) {
                correctOrder(rules, it)
            } else {
                null
            }
        }.sumOf { it[it.size / 2] }
    }

    private fun correctOrder(
        rules: Map<Int, Set<Int>>,
        list: List<Int>,
    ): List<Int> {
        var resultList = list
        var valuePositions = resultList.mapIndexed { index, i -> i to index }.toMap()
        var i = 0
        while (i < resultList.size) {
            val leftValue = resultList[i]
            val newPosition = rules[leftValue]?.mapNotNull { rightValue ->
                valuePositions[rightValue]?.let {
                    if (it >= i) {
                        it + 1
                    } else {
                        null
                    }
                }
            }?.maxOrNull() ?: i
            if (newPosition > i) {
                val newList = resultList.toMutableList().apply {
                    add(newPosition, leftValue)
                    removeAt(i)
                }
                resultList = newList
                valuePositions = resultList.mapIndexed { index, x -> x to index }.toMap()
            } else {
                i += 1
            }
        }
        return resultList
    }

    private fun isInCorrectOrder(
        rules: Map<Int, Set<Int>>,
        list: List<Int>,
        numberPositions: Map<Int, Int> = list.mapIndexed { index, i -> i to index }.toMap()
    ): Boolean {
        list.forEachIndexed { printPosition, value ->
            rules[value]?.forEach { rightValue ->
                val rightPrintedPosition = numberPositions[rightValue]
                if (rightPrintedPosition != null && rightPrintedPosition < printPosition) {
                    return false
                }
            }
        }
        return true
    }

    private fun parseInput(input: List<String>): Pair<Map<Int, Set<Int>>, List<List<Int>>> {
        val emptyPos = input.indexOf("")
        val rules: Map<Int, Set<Int>> = input.take(emptyPos)
            .map {
                val (left: Int, right: Int) = it.split('|').map { it.toInt() }
                left to right
            }
            .groupBy({it.first}, {it.second})
            .mapValues { it.value.toSet() }
        val lists = input.drop(emptyPos + 1).map { it.split(',').map { it.toInt() } }
        return rules to lists
    }
}
