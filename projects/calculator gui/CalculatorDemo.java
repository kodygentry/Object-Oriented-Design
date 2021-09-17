package HW7;

import javax.swing.*;
import java.awt.event.*;
import static javax.swing.WindowConstants.*;
import java.lang.Math;

/**
 * @author Kody Gentry
 */
class CalculatorDemo implements ActionListener{	
    JFrame f;
    JTextField t;
    JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bdiv,bmul,bsub,badd,binv,bsqr,bsqrt,bdec,beq,bdel,bclr;

    static double a=0,b=0,result=0;
    static int operator=0;

    /** Displays Calculator GUI and implements functionality
     * @author Kody Gentry
     */
    private static class ButtonListener extends CalculatorDemo{
        // ButtonListener constructor
        ButtonListener(){
            f=new JFrame("Calculator");
            t=new JTextField();
            b1=new JButton("1");
            b2=new JButton("2");
            b3=new JButton("3");
            b4=new JButton("4");
            b5=new JButton("5");
            b6=new JButton("6");
            b7=new JButton("7");
            b8=new JButton("8");
            b9=new JButton("9");
            b0=new JButton("0");
            bdiv=new JButton("/");
            bmul=new JButton("*");
            bsub=new JButton("-");
            badd=new JButton("+");
            binv=new JButton("1/x");
            bsqr=new JButton("x^2");
            bsqrt=new JButton("sqrt(x)");
            bdec=new JButton(".");
            beq=new JButton("=");
            bdel=new JButton("Delete");
            bclr=new JButton("Clear");
            
            t.setBounds(30,40,370,30);
            b7.setBounds(40,100,50,40);
            b8.setBounds(110,100,50,40);
            b9.setBounds(180,100,50,40);
            bdiv.setBounds(250,100,50,40);
            
            b4.setBounds(40,170,50,40);
            b5.setBounds(110,170,50,40);
            b6.setBounds(180,170,50,40);
            bmul.setBounds(250,170,50,40);
            binv.setBounds(320,170,80,40);

            b1.setBounds(40,240,50,40);
            b2.setBounds(110,240,50,40);
            b3.setBounds(180,240,50,40);
            bsub.setBounds(250,240,50,40);
            bsqr.setBounds(320,240,80,40);
            
            bdec.setBounds(40,310,50,40);
            b0.setBounds(110,310,50,40);
            beq.setBounds(180,310,50,40);
            badd.setBounds(250,310,50,40);
            bsqrt.setBounds(320,310,80,40);
            
            bdel.setBounds(100,380,100,40);
            bclr.setBounds(220,380,100,40);
            
            f.add(t);
            f.add(b7);
            f.add(b8);
            f.add(b9);
            f.add(bdiv);
            f.add(b4);
            f.add(b5);
            f.add(b6);
            f.add(bmul);
            f.add(b1);
            f.add(b2);
            f.add(b3);
            f.add(bsub);
            f.add(bdec);
            f.add(b0);
            f.add(beq);
            f.add(badd);
            f.add(binv);
            f.add(bsqr);
            f.add(bsqrt);
            f.add(bdel);
            f.add(bclr);
            
            f.setLayout(null);
            f.setVisible(true);
            f.setSize(450,500);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setResizable(false);
            
            b1.addActionListener(this);
            b2.addActionListener(this);
            b3.addActionListener(this);
            b4.addActionListener(this);
            b5.addActionListener(this);
            b6.addActionListener(this);
            b7.addActionListener(this);
            b8.addActionListener(this);
            b9.addActionListener(this);
            b0.addActionListener(this);
            badd.addActionListener(this);
            bdiv.addActionListener(this);
            bmul.addActionListener(this);
            bsub.addActionListener(this);
            bdec.addActionListener(this);
            beq.addActionListener(this);
            bdel.addActionListener(this);
            bclr.addActionListener(this);
        }
    }

    /** given an ActionEvent formats and calculates
     * Override ActionListener method actionPerformed
     * @param e - input action
     */
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e){
            // some weird stuff:
            // new buttons only kind of work after a basic operation is done
            // i think its storing 'b' and using it for the new operations
            // tried to get passed needing a second input but to no previal
        if(e.getSource()==b1)
            t.setText(t.getText().concat("1"));
        
        if(e.getSource()==b2)
            t.setText(t.getText().concat("2"));
        
        if(e.getSource()==b3)
            t.setText(t.getText().concat("3"));
        
        if(e.getSource()==b4)
            t.setText(t.getText().concat("4"));
        
        if(e.getSource()==b5)
            t.setText(t.getText().concat("5"));
        
        if(e.getSource()==b6)
            t.setText(t.getText().concat("6"));
        
        if(e.getSource()==b7)
            t.setText(t.getText().concat("7"));
        
        if(e.getSource()==b8)
            t.setText(t.getText().concat("8"));
        
        if(e.getSource()==b9)
            t.setText(t.getText().concat("9"));
        
        if(e.getSource()==b0)
            t.setText(t.getText().concat("0"));
        
        if(e.getSource()==bdec)
            t.setText(t.getText().concat("."));
        
        if(e.getSource()==badd)
        {
            a=Double.parseDouble(t.getText());
            operator=1;
            t.setText("");
        } 
        
        if(e.getSource()==bsub)
        {
            a=Double.parseDouble(t.getText());
            operator=2;
            t.setText("");
        }
        
        if(e.getSource()==bmul)
        {
            a=Double.parseDouble(t.getText());
            operator=3;
            t.setText("");
        }
        
        if(e.getSource()==bdiv)
        {
            a=Double.parseDouble(t.getText());
            operator=4;
            t.setText("");
        }

        if(e.getSource()==binv)
        {
            a=Double.parseDouble(t.getText());
            operator=5;
            t.setText("");
        }
        if(e.getSource()==bsqr)
        {
            a=Double.parseDouble(t.getText());
            operator=6;
            t.setText("");
        }
        if(e.getSource()==bsqrt)
        {
            a=Double.parseDouble(t.getText());
            operator=7;
            t.setText("");
        }
        
        if(e.getSource()==beq)
        {
            switch(operator)
            {
                case 1: 
                    b=Double.parseDouble(t.getText());
                    result=a+b;
                    break;
        
                case 2: 
                    b=Double.parseDouble(t.getText());
                    result=a-b;
                    break;
        
                case 3: 
                    b=Double.parseDouble(t.getText());
                    result=a*b;
                    break;
        
                case 4: 
                    b=Double.parseDouble(t.getText());
                    result=a/b;
                    break;

                case 5: 
                    b = 1;
                    result=b/a;
                    break;

                case 6: 
                    result=a*a;
                    break;

                case 7: 
                    result=Math.sqrt(a);
                    break;
        
                default: result=0;
            }
        
            t.setText(""+result);
        }
        
        if(e.getSource()==bclr)
            t.setText("");
        
        if(e.getSource()==bdel)
        {
            String s=t.getText();
            t.setText("");
            for(int i=0;i<s.length()-1;i++)
            t.setText(t.getText()+s.charAt(i));
        }		
    }
    public static void main(String[] args){
		new ButtonListener();
	}
}