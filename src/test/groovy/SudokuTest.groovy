import spock.lang.Specification
import spock.lang.Unroll

class SudokuSpec extends Specification {

    Board board = new Board()

    def setup() {
        def n = null
        board.fillRow(0, [n, 6, 1, 7, n, n, n, 3, 4])
        board.fillRow(1, [n, n, 2, n, 3, n, n, n, n])
        board.fillRow(2, [4, 9, n, 6, 1, n, n, n, 8])
        board.fillRow(3, [n, 1, 8, 5, n, n, n, n, n])
        board.fillRow(4, [7, 4, 5, n, n, n, 3, 9, 1])
        board.fillRow(5, [n, n, n, n, n, 4, 6, 8, n])
        board.fillRow(6, [3, n, n, n, 2, 7, n, 4, 9])
        board.fillRow(7, [n, n, n, n, 6, n, 7, n, n])
        board.fillRow(8, [1, 2, n, n, n, 5, 8, 6, n])
    }

    def "proper cell value"() {
        expect:
        board.printBoard()
    }

    @Unroll
    def "get row"() {
        expect:
        List cells = board.getRowCells(a)
        assert 9 == cells.size()
        assert cells*.val.containsAll(b)

        where:
        a || b
        0 || [6, 1, 7, 3, 4]
        1 || [2, 3]
        2 || [4, 9, 6, 1, 8]
        3 || [1, 8, 5]
        4 || [7, 4, 5, 3, 9, 1]
        5 || [4, 6, 8]
        6 || [3, 2, 7, 4, 9]
        7 || [6, 7]
        8 || [1, 2, 5, 8, 6]
    }

    @Unroll
    def "get column"() {
        expect:
        List cells = board.getColumnCells(a)
        assert 9 == cells.size()
        assert cells*.val.containsAll(b)

        where:
        a || b
        0 || [4, 7, 3, 1]
        1 || [6, 9, 1, 4, 2]
        2 || [1, 2, 8, 5]
        3 || [7, 6, 5]
        4 || [3, 1, 2, 6]
        5 || [4, 7, 5]
        6 || [3, 6, 7, 8]
        7 || [3, 9, 8, 4, 6]
        8 || [4, 8, 1, 9]
    }

    def "test simple solution"() {
        expect:
        SimpleSolutionFinder solutionFinder = new SimpleSolutionFinder()
        solutionFinder.answer(board, 4, 3)
        assert board.getCell(4, 3).possibleVal.containsAll([2, 6, 8])
    }

    def "most frequent number"() {
        expect:
        assert board.mostFrequentNumber() == 6
    }

    @Unroll
    def "cluster contains"() {
        expect:
        assert board.clusterContains(a, b, c)

        where:
        a | b || c
        0 | 0 || 6
        0 | 1 || 4
        1 | 1 || 9
        1 | 2 || 2
        0 | 4 || 6
        1 | 5 || 3
        2 | 4 || 7
        1 | 7 || 8
        2 | 7 || 3
        0 | 8 || 3
        3 | 0 || 8
        3 | 1 || 5
        4 | 4 || 4
        3 | 6 || 6
        3 | 7 || 9
        6 | 0 || 3
        7 | 4 || 6
        8 | 8 || 8
    }
}