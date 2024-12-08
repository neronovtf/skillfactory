public class Horse extends ChessPiece{
    public Horse(String color){
        super.color = color;
    }

    @Override
    public String getColor(){
        return super.color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int startLine, int startColumn, int endLine, int endColumn){

        if(startLine != endLine && startColumn != endColumn){
            if (checkPos(startLine) && checkPos(startColumn)) {
                if (checkPos(endLine) && checkPos(endColumn)) {
                    if((Math.abs(startLine - endLine) + Math.abs(startColumn - endColumn)) == 3){
                        return super.killChessWay(chessBoard, startLine, startColumn, endLine, endColumn);
                    }
                    else return false;
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
        return "H";
    }
};