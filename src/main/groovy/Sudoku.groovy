class Cell {
    /**
     * Value
     */
    Integer val

    /**
     * Possible values
     */
    Set<Integer> possibleVal = []

    /**
     * Preset cell or empty cell to be filled
     */
    Boolean userFilled

    String toString() {
        if (val) "" + val
        else "_"
    }
}

class Board {

    Cell[][] cells = new Cell[9][9]

    Board() {
        for (i in 0..8) {
            for (j in 0..8) {
                cells[i][j] = new Cell()
            }
        }
    }

    def printBoard() {
        cells.eachWithIndex { row, rowIndex ->
            if (rowIndex % 3 == 0) {
                println "====================================="
            } else {
                println "-------------------------------------"
            }

            row.eachWithIndex { col, colIndex ->
                if (colIndex % 3 == 0) {
                    if (colIndex != 0) print " "
                    //print "\u02016 "
                    print "\u2225 "
                } else {
                    print " | "
                }
                print col
            }
            print " \u2225"
            println()
        }
        println "====================================="
        return true
    }

    def fill(int x, int y, int val) {
        cells[x][y].val = val
    }

    def fillRow(Integer rowX, List<Integer> rowVal) {
        cells[rowX].eachWithIndex { cell, index ->
            cell.val = rowVal[index]
        }
    }

    def answer(Integer x, Integer y, Integer val) {
        cells[x][y].possibleVal = []
        cells[x][y].val = val
    }

    def possibleAnswers(Integer x, Integer y, List<Integer> possibleAnswers) {
        cells[x][y].possibleVal = possibleAnswers
    }

    List<Cell> getRowCells(Integer x) {
        return cells[x] as List
    }

    List<Cell> getColumnCells(Integer y) {
        List<Cell> columnCells = []
        for (i in 0..8) {
            columnCells << cells[i][y]
        }

        columnCells
    }

    List<Cell> getAllCells() {
        List<Cell> allCells = []
        for (x in 0..8) {
            for (y in 0..8) {
                allCells << cells[x][y]
            }
        }

        allCells
    }

    Cell getCell(Integer x, Integer y) {
        cells[x][y]
    }

    Integer mostFrequentNumber() {
        Map<Integer,Integer> count = getAllCells().countBy { it.val }

        Integer mostFrequentNumber = null
        Integer mostFrequentCount = 0
        count.each { k, v ->
            if (k != null && mostFrequentCount < v) {
                mostFrequentCount = v
                mostFrequentNumber = k
            }
        }

        mostFrequentNumber
    }

    List<Cell> getClusterCells(Integer x, Integer y) {
        List<Cell> clusters = []
        for (i in findClusterRange(x)) {
            for (j in findClusterRange(y)) {
                clusters << cells[i][j]
            }
        }

        clusters
    }

    /**
     * For internal cluster coordinate calculation
     * @param p
     */
    def findClusterRange(Integer p) {
        int start = 0
        int end = 0

        if (p == 0) {
            start = 0
        } else {
            start = p - (p % 3)
        }
        end = start + 2

        return start..end
    }

    boolean clusterContains(Integer x, Integer y, Integer val) {
        getClusterCells(x, y)*.val.contains(val)
    }
}

interface PartSolutionFinder {

}

/**
 * Given 9 cells, a row, column or just a group.
 * If 8 out of 9 filled, return the only possible value.
 */
class SimpleSolutionFinder implements PartSolutionFinder {

    /**
     *
     * @param board
     * @param cells
     * @param x
     * @param y
     */
    def answer(Board board, int x, int y) {
        List<Integer> completeGroup = [1,2,3,4,5,6,7,8,9]

        List<Integer> rowAnswer = completeGroup[0..-1]
//        Collections.copy(rowAnswer, completeGroup)
        List rowCells = board.getRowCells(x)
        rowAnswer.removeAll(rowCells*.val)
        if (rowAnswer.size() == 1) {
            board.answer(x, y, rowAnswer.get(0))
        } else {
            board.possibleAnswers(x, y, rowAnswer)
        }
    }
}

Board board = new Board()
board.fill(0, 0, 8)
println board.mostFrequentNumber()