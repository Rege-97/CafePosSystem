package cafePosSystem;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUpWindow {
	Frame parent;
	String title;
	String message1;
	String message2;
	
	public PopUpWindow(Frame parent) {
		this.parent=parent;
		
	}
	public void showPopUp(String title,String message1,String message2) {
		this.title=title;
		this.message1=message1;
		this.message2=message2;
		
		Font font_1 = new Font("Default Font", Font.BOLD, 15);
		Font font_2 = new Font("Default Font", Font.BOLD, 12);
		
		Dialog popup = new Dialog(parent,title) {
			@Override
			public Insets getInsets() {
				return new Insets(40, 0, 15, 0);
			}
		};
		popup.setSize(300, 150);
		popup.setLayout(new BorderLayout());

		Panel p_popup_center = new Panel(new GridLayout(4, 1));

		Label lb_popup_pop1 = new Label(message1, Label.CENTER);
		Label lb_popup_pop2 = new Label(message2, Label.CENTER);
		Label lb_popup_pop3 = new Label();
		lb_popup_pop1.setFont(font_1);
		lb_popup_pop2.setFont(font_1);

		popup.add(p_popup_center, "Center");

		p_popup_center.add(lb_popup_pop3);
		p_popup_center.add(lb_popup_pop1);
		p_popup_center.add(lb_popup_pop2);
		lb_popup_pop3 = new Label();
		p_popup_center.add(lb_popup_pop3);

		Panel p_popup_south = new Panel(new FlowLayout());
		popup.add(p_popup_south, "South");
		Button bt_popup_popclose = new Button("확인");
		bt_popup_popclose.setPreferredSize(new Dimension(100, 20));
		bt_popup_popclose.setFont(font_2);
		p_popup_south.add(bt_popup_popclose);
		popup.setVisible(true);
		popup.setLocationRelativeTo(parent);
		bt_popup_popclose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					popup.setVisible(false);
					popup.dispose();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
	}
	public void showPopUp(String title,String message1,String message2,CafePosSystem_pay cpsp) {
		this.title=title;
		this.message1=message1;
		this.message2=message2;
		
		Font font_1 = new Font("Default Font", Font.BOLD, 15);
		Font font_2 = new Font("Default Font", Font.BOLD, 12);
		
		Dialog popup = new Dialog(parent,title) {
			@Override
			public Insets getInsets() {
				return new Insets(40, 0, 15, 0);
			}
		};
		popup.setSize(300, 150);
		popup.setLayout(new BorderLayout());

		Panel p_popup_center = new Panel(new GridLayout(4, 1));

		Label lb_popup_pop1 = new Label(message1, Label.CENTER);
		Label lb_popup_pop2 = new Label(message2, Label.CENTER);
		Label lb_popup_pop3 = new Label();
		lb_popup_pop1.setFont(font_1);
		lb_popup_pop2.setFont(font_1);

		popup.add(p_popup_center, "Center");

		p_popup_center.add(lb_popup_pop3);
		p_popup_center.add(lb_popup_pop1);
		p_popup_center.add(lb_popup_pop2);
		lb_popup_pop3 = new Label();
		p_popup_center.add(lb_popup_pop3);

		Panel p_popup_south = new Panel(new FlowLayout());
		popup.add(p_popup_south, "South");
		Button bt_popup_popclose = new Button("확인");
		bt_popup_popclose.setPreferredSize(new Dimension(100, 20));
		bt_popup_popclose.setFont(font_2);
		p_popup_south.add(bt_popup_popclose);
		popup.setVisible(true);
		popup.setLocationRelativeTo(parent);
		bt_popup_popclose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					cpsp.payLayout();
					
					popup.setVisible(false);
					popup.dispose();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
	}
	
}
