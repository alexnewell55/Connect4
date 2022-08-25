import javax.swing.*;
import java.awt.*;

public class RoundButton extends JButton {

    /*

    This class is used to add curves to JButtons.
    Credit must go to Stack Overflow user BackSlash.
    The source can be found here:
    https://stackoverflow.com/questions/15025092/border-with-rounded-corners-transparency

     */

    Dimension arcs;

    public RoundButton(Integer dimension) {
        super();
        arcs = new Dimension(dimension, dimension);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;

        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width,
                height, arcs.width, arcs.height);
        graphics.setColor(getForeground());
        graphics.drawRoundRect(0, 0, width,
                height, arcs.width, arcs.height);

    }

}

