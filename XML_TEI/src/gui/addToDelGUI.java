package gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
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
	private JPanel panel_1;

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
		table = new JTable( data,columnNames );
		panel_1.add(new JScrollPane(table));
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newConverter.converter.addToDelConfig(textReplace.getText());
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
