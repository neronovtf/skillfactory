public class ChessBoard {
    public ChessPiece[][] board = new ChessPiece[8][8]; // creating a field for game
    String nowPlayer;

    public ChessBoard(String nowPlayer) {
        this.nowPlayer = nowPlayer;
    }

    public String nowPlayerColor() {
        return this.nowPlayer;
    }

    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {
        if (checkPos(startLine) && checkPos(startColumn)) {

            if (!nowPlayer.equals(board[startLine][startColumn].getColor())) return false;

            if (board[startLine][startColumn].canMoveToPosition(this, startLine, startColumn, endLine, endColumn)) {

                if (board[startLine][startColumn].getSymbol().equals("K") ||  // check position for castling
                        board[startLine][startColumn].getSymbol().equals("R")) {
                    board[startLine][startColumn].check = false;
                }

                board[endLine][endColumn] = board[startLine][startColumn]; // if piece can move, we moved a piece
                board[startLine][startColumn] = null; // set null to previous cell
                this.nowPlayer = this.nowPlayerColor().equals("White") ? "Black" : "White";

                return true;
            } else return false;
        } else return false;
    }

    public void printBoard() {  //print board in console
        System.out.println("Turn " + nowPlayer);
        System.out.println();
        System.out.println("Player 2(Black)");
        System.out.println();
        System.out.println("\t0\t1\t2\t3\t4\t5\t6\t7");
        for (int i = 7; i > -1; i--) {
            System.out.print(i + "\t");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(".." + "\t");
                } else {
                    System.out.print(board[i][j].getSymbol() + board[i][j].getColor().substring(0, 1).toLowerCase() + "\t");
                }
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("Player 1(White)");
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }

    public boolean castling0() {
        if (nowPlayer.equals("White")) {
            if (board[0][0] == null || board[0][4] == null) return false;
            if (board[0][0].getSymbol().equals("R") && board[0][4].getSymbol().equals("K") && // check that King and Rook
                    board[0][1] == null && board[0][2] == null && board[0][3] == null) {              // never moved
                if (board[0][0].getColor().equals("White") && board[0][4].getColor().equals("White") &&
                        board[0][0].check && board[0][4].check &&
                        !new King("White").isUnderAttack(this, 0, 2)) { // check that position not in under attack
                    board[0][4] = null;
                    board[0][2] = new King("White");   // move King
                    board[0][2].check = false;
                    board[0][0] = null;
                    board[0][3] = new Rook("White");   // move Rook
                    board[0][3].check = false;
                    nowPlayer = "Black";  // next turn
                    return true;
                } else return false;
            } else return false;
        } else {
            if (board[7][0] == null || board[7][4] == null) return false;
            if (board[7][0].getSymbol().equals("R") && board[7][4].getSymbol().equals("K") && // check that King and Rook
                    board[7][1] == null && board[7][2] == null && board[7][3] == null) {              // never moved
                if (board[7][0].getColor().equals("Black") && board[7][4].getColor().equals("Black") &&
                        board[7][0].check && board[7][4].check &&
                        !new King("Black").isUnderAttack(this, 7, 2)) { // check that position not in under attack
                    board[7][4] = null;
                    board[7][2] = new King("Black");   // move King
                    board[7][2].check = false;
                    board[7][0] = null;
                    board[7][3] = new Rook("Black");   // move Rook
                    board[7][3].check = false;
                    nowPlayer = "White";  // next turn
                    return true;
                } else return false;
            } else return false;
        }
    }

    public boolean castling7() {
        int line = nowPlayer.equals("White") ? 0 : 7;
        int kingColumn = 4;
        int rookLeftColumn = 0;
        int rookRightColumn = 7;

        ChessPiece king = board[line][kingColumn]; // проверяем короля
        if (king != null){
            if(!king.getSymbol().equals("K")) return false; // на месте короля, не король
        }
        else return false; // если короля нет на месте, всё пропало

        ChessPiece rookLeft = board[line][rookLeftColumn];  // проверяем левую ладью
        boolean rookLeftCheck = (rookLeft != null && rookLeft.getSymbol().equals("R")); // только если ячейка занята и занята ладьёй, тогда флаг в TRUE
        ChessPiece rookRight = board[line][rookRightColumn];  // проверяем правую ладью
        boolean rookRightCheck = (rookRight != null && rookRight.getSymbol().equals("R")); // только если ячейка занята и занята ладьёй, тогда флаг в TRUE

        if(rookLeftCheck == false && rookRightCheck == false) return false; // ни одной ладьи нет на своём месте ...

        int countStep = 2; // кол-во шагов, которые делает король во время рокировки

        boolean leftCastling = true; // Пробуем сделать короткую рокировку, в лево. Проверим все условия
        if(rookLeftCheck){
            // Шаг №1. Проверяем что между левой ладьёй и королём, нет других фигур
            for(int i = (rookLeftColumn + 1); i < kingColumn; i++){
                if(board[line][i] != null){
                    leftCastling = false;
                    break;
                }
            }

            if(leftCastling){
                // Шаг №2. Проверяем что место, куда попадёт король, не находится под ударом соперника, а то будет сразу мат ...
                if(king instanceof KingInterface kingInterface){
                    // Если позиция, куда придёт король находится под атакой, значит рокировка запрещена ...
                    leftCastling = !(kingInterface.isUnderAttack(this, line, (kingColumn - countStep)));
                }
            }

            if(leftCastling){
                // Шаг №3. Все проверки прошли успешно, делаем рокировку !!
                board[line][kingColumn - countStep] = king; // Переместили короля, на новое место
                board[line][kingColumn] = null; // Очистили ячейку, которую занимал король ранее
                board[line][kingColumn - 1] = rookLeft; // Переместили левую ладью
                board[line][rookLeftColumn] = null; // Очистили ячейку, которую занимала левая ладья ранее
            }
        }

        boolean rightCastling = true; // Пробуем сделать длинную рокировку, в право. Проверим все условия
        if(rookRightCheck){
            // Шаг №1. Проверяем что между королём и правой ладьёй, нет других фигур
            for(int i = (kingColumn + 1); i < rookRightColumn; i++){
                if(board[line][i] != null){
                    rightCastling = false;
                    break;
                }
            }

            if(rightCastling){
                // Шаг №2. Проверяем что место, куда попадёт король, не находится под ударом соперника, а то будет сразу мат ...
                if(king instanceof KingInterface kingInterface){
                    // Если позиция, куда придёт король находится под атакой, значит рокировка запрещена ...
                    rightCastling = !(kingInterface.isUnderAttack(this, line, (kingColumn + countStep)));
                }
            }

            if(rightCastling){
                // Шаг №3. Все проверки прошли успешно, делаем рокировку !!
                board[line][kingColumn + countStep] = king; // Переместили короля, на новое место
                board[line][kingColumn] = null; // Очистили ячейку, которую занимал король ранее
                board[line][kingColumn + 1] = rookLeft; // Переместили левую ладью
                board[line][rookLeftColumn] = null; // Очистили ячейку, которую занимала левая ладья ранее
            }
        }

        // сделали все проверки, пробовали сделать рокировку в лево, и в право. Возможно, что-то и удалось.
        return leftCastling || rightCastling;
    }
}