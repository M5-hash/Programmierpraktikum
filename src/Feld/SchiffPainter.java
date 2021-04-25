package Feld;

public class SchiffPainter {

    private int[][] SchiffPos;


    public SchiffPainter(int[][] schiffPos) {
        SchiffPos = schiffPos;
    }

    private boolean Enemyplacement() {

        for (int i = 0; i < SchiffPos.length; i++) {
            for (int j = 0; j < SchiffPos[0].length; j++) {
                switch (SchiffPos[i][j]) {
                    case 0:
                        break;                                          //An dieser position befindet sich kein Schiff bzw. Schiffteil

                    case 1:                                             //Hier befindet sich ein zerstörtes Schiffteil
                        drawEntity(1);
                    case 2:                                             //Hier befindet ein zerstörtes Schiff
                        drawEntity(2);
                    case 3:                                             //Hier befindet sich ein intaktes Schiffteil
                        drawEntity(3);

                    default:                                        //Invalid Entry mein Gamer, dass sollte aber nicht vorkommen.
                        System.out.println("Invalid entry");
                        return false;
                }
            }
        }
        return true;
    }

    private void drawEntity(int Ship) {
        //Bildloader

    }

    public boolean Schiffplatzieren(int x, int y , int groesse, boolean vertikal){

        int var = vertikal ? 1 : 0 ;
        int j = y ;

        for(int i = x ; i <= x + var * groesse; i++){
            System.out.println(i + " , " + j);
            for(; j >= y - Math.pow(-1, var) * groesse; j-- ){
                System.out.println(i + " , " + j);
            }
        }

        return true ;
    }


}
