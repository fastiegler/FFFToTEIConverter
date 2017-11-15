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

import newConverter.converterThread;

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
	JButton btnTest;
	JButton btnAddtorep;
	JButton btnAddtodel;
	JButton btnChangeds;
	private JLabel lblLblreplacedcount;
	private JLabel lblLbldeletedcount;

	public gui() {
		frmKonvertierer = new JFrame("converter");
		frmKonvertierer.setTitle("Konvertierer");
		frmKonvertierer.setSize(800, 700);
		frmKonvertierer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmKonvertierer.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		frmKonvertierer.getContentPane().add(panel_1, BorderLayout.CENTER);
		progressBar = new JProgressBar();
		btnAddtodel = new JButton("Zu l\u00F6schenden Ausdruck hinzuf\u00FCgen");
		btnAddtodel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				progressBar.setValue(0);
				frmKonvertierer.setEnabled(false);
				// open dialog
				if (addDelGUI == null) {
					addDelGUI = new addToDelGUI();
				} else {
					addDelGUI.frmZuLschendenAusdruck.setVisible(true);
				}
			}
		});
		panel_1.add(btnAddtodel);

		btnAddtorep = new JButton("Zu ersetzenden Ausdruck hinzuf\u00FCgen");
		btnAddtorep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				progressBar.setValue(0);
				frmKonvertierer.setEnabled(false);
				// open dialog
				if (addRepGUI == null) {
					addRepGUI = new addToRepGUI();
				} else {
					addRepGUI.frmZuErsetzendenAusdruck.setVisible(true);
				}
			}
		});
		panel_1.add(btnAddtorep);

		btnChangeds = new JButton("Variablen setzen");
		btnChangeds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				progressBar.setValue(0);
				frmKonvertierer.setEnabled(false);
				// open dialog
				if (setDGUI == null) {
					setDGUI = new setDsGUI();
				} else {
					setDGUI.frame.setVisible(true);
				}
			}
		});
		panel_1.add(btnChangeds);

		btnTest = new JButton("Konvertieren");
		panel_1.add(btnTest);
		
		lblLblreplacedcount = new JLabel("Anzahl ausgetauschter Abschnitte: ");
		panel_1.add(lblLblreplacedcount);
		
		lblLbldeletedcount = new JLabel("Anzahl gel\u00F6schter Abschnitte: ");
		panel_1.add(lblLbldeletedcount);

		progressBar.setForeground(Color.GREEN);
		frmKonvertierer.getContentPane().add(progressBar, BorderLayout.SOUTH);

		JPanel panel = new JPanel();
		DropPane drop = new DropPane();
		panel.add(drop);
		drop.setSize(50, 50);
		drop.setBackground(Color.GRAY);
		drop.setToolTipText("Ziehe .FFF Datei und .xml Datei(optional) in dieses Feld");
		frmKonvertierer.getContentPane().add(panel, BorderLayout.NORTH);
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				progressBarReset();
				Thread conv = new Thread(new converterThread());
				conv.start();
				disableButtons();
			}
		});
		frmKonvertierer.setVisible(true);
		frmKonvertierer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void enableGUI() {
		frmKonvertierer.setEnabled(true);
	}

	public void setProgressBarMaximum(int max) {
		progressBar.setMaximum(max);
	}

	public void progressBarDoStep() {
		progressBar.setValue(progressBar.getValue() + 1);
		double green = ((double) progressBar.getValue()) / ((double) progressBar.getMaximum()) * 256.0;
		progressBar.setForeground(new Color(255 - (int) green, (int) green, 0));
	}

	public void progressBarEnd() {
		progressBar.setValue(progressBar.getMaximum());
		progressBar.setForeground(Color.GREEN);
	}

	public void progressBarReset() {
		progressBar.setValue(0);
		progressBar.setForeground(Color.RED);
	}

	public void enableButtons() {
		btnTest.setEnabled(true);
		btnAddtorep.setEnabled(true);
		btnAddtodel.setEnabled(true);
		btnChangeds.setEnabled(true);
	}

	public void disableButtons() {
		btnTest.setEnabled(false);
		btnAddtorep.setEnabled(false);
		btnAddtodel.setEnabled(false);
		btnChangeds.setEnabled(false);
	}

	public void endConvertion(int countDeletions, int countReplacements) {
		this.progressBarEnd();
		this.lblLbldeletedcount.setText(this.lblLbldeletedcount.getText().split(": ")[0]+": "+countDeletions);
		this.lblLblreplacedcount.setText(this.lblLblreplacedcount.getText().split(": ")[0]+": "+countReplacements);
		this.enableButtons();
	}

}
