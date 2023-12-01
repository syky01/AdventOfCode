package net.testuje.syky.aoc.year2022

import net.testuje.syky.aoc.Utils.Input

class Day7 {
    private val input = Input()
    fun run() {
        val root = Folder("/")
        var files = root

        //for simplicity first line with `cd /` was removed
        input.get(FILE).forEach { line ->
            val command = line.split(" ")
            when {
                command[1] == "cd" -> {
                    files = if (command[2] == "..") {
                        files.parent!!
                    } else {
                        files.folders[command[2]]!!
                    }
                }

                command[0] == "dir" -> {
                    files.folders[command[1]] = Folder(command[1], files)
                }

                command[0].contains(Regex("\\d+")) -> {
                    val size = command[0].toInt()
                    files.files[command[1]] = size
                    addSize(files, size)
                }

                else -> {
                    // Do nothing, just $ ls - we don't need this
                }
            }
        }

        part1(root)
        part2(root)

        return
    }

    private fun part1(files: Folder) {
        val maxFolderSize = 100000
        val result = countAllFoldersUnder(files, maxFolderSize)
        println("Part1: $result ")

    }

    private fun part2(root: Folder) {
        val needed = DISK_NEEDED - (DISK_SIZE - root.size)
        val folders = findFoldersOver(root, needed)
        folders.sort()
        println("Part2: ${folders.first()}")
    }

    private fun findFoldersOver(folder: Folder, needed: Int): MutableList<Int> {
        val sizes = mutableListOf<Int>()
        folder.folders.forEach {
            sizes.addAll(findFoldersOver(it.value, needed))
        }
        if (folder.size >= needed) {
            sizes.add(folder.size)
        }
        return sizes
    }

    private fun countAllFoldersUnder(files: Folder, maxSize: Int): Int {
        var foldersSize = 0
        files.folders.forEach {
            foldersSize += countAllFoldersUnder(it.value, maxSize)
        }
        return if (files.size < maxSize) {
            foldersSize + files.size
        } else {
            foldersSize
        }

    }

    private fun addSize(files: Folder, size: Int) {
        files.size += size
        if (files.parent != null) {
            addSize(files.parent, size)
        }
    }

    companion object {
        const val FILE = "/2022/input7"
        const val FILE_TEST = "/2022/input7-test"
        const val DISK_SIZE = 70000000
        const val DISK_NEEDED = 30000000

        data class Folder(
            val name: String,
            val parent: Folder? = null,
            val files: MutableMap<String, Int> = mutableMapOf(),
            val folders: MutableMap<String, Folder> = mutableMapOf(),
            var size: Int = 0
        )
    }
}