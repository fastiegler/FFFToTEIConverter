package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import converter.RefactorXML;

public class ConverterGUI extends JDialog implements ActionListener {
	private JTextField txtPathInput;
	private JTextField txtPathOutput;
	private JButton okButton;
	private File inputFile=new File(System.getProperty("user.dir")+"\\XML_TEI\\in.FFF");
	private File outputFile=new File(System.getProperty("user.dir")+"\\XML_TEI\\out.xml");
	private JLabel lblMsg;
	private JProgressBar progressBar;
	private int PROGRESS_MAX= 5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ConverterGUI dialog = new ConverterGUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ConverterGUI() {
		setTitle("FFF to XML Converter");
		setResizable(false);
		setBounds(100, 100, 499, 265);
		getContentPane().setLayout(null);
		
		JLabel lblFolioFlatFile = new JLabel("Folio Flat input File:");
		lblFolioFlatFile.setBounds(10, 11, 105, 27);
		getContentPane().add(lblFolioFlatFile);
		
		txtPathInput = new JTextField();
		txtPathInput.setText(System.getProperty("user.dir")+"\\XML_TEI\\in.FFF");
		txtPathInput.setFont(new Font("Tahoma", Font.PLAIN, 8));
		txtPathInput.setEnabled(false);
		txtPathInput.setBounds(10, 37, 302, 20);
		getContentPane().add(txtPathInput);
		txtPathInput.setColumns(10);
		
		JLabel lblOutputFile = new JLabel("TEI output File:");
		lblOutputFile.setBounds(10, 68, 105, 20);
		getContentPane().add(lblOutputFile);
		
		txtPathOutput = new JTextField();
		txtPathOutput.setText(System.getProperty("user.dir")+"\\XML_TEI\\out.xml");
		txtPathOutput.setFont(new Font("Tahoma", Font.PLAIN, 8));
		txtPathOutput.setEnabled(false);
		txtPathOutput.setBounds(10, 96, 302, 20);
		getContentPane().add(txtPathOutput);
		txtPathOutput.setColumns(10);
		
		JButton btnBrowseInput = new JButton("Durchsuchen");
		btnBrowseInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				 int returnVal = fc.showOpenDialog(ConverterGUI.this);
			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			        	
			        	inputFile = fc.getSelectedFile();
			            txtPathInput.setText(inputFile.getAbsolutePath());
			        } //else canceled by user so do nothing
			}
		});
		btnBrowseInput.setBounds(322, 36, 151, 23);
		getContentPane().add(btnBrowseInput);
		
		JButton btnBrowseOutput = new JButton("Durchsuchen");
		btnBrowseOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				 int returnVal = fc.showOpenDialog(ConverterGUI.this);
			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			        	
			        	outputFile = fc.getSelectedFile();
			            txtPathOutput.setText(outputFile.getAbsolutePath());
			        } //else canceled by user so do nothing
			}
		});		
		btnBrowseOutput.setBounds(322, 95, 151, 23);
		getContentPane().add(btnBrowseOutput);
		
		lblMsg = new JLabel("");
		lblMsg.setForeground(Color.RED);
		lblMsg.setBounds(10, 159, 463, 20);
		getContentPane().add(lblMsg);
		
		progressBar = new JProgressBar();
		progressBar.setEnabled(false);
		progressBar.setBackground(Color.GRAY);
		progressBar.setForeground(Color.GREEN);
		progressBar.setBounds(10, 134, 463, 20);
		getContentPane().add(progressBar);
		{
			okButton = new JButton("Konvertierung starten");
			okButton.setBounds(137, 190, 209, 23);
			getContentPane().add(okButton);
			okButton.setActionCommand("OK");
			okButton.addActionListener(this);
			getRootPane().setDefaultButton(okButton);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == okButton) {
			//init Progessbar...
			progressBar.setVisible(true);
			progressBar.setMaximum(PROGRESS_MAX); //Die Anzahl der Schritte. TODO: Muss noch angepasst werden...
			progressBar.setValue(0);
			lblMsg.setForeground(Color.RED);
			lblMsg.setText("Konvertierung wurde gestartet...");
			RefactorXML converter = new RefactorXML(inputFile, outputFile);
			converter.startConversion();
			lblMsg.setForeground(Color.GREEN);
			lblMsg.setText("Konvertierung erfolgreich");
			progressBar.setValue(PROGRESS_MAX);
			
		}
		
	}
	
	public void makeProgessSteps() {
		if(progressBar.getValue()<PROGRESS_MAX)
		progressBar.setValue(progressBar.getValue() + 1);
	}
}
