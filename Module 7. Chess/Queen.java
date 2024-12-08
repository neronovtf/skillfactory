public class Queen extends ChessPiece{
    public Queen(String color){
        super.color = color;
    }

    @Override
    public String getColor(){
        return super.color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn){
        if (chessBoard.checkPos(line) && 
            chessBoard.checkPos(column) && 
            chessBoard.checkPos(toLine) && 
            chessBoard.checkPos(toColumn)
        ){
            if((line != toLine) && (column != toColumn)){
                return super.killChessWay(chessBoard, line, column, toLine, toColumn);
            }
            else return false;
        }
        else return false;
    }

    @Override
    public String getSymbol(){
        return "Q";
    }
};