package src;

import java.util.Random;

/**
 * Computer-Spieler mit Schwierigkeitsgrad Einfach
 * Schiffssetzung und Schüsse hauptsächlich zufällig
 */
public class ComPlayerEasy extends ComPlayer{
    public ComPlayerEasy(int rows, int[] ships) throws Exception{
        super(rows, ships);
    }
}
