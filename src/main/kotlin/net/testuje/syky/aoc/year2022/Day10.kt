package net.testuje.syky.aoc.year2022

import net.testuje.syky.aoc.Utils.Input
import kotlin.math.abs

/**
 * Advent of Code 10/2022
 * more details at https://adventofcode.com/2022/day/10
 */
class Day10 {
    private val input = Input()

    fun run() {
        val readings = arrayListOf(20, 60, 100, 140, 180, 220)
        val data = input.get(FILE)
        val screen = mutableListOf<Char>()
        var pointer = 0
        var sumOfSignals = 0
        var registry = 1
        var addInProgress = 0
        var i = 1
        do {
            if (readings.contains(i)) {
                sumOfSignals += registry * i
            }
            if (addInProgress != 0) {
                registry += addInProgress
                addInProgress = 0
            } else {
                val command = data[pointer++].split(" ")
                if (command[0] == "addx") {
                    addInProgress = command[1].toInt()
                }
            }
            if (abs(i % 40 - registry) <= 1) {
                screen.add('#')
            } else {
                screen.add('.')
            }

        } while (i++ < 240)


        println("Part1: $sumOfSignals")
        println("Part2: ")
        screen.chunked(40).forEach {
            println(it)
        }
    }

    companion object {
        const val FILE = "/2022/input10"
    }
}