/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TimeZoneConverter;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


/**
 *
 * @author Dakota Scott
 * 4/4/2014
 */
public class frame extends JFrame {
    private JLabel timeLbl, outputLbl;
    private JTextField hours;
    private JTextField minutes;
    private JButton convertButton;
    private JRadioButton cstButton, mstButton, pstButton, gmtButton;
    private JCheckBox dateCheckBox;
    
    public frame(String title)
    {
        
        super(title);
        createMenu();
        createControlPanel();
    }
    
    public void createMenu()
    {
        JMenuBar menuBar = new JMenuBar();
        
        setJMenuBar(menuBar);
        menuBar.add(createFileMenu());
    }
    

    
    public JMenu createFileMenu()
    {
        JMenu menu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        ActionListener listener = new ExitItemListener();
        
        exitItem.addActionListener(listener);
        menu.add(exitItem);
        
        return menu;
    }
    
    private class ExitItemListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            System.exit(0);
        }
    }
    
    public void createControlPanel()
    {
        JPanel buttonPanel = createRadioButtons();
        JPanel inputPanel = createTextField();
        JPanel outputPanel = createOutput();
        JPanel checkBoxPanel = createCheckBox();
        
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());

        controlPanel.add(inputPanel, BorderLayout.CENTER);
        controlPanel.add(buttonPanel, BorderLayout.WEST);
        controlPanel.add(outputPanel, BorderLayout.SOUTH);
        controlPanel.add(checkBoxPanel, BorderLayout.EAST);
        
        add(controlPanel);
    }
    
    public JPanel createRadioButtons()
    {
        JPanel buttonPanel = new JPanel();

        cstButton = new JRadioButton("CST (EST-1)");
        mstButton = new JRadioButton("MST (EST-2)");
        pstButton = new JRadioButton("PST (EST-3)");
        gmtButton = new JRadioButton("GMT (EST+5)");
        
        buttonPanel.setLayout(new GridLayout(2,2));
        
        ButtonGroup group = new ButtonGroup();
        group.add(cstButton);
        group.add(mstButton);
        group.add(pstButton);
        group.add(gmtButton);
        
        buttonPanel.add(cstButton);
        buttonPanel.add(mstButton);
        buttonPanel.add(pstButton);
        buttonPanel.add(gmtButton);
        buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), "Time Zones"));
     
        cstButton.setSelected(true);
        
        JPanel panel = new JPanel();
       
        panel.add(buttonPanel);
        return panel;
    }
    
    public JPanel createTextField()
    {
        JPanel textFieldPanel = new JPanel();
        
        hours = new JTextField("", 2);
        timeLbl = new JLabel(":");
        minutes = new JTextField("", 2);

        convertButton = new JButton("Convert");
        convertButton.addActionListener(new ChoiceListener());

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ClearListener());
        textFieldPanel.add(hours);
        textFieldPanel.add(timeLbl);
        textFieldPanel.add(minutes);
        textFieldPanel.add(convertButton);
        textFieldPanel.add(clearButton);
        textFieldPanel.setBorder(new TitledBorder(new EtchedBorder(), "HH:MM"));
        
        JPanel panel = new JPanel();
        panel.add(textFieldPanel);
        return panel;
    }  
    
    public JPanel createCheckBox()
    {
        JPanel checkBoxPanel = new JPanel();
        
        dateCheckBox = new JCheckBox("Use Computer's Time?");
        checkBoxPanel.add(dateCheckBox);
        checkBoxPanel.setBorder(new TitledBorder(new EtchedBorder(), "Options"));
        
        JPanel panel = new JPanel();
        panel.add(checkBoxPanel);
        return panel;
    }
    
    public JPanel createOutput()
    {
        JPanel outputPanel = new JPanel();
        
        outputLbl = new JLabel();
        outputPanel.add(outputLbl);
        
        JPanel panel = new JPanel();
        panel.add(outputPanel);
        return panel;
    }
    
    private class ClearListener implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            frame.this.hours.setText("");
            frame.this.minutes.setText("");
            frame.this.outputLbl.setText("");
            frame.this.dateCheckBox.setSelected(false);
        }
    }
   
    private class ChoiceListener implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            Date convertedHours;
            int minutes;
            String input = frame.this.hours.getText().trim();
            String input2 = frame.this.hours.getText().trim();
            
            if (input.equals("")&&!dateCheckBox.isSelected())
            {
                outputLbl.setText("Invalid, Clear and Try Again");
            }

            else if (dateCheckBox.isSelected())
            {
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("h");
                SimpleDateFormat minutesFormat = new SimpleDateFormat("m");
                
                convertedHours = setConsole(date);
                
                outputLbl.setText("The time is: " + dateFormat.format(convertedHours) + ":" + minutesFormat.format(date));
            }
                     
            else 
            {
                Date date = null;  
                SimpleDateFormat dateFormat = new SimpleDateFormat("h");
                    
                try 
                {
                    date = dateFormat.parse(input);
                }
                catch (ParseException ex) 
                {
                    outputLbl.setText("Invalid");
                }
                
                minutes = Integer.parseInt(input2);
                convertedHours = setConsole(date);
                outputLbl.setText("The time is: " + dateFormat.format(convertedHours) + ":" + minutes);
            }      
        }
    }
    
    private Date setConsole(Date date)
    {
        Calendar cal = Calendar.getInstance();
        
        cal.setTime(date);  
        
        if (cstButton.isSelected())
        {
            cal.add(Calendar.HOUR, -1);
            return cal.getTime();
        }
        
        if (mstButton.isSelected())
        {
            cal.add(Calendar.HOUR, -2);
            return cal.getTime();
        }
        
        if (pstButton.isSelected())
        {
            cal.add(Calendar.HOUR, -3);
            return cal.getTime();
        }
        
        if (gmtButton.isSelected())
        {
            cal.add(Calendar.HOUR, 5);
            return cal.getTime();
        }
        
        return null;
    }  
}
