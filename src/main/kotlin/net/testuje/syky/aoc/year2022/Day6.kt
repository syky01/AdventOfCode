package net.testuje.syky.aoc.year2022

import net.testuje.syky.aoc.Utils.Input

class Day6 {
    private val input = Input()
    fun run() {
        val length = 4
        val part1result = find(4)
        println("Part1: $part1result")

        val part2result = find(14)
        println("Part2: $part2result")
    }

    private fun find(length: Int): Int {
        val data = input.get(FILE)[0]
        for (i in 0 until data.length - length) {
            val code = data.subSequence(i, i + length)
            if (code.toList().toCharArray().distinct().size == length) {
                return (i + length)
            }
        }
        return -1
    }

    companion object {
        const val FILE = "/2022/input6"
    }
}