package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

public class CafePosSystem_guest extends Panel {

	
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
	Connection conn;
	String sql;

	Frame frame;
	
	public CafePosSystem_guest(Frame frame, Connection conn) {
		this.conn = conn;
		this.frame = frame;
		Font f_title = new Font("Default Font", Font.BOLD, 15);
		Font f_subtitle = new Font("Default Font", Font.BOLD, 12);

		// 메인
		Panel p_main = new Panel(new BorderLayout(0, 20));
		this.setLayout(new BorderLayout(0,20));
		

		////////////////////////////////////////////////////////////////////
		///////////////////////////// 상단 //////////////////////////////////
		///////////////////////////////////////////////////////////////////

		// 상단 borderLaout(10,10) 간격
		Panel p_north = new Panel(new BorderLayout(10, 10));
		this.add(p_north, "North");

		// main에 p_north부착

		// 상단의 상단
		Panel p_north_north = new Panel(new BorderLayout());
		p_north.add(p_north_north, "North");

		Label lb_title = new Label("회원 관리", Label.CENTER);
		JSeparator js_line = new JSeparator();
		lb_title.setFont(f_title);

		p_north.add(lb_title, "North");
		p_north.add(js_line, "Center");

		// 상단의 하단
		Panel p_north_south = new Panel(new FlowLayout());
		p_north.add(p_north_south, "South");

		Label lb_search = new Label("회원검색", Label.LEFT);
		lb_search.setFont(f_subtitle);

		CheckboxGroup cbg_gusgroup = new CheckboxGroup();
		Checkbox cb_gusname = new Checkbox("이름", cbg_gusgroup, true);
		Checkbox cb_gustel = new Checkbox("전화번호", cbg_gusgroup, true);
		TextField tf_search = new TextField(20);
		Button bt_search = new Button("검색");
		Button bt_view = new Button("전체검색");
		// true면 기본으로 선택되어있는 것

		p_north_south.add(lb_search);
		p_north_south.add(cb_gusname);
		p_north_south.add(cb_gustel);
		p_north_south.add(tf_search);
		p_north_south.add(bt_search);
		p_north_south.add(bt_view);

		////////////////////////////////////////////////////////////////////
		///////////////////////////// 중단 //////////////////////////////////
		///////////////////////////////////////////////////////////////////

		// 중단
		Panel p_center = new Panel(new GridLayout());
		this.add(p_center, "Center");

		// 중단의 왼쪽
		Panel p_center_west = new Panel(new BorderLayout(10, 10));
		p_center.add(p_center_west, "West");

		// 중단의 왼쪽 상단
		Panel p_center_west_north = new Panel(new BorderLayout());
		p_center_west.add(p_center_west_north, "North");

		Label lb_viewlist = new Label("조회 목록", Label.CENTER);
		p_center_west_north.add(lb_viewlist);
		lb_viewlist.setFont(f_subtitle);

		// 중단의 왼쪽 하단
		Panel p_cener_west_south = new Panel(new BorderLayout());
		p_center_west.add(p_cener_west_south, "Center");

		List li_veiwlist = new List();
		p_cener_west_south.add(li_veiwlist, "Center");

		// 중단의 오른쪽
		Panel p_center_East = new Panel(new BorderLayout(10, 10));
		p_center.add(p_center_East, "East");

		// 중단의 오른쪽 상단
		Panel p_center_East_north = new Panel(new BorderLayout());
		p_center_East.add(p_center_East_north, "North");

		Label lb_selectInfo = new Label("선택 회원 정보", Label.CENTER);
		p_center_East_north.add(lb_selectInfo);
		lb_selectInfo.setFont(f_subtitle);

		// 중단의 오른쪽 중단
		Panel p_cener_East_Center = new Panel(new GridLayout(5, 2));
		p_center_East.add(p_cener_East_Center, "Center");

		Label lb_gusNo = new Label("회원 번호", Label.CENTER);
		p_cener_East_Center.add(lb_gusNo);
		TextField tf_gusNo = new TextField();
		p_cener_East_Center.add(tf_gusNo);
		tf_gusNo.setEditable(false);

		Label lb_gusName = new Label("회원 이름", Label.CENTER);
		p_cener_East_Center.add(lb_gusName);
		TextField tf_gusName = new TextField();
		p_cener_East_Center.add(tf_gusName);

		Label lb_gusTel = new Label("회원 전화번호", Label.CENTER);
		p_cener_East_Center.add(lb_gusTel);
		TextField tf_gusTel = new TextField();
		p_cener_East_Center.add(tf_gusTel);

		Label lb_gusRank = new Label("회원 등급", Label.CENTER);
		p_cener_East_Center.add(lb_gusRank);

		Choice c_gusRank = new Choice();
		p_cener_East_Center.add(c_gusRank);
		c_gusRank.add("Bronze");
		c_gusRank.add("Silver");
		c_gusRank.add("Gold");
		c_gusRank.add("Platinum");

		Label lb_gusPoint = new Label("회원 포인트", Label.CENTER);
		p_cener_East_Center.add(lb_gusPoint);
		TextField tf_gusPoint = new TextField();
		p_cener_East_Center.add(tf_gusPoint);

		// 중단의 오른쪽 하단
		Panel p_center_East_South = new Panel(new FlowLayout(1, 10, 10));
		p_center_East.add(p_center_East_South, "South");

		Button bt_Edit = new Button("   수정   ");
		p_center_East_South.add(bt_Edit);

		Button bt_Delete = new Button("   삭제   ");
		p_center_East_South.add(bt_Delete);

		////////////////////////////////////////////////////////////////////
		///////////////////////////// 하단 //////////////////////////////////
		///////////////////////////////////////////////////////////////////

		// 하단
		Panel p_south = new Panel(new BorderLayout(10, 10));
		this.add(p_south, "South");

		// 하단의 상단
		Panel p_south_north = new Panel(new BorderLayout(10, 10));
		p_south.add(p_south_north, "North");

		JSeparator js_line2 = new JSeparator();
		Label lb_add = new Label("회원 등록", Label.CENTER);
		lb_add.setFont(f_subtitle);

		p_south_north.add(js_line2, "Center");
		p_south_north.add(lb_add, "South");

		// 하단의 중단
		Panel p_south_center = new Panel(new GridLayout(2, 5));
		p_south.add(p_south_center, "Center");

		Label lb_add_gusNo = new Label("회원 번호", Label.CENTER);
		p_south_center.add(lb_add_gusNo);

		Label lb_add_gusName = new Label("회원 이름", Label.CENTER);
		p_south_center.add(lb_add_gusName);

		Label lb_add_gusTel = new Label("회원 전화번호", Label.CENTER);
		p_south_center.add(lb_add_gusTel);

		Label lb_add_gusRank = new Label("회원 등급", Label.CENTER);
		p_south_center.add(lb_add_gusRank);

		TextField tf_add_gusNo = new TextField();
		p_south_center.add(tf_add_gusNo);
		tf_add_gusNo.setEditable(false);

		TextField tf_add_gusName = new TextField();
		p_south_center.add(tf_add_gusName);

		TextField tf_add_gusTel = new TextField();
		p_south_center.add(tf_add_gusTel);

		Choice c_add_gusRank = new Choice();
		p_south_center.add(c_add_gusRank);
		c_add_gusRank.add("Bronze");
		c_add_gusRank.add("Silver");
		c_add_gusRank.add("Gold");
		c_add_gusRank.add("Platinum");

		// 하단의 하단
		Panel p_south_south = new Panel(new FlowLayout(1, 10, 10));
		p_south.add(p_south_south, "South");

		Button bt_add = new Button("   등록   ");
		p_south_south.add(bt_add);

		Button bt_reset = new Button("   입력 초기화   ");
		p_south_south.add(bt_reset);
		
		

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////// 이벤트 //////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		
		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////  1번 회원 이름, 전화번호 조회  //////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// 1번 회원 이름, 전화번호 검색
		bt_search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (tf_search.getText().isEmpty()) {
						li_veiwlist.removeAll();
					} else {

						if (cbg_gusgroup.getSelectedCheckbox() == cb_gusname) {
							sql = "select gusno,gusname,gustel,rname,guspoint from guest where gusname like ? order by gusno asc ";
						} else if (cbg_gusgroup.getSelectedCheckbox() == cb_gustel) {
							sql = "select gusno,gusname,gustel,rname,guspoint from guest where gustel like ? order by gusno asc ";
						} else {
							li_veiwlist.removeAll();
						}

						PreparedStatement ps = conn.prepareStatement(sql);

						ps.setString(1, "%" + tf_search.getText() + "%");

						ResultSet rs = ps.executeQuery(); // 셋팅한 결과 가져와
						li_veiwlist.removeAll();
						while (rs.next()) {

							// 체크상태일때 실행 그룹에서의 선택한 체크박스를 반환하는 메소드 반환 후 cb_gustel 변수와 동일하다는 조건문

							gusno = rs.getInt("gusno");
							gusname = rs.getString("gusname");
							gustel = rs.getString("gustel");
							rname = rs.getString("rname");
							guspoint = rs.getInt("guspoint");

							li_veiwlist
									.add(gusno + "   " + gusname + "   " + gustel + "   " + rname + "   " + guspoint);

						}

						rs.close();

						ps.close();

					}
				} catch (Exception ee) {
					ee.printStackTrace();

				}

			}
		});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////  2번 전체 검색 조회  ////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// 2번 전체 검색 조회
		bt_view.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					sql = "select * from guest order by gusno asc ";
					PreparedStatement ps = conn.prepareStatement(sql);

					ResultSet rs = ps.executeQuery(); // 셋팅한 결과 가져와
				
					li_veiwlist.removeAll();
					while (rs.next()) {

						gusno = rs.getInt("gusno");
						gusname = rs.getString("gusname");
						gustel = rs.getString("gustel");
						rname = rs.getString("rname");
						guspoint = rs.getInt("guspoint");

						li_veiwlist.add(gusno + "  " + gusname + "    " + gustel + "   " + rname + "   " + guspoint);
						
					}
					
					rs.close();
					ps.close();
				} catch (SQLException ee) {
					ee.printStackTrace();
				}
			}
		});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////  3번 ListView에서 정보 선택한 것 textView에 불러오기  ////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// 3번 ListView에서 정보 선택한 것 textView에 불러오기
		li_veiwlist.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					if (li_veiwlist.getSelectedItem().substring(1, 2).equals(" ")) {
						gusno = Integer.parseInt(li_veiwlist.getSelectedItem().substring(0, 1));
					} else {
						gusno = Integer.parseInt(li_veiwlist.getSelectedItem().substring(0, 2));
					}

					tf_gusNo.setText(gusno + "");
					sql = "select * from guest where gusno=? order by gusno asc";

					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setInt(1, gusno);

					ResultSet rs = ps.executeQuery(); // 셋팅한 결과 가져와
					while (rs.next()) {
						gusname = rs.getString("gusname");
						gustel = rs.getString("gustel");
						rname = rs.getString("rname");
						guspoint = rs.getInt("guspoint");
					}

					tf_gusName.setText(gusname);
					tf_gusTel.setText(gustel);
					c_gusRank.select(rname);
					tf_gusPoint.setText(guspoint + "");

					rs.close();
					ps.close();

				}

				catch (Exception ee) {
					ee.printStackTrace();

				}
			}
		});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////  4번 선택 회원 정보 수정   //////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// 4번 선택 회원 정보 수정

		bt_Edit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
				
					
					gusno = Integer.parseInt(tf_gusNo.getText());

					sql = "update guest set gusname=? ,gustel=? ,rname=? ,guspoint=? where gusno=?";

					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1, tf_gusName.getText());
					ps.setString(2, tf_gusTel.getText());
					ps.setString(3, c_gusRank.getSelectedItem());
					ps.setInt(4, Integer.parseInt(tf_gusPoint.getText()));
					ps.setInt(5, gusno);

					ps.executeUpdate();

					ps.close();
					}
				 catch (Exception ee) {
					ee.printStackTrace();
				}
			}
		});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////  5번 선택 회원 정보 삭제   //////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// 5번 선택 회원 정보 삭제

		bt_Delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					gusno = Integer.parseInt(tf_gusNo.getText());

					sql = "delete from guest where gusno=?";
					PreparedStatement ps = conn.prepareStatement(sql);

					ps.setInt(1, gusno);
					ps.executeUpdate();

					ps.close();

				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
		});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////  6번 회원 등록  //////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// 6번 회원 등록 - 등록 버튼 클릭시 저장됨
		bt_add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					sql = "insert into guest values(sq_guest_gusno.nextval,?,0,?,?,0)";

					PreparedStatement ps = conn.prepareStatement(sql);

					ps.setString(1, tf_add_gusName.getText()); // 이름
					ps.setString(2, tf_add_gusTel.getText()); // 전화번호
					ps.setString(3, c_add_gusRank.getSelectedItem()); // 등급

					ps.executeUpdate();

					tf_add_gusName.setText(" ");
					tf_add_gusTel.setText(" ");

					int count2 = ps.executeUpdate();
					System.out.println(count2 + "개의 학생정보가 입력되었습니다");

					ps.close();
				}

				catch (Exception ee) {
					ee.printStackTrace();
				}

			}
		});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////  7번 입력한 내용 삭제  ////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// 7번 입력 초기화 버튼 클릭시 입력한 내용 삭제

		bt_reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					tf_gusNo.setText(" ");
					tf_gusName.setText(" ");
					tf_gusTel.setText(" ");
					tf_gusPoint.setText(" ");
					tf_add_gusName.setText(" ");
					tf_add_gusTel.setText(" ");

				} catch (Exception ee) {
					ee.printStackTrace();

				}
			}
		});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////8번 윈도우 창 x 버튼 클릭 시 종료 //////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////  9번 화면 창 비율 맞춤   //////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 9번 창 비율 맞춤
	@Override
	public Insets insets() {
		Insets i = new Insets(10, 30, 30, 30);
		return i;
	}


}



