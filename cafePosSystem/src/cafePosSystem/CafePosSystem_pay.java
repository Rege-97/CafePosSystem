package cafePosSystem;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.JSeparator;

public class CafePosSystem_pay extends Frame {

	Panel p_main;

	int gusno;
	String gusname;
	int guspoint;
	String gustel;
	String rname;
	double rpoint;
	int mno;
	String mname;
	int mprice;
	int ono;
	String odate;
	int opoint;
	int ocash;
	int ocount;
	int eno;
	String ename;
	String gname;
	String etel;
	String eid;
	String epwd;
	String gsal;
	int money;
	int gussale;

	static Connection conn;
	String sql;

	public CafePosSystem_pay() throws Exception {

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					System.exit(0);
				} catch (Exception e1) {
				}
			}
		});

		HashMap<Integer, Integer> hm_select_menu_count = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> hm_select_menu_price = new HashMap<Integer, Integer>();
		ArrayList<Integer> arr = new ArrayList<Integer>();
		hashMapSet(hm_select_menu_count, hm_select_menu_price, arr);

		Font f_title = new Font("Default Font", Font.BOLD, 15);
		Font f_subtitle = new Font("Default Font", Font.BOLD, 12);
		Font f_menu = new Font("Default Font", Font.TRUETYPE_FONT, 15);
		Font f_cat = new Font("Default Font", Font.TRUETYPE_FONT, 10);

		DecimalFormat money_format = new DecimalFormat("#,###");
		SimpleDateFormat sdf_now = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

		Dialog dialog_nomenu = new Dialog(this, "메뉴 없음") {
			@Override
			public Insets getInsets() {
				return new Insets(40, 0, 15, 0);
			}
		};

		Dialog dialog_pay = new Dialog(this, "메뉴 없음") {
			@Override
			public Insets getInsets() {
				return new Insets(40, 0, 15, 0);
			}
		};

		Dialog dialog_rankup = new Dialog(this, "등급 상승!") {
			@Override
			public Insets getInsets() {
				return new Insets(40, 0, 15, 0);
			}
		};

		p_main = new Panel(new BorderLayout(30, 30));
		this.add(p_main);
		JSeparator js;

		// 상단
		Panel p_north = new Panel(new BorderLayout(5, 5));
		p_main.add(p_north, "North");
		Label title = new Label("쌍용 1팀 카페 결제 시스템", Label.CENTER);
		title.setFont(f_title);
		p_north.add(title, "North");

		js = new JSeparator();
		p_north.add(js, "Center");

		// 중단
		Panel p_center = new Panel(new GridLayout(2, 2, 30, 30));
		p_main.add(p_center, "Center");

		// 중단 -1
		Panel p_center_1 = new Panel(new BorderLayout(10, 10));
		p_center.add(p_center_1);

		List li_selectmenu = new List();
		li_selectmenu.setFont(f_menu);
		ScrollPane sp_selectmenu = new ScrollPane();
		sp_selectmenu.add(li_selectmenu);

		p_center_1.add(sp_selectmenu, "Center");

		// 중단 -2
		Panel p_center_2 = new Panel(new BorderLayout(5, 5));
		p_center.add(p_center_2);

		List li_menu = new List();
		li_menu.setFont(f_menu);

		menuListUp(money_format, li_menu);

		p_center_2.add(li_menu, "Center");

		Panel p_center_2_south = new Panel(new BorderLayout(5, 5));
		p_center_2_south.setPreferredSize(new Dimension(200, 60));
		p_center_2.add(p_center_2_south, "South");

		Panel p_center_2_south_north = new Panel(new BorderLayout());
		p_center_2_south.add(p_center_2_south_north, "North");

		Label lb_menu_count = new Label("수량", Label.CENTER);
		lb_menu_count.setFont(f_subtitle);
		lb_menu_count.setPreferredSize(new Dimension(100, 20));
		TextField tf_menu_count = new TextField();
		tf_menu_count.setFont(f_subtitle);

		p_center_2_south_north.add(lb_menu_count, "West");
		p_center_2_south_north.add(tf_menu_count, "Center");

		Panel p_center_2_south_south = new Panel(new GridLayout(1, 2));
		p_center_2_south.add(p_center_2_south_south);

		Button bt_menu_add = new Button("추가");
		bt_menu_add.setFont(f_subtitle);

		Button bt_menu_del = new Button("삭제");
		bt_menu_del.setFont(f_subtitle);

		p_center_2_south_south.add(bt_menu_add);
		p_center_2_south_south.add(bt_menu_del);

		// 중단 -3
		Panel p_center_3 = new Panel(new BorderLayout(5, 5));
		p_center.add(p_center_3);

		Panel p_center_3_center = new Panel(new GridLayout(5, 2, 10, 10));
		p_center_3_center.setPreferredSize(new Dimension(330, 500));
		p_center_3.add(p_center_3_center, "West");

		Label lb_allmoney = new Label("총 금액", Label.LEFT);
		lb_allmoney.setFont(f_title);

		TextField tf_allmoney = new TextField("0");
		tf_allmoney.setFont(f_title);
		tf_allmoney.setEditable(false);

		Label lb_usepoint = new Label("사용 포인트", Label.LEFT);
		lb_usepoint.setFont(f_title);

		TextField tf_usepoint = new TextField("0");
		tf_usepoint.setEditable(false);
		tf_usepoint.setFont(f_title);

		Label lb_getmoney = new Label("받은 금액", Label.LEFT);
		lb_getmoney.setFont(f_title);

		TextField tf_getmoney = new TextField();
		tf_getmoney.setFont(f_title);

		Label lb_balance = new Label("남은 금액", Label.LEFT);
		lb_balance.setFont(f_title);

		TextField tf_balance = new TextField("0");
		tf_balance.setEditable(false);
		tf_balance.setFont(f_title);

		Label lb_change = new Label("거스름돈", Label.LEFT);
		lb_change.setFont(f_title);

		TextField tf_change = new TextField("0");
		tf_change.setEditable(false);
		tf_change.setFont(f_title);

		p_center_3_center.add(lb_allmoney);
		p_center_3_center.add(tf_allmoney);
		p_center_3_center.add(lb_usepoint);
		p_center_3_center.add(tf_usepoint);
		p_center_3_center.add(lb_getmoney);
		p_center_3_center.add(tf_getmoney);
		p_center_3_center.add(lb_balance);
		p_center_3_center.add(tf_balance);
		p_center_3_center.add(lb_change);
		p_center_3_center.add(tf_change);

		Panel p_center_3_east = new Panel(new GridLayout(5, 1, 10, 10));
		p_center_3.add(p_center_3_east, "Center");

		for (int i = 0; i < 5; i++) {
			Label lb_won = new Label("원");
			lb_won.setFont(f_title);
			p_center_3_east.add(lb_won);
		}

		// 중단 -4
		Panel p_center_4 = new Panel(new BorderLayout());
		p_center.add(p_center_4);

		Panel p_center_4_north = new Panel(new BorderLayout());
		p_center_4.add(p_center_4_north, "North");
		Label lb_guest = new Label("                        회원 검색", Label.CENTER);
		lb_guest.setFont(f_subtitle);
		Button bt_unrank = new Button("비회원 주문");
		bt_unrank.setFont(f_subtitle);

		p_center_4_north.add(lb_guest, "Center");
		p_center_4_north.add(bt_unrank, "East");

		Panel p_center_4_center = new Panel(new BorderLayout());
		p_center_4.add(p_center_4_center, "Center");

		TextField tf_guest_cat = new TextField(" 번호   회원이름   포인트   전화번호          회원등급   결제금액");
		tf_guest_cat.setEditable(false);
		tf_guest_cat.setFont(f_cat);
		p_center_4_center.add(tf_guest_cat, "North");

		List li_guest = new List();
		li_guest.setFont(f_cat);

		p_center_4_center.add(li_guest, "Center");

		Panel p_center_4_center_south = new Panel(new FlowLayout());
		p_center_4_center.add(p_center_4_center_south, "South");

		CheckboxGroup cbg_guest_search = new CheckboxGroup();
		Checkbox cb_guest_search1 = new Checkbox("회원이름", cbg_guest_search, true);
		Checkbox cb_guest_search2 = new Checkbox("전화번호", cbg_guest_search, false);

		TextField tf_guest = new TextField();
		tf_guest.setPreferredSize(new Dimension(120, 20));
		Button bt_search = new Button("검색");
		bt_search.setFont(f_subtitle);
		bt_search.setPreferredSize(new Dimension(80, 20));
		p_center_4_center_south.add(cb_guest_search1);
		p_center_4_center_south.add(cb_guest_search2);
		p_center_4_center_south.add(tf_guest);
		p_center_4_center_south.add(bt_search);

		Panel p_center_4_south = new Panel(new GridLayout(2, 4, 30, 5));
		p_center_4.add(p_center_4_south, "South");

		Label lb_gusno = new Label("회원 번호", Label.CENTER);
		TextField tf_gusno = new TextField("0");
		tf_gusno.setPreferredSize(new Dimension(100, 20));
		tf_gusno.setEditable(false);
		Label lb_gusname = new Label("회원 이름", Label.CENTER);
		TextField tf_gusname = new TextField("비회원");
		tf_gusname.setPreferredSize(new Dimension(100, 20));
		tf_gusname.setEditable(false);
		Label lb_gusrank = new Label("회원 등급", Label.CENTER);
		TextField tf_gusrank = new TextField("Unrank");

		tf_gusrank.setPreferredSize(new Dimension(100, 20));
		tf_gusrank.setEditable(false);
		Label lb_guest_point = new Label("잔여 포인트", Label.CENTER);
		TextField tf_guest_point = new TextField("0");
		tf_guest_point.setPreferredSize(new Dimension(100, 20));
		tf_guest_point.setEditable(false);

		p_center_4_south.add(lb_gusno);
		p_center_4_south.add(tf_gusno);
		p_center_4_south.add(lb_gusname);
		p_center_4_south.add(tf_gusname);
		p_center_4_south.add(lb_gusrank);
		p_center_4_south.add(tf_gusrank);
		p_center_4_south.add(lb_guest_point);
		p_center_4_south.add(tf_guest_point);

		// 하단
		Panel p_south = new Panel(new GridLayout(1, 2));
		p_south.setPreferredSize(new Dimension(740, 40));
		p_main.add(p_south, "South");
		Button bt_pay = new Button("결제");
		bt_pay.setFont(f_title);
		Button bt_reset = new Button("입력 초기화");
		bt_reset.setFont(f_title);
		p_south.add(bt_pay);
		p_south.add(bt_reset);

		// 이벤트

		tf_menu_count.addKeyListener(new KeyListener() {

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
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					selectMenuAdd(li_menu, li_selectmenu, hm_select_menu_count, hm_select_menu_price, money_format, tf_menu_count, tf_allmoney, arr);
				}
			}
		});
		tf_usepoint.addKeyListener(new KeyListener() {

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

		tf_getmoney.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (!(e.getKeyChar() >= 48 && e.getKeyChar() <= 57)) {
					e.consume();
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(!(tf_getmoney.getText().equals("")) &&e.getKeyCode()==KeyEvent.VK_ENTER) {
					
				}

			}
		});

		li_menu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectMenuAdd(li_menu, li_selectmenu, hm_select_menu_count, hm_select_menu_price, money_format,
						tf_menu_count, tf_allmoney, arr);

			}
		});

		bt_menu_add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (li_menu.getSelectedIndex() == -1) {
					return;
				}

				selectMenuAdd(li_menu, li_selectmenu, hm_select_menu_count, hm_select_menu_price, money_format,
						tf_menu_count, tf_allmoney, arr);

			}
		});

		bt_menu_del.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (li_selectmenu.getSelectedIndex() == -1) {
					return;
				}

				selectMenuDelete(li_selectmenu, hm_select_menu_count, hm_select_menu_price, tf_menu_count, tf_allmoney,
						arr, money_format);
			}
		});

		tf_allmoney.addTextListener(new TextListener() {

			@Override
			public void textValueChanged(TextEvent e) {
				payMoney(tf_usepoint, tf_allmoney, tf_getmoney, tf_change, tf_balance, money_format, arr,
						hm_select_menu_price);
			}
		});

		tf_usepoint.addTextListener(new TextListener() {

			@Override
			public void textValueChanged(TextEvent e) {

				if (!tf_usepoint.getText().equals("")) {
					if (Integer.parseInt(tf_usepoint.getText()) > Integer.parseInt(tf_guest_point.getText())) {
						tf_usepoint.setText(tf_guest_point.getText());
					}
				} else {
					tf_usepoint.setText("0");
				}

				payMoney(tf_usepoint, tf_allmoney, tf_getmoney, tf_change, tf_balance, money_format, arr,
						hm_select_menu_price);

			}
		});

		tf_guest_point.addTextListener(new TextListener() {

			@Override
			public void textValueChanged(TextEvent e) {

				if (tf_guest_point.getText().equals("0")) {
					tf_usepoint.setEditable(false);
				}

				if (Integer.parseInt(tf_guest_point.getText()) >= 1000) {
					tf_usepoint.setText(tf_guest_point.getText());
					tf_usepoint.setEditable(true);
				}

			}
		});

		tf_getmoney.addTextListener(new TextListener() {

			@Override
			public void textValueChanged(TextEvent e) {
				payMoney(tf_usepoint, tf_allmoney, tf_getmoney, tf_change, tf_balance, money_format, arr,
						hm_select_menu_price);

			}
		});
		bt_search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				searchGuest(li_guest, cbg_guest_search, tf_guest);
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
						rname = rs.getString("rname");
						gussale = rs.getInt("gussale");
					}

					tf_gusno.setText(gusno + "");
					tf_gusname.setText(gusname);
					tf_gusrank.setText(rname);
					tf_guest_point.setText(guspoint + "");

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		bt_unrank.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tf_gusno.setText("0");
				tf_gusname.setText("비회원");
				tf_gusrank.setText("Unrank");
				tf_guest_point.setText("0");

			}
		});
		bt_reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					menuListUp(money_format, li_menu);
					hashMapSet(hm_select_menu_count, hm_select_menu_price, arr);
					tf_gusno.setText("0");
					tf_gusname.setText("비회원");
					tf_gusrank.setText("Unrank");
					tf_guest_point.setText("0");
					li_selectmenu.removeAll();
					li_guest.removeAll();
					tf_allmoney.setText("0");
					tf_usepoint.setText("0");
					tf_balance.setText("0");
					tf_getmoney.setText("0");
					tf_change.setText("0");
					tf_menu_count.setText("");
					tf_guest.setText("");

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		;

		bt_pay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int rankup = 0;
					String rankname = "";
					if (tf_allmoney.getText().equals("0")) {
						dialog_nomenu.setSize(300, 150);
						dialog_nomenu.setLayout(new BorderLayout());

						Panel p_dialog_nomenu_center = new Panel(new GridLayout(4, 1));

						Label lb_dialog_nomenu_pop1 = new Label("메뉴가 담겨있지 않습니다.", Label.CENTER);
						Label lb_dialog_nomenu_pop2 = new Label("메뉴를 담아주세요.", Label.CENTER);
						Label lb_dialog_nomenu_pop3 = new Label();
						lb_dialog_nomenu_pop1.setFont(f_title);
						lb_dialog_nomenu_pop2.setFont(f_title);

						dialog_nomenu.add(p_dialog_nomenu_center, "Center");

						p_dialog_nomenu_center.add(lb_dialog_nomenu_pop3);
						p_dialog_nomenu_center.add(lb_dialog_nomenu_pop1);
						p_dialog_nomenu_center.add(lb_dialog_nomenu_pop2);
						lb_dialog_nomenu_pop3 = new Label();
						p_dialog_nomenu_center.add(lb_dialog_nomenu_pop3);

						Panel p_dialog_nomenu_south = new Panel(new FlowLayout());
						dialog_nomenu.add(p_dialog_nomenu_south, "South");
						Button bt_dialog_nomenu_popclose = new Button("확인");
						bt_dialog_nomenu_popclose.setPreferredSize(new Dimension(100, 20));
						bt_dialog_nomenu_popclose.setFont(f_subtitle);
						p_dialog_nomenu_south.add(bt_dialog_nomenu_popclose);
						dialog_nomenu.setVisible(true);
						dialog_nomenu.setLocationRelativeTo(p_main);
						bt_dialog_nomenu_popclose.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								try {
									dialog_nomenu.setVisible(false);
									dialog_nomenu.dispose();
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						});

					} else {

						int bank_to_money = 0;
						int allmoney = 0;
						int usepoint = Integer.parseInt(tf_usepoint.getText());
						gusno = Integer.parseInt(tf_gusno.getText());
						for (int i = 0; i < arr.size(); i++) {
							allmoney += hm_select_menu_price.get(arr.get(i));
						}
						if (tf_balance.getText().equals(tf_allmoney.getText())) {
							tf_getmoney.setText(allmoney + "");
						}
						// 금고 입금
						sql = "select * from bank";
						PreparedStatement ps = conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery();
						while (rs.next()) {
							bank_to_money += rs.getInt("money");
						}
						bank_to_money = bank_to_money + allmoney - usepoint;
						sql = "update bank set money=?";
						ps = conn.prepareStatement(sql);
						ps.setInt(1, bank_to_money);
						ps.executeUpdate();

						if (!tf_gusno.getText().equals("0")) {
							if (usepoint > 0) {
								// 포인트 차감
								sql = "select * from guest where gusno=?";
								ps = conn.prepareStatement(sql);
								ps.setInt(1, gusno);
								while (rs.next()) {
									guspoint = rs.getInt("guspoint");
								}

								guspoint -= usepoint;
								sql = "update guest set guspoint=? where gusno=?";
								ps = conn.prepareStatement(sql);
								ps.setInt(1, guspoint);
								ps.setInt(2, gusno);
								ps.executeUpdate();
							} else {
								// 포인트 적립
								sql = "select * from guest g,rank r where g.rname=r.rname and g.gusno=?";
								ps = conn.prepareStatement(sql);
								ps.setInt(1, gusno);
								rs = ps.executeQuery();
								while (rs.next()) {
									guspoint = rs.getInt("guspoint");
									rpoint = rs.getDouble("rpoint");
								}
								guspoint += (allmoney * rpoint);
								sql = "update guest set guspoint=? where gusno=?";
								ps = conn.prepareStatement(sql);
								ps.setInt(1, guspoint);
								ps.setInt(2, gusno);
								ps.executeUpdate();
							}

							// 누적금액
							sql = "select * from guest where gusno=?";
							ps = conn.prepareStatement(sql);
							ps.setInt(1, gusno);
							while (rs.next()) {
								gussale = rs.getInt("gussale");
							}

							gussale += allmoney;
							sql = "update guest set gussale=? where gusno=?";
							ps = conn.prepareStatement(sql);
							ps.setInt(1, gussale);
							ps.setInt(2, gusno);
							ps.executeUpdate();

							// 회원등급 상승
							if (gussale >= 50000 && gussale < 100000&& tf_gusrank.getText().equals("Bronze")) {
								sql = "update guest set rname=? where gusno=?";
								ps = conn.prepareStatement(sql);
								ps.setString(1, "Silver");
								ps.setInt(2, gusno);
								rankup = ps.executeUpdate();
								rankname = "Silver";
							} else if (gussale >= 100000 && gussale < 200000&& tf_gusrank.getText().equals("Silver")) {
								sql = "update guest set rname=? where gusno=?";
								ps = conn.prepareStatement(sql);
								ps.setString(1, "Gold");
								ps.setInt(2, gusno);
								rankup = ps.executeUpdate();
								rankname = "Gold";
							} else if (gussale >= 200000&& tf_gusrank.getText().equals("Gold")) {
								sql = "update guest set rname=? where gusno=?";
								ps = conn.prepareStatement(sql);
								ps.setString(1, "Platinum");
								ps.setInt(2, gusno);
								rankup = ps.executeUpdate();
								rankname = "Platinum";
							}

						}
						// 결제 기록 저장

						Calendar now = Calendar.getInstance();
						java.sql.Timestamp jst = new java.sql.Timestamp(now.getTimeInMillis());
						int menucount = 0;
						int menuprice = 0;

						for (int i = 0; i < arr.size(); i++) {
							if (hm_select_menu_count.get(arr.get(i)) != 0) {
								menucount++;
							}
						}
						if (usepoint != 0) {
							usepoint = usepoint / menucount;
						}

						for (int i = 0; i < arr.size(); i++) {

							if (hm_select_menu_count.get(arr.get(i)) != 0) {
								ocount = hm_select_menu_count.get(arr.get(i));
								mno = arr.get(i);

								menuprice = hm_select_menu_price.get(arr.get(i));

								sql = "insert into sales values(sq_sales_ono.nextval,?,?,?,?,?,?)";
								ps = conn.prepareStatement(sql);
								ps.setInt(1, mno);
								ps.setInt(2, gusno);
								ps.setInt(3, usepoint);
								ps.setInt(4, menuprice - usepoint);
								ps.setTimestamp(5, jst);
								ps.setInt(6, ocount);
								ps.execute();
							}

						}

						dialog_pay.setSize(300, 150);
						dialog_pay.setLayout(new BorderLayout());

						Panel p_dialog_pay_center = new Panel(new GridLayout(4, 1));

						Label lb_dialog_pay_pop1 = new Label("결제되었습니다", Label.CENTER);
						Label lb_dialog_pay_pop2 = new Label("거스름 돈은 " + tf_change.getText() + "원 입니다.", Label.CENTER);
						Label lb_dialog_pay_pop3 = new Label("", Label.CENTER);
						lb_dialog_pay_pop1.setFont(f_title);
						lb_dialog_pay_pop2.setFont(f_title);

						dialog_pay.add(p_dialog_pay_center, "Center");

						p_dialog_pay_center.add(lb_dialog_pay_pop3);
						p_dialog_pay_center.add(lb_dialog_pay_pop1);
						p_dialog_pay_center.add(lb_dialog_pay_pop2);
						lb_dialog_pay_pop3 = new Label();
						p_dialog_pay_center.add(lb_dialog_pay_pop3);

						Panel p_dialog_pay_south = new Panel(new FlowLayout());
						dialog_pay.add(p_dialog_pay_south, "South");
						Button bt_dialog_pay_close = new Button("확인");
						bt_dialog_pay_close.setPreferredSize(new Dimension(100, 20));
						bt_dialog_pay_close.setFont(f_subtitle);
						p_dialog_pay_south.add(bt_dialog_pay_close);
						dialog_pay.setVisible(true);
						dialog_pay.setLocationRelativeTo(p_main);

						bt_dialog_pay_close.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								try {
									menuListUp(money_format, li_menu);
									hashMapSet(hm_select_menu_count, hm_select_menu_price, arr);
									tf_gusno.setText("0");
									tf_gusname.setText("비회원");
									tf_gusrank.setText("Unrank");
									tf_guest_point.setText("0");
									li_selectmenu.removeAll();
									li_guest.removeAll();
									tf_allmoney.setText("0");
									tf_usepoint.setText("0");
									tf_getmoney.setText("0");
									tf_balance.setText("0");
									tf_change.setText("0");
									tf_menu_count.setText("");
									tf_guest.setText("");

									dialog_pay.setVisible(false);
									dialog_pay.dispose();
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						});

					}
		
					if (rankup == 1) {
						dialog_rankup.setSize(300, 150);
						dialog_rankup.setLayout(new BorderLayout());

						Panel p_dialog_rankup_center = new Panel(new GridLayout(4, 1));

						Label lb_dialog_rankup_pop1 = new Label("축하합니다.", Label.CENTER);
						Label lb_dialog_rankup_pop2 = new Label("회원 등급이 " + rankname + "(으)로 상승했습니다.", Label.CENTER);
						Label lb_dialog_rankup_pop3 = new Label("", Label.CENTER);
						lb_dialog_rankup_pop1.setFont(f_title);
						lb_dialog_rankup_pop2.setFont(f_title);

						dialog_rankup.add(p_dialog_rankup_center, "Center");

						p_dialog_rankup_center.add(lb_dialog_rankup_pop3);
						p_dialog_rankup_center.add(lb_dialog_rankup_pop1);
						p_dialog_rankup_center.add(lb_dialog_rankup_pop2);
						lb_dialog_rankup_pop3 = new Label();
						p_dialog_rankup_center.add(lb_dialog_rankup_pop3);

						Panel p_dialog_rankup_south = new Panel(new FlowLayout());
						dialog_rankup.add(p_dialog_rankup_south, "South");
						Button bt_dialog_rankup_close = new Button("확인");
						bt_dialog_rankup_close.setPreferredSize(new Dimension(100, 20));
						bt_dialog_rankup_close.setFont(f_subtitle);
						p_dialog_rankup_south.add(bt_dialog_rankup_close);
						dialog_rankup.setVisible(true);
						dialog_rankup.setLocationRelativeTo(p_main);

						bt_dialog_rankup_close.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								try {

									dialog_rankup.setVisible(false);
									dialog_rankup.dispose();
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						});
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		});
	}

	// 결제 금액 자동 계산 메서드
	public void payMoney(TextField tf_usepoint, TextField tf_allmoney, TextField tf_getmoney, TextField tf_change,
			TextField tf_balance, DecimalFormat money_format, ArrayList<Integer> arr,
			HashMap<Integer, Integer> hm_select_menu_price) {
		int allmoney = 0;
		int usepoint = 0;
		int getmoney = 0;
		int balance = 0;
		int change = 0;

		if (tf_usepoint.getText().equals("")) {
			usepoint = 0;
		} else {
			usepoint = Integer.parseInt(tf_usepoint.getText());
		}

		if (tf_getmoney.getText().equals("")) {
			getmoney = 0;
		} else {
			getmoney = Integer.parseInt(tf_getmoney.getText());
		}

		for (int i = 0; i < arr.size(); i++) {
			allmoney += hm_select_menu_price.get(arr.get(i));
		}

		if (allmoney - usepoint < usepoint + getmoney) {
			if (tf_allmoney.getText().equals("0")) {
				change = 0;
			} else {
				change = usepoint + getmoney - allmoney;
			}
			tf_change.setText(money_format.format(change));
			tf_balance.setText("0");
		} else {
			balance = allmoney - usepoint - getmoney;
			tf_balance.setText(money_format.format(balance));
			tf_change.setText("0");
		}
	}

	// 메뉴 리스트 자동 생성 메서드
	public void menuListUp(DecimalFormat money_format, List li_menu) {
		try {
			sql = "select * from menu order by mno";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			String mprice_s = "";

			while (rs.next()) {

				mno = rs.getInt("mno");
				String mno_s = "";
				mname = rs.getString("mname");
				mprice_s = money_format.format(rs.getInt("mprice"));

				if (mno < 10) {
					mno_s = "0" + mno;
				} else {
					mno_s = "" + mno;
				}

				String empty = "   ";
				for (int i = 0; i < (14 - mname.length()); i++) {
					empty += "    ";
				}

				li_menu.add(mno_s + "   -   " + mname + empty + mprice_s + "원");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 메뉴 선택 시 정보를 담는 해쉬맵을 세팅하는 메서드
	public void hashMapSet(HashMap<Integer, Integer> hm_select_menu_count,
			HashMap<Integer, Integer> hm_select_menu_price, ArrayList<Integer> arr) throws Exception {

		sql = "select * from menu order by mno";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		arr.clear();
		while (rs.next()) {
			hm_select_menu_count.put(rs.getInt("mno"), 0);
			hm_select_menu_price.put(rs.getInt("mno"), 0);
			arr.add(rs.getInt("mno"));
		}

	}

	// 메뉴 리스트에 추가하는 메서드
	public void selectMenuAdd(List li_menu, List li_selectmenu, HashMap<Integer, Integer> hm_select_menu_count,
			HashMap<Integer, Integer> hm_select_menu_price, DecimalFormat money_format, TextField tf_menu_count,
			TextField tf_allmoney, ArrayList<Integer> arr) {
		try {
			String mno_s = li_menu.getSelectedItem().substring(0, 2);

			if (li_menu.getSelectedItem().substring(0, 1).equals("0")) {
				mno = Integer.parseInt(li_menu.getSelectedItem().substring(1, 2));
			} else {
				mno = Integer.parseInt(li_menu.getSelectedItem().substring(0, 2));
			}

			sql = "select * from menu where mno=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, mno);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				mname = rs.getString("mname");
				mprice = rs.getInt("mprice");
			}

			int mno_count = 0;
			if (tf_menu_count.getText().length() == 0) {
				mno_count = hm_select_menu_count.get(mno) + 1;
			} else {
				mno_count = Integer.parseInt(tf_menu_count.getText());
			}

			int mprice_all = mprice * mno_count;
			String mprice_s = money_format.format(mprice_all);

			hm_select_menu_count.put(mno, mno_count);
			hm_select_menu_price.put(mno, mprice_all);
			boolean hasMenu = false;
			String empty = "";
			String empty_p = "";

			if (mprice_s.length() > 6) {
				empty_p = "           ";
			} else if (mprice_s.length() == 6) {
				empty_p = "             ";
			} else {
				empty_p = "               ";

			}
			for (int i = 0; i < (8 - mname.length()); i++) {
				empty += "    ";
			}

			if (mno_count < 10) {
				empty += "      ";
			} else {
				empty += "    ";
			}

			for (int i = 0; i < li_selectmenu.getItemCount(); i++) {
				if (li_selectmenu.getItem(i).substring(0, 2).equals(mno_s)) {
					hasMenu = true;
					li_selectmenu.replaceItem(
							mno_s + "   -   " + mname + empty + mno_count + "개" + empty_p + mprice_s + "원", i);
					break;
				}
			}

			if (!hasMenu) {

				li_selectmenu.add(mno_s + "   -   " + mname + empty + mno_count + "개" + empty_p + mprice_s + "원");

			}

			allMoneySet(hm_select_menu_price, tf_menu_count, tf_allmoney, arr, money_format);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// 담겨져 있는 메뉴를 선택 후 삭제 버튼 누를 시 삭제 메서드
	public void selectMenuDelete(List li_selectmenu, HashMap<Integer, Integer> hm_select_menu_count,
			HashMap<Integer, Integer> hm_select_menu_price, TextField tf_menu_count, TextField tf_allmoney,
			ArrayList<Integer> arr, DecimalFormat money_format) {
		String mno_s = li_selectmenu.getSelectedItem().substring(0, 2);
		if (mno_s.substring(0, 1).equals("0")) {
			mno = Integer.parseInt(mno_s.substring(1, 2));
		} else {
			mno = Integer.parseInt(mno_s.substring(0, 2));
		}

		hm_select_menu_count.put(mno, 0);
		hm_select_menu_price.put(mno, 0);

		for (int i = 0; i < li_selectmenu.getItemCount(); i++) {
			if (li_selectmenu.getItem(i).substring(0, 2).equals(mno_s)) {
				li_selectmenu.remove(i);
				break;
			}
		}
		allMoneySet(hm_select_menu_price, tf_menu_count, tf_allmoney, arr, money_format);

	}

	// 총 금액 업데이트 메서드
	public void allMoneySet(HashMap<Integer, Integer> hm_select_menu_price, TextField tf_menu_count,
			TextField tf_allmoney, ArrayList<Integer> arr, DecimalFormat money_format) {
		int allmoney = 0;
		for (int i = 0; i < arr.size(); i++) {
			allmoney += hm_select_menu_price.get(arr.get(i));
		}

		tf_allmoney.setText(money_format.format(allmoney));

	}

	// 회원 검색 메서드
	public void searchGuest(List li_guest, CheckboxGroup cbg_guest_search, TextField tf_guest) {
		try {
			li_guest.removeAll();
			PreparedStatement ps;
			if (cbg_guest_search.getSelectedCheckbox().getLabel().equals("회원이름")) {
				sql = "select * from guest where gusname like ? order by gusno";
				ps = conn.prepareStatement(sql);
				ps.setString(1, "%" + tf_guest.getText() + "%");
			} else {
				sql = "select * from guest where gustel like ? order by gusno";
				ps = conn.prepareStatement(sql);
				ps.setString(1, "%" + tf_guest.getText() + "%");
			}

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				String no_empty = "";
				String name_empty = "";
				String point_empty = "";
				String tel_empty = "";
				String rank_empty = "";

				gusno = rs.getInt("gusno");
				gusname = rs.getString("gusname");
				guspoint = rs.getInt("guspoint");
				String guspoint_s = guspoint + "";
				gustel = rs.getString("gustel");
				rname = rs.getString("rname");
				gussale = rs.getInt("gussale");

				if (gusno == 0) {
					continue;
				}

				if (gusno < 10) {
					no_empty = "         ";
				} else if (gusno >= 10 && gusno < 100) {
					no_empty = "       ";
				} else {
					no_empty = "     ";
				}

				for (int i = 0; i < 16 - (gusname.length() * 3); i++) {
					name_empty += " ";
				}

				if (guspoint_s.length() < 2) {
					for (int i = 0; i < 14 - (guspoint_s.length() * 2); i++) {
						point_empty += " ";
					}
				} else {
					for (int i = 0; i < 15 - (guspoint_s.length() * 2); i++) {
						point_empty += " ";
					}
				}

				for (int i = 0; i < 31 - (gustel.length() * 2); i++) {
					tel_empty += " ";
				}

				for (int i = 0; i < 18 - (rname.length() * 2); i++) {
					rank_empty += " ";
				}

				li_guest.add(" " + gusno + no_empty + gusname + name_empty + guspoint + point_empty + gustel + tel_empty
						+ rname + rank_empty + gussale);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public Insets insets() {
		Insets i = new Insets(60, 30, 30, 30);
		return i;
	}

	public static void main(String[] args) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");

		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String pwd = "1234";

		conn = DriverManager.getConnection(url, user, pwd);

		CafePosSystem_pay cpsp = new CafePosSystem_pay();
		cpsp.setSize(800, 600);
		cpsp.setVisible(true);
	}

}
