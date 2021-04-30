package Communication;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.TextArea;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Client extends JFrame{

	private JFrame frame;
	private JTextField textField;
	static TextArea textArea;
	private JButton btn_send;
	
	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
			}
				//new Client().setVisible(true);
			}
		});
		try {
			s = new Socket("127.0.0.1",1202); //A client creates a socket on its end of the communication & attempts to connect that socket to a server. 
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());
			
			String msgin = "";
			while(!msgin.equals("exit")) {
				msgin = din.readUTF();
				textArea.setText(textArea.getText().trim() + "\n Server: " + msgin);
				// getText method returns a String, while the setText receives a String,it writes the msg on label. 
			}
		}
		catch(Exception e) {
			System.out.println("Exception from Client's main: " + e);
		}
	}

	public Client() {
	
		frame = new JFrame();
		frame.setTitle("CLIENT SIDE");
		frame.setBounds(100, 100, 580, 348);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		textArea = new TextArea();
		textArea.setBounds(33, 10, 497, 167);
		frame.getContentPane().add(textArea);
		
		textField = new JTextField();
		textField.setBounds(33, 236, 321, 36);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		btn_send = new JButton("Send");
		btn_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String msgout = "";
				try {
					
					msgout = textField.getText().trim();
					dout.writeUTF(msgout);
					//textField.setText("");
				}
				catch(Exception e) {
					System.out.println("Exception from Client's action: ");
					e.printStackTrace();
				}
			}
		});
		btn_send.setBounds(382, 236, 146, 35);
		frame.getContentPane().add(btn_send);
	}
}
