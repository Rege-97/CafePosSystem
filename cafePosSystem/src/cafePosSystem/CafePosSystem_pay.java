package cafePosSystem;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.JSeparator;

public class CafePosSystem_pay extends Panel {

	Panel p_north, p_center, p_center_1, p_center_2, p_center_2_south, p_center_2_south_north, p_center_2_south_south,
			p_center_3, p_center_3_center, p_center_3_east, p_center_4, p_center_4_north, p_center_4_center,
			p_center_4_center_south, p_center_4_south, p_south;

	Label title, lb_menu_count, lb_allmoney, lb_usepoint, lb_getmoney, lb_balance, lb_change, lb_guest, lb_gusno,
			lb_gusname, lb_gusrank, lb_guest_point;
	TextField tf_menu_count, tf_allmoney, tf_usepoint, tf_getmoney, tf_balance, tf_change, tf_guest_cat, tf_guest,
			tf_gusno, tf_gusname, tf_gusrank, tf_guest_point;
	List li_selectmenu, li_menu, li_guest;
	Button bt_menu_add, bt_menu_del, bt_unrank, bt_search, bt_pay, bt_reset;
	CheckboxGroup cbg_guest_search;
	Checkbox cb_guest_search1;
	Checkbox cb_guest_search2;

	HashMap<Integer, Integer> hm_select_menu_count, hm_select_menu_price;
	ArrayList<Integer> arr;
	Font f_title, f_subtitle, f_menu, f_cat;
	DecimalFormat money_format;
	SimpleDateFormat sdf_now;
	Dialog dialog_pay;
	JSeparator js;

	int gusno;
	String gusname;
	int guspoint;
	String gustel;
	String rname;
	double rpoint;
	int mno;
	String mname;
	int mprice;
	int ocount;
	int gussale;

	Connection conn;
	String sql;

	Frame frame;

	public CafePosSystem_pay(Frame frame, Connection conn) throws Exception {
		this.conn = conn;
		this.frame = frame;
		payLayout();
	}

	public void payLayout() throws Exception {
		this.removeAll();

		hm_select_menu_count = new HashMap<Integer, Integer>();
		hm_select_menu_price = new HashMap<Integer, Integer>();
		arr = new ArrayList<Integer>();
		hashMapSet();

		f_title = new Font("Default Font", Font.BOLD, 15);
		f_subtitle = new Font("Default Font", Font.BOLD, 12);
		f_menu = new Font("Default Font", Font.TRUETYPE_FONT, 15);
		f_cat = new Font("Default Font", Font.TRUETYPE_FONT, 10);

		money_format = new DecimalFormat("#,###");
		sdf_now = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		this.setLayout(new BorderLayout(30, 30));

		// 상단
		p_north = new Panel(new BorderLayout(5, 5));
		this.add(p_north, "North");
		title = new Label("CAFE DMOA POS SYSTEM", Label.CENTER);
		title.setFont(f_title);
		p_north.add(title, "North");

		js = new JSeparator();
		p_north.add(js, "Center");
		js.setForeground(Color.pink);

		// 중단
		p_center = new Panel(new GridLayout(2, 2, 30, 30));
		this.add(p_center, "Center");

		// 중단 -1
		p_center_1 = new Panel(new BorderLayout(10, 10));
		p_center.add(p_center_1);

		li_selectmenu = new List();
		li_selectmenu.setFont(f_menu);
		ScrollPane sp_selectmenu = new ScrollPane();
		sp_selectmenu.add(li_selectmenu);

		p_center_1.add(sp_selectmenu, "Center");

		// 중단 -2
		p_center_2 = new Panel(new BorderLayout(5, 5));
		p_center.add(p_center_2);

		li_menu = new List();
		li_menu.setFont(f_menu);

		menuListUp();

		p_center_2.add(li_menu, "Center");

		p_center_2_south = new Panel(new BorderLayout(5, 5));
		p_center_2_south.setPreferredSize(new Dimension(200, 60));
		p_center_2.add(p_center_2_south, "South");

		p_center_2_south_north = new Panel(new BorderLayout());
		p_center_2_south.add(p_center_2_south_north, "North");

		lb_menu_count = new Label("수량", Label.CENTER);
		lb_menu_count.setFont(f_subtitle);
		lb_menu_count.setPreferredSize(new Dimension(100, 20));
		tf_menu_count = new TextField();
		tf_menu_count.setFont(f_subtitle);

		p_center_2_south_north.add(lb_menu_count, "West");
		p_center_2_south_north.add(tf_menu_count, "Center");

		p_center_2_south_south = new Panel(new GridLayout(1, 2));
		p_center_2_south.add(p_center_2_south_south);

		bt_menu_add = new Button("추가");
		bt_menu_add.setFont(f_subtitle);

		bt_menu_del = new Button("삭제");
		bt_menu_del.setFont(f_subtitle);

		p_center_2_south_south.add(bt_menu_add);
		p_center_2_south_south.add(bt_menu_del);

		// 중단 -3
		p_center_3 = new Panel(new BorderLayout(5, 5));
		p_center.add(p_center_3);

		p_center_3_center = new Panel(new GridLayout(5, 2, 10, 10));
		p_center_3_center.setPreferredSize(new Dimension(330, 500));
		p_center_3.add(p_center_3_center, "West");

		lb_allmoney = new Label("총 금액", Label.LEFT);
		lb_allmoney.setFont(f_title);

		tf_allmoney = new TextField("0");
		tf_allmoney.setFont(f_title);
		tf_allmoney.setEditable(false);

		lb_usepoint = new Label("사용 포인트", Label.LEFT);
		lb_usepoint.setFont(f_title);

		tf_usepoint = new TextField("0");
		tf_usepoint.setEditable(false);
		tf_usepoint.setFont(f_title);

		lb_getmoney = new Label("받은 금액", Label.LEFT);
		lb_getmoney.setFont(f_title);

		tf_getmoney = new TextField();
		tf_getmoney.setFont(f_title);

		lb_balance = new Label("남은 금액", Label.LEFT);
		lb_balance.setFont(f_title);

		tf_balance = new TextField("0");
		tf_balance.setEditable(false);
		tf_balance.setFont(f_title);

		lb_change = new Label("거스름돈", Label.LEFT);
		lb_change.setFont(f_title);

		tf_change = new TextField("0");
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

		p_center_3_east = new Panel(new GridLayout(5, 1, 10, 10));
		p_center_3.add(p_center_3_east, "Center");

		for (int i = 0; i < 5; i++) {
			Label lb_won = new Label("원");
			lb_won.setFont(f_title);
			p_center_3_east.add(lb_won);
		}

		// 중단 -4
		p_center_4 = new Panel(new BorderLayout());
		p_center.add(p_center_4);

		p_center_4_north = new Panel(new BorderLayout());
		p_center_4.add(p_center_4_north, "North");
		lb_guest = new Label("                        회원 검색", Label.CENTER);
		lb_guest.setFont(f_subtitle);
		bt_unrank = new Button("비회원 주문");
		bt_unrank.setFont(f_subtitle);

		p_center_4_north.add(lb_guest, "Center");
		p_center_4_north.add(bt_unrank, "East");

		p_center_4_center = new Panel(new BorderLayout());
		p_center_4.add(p_center_4_center, "Center");

		tf_guest_cat = new TextField(" 번호   회원이름   포인트    전화번호          회원등급   결제금액");
		tf_guest_cat.setEditable(false);
		tf_guest_cat.setFont(f_cat);
		p_center_4_center.add(tf_guest_cat, "North");

		li_guest = new List();
		li_guest.setFont(f_cat);

		p_center_4_center.add(li_guest, "Center");

		p_center_4_center_south = new Panel(new FlowLayout());
		p_center_4_center.add(p_center_4_center_south, "South");

		cbg_guest_search = new CheckboxGroup();
		cb_guest_search1 = new Checkbox("회원이름", cbg_guest_search, true);
		cb_guest_search2 = new Checkbox("전화번호", cbg_guest_search, false);

		tf_guest = new TextField();
		tf_guest.setPreferredSize(new Dimension(120, 20));
		bt_search = new Button("검색");
		bt_search.setFont(f_subtitle);
		bt_search.setPreferredSize(new Dimension(80, 20));
		p_center_4_center_south.add(cb_guest_search1);
		p_center_4_center_south.add(cb_guest_search2);
		p_center_4_center_south.add(tf_guest);
		p_center_4_center_south.add(bt_search);

		p_center_4_south = new Panel(new GridLayout(2, 4, 30, 5));
		p_center_4.add(p_center_4_south, "South");

		lb_gusno = new Label("회원 번호", Label.CENTER);
		tf_gusno = new TextField("0");
		tf_gusno.setPreferredSize(new Dimension(100, 20));
		tf_gusno.setEditable(false);
		lb_gusname = new Label("회원 이름", Label.CENTER);
		tf_gusname = new TextField("비회원");
		tf_gusname.setPreferredSize(new Dimension(100, 20));
		tf_gusname.setEditable(false);
		lb_gusrank = new Label("회원 등급", Label.CENTER);
		tf_gusrank = new TextField("Unrank");

		tf_gusrank.setPreferredSize(new Dimension(100, 20));
		tf_gusrank.setEditable(false);
		lb_guest_point = new Label("잔여 포인트", Label.CENTER);
		tf_guest_point = new TextField("0");
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
		p_south = new Panel(new GridLayout(1, 2));
		p_south.setPreferredSize(new Dimension(740, 40));
		this.add(p_south, "South");
		bt_pay = new Button("결제");
		bt_pay.setFont(f_title);
		bt_reset = new Button("입력 초기화");
		bt_reset.setFont(f_title);
		p_south.add(bt_pay);
		p_south.add(bt_reset);

		this.validate();

		// 이벤트

		tf_menu_count.addKeyListener(new KeyAdapter() {
			// 숫자 이외의 입력 방지
			@Override
			public void keyTyped(KeyEvent e) {
				if (!(e.getKeyChar() >= 48 && e.getKeyChar() <= 57)) {
					e.consume();
				}

			}
			// 엔터키 입력 시 메뉴 선택
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					selectMenuAdd();
				}
			}
		});
		tf_usepoint.addKeyListener(new KeyAdapter() {
			// 숫자 이외의 입력 방지
			@Override
			public void keyTyped(KeyEvent e) {
				if (!(e.getKeyChar() >= 48 && e.getKeyChar() <= 57)) {
					e.consume();
				}

			}
		});

		tf_getmoney.addKeyListener(new KeyAdapter() {
			// 숫자 이외의 입력 방지
			@Override
			public void keyTyped(KeyEvent e) {
				if (!(e.getKeyChar() >= 48 && e.getKeyChar() <= 57)) {
					e.consume();
				}

			}
			// 받은 금액 입력후 엔터키 입력 시 결제
			@Override
			public void keyPressed(KeyEvent e) {
				if (!(tf_getmoney.getText().equals("")) && e.getKeyCode() == KeyEvent.VK_ENTER) {
					pay();
				}

			}
		});

		li_menu.addActionListener(new ActionListener() {
			// 메뉴 선택하여 추가
			@Override
			public void actionPerformed(ActionEvent e) {
				selectMenuAdd();

			}
		});

		bt_menu_add.addActionListener(new ActionListener() {
			// 추가 버튼 누를 시 메뉴 추가
			@Override
			public void actionPerformed(ActionEvent e) {
				if (li_menu.getSelectedIndex() == -1) {
					return;
				}

				selectMenuAdd();

			}
		});

		bt_menu_del.addActionListener(new ActionListener() {
			// 삭제 버튼 누를 시 메뉴 삭제
			@Override
			public void actionPerformed(ActionEvent e) {
				if (li_selectmenu.getSelectedIndex() == -1) {
					return;
				}

				selectMenuDelete();
			}
		});

		tf_allmoney.addTextListener(new TextListener() {
			// 전체 금액 변경 시 자동계산
			@Override
			public void textValueChanged(TextEvent e) {
				payMoney();
			}
		});

		tf_usepoint.addTextListener(new TextListener() {
			// 회원이 가진 포인트보다 많은 포인트 입력 불가
			@Override
			public void textValueChanged(TextEvent e) {

				if (!tf_usepoint.getText().equals("")) {
					if (Integer.parseInt(tf_usepoint.getText()) > Integer.parseInt(tf_guest_point.getText())) {

						tf_usepoint.setText(tf_guest_point.getText());

					}
				} else {
					tf_usepoint.setText("0");
				}

				payMoney();

			}
		});

		tf_guest_point.addTextListener(new TextListener() {
			// 회원 선택 시 포인트 자동 입력
			@Override
			public void textValueChanged(TextEvent e) {

				if (tf_guest_point.getText().equals("0")) {
					tf_usepoint.setEditable(false);
				}

				if (Integer.parseInt(tf_guest_point.getText()) >= 1000) {
					if (Integer.parseInt(tf_allmoney.getText().replaceAll(",", "")) < Integer
							.parseInt(tf_guest_point.getText())) {
						tf_usepoint.setText(tf_allmoney.getText().replaceAll(",", ""));
						tf_usepoint.setEditable(true);
					} else {
						tf_usepoint.setText(tf_guest_point.getText());
						tf_usepoint.setEditable(true);
					}

				}

			}
		});

		tf_getmoney.addTextListener(new TextListener() {
			// 받은 금액 입력 시 자동 계산
			@Override
			public void textValueChanged(TextEvent e) {
				payMoney();

			}
		});
		bt_search.addActionListener(new ActionListener() {
			// 회원 검색
			@Override
			public void actionPerformed(ActionEvent e) {
				searchGuest();
			}
		});
		li_guest.addActionListener(new ActionListener() {
			// 회원 선택
			@Override
			public void actionPerformed(ActionEvent e) {
				guestSelect();
			}
		});

		bt_unrank.addActionListener(new ActionListener() {
			// 비회원 전환
			@Override
			public void actionPerformed(ActionEvent e) {
				tf_gusno.setText("0");
				tf_gusname.setText("비회원");
				tf_gusrank.setText("Unrank");
				tf_guest_point.setText("0");

			}
		});
		bt_reset.addActionListener(new ActionListener() {
			// 입력 초기화
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					payLayout();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		;

		bt_pay.addActionListener(new ActionListener() {
			// 결제
			@Override
			public void actionPerformed(ActionEvent e) {
				pay();
			}

		});

	}

	// 결제 금액 자동 계산 메서드
	public void payMoney() {
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
		balance = allmoney - usepoint - getmoney;
		change = usepoint + getmoney - allmoney;
		
		
		if (balance < 0) {
			balance = 0;
		}
		if (change < 0) {
			change = 0;
		}
		if(allmoney<=usepoint) {
			change = 0;
		}

		tf_balance.setText(money_format.format(balance));
		tf_change.setText(money_format.format(change));
		
	}

	// 메뉴 리스트 자동 생성 메서드
	public void menuListUp() {
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
			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 메뉴 선택 시 정보를 담는 해쉬맵을 세팅하는 메서드
	public void hashMapSet() throws Exception {

		sql = "select * from menu order by mno";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		arr.clear();
		while (rs.next()) {
			hm_select_menu_count.put(rs.getInt("mno"), 0);
			hm_select_menu_price.put(rs.getInt("mno"), 0);
			arr.add(rs.getInt("mno"));
		}
		rs.close();
		ps.close();

	}

	// 메뉴 리스트에 추가하는 메서드
	public void selectMenuAdd() {
		try {
			StringTokenizer st=new StringTokenizer(li_menu.getSelectedItem());
			
			String mno_s = st.nextToken();
			mno=Integer.parseInt(mno_s);

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

			allMoneySet();

			tf_menu_count.setText("");

			rs.close();
			ps.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// 담겨져 있는 메뉴를 선택 후 삭제 버튼 누를 시 삭제 메서드
	public void selectMenuDelete() {
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
		allMoneySet();

	}

	// 총 금액 업데이트 메서드
	public void allMoneySet() {
		int allmoney = 0;
		for (int i = 0; i < arr.size(); i++) {
			allmoney += hm_select_menu_price.get(arr.get(i));
		}

		tf_allmoney.setText(money_format.format(allmoney));

	}

	// 회원 검색 메서드
	public void searchGuest() {
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
			li_guest.setFont(new Font("monospace", Font.PLAIN, 10));

			while (rs.next()) {

				gusno = rs.getInt("gusno");
				gusname = rs.getString("gusname");
				guspoint = rs.getInt("guspoint");
				gustel = rs.getString("gustel");
				rname = rs.getString("rname");
				gussale = rs.getInt("gussale");
				if (gusno == 0) {
					continue;
				}

				li_guest.add(" " + emptySet(gusno + "", 12) + emptySet_Kor(gusname, 16) + emptySet(guspoint + "", 16)
						+ emptySet_Kor(gustel, 44) + emptySet(rname, 18) + gussale);
			}
			rs.close();
			ps.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// 결제 메서드
	public void pay() {
		try {
			int rankup = 0;
			String rankname = "";
			PopUpWindow puw = new PopUpWindow(frame);
			if (tf_allmoney.getText().equals("0")) {

				puw.showPopUp("메뉴 없음", "메뉴가 담겨있지 않습니다.", "메뉴를 담아주세요.");

			} else if (!tf_balance.getText().equals("0") && !tf_getmoney.getText().equals("")) {
				puw.showPopUp("금액 부족", "주신 금액이 부족합니다.", "금액을 더 입력하세요.");

			} else if (Integer.parseInt(tf_allmoney.getText().replaceAll(",", "")) < Integer
					.parseInt(tf_usepoint.getText())) {
				puw.showPopUp("포인트 오류", "포인트를 잘못 입력하였습니다.", "포인트는 총 금액보다 많이 사용할 수 없습니다.");
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
					bank_to_money = rs.getInt("money");
				}
				
				int deposit=(int) ((allmoney * 0.9) - usepoint);
				bank_to_money = bank_to_money + deposit;
				sql = "update bank set money=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, bank_to_money);
				ps.executeUpdate();
				
				
				// 뱅킹 기록
				Calendar now = Calendar.getInstance();
				java.sql.Timestamp jst = new java.sql.Timestamp(now.getTimeInMillis());
				sql="insert into banking values(sq_banking_bno.nextval,?,?,?,?)";
				ps=conn.prepareStatement(sql);
				ps.setString(1, "판매수익");
				ps.setInt(2,deposit );
				ps.setInt(3, bank_to_money);
				ps.setTimestamp(4, jst);
				
				ps.execute();
				

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
					if (gussale >= 50000 && gussale < 100000 && tf_gusrank.getText().equals("Bronze")) {
						sql = "update guest set rname=? where gusno=?";
						ps = conn.prepareStatement(sql);
						ps.setString(1, "Silver");
						ps.setInt(2, gusno);
						rankup = ps.executeUpdate();
						rankname = "Silver";
					} else if (gussale >= 100000 && gussale < 200000 && tf_gusrank.getText().equals("Silver")) {
						sql = "update guest set rname=? where gusno=?";
						ps = conn.prepareStatement(sql);
						ps.setString(1, "Gold");
						ps.setInt(2, gusno);
						rankup = ps.executeUpdate();
						rankname = "Gold";
					} else if (gussale >= 200000 && tf_gusrank.getText().equals("Gold")) {
						sql = "update guest set rname=? where gusno=?";
						ps = conn.prepareStatement(sql);
						ps.setString(1, "Platinum");
						ps.setInt(2, gusno);
						rankup = ps.executeUpdate();
						rankname = "Platinum";
					}

				}
				// 결제 기록 저장

				
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
						ps.setInt(4, menuprice);
						ps.setTimestamp(5, jst);
						ps.setInt(6, ocount);
						ps.execute();
					}

				}
				rs.close();
				ps.close();

				puw.showPopUp( this,rankup,rankname);
			}

		

		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	// 회원 정보 선택 메서드
	public void guestSelect() {
		try {
			
			StringTokenizer st=new StringTokenizer(li_guest.getSelectedItem());
			gusno=Integer.parseInt(st.nextToken());

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

			rs.close();
			ps.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	// 공백 생성(한글)
	public static String emptySet_Kor(String text, int length) {

		StringBuffer empty_s = new StringBuffer();
		StringBuffer result = new StringBuffer();
		length -= text.length() * 3;
		for (int i = 0; i < length; i++) {
			empty_s.append(" ");
		}
		result.append(text);
		result.append(empty_s.toString());

		return result.toString();
	}
	// 공백 생성
	public static String emptySet(String text, int length) {

		StringBuffer empty_s = new StringBuffer();
		StringBuffer result = new StringBuffer();
		length -= text.length() * 2;
		for (int i = 0; i < length; i++) {
			empty_s.append(" ");
		}
		if (text.length() < 4) {
			empty_s.deleteCharAt(0);
		}
		result.append(text);
		result.append(empty_s.toString());

		return result.toString();
	}

	@Override
	public Insets insets() {
		Insets i = new Insets(10, 30, 30, 30);
		return i;
	}

}
