package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

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
		frmZuErsetzendenAusdruck.setSize(1000, 200);
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
