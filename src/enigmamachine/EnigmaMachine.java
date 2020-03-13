/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enigmamachine;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author Giuseppe
 */
public class EnigmaMachine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Encryption c=new Encryption();
        JFrame finestra=new JFrame(); 
        finestra.setSize(500, 500);
        JTextArea testo=new JTextArea(8,42);
        testo.setEditable(false);
        testo.setLineWrap(true);
        JTextArea testo_cifrato=new JTextArea(8,42);
        testo_cifrato.setLineWrap(true);
        testo_cifrato.setEditable(false);
        JButton button=new JButton(c.getRotorsPosition());   
        JButton cancella=new JButton("Resetta");
        JButton coppia=new JButton("Accoppiamenti");
        testo.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke){}
            @Override
            public void keyPressed(KeyEvent ke) {
                if(ke.getKeyCode()>=65 && ke.getKeyCode()<=90 && !ke.isControlDown())
                {
                    testo.append(ke.getKeyChar()+"");
                    testo_cifrato.append(c.encryptLetter((char)ke.getKeyCode())+"");
                    button.setText(c.getRotorsPosition());
                }
            }

            @Override
            public void keyReleased(KeyEvent ke){}
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String temp=JOptionPane.showInputDialog("Inserisci la posizione dei rotori", c.getRotorsPosition());
                if(temp!=null && !temp.equals("")) c.setRotorsPosition(temp.toUpperCase().split("-"));
                button.setText(c.getRotorsPosition());
                
            }
        });
        
        coppia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String temp=JOptionPane.showInputDialog("Inserisci la coppia di lettere", "A-B");
                if(temp!=null && !temp.equals("")) c.setPlugBoard(temp.toUpperCase().split("-"));
            }
        });
        
        cancella.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                testo.setText("");
                testo_cifrato.setText(testo.getText());
                c.setRotorsPosition(new String[]{"A","A","A"});
                button.setText(c.getRotorsPosition());
            }
        });
        
        
        GridBagLayout layout=new GridBagLayout();      
        GridBagConstraints p=new  GridBagConstraints(); 
        finestra.setResizable(false);                   
        finestra.setLayout(layout);                     
        
        
        p.weightx=1;
        p.weighty=1;
        p.gridwidth=5;
        p.gridx=0;    
        p.gridy=0;    
        layout.setConstraints(testo, p);
        finestra.add(testo);
        
        p.weightx=1;
        p.weighty=2;
        p.gridx=0;    
        p.gridy=1;    
        layout.setConstraints(testo_cifrato, p);
        finestra.add(testo_cifrato);
        
        p.weightx=1;
        p.weighty=1;
        p.gridx=2;    
        p.gridy=2;    
        layout.setConstraints(button, p);
        finestra.add(button);
        
        
        p.weightx=1;
        p.weighty=1;
        p.gridx=1;    
        p.gridy=3;    
        layout.setConstraints(coppia, p);
        finestra.add(coppia);
        
        p.weightx=1;
        p.weighty=1;
        p.gridx=1;    
        p.gridy=4;    
        layout.setConstraints(cancella, p);
        finestra.add(cancella);
        

        finestra.setVisible(true); 
        finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }
    
}
