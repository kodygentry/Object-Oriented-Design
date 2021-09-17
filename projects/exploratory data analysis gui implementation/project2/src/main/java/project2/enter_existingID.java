package project2;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class enter_existingID{  
    JFrame frame;  
    enter_existingID() throws InvalidPathException, IOException{ 

        DatasetHandler dh = new DatasetHandler();
		int dbSize = dh.getDataSets();

        JFrame frame= new JFrame("Enter ID");  
        JLabel header = new JLabel("Data sets available " + dbSize + ":"); // add num of data sets available
        JLabel data1 = new JLabel(dh.printDB());
        data1.setText("<html>" + dh.printDB().replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
    
        JLabel enterID = new JLabel("Please enter Data ID from the list.");

        // clears textbox when clicked
        JTextField dataID = new JTextField("");
        
        header.setBounds(240,10,260,30);
        data1.setBounds(240,10,500,200);
         
        dataID.setBounds(15,40,200,30);  
        enterID.setBounds(15,10,260,30);
        
        frame.add(enterID);frame.add(dataID);
        frame.add(header);frame.add(data1);

        frame.setSize(570,300); 
        frame.setResizable(false); 
        frame.setLayout(null);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true); 

        dataID.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event) {
                if (!(dh.checkID(dataID.getText()))) {
                System.out.print("dataID " + dataID.getText() + " not in the current database, select another option \n");
                }
                else{
                    frame.setVisible(false);
                    new display_computed_stats();
                }
                }
        });
    }      
    public static void main(String[] args) throws InvalidPathException, IOException {  
        new enter_existingID();  
    }  
}  