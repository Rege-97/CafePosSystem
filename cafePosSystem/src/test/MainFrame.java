package test;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
	Button bt_pay, bt_guest, bt_menu, bt_emp, bt_sale,bt_bank;
	Font f_title, f_subtitle, f_menu, f_cat;

	Panel p_center_center, p_center_center_center, p_center_center_south, p_center_north, p_center_north_south,
			p_center_center_north, p_center_center_north_center, p_center_center_north_center_west,
			p_center_center_north_center_center, p_center_center_north_east;
	Label lb_title, lb_id, lb_pwd, lb_subtitle, lb_editer1, lb_editer2, lb_editer3, lb_editer4, lb_editer5;
	TextField tf_id, tf_pwd;
	Button bt_login, bt_findid, bt_findpwd;
	Checkbox cb_autoid, cb_checkpwd;
	boolean login;

	static Connection conn;
	String sql;
	String gname;
	PopUpWindow puw;

	public MainFrame() {
		try {
			setSize(800, 600);
			setVisible(true);

			// 화면 중앙 출력
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = getSize();
			int x = (screenSize.width - frameSize.width) / 2;
			int y = (screenSize.height - frameSize.height) / 2;
			setLocation(x, y);

			this.setLayout(new BorderLayout());

			f_title = new Font("Default Font", Font.BOLD, 15);
			f_subtitle = new Font("Default Font", Font.BOLD, 12);
			f_menu = new Font("Default Font", Font.TRUETYPE_FONT, 15);
			f_cat = new Font("Default Font", Font.TRUETYPE_FONT, 10);

			puw = new PopUpWindow(this);

			gname = "";

			login();

			this.validate();

			// 창 닫기
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void login() {
		// 메뉴바 세팅
		mbar = new MenuBar();
		this.setMenuBar(mbar);

		// 파일 메뉴
		m_file = new Menu("파일");

		mi_close = new MenuItem("닫기");

		mbar.add(m_file);
		m_file.add(mi_close);

		///////////// 메인/////////////
		p_center = new Panel(new BorderLayout());
		this.add(p_center);
		///////////// 상단/////////////
		///
		p_center_north = new Panel(new BorderLayout()) {
			public Insets getInsets() {
				return new Insets(130, 0, 20, 0); // 상, 좌, 하, 우 여백 설정
			}
		};

		p_center.add(p_center_north, "North");

		lb_title = new Label("CAFE DMOA", Label.CENTER);
		lb_title.setForeground(Color.pink);
		lb_title.setFont(new Font("Default Font", Font.BOLD, 60));
		p_center_north.add(lb_title, "North");

		lb_subtitle = new Label("Cafe Pos System", Label.CENTER);
		lb_subtitle.setFont(new Font("Default Font", Font.ITALIC, 20));
		p_center_north.add(lb_subtitle, "Center");

		p_center_north_south = new Panel(new FlowLayout());
		p_center_north.add(p_center_north_south, "South");

		lb_editer1 = new Label("김채현", Label.CENTER);
		lb_editer2 = new Label("김호찬", Label.CENTER);
		lb_editer3 = new Label("오진우", Label.CENTER);
		lb_editer4 = new Label("박현진", Label.CENTER);
		lb_editer5 = new Label("박주연", Label.CENTER);
		p_center_north_south.add(lb_editer1);
		p_center_north_south.add(lb_editer2);
		p_center_north_south.add(lb_editer3);
		p_center_north_south.add(lb_editer4);
		p_center_north_south.add(lb_editer5);

		///////////// 중단/////////////
		///
		///

		p_center_center = new Panel(new BorderLayout(10, 10)) {
			public Insets getInsets() {
				return new Insets(10, 240, 10, 240); // 상, 좌, 하, 우 여백 설정
			}
		};

		p_center.add(p_center_center, "Center");

		p_center_center_north = new Panel(new BorderLayout(10, 20));
		p_center_center_north.setPreferredSize(new Dimension(100, 50));
		p_center_center.add(p_center_center_north, "North");
		p_center_center_north_center = new Panel(new BorderLayout(10, 10));
		p_center_center_north.add(p_center_center_north_center, "Center");
		p_center_center_north_center_west = new Panel(new GridLayout(2, 1));
		p_center_center_north_center.add(p_center_center_north_center_west, "West");
		lb_id = new Label("아이디", Label.CENTER);
		lb_pwd = new Label("비밀번호", Label.CENTER);
		p_center_center_north_center_west.add(lb_id);
		p_center_center_north_center_west.add(lb_pwd);

		p_center_center_north_center_center = new Panel(new GridLayout(2, 1));
		p_center_center_north_center.add(p_center_center_north_center_center, "Center");
		tf_id = new TextField(10);
		tf_pwd = new TextField(10);
		tf_pwd.setEchoChar('*');
		p_center_center_north_center_center.add(tf_id);
		p_center_center_north_center_center.add(tf_pwd);

		p_center_center_north_east = new Panel(new BorderLayout());
		p_center_center_north.add(p_center_center_north_east, "East");
		bt_login = new Button("로그인");
		bt_login.setFont(new Font("Default Font", Font.BOLD, 13));
		p_center_center_north_east.add(bt_login);

		p_center_center_center = new Panel(new FlowLayout(FlowLayout.RIGHT));
		p_center_center.add(p_center_center_center, "Center");

		cb_autoid = new Checkbox("ID 저장", false);
		cb_checkpwd = new Checkbox("비밀번호 확인", false);
		bt_findid = new Button("ID 찾기");
		bt_findpwd = new Button("비밀번호 찾기");

		p_center_center_center.add(cb_autoid);
		p_center_center_center.add(cb_checkpwd);
		p_center_center_center.add(bt_findid);
		p_center_center_center.add(bt_findpwd);
		boolean hasauto = false;

		try {
			sql = "select * from employee where eauto = 1";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				tf_id.setText(rs.getString("eid"));
				hasauto = true;
			}

			if (hasauto) {
				cb_autoid.setState(true);
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 이벤트

		tf_id.setFocusable(true);
		this.setFocusable(true);

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				try {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						loginCheck();

					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		cb_checkpwd.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cb_checkpwd.getState()) {
					tf_pwd.setEchoChar((char) 0);
				} else {
					tf_pwd.setEchoChar('*');
				}

			}
		});

		tf_id.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				try {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						loginCheck();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		tf_pwd.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				try {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						loginCheck();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		bt_login.addActionListener(this);
		bt_findid.addActionListener(this);
		bt_findpwd.addActionListener(this);
		mi_close.addActionListener(this);

	}

	public void setMenu() {
		p_north = new Panel(new GridLayout(1, 5));
		this.add(p_north, "North");
		m_file.removeAll();
		mi_logout = new MenuItem("로그아웃");
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
		mi_reset_all = new MenuItem("모든 정보 초기화");

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
		bt_bank = new Button("인터넷 뱅킹");

		bt_pay.setFont(f_subtitle);
		bt_guest.setFont(f_subtitle);
		bt_menu.setFont(f_subtitle);
		bt_emp.setFont(f_subtitle);
		bt_sale.setFont(f_subtitle);
		bt_bank.setFont(f_subtitle);

		p_north.setPreferredSize(new Dimension(800, 30));

		p_north.add(bt_pay);
		p_north.add(bt_guest);
		p_north.add(bt_menu);
		p_north.add(bt_emp);
		p_north.add(bt_sale);
		p_north.add(bt_bank);

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
		bt_bank.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Object ob = e.getSource();

			if (ob == mi_logout) {
				this.remove(p_center);
				this.remove(mbar);
				this.remove(p_north);
				login();
				this.validate();

			} else if (ob == mi_close) {
				System.exit(0);

			} else if (ob == mi_qguest) {
				puw.quickGuestAdd(conn);

			} else if (ob == mi_qpoint) {
				puw.quickPointAdd(conn);
			} else if (ob == mi_qmenu) {
				puw.quickMenuAdd(conn);

			} else if (ob == mi_reset_guest) {
				puw.showResetPopup("회원 정보 초기화", this, conn, "guest");

			} else if (ob == mi_reset_menu) {
				puw.showResetPopup("메뉴 정보 초기화", this, conn, "menu");

			} else if (ob == mi_reset_emp) {
				puw.showResetPopup("직원 정보 초기화", this, conn, "emp");

			} else if (ob == mi_reset_sale) {
				puw.showResetPopup("영업 현황 초기화", this, conn, "sale");

			} else if (ob == mi_reset_all) {
				puw.showResetPopup("메뉴 정보 초기화", this, conn, "all");

			} else if (ob == bt_pay) {
				remove(p_center);
				p_center = new CafePosSystem_pay(this, conn);
				this.add(p_center, "Center");
				this.validate();

			} else if (ob == bt_guest) {
				remove(p_center);
				p_center = new CafePosSystem_guest(this, conn);
				this.add(p_center, "Center");
				this.validate();

			} else if (ob == bt_menu) {
				remove(p_center);
				p_center = new CafePosSystem_menu(this, conn);
				this.add(p_center, "Center");
				this.validate();

			} else if (ob == bt_emp) {
				remove(p_center);
				p_center = new CafePosSystem_emp(this, conn);
				this.add(p_center, "Center");
				this.validate();

			} else if (ob == bt_sale) {
				remove(p_center);
				p_center = new CafePosSystem_sale(this, conn);
				this.add(p_center, "Center");
				this.validate();

			}else if (ob == bt_bank) {
				remove(p_center);
				p_center = new CafePosSystem_bank(this, conn);
				this.add(p_center, "Center");
				this.validate();

			} else if (ob == bt_login) {
				loginCheck();
			} else if (ob == bt_findid) {
				puw.findId(conn);
			} else if (ob == bt_findpwd) {
				puw.findPwd(conn);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	// 로그인 확인
	public void loginCheck() throws Exception {
		sql = "select * from employee where eid=? and epwd=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, tf_id.getText());
		ps.setString(2, tf_pwd.getText());

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			if (tf_id.getText().equals(rs.getString("eid")) && tf_pwd.getText().equals(rs.getString("epwd"))) {
				login = true;
				gname = rs.getString("gname");
			}
		}
		if (login) {
			if (cb_autoid.getState()) {
				sql = "update employee set eauto=0";

				ps = conn.prepareStatement(sql);
				ps.execute();

				sql = "update employee set eauto=1 where eid=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, tf_id.getText());
				ps.execute();
			}else {
				sql = "update employee set eauto=0";

				ps = conn.prepareStatement(sql);
				ps.execute();
			}
			
			rs.close();
			ps.close();

			setMenu();
			remove(p_center);
			p_center = new CafePosSystem_pay(this, conn);
			this.add(p_center, "Center");
			this.validate();
		} else {
			puw.showPopUp("로그인", "ID와 패스워드가 틀렸습니다.", "아이디와 패스워드를 확인하세요.");
		}
	}

	@Override
	public Insets insets() {
		Insets i = new Insets(45, 0, 0, 0);
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
