package net.testuje.syky.aoc.year2024

import net.testuje.syky.aoc.Utils.Input
import kotlin.math.abs

class Day1 {
    private val input = Input()

    fun run() {
        part1()
        part2()

        return
    }

    private fun part1() {
        val lists = input.get(FILE).map { line ->
            val (first, second) = line.split("   ")
            first.toInt() to second.toInt()
        }.toList().unzip()
        val result = lists.first.sorted().zip(lists.second.sorted()).sumOf { (first, second) ->
            abs(first - second)
        }
        println("Part1: $result")

    }

    private fun part2() {
        val lists = input.get(FILE).map { line ->
            val (first, second) = line.split("   ")
            first.toInt() to second.toInt()
        }.toList().unzip()
        val result = lists.first.sumOf { number ->
            lists.second.count { it == number } * number
        }
        println("Part2: $result")

    }

    companion object {
        const val FILE = "/2024/input1_test"
    }
}