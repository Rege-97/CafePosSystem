package test;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import javax.swing.JSeparator;
import javax.swing.Timer;

public class CafePosSystem_bank extends Panel {

	int eno;
	String ename;
	String gname;
	String etel;
	int gsal;
	int gussale;
	String sql;
	int bno;
	String bname;
	int bmoney;
	int balance;
	int bank_to_money;
	boolean stop;
	int index;
	java.sql.Timestamp bdate;
	Timer timer;

	Font f_title, f_subtitle, f_menu, f_cat;
	Panel p_north, p_center, p_center_north, p_center_north_center, p_center_north_south, p_center_south,
			p_center_south_west, p_center_south_west_center, p_center_south_west_south,
			p_center_south_west_south_center, p_center_south_west_south_south, p_center_south_east,
			p_center_south_east_south, p_center_south_east_south_north, p_center_south_east_south_center;
	Label title, lb_banking, lb_money, lb_salary, lb_eno, lb_ename, lb_gname, lb_gsal, lb_gamemain, lb_allmoney,
			lb_game, lb_gamemoney;
	Button bt_deposit, bt_withdrawal, bt_salary, bt_allsalary, bt_reset, bt_gamestart, bt_gamestop;
	TextArea ta_banking;
	TextField tf_money, tf_eno, tf_ename, tf_gname, tf_gsal, tf_allmoney, tf_gamemoney, tf_bankin_cat, tf_emp_cat;
	List li_salary;
	JSeparator js;
	DecimalFormat money_format;
	SimpleDateFormat sdf_now;
	PopUpWindow puw;

	Connection conn;
	Frame frame;

	public CafePosSystem_bank(Frame frame, Connection conn) {
		this.frame = frame;
		this.conn = conn;

		money_format = new DecimalFormat("#,###");
		sdf_now = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		this.setLayout(new BorderLayout(10, 10));

		f_title = new Font("Default Font", Font.BOLD, 15);
		f_subtitle = new Font("Default Font", Font.BOLD, 12);
		f_menu = new Font("Default Font", Font.TRUETYPE_FONT, 15);
		f_cat = new Font("Default Font", Font.TRUETYPE_FONT, 10);

		puw = new PopUpWindow(frame);

		// 상단
		p_north = new Panel(new BorderLayout(10, 10));
		this.add(p_north, "North");
		title = new Label("인터넷 뱅킹", Label.CENTER);
		title.setFont(f_title);
		p_north.add(title, "North");

		js = new JSeparator();
		js.setForeground(Color.pink);
		p_north.add(js, "Center");

		// 중단
		p_center = new Panel(new GridLayout(2, 1));
		this.add(p_center, "Center");

		p_center_north = new Panel(new BorderLayout());
		p_center.add(p_center_north);

		lb_banking = new Label("은행 입출금 관리", Label.CENTER);
		lb_banking.setFont(f_subtitle);
		p_center_north.add(lb_banking, "North");

		p_center_north_center = new Panel(new BorderLayout());
		p_center_north.add(p_center_north_center, "Center");
		tf_bankin_cat = new TextField("번호\t입출금일\t\t입출금\t\t내역\t\t\t입출금금액\t\t잔액");
		tf_bankin_cat.setEditable(false);
		tf_bankin_cat.setFont(f_subtitle);
		ta_banking = new TextArea("", 10, 10, TextArea.SCROLLBARS_VERTICAL_ONLY);
		ta_banking.setEditable(false);
		p_center_north_center.add(tf_bankin_cat, "North");
		p_center_north_center.add(ta_banking, "Center");

		p_center_north_south = new Panel(new FlowLayout());
		p_center_north.add(p_center_north_south, "South");
		lb_allmoney = new Label("현재 잔액");
		tf_allmoney = new TextField();
		tf_allmoney.setEditable(false);
		tf_allmoney.setPreferredSize(new Dimension(150, 20));
		tf_allmoney.setFont(f_subtitle);
		p_center_north_south.add(lb_allmoney);
		p_center_north_south.add(tf_allmoney);

		lb_money = new Label("입출금 금액");
		tf_money = new TextField();
		tf_money.setPreferredSize(new Dimension(200, 20));
		p_center_north_south.add(lb_money);
		p_center_north_south.add(tf_money);

		bt_deposit = new Button("입금");
		bt_withdrawal = new Button("출금");
		p_center_north_south.add(bt_deposit);
		p_center_north_south.add(bt_withdrawal);

		p_center_south = new Panel(new GridLayout(1, 2, 10, 10));
		p_center.add(p_center_south);

		p_center_south_west = new Panel(new BorderLayout(10, 10));
		p_center_south.add(p_center_south_west);
		lb_salary = new Label("직원 급여 지급", Label.CENTER);
		lb_salary.setFont(f_subtitle);
		p_center_south_west.add(lb_salary, "North");

		p_center_south_west_center = new Panel(new BorderLayout());
		p_center_south_west.add(p_center_south_west_center, "Center");

		tf_emp_cat = new TextField("번호\t이름\t\t직급\t\t급여");
		tf_emp_cat.setEditable(false);
		li_salary = new List();
		p_center_south_west_center.add(tf_emp_cat, "North");
		p_center_south_west_center.add(li_salary, "Center");

		p_center_south_west_south = new Panel(new BorderLayout(10, 10));
		p_center_south_west.add(p_center_south_west_south, "South");

		p_center_south_west_south_center = new Panel(new GridLayout(2, 4));
		p_center_south_west_south.add(p_center_south_west_south_center, "Center");

		lb_eno = new Label("직원번호", Label.CENTER);
		lb_ename = new Label("직원이름", Label.CENTER);
		lb_gname = new Label("직급명", Label.CENTER);
		lb_gsal = new Label("급여", Label.CENTER);

		tf_eno = new TextField();
		tf_eno.setEditable(false);
		tf_ename = new TextField();
		tf_ename.setEditable(false);
		tf_gname = new TextField();
		tf_gname.setEditable(false);
		tf_gsal = new TextField();
		tf_gsal.setEditable(false);

		p_center_south_west_south_center.add(lb_eno);
		p_center_south_west_south_center.add(lb_ename);
		p_center_south_west_south_center.add(lb_gname);
		p_center_south_west_south_center.add(lb_gsal);
		p_center_south_west_south_center.add(tf_eno);
		p_center_south_west_south_center.add(tf_ename);
		p_center_south_west_south_center.add(tf_gname);
		p_center_south_west_south_center.add(tf_gsal);

		p_center_south_west_south_south = new Panel(new GridLayout(1, 2));
		p_center_south_west_south.add(p_center_south_west_south_south, "South");

		bt_salary = new Button("급여 지급");
		bt_allsalary = new Button("전체 지급");
		p_center_south_west_south_south.add(bt_salary);
		p_center_south_west_south_south.add(bt_allsalary);

		p_center_south_east = new Panel(new BorderLayout(10, 10));
		p_center_south.add(p_center_south_east);
		lb_game = new Label("보너스 랜덤 지급", Label.CENTER);
		lb_game.setFont(f_subtitle);
		p_center_south_east.add(lb_game, "North");
		lb_gamemain = new Label("당첨은 누구?", Label.CENTER);
		lb_gamemain.setFont(new Font("Default Font", Font.BOLD, 50));
		lb_gamemain.setBackground(Color.BLACK);
		lb_gamemain.setForeground(Color.white);

		p_center_south_east.add(lb_gamemain, "Center");

		p_center_south_east_south = new Panel(new BorderLayout(10, 10));
		p_center_south_east.add(p_center_south_east_south, "South");

		p_center_south_east_south_north = new Panel(new BorderLayout());
		p_center_south_east_south.add(p_center_south_east_south_north, "North");
		lb_gamemoney = new Label("보너스 금액", Label.CENTER);
		tf_gamemoney = new TextField();
		p_center_south_east_south_north.add(lb_gamemoney, "West");
		p_center_south_east_south_north.add(tf_gamemoney, "Center");

		p_center_south_east_south_center = new Panel(new GridLayout(1, 2));
		p_center_south_east_south.add(p_center_south_east_south_center, "Center");

		bt_gamestart = new Button("게임 시작");
		bt_gamestop = new Button("스탑!!!!");
		bt_gamestop.setEnabled(false);
		p_center_south_east_south_center.add(bt_gamestart);
		p_center_south_east_south_center.add(bt_gamestop);

		// 이벤트

		// 리스트 새로고침
		try {
			setBankList();
			setEmpList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		tf_money.addKeyListener(new KeyAdapter() {
			// 숫자 외 입력 방지
			@Override
			public void keyTyped(KeyEvent e) {
				if (!(e.getKeyChar() >= 48 && e.getKeyChar() <= 57)) {
					e.consume();
				}
			}
		});
		tf_gamemoney.addKeyListener(new KeyAdapter() {
			// 숫자 외 입력 방지
			@Override
			public void keyTyped(KeyEvent e) {
				if (!(e.getKeyChar() >= 48 && e.getKeyChar() <= 57)) {
					e.consume();
				}
			}
		});

		bt_deposit.addActionListener(new ActionListener() {
			// 입금
			@Override
			public void actionPerformed(ActionEvent e) {
				inBank();

			}
		});
		bt_withdrawal.addActionListener(new ActionListener() {
			// 출금
			@Override
			public void actionPerformed(ActionEvent e) {
				outBank();

			}
		});

		li_salary.addActionListener(new ActionListener() {
			// 직원 선택
			@Override
			public void actionPerformed(ActionEvent e) {
				selectEmp();

			}
		});

		bt_salary.addActionListener(new ActionListener() {
			// 직원 급여 지급
			@Override
			public void actionPerformed(ActionEvent e) {
				salary();

			}
		});

		bt_allsalary.addActionListener(new ActionListener() {
			// 전체 직원 급여 지급
			@Override
			public void actionPerformed(ActionEvent e) {
				allSalary();

			}
		});

		bt_gamestart.addActionListener(new ActionListener() {
			// 게임 시작
			@Override
			public void actionPerformed(ActionEvent e) {
				game();
			}
		});
		bt_gamestop.addActionListener(new ActionListener() {
			// 게임 종료
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					gamestop();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	// 입출금 내역 조회
	public void setBankList() throws Exception {
		ta_banking.setText("");

		sql = "select * from banking order by bno";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			bno = rs.getInt("bno");
			bname = rs.getString("bname");
			bmoney = rs.getInt("bmoney");
			balance = rs.getInt("balance");
			bdate = rs.getTimestamp("bdate");
			String io = "";
			if (bmoney >= 0) {
				io = "입금";
			} else {
				io = "출금";
			}
			String empty = "\t\t\t";
			if ((money_format.format(bmoney) + "원").length() >= 8) {
				empty = "\t\t";
			}

			ta_banking.append(bno + "\t" + sdf_now.format(bdate) + "\t\t" + io + "\t\t" + bname + "\t\t"
					+ money_format.format(bmoney) + "원" + empty + money_format.format(balance) + "원\n");
		}

		sql = "select * from bank";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			tf_allmoney.setText(money_format.format(rs.getInt("money")) + " 원");
		}
		tf_money.setText("");
		tf_eno.setText("");
		tf_ename.setText("");
		tf_gname.setText("");
		tf_gsal.setText("");
		

		rs.close();
		ps.close();
	}

	// 직원 목록 조회
	public void setEmpList() throws Exception {
		li_salary.removeAll();
		sql = "select e.eno,e.ename,e.gname,g.gsal\r\n" + "from employee e,grade g\r\n"
				+ "where e.gname=g.gname order by eno";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			eno = rs.getInt("eno");
			ename = rs.getString("ename");
			gname = rs.getString("gname");
			gsal = rs.getInt("gsal");

			li_salary.add(emptySet(eno + "", 16) + emptySet_Kor(ename, 29) + emptySet(gname, 27)
					+ money_format.format(gsal) + "원");
		}

		rs.close();
		ps.close();

	}

	// 직원 선택
	public void selectEmp() {
		try {
			StringTokenizer st = new StringTokenizer(li_salary.getSelectedItem());
			eno = Integer.parseInt(st.nextToken());

			System.out.println(eno);

			sql = "select e.eno,e.ename,e.gname,g.gsal\r\n" + "from employee e,grade g\r\n"
					+ "where e.gname=g.gname and eno=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, eno);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				eno = rs.getInt("eno");
				ename = rs.getString("ename");
				gname = rs.getString("gname");
				gsal = rs.getInt("gsal");
			}
			tf_eno.setText(eno + "");
			tf_ename.setText(ename);
			tf_gname.setText(gname);
			tf_gsal.setText(money_format.format(gsal));

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// 선택 직원 급여 지급
	public void salary() {
		try {
			if (tf_eno.getText().equals("")) {
				puw.showPopUp("직원 선택 오류", "직원이 선택되지 않았습니다.", "직원을 선택해주세요.");
			} else {

				Calendar now = Calendar.getInstance();
				java.sql.Timestamp jst = new java.sql.Timestamp(now.getTimeInMillis());
				bmoney = -Integer.parseInt(tf_gsal.getText().replace(",", ""));
				// 출금
				sql = "select * from bank";
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					bank_to_money = rs.getInt("money");
				}

				if (bank_to_money + bmoney < 0) {
					puw.showPopUp("잔고 부족", "금고에 잔고가 부족합니다.", "현재 잔고 : " + money_format.format(bank_to_money) + "원");
					return;
				}

				bank_to_money = bank_to_money + bmoney;
				sql = "update bank set money=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, bank_to_money);
				ps.executeUpdate();

				sql = "insert into banking values(sq_banking_bno.nextval,?,?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, ename + " 급여");
				ps.setInt(2, bmoney);
				ps.setInt(3, bank_to_money);
				ps.setTimestamp(4, jst);

				ps.execute();
				rs.close();
				ps.close();
				puw.showPopUp("출금 완료", "출금 완료하였습니다.", "현재 잔고 : " + money_format.format(bank_to_money) + "원");
				setBankList();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// 은행 입금
	public void inBank() {
		try {
			if (tf_money.getText().equals("")) {
				puw.showPopUp("금액 오류", "금액이 입력되지 않았습니다.", "금액을 입력해주세요.");
				return;
			}
			Calendar now = Calendar.getInstance();
			java.sql.Timestamp jst = new java.sql.Timestamp(now.getTimeInMillis());
			bmoney = Integer.parseInt(tf_money.getText());
			// 입금
			sql = "select * from bank";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bank_to_money = rs.getInt("money");
			}

			bank_to_money = bank_to_money + bmoney;
			sql = "update bank set money=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, bank_to_money);
			ps.executeUpdate();

			sql = "insert into banking values(sq_banking_bno.nextval,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "은행입금");
			ps.setInt(2, bmoney);
			ps.setInt(3, bank_to_money);
			ps.setTimestamp(4, jst);

			ps.execute();
			rs.close();
			ps.close();
			puw.showPopUp("입금 완료", "입금 완료하였습니다.", "현재 잔고 : " + money_format.format(bank_to_money) + "원");
			setBankList();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// 은행 출금
	public void outBank() {
		try {
			if (tf_money.getText().equals("")) {
				puw.showPopUp("금액 오류", "금액이 입력되지 않았습니다.", "금액을 입력해주세요.");
				return;
			}
			Calendar now = Calendar.getInstance();
			java.sql.Timestamp jst = new java.sql.Timestamp(now.getTimeInMillis());
			bmoney = -Integer.parseInt(tf_money.getText());
			// 출금
			sql = "select * from bank";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bank_to_money = rs.getInt("money");
			}
			if (bank_to_money + bmoney < 0) {
				puw.showPopUp("잔고 부족", "금고에 잔고가 부족합니다.", "현재 잔고 : " + money_format.format(bank_to_money) + "원");
				return;
			}

			bank_to_money = bank_to_money + bmoney;
			sql = "update bank set money=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, bank_to_money);
			ps.executeUpdate();

			sql = "insert into banking values(sq_banking_bno.nextval,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "은행출금");
			ps.setInt(2, bmoney);
			ps.setInt(3, bank_to_money);
			ps.setTimestamp(4, jst);

			ps.execute();
			rs.close();
			ps.close();
			puw.showPopUp("출금 완료", "출금 완료하였습니다.", "현재 잔고 : " + money_format.format(bank_to_money) + "원");
			setBankList();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// 전직원 급여 지급
	public void allSalary() {
		try {
			bmoney = 0;

			sql = "select e.eno,e.ename,e.gname,g.gsal\r\n" + "from employee e,grade g\r\n" + "where e.gname=g.gname";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bmoney += rs.getInt("gsal");
			}
			bmoney = -bmoney;

			sql = "select * from bank";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				bank_to_money = rs.getInt("money");
			}

			if (bank_to_money + bmoney < 0) {
				puw.showPopUp("잔고 부족", "금고에 잔고가 부족합니다.", "현재 잔고 : " + money_format.format(bank_to_money) + "원");
				return;
			}
			bank_to_money = bank_to_money + bmoney;
			puw.allSalary(bank_to_money, bmoney, conn, this);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// 보너스 게임 시작
	public void game() {
		try {
			if (tf_gamemoney.getText().equals("")) {
				puw.showPopUp("금액 오류", "보너스 금액이 입력되지 않았습니다.", "보너스 금액을 입력하세요.");
				return;
			}
			lb_gamemain.setFont(new Font("Default Font", Font.BOLD, 70));

			bt_gamestop.setEnabled(true);
			tf_gamemoney.setEditable(false);

			ArrayList<Integer> eno = new ArrayList<Integer>();
			ArrayList<String> ename = new ArrayList<String>();
			index = 0;
			stop = false;

			sql = "select * from employee order by eno";

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				eno.add(rs.getInt("eno"));
				ename.add(rs.getString("ename"));
			}

			timer = new Timer(100, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					lb_gamemain.setText(eno.get(index) + " " + ename.get(index));
					index += 1;
					if (index == eno.size()) {
						index = 0;
					}
				}
			});
			timer.start();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// 게임 스탑
	public void gamestop() throws Exception {
		if (timer != null) {
			timer.stop();
		}
		stop = true;
		bt_gamestop.setEnabled(false);

		bmoney = -Integer.parseInt(tf_gamemoney.getText());
		StringTokenizer st = new StringTokenizer(lb_gamemain.getText());
		eno = Integer.parseInt(st.nextToken());
		ename = st.nextToken();

		sql = "select * from bank";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			bank_to_money = rs.getInt("money");
		}

		if (bank_to_money + bmoney < 0) {
			puw.showPopUp("잔고 부족", "금고에 잔고가 부족합니다.", "현재 잔고 : " + money_format.format(bank_to_money) + "원");
			return;
		}
		bank_to_money += bmoney;

		puw.gamePopUp(bank_to_money, bmoney, eno, ename, conn, this);

	}

	// 공백세팅(한글)
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

	// 공백세팅
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
