package chatting.application;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Client implements ActionListener{
    
    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame f = new JFrame();
    
    Client(){
        
        f.setLayout(null);
        
        JPanel p1= new JPanel();
        p1.setBackground(new Color(7, 94, 84)); //Green panel on which dp appears
        p1.setBounds(0,0, 450, 70);
        p1.setLayout(null);
        f.add(p1);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons-20230325T112926Z-001/icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel((i3));
        back.setBounds(5, 20, 25, 25);
        p1.add(back);
        
        //The frame closes on clicking the arrow
        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });
        
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons-20230325T112926Z-001/icons/2.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel((i6));
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);
        
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons-20230325T112926Z-001/icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel((i9));
        video.setBounds(300, 20, 30, 30);
        p1.add(video);
        
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons-20230325T112926Z-001/icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel((i12));
        phone.setBounds(360, 20, 35, 30);
        p1.add(phone);
        
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons-20230325T112926Z-001/icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel((i15));
        morevert.setBounds(410, 20, 10, 25);
        p1.add(morevert);
        
        JLabel name =new JLabel("Kabir");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);
        
        JLabel status =new JLabel("Active Now");
        status.setBounds(110, 35, 100, 18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        p1.add(status);
        
        a1 = new JPanel(); //Gray area
        a1.setBounds(5, 75, 440, 560);
        f.add(a1);
        
        text = new JTextField(); //Area where you type
        text.setBounds(5, 640, 310, 35);
        text.setBounds(5, 640, 310, 35);
        f.add(text);
        
        JButton send = new JButton("Send");
        send.setBounds(320, 640, 123, 35);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this); //Message issent on clicking send
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(send);
        
        f.setSize(450, 680);//size of frame
        f.setLocation(800, 50);//determines where your frame will appear on screen
        f.setUndecorated(true);//for removing the upper bar from the frame
        f.getContentPane().setBackground(Color.WHITE);
        
        f.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        try{
            String out = text.getText();

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right); //One message will come below the other
            vertical.add(Box.createVerticalStrut(15)); // Gap b/w two vertical messages is 15

            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");//Making the textbox empty after clicking send

            f.repaint();
            f.invalidate();
            f.validate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //return type of this method is JPanel(here p2)
    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel(out);
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));//size of message
        output.setBackground(new Color(218, 247, 166));//bgcolor of text 
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50)); //padding the text box
        
        panel.add(output);
        
        //showing time
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);
        
        return panel;
    }
    
    public static void main(String[] args){
        new Client();
        
        try {
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());//recieves messages
            dout = new DataOutputStream(s.getOutputStream());
            
            while(true){
                   a1.setLayout(new BorderLayout());
                   String msg = din.readUTF();
                   JPanel panel = formatLabel(msg);
                   
                   JPanel left = new JPanel(new BorderLayout());//recieved msgs appear on left of frame
                   left.add(panel, BorderLayout.LINE_START);
                   vertical.add(left);
                   
                   vertical.add(Box.createVerticalStrut(15));
                   a1.add(vertical, BorderLayout.PAGE_START);
                   
                   f.validate();
                   
               }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
