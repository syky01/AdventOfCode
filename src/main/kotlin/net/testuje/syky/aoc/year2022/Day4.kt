package net.testuje.syky.aoc.year2022

import net.testuje.syky.aoc.Utils.Input

class Day4 {
    private val input = Input()
    fun run() {
        val jobs = mutableListOf<Array<IntRange>>()
        input.get(FILE).forEach { line ->
            val inputs = line.split(",")
            val input1 = inputs[0].split("-").map { it.toInt() }
            val input2 = inputs[1].split("-").map { it.toInt() }
            jobs.add(arrayOf(IntRange(input1[0], input1[1]), IntRange(input2[0], input2[1])))
        }
        part1(jobs)
        part2(jobs)

        return
    }

    private fun part1(jobsInput: List<Array<IntRange>>) {
        var points = 0
        jobsInput.forEach { jobs ->
            val jobSize1 = jobs[0].count()
            val jobSize2 = jobs[1].count()
            val intersectCount = jobs[0].intersect(jobs[1]).count()
            if (jobSize1 < jobSize2) {
                if (intersectCount == jobSize1) {
                    points++
                }
            } else {
                if (intersectCount == jobSize2) {
                    points++
                }
            }
        }
        println("Part1: $points")

    }

    private fun part2(jobsInput: List<Array<IntRange>>) {
        var points = 0
        jobsInput.forEach { jobs ->
            if (!jobs[0].intersect(jobs[1]).isEmpty()) {
                points++
            }
        }
        println("Part1: $points")

    }

    companion object {
        const val FILE = "/2022/input4"
        const val FILE_TEST = "/2022/input4-test"
    }
}