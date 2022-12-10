package net.testuje.syky.aoc.Utils

import net.testuje.syky.aoc.year2022.Day1

class Input {
    fun get(file: String): List<String> {
        return this::class.java.getResource(file).readText().split(System.lineSeparator())
    }
}