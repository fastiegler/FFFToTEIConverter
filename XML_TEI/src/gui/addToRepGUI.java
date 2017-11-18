package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

public class addToRepGUI {
	JFrame frmZuErsetzendenAusdruck;
	private JTextField textReplace;
	private JTextField textWith;
	private JPanel panel;
	private JButton btnCancel;
	private JPanel panel_1;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton btnNewButton;
	private JButton btnRem;
	private JButton btnUp;
	private JButton btnDown;
	private JPanel panel_2;

	public addToRepGUI(Object[][] data,String[] columnNames) {
		frmZuErsetzendenAusdruck = new JFrame("");
		frmZuErsetzendenAusdruck.setTitle("Zu ersetzenden Ausdruck hinzuf\u00FCgen");
		frmZuErsetzendenAusdruck.setSize(1000, 316);
		frmZuErsetzendenAusdruck.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmZuErsetzendenAusdruck.getContentPane().setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		frmZuErsetzendenAusdruck.getContentPane().add(panel, BorderLayout.SOUTH);
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
		frmZuErsetzendenAusdruck.getContentPane().add(panel_1, BorderLayout.CENTER);
		
		textReplace = new JTextField();
		textReplace.setToolTipText("Ersetzte diesen Ausdruck");
		textReplace.setColumns(10);
		
		textWith = new JTextField();
		textWith.setToolTipText("Ausdruck wird hiermit ersetzt");
		textWith.setColumns(10);
		
		JLabel lblZuErsetzen = new JLabel("Zu ersetzen:");
		
		JLabel lblMit = new JLabel("mit:");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblZuErsetzen)
							.addContainerGap(423, Short.MAX_VALUE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblMit)
							.addContainerGap(438, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addComponent(textReplace, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
								.addComponent(textWith, GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE))
							.addContainerGap())))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblZuErsetzen)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textReplace, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblMit)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textWith, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(218, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newConverter.converter.addToRepConfig(tableModel.getDataVector());
//				newConverter.converter.addToRepConfig(textReplace.getText(), textWith.getText());
				reset();
			}
		});
		btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableModel.addRow(new Object[] {tableModel.getRowCount()+1,textReplace.getText()});
				table.setModel(tableModel);
			}
		});
		panel_2 = new JPanel();
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
		panel_2.add(btnUp);
		
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
		panel_2.add(btnDown);
		panel_2.add(btnRem);
		panel_2.add(btnNewButton);
		tableModel=new DefaultTableModel(data,columnNames);
		table = new JTable(  );
		table.setModel(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel_2.add(new JScrollPane(table));
		

		frmZuErsetzendenAusdruck.getContentPane().add(panel_2, BorderLayout.NORTH);
		frmZuErsetzendenAusdruck.setVisible(true);
	}
	private void reset() {
		newConverter.converter.enableGUI();
		frmZuErsetzendenAusdruck.setVisible(false);
		textReplace.setText("");
		textWith.setText("");
	}
}
