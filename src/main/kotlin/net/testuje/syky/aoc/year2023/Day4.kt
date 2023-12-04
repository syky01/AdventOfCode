package net.testuje.syky.aoc.year2023

import net.testuje.syky.aoc.Utils.Input
import kotlin.math.pow

class Day4 {
    private val input = Input()

    fun run() {
        part1()
        part2()

        return
    }

    private fun part1() {

        val result = input.get(FILE).sumOf { line ->
            val count = processLine(line)
            2f.pow(count - 1).toInt() // for 0, conversion to int effectively rounds it to 0
        }

        println("Part1: $result")

    }

    private fun part2() {
        val cards = mutableMapOf<Int, Int>()
        input.get(FILE).forEachIndexed { lineNumber, line ->
            cards[lineNumber] = (cards[lineNumber] ?: 0) + 1
            val winningCount = processLine(line)
            for (i in lineNumber + 1..lineNumber + winningCount) {
                cards[i] = (cards[i] ?: 0) + 1 * (cards[lineNumber] ?: 1)
            }
        }
        val result = cards.values.sum()

        println("Part2: $result")

    }

    private fun processLine(line: String): Int {
        val regex = Regex("""(\d+)""")
        val (winningNumbers, cardNumbers) = line.substringAfter(":")
            .split("|")
            .map {
                regex.findAll(it).map { v -> v.value }
            }
        return winningNumbers.count { cardNumbers.contains(it) }
    }


    companion object {
        const val FILE = "/2023/input4.my"

    }
}


