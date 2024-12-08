public class King extends ChessPiece implements KingInterface{
    public King(String color){
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
                int point = Math.abs(toLine - line) + Math.abs(toColumn - column);
                
                if(point == 1 || point == 2){
                    return super.killChessWay(chessBoard, line, column, toLine, toColumn);
                }
                else return false;
            }
            else return false;
        }
        else return false;
    }

    @Override
    public String getSymbol(){
        return "K";
    }

    @Override
    public boolean isUnderAttack(ChessBoard board, int line, int column){
        ChessPiece piece;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                piece = super.board[i][j];
                if(piece != null){
                    if(piece.color.equals(super.color) == false){
                        return piece.canMoveToPosition(board, i, j, line, column);
                    }
                }
            }
        }

        return true;
    }
};