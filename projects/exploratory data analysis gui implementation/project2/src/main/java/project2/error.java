package project2;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class error {
   
    static void noData(){
        
    JFrame frame= new JFrame("Amazon Interface");  
        JOptionPane.showMessageDialog(frame, "There is no data to select",
            "Amazon Interface Warning", JOptionPane.WARNING_MESSAGE);
          
    }
    static void idNotFound(){

        JFrame frame= new JFrame("Amazon Interface");  
        JOptionPane.showMessageDialog(frame, "Data ID not found.",
            "Amazon Interface Warning", JOptionPane.WARNING_MESSAGE);   
        }
        static void fileNotFound(){

            JFrame frame= new JFrame("Amazon Interface");  
            JOptionPane.showMessageDialog(frame, "File was not found.",
                "Amazon Interface Warning", JOptionPane.WARNING_MESSAGE);   
            }



        public static void main(String[] args) {  
    fileNotFound();
    }  
}  