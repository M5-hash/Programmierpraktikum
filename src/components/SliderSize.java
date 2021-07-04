package src.components;

import javax.swing.*;

import static src.FontLoader.Pokemon;

/**
 * Custom Slider für das Sizemenü
 */
public class SliderSize extends JSlider {
    /**
     * Default Werte des Sliders
     */
    public SliderSize(){
        setOrientation(JSlider.HORIZONTAL);
        setMaximum(30);
        setMinimum(0);
        setValue(0);
        setPaintTicks(true);
        setPaintTrack(true);
        setPaintLabels(true);
        setMajorTickSpacing(5);
        setOpaque(false);
        setFont(Pokemon.deriveFont(11f));
    }
}
