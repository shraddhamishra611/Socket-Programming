package Communication;

import java.net.*;
import java.awt.EventQueue;
import java.io.*;

import javax.swing.JFrame;
import java.awt.TextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Server extends JFrame{
	
	static ServerSocket ss;
	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;
	
	private JFrame frmServerSide;
	private JTextField textField;
	static TextArea textArea;
	private JButton btn_send;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				//new Server().setVisible(true);
				try {
					Server window = new Server();
					window.frmServerSide.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		String msgin = "";
		try {
			ss = new ServerSocket(1202); // throws an exception if it can't listen on the specified port.
			s = ss.accept();
			din = new DataInputStream(s.getInputStream()); // getInputStream() returns an input stream for the given socket. 
			dout = new DataOutputStream(s.getOutputStream());
			
			while(!msgin.equals("exit")){
				msgin = din.readUTF();
				textArea.setText(textArea.getText().trim() + "\n Client: " + msgin);
			}
		}catch(Exception e) {
			System.out.println("Exception from Server's main: ");
			e.printStackTrace();
		}
	}


	public Server() {
		
		frmServerSide = new JFrame();
		frmServerSide.setTitle("SERVER SIDE");
		frmServerSide.setBounds(100, 100, 580, 348);
		frmServerSide.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServerSide.getContentPane().setLayout(null);
		frmServerSide.setVisible(true);
		
		textArea = new TextArea();
		textArea.setBounds(31, 21, 497, 178);
		frmServerSide.getContentPane().add(textArea);
		
		textField = new JTextField();
		textField.setBounds(31, 236, 322, 46);
		frmServerSide.getContentPane().add(textField);
		textField.setColumns(10);
		
		btn_send = new JButton("Send");
		btn_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String msgout = "";
					msgout = textField.getText().trim();
					dout.writeUTF(msgout);	
					//textField.setText("");
				}catch(Exception e) {
					System.out.println("Exception from Server's action: " + e);
				}
			}
		});
		btn_send.setBounds(382, 236, 146, 35);
		frmServerSide.getContentPane().add(btn_send);
	}
}