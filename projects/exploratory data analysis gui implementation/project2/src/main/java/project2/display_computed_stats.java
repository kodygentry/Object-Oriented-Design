package project2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class display_computed_stats {
    JFrame frame;  
    display_computed_stats(){ 
        JFrame frame = new JFrame("Amazon Statistics");  
        JLabel reviewerHeader = new JLabel("Top 3 REVIEWER ANALYSIS");
        JLabel reviewerHeader2 = new JLabel("Reviewers with highest number of reviews");
        JLabel format1 = new JLabel("ID | Degree | Product avg | Reviewer avg (format)");
        JLabel format2 = new JLabel("ID | Degree | Product avg | Reviewer avg (format)");
        JLabel format3 = new JLabel("ID | Degree | Product avg | Reviewer avg (format)"); 
        JLabel format4 = new JLabel("ID | Degree | Product avg | Reviewer avg (format)"); 
        JLabel reviewerNum1 = new JLabel("ID | Degree | Product avg | Reviewer avg"); // add actual data when implementing
        JLabel reviewerNum2 = new JLabel("ID | Degree | Product avg | Reviewer avg"); // add actual data when implementing
        JLabel reviewerNum3 = new JLabel("ID | Degree | Product avg | Reviewer avg"); // add actual data when implementing
        JLabel reviewerHeader3 = new JLabel("Reviewers with highest discrepancies per reviewer");
        JLabel reviewerDis1 = new JLabel("ID | Degree | Product avg | Reviewer avg"); // add actual data when implementing
        JLabel reviewerDis2 = new JLabel("ID | Degree | Product avg | Reviewer avg"); // add actual data when implementing
        JLabel reviewerDis3 = new JLabel("ID | Degree | Product avg | Reviewer avg"); // add actual data when implementing
        JLabel productHeader = new JLabel("Top 3 PRODUCT ANALYSIS");
        JLabel productHeader2 = new JLabel("Products with highest number of reviews"); 
        JLabel productNum1 = new JLabel("ID | Degree | Product avg | Reviewer avg"); // add actual data when implementing
        JLabel productNum2 = new JLabel("ID | Degree | Product avg | Reviewer avg"); // add actual data when implementing
        JLabel productNum3 = new JLabel("ID | Degree | Product avg | Reviewer avg"); // add actual data when implementing
        JLabel productHeader3 = new JLabel("Products with highest rating discrepancies");
        JLabel productDis1 = new JLabel("ID | Degree | Product avg | Reviewer avg"); // add actual data when implementing
        JLabel productDis2 = new JLabel("ID | Degree | Product avg | Reviewer avg"); // add actual data when implementing
        JLabel productDis3 = new JLabel("ID | Degree | Product avg | Reviewer avg"); // add actual data when implementing

        reviewerHeader.setBounds(220, 10, 260, 30);
        reviewerHeader2.setBounds(15, 35, 320, 30); 
        format1.setBounds(15, 60, 300, 30); 
        reviewerNum1.setBounds(15, 80, 260, 30); 
        reviewerNum2.setBounds(15, 100, 260, 30); 
        reviewerNum3.setBounds(15, 120, 260, 30); 

        reviewerHeader3.setBounds(330, 35, 320, 30); 
        format2.setBounds(330, 60, 300, 30);
        reviewerDis1.setBounds(330, 80, 260, 30); 
        reviewerDis2.setBounds(330, 100, 260, 30); 
        reviewerDis3.setBounds(330, 120, 260, 30);

        productHeader.setBounds(220, 150, 260, 30);
        productHeader2.setBounds(15, 175, 300, 30); 
        format3.setBounds(15, 200, 300, 30);
        productNum1.setBounds(15, 220, 260, 30); 
        productNum2.setBounds(15, 240, 260, 30); 
        productNum3.setBounds(15, 260, 260, 30); 

        productHeader3.setBounds(330, 175, 260, 30); 
        format4.setBounds(330, 200, 300, 30);
        productDis1.setBounds(330, 220, 260, 30); 
        productDis2.setBounds(330, 240, 260, 30); 
        productDis3.setBounds(330, 260, 260, 30); 

        JButton exit, startAgain;   
        exit = new JButton("Exit");
        startAgain = new JButton("Start Again");
        exit.setBounds(190, 300, 70, 40);
        startAgain.setBounds(270, 300, 150, 40);
        
        frame.add(exit);frame.add(startAgain);
        frame.add(format1);frame.add(format2);frame.add(format3);frame.add(format4);
        frame.add(reviewerHeader);frame.add(reviewerHeader2);frame.add(reviewerHeader3);
        frame.add(reviewerNum1);frame.add(reviewerNum2);frame.add(reviewerNum3);
        frame.add(reviewerDis1);frame.add(reviewerDis2);frame.add(reviewerDis3);
        frame.add(productHeader);frame.add(productHeader2);frame.add(productHeader3);
        frame.add(productNum1);frame.add(productNum2);frame.add(productNum3);
        frame.add(productDis1);frame.add(productDis2);frame.add(productDis3);
        
        frame.setSize(650,400);  
        frame.setLayout(null);  
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);  
    }      
    public static void main(String[] args) {  
        new display_computed_stats();  
    }  
}