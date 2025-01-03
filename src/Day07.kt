import kotlin.math.pow

fun main() = Day07.main()

object Day07 {
    fun main() {
        val testInput = readInput("Day07_test")
        val input = readInput("Day07")

        part1(testInput).also { println("Part 1, test input: $it"); check(it == 3749L) }
        part1(input).also { println("Part 1, real input: $it"); check(it == 2501605301465) }

        part2(testInput).also { println("Part 2, test input: $it"); check(it == 11387L) }
        part2(input).also { println("Part 2, real input: $it"); /* check(it == 2) */ }
    }

    private fun parseInput(input: List<String>): List<Pair<Long, List<Long>>> = input.map { inputString ->
        val totalSumm = inputString.substringBefore(": ").toLong()
        val components = inputString.substringAfter(": ").split(' ').map { it.toLong() }
        totalSumm to components
    }

    private fun part1(input: List<String>): Long {
        return parseInput(input).sumOf {
            if (isSolvable(it.first, it.second)) {
                it.first
            } else {
                0
            }
        }
    }

    private fun isSolvable(totalSumm: Long, components: List<Long>): Boolean {
        val max = 1.shl(components.size - 1)
        for (combination in 0 until max) {
            val result = components.reduceIndexed { index, acc, number ->
                if ((combination and 1.shl(index - 1)) == 0) {
                    acc + number
                } else {
                    acc * number
                }
            }
            if (result == totalSumm) {
                return true
            }
        }
        return false
    }

    private fun part2(input: List<String>): Long {
        return parseInput(input).sumOf {
            if (isSolvable2(it.first, it.second)) {
                it.first
            } else {
                0
            }
        }
    }

    private fun isSolvable2(totalSumm: Long, components: List<Long>): Boolean {
        val max = 3f.pow(components.size - 1).toLong()
        for (combination in 0 until max) {
            val result = components.reduceIndexed { index, acc, number ->
                val bit = (combination / 3f.pow(index - 1)).toLong()
                when (bit % 3) {
                    0L -> acc + number
                    1L -> acc * number
                    2L -> "$acc$number".toLong()
                    else -> error("Should not be called")
                }
            }
            if (result == totalSumm) {
                return true
            }
        }
        return false
    }

}
