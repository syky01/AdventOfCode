package net.testuje.syky.aoc.year2022

import net.testuje.syky.aoc.Utils.Input

/**
 * Advent of Code 8/2022
 * more details at https://adventofcode.com/2022/day/8
 */
class Day8 {
    private val input = Input()

    fun run() {
        val map = mutableListOf<MutableList<Int>>()
        input.get(FILE).forEach { data ->
            val row = mutableListOf<Int>()
            data.toCharArray().forEach {
                row.add(it.digitToInt())
            }
            map.add(row)
        }

        part1(map)
        part2(map)

        return
    }

    /**
     * We will crate 4 new arrays. Each new array will have max values in selected direction.
     * First value will be always -1 (First row (even 0) is always visible.
     * E.g. if values in row will be 1,3,0,5,0, new Array will be -1,1,3,3,5
     *
     * This new maps are created with first pass through original map. With second pass, we will compare original value
     * with all new values. If any value in new maps is lower than original, then it's visible from edge,
     * and we will add one point to result.
     */
    private fun part1(map: MutableList<MutableList<Int>>) {
        var points = 0
        val mapTop = MutableList(map.size) { MutableList(map.size) { -1 } }
        val mapRight = MutableList(map.size) { MutableList(map.size) { -1 } }
        val mapBottom = MutableList(map.size) { MutableList(map.size) { -1 } }
        val mapLeft = MutableList(map.size) { MutableList(map.size) { -1 } }
        val size = map.size - 1 //Align with map indexes
        map.withIndex().forEach { row ->
            val r = row.index
            row.value.withIndex().forEach { col ->
                val c = col.index
                if (r > 0) {//TOP & BOTTOM
                    if (mapTop[r - 1][c] > map[r - 1][c]) {
                        mapTop[r][c] = mapTop[r - 1][c]
                    } else {
                        mapTop[r][c] = map[r - 1][c]
                    }

                    if (mapBottom[size - r + 1][c] > map[size - r + 1][c]) {
                        mapBottom[size - r][c] = mapBottom[size - r + 1][c]
                    } else {
                        mapBottom[size - r][c] = map[size - r + 1][c]
                    }
                }
                if (c > 0) { // LEFT & RIGHT
                    if (mapLeft[r][c - 1] > map[r][c - 1]) {
                        mapLeft[r][c] = mapLeft[r][c - 1]
                    } else {
                        mapLeft[r][c] = map[r][c - 1]
                    }

                    if (mapRight[r][size - c + 1] > map[r][size - c + 1]) {
                        mapRight[r][size - c] = mapRight[r][size - c + 1]
                    } else {
                        mapRight[r][size - c] = map[r][size - c + 1]
                    }
                }

            }
        }
        map.withIndex().forEach { row ->
            val r = row.index
            row.value.withIndex().forEach { col ->
                val c = col.index
                if (
                    map[r][c] > mapTop[r][c] ||
                    map[r][c] > mapRight[r][c] ||
                    map[r][c] > mapBottom[r][c] ||
                    map[r][c] > mapLeft[r][c]
                ) {
                    points++
                }
            }

        }
        println("Part1: $points")

    }

    private fun part2(map: MutableList<MutableList<Int>>) {
        var points = 0
        val size = map[0].size - 1 //Align with map indexes
        map.withIndex().forEach { row ->
            val r = row.index
            if (r == 0 || r == size) { // edges are always 0
                return@forEach
            }
            row.value.withIndex().forEach inner@{ col ->
                val c = col.index
                if (c == 0 || r == size) { //edges are always 0
                    return@inner
                }
                var score = 1
                val value = map[r][c]

                score *= calculateVisibleTrees( //TOP
                    IntProgression.fromClosedRange(r - 1, 0, -1).toList(),
                    IntRange(c, c).toList(),
                    value,
                    map
                )
                score *= calculateVisibleTrees( //DOWN
                    IntProgression.fromClosedRange(r + 1, size, 1).toList(),
                    IntRange(c, c).toList(),
                    value,
                    map
                )
                score *= calculateVisibleTrees(//LEFT
                    IntRange(r, r).toList(),
                    IntProgression.fromClosedRange(c - 1, 0, -1).toList(),
                    value,
                    map
                )
                score *= calculateVisibleTrees(//RIGHT
                    IntRange(r, r).toList(),
                    IntProgression.fromClosedRange(c + 1, size, 1).toList(),
                    value,
                    map
                )

                if (score > points) {
                    points = score
                }
            }
        }

        println("Part2: $points")
    }

    private fun calculateVisibleTrees(
        x: List<Int>,
        y: List<Int>,
        value: Int,
        map: MutableList<MutableList<Int>>
    ): Int {
        var visibility = 0
        x.forEach { r ->
            y.forEach { c ->
                visibility++
                if (map[r][c] >= value) {
                    return visibility
                }
            }
        }
        return visibility
    }


    companion object {
        const val FILE = "/2022/input8"
        const val FILE_TEST = "/2022/input8-test"
    }
}