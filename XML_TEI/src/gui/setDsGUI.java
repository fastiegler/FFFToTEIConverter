package gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.FlowLayout;

public class setDsGUI {
	JFrame frame;
	private JTextField textD_0;
	private JTextField textD_2;
	private JTextField textD_1;
	private JPanel panel;
	private JButton btnCancel;

	public setDsGUI() {
		frame = new JFrame("");
		frame.setSize(800, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		textD_0 = new JTextField();
		frame.getContentPane().add(textD_0, BorderLayout.WEST);
		textD_0.setColumns(10);
		
		textD_1 = new JTextField();
		frame.getContentPane().add(textD_1, BorderLayout.CENTER);
		textD_1.setColumns(10);
		
		textD_2 = new JTextField();
		frame.getContentPane().add(textD_2, BorderLayout.EAST);
		textD_2.setColumns(10);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnConfirm = new JButton("confirm");
		panel.add(btnConfirm);
		
		btnCancel = new JButton("cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		panel.add(btnCancel);
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newConverter.converter.replaceDs(textD_0.getText(), textD_1.getText(), textD_2.getText());
				reset();
			}
		});
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private void reset() {
		newConverter.converter.enableGUI();
		frame.setVisible(false);
		textD_0.setText("");
		textD_1.setText("");
		textD_2.setText("");
	}
}
