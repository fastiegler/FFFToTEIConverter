package gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class addToDelGUI {
	JFrame frmZuLschendenAusdruck;
	private JTextField textReplace;
	private JPanel panel;
	private JButton btnCancel;
	private JTable table;
	private DefaultTableModel tableModel;
	private JPanel panel_1;
	private JButton btnNewButton;
	private JButton btnRem;
	private JButton btnUp;
	private JButton btnDown;

	public addToDelGUI(Object[][] data,String[] columnNames) {
		frmZuLschendenAusdruck = new JFrame("");
		frmZuLschendenAusdruck.setTitle("Zu l\u00F6schenden Ausdruck hinzuf\u00FCgen");
		frmZuLschendenAusdruck.setSize(800, 700);
		frmZuLschendenAusdruck.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmZuLschendenAusdruck.getContentPane().setLayout(new BorderLayout(0, 0));
		textReplace = new JTextField();
		textReplace.setToolTipText("Zu ersetzender Ausdruck\r\nz.B: \"<EL>\"");
		frmZuLschendenAusdruck.getContentPane().add(textReplace, BorderLayout.NORTH);
		textReplace.setColumns(10);
		
		panel = new JPanel();
		frmZuLschendenAusdruck.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnConfirm = new JButton("Best\u00E4tigen");
		panel.add(btnConfirm);
		
		btnCancel = new JButton("Abbrechen");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		panel.add(btnCancel);
		
		panel_1 = new JPanel();
		frmZuLschendenAusdruck.getContentPane().add(panel_1, BorderLayout.CENTER);
		
		btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableModel.addRow(new Object[] {tableModel.getRowCount()+1,textReplace.getText()});
				table.setModel(tableModel);
			}
		});
		
		btnRem = new JButton("REm");
		btnRem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectionModel().isSelectionEmpty())return;
				int sel=table.getSelectedRow();
				tableModel.removeRow(table.getSelectedRow());
				for (int i = 0; i < tableModel.getRowCount(); i++) {
					tableModel.setValueAt(i+1, i, 0);
				}
				if(tableModel.getRowCount()>0)
				table.setRowSelectionInterval(sel, sel);
			}
		});
		
		btnUp = new JButton("Up");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectionModel().isSelectionEmpty())return;
				if(table.getSelectedRow()==0)return;
				tableModel.moveRow(table.getSelectedRow(), table.getSelectedRow(), table.getSelectedRow()-1);
				table.setRowSelectionInterval(table.getSelectedRow()-1, table.getSelectedRow()-1);
				for (int i = 0; i < tableModel.getRowCount(); i++) {
					tableModel.setValueAt(i+1, i, 0);
				}
			}
		});
		panel_1.add(btnUp);
		
		btnDown = new JButton("Down");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectionModel().isSelectionEmpty())return;
				if(table.getSelectedRow()==tableModel.getRowCount()-1)return;
				tableModel.moveRow(table.getSelectedRow(), table.getSelectedRow(), table.getSelectedRow()+1);
				table.setRowSelectionInterval(table.getSelectedRow()+1, table.getSelectedRow()+1);
				for (int i = 0; i < tableModel.getRowCount(); i++) {
					tableModel.setValueAt(i+1, i, 0);
				}
			}
		});
		panel_1.add(btnDown);
		panel_1.add(btnRem);
		panel_1.add(btnNewButton);
		tableModel=new DefaultTableModel(data,columnNames);
		table = new JTable(  );
		table.setModel(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel_1.add(new JScrollPane(table));
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				newConverter.converter.addToDelConfig(textReplace.getText());
				newConverter.converter.addToDelConfig(tableModel.getDataVector());
				reset();
			}
		});
		frmZuLschendenAusdruck.setVisible(true);
	}
	private void reset() {
		newConverter.converter.enableGUI();
		frmZuLschendenAusdruck.setVisible(false);
		textReplace.setText("");
	}

}
