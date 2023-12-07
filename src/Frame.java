import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Frame {
    
    Frame() {
        //Initializing Flatlaf (Flatlaf is a library containing 50+ themes for java swing)
        FlatArcOrangeIJTheme.install(new FlatArcOrangeIJTheme());
        UIManager.put("Button.arc", 20);
        UIManager.put("TextComponent.arc", 20);
        
        //Number formatter to separate large numbers by commas and set the possible length of the fraction part
        NumberFormat formatter = new DecimalFormat();
        formatter.setMaximumFractionDigits(2);
        formatter.setGroupingUsed(true);
        
        //Initializing all the needed elements
        JFrame frame = frame();
        JPanel panel = panel(frame);
        JLabel drawer = drawLines(panel);
        JButton solve = solveIt(panel);
        JTextField input = input(panel);
        Icon bouncingAni = new ImageIcon("img/bouncingAni.gif");
        
        //Putting a copy of label method in a 5 index array, so we can position them by looping over the array
        JLabel[] labels = new JLabel[6];
        for (int i = 0; i < labels.length; i++) {
            if (i == 5) {
                labels[i] = label(panel);
                labels[i].setText("Time");
                labels[i].setBounds(227, 362, 30, 40);
                break;
            }
            labels[i] = label(panel);
            labels[i].setBounds(0, (i * 80), 65, 20);
        }
        
        //Positioning the textFields as a graph area
        for (int i = 10; i <= 330; i += 40) {
            JTextField areas = grid(panel);
            areas.setLocation(65, i);
        }
        
        //All the actions
        ActionListener action = action(input, drawer, bouncingAni, labels, formatter);
        input.addActionListener(action);
        solve.addActionListener(action);
        keyDisabler(input.getInputMap());
        
        //After everything is done, set the frame to be visible (it should come last for various reasons)
        frame.setVisible(true);
    }
    
    static JFrame frame() {
        
        ImageIcon icon = new ImageIcon("C:\\Users\\AL AMEER\\IdeaProjects\\Bouncing ball\\img\\ball.png");
        JFrame frame = new JFrame("Bouncing Ball!");
        frame.setIconImage(icon.getImage());
        frame.setBounds(440, 100, 455, 490);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        return frame;
    }
    static JPanel panel(JFrame frame) {
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);
        return panel;
    }
    static JButton solveIt(JPanel panel) {
        
        Icon solvingIcon = new ImageIcon("img/solve.png");
        JButton solve = new JButton(solvingIcon);
        solve.setBounds(5, 395, 55, 40);
        solve.setToolTipText("Press This After Inputting the Starting Height\nOnly Numbers Allowed");
        solve.setFocusable(false);
        solve.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        panel.add(solve);
        return solve;
    }
    
    static JTextField input(JPanel panel) {
        
        JTextField input = new JTextField();
        input.setBounds(65, 395, 360, 40);
        input.setMargin(new Insets(10, 10, 10, 10));
        input.setHorizontalAlignment(JTextField.CENTER);
        input.setToolTipText("Input The Starting Height\nOf The Ball Here In Meters");
        input.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 14));
        panel.add(input);
        return input;
    }
    static JTextField grid(JPanel panel) {
        
        JTextField area = new JTextField();
        area.setFocusable(false);
        area.setSize(360, 42);
        area.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        area.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0xF37800)));
        
        panel.add(area);
        return area;
    }
    private static void keyDisabler(InputMap inputMap) {
        //Disables all keys except the numbers from 0 to 9 and . (49 - 57 in ascii, 46 for the .)
        char[] keys = new char[95];
        for (int i = 32; i < 127; i++) {
            if (i == 48) {
                i = 58;
            }
            if (i == 46) {
                continue;
            }
            keys[i - 32] = (char) i;
        }
        for (char disableThis : keys) {
            inputMap.put(KeyStroke.getKeyStroke(disableThis), "none");
        }
    }
    
    static JLabel label(JPanel panel) {
        
        JLabel label = new JLabel((String) null, SwingConstants.RIGHT);
        label.setText(" -");
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        panel.add(label);
        return label;
    }
    static JLabel drawLines(JPanel panel) {
        
        JLabel labelBouncing = new JLabel();
        labelBouncing.setBounds(65, 10, 360, 360);
        labelBouncing.setVisible(false);
        panel.add(labelBouncing);
        return labelBouncing;
    }
    public ActionListener action(JTextField input, JLabel drawer, Icon bouncingAni, JLabel[] labels, NumberFormat formatter) {
        
        return e -> {
            if (!input.getText().isBlank() && !input.getText().matches(".*[a-z].*")) {
                drawer.setIcon(bouncingAni);
                drawer.setVisible(true);
                double currentHeight = Double.parseDouble(input.getText());
                double distance = currentHeight * 5;
                
                for (int i = 0; i < labels.length - 1; i++) {
                    labels[i].setText(formatter.format(currentHeight) + "m -");
                    currentHeight = (currentHeight * 2) / 3;
                }
                input.setText("Total Distance = " + distance + "m");
            }
            else {
                for (JLabel label : labels) {
                    label.setText(" -");
                }
                input.setText("");
                drawer.setIcon(null);
            }
        };
    }
}