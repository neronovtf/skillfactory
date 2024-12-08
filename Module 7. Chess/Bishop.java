public class Bishop extends ChessPiece{
    public Bishop(String color){
        super.color = color;
    }

    @Override
    public String getColor(){
        return super.color;
    }
    
    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int startLine, int startColumn, int endLine, int endColumn){
        if (checkPos(startLine) && checkPos(startColumn) && checkPos(endLine) && checkPos(endColumn)){
            if((startLine != endLine) && (startColumn != endColumn)){
                if(super.killChessWay(chessBoard, startLine, startColumn, endLine, endColumn)){
                    return (Math.abs(endLine - startLine) == Math.abs(endColumn - startColumn));
                }
                else return false;
            }
            else return false;
        }
        else return false;
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }

    @Override
    public String getSymbol(){
        return "B";
    }
};