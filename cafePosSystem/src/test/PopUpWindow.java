package test;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

public class PopUpWindow {
	Frame frame;
	String title;
	String message1;
	String message2;

	String sql;
	Dialog popup, popupinpop;

	int gusno;
	String gusname;
	int guspoint;
	String gustel;
	String rname;
	Font font_1, font_2, f_cat;
	DecimalFormat money_format;

	public PopUpWindow(Frame frame) {

		this.frame = frame;

		money_format = new DecimalFormat("#,###");
	}

	// 기본 팝업 메서드
	public void showPopUp(String title, String message1, String message2) {
		if(popup!=null&&popup.isVisible()) {
			return;
		}
		
		this.title = title;
		this.message1 = message1;
		this.message2 = message2;
		

		font_1 = new Font("Default Font", Font.BOLD, 13);
		font_2 = new Font("Default Font", Font.BOLD, 13);
		f_cat = new Font("Default Font", Font.TRUETYPE_FONT, 10);

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
	public void showPopUp(CafePosSystem_pay cpsp,int rankup,String rankname) {
		if(popup!=null&&popup.isVisible()) {
			return;
		}
		Font font_1 = new Font("Default Font", Font.BOLD, 13);
		Font font_2 = new Font("Default Font", Font.BOLD, 12);

		popup = new Dialog(frame, "결제 완료") {
			@Override
			public Insets getInsets() {
				return new Insets(40, 0, 15, 0);
			}
		};
		popup.setSize(300, 150);
		popup.setLayout(new BorderLayout());

		Panel p_popup_center = new Panel(new GridLayout(3, 1));

		Label lb_popup_pop1 = new Label( "결제되었습니다", Label.CENTER);
		Label lb_popup_pop2 = new Label("거스름 돈은 " + cpsp.tf_change.getText() + "원 입니다.", Label.CENTER);
		Label lb_popup_pop3 = new Label("");
		lb_popup_pop1.setFont(font_1);
		lb_popup_pop2.setFont(font_1);

		popup.add(p_popup_center, "Center");

		
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
		if (rankup==1) {
			p_popup_center.remove(lb_popup_pop3);
			lb_popup_pop3 = new Label("회원 등급이 " + rankname + "(으)로 상승했습니다.", Label.CENTER);
			lb_popup_pop3.setFont(font_1);
			p_popup_center.add(lb_popup_pop3);
			
		}
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
		if(popup!=null&&popup.isVisible()) {
			return;
		}
		
		this.title = title;
		

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
						sql = "drop sequence sq_guest_gusno";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql = "create sequence sq_guest_gusno";
						ps = conn.prepareStatement(sql);
						ps.execute();
						ps.close();

					} else if (table.equals("menu")) {
						sql = "delete menu";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql = "drop sequence sq_menu_mno";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql = "create sequence sq_menu_mno";
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
						sql = "drop sequence sq_employee_eno";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql = "create sequence sq_employee_eno";
						ps = conn.prepareStatement(sql);
						ps.execute();
						ps.close();
					} else if (table.equals("sale")) {
						sql = "delete sales";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql = "drop sequence sq_sales_ono";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql = "create sequence sq_sales_ono";
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
						sql = "drop sequence sq_guest_gusno";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql = "create sequence sq_guest_gusno";
						ps = conn.prepareStatement(sql);
						ps.execute();

						sql = "delete menu";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql = "drop sequence sq_menu_mno";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql = "create sequence sq_menu_mno";
						ps = conn.prepareStatement(sql);
						ps.execute();

						sql = "delete employee";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql = "insert into employee values(0,'김채현','BOSS','010-1234-5678','admin','1234')";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql = "drop sequence sq_employee_eno";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql = "create sequence sq_employee_eno";
						ps = conn.prepareStatement(sql);
						ps.execute();

						sql = "delete sales";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql = "drop sequence sq_sales_ono";
						ps = conn.prepareStatement(sql);
						ps.execute();
						sql = "create sequence sq_sales_ono";
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
		if(popup!=null&&popup.isVisible()) {
			return;
		}
		
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
						Label lb_waring = new Label("정보를 정확하게 입력해주세요!", Label.CENTER);
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
						Label lb_addguest = new Label("등록이 완료되었습니다.", Label.CENTER);
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

	// 퀵 메뉴 메뉴 등록 팝업
	public void quickMenuAdd(Connection conn) {
		if(popup!=null&&popup.isVisible()) {
			return;
		}
		Font font_1 = new Font("Default Font", Font.BOLD, 15);
		Font font_2 = new Font("Default Font", Font.BOLD, 12);

		popup = new Dialog(frame, "메뉴 등록") {
			@Override
			public Insets getInsets() {
				return new Insets(40, 20, 15, 20);
			}
		};
		popup.setSize(300, 150);
		popup.setLayout(new BorderLayout());

		Panel p_popup_center = new Panel(new GridLayout(2, 2));
		Label lb_mname = new Label("메뉴 이름");
		lb_mname.setFont(font_2);
		TextField tf_mname = new TextField();
		Label lb_mprice = new Label("메뉴 가격");
		lb_mprice.setFont(font_2);
		TextField tf_mprice = new TextField();

		popup.add(p_popup_center, "Center");
		p_popup_center.add(lb_mname);
		p_popup_center.add(tf_mname);
		p_popup_center.add(lb_mprice);
		p_popup_center.add(tf_mprice);

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

		tf_mprice.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (!(e.getKeyChar() >= 48 && e.getKeyChar() <= 57)) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		bt_popup_ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (tf_mname.getText().equals("") || tf_mprice.getText().equals("")) {
						p_popup_center.removeAll();
						Label lb_waring = new Label("정보를 정확하게 입력해주세요!", Label.CENTER);
						lb_waring.setFont(font_1);
						p_popup_center.add(lb_waring);
						popup.validate();

						Thread.sleep(1500);
						p_popup_center.removeAll();

						p_popup_center.add(lb_mname);
						p_popup_center.add(tf_mname);
						p_popup_center.add(lb_mprice);
						p_popup_center.add(tf_mprice);

					} else {
						sql = "insert into menu values(sq_menu_mno.nextval,?,?)";

						PreparedStatement ps = conn.prepareStatement(sql);
						ps.setString(1, tf_mname.getText());
						ps.setInt(2, Integer.parseInt(tf_mprice.getText()));

						ps.execute();
						p_popup_center.removeAll();
						Label lb_addguest = new Label("등록이 완료되었습니다.", Label.CENTER);
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

	// 퀵 메뉴 포인트 지급 팝업
	public void quickPointAdd(Connection conn) {
		if(popup!=null&&popup.isVisible()) {
			return;
		}
		Font font_1 = new Font("Default Font", Font.BOLD, 15);
		Font font_2 = new Font("Default Font", Font.BOLD, 12);

		popup = new Dialog(frame, "포인트 적립") {
			@Override
			public Insets getInsets() {
				return new Insets(40, 20, 15, 20);
			}
		};
		popup.setSize(300, 275);
		popup.setLayout(new BorderLayout());

		Panel p_popup_north = new Panel(new BorderLayout(5, 5));
		popup.add(p_popup_north, "North");
		
		Panel p_popup_north_north=new Panel(new BorderLayout());
		p_popup_north.add(p_popup_north_north,"North");
		TextField tf_cat = new TextField("번호  이름          포인트       전화번호");
		tf_cat.setEditable(false);
		tf_cat.setFont(new Font("Default Font",Font.PLAIN,10));
		p_popup_north_north.add(tf_cat,"North");
		List li_guest = new List();
		li_guest.setFont(f_cat);
		p_popup_north_north.add(li_guest, "Center");

		Panel p_popup_north_center = new Panel(new FlowLayout());
		p_popup_north.add(p_popup_north_center, "Center");
		CheckboxGroup cbg_guest = new CheckboxGroup();
		Checkbox cb_gusname = new Checkbox("이름", cbg_guest, true);
		Checkbox cb_gustel = new Checkbox("전화번호", cbg_guest, false);

		TextField tf_search = new TextField();
		tf_search.setPreferredSize(new Dimension(90, 20));
		Button bt_search = new Button("검색");

		p_popup_north_center.add(cb_gusname);
		p_popup_north_center.add(cb_gustel);
		p_popup_north_center.add(tf_search);
		p_popup_north_center.add(bt_search);

		Panel p_popup_north_south = new Panel(new GridLayout(2, 3));
		p_popup_north.add(p_popup_north_south, "South");
		Label lb_gusno = new Label("회원번호", Label.CENTER);
		Label lb_gusname = new Label("회원이름", Label.CENTER);
		Label lb_guspoint = new Label("현재 포인트", Label.CENTER);

		TextField tf_gusno = new TextField();
		tf_gusno.setEditable(false);
		TextField tf_gusname = new TextField();
		tf_gusname.setEditable(false);
		TextField tf_guspoint = new TextField();
		tf_guspoint.setEditable(false);

		p_popup_north_south.add(lb_gusno);
		p_popup_north_south.add(lb_gusname);
		p_popup_north_south.add(lb_guspoint);
		p_popup_north_south.add(tf_gusno);
		p_popup_north_south.add(tf_gusname);
		p_popup_north_south.add(tf_guspoint);

		Panel p_popup_center = new Panel(new GridLayout(1, 2, 5, 5));
		popup.add(p_popup_center, "Center");
		Label lb_addpoint = new Label("추가 포인트", Label.CENTER);
		lb_addpoint.setFont(font_2);
		TextField tf_addpoint = new TextField("0");
		tf_addpoint.setEditable(false);
		tf_addpoint.setPreferredSize(new Dimension(200, 20));
		p_popup_center.add(lb_addpoint);
		p_popup_center.add(tf_addpoint);

		Panel p_popup_south = new Panel(new FlowLayout());
		popup.add(p_popup_south, "South");
		Button bt_popup_ok = new Button("적립");
		bt_popup_ok.setPreferredSize(new Dimension(100, 20));
		bt_popup_ok.setFont(font_2);

		Button bt_popup_cancle = new Button("취소");
		bt_popup_cancle.setPreferredSize(new Dimension(100, 20));
		bt_popup_cancle.setFont(font_2);

		p_popup_south.add(bt_popup_ok);
		p_popup_south.add(bt_popup_cancle);
		popup.setVisible(true);
		popup.setLocationRelativeTo(frame);

		bt_search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					li_guest.removeAll();
					PreparedStatement ps;
					if (cbg_guest.getSelectedCheckbox().getLabel().equals("이름")) {
						sql = "select * from guest where gusname like ? order by gusno";
						ps = conn.prepareStatement(sql);
						ps.setString(1, "%" + tf_search.getText() + "%");
					} else {
						sql = "select * from guest where gustel like ? order by gusno";
						ps = conn.prepareStatement(sql);
						ps.setString(1, "%" + tf_search.getText() + "%");
					}

					ResultSet rs = ps.executeQuery();

					while (rs.next()) {

						gusno = rs.getInt("gusno");
						gusname = rs.getString("gusname");
						guspoint = rs.getInt("guspoint");
						gustel = rs.getString("gustel");

						if (gusno == 0) {
							continue;
						}

						li_guest.add(" " + emptySet(gusno+"", 8) + emptySet_Kor(gusname, 18) + emptySet(guspoint+"", 20) + gustel);
					}
					rs.close();
					ps.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		li_guest.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (!li_guest.getSelectedItem().substring(3, 4).equals(" ")
							&& li_guest.getSelectedItem().substring(4, 5).equals(" ")) {
						gusno = Integer.parseInt(li_guest.getSelectedItem().substring(1, 4));
					} else if (!li_guest.getSelectedItem().substring(2, 3).equals(" ")
							&& li_guest.getSelectedItem().substring(3, 4).equals(" ")) {
						gusno = Integer.parseInt(li_guest.getSelectedItem().substring(1, 3));
					} else {
						gusno = Integer.parseInt(li_guest.getSelectedItem().substring(1, 2));
					}

					sql = "select * from guest where gusno=?";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setInt(1, gusno);
					ResultSet rs = ps.executeQuery();

					while (rs.next()) {
						gusname = rs.getString("gusname");
						guspoint = rs.getInt("guspoint");
						gustel = rs.getString("gustel");
					}

					tf_gusno.setText(gusno + "");
					tf_gusname.setText(gusname);
					tf_guspoint.setText(money_format.format(guspoint) + "");

					tf_addpoint.setEditable(true);

					rs.close();
					ps.close();

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		tf_addpoint.addTextListener(new TextListener() {

			@Override
			public void textValueChanged(TextEvent e) {
				if (tf_addpoint.getText().equals("")) {
					tf_addpoint.setText("0");
				}

			}
		});

		bt_popup_ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (tf_addpoint.getText().equals("0") && !tf_gusno.getText().equals("")) {
						tf_addpoint.setText("포인트를 입력하세요");
						Thread.sleep(1000);
						tf_addpoint.setText("0");
					} else if (tf_gusno.getText().equals("")) {
						tf_addpoint.setText("회원을 선택하세요");
						Thread.sleep(1000);
						tf_addpoint.setText("0");
					} else {
						gusno = Integer.parseInt(tf_gusno.getText());
						guspoint = guspoint + Integer.parseInt(tf_addpoint.getText());

						sql = "update guest set guspoint=? where gusno=?";
						PreparedStatement ps = conn.prepareStatement(sql);

						ps.setInt(1, guspoint);
						ps.setInt(2, gusno);

						ps.execute();

						tf_addpoint.setText("적립 완료");
						Thread.sleep(1000);
						tf_gusname.setText("");
						tf_gusno.setText("");
						tf_guspoint.setText("");
						tf_addpoint.setText("0");
						li_guest.removeAll();

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
public static String emptySet_Kor(String text,int length) {
		
		StringBuffer empty_s=new StringBuffer();
		StringBuffer result=new StringBuffer();
		length-=text.length()*3;
		for(int i=0;i<length;i++) {
			empty_s.append(" ");
		}
		result.append(text);
		result.append(empty_s.toString());
		
		
	    return result.toString();
	}
	
	public static String emptySet(String text,int length) {
		
		StringBuffer empty_s=new StringBuffer();
		StringBuffer result=new StringBuffer();
		length-=text.length()*2;
		for(int i=0;i<length;i++) {
			empty_s.append(" ");
		}
		if(text.length()<4) {
		empty_s.deleteCharAt(0);
		}
		result.append(text);
		result.append(empty_s.toString());
		
		
		return result.toString();
	}
	
}
