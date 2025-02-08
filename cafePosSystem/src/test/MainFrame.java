package test;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MainFrame extends Frame implements ActionListener {
	MenuBar mbar;
	Menu m_file, m_quick, m_reset;
	// 파일 메뉴아이템
	MenuItem mi_logout, mi_close;
	// 퀵 메뉴아이템
	MenuItem mi_qmenu, mi_qpoint, mi_qguest;
	// 초기화 메뉴아이템
	MenuItem mi_reset_guest, mi_reset_menu, mi_reset_emp, mi_reset_sale, mi_reset_all;

	Panel p_north, p_center;
	Button bt_pay, bt_guest, bt_menu, bt_emp, bt_sale;
	Font f_title, f_subtitle, f_menu, f_cat;

	static Connection conn;
	String sql;
	PopUpWindow puw;

	public MainFrame() {
		try {
			setSize(800, 600);
			setVisible(true);
			this.setLayout(new BorderLayout());

			f_title = new Font("Default Font", Font.BOLD, 15);
			f_subtitle = new Font("Default Font", Font.BOLD, 12);
			f_menu = new Font("Default Font", Font.TRUETYPE_FONT, 15);
			f_cat = new Font("Default Font", Font.TRUETYPE_FONT, 10);
			puw = new PopUpWindow(this);

			p_center = new Panel();
			this.add(p_center, "Center");
			p_north = new Panel(new GridLayout(1, 5));
			this.add(p_north, "North");

			setMenu();

			// 이벤트

			// 창 닫기
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});

			// 파일 메뉴
			mi_logout.addActionListener(this);
			mi_close.addActionListener(this);

			// 퀵 메뉴
			mi_qguest.addActionListener(this);
			mi_qmenu.addActionListener(this);
			mi_qpoint.addActionListener(this);

			// 초기화 메뉴
			mi_reset_guest.addActionListener(this);
			mi_reset_menu.addActionListener(this);
			mi_reset_emp.addActionListener(this);
			mi_reset_sale.addActionListener(this);
			mi_reset_all.addActionListener(this);

			// 기능 메뉴 이동
			bt_pay.addActionListener(this);
			bt_guest.addActionListener(this);
			bt_menu.addActionListener(this);
			bt_emp.addActionListener(this);
			bt_sale.addActionListener(this);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setMenu() {
		// 메뉴바 세팅
		mbar = new MenuBar();
		this.setMenuBar(mbar);

		// 파일 메뉴
		m_file = new Menu("파일");
		mi_logout = new MenuItem("로그아웃");
		mi_close = new MenuItem("닫기");

		mbar.add(m_file);
		m_file.add(mi_logout);
		m_file.addSeparator();
		m_file.add(mi_close);

		// 퀵 메뉴
		m_quick = new Menu("퀵 메뉴");
		mi_qguest = new MenuItem("회원 추가");
		mi_qmenu = new MenuItem("메뉴 추가");
		mi_qpoint = new MenuItem("포인트 지급");

		mbar.add(m_quick);
		m_quick.add(mi_qguest);
		m_quick.add(mi_qpoint);
		m_quick.add(mi_qmenu);

		// 초기화 메뉴
		m_reset = new Menu("초기화");
		mi_reset_guest = new MenuItem("회원 정보 초기화");
		mi_reset_menu = new MenuItem("메뉴 정보 초기화");
		mi_reset_emp = new MenuItem("직원 정보 초기화");
		mi_reset_sale = new MenuItem("영업 현황 초기화");
		mi_reset_all = new MenuItem("모든 정보");

		mbar.add(m_reset);
		m_reset.add(mi_reset_guest);
		m_reset.add(mi_reset_menu);
		m_reset.add(mi_reset_emp);
		m_reset.add(mi_reset_sale);
		m_reset.addSeparator();
		m_reset.add(mi_reset_all);

		// 기능 메뉴
		bt_pay = new Button("결제");
		bt_guest = new Button("회원 관리");
		bt_menu = new Button("메뉴 관리");
		bt_emp = new Button("직원 관리");
		bt_sale = new Button("영업 현황");

		bt_pay.setFont(f_subtitle);
		bt_guest.setFont(f_subtitle);
		bt_menu.setFont(f_subtitle);
		bt_emp.setFont(f_subtitle);
		bt_sale.setFont(f_subtitle);

		p_north.add(bt_pay);
		p_north.add(bt_guest);
		p_north.add(bt_menu);
		p_north.add(bt_emp);
		p_north.add(bt_sale);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Object ob = e.getSource();

			if (ob == mi_logout) {

			} else if (ob == mi_close) {
				System.exit(0);

			} else if (ob == mi_qguest) {
				puw.quickGuestAdd(conn);
				

			} else if (ob == mi_qpoint) {

			} else if (ob == mi_qmenu) {

			} else if (ob == mi_reset_guest) {
				try {
					puw.showResetPopup("회원 정보 초기화", this, conn, "guest");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else if (ob == mi_reset_menu) {
				try {
					puw.showResetPopup("메뉴 정보 초기화", this, conn, "menu");
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			} else if (ob == mi_reset_emp) {
				try {
					puw.showResetPopup("직원 정보 초기화", this, conn, "emp");
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			} else if (ob == mi_reset_sale) {
				try {
					puw.showResetPopup("영업 현황 초기화", this, conn, "sale");
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			} else if (ob == mi_reset_all) {
				try {
					puw.showResetPopup("메뉴 정보 초기화", this, conn, "all");
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			} else if (ob == bt_pay) {
				remove(p_center);
				p_center = new CafePosSystem_pay(this, conn);
				this.add(p_center, "Center");
				this.validate();

			} else if (ob == bt_guest) {

			} else if (ob == bt_menu) {

			} else if (ob == bt_emp) {
				remove(p_center);
				p_center = new CafePosSystem_emp(this, conn);
				this.add(p_center, "Center");
				this.validate();

			} else if (ob == bt_sale) {

			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	@Override
	public Insets insets() {
		Insets i = new Insets(50, 0, 0, 0);
		return i;
	}

	public static void main(String[] args) throws Exception {

		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String pwd = "1234";

		conn = DriverManager.getConnection(url, user, pwd);
		new MainFrame();

	}

}
