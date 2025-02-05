package cafePosSystem;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.HashMap;

import javax.swing.JSeparator;

public class CafePosSystem_pay extends Frame {

	Panel p_main;

	int gusno;
	String gusname;
	int guspoint;
	String gustel;
	String rname;
	int rpoint;
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
		hashMapSet(hm_select_menu_count, hm_select_menu_price);

		Font f_title = new Font("Default Font", Font.BOLD, 15);
		Font f_subtitle = new Font("Default Font", Font.BOLD, 12);
		Font f_menu = new Font("Default Font", Font.TRUETYPE_FONT, 15);

		DecimalFormat money_format = new DecimalFormat("#,###");
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

		Label lb_menu = new Label("메뉴 선택", Label.CENTER);
		lb_menu.setFont(f_subtitle);
		p_center_2.add(lb_menu, "North");

		List li_menu = new List();
		li_menu.setFont(f_menu);

		menuListUp(money_format, li_menu);

		ScrollPane sp_menu = new ScrollPane();
		sp_menu.add(li_menu);

		p_center_2.add(li_menu, "Center");

		Panel p_center_2_south = new Panel(new GridLayout(1, 2));
		p_center_2_south.setPreferredSize(new Dimension(200, 40));
		p_center_2.add(p_center_2_south, "South");
		Button bt_menu_add = new Button("추가");

		bt_menu_add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectMenuAdd(li_menu, li_selectmenu, hm_select_menu_count, hm_select_menu_price, money_format);

			}
		});

		Button bt_menu_del = new Button("삭제");

		bt_menu_del.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String mno_s = li_selectmenu.getSelectedItem().substring(0, 2);

				hm_select_menu_count.put(mno, 0);
				hm_select_menu_price.put(mno, 0);

				for (int i = 0; i < li_selectmenu.getItemCount(); i++) {
					if (li_selectmenu.getItem(i).substring(0, 2).equals(mno_s)) {
						li_selectmenu.remove(i);
						break;
					}
				}

			}
		});
		p_center_2_south.add(bt_menu_add);
		p_center_2_south.add(bt_menu_del);

		// 중단 -3
		Panel p_center_3 = new Panel(new GridLayout(5, 2, 10, 10));
		p_center.add(p_center_3);

		Label lb_allmoney = new Label("총 금액", Label.CENTER);
		TextField tf_allmoney = new TextField();
		tf_allmoney.setEditable(false);
		Label lb_usepoint = new Label("사용 포인트", Label.CENTER);
		TextField tf_usepoint = new TextField();
		Label lb_getmoney = new Label("받은 금액", Label.CENTER);
		TextField tf_getmoney = new TextField();
		Label lb_balance = new Label("남은 금액", Label.CENTER);
		TextField tf_balance = new TextField();
		tf_balance.setEditable(false);
		Label lb_change = new Label("거스름돈", Label.CENTER);
		TextField tf_change = new TextField();
		tf_change.setEditable(false);

		p_center_3.add(lb_allmoney);
		p_center_3.add(tf_allmoney);
		p_center_3.add(lb_usepoint);
		p_center_3.add(tf_usepoint);
		p_center_3.add(lb_getmoney);
		p_center_3.add(tf_getmoney);
		p_center_3.add(lb_balance);
		p_center_3.add(tf_balance);
		p_center_3.add(lb_change);
		p_center_3.add(tf_change);

		// 중단 -4
		Panel p_center_4 = new Panel(new BorderLayout(10, 10));
		p_center.add(p_center_4);

		Label lb_guest = new Label("회원 검색", Label.CENTER);
		lb_guest.setFont(f_subtitle);
		p_center_4.add(lb_guest, "North");

		Panel p_center_4_center = new Panel(new BorderLayout(10, 10));
		p_center_4.add(p_center_4_center, "Center");

		List li_guest = new List();
		ScrollPane sp_guest = new ScrollPane();
		sp_guest.add(li_guest);
		p_center_4_center.add(sp_guest, "Center");

		Panel p_center_4_center_south = new Panel(new FlowLayout());
		p_center_4_center.add(p_center_4_center_south, "South");

		CheckboxGroup cbg_guest_search = new CheckboxGroup();
		Checkbox cb_guest_search1 = new Checkbox("회원이름", cbg_guest_search, true);
		Checkbox cb_guest_search2 = new Checkbox("전화번호", cbg_guest_search, false);

		TextField tf_guest = new TextField();
		tf_guest.setPreferredSize(new Dimension(120, 20));
		Button bt_search = new Button("검색");
		bt_search.setPreferredSize(new Dimension(80, 20));
		p_center_4_center_south.add(cb_guest_search1);
		p_center_4_center_south.add(cb_guest_search2);
		p_center_4_center_south.add(tf_guest);
		p_center_4_center_south.add(bt_search);

		Panel p_center_4_south = new Panel(new GridLayout(2, 4, 30, 5));
		p_center_4.add(p_center_4_south, "South");

		Label lb_gusno = new Label("회원 번호", Label.CENTER);
		TextField tf_gusno = new TextField();
		tf_gusno.setPreferredSize(new Dimension(100, 20));
		tf_gusno.setEditable(false);
		Label lb_gusname = new Label("회원 이름", Label.CENTER);
		TextField tf_gusname = new TextField();
		tf_gusname.setPreferredSize(new Dimension(100, 20));
		tf_gusname.setEditable(false);
		Label lb_gusrank = new Label("회원 등급", Label.CENTER);
		TextField tf_gusrank = new TextField();
		tf_gusrank.setPreferredSize(new Dimension(100, 20));
		tf_gusrank.setEditable(false);
		Label lb_guest_point = new Label("잔여 포인트", Label.CENTER);
		TextField tf_guest_point = new TextField();
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
		Button bt_reset = new Button("입력 초기화");
		p_south.add(bt_pay);
		p_south.add(bt_reset);

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

				System.out.println(mno_s + "   -   " + mname + empty + mprice_s + "원");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 메뉴 선택 시 정보를 담는 해쉬맵을 세팅하는 메서드
	public void hashMapSet(HashMap<Integer, Integer> hm_select_menu_count,
			HashMap<Integer, Integer> hm_select_menu_price) throws Exception {

		sql = "select * from menu order by mno";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			hm_select_menu_count.put(rs.getInt("mno"), 0);
			hm_select_menu_price.put(rs.getInt("mno"), 0);
		}
	}

	// 메뉴선택 후 추가 버튼을 눌렀을 시 리스트에 추가하는 메서드
	public void selectMenuAdd(List li_menu, List li_selectmenu, HashMap<Integer, Integer> hm_select_menu_count,
			HashMap<Integer, Integer> hm_select_menu_price, DecimalFormat money_format) {
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

			int mno_count = hm_select_menu_count.get(mno) + 1;
			int mprice_all = mprice * mno_count;
			String mprice_s = money_format.format(mprice_all);

			hm_select_menu_count.put(mno, mno_count);
			hm_select_menu_price.put(mno, mprice_all);
			boolean hasMenu = false;
			String empty = "   ";
			String empty_p = "";

			if (mprice_s.length() > 5) {
				empty_p = "             ";
			} else {
				empty_p = "               ";

			}

			for (int i = 0; i < (9 - mname.length()); i++) {
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
