abstract public class ChessPiece{
    public ChessPiece[][] board = new ChessPiece[8][8];
    protected String color;
    public boolean check = true;

    abstract public String getColor();

    abstract public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn);

    protected boolean killChessWay(ChessBoard chessBoard, int line, int column, int toLine, int toColumn){
        boolean znakOneIsPlus = (toLine - line >= 0);
        boolean znakTwoIsPlus = (toColumn - column >= 0);

        ChessPiece chess; // создам переменную тут, чтобы она не создавать постоянно в цикле заново, а лишь переопределялась
        boolean isStop = false;
        boolean canGo = true;

        for(int i = line; (znakOneIsPlus ? i < toLine + 1 : i > toLine - 1);){
            for(int j = column; (znakTwoIsPlus ? j < toColumn + 1 : j > toColumn - 1);){
                if(znakOneIsPlus) i++; else i--;
                if(znakTwoIsPlus) j++; else j--;

                chess = chessBoard.board[i][j];
                if(chess != null){ // если в этой ячейке что-то есть, нас это интересует
                    if(chess.color.equals(this.color)){ // если цвета совпадают, мы не можем пройти через эту фигуру
                        isStop = true; // флаг для оставновки двух циклов
                        canGo = false; // движение от отчки А до точки Б, запрещено
                    } // ежели цвета будут совпадать, то будет кушаться, всё окей =)
                }
                if(isStop) break;
            }
            if(isStop) break;
        }
        return canGo;
    }

    abstract public String getSymbol();
};