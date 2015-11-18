package ListAllDB;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ListAllDB extends JPanel {

 

  JList list;

  public ListAllDB() {
    setLayout(new BorderLayout());

    list = new JList(StartPage.dbList.toArray());
    JButton button = new JButton("execute");
    JScrollPane pane = new JScrollPane(list);
    System.out.println("isi dari jlist");
    for(int i = 0; i < list.getModel().getSize(); i++) {
    	//System.out.println(list.getModel().getElementAt(i));
    }
    
    System.out.println("isi dari dblist");
    for(int i = 0; i < StartPage.dbList.size();i++) {
    	//System.out.println(StartPage.dbList.get(i));
    }
    DefaultListSelectionModel m = new DefaultListSelectionModel();
    m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    m.setLeadAnchorNotificationEnabled(false);
    list.setSelectionModel(m);

    list.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        //System.out.println(e.toString());
    	 /* int x = list.getSelectedIndex();
    	  System.out.println(x);*/
      }
    });
    button.addActionListener(new PrintListener());

    add(pane, BorderLayout.NORTH);
    add(button, BorderLayout.SOUTH);
  }

  public static void main(String s[]) {
    JFrame frame = new JFrame("DB list");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setContentPane(new ListAllDB());
    frame.pack();
    frame.setVisible(true);
  }
  
  
  // An inner class to respond to clicks on the Print button
  class PrintListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      int selected[] = list.getSelectedIndices();
      System.out.println("Selected Elements:  ");

      for (int i = 0; i < selected.length; i++) {
    	try {
    	BufferedReader br = null;
        String element = (String) list.getModel().getElementAt(selected[i]);
        System.out.println(selected[i]);
        System.out.println("  " + element);
        String dirPath = StartPage.path.replace("\\","\\\\");
		String dbPath = dirPath+"\\"+element;
		String filePath = dbPath+"\\"+"jaisGraphDesc.text";
		System.out.println(filePath);	
		File file = new File(filePath);
		if (!file.exists()) {
			System.out.println("capek");
			file.createNewFile();
		}else {
			StartPage.graphList = new ArrayList();
			br = new BufferedReader(new FileReader(filePath));
			String graphPath;
			StartPage.graphList.clear();
			while((graphPath = br.readLine())!= null) {
				System.out.println(graphPath);
				StartPage.graphList.add(graphPath);
			}
			
			ModifyDB dialog = new ModifyDB(element);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	
      }
    }
  }
  
  public static void initListAllDB() {
	  	JFrame frame = new JFrame("List All Databases");
	  	frame.setSize(260, 200);
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setContentPane(new ListAllDB());
	    frame.pack();
	    frame.setVisible(true);
  }
}