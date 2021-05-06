package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	// ...
	Controller _ctrl;

	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);
		
		JPanel menu = new ControlPanel(_ctrl);
		mainPanel.add(menu);
		BodiesTable bodies = new BodiesTable(_ctrl);
		mainPanel.add(bodies);
		Viewer viewer = new Viewer(_ctrl);
		mainPanel.add(viewer);
		StatusBar status = new StatusBar(_ctrl);
		mainPanel.add(status);
		this.setMinimumSize(new Dimension(700,900));
		// TODO complete this method to build the GUI
		// ..
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	// other private/protected methods
	// ...
}
