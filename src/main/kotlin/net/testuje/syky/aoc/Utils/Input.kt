package net.testuje.syky.aoc.Utils

class Input {
    fun get(file: String): List<String> {
        return this::class.java.getResource(file).readText().split(System.lineSeparator())
    }
}