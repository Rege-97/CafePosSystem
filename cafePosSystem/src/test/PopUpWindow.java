package test;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class PopUpWindow {
	Frame frame;
	String title;
	String message1;
	String message2;

	String sql;
	Dialog popup;

	public PopUpWindow(Frame frame) {

		this.frame = frame;

	}

	// 기본 팝업 메서드
	public void showPopUp(String title, String message1, String message2) {
		this.title = title;
		this.message1 = message1;
		this.message2 = message2;

		Font font_1 = new Font("Default Font", Font.BOLD, 15);
		Font font_2 = new Font("Default Font", Font.BOLD, 13);

		popup = new Dialog(frame, title) {
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
		popup.setLocationRelativeTo(frame);
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

		bt_popup_popclose.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					popup.setVisible(false);
					popup.dispose();

				}

			}
		});
		popup.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				popup.setVisible(false);
				popup.dispose();
			}
		});

	}

	// 결제 완료 팝업 메서드
	public void showPopUp(String title, String message1, String message2, CafePosSystem_pay cpsp) {
		this.title = title;
		this.message1 = message1;
		this.message2 = message2;

		Font font_1 = new Font("Default Font", Font.BOLD, 15);
		Font font_2 = new Font("Default Font", Font.BOLD, 12);

		popup = new Dialog(frame, title) {
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
		popup.setLocationRelativeTo(frame);
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

		bt_popup_popclose.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						cpsp.payLayout();

						popup.setVisible(false);
						popup.dispose();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		popup.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
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
	// 초기화 기능 팝업
	public void showResetPopup(String title, MainFrame mf, Connection conn, String table) {
		this.title = title;
		this.message1 = message1;

		Font font_1 = new Font("Default Font", Font.BOLD, 15);
		Font font_2 = new Font("Default Font", Font.BOLD, 12);

		popup = new Dialog(frame, title) {
			@Override
			public Insets getInsets() {
				return new Insets(40, 0, 15, 0);
			}
		};
		popup.setSize(300, 150);
		popup.setLayout(new BorderLayout());

		Panel p_popup_center = new Panel(new GridLayout(4, 1));

		Label lb_popup_pop1 = new Label(title, Label.CENTER);
		Label lb_popup_pop2 = new Label("정말 초기화 하시겠습니까?", Label.CENTER);
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
		Button bt_popup_ok = new Button("초기화");
		bt_popup_ok.setPreferredSize(new Dimension(100, 20));
		bt_popup_ok.setFont(font_2);

		Button bt_popup_cancle = new Button("취소");
		bt_popup_cancle.setPreferredSize(new Dimension(100, 20));
		bt_popup_cancle.setFont(font_2);

		p_popup_south.add(bt_popup_ok);
		p_popup_south.add(bt_popup_cancle);
		popup.setVisible(true);
		popup.setLocationRelativeTo(frame);

		bt_popup_ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PreparedStatement ps;

					if (table.equals("guest")) {
						sql = "delete guest";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql = "insert into guest values(0, '비회원',0,'000-0000-0000','Unrank',0)";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql="drop sequence sq_guest_gusno";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql="create sequence sq_guest_gusno";
						ps = conn.prepareStatement(sql);
						ps.execute();
						ps.close();
						
					} else if (table.equals("menu")) {
						sql = "delete menu";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql="drop sequence sq_menu_mno";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql="create sequence sq_menu_mno";
						ps = conn.prepareStatement(sql);
						ps.execute();
						ps.close();
					} else if (table.equals("emp")) {
						sql = "delete employee";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql = "insert into employee values(0,'김채현','BOSS','010-1234-5678','admin','1234')";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql="drop sequence sq_employee_eno";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql="create sequence sq_employee_eno";
						ps = conn.prepareStatement(sql);
						ps.execute();
						ps.close();
					} else if (table.equals("sale")) {
						sql = "delete sales";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql="drop sequence sq_sales_ono";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql="create sequence sq_sales_ono";
						ps = conn.prepareStatement(sql);
						ps.execute();
						ps.close();
					} else if (table.equals("all")) {
						sql = "delete guest";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql = "insert into guest values(0, '비회원',0,'000-0000-0000','Unrank',0)";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql="drop sequence sq_guest_gusno";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql="create sequence sq_guest_gusno";
						ps = conn.prepareStatement(sql);
						ps.execute();
						
						sql = "delete menu";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql="drop sequence sq_menu_mno";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql="create sequence sq_menu_mno";
						ps = conn.prepareStatement(sql);
						ps.execute();
						
						sql = "delete employee";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql = "insert into employee values(0,'김채현','BOSS','010-1234-5678','admin','1234')";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql="drop sequence sq_employee_eno";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql="create sequence sq_employee_eno";
						ps = conn.prepareStatement(sql);
						ps.execute();
						
						sql = "delete sales";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql="drop sequence sq_sales_ono";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql="create sequence sq_sales_ono";
						ps = conn.prepareStatement(sql);
						ps.execute();

					}

					popup.setVisible(false);
					popup.dispose();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		bt_popup_cancle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				popup.setVisible(false);
				popup.dispose();

			}
		});

		popup.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				popup.setVisible(false);
				popup.dispose();
			}
		});

	}

	// 퀵 메뉴 고객 등록 팝업
	public void quickGuestAdd(Connection conn) {

		Font font_1 = new Font("Default Font", Font.BOLD, 15);
		Font font_2 = new Font("Default Font", Font.BOLD, 12);

		popup = new Dialog(frame, "회원 등록") {
			@Override
			public Insets getInsets() {
				return new Insets(40, 20, 15, 20);
			}
		};
		popup.setSize(300, 150);
		popup.setLayout(new BorderLayout());

		Panel p_popup_center = new Panel(new GridLayout(2, 2));
		Label lb_gusname = new Label("회원 이름");
		lb_gusname.setFont(font_2);
		TextField tf_gusname = new TextField();
		Label lb_gustel = new Label("회원 전화번호");
		lb_gustel.setFont(font_2);
		TextField tf_gustel = new TextField();

		popup.add(p_popup_center, "Center");
		p_popup_center.add(lb_gusname);
		p_popup_center.add(tf_gusname);
		p_popup_center.add(lb_gustel);
		p_popup_center.add(tf_gustel);

		Panel p_popup_south = new Panel(new FlowLayout());
		popup.add(p_popup_south, "South");
		Button bt_popup_ok = new Button("등록");
		bt_popup_ok.setPreferredSize(new Dimension(100, 20));
		bt_popup_ok.setFont(font_2);

		Button bt_popup_cancle = new Button("취소");
		bt_popup_cancle.setPreferredSize(new Dimension(100, 20));
		bt_popup_cancle.setFont(font_2);

		p_popup_south.add(bt_popup_ok);
		p_popup_south.add(bt_popup_cancle);
		popup.setVisible(true);
		popup.setLocationRelativeTo(frame);

		bt_popup_ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (tf_gusname.getText().equals("") || tf_gustel.getText().equals("")) {
						p_popup_center.removeAll();
						Label lb_waring = new Label("정보를 정확하게 입력해주세요!",Label.CENTER);
						lb_waring.setFont(font_1);
						p_popup_center.add(lb_waring);
						popup.validate();

						Thread.sleep(1500);
						p_popup_center.removeAll();

						p_popup_center.add(lb_gusname);
						p_popup_center.add(tf_gusname);
						p_popup_center.add(lb_gustel);
						p_popup_center.add(tf_gustel);

					} else {
						sql = "insert into guest values(sq_guest_gusno.nextval,?,0,?,'Bronze',0)";

						PreparedStatement ps = conn.prepareStatement(sql);
						ps.setString(1, tf_gusname.getText());
						ps.setString(2, tf_gustel.getText());
						
						ps.execute();
						p_popup_center.removeAll();
						Label lb_addguest = new Label("등록이 완료되었습니다.",Label.CENTER);
						lb_addguest.setFont(font_1);
						p_popup_center.add(lb_addguest);

						p_popup_south.remove(bt_popup_ok);
						bt_popup_cancle.setLabel("확인");
						popup.validate();

					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		
		bt_popup_cancle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				popup.setVisible(false);
				popup.dispose();
				
			}
		});

		popup.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				popup.setVisible(false);
				popup.dispose();
			}
		});

	}
}
