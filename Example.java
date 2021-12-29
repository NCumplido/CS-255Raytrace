import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.lang.Math.*;

// OK this is not best practice - maybe you'd like to extend
// the BufferedImage class for the image processing operations?
// I won't give extra marks for that though.
public class Example extends JFrame {

        final JButton                 
        BTN_INVERT = new JButton("Invert"),
        BT_SLOW_GAMMA = new JButton("Slow Gamma"),
        BTN_FAST_GAMMA = new JButton("Fast Gamma"),
        BTN_CORRELATE = new JButton("Correlate"),
        BTN_EQUAL = new JButton("Equalize"),
        BTN_OPEN_FILE = new JButton("Open file"),
        BTN_RESET_IMAGE = new JButton("Reset Image"),
        BTN_THRESHOLD = new JButton("Threshold");

        ImageProcessorFunctions imgProcFunct;
        BufferedImage image;
        JLabel image_icon;
        JSlider val_slider;
        Container container = getContentPane();
        File selectedFile;

        GUIEventHandler handler = new GUIEventHandler();

        final String DEFAULT_IMAGE_NAME = "raytrace.jpg";

        /*
         * This function sets up the GUI and reads an image
         */
        public void Example() {
                imgProcFunct = new ImageProcessorFunctions();
                image = imgProcFunct.image;
                initialiseGUI();
                initialiseHandler();

                // ... and display everything
                pack();
                setLocationRelativeTo(null);
                setVisible(true);
                this.setExtendedState(JFrame.MAXIMIZED_BOTH);
                this.setUndecorated(true);
                this.setVisible(true);
        }

        private void initialiseHandler() {
                BTN_INVERT.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                                image = imgProcFunct.Invert();
                                // Update image
                                image_icon.setIcon(new ImageIcon(image));                        }
                    });
                //BTN_INVERT.addActionListener(handler);
                BT_SLOW_GAMMA.addActionListener(handler);
                BTN_FAST_GAMMA.addActionListener(handler);
                BTN_CORRELATE.addActionListener(handler);
                BTN_EQUAL.addActionListener(handler);
                BTN_OPEN_FILE.addActionListener(handler);
                BTN_RESET_IMAGE.addActionListener(handler);
                val_slider.addChangeListener(handler);
                BTN_THRESHOLD.addChangeListener(handler);
        }

        private void initialiseGUI() {
                container.setLayout(new FlowLayout());
                image_icon = new JLabel(new ImageIcon(image));
                container.add(image_icon);
                container.add(BTN_INVERT);
                container.add(BTN_EQUAL);
                container.add(BT_SLOW_GAMMA);
                container.add(BTN_FAST_GAMMA);
                container.add(BTN_CORRELATE);
                container.add(BTN_RESET_IMAGE);
                container.add(BTN_OPEN_FILE);
                container.add(BTN_THRESHOLD);

                val_slider = new JSlider(0, 100);
                container.add(val_slider);
                val_slider.setMajorTickSpacing(50);
                val_slider.setMinorTickSpacing(10);
                val_slider.setPaintTicks(true);
                val_slider.setPaintLabels(true);
                // see
                // http://docs.oracle.com/javase/7/docs/api/javax/swing/JSlider.html
                // for documentation (e.g. how to get the value, how to display vertically if
                // you want)
        }

        /*
         * This is the event handler for the application
         */
        private class GUIEventHandler implements ActionListener, ChangeListener {
                // Change handler (e.g. for sliders)
                public void stateChanged(ChangeEvent e) {
                        System.out.println(val_slider.getValue());
                        // you could pass the value to another function to change something
                        // then update the image
                }

                public void actionPerformed(ActionEvent event) {

                        // if (event.getSource() == BTN_INVERT) {
                        //         // Call image processing function
                        //         image = Invert(image);

                        //         // Update image
                        //         image_icon.setIcon(new ImageIcon(image));
                        // } else 
                        if (event.getSource() == BT_SLOW_GAMMA) {
                                // Call image processing function
                                image = imgProcFunct.SlowGamma();

                                // Update image
                                image_icon.setIcon(new ImageIcon(image));
                        } else if (event.getSource() == BTN_FAST_GAMMA) {
                                // Call image processing function
                                image = imgProcFunct.FastGamma();

                                // Update image
                                image_icon.setIcon(new ImageIcon(image));
                        } else if (event.getSource() == BTN_CORRELATE) {
                                // Call image processing function
                                image = imgProcFunct.BlueFade();

                                // Update image
                                image_icon.setIcon(new ImageIcon(image));
                        } else if (event.getSource() == BTN_EQUAL) {
                                // Call function
                                image = imgProcFunct.Equalise();

                                // Update image
                                image_icon.setIcon(new ImageIcon(image));
                        } else if (event.getSource() == BTN_OPEN_FILE) {
                                FileHelper fileChooser = new FileHelper(container);
                                selectedFile = fileChooser.openDialogue();
                                image_icon = imgProcFunct.ChangeImage(selectedFile, image_icon);
                        } else if (event.getSource() == BTN_RESET_IMAGE) {
                                image_icon = imgProcFunct.ResetImage(image_icon);
                        } else if (event.getSource() == BTN_THRESHOLD) {
                                imgProcFunct.Threshold();
                        }
                }
        }

        



        public static void main(String[] args) throws IOException {

                Example e = new Example();
                e.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                e.Example();

                SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                                new Example().setVisible(true);
                        }
                });
        }
}