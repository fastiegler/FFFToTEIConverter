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
	private File inputFile;
	private File outputFile;
	private JLabel lblMsg;
	private JProgressBar progressBar;

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
		setBounds(100, 100, 499, 265);
		getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(10, 183, 463, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane);
			{
				okButton = new JButton("Konvertierung starten");
				okButton.setActionCommand("OK");
				okButton.addActionListener(this);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		JLabel lblFolioFlatFile = new JLabel("Folio Flat File:");
		lblFolioFlatFile.setBounds(10, 11, 105, 27);
		getContentPane().add(lblFolioFlatFile);
		
		txtPathInput = new JTextField();
		txtPathInput.setFont(new Font("Tahoma", Font.PLAIN, 8));
		txtPathInput.setEnabled(false);
		txtPathInput.setBounds(10, 37, 302, 20);
		getContentPane().add(txtPathInput);
		txtPathInput.setColumns(10);
		
		JLabel lblOutputFile = new JLabel("TEI output File:");
		lblOutputFile.setBounds(10, 68, 105, 20);
		getContentPane().add(lblOutputFile);
		
		txtPathOutput = new JTextField();
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
		progressBar.setVisible(false);
		progressBar.setBounds(10, 134, 463, 20);
		getContentPane().add(progressBar);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == okButton) {
			//init Progessbar...
			progressBar.setVisible(true);
			progressBar.setMaximum(5); //Die Anzahl der Schritte. TODO: Muss noch angepasst werden...
			progressBar.setValue(1);
			
			lblMsg.setText("Konvertierung wurde gestartet...");
			
			RefactorXML converter = new RefactorXML(inputFile, outputFile);
			converter.startConversion();
			
		}
		
	}
	
	public void makeProgessSteps() {
		progressBar.setValue(progressBar.getValue() + 1);
	}
}
