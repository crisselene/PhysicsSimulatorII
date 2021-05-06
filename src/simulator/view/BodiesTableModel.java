package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {
	// ...
	private List<Body> _bodies;

	BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return 0;
		// TODO complete
	}

	@Override
	public int getColumnCount() {
		return 0;
		// TODO complete
	}

	@Override
	public String getColumnName(int column) {
		return null;
		// TODO complete
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Body b = _bodies.get(rowIndex);
		if(columnIndex == 0) {
			return b.getId();
		}
		return columnIndex;
		// TODO complete
	}
	// SimulatorObserver methods
	// ...

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}
}
