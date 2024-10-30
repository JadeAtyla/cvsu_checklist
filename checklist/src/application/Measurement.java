package application;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Measurement {
	Dimension measure = Toolkit.getDefaultToolkit().getScreenSize();
	int HEIGHT = (measure.height<905) ? measure.height : 905;
	int WIDTH = (measure.width<1390) ? measure.width : 1390;
}
