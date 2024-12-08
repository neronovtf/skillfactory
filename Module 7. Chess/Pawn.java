public class Pawn extends ChessPiece{

    public Pawn(String color){
        super.color = color;
    }

    @Override
    public String getColor(){
        return super.color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int startLine, int startColumn, int endLine, int endColumn){

        if(startLine != endLine && super.killChessWay(chessBoard, startLine, startColumn, endLine, endColumn)){
            if (checkPos(startLine) && checkPos(startColumn)) {
                if (checkPos(endLine) && checkPos(endColumn)) {
                    if(endLine > startLine){
                        if(endLine - startLine == 1 && (Math.abs(endColumn - startColumn) <= 1)){
                            if(startLine == 1){
                                return (endLine == 3 || endLine == 2);
                            }
                            else{
                                return (endLine == startLine + 1);
                            }
                        }
                        else return false;
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
        return pos > 0 && pos <= 7;
    }

    @Override
    public String getSymbol(){
        return "P";
    }
};