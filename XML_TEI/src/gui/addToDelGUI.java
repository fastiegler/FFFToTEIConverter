package gui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.FlowLayout;

public class addToDelGUI {
	JFrame frmZuLschendenAusdruck;
	private JTextField textReplace;
	private JPanel panel;
	private JButton btnCancel;

	public addToDelGUI() {
		frmZuLschendenAusdruck = new JFrame("");
		frmZuLschendenAusdruck.setTitle("Zu l\u00F6schenden Ausdruck hinzuf\u00FCgen");
		frmZuLschendenAusdruck.setSize(800, 700);
		frmZuLschendenAusdruck.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmZuLschendenAusdruck.getContentPane().setLayout(new BorderLayout(0, 0));
		textReplace = new JTextField();
		textReplace.setToolTipText("Zu ersetzender Ausdruck\r\nz.B: \"<EL>\"");
		frmZuLschendenAusdruck.getContentPane().add(textReplace, BorderLayout.CENTER);
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
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newConverter.converter.addToDelConfig(textReplace.getText());
				reset();
			}
		});
		frmZuLschendenAusdruck.setVisible(true);
		frmZuLschendenAusdruck.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private void reset() {
		newConverter.converter.enableGUI();
		frmZuLschendenAusdruck.setVisible(false);
		textReplace.setText("");
	}
}
