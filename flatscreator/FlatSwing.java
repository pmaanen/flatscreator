package flatscreator;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import java.awt.FileDialog;


import com.lowagie.text.DocumentException;
import javax.swing.JCheckBox;

public class FlatSwing {

    private JFrame jFrame = null; // @jve:decl-index=0:visual-constraint="10,10"
    private JPanel jContentPane = null;
    private JMenuBar jJMenuBar = null;
    private JMenu fileMenu = null;
    private JMenu helpMenu = null;
    private JMenuItem exitMenuItem = null;
    private JMenuItem saveMenuItem = null;
    private JMenuItem aboutMenuItem = null;
    private JMenuItem loadMenuItem = null;
    private JDialog aboutDialog = null;
    private JPanel aboutContentPane = null;
    private JLabel aboutVersionLabel = null;
    private JMenuItem newMenuItem = null;
    private JButton addFlatButton = null;
    private JScrollPane scrollPane = null;
    private JPanel flatPanel = null;
    private JLabel jLabel = null;
    private JLabel jLabel1 = null;
    private JLabel jLabel2 = null;
    private List<Flat> flats = new ArrayList<Flat>();
    private List<JLabel> files = new ArrayList<JLabel>(); // @jve:decl-index=0:
    private List<JTextField> names = new ArrayList<JTextField>();
    private List<JSpinner> counts = new ArrayList<JSpinner>();
    private List<JTextField> sizes = new ArrayList<JTextField>();
    private List<JButton> removes = new ArrayList<JButton>();
    private JLabel jLabel3 = null;
    private JLabel jLabel4 = null;
    private JMenuItem exportMenuItem = null;
    private JLabel jLabel5 = null;
    private JTextField bottom = null;
    private JPanel jPanel = null;
    private JLabel jLabel6 = null;
    private JLabel jLabel7 = null;
    private JTextField left = null;
    private JTextField right = null;
    private JLabel jLabel8 = null;
    
	/**
	 * This method initializes newMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getNewMenuItem() {
		if (newMenuItem == null) {
			newMenuItem = new JMenuItem();
			newMenuItem.setText("New...");
			newMenuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int r = JOptionPane.showConfirmDialog(jFrame,
							"All changes will be lost.", "New...",
							JOptionPane.WARNING_MESSAGE);
					if (r == JOptionPane.OK_OPTION) {
						for (JLabel f : files) {
							flatPanel.remove(f);
						}
						for (JTextField t : names) {
							flatPanel.remove(t);
						}
						for (JSpinner s : counts) {
							flatPanel.remove(s);
						}
						for (JTextField t : sizes) {
							flatPanel.remove(t);
						}
						for (JButton b : removes) {
							flatPanel.remove(b);
						}
						flats = new ArrayList<Flat>();
						files = new ArrayList<JLabel>();
						names = new ArrayList<JTextField>();
						counts = new ArrayList<JSpinner>();
						sizes = new ArrayList<JTextField>();
						removes = new ArrayList<JButton>();
						flatPanel.validate();
					}
				}
			});
		}
		return newMenuItem;
	}

	/**
	 * This method initializes addFlatButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddFlatButton() {
		if (addFlatButton == null) {
			addFlatButton = new JButton();
			addFlatButton.setText("Add Flat");
			addFlatButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
				    FileDialog file = new FileDialog(jFrame, "", FileDialog.LOAD);
				    file.setFile("*.png");
				    file.setMultipleMode(true);
				    file.setVisible(true);
				    File[] fnames=file.getFiles();
				    if (fnames.length!=0) {
					for(int ii=0;ii<fnames.length;ii++){
						try {
						    flats.add(new Flat(
								       fnames[ii].getAbsolutePath(),
								       doubleFlap.isSelected(), autoSize.isSelected(), drawShadow.isSelected()));
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(jFrame,
										      "Error while reading "+fnames[ii].getName()+": "+e1.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
							continue;
						} catch (DocumentException e1) {
							JOptionPane.showMessageDialog(jFrame,
									e1.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						JLabel label = new JLabel(
									  fnames[ii].getName());
						files.add(label);
						label.setAlignmentY(Component.TOP_ALIGNMENT);
						flatPanel.add(label);
						JTextField text = new JTextField();
						text.setAlignmentY(Component.TOP_ALIGNMENT);
						names.add(text);
						flatPanel.add(text);
						text = new JTextField();
						text.setAlignmentY(Component.TOP_ALIGNMENT);
						text.setText("19");
						text.setEnabled(!autoSize.isEnabled());
						sizes.add(text);
						flatPanel.add(text);
						JSpinner spinner = new JSpinner();
						spinner.setAlignmentY(Component.TOP_ALIGNMENT);
						spinner.setValue(1);
						counts.add(spinner);
						flatPanel.add(spinner);
						JButton remove = new JButton("Remove");
						remove.setAlignmentY(Component.TOP_ALIGNMENT);
						remove.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent event) {
								int id = removes.indexOf(event.getSource());
								flats.remove(id);
								flatPanel.remove(files.get(id));
								files.remove(id);
								flatPanel.remove(counts.get(id));
								counts.remove(id);
								flatPanel.remove(sizes.get(id));
								sizes.remove(id);
								flatPanel.remove(names.get(id));
								names.remove(id);
								flatPanel.remove(removes.get(id));
								removes.remove(id);
								flatPanel.validate();
							}
						});
						removes.add(remove);
						flatPanel.add(remove);
						flatPanel.validate();
					}
				    }
				}
			});
		}
		return addFlatButton;
	}

	/**
	 * This method initializes scrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setAlignmentY(Component.TOP_ALIGNMENT);
			scrollPane.setViewportView(getFlatPanel());
		}
		return scrollPane;
	}

	/**
	 * This method initializes flatPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getFlatPanel() {
		if (flatPanel == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("Remove");
			jLabel3 = new JLabel();
			jLabel3.setText("Size (in mm)");
			jLabel2 = new JLabel();
			jLabel2.setText("Count");
			jLabel1 = new JLabel();
			jLabel1.setText("Name");
			jLabel = new JLabel();
			jLabel.setText("Image file");
			GridLayout gridLayout = new GridLayout(0, 5, 1, 1);
			flatPanel = new JPanel();
			flatPanel.setLayout(gridLayout);
			flatPanel.setAlignmentY(Component.TOP_ALIGNMENT);
			flatPanel.setAutoscrolls(true);
			flatPanel.add(jLabel, null);
			flatPanel.add(jLabel1, null);
			flatPanel.add(jLabel3, null);
			flatPanel.add(jLabel2, null);
			flatPanel.add(jLabel4, null);
		}
		return flatPanel;
	}

	/**
	 * This method initializes the export menu item
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getExportMenuItem() {
		if (exportMenuItem == null) {
			exportMenuItem = new JMenuItem();
			exportMenuItem.setText("Export...");
			exportMenuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
				    FileDialog file = new FileDialog(jFrame, "", FileDialog.SAVE);
				    file.setFile("*.pdf");
                                    file.setVisible(true);
                                    String fname = file.getDirectory() +
                                        System.getProperty("file.separator") + file.getFile();
				    if (file.getFile()!=null) {
						try {
							float bLeft = Float.parseFloat(left.getText().replace(
									',', '.'));
							float bRight = Float.parseFloat(right.getText().replace(
									',', '.'));
							float bBottom = Float.parseFloat(bottom.getText().replace(
									',', '.'));
							float bTop = Float.parseFloat(top.getText().replace(
									',', '.'));
							Sheet s = new Sheet(bLeft, bRight, bBottom, bTop);
							Iterator<JTextField> namesIter = names.iterator();
							Iterator<JSpinner> countsIter = counts.iterator();
							Iterator<JTextField> sizesIter = sizes.iterator();
							for (Flat f : flats) {
								f.setName(namesIter.next().getText());
								f.setCount((Integer) countsIter.next().getValue());
								f.setWidth(Float.parseFloat(sizesIter.next().getText().replace(
										',', '.')));
								f.setHeight(Float.parseFloat(height.getText().replace(
										',', '.')));
								s.addFlat(f);

							}
							s.output(new File(fname));
						} catch (FileNotFoundException e1) {
							JOptionPane.showMessageDialog(jFrame,
									e1.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
						} catch (DocumentException e1) {
							JOptionPane.showMessageDialog(jFrame,
									e1.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
						} catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(jFrame,
									e1.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
		}
		return exportMenuItem;
	}
	/**
	 * This method initializes the load button
	 * 
	 * @return javax.swing.JMenuItem
	 */
    private JMenuItem getLoadMenuItem() {
	if (loadMenuItem == null) {
	    loadMenuItem = new JMenuItem();
	    loadMenuItem.setText("Load...");
	    loadMenuItem.addActionListener(new ActionListener() {
		    @Override
			public void actionPerformed(ActionEvent e) {
			FileDialog file = new FileDialog(jFrame, "", FileDialog.LOAD);
			file.setFile("*.flat");
			file.setVisible(true);
			if (file.getFile()!=null) {
				readSheet(file.getDirectory()+ System.getProperty("file.separator") + file.getFile());
			}
		    }});
	}
	return loadMenuItem;
    }
    /**                                                                                                                                                                                                                                                                                       
     * This method initializes the save button                                                                                                                                                                                                                                                
     *                                                                                                                                                                                                                                                                                        
     * @return javax.swing.JMenuItem                                                                                                                                                                                                                                                          
     */
    private JMenuItem getSaveMenuItem() {
	    if (saveMenuItem == null) {
		saveMenuItem = new JMenuItem();
		saveMenuItem.setText("Save...");
		saveMenuItem.addActionListener(new ActionListener() {
			
			@Override
				    public void actionPerformed(ActionEvent e) {
				    FileDialog file = new FileDialog(jFrame, "", FileDialog.SAVE);
				    file.setFile("*.flat");
                                    file.setVisible(true);
                                    String fname = file.getDirectory() +
                                        System.getProperty("file.separator") + file.getFile();
				    if (file.getFile()!=null) {
					try {
							float bLeft = Float.parseFloat(left.getText().replace(
									',', '.'));
							float bRight = Float.parseFloat(right.getText().replace(
									',', '.'));
							float bBottom = Float.parseFloat(bottom.getText().replace(
									',', '.'));
							float bTop = Float.parseFloat(top.getText().replace(
									',', '.'));
							Sheet s = new Sheet(bLeft, bRight, bBottom, bTop);
							Iterator<JTextField> namesIter = names.iterator();
							Iterator<JSpinner> countsIter = counts.iterator();
							Iterator<JTextField> sizesIter = sizes.iterator();
							for (Flat f : flats) {
								f.setName(namesIter.next().getText());
								f.setCount((Integer) countsIter.next().getValue());
								f.setWidth(Float.parseFloat(sizesIter.next().getText().replace(
										',', '.')));
								f.setHeight(Float.parseFloat(height.getText().replace(
										',', '.')));
								s.addFlat(f);

							}
							FileOutputStream fileOut =new FileOutputStream(fname);
							ObjectOutputStream out = new ObjectOutputStream(fileOut);
							out.writeObject(s);
							out.close();
							fileOut.close();
							//System.out.printf("Serialized data is saved in /tmp/employee.ser");
							//s.writeObject(fname);

							
						} catch (FileNotFoundException e1) {
							JOptionPane.showMessageDialog(jFrame,
									e1.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(jFrame,
									e1.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
						} catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(jFrame,
									e1.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
		}
		return saveMenuItem;
	}
    
	/**
	 * This method initializes bottom
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getBottom() {
		if (bottom == null) {
			bottom = new JTextField();
			bottom.setText("10");
		}
		return bottom;
	}

	JTextField top;
	JTextField height;

	private JTextField getTop() {
		if (top == null) {
			top = new JTextField();
			top.setText("10");
		}
		return top;
	}

	private JTextField getHeight() {
		if (height == null) {
			height = new JTextField();
			height.setText("32");
		}
		return height;
	}

	private JLabel jLabel9;
	private JLabel jLabel10;
    private JCheckBox doubleFlap = null;
    private JCheckBox drawShadow = null;
    private JCheckBox autoSize = null;
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("mm");
			jLabel7 = new JLabel();
			jLabel7.setText("bottom:");
			jLabel6 = new JLabel();
			jLabel6.setText("right:");
			jLabel9 = new JLabel();
			jLabel9.setText("top:");
			jLabel10 = new JLabel();
			jLabel10.setText("height:");
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel.add(jLabel5, null);
			jPanel.add(getLeft(), null);
			jPanel.add(jLabel6, null);
			jPanel.add(getRight(), null);
			jPanel.add(jLabel7, null);
			jPanel.add(getBottom(), null);
			jPanel.add(jLabel9, null);
			jPanel.add(getTop(), null);
			jPanel.add(jLabel10, null);
			jPanel.add(getHeight(), null);
			jPanel.add(jLabel8, null);
			jPanel.add(getDrawShadow(),null);
			jPanel.add(getAutoSize(),null);
			jPanel.add(getDoubleFlap(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes left
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getLeft() {
		if (left == null) {
			left = new JTextField();
			left.setText("10");
		}
		return left;
	}

	/**
	 * This method initializes right
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getRight() {
		if (right == null) {
			right = new JTextField();
			right.setText("10");
		}
		return right;
	}

	/**
	 * This method initializes doubleFlap
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getDoubleFlap() {
		if (doubleFlap == null) {
			doubleFlap = new JCheckBox();
			doubleFlap.setText("double flap");
			doubleFlap.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (Flat f : flats) {
						f.setDoubleFlap(doubleFlap.isSelected());
					}
				}
			});
		}
		return doubleFlap;
	}
    	/**
	 * This method initializes drawShadow
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getDrawShadow() {
		if (drawShadow == null) {
			drawShadow = new JCheckBox();
			drawShadow.setText("draw shadow");
			drawShadow.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (Flat f : flats) {
						f.setDrawShadow(drawShadow.isSelected());
					}
				}
			    });
		}
		drawShadow.setSelected (true);
		return drawShadow;
	}

        	/**
	 * This method initializes autoSize
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getAutoSize() {
		if (autoSize == null) {
			autoSize = new JCheckBox();
			autoSize.setText("auto size");
			autoSize.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (Flat f : flats) {
						f.setAutoSize(autoSize.isSelected());
					}
					height.setEnabled(!autoSize.isSelected());
					    for(JTextField size : sizes){
						size.setEnabled(!autoSize.isSelected());
					    }
				}
			    });
			    
		
		}
		return autoSize;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				FlatSwing application = new FlatSwing();
				application.getJFrame().setVisible(true);
			}
		});
	}

	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setJMenuBar(getJJMenuBar());
			jFrame.setSize(600, 300);
			jFrame.setContentPane(getJContentPane());
			jFrame.setTitle("FlatsCreator");
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Border left:");
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getScrollPane(), BorderLayout.CENTER);
			jContentPane.add(getAddFlatButton(), BorderLayout.SOUTH);
			jContentPane.add(getJPanel(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jJMenuBar
	 * 
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getFileMenu());
			jJMenuBar.add(getHelpMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.add(getNewMenuItem());
			fileMenu.add(getExportMenuItem());
			//Not implemented
			fileMenu.add(getLoadMenuItem());
			fileMenu.add(getSaveMenuItem());
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText("Help");
			helpMenu.add(getAboutMenuItem());
		}
		return helpMenu;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("Exit");
			exitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return exitMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText("About");
			aboutMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JDialog aboutDialog = getAboutDialog();
					aboutDialog.pack();
					Point loc = getJFrame().getLocation();
					loc.translate(20, 20);
					aboutDialog.setLocation(loc);
					aboutDialog.setVisible(true);
				}
			});
		}
		return aboutMenuItem;
	}

	/**
	 * This method initializes aboutDialog
	 * 
	 * @return javax.swing.JDialog
	 */
	private JDialog getAboutDialog() {
		if (aboutDialog == null) {
			aboutDialog = new JDialog(getJFrame(), true);
			aboutDialog.setResizable(false);
			aboutDialog.setTitle("About");
			aboutDialog.setContentPane(getAboutContentPane());
		}
		return aboutDialog;
	}

	/**
	 * This method initializes aboutContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAboutContentPane() {
		if (aboutContentPane == null) {
			aboutContentPane = new JPanel();
			aboutContentPane.setLayout(new BorderLayout());
			aboutContentPane.add(getAboutVersionLabel(), BorderLayout.CENTER);
		}
		return aboutContentPane;
	}

	/**
	 * This method initializes aboutVersionLabel
	 * 
	 * @return javax.swing.JLabel
	 */
    private JLabel getAboutVersionLabel() {
		if (aboutVersionLabel == null) {
			aboutVersionLabel = new JLabel();
			aboutVersionLabel.setText("");
		}
		return aboutVersionLabel;
	}

    public void readSheet(String filename){
	Sheet e = null;
      try
	  {
	      FileInputStream fileIn = new FileInputStream("/Users/pmaanen/Desktop/brawler.flat");
	      ObjectInputStream in = new ObjectInputStream(fileIn);

	      try{
		  e =(Sheet)in.readObject();
		  e.initializeFlats();
	      }
	      catch (NullPointerException n){
		  System.out.println("Could not read "+filename);
		  in.close();
		  fileIn.close();
		  return;
	      }
	      in.close();
	      fileIn.close();

	      FileDialog file = new FileDialog(jFrame, "", FileDialog.SAVE);
	      file.setFile("*.pdf");
	      file.setVisible(true);
	      String fname = file.getDirectory() +
		  System.getProperty("file.separator") + file.getFile();
	      if (file.getFile()!=null) {
		  try {
		      e.output(new File(fname));
		  } catch (FileNotFoundException e1) {
		      JOptionPane.showMessageDialog(jFrame,
						    e1.getMessage(), "Error",
						    JOptionPane.ERROR_MESSAGE);
		  } catch (DocumentException e1) {
		      JOptionPane.showMessageDialog(jFrame,
						    e1.getMessage(), "Error",
						    JOptionPane.ERROR_MESSAGE);
		  } catch (NumberFormatException e1) {
		      JOptionPane.showMessageDialog(jFrame,
						    e1.getMessage(), "Error",
						    JOptionPane.ERROR_MESSAGE);
		  }
	      }
	  } catch(IOException i) {
	  i.printStackTrace();
	  return;
      } catch(DocumentException d){
	  d.printStackTrace();
	  return;
      } 
      catch(ClassNotFoundException c){
	  System.out.println("Sheet class not found");
	  c.printStackTrace();
	  return;
      }
    }
}
