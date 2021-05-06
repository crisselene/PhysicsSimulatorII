package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;
import javax.swing.JTable;

public class BodiesTable extends JPanel {
	
	private JTable table;
	
	BodiesTable(Controller ctrl) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(
		BorderFactory.createLineBorder(Color.black, 2),
		"Bodies",
		TitledBorder.LEFT, TitledBorder.TOP));
		
		table = new JTable();
		add(table, BorderLayout.CENTER);
		// TODO complete
		}
}
