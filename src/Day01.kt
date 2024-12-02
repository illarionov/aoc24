import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val pairs = input.map {
            val (a,b) = it.split("\\s+".toRegex()).map { it.toInt() }
            a to b
        }
        val list1 = pairs.map { it.first }.sorted()
        val list2 = pairs.map { it.second }.sorted()

        val sum = list1.zip(list2) { a, b -> abs(a-b) }.sum()
        return sum
    }

    fun part2(input: List<String>): Int {
        val pairs = input.map {
            val (a,b) = it.split("\\s+".toRegex()).map { it.toInt() }
            a to b
        }
        val list1 = pairs.map { it.first }.sorted()
        val list2 = pairs.map { it.second }.groupingBy { it }.eachCount()

        val sum = list1.sumOf {
            (list2[it] ?: 0) * it
        }
        return sum
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput: List<String> = readInput("Day01_test")
    println(part1(testInput))
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
