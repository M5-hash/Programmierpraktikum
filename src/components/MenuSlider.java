package src.components;

import javax.swing.*;

public class MenuSlider extends JSlider {
    public MenuSlider(){
        setOrientation(JSlider.HORIZONTAL);
        setMaximum(10);
        setMinimum(1);
        setValue(1);
        setPaintTicks(true);
        setPaintTrack(true);
        setPaintLabels(true);
        setMajorTickSpacing(1);
    }
}
