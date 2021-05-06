package simulator.view;


import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JComboBox;
import java.awt.Choice;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

import org.json.JSONObject;

import simulator.control.Controller;


public class ForceLawsDialog extends JDialog {
	private JLabel info;
	private lawsTableModel table;
	private DefaultComboBoxModel comboBox;
	private Controller _ctrl;
	private JSONObject forcelawsinfo;

	public ForceLawsDialog(Controller ctrl) {
		super();
		_ctrl = ctrl;
		initGUI();
	}
	
	private class lawsTableModel extends AbstractTableModel{

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

	private void initGUI() {
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.setSize(700,600);
		info = new JLabel();
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setAlignmentX(Component.CENTER_ALIGNMENT);
		info.setText("<html><p style=\"width:500px\">"+"Select a force law and provide values for the parameters in the value column"
				+ " (default values are used for the parameters with no value) "+"</p></html>");
		getContentPane().add(info);
		
		
		Object[] possibilities = {"Newton Laws of Universal Gravitation",
				"Moving Towards a Fixed Point","No Force"};
		
		table = new lawsTableModel();
		//getContentPane().add(table);
		
		comboBox = new DefaultComboBoxModel<>();
		for ( JSONObject j : forcelawsinfo) {
			comboBox.addElement(j.getString("desc"));
		}
		Comboboxf = new JComboBox<>(comboBox);
		//comboBox.add(jo.desc);
		this.setVisible(true);
		
	}
	
}
