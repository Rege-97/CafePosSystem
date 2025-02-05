package cafePosSystem;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.Scrollbar;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Choice;
import java.awt.TextField;
import java.awt.Button;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.Color;

public class TestDesign extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestDesign frame = new TestDesign();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestDesign() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 824, 736);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		Label label = new Label("회원 관리");
		label.setFont(new Font("Dialog", Font.BOLD, 14));
		panel.add(label);
		
		Panel panel_1 = new Panel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		Panel panel_3 = new Panel();
		panel_1.add(panel_3, BorderLayout.NORTH);
		FlowLayout fl_panel_3 = new FlowLayout(FlowLayout.CENTER, 10, 5);
		panel_3.setLayout(fl_panel_3);
		
		JLabel lblNewLabel = new JLabel("회원 검색  ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setSelectedIndex(3);
		comboBox.setEditable(true);
		comboBox.setToolTipText("");
		panel_3.add(comboBox);
		
		textField = new JTextField();
		panel_3.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("검색");
		panel_3.add(btnNewButton);
		
		Panel panel_4 = new Panel();
		panel_1.add(panel_4, BorderLayout.EAST);
		panel_4.setLayout(new GridLayout(5, 2, 0, 0));
		
		ScrollPane scrollPane = new ScrollPane();
		panel_1.add(scrollPane, BorderLayout.WEST);
		scrollPane.setBackground(new Color(255, 255, 255));
		
		Panel panel_5 = new Panel();
		panel_1.add(panel_5, BorderLayout.SOUTH);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		Panel panel_6 = new Panel();
		panel_5.add(panel_6, BorderLayout.CENTER);
		panel_6.setLayout(new GridLayout(0, 5, 0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("회원 번호");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("회원 이름");
		panel_6.add(lblNewLabel_2);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_3 = new JLabel("회원 전화번호");
		panel_6.add(lblNewLabel_3);
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_4 = new JLabel("회원 등급");
		panel_6.add(lblNewLabel_4);
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_5 = new JLabel("회원 포인트");
		panel_6.add(lblNewLabel_5);
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		panel_6.add(textField_4);
		textField_4.setColumns(10);
		
		textField_1 = new JTextField();
		panel_6.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		panel_6.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		panel_6.add(textField_3);
		textField_3.setColumns(10);
		
		textField_5 = new JTextField();
		panel_6.add(textField_5);
		textField_5.setColumns(10);
		
		JPanel panel_7 = new JPanel();
		panel_5.add(panel_7, BorderLayout.SOUTH);
		
		JButton btnNewButton_1 = new JButton("New button");
		panel_7.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		panel_7.add(btnNewButton_2);
		
		Panel panel_9 = new Panel();
		panel_5.add(panel_9, BorderLayout.NORTH);
		
		JLabel lblNewLabel_6 = new JLabel("회원 등록");
		panel_9.add(lblNewLabel_6);
		
		Panel panel_2 = new Panel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
	}

}
