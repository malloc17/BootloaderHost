package Draw;


import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Burak
 */
public class DrawLed {
    ArrayList<ImageIcon> imageIcon = new ArrayList(2);
    ImageIcon scaledredLedIcon;
    ImageIcon scaledGreenLedIcon;
    
    public final byte RED_COLOR = 0;
    public final byte GREEN_COLOR = 1;
        

    public DrawLed(ArrayList<JLabel> label) {

        ImageIcon redLed = new ImageIcon("src\\Pictures\\red_top.png");
        ImageIcon greenLed = new ImageIcon("src\\Pictures\\green_top.png");
        
        Image img = redLed.getImage();
        Image imgScale = img.getScaledInstance(16, 16, Image.SCALE_SMOOTH); // jLabel4.getWidth()
        scaledredLedIcon = new ImageIcon(imgScale);
        
        img = greenLed.getImage();
        imgScale = img.getScaledInstance(16, 16, Image.SCALE_SMOOTH); // jLabel4.getWidth()
        scaledGreenLedIcon = new ImageIcon(imgScale);
        
        InitImageIcon();

        
        
        for (int i = 0; i < label.size(); i++) {
            label.get(i).setIcon(scaledredLedIcon);
        }
    }
    
    public void setLedColor(JLabel label,byte Color)
    {
        label.setIcon(imageIcon.get(Color));
    }

    private void InitImageIcon() {
        imageIcon.add(scaledredLedIcon);
        imageIcon.add(scaledGreenLedIcon);
    }
    
    
    
}
