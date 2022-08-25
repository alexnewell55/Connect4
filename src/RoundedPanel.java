import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {

    /*

    This class is used to add curves to JPanels.
    Credit must go to Stack Overflow user BackSlash.
    The source can be found here:
    https://stackoverflow.com/questions/15025092/border-with-rounded-corners-transparency

     */

    protected Dimension arcs;

    public RoundedPanel(Integer dimension) {
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
