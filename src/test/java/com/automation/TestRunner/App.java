package com.automation.TestRunner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.IconUIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.automation.Generic.PropertyReader;

class MyTreeCellRenderer extends DefaultTreeCellRenderer{

	DefaultMutableTreeNode root;
	public MyTreeCellRenderer(DefaultMutableTreeNode root) {
		this.root = root;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
    public Color getBackgroundNonSelectionColor() {
        return (null);
    }

    @Override
    public Color getBackgroundSelectionColor() {
        return new  Color(186,207,250);
    }

    @Override
    public Color getBackground() {
        return (null);
    }

    @Override
    public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {
        final Component ret = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
    	DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Image leafIcon = new ImageIcon("./data/suite-icon.png").getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        if(!node.equals(this.root)) setIcon(new ImageIcon(leafIcon));
        return ret;
    }
}

class NodeIcon implements Icon {

    private static final int SIZE = 9;

    private char type;

    public NodeIcon(char type) {
        this.type = type;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(UIManager.getColor("Tree.background"));
        g.fillRect(x, y, SIZE - 1, SIZE - 1);

        g.setColor(UIManager.getColor("Tree.hash").darker());
        g.drawRect(x, y, SIZE - 1, SIZE - 1);

        g.setColor(UIManager.getColor("Tree.foreground"));
        g.drawLine(x + 2, y + SIZE / 2, x + SIZE - 3, y + SIZE / 2);
        if (type == '+') {
            g.drawLine(x + SIZE / 2, y + 2, x + SIZE / 2, y + SIZE - 3);
        }
    }

    public int getIconWidth() {
        return SIZE;
    }

    public int getIconHeight() {
        return SIZE;
    }
}
public class App {

	private JFrame frmTestrunner;
	private DefaultMutableTreeNode suiteNode;
	private DefaultTreeModel treeModel;
	private List<Map<String, Object>> tests;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frmTestrunner.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		try {
			initialize();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	private JFrame renderMainFrame() {
		frmTestrunner = new JFrame();
		frmTestrunner.setTitle("Test Runner");
		frmTestrunner.setBounds(100, 100, 667, 447);
		frmTestrunner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTestrunner.setResizable(false);
		frmTestrunner.setIconImage(new ImageIcon("./data/logo.png").getImage());		
		frmTestrunner.getContentPane().setLayout(new BorderLayout(0, 0));
		return frmTestrunner;
	}
	private JPanel renderMainPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setForeground(new Color(255, 255, 255));
		panel.setBounds(new Rectangle(70, 0, 0, 100));
		frmTestrunner.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		return panel;
	}
	private void setMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.menu);
		frmTestrunner.setJMenuBar(menuBar);

		JMenu FileMenu = new JMenu("File");
		FileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menuBar.add(FileMenu);

		JMenuItem FileMenu_new = new JMenuItem("New");
		FileMenu_new.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		FileMenu_new.setHorizontalAlignment(SwingConstants.TRAILING);
		FileMenu_new.setHorizontalTextPosition(SwingConstants.LEFT);
		FileMenu.add(FileMenu_new);

		JMenuItem FileMenu_run = new JMenuItem("Run");
		FileMenu_run.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		FileMenu.add(FileMenu_run);

		JMenu HelpMenu = new JMenu("Help");
		HelpMenu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menuBar.add(HelpMenu);

		JMenuItem HelpMenu_exit = new JMenuItem("Exit");
		HelpMenu_exit.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		HelpMenu_exit.setHorizontalTextPosition(SwingConstants.LEFT);
		HelpMenu.add(HelpMenu_exit);
	}
	
	private JPanel renderTestExplorer() {
		JPanel testExplorer = new JPanel();
		testExplorer.setBounds(0, 0, 120, 388);
		testExplorer.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		return testExplorer;
	}
	
	
	private JTree renderTestTree(DefaultTreeModel treeModel,DefaultMutableTreeNode suiteNode) {
		JTree testTree = new JTree(treeModel);
		testTree.setCellRenderer(new MyTreeCellRenderer(suiteNode));
		testTree.setAlignmentX(Component.LEFT_ALIGNMENT);
		testTree.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		testTree.setForeground(new Color(0, 0, 0));
		testTree.setEditable(false);
		testTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		testTree.setShowsRootHandles(true);
		testTree.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		testTree.setBackground(new Color(240, 240, 240));
		return testTree;
	}
	private JToolBar renderToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(130, 11, 513, 45);
		return toolBar;
	}
	private JPanel renderToolBarContent(JToolBar toolBar) {
		JPanel panel_1 = new JPanel();
		toolBar.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel = new JLabel("Add a test to run it");
		lblNewLabel.setBounds(20, 11, 138, 17);
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		panel_1.add(lblNewLabel);
		return panel_1;
	}
	
	private JButton renderCreateNewTestButton(JPanel toolBarPanel) {
		JButton createNewBtn = new JButton("Add Test");
		createNewBtn.setToolTipText("Add a new test");
		createNewBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		createNewBtn.setBorder(UIManager.getBorder("Button.border"));
		createNewBtn.setBounds(253, 5, 113, 32);
		createNewBtn.setForeground(new Color(0, 0, 0));
		createNewBtn.setBackground(new Color(192, 192, 192));
		createNewBtn.setFocusPainted(false);
		toolBarPanel.add(createNewBtn);
		return createNewBtn;
	}
	
	private JButton renderRemoveTestButton(JPanel toolBarPanel) {
		JButton btnRemove = new JButton("Remove Test");
		btnRemove.setToolTipText("Remove selected test");
		btnRemove.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnRemove.setEnabled(false);
		btnRemove.setForeground(new Color(0, 0, 0));
		btnRemove.setFocusPainted(false);
		btnRemove.setBorder(UIManager.getBorder("Button.border"));
		btnRemove.setBackground(SystemColor.controlHighlight);
		btnRemove.setBounds(376, 5, 113, 32);
		toolBarPanel.add(btnRemove);
		return btnRemove;
	}
	
	private JButton renderRunTestButton() {
		JButton runTestBtn = new JButton("Run");
		runTestBtn.setBounds(455, 354, 89, 23);
		runTestBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		runTestBtn.setFocusPainted(false);
		runTestBtn.setEnabled(false);
		return runTestBtn;
	}
	
	private JButton renderCancelTestButton() {
		JButton cancelTestBtn = new JButton("Cancel");
		cancelTestBtn.setBounds(554, 354, 89, 23);
		cancelTestBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		cancelTestBtn.setFocusPainted(false);
		return cancelTestBtn;
	}
	
	private void renderConsoleSeparator(JPanel panel) {
		JSeparator separator = new JSeparator();
		separator.setBounds(130, 341, 513, 2);
		separator.setForeground(new Color(192, 192, 192));
		panel.add(separator);
	}
	
	private JPanel renderTestMonitorPanel(JPanel panel) {
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(130, 75, 513, 106);
		panel_3.setBorder(UIManager.getBorder("FormattedTextField.border"));
		panel_3.setBackground(new Color(255, 255, 255));
		panel.add(panel_3);
		panel_3.setLayout(null);
		return panel_3;
	}
	
	private JTextArea renderTestMonitor(JPanel testMonitorPanel) {
		JTextArea testDetails = new JTextArea();
		testDetails.setBounds(10, 11, 493, 82);
		testDetails.setFont(new Font("Monospaced", Font.PLAIN, 12));
		testDetails.setEditable(false);
		testDetails.setLineWrap(true);
		testMonitorPanel.add(testDetails);
		return testDetails;

	}
	private JPanel renderTestConsolePanel(JPanel panel) {
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(130, 192, 513, 137);
		panel.add(panel_2);
		panel_2.setLayout(null);
		return panel_2;
	}
	private JTextArea renderTestConsole() {
		JTextArea testConsole = new JTextArea();
		testConsole.setMargin(new Insets(2, 5, 2, 2));
		testConsole.setForeground(new Color(255, 255, 255));
		testConsole.setBackground(new Color(128, 128, 128));
		testConsole.setFont(new Font("Monospaced", Font.PLAIN, 11));
		testConsole.setEditable(false);
		testConsole.setLineWrap(true);
		return testConsole;
	}
	private void renderTestConsoleLabel(JPanel testConsolePanel) {
		JLabel testConsoleLabel = new JLabel("Console");
		testConsoleLabel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		testConsoleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		testConsoleLabel.setBounds(10, 0, 49, 14);
		testConsolePanel.add(testConsoleLabel);
	}
	
	private PrintStream testConsoleWriter(JTextArea testConsole) {
		return new PrintStream(new OutputStream() {
			
			@Override
			public void write(int b) throws IOException {
				testConsole.append(String.valueOf((char) b));
			}
		});
	}
	private ActionListener createNewTestActionListener() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ConfigUI frame = new ConfigUI();
				frame.setVisible(true);
				frame.setResizable(false);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
			}
		};
	}
	
	private TreeSelectionListener testTreeSelectionListener(JButton runTestBtn,JTextArea testDetails
			,JButton removeTestBtn) {
		return new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				Object node = e.getPath().getLastPathComponent();
				if (node == null)
					return;
				else if (node.toString().equals("suite")) {
					runTestBtn.setEnabled(true);
					testDetails.setText("Suite: suite");
					if(treeModel.getChildCount(suiteNode)>0) {
						testDetails.append("\nTests: ");
						for(int i=0;i<treeModel.getChildCount(suiteNode);i++) {
							testDetails.append(treeModel.getChild(suiteNode, i).toString().toUpperCase()+",");
						}
					}
					removeTestBtn.setEnabled(false);
				} else {
					runTestBtn.setEnabled(true);
					testDetails.setText("Test " + node.toString().toUpperCase());
					removeTestBtn.setEnabled(true);
				}
				
			}
		};
	}
	
	private TreeExpansionListener testTreeExpansionListener(JTextArea testDetails,JButton removeTestBtn
			,JButton createNewTestBtn) {
		return new TreeExpansionListener() {
			
			@Override
			public void treeExpanded(TreeExpansionEvent event) {
			}

			@Override
			public void treeCollapsed(TreeExpansionEvent event) {
				testDetails.setText("");
				removeTestBtn.setEnabled(false);
				createNewTestBtn.setEnabled(true);
			}
		};
	}
	
	private ActionListener removeTestActionListener(JTree testTree,JTextArea testDetails,JButton runTestBtn) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TreePath currentSelection = testTree.getSelectionPath();
				if (currentSelection != null) {
					DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) currentSelection
							.getLastPathComponent();
					tests.removeIf(s -> s.get("testname").toString().contains(currentNode.toString()) && tests.indexOf(s) == treeModel.getIndexOfChild(suiteNode, currentNode));
					treeModel.removeNodeFromParent(currentNode);
					testDetails.setText("");
					JButton btnRemove = (JButton) e.getSource();
					btnRemove.setEnabled(false);
					runTestBtn.setEnabled(false);
			}
		}
	};
	}
	
	private WindowFocusListener setWindowFocusListener(JTree testTree) {
		return new WindowFocusListener() {
			@Override
			public void windowLostFocus(WindowEvent e) {
			}

			@Override
			public void windowGainedFocus(WindowEvent e) {
				if (ConfigUI.getTestConfig() != null) {
					tests.add(ConfigUI.getTestConfig());
					String testName =  ConfigUI.getTestConfig().get("testname").toString().split("_")[0];
					DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(testName);
					treeModel.insertNodeInto(childNode, suiteNode,suiteNode.getChildCount());
					testTree.scrollPathToVisible(new TreePath(childNode.getPath()));
					ConfigUI.resetTestConfig();
				}
			}
		};
	}
	private void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		this.suiteNode = new DefaultMutableTreeNode("suite");
		this.treeModel = new DefaultTreeModel(suiteNode);
		tests = new ArrayList<>();
		UIManager.put("Tree.collapsedIcon", new IconUIResource(new NodeIcon('+')));
		UIManager.put("Tree.expandedIcon",  new IconUIResource(new NodeIcon('-')));
		
		frmTestrunner = this.renderMainFrame();
		JPanel panel = this.renderMainPanel();

		this.setMenuBar();
		
		
		JPanel testExplorer = this.renderTestExplorer();
		JTree testTree = this.renderTestTree(treeModel, suiteNode);
		testExplorer.add(testTree);
		panel.add(testExplorer);
		
		
		JToolBar toolBar = this.renderToolBar();
		JPanel toolBarPanel = this.renderToolBarContent(toolBar);
		JButton createNewTestBtn = this.renderCreateNewTestButton(toolBarPanel);
		JButton removeTestBtn = this.renderRemoveTestButton(toolBarPanel);

		panel.add(toolBar);
		
		this.renderConsoleSeparator(panel);

		JButton runTestBtn = this.renderRunTestButton();
		panel.add(runTestBtn);

		JButton cancelTestBtn = this.renderCancelTestButton();
		panel.add(cancelTestBtn);

		JPanel testMonitorPanel = this.renderTestMonitorPanel(panel);
		JTextArea testDetails = this.renderTestMonitor(testMonitorPanel);
		
		
		JPanel testConsolePanel = this.renderTestConsolePanel(panel);
		JTextArea testConsole = this.renderTestConsole();
		
		JScrollPane scrollPane = new JScrollPane(testConsole);
		scrollPane.setBounds(10, 20, 493, 106);
		testConsolePanel.add(scrollPane);
		
		this.renderTestConsoleLabel(testConsolePanel);
		
		
		System.setOut(this.testConsoleWriter(testConsole));

		createNewTestBtn.addActionListener(this.createNewTestActionListener());
		removeTestBtn.addActionListener(this.removeTestActionListener(testTree, testDetails, runTestBtn));

		testTree.addTreeSelectionListener(this.testTreeSelectionListener(runTestBtn, testDetails, removeTestBtn));
		testTree.addTreeExpansionListener(this.testTreeExpansionListener(testDetails, removeTestBtn, createNewTestBtn));

		frmTestrunner.addWindowFocusListener(this.setWindowFocusListener(testTree));
		
		runTestBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TreePath currentSelect = testTree.getSelectionPath();
				if (currentSelect == null)
					return;
				else {
					String test = currentSelect.getLastPathComponent().toString();
					testConsole.setText("");
					if (test.equals("suite")) {
						Thread t1 = new Thread() {
							public void run() {
								PropertyReader.initProperty();
								XmlSuite suite = RunnerConfig.addSuite("suite");
								for (Map<String, Object> map : tests) {
									if ((boolean) map.get("testdisabled")) continue;
									int testRun = Integer.parseInt(map.get("testcount").toString());
									for (int y = 1; y <= testRun; y++) {
										XmlTest xmlTest = RunnerConfig.createTest(map.get("testname").toString() + "#" + y, suite);
										@SuppressWarnings("unchecked")
										Map<String, String> params = (Map<String, String>) map.get("testparams");
										@SuppressWarnings("unchecked")
										Map<String,String> browserParams = (Map<String, String>) map.get("driver");
										if (!params.isEmpty())
											xmlTest.setParameters(params);
										StringBuffer browserOptions =new StringBuffer( browserParams.get("mode"));
										if(browserParams.get("maximized") != null) browserOptions.append(",start-maximized");								
										xmlTest.addParameter("browser", browserParams.get("name"));
										xmlTest.addParameter("mode", browserOptions.toString());
										RunnerConfig.createClasses(xmlTest, map.get("testclass").toString());
									}
								}
								suite.addListener("com.automation.Generic.Listener");
//								System.out.println(tests);
								RunnerConfig.runNG();
							}
						};
						t1.start();
//						try {
//							t1.join(0);
//						} catch (InterruptedException e1) {
//							e1.printStackTrace();
//						}
						return;
					}
					Map<String, Object> testMap = tests.stream().filter(i -> i.get("testname").toString().contains(test)).findFirst()
							.get();
					String testClass = testMap.get("testclass").toString();
					int testRun = Integer.parseInt(testMap.get("testcount").toString());
					@SuppressWarnings("unchecked")
					Map<String, String> params = (Map<String, String>) testMap.get("testparams");
					@SuppressWarnings("unchecked")
					Map<String,String> browserParams = (Map<String, String>) testMap.get("driver");
					Thread t2 = new Thread() {
						public void run() {
							PropertyReader.initProperty();
							XmlSuite suite = RunnerConfig.addSuite("suite");
							if ((boolean) testMap.get("testdisabled"))
								return;
							for (int x = 1; x <= testRun; x++) {
								XmlTest xmlTest = RunnerConfig.createTest(test + "#" + x, suite);
								if (!params.isEmpty())
									xmlTest.setParameters(params);
								StringBuffer browserOptions =new StringBuffer( browserParams.get("mode"));
								if(browserParams.get("maximized") != null) browserOptions.append(",start-maximized");								
								xmlTest.addParameter("browser", browserParams.get("name"));
								xmlTest.addParameter("mode", browserOptions.toString());
								RunnerConfig.createClasses(xmlTest, testClass);
							}
							suite.addListener("com.automation.Generic.Listener");
							RunnerConfig.runNG();
						}
					};
					t2.start();
//					try {
//						t2.join(0);
//					} catch (InterruptedException err) {
//						err.printStackTrace();
//					}
				}
			}
		});
		
	}
}
