import javax.sound.midi.SoundbankResource;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GUI {
    private static JFrame jFrame;
    private static JScrollBar jScrollBar;        // task1 
    private static JButton jButton1;
    private static JLabel jLabel;
    private static JLabel hint;

    private static JComboBox jComboBox;          // task2 and task3
    private static JComboBox jComboBox2; 
    private static JButton jButton2;
    private static JLabel jLabel2;
    private static String level[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static String mode[] = {"Single Tone", "Dual Tone"};
    //private static int[] singleDict = new int[]{16000, 16200, 16400, 16600, 16800, 17000, 17200, 17400, 17600};
                                                                  //  1       2        3         4        5         6         7       8        9
    private static int[] singleDict = new int[]{2000, 2200, 2400, 2600, 2800, 3000, 3200, 3400, 3600};
                                                             // 1                 2                 3                 4                 5                 6                7                 8                 9
    private static int[] dualDict = new int[]{697, 1209, 697, 1336, 697, 1477, 770, 1209, 770, 1336, 770, 1477, 852, 1209, 852, 1336, 852, 1477};
  
    private static JTextArea jTextArea;          // task4 
    private static JButton jButton;
    private static JLabel jLabel3;
    
    private static JLabel jLabel4;                     // task5
    private static JButton jButton3;
    private static JTextField jTextField;

    public static void display() {
        jFrame = new JFrame("COMP9336");

        //task 1
        jScrollBar = new JScrollBar(0);
        jScrollBar.setMaximum(18010);
        jScrollBar.setMinimum(300);
        jScrollBar.setBounds(50, 30, 220, 30);
        jButton = new JButton("start");
        jButton.setBounds(290, 30, 70, 30);
        hint = new JLabel("task 1");
        hint.setBounds(10, 30, 40, 30);
        jLabel = new JLabel("task 1");
        jLabel.setBounds(150, 0, 50, 30);
        GUI.task1_logic();

        jFrame.add(jLabel);
        jFrame.add(hint);
        jFrame.add(jScrollBar);
        jFrame.add(jButton);

        // task 2 and task 3
        jLabel2 = new JLabel("task 2/3");
        jLabel2.setBounds(10, 80, 50, 30);
        jComboBox = new JComboBox<String>(mode);
        jComboBox.setBounds(70, 80, 100, 30);
        jComboBox2 = new JComboBox<String>(level);
        jComboBox2.setBounds(190, 80, 50, 30);
        jButton1 = new JButton("send");
        jButton1.setBounds(290, 80, 70, 30);
        GUI.task23_logic();

        jFrame.add(jComboBox);
        jFrame.add(jComboBox2);
        jFrame.add(jLabel2);
        jFrame.add(jButton1);

        // task 4 and task 5
        jLabel3 = new JLabel("task 4");
        jLabel3.setBounds(10, 150, 50, 30);
        jTextArea = new JTextArea();
        jTextArea.setLineWrap(true);
        jTextArea.setBounds(70, 150, 190, 30);
        jButton2 = new JButton("send");
        jButton2.setBounds(290, 150, 70, 30);
        GUI.task4_logic();

        jFrame.add(jLabel3);
        jFrame.add(jTextArea);
        jFrame.add(jButton2);
        
        // error detect；
        jLabel4 = new JLabel("task 5");
        jLabel4.setBounds(10, 210, 50, 30);
        jTextField = new JTextField();
        jTextField.setBounds(70, 210, 190, 30);
        jButton3 = new JButton("send");
        jButton3.setBounds(290, 210, 70, 30);
        GUI.task5_logic();
        
        jFrame.add(jLabel4);
        jFrame.add(jTextField);
        jFrame.add(jButton3);
        
        jFrame.setLayout(null);
        jFrame.setSize(400, 300);
        jFrame.setVisible(true);
    }

    public static void task1_logic() {
        
        jScrollBar.addAdjustmentListener(new AdjustmentListener(){
            public void adjustmentValueChanged(AdjustmentEvent e) {  
                jLabel.setText(String.valueOf(jScrollBar.getValue()) + "Hz");  
             }
        });

        jButton.addActionListener(new ActionListener() {           // generate single tone by given frequency
            public void actionPerformed(ActionEvent e) {
                try {
                    Sound.tone(jScrollBar.getValue(), 1000);                // tone generating
                } catch (LineUnavailableException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void task23_logic() {
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jComboBox.getSelectedIndex() == 0) {             // single tone
                    System.out.println(String.valueOf(singleDict[jComboBox2.getSelectedIndex()]));
                    try{
                        Sound.tone(singleDict[jComboBox2.getSelectedIndex()], 1000);
                    }
                    catch (LineUnavailableException e1) {}
                    
                }
                else {                                               // dual tone
                    System.out.println(String.valueOf(dualDict[jComboBox2.getSelectedIndex() * 2]) + " " + String.valueOf(dualDict[jComboBox2.getSelectedIndex() * 2 + 1]));
                    try{
                       Sound.tone(dualDict[jComboBox2.getSelectedIndex() * 2], dualDict[jComboBox2.getSelectedIndex() * 2 + 1], 900, 1); 
                    }
                    catch (LineUnavailableException e1) {}
                }
            }
        });
    }

    public static void task4_logic() {                           // send text field with modulation and implement the error correction
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(jTextArea.getText());
                String sendContext = jTextArea.getText();
                coding(sendContext);
            }
        });
    }
    
    public static void task5_logic() {
    	jButton3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println(jTextField.getText());
				String sendString = jTextField.getText();
				String str[] = sendString.split("");
				
				ArrayList<Integer> combo = new ArrayList<Integer>();                             // sequence of bits
		        combo.add(-4);
		        combo.add(-6);
		        
	            ArrayList<Integer> HammingLs = new ArrayList<Integer>();
	            for (int i = 0; i < str.length; i ++ ) {
	            	HammingLs.add(Integer.valueOf(str[i]));
	            }
	            
	            if (HammingLs.size() == 12) {
	            	int highBit = 32 * (int)HammingLs.get(0) + 16 * (int)HammingLs.get(1) + 8 * (int)HammingLs.get(2) + 4 *(int) HammingLs.get(3) + 2 * (int)HammingLs.get(4) + (int)HammingLs.get(5);
	            	int lowBit = 32 * (int)HammingLs.get(6) + 16 * (int)HammingLs.get(7) + 8 * (int)HammingLs.get(8) + 4 *(int) HammingLs.get(9) + 2 * (int)HammingLs.get(10) + (int)HammingLs.get(11);
	            	System.out.println(String.valueOf(highBit) + "-" + String.valueOf(lowBit));
	            	combo.add(highBit);
	            	combo.add(lowBit);
	            }
		        
		        combo.add(-2);
		        try {
					Sound.tone1(combo, 700, 1);
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
    }

    public static String stringToAscii(String value)
	{
		StringBuffer sbu = new StringBuffer();
		char[] chars = value.toCharArray(); 
		for (int i = 0; i < chars.length; i++) {
			if(i != chars.length - 1)
			{
				sbu.append((int)chars[i]).append(",");
			}
			else {
				sbu.append((int)chars[i]);
			}
        }
		return sbu.toString();
    }
    
    public static void coding(String s) {                               // coding
        System.out.println("Ascii is: " + stringToAscii(s));
        String str[] = stringToAscii(s).split(",");
       
        if (str.length > 0) {
		        ArrayList<Integer> combo = new ArrayList<Integer>();                             // sequence of bits
		        combo.add(-4);
		        combo.add(-6);
		        
		        for (int i = 0; i < str.length; i ++ ) {
		            String Binary = Integer.toBinaryString(Integer.valueOf(str[i]));
		            
		            Binary = String.format("%08d", Integer.valueOf(Binary));
		            ArrayList<Integer> HammingLs = Hamming_code(Binary);                      // get Hamming code
		            for (int j = 0; j < HammingLs.size(); j ++ ) {
		            	System.out.print(HammingLs.get(j));
		            }
		            System.out.println("");
		            
		            if (HammingLs.size() == 12) {
		            	int highBit = 32 * (int)HammingLs.get(0) + 16 * (int)HammingLs.get(1) + 8 * (int)HammingLs.get(2) + 4 *(int) HammingLs.get(3) + 2 * (int)HammingLs.get(4) + (int)HammingLs.get(5);
		            	int lowBit = 32 * (int)HammingLs.get(6) + 16 * (int)HammingLs.get(7) + 8 * (int)HammingLs.get(8) + 4 *(int) HammingLs.get(9) + 2 * (int)HammingLs.get(10) + (int)HammingLs.get(11);
		            	System.out.println(String.valueOf(highBit) + "-" + String.valueOf(lowBit));
		            	combo.add(highBit);
		            	combo.add(lowBit);
		            }
		        }
		        
		        combo.add(-2);
		        
		        for (int m = 0; m < combo.size(); m ++ ) {
		        	System.out.print(combo.get(m) + " ");
		        }
		        
		        System.out.println("");
		        try {
					Sound.tone1(combo, 800, 1);
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
    }

    public static ArrayList Hamming_code(String str) {                 // coding hamming code                
        ArrayList ls = new ArrayList<Integer>();
        String[] bits = str.split("");

        if (bits.length == 8) {                                       // get correct coding 8 bits, p, q, m, n is correct bits
            int p = Integer.valueOf(bits[0]) ^ Integer.valueOf(bits[1]) ^ Integer.valueOf(bits[3]) ^ Integer.valueOf(bits[4]) ^ Integer.valueOf(bits[6]);
            int q = Integer.valueOf(bits[0]) ^ Integer.valueOf(bits[2]) ^ Integer.valueOf(bits[3]) ^ Integer.valueOf(bits[5]) ^ Integer.valueOf(bits[6]);
            int m = Integer.valueOf(bits[1]) ^ Integer.valueOf(bits[2]) ^ Integer.valueOf(bits[3]) ^ Integer.valueOf(bits[7]);
            int n = Integer.valueOf(bits[4]) ^ Integer.valueOf(bits[5]) ^ Integer.valueOf(bits[6]) ^ Integer.valueOf(bits[7]);

            ls.add(p);
            ls.add(q);
            ls.add(Integer.valueOf(bits[0]));
            ls.add(m);
            ls.add(Integer.valueOf(bits[1]));
            ls.add(Integer.valueOf(bits[2]));
            ls.add(Integer.valueOf(bits[3]));
            ls.add(n);
            ls.add(Integer.valueOf(bits[4]));
            ls.add(Integer.valueOf(bits[5]));
            ls.add(Integer.valueOf(bits[6]));
            ls.add(Integer.valueOf(bits[7]));
            
            return ls;
        }
        else {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {         // MAIN function
            GUI.display();
    }
}