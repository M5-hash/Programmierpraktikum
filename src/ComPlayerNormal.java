package src;

public class ComPlayerNormal extends ComPlayer{
    public ComPlayerNormal(int rows, int[] ships) throws Exception {
        super(rows, ships);
        this.difficulty = 1;
    }

    @Override
    public int[] doNextShot() {
        //TODO
        return new int[0];
    }
}
