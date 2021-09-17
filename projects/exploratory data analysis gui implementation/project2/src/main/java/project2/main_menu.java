package project2;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.InvalidPathException;

import javax.swing.*;
   
// code for opening page of the interface
public class main_menu implements ActionListener{  
    JFrame f;  
    public main_menu() throws InvalidPathException, IOException{  
        f=new JFrame();  
        new JFrame("Amazon Interface Menu");  

        DatasetHandler dh = new DatasetHandler();
		int dbSize = dh.getDataSets();

        JLabel header = new JLabel("Choose one of the following.");
        JLabel dataSets = new JLabel(dbSize + " datasets available"); // add num when implementing
        JLabel dataSetsPath = new JLabel("from: " + dh.getFolderPath() + System.lineSeparator());
        
        JButton display, addNew, exit;  
        display = new JButton("Display computed stats for specific dataID."); // moves to "enter a data id" page
        addNew = new JButton("Add new collection and compute stats."); // moves to unique id page
        exit = new JButton("Exit"); // exits the code and java fx screen

        header.setBounds(110,20,260,30); 
        display.setBounds(40,50,300,50);   
        addNew.setBounds(40,100,300,50);   
        exit.setBounds(110,150,150,50); 
        dataSets.setBounds(10,230,260,30);dataSetsPath.setBounds(10,250,300,30);

        f.add(header);f.add(dataSets);f.add(dataSetsPath);
        f.add(display);f.add(addNew);f.add(exit);

        f.setResizable(false);
        f.setSize(400,330);  
        f.setLayout(null);  
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        exit.setActionCommand("exit");
        exit.addActionListener(this);

        display.setActionCommand("display");
        display.addActionListener(this);
        
        addNew.setActionCommand("add");
        addNew.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if("exit".equals(e.getActionCommand())){
            f.setVisible(false);
            System.exit(1);
        }
        if("display".equals(e.getActionCommand())){
            f.setVisible(false);
            new display_computed_stats();
        }
        if("add".equals(e.getActionCommand())){
            f.setVisible(false);
            new enter_newID();
        }
    }  
    public static void main(String[] args) throws InvalidPathException, IOException {  
        new main_menu();  
    }  
}  