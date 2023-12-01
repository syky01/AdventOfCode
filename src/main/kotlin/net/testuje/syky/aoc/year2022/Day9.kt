package net.testuje.syky.aoc.year2022

import net.testuje.syky.aoc.Utils.Input
import java.awt.Point
import kotlin.math.abs


/**
 * Advent of Code 9/2022
 * more details at https://adventofcode.com/2022/day/9
 */
class Day9 {
    private val input = Input()

    fun run() {
        simulate(2)
        simulate(10)

        return
    }

    private fun simulate(numberOfPlanks: Int) {
        val map = mutableListOf<Pair<Int, Int>>()
        map.add(Pair(50, 250))

        val knots = List(numberOfPlanks) { Point(50, 250) }
        val size = knots.size - 1

        input.get(FILE).forEach { data ->
            val command = data.split(" ")
            val count = command[1].toInt()
            when (command[0]) {
                "U" -> {
                    for (i in 1..count) {
                        knots[0].translate(0, -1)
                        alignTail(map, knots)

                    }
                }

                "R" -> {
                    for (i in 1..count) {
                        knots[0].translate(1, 0)
                        alignTail(map, knots)
                    }
                }

                "D" -> {
                    for (i in 1..count) {
                        knots[0].translate(0, 1)
                        alignTail(map, knots)
                    }
                }

                "L" -> {
                    for (i in 1..count) {
                        knots[0].translate(-1, 0)
                        alignTail(map, knots)
                    }
                }
            }
        }
        println(map.distinct().count())
    }

    private fun alignTail(map: MutableList<Pair<Int, Int>>, knots: List<Point>) {
        for (i in 0 until knots.size-1) {
            val head = knots[i]
            val tail = knots[i+1]
            if (abs(head.x - tail.x) > 1 && abs(head.y - tail.y) > 1) {
                val x = if (tail.x < head.x) head.x - 1 else head.x + 1
                val y = if (tail.y < head.y) head.y - 1 else head.y + 1
                tail.move(x, y)
            } else if (head.x - tail.x > 1) {
                tail.move(head.x - 1, head.y)
            } else if (tail.x - head.x > 1) {
                tail.move(head.x + 1, head.y)
            } else if (head.y - tail.y > 1) {
                tail.move(head.x, head.y - 1)
            } else if (tail.y - head.y > 1) {
                tail.move(head.x, head.y + 1)
            }
        }
        val tail = knots.last()
        map.add(Pair(tail.x, tail.y))
    }

    companion object {
        const val FILE = "/2022/input9"
        const val FILE_TEST = "/2022/input9-test"
        const val FILE_TEST2 = "/2022/input9-test2"
    }
}

