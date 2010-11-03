package dpclock.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ItemTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6315477172440073226L;
	
	
	private List<Object> itemList;

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	public int getColumnCount() {
		return 1;
	}

	public String getColumnName(int column) {
		return "Items";
	}

	public void setItemList(List<Object> itemList) {
		this.itemList = itemList;
	}

	public int getRowCount() {
		return itemList.size();
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		itemList.set(rowIndex, value);
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return itemList.get(rowIndex);
	}
}