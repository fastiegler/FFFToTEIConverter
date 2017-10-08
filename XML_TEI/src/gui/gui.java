package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import java.awt.Color;


public class gui {
	private JFrame frmKonvertierer;
	addToDelGUI addDelGUI;
	addToRepGUI addRepGUI;
	setDsGUI setDGUI;
	JProgressBar progressBar;
	public gui(){
	frmKonvertierer = new JFrame("converter");
	frmKonvertierer.setTitle("Konvertierer");
        frmKonvertierer.setSize(800,700);
	frmKonvertierer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frmKonvertierer.getContentPane().setLayout(new BorderLayout(0, 0));
	
	JPanel panel_1 = new JPanel();
	frmKonvertierer.getContentPane().add(panel_1, BorderLayout.CENTER);
	progressBar = new JProgressBar();
	JButton btnAddtodel = new JButton("Zu l\u00F6schenden Ausdruck hinzuf\u00FCgen");
	btnAddtodel.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			progressBar.setValue(0);
			frmKonvertierer.setEnabled(false);
			//open dialog
			if (addDelGUI==null) {
				addDelGUI=new addToDelGUI();
			}else {
				addDelGUI.frmZuLschendenAusdruck.setVisible(true);
			}
		}
	});
	panel_1.add(btnAddtodel);
	
	JButton btnAddtorep = new JButton("Zu ersetzenden Ausdruck hinzuf\u00FCgen");
	btnAddtorep.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			progressBar.setValue(0);
			frmKonvertierer.setEnabled(false);
			//open dialog
			if (addRepGUI==null) {
				addRepGUI=new addToRepGUI();
			}else {
				addRepGUI.frmZuErsetzendenAusdruck.setVisible(true);
			}
		}
	});
	panel_1.add(btnAddtorep);
	
	JButton btnChangeds = new JButton("Variablen setzen");
	btnChangeds.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			progressBar.setValue(0);
			frmKonvertierer.setEnabled(false);
			//open dialog
			if (setDGUI==null) {
				setDGUI=new setDsGUI();
			}else {
				setDGUI.frame.setVisible(true);
			}
		}
	});
	panel_1.add(btnChangeds);
	
	JButton btnTest = new JButton("Konvertieren");
	panel_1.add(btnTest);
	
	
	progressBar.setForeground(Color.GREEN);
	frmKonvertierer.getContentPane().add(progressBar, BorderLayout.SOUTH);
	
	JPanel panel = new JPanel();
	DropPane drop =new DropPane();
	panel.add(drop);
	drop.setSize(50, 50);
	drop.setBackground(Color.GRAY);
	drop.setToolTipText("Ziehe .FFF Datei und .xml Datei(optional) in dieses Feld");
	frmKonvertierer.getContentPane().add(panel, BorderLayout.NORTH);
	btnTest.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			progressBar.setValue(0);
			newConverter.converter.btnTest();
			progressBar.setValue(progressBar.getMaximum());
		}
	});
	frmKonvertierer.setVisible(true);
	frmKonvertierer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}
	public void enableGUI() {
		frmKonvertierer.setEnabled(true);
	}
	
	
}
