package project2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class enter_newID{  
    JFrame frame;  
    enter_newID(){ 
        JFrame frame= new JFrame("New Collection");

        JLabel enterNew = new JLabel("Please enter new unique dataID:");
        JLabel sourceName = new JLabel("For new _____ collection, what is the source file name?");
        JLabel collectionAdded = new JLabel("Collection _____ added:");
        JLabel whatHappened = new JLabel("What happened?");
        JLabel sourcePath = new JLabel("__________");

        JTextField newID, source;
        newID =new JTextField("Enter new dataID");
        source =new JTextField("Enter source file"); 

        JButton computeStats;
        computeStats = new JButton("Compute Stats");

        enterNew.setBounds(15,10,260,30);
        newID.setBounds(15,40,170,30);
        sourceName.setBounds(15,80,3400,30); 
        source.setBounds(15,110,170,30);
        collectionAdded.setBounds(15,150,260,30); // display if added
        whatHappened.setBounds(15,180,260,30);  
        sourcePath.setBounds(15,195,260,30);
        computeStats.setBounds(220,150,150,30);

        frame.add(sourceName);frame.add(enterNew);
        frame.add(collectionAdded);frame.add(whatHappened);frame.add(sourcePath);
        frame.add(source);frame.add(newID);frame.add(computeStats);

        frame.setSize(400,270);
        frame.setResizable(false);  
        frame.setLayout(null);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);  
    }  
    public static void main(String[] args) {  
        new enter_newID();  
    }  
}  
