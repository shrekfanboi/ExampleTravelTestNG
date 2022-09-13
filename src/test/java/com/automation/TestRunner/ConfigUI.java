package com.automation.TestRunner;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JCheckBox;

public class ConfigUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField testClassField;
	private JTextField keyField;
	private JTextField valueField;
	private JTextField testSuiteField;
	public static Map<String,Object> testConfiguration;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigUI frame = new ConfigUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static Map<String,Object> getTestConfig(){
		return testConfiguration;
	}
	public static void resetTestConfig() {
		testConfiguration=null;
	}
	public String generateRandomString() {
		int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	    return generatedString;
	}
	
	/**
	 * Create the frame.
	 */
	public ConfigUI() {
		setTitle("Configure");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(null);
		tabbedPane.setBounds(5, 5, 516, 403);
		contentPane.add(tabbedPane);

		JPanel general = new JPanel();
		general.setBorder(null);
		tabbedPane.addTab("General", null, general, null);
		general.setLayout(null);
	
		
		JLabel lblNewLabel = new JLabel("Test Name");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblNewLabel.setBounds(29, 11, 118, 20);
		general.add(lblNewLabel);
		
		String[] testNames = {"register","search","login","book"};
		JComboBox<?> testNameField = new JComboBox<Object>(testNames);
		testNameField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		testNameField.setSize(new Dimension(10, 0));
		testNameField.setEditable(false);
		testNameField.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		testNameField.setBounds(29, 42, 407, 30);
		general.add(testNameField);
		
		testNameField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String testSelected = testNames[testNameField.getSelectedIndex()];
			    switch(testSelected) {
			    case "register":
			    	testClassField.setText("com.automation.Test.RegisterTest");
			    	break;
				case "login":
					testClassField.setText("com.automation.Test.LoginTest");
					break;
				case "search":
					testClassField.setText("com.automation.Test.SearchFlightTest");
					break;
				case "book":
					testClassField.setText("com.automation.Test.BookingTest");
					break;
				default:
					return;
			}
			}
		});
		
		JLabel lblNewLabel_2 = new JLabel("Run Count");
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblNewLabel_2.setBounds(29, 236, 64, 14);
		general.add(lblNewLabel_2);
		
		JSpinner testCountField = new JSpinner(new SpinnerNumberModel(1,1,10,1));
		testCountField.setBounds(29, 261, 64, 20);
		general.add(testCountField);
		
		JRadioButton testAlwaysRunField = new JRadioButton("disable");
		testAlwaysRunField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		testAlwaysRunField.setBounds(29, 302, 111, 23);
		general.add(testAlwaysRunField);
		
		testClassField = new JTextField();
		testClassField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		testClassField.setText("com.automation.Test.RegisterTest");
		testClassField.setEditable(false);
		testClassField.setColumns(10);
		testClassField.setBackground(Color.LIGHT_GRAY);
		testClassField.setAlignmentX(0.0f);
		testClassField.setBounds(27, 115, 409, 30);
		general.add(testClassField);
		
		JLabel lblNewLabel_3 = new JLabel("Test Class");
		lblNewLabel_3.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblNewLabel_3.setBounds(29, 83, 78, 33);
		general.add(lblNewLabel_3);
		
		JButton addTestConfigBtn = new JButton("Add");
		addTestConfigBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		addTestConfigBtn.setBounds(278, 341, 89, 23);
		general.add(addTestConfigBtn);
		
		JButton cancelTestConfigBtn = new JButton("Cancel");
		cancelTestConfigBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		cancelTestConfigBtn.setBounds(377, 341, 89, 23);
		general.add(cancelTestConfigBtn);
		
		JLabel lblNewLabel_6 = new JLabel("Test Suite");
		lblNewLabel_6.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblNewLabel_6.setBounds(29, 156, 67, 30);
		general.add(lblNewLabel_6);
		
		testSuiteField = new JTextField();
		testSuiteField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		testSuiteField.setBounds(29, 190, 407, 30);
		general.add(testSuiteField);
		testSuiteField.setColumns(10);
		testSuiteField.setText("suite");
		testSuiteField.setEditable(false);
		
		
		JPanel param = new JPanel();
		tabbedPane.addTab("Parameter",param);
		param.setLayout(null);
		
		DefaultListModel<String> keyItemsModel = new DefaultListModel<>();
		JList<String>  keyArea = new JList<String>(keyItemsModel);
		keyArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		keyArea.setBounds(84, 92, 107, 183);
		param.add(keyArea);
		
		keyField = new JTextField();
		keyField.setBounds(84, 34, 107, 32);
		param.add(keyField);
		keyField.setColumns(10);
		
		valueField = new JTextField();
		valueField.setColumns(10);
		valueField.setBounds(287, 34, 107, 32);
		param.add(valueField);
		
		JLabel keyLabel = new JLabel("key");
		keyLabel.setBounds(84, 11, 49, 14);
		param.add(keyLabel);
		
		JLabel valueLabel = new JLabel("value");
		valueLabel.setBounds(287, 11, 49, 14);
		param.add(valueLabel);
		
		DefaultListModel<String> valueItemsModel = new DefaultListModel<>();
		JList<String> valueArea = new JList<String>(valueItemsModel);
		valueArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		valueArea.setBounds(287, 92, 107, 189);
		param.add(valueArea);
		
		JButton paramsAddBtn = new JButton("Add");
		paramsAddBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		paramsAddBtn.setFocusPainted(false);
		paramsAddBtn.setBounds(151, 309, 89, 23);
		param.add(paramsAddBtn);
		
		JButton paramsRemoveBtn = new JButton("Remove");
		paramsRemoveBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		paramsRemoveBtn.setBounds(247, 309, 89, 23);
		paramsRemoveBtn.setFocusPainted(false);
		param.add(paramsRemoveBtn);
		
		JPanel driver = new JPanel();
		tabbedPane.addTab("Driver", driver);
		driver.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Browser");
		lblNewLabel_4.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblNewLabel_4.setBounds(30, 29, 78, 27);
		driver.add(lblNewLabel_4);
		
		String[] browsers = {"Chrome","Firefox","Edge"};
		JComboBox<String> browserField = new JComboBox<String>(browsers);
		browserField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		browserField.setBounds(27, 62, 421, 22);
		browserField.setEditable(false);
		driver.add(browserField);
		
		JLabel lblNewLabel_5 = new JLabel("Mode");
		lblNewLabel_5.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblNewLabel_5.setBounds(30, 105, 55, 22);
		driver.add(lblNewLabel_5);
		
		String[] browserModes = {"default","incognito","headless"};
		JComboBox<String> modeField = new JComboBox<String>(browserModes);
		modeField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		modeField.setBounds(27, 138, 421, 22);
		driver.add(modeField);
		
		JCheckBox maximizableField = new JCheckBox("maximized");
		maximizableField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		maximizableField.setBounds(30, 183, 99, 23);
		maximizableField.setSelected(true);
		driver.add(maximizableField);
		
		paramsAddBtn.addActionListener(new ActionListener() {

	@Override
			public void actionPerformed(ActionEvent e) {
				String key = keyField.getText();
				String value=valueField.getText();
				if(!key.isEmpty() && !value.isEmpty()) {
					keyItemsModel.addElement(key);
					valueItemsModel.addElement(value);
					keyField.setText("");
					valueField.setText("");
				}
				
			}
		});
		
		keyArea.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				valueArea.clearSelection();
			}
		});
		valueArea.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				keyArea.clearSelection();
			}
		});
		paramsRemoveBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!keyArea.isSelectionEmpty()) {
					int itemIndex = keyArea.getSelectedIndex();
					keyItemsModel.remove(itemIndex);
					valueItemsModel.remove(itemIndex);
				}
				else if(!valueArea.isSelectionEmpty()) {
					int itemIndex = valueArea.getSelectedIndex();
					keyItemsModel.remove(itemIndex);
					valueItemsModel.remove(itemIndex);
				}
				else {
					return;
				}
			}
		});
		addTestConfigBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				testConfiguration = new LinkedHashMap<String,Object>();
				testConfiguration.put("testname", testNames[testNameField.getSelectedIndex()]+"_"+generateRandomString());
				testConfiguration.put("testclass",testClassField.getText());
				testConfiguration.put("testsuite",testSuiteField.getText());
				testConfiguration.put("testcount",Integer.parseInt(testCountField.getValue().toString()));
				testConfiguration.put("testdisabled",testAlwaysRunField.isSelected());
				testConfiguration.put("testparams", new HashMap<String,String>());
				for(int i=0;i<keyItemsModel.getSize();i++) {
					@SuppressWarnings("unchecked")
					Map<String,String> paramMap = (Map<String, String>) testConfiguration.get("testparams");
					paramMap.put(keyItemsModel.get(i), valueItemsModel.get(i));
				}
				browserModes[0] = "";
				Map<String,String> driverConfiguration = new HashMap<>();
				driverConfiguration.put("name", browsers[browserField.getSelectedIndex()]);
				driverConfiguration.put("mode", browserModes[modeField.getSelectedIndex()]);
				if(maximizableField.isSelected()) driverConfiguration.put("maximized", "start-maximized");
				testConfiguration.put("driver", driverConfiguration);
				dispose();
			}
		});
		
	}
}
