package utils;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class gui {
    public static void changePanel(JPanel source, JPanel target){
        source.setLayout(new BorderLayout());
        source.removeAll();
        source.add(target, BorderLayout.CENTER);
        source.revalidate();
        source.repaint();
    }
}
