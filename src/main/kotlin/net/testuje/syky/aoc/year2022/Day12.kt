package net.testuje.syky.aoc.year2022

import net.testuje.syky.aoc.Utils.Input


/**
 * Advent of Code 12/2022
 * more details at https://adventofcode.com/2022/day/12
 */
class Day12 {
    private val input = Input()

    fun run() {
        val startData = parseInitialData(input.get(FILE))

        part1(startData)
        part2(startData)

        return
    }

    private fun part1(startData: Data) {
        val map = MutableList(startData.heightMap.size) { MutableList(startData.heightMap[0].size) { 9999 } }

        createStep(startData.heightMap, map, startData.start)

        println("Part1: ${map[startData.end.first][startData.end.second]}")
    }

    private fun part2(startData: Data) {
        val map = MutableList(startData.heightMap.size) { MutableList(startData.heightMap[0].size) { 9999 } }
        startData.heightMap.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { lineIndex, point ->
                if (point == 'a') {
                    createStep(startData.heightMap, map, Pair(rowIndex, lineIndex))
                }
            }
        }


        println("Part2: ${map[startData.end.first][startData.end.second]}")
    }

    private fun createStep(
        heightMap: MutableList<MutableList<Char>>,
        map: MutableList<MutableList<Int>>,
        point: Pair<Int, Int>,
        steps: Int = 0,
        lastHeight: Char = 'a'
    ) {
        val height = heightMap[point.first][point.second]

        if (height.code > lastHeight.code + 1) {
            return
        }

        if (map[point.first][point.second] > steps) {
            map[point.first][point.second] = steps
        } else {
            return
        }

        if (point.first > 0) {
            createStep(heightMap, map, Pair(point.first - 1, point.second), steps + 1, height)
        }
        if (point.second > 0) {
            createStep(heightMap, map, Pair(point.first, point.second - 1), steps + 1, height)
        }
        if (point.first < map.size - 1) {
            createStep(heightMap, map, Pair(point.first + 1, point.second), steps + 1, height)
        }
        if (point.second < map[0].size - 1) {
            createStep(heightMap, map, Pair(point.first, point.second + 1), steps + 1, height)
        }
    }

    private fun parseInitialData(data: List<String>): Data {
        val rows = data.count()
        val lines = data[0].count()
        var start: Pair<Int, Int> = Pair(0, 0)
        var end: Pair<Int, Int> = Pair(0, 0)

        val map = MutableList(rows) { MutableList(lines) { ' ' } }
        data.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { lineIndex, point ->
                when (point) {
                    'S' -> {
                        start = Pair(rowIndex, lineIndex)
                        map[rowIndex][lineIndex] = 'a'
                    }

                    'E' -> {
                        end = Pair(rowIndex, lineIndex)
                        map[rowIndex][lineIndex] = 'z'
                    }

                    else -> {
                        map[rowIndex][lineIndex] = point
                    }
                }
            }
        }
        return Data(start, end, map)
    }


    companion object {
        const val FILE = "/2022/input12.my"

        class Data(val start: Pair<Int, Int>, val end: Pair<Int, Int>, val heightMap: MutableList<MutableList<Char>>)
    }
}

