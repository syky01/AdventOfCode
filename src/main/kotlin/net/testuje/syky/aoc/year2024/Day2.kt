package net.testuje.syky.aoc.year2024

import net.testuje.syky.aoc.Utils.Input
import kotlin.collections.windowed


class Day2 {
    private val input = Input()

    fun run() {

        part1()
        part2()

        return
    }

    private fun part1() {
        val result = input.get(FILE).map { line ->
            checkSafety(line.split(" ").map { it.toInt() })
        }.count { it }

        println("Part1: $result")
    }

    private fun part2() {
        val result = input.get(FILE).map { line ->
            val levels = line.split(" ").map { it.toInt() }
            if (checkSafety(levels)) {
                return@map true
            }
            for (i in levels.indices) {
                val check = checkSafety(levels.filterIndexed { index, _ -> index != i })
                if (check) return@map true
            }
            return@map false
        }.count { it }

        println("Part2: $result")
    }

    private fun checkSafety(levels: List<Int>): Boolean {
        val safetyChecks = levels.windowed(2).map { (i, j) -> compareLevels(i, j) }.toList()
        return when {
            safetyChecks.contains(0) -> false
            safetyChecks.distinct().size == 1 -> true
            else -> false
        }
    }


    private fun compareLevels(first: Int, second: Int): Int {
        return if (first < second && second - first <= 3) {
            1
        } else if (first > second && first - second <= 3) {
            -1
        } else {
            0
        }
    }


    companion object {
        const val FILE = "/2024/input2_test"
    }
}