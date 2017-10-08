package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.FlowLayout;

public class addToRepGUI {
	JFrame frmZuErsetzendenAusdruck;
	private JTextField textReplace;
	private JTextField textWith;
	private JPanel panel;
	private JButton btnCancel;
	private JPanel panel_1;

	public addToRepGUI() {
		frmZuErsetzendenAusdruck = new JFrame("");
		frmZuErsetzendenAusdruck.setTitle("Zu ersetzenden Ausdruck hinzuf\u00FCgen");
		frmZuErsetzendenAusdruck.setSize(510, 353);
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
		panel_1.setLayout(new BorderLayout(0, 0));
		
		textReplace = new JTextField();
		panel_1.add(textReplace, BorderLayout.WEST);
		textReplace.setToolTipText("Ersetzte diesen Ausdruck");
		textReplace.setColumns(10);
		
		textWith = new JTextField();
		panel_1.add(textWith, BorderLayout.EAST);
		textWith.setToolTipText("Ausdruck wird hiermit ersetzt");
		textWith.setColumns(10);
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newConverter.converter.addToRepConfig(textReplace.getText(), textWith.getText());
				reset();
			}
		});
		frmZuErsetzendenAusdruck.setVisible(true);
	}
	private void reset() {
		newConverter.converter.enableGUI();
		frmZuErsetzendenAusdruck.setVisible(false);
		textReplace.setText("");
		textWith.setText("");
	}
}
