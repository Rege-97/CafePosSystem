package cafePosSystem;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
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
//import java.security.acl.LastOwnerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JSeparator;

public class CafePosSystem_sale extends Panel  {

	Frame frame;
	String sql;
	Connection conn;
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	PopUpWindow puw;

	// 메뉴총괄표 카테고리 테마 모음 (매뉴총괄표 , 메뉴별매출, 취소내역, 인터넷뱅킹 , 조회
	Button bt_salesstatus, bt_salesbymenu, bt_cancellationdetails, bt_internet_banking, bt_inquiry;
	List li_employee_list;

	/** 기본 생성창 매출총괄표 */
	public CafePosSystem_sale(Frame frame, Connection conn) {
		this.conn = conn;
		this.frame = frame;
		puw = new PopUpWindow(frame);
		salesStatus();

	}

	public void salesStatus() {

		Font f_title = new Font("Default Font", Font.BOLD, 15);
		Font f_subtitle = new Font("Default Font", Font.BOLD, 12);
		Font f_menu = new Font("Default Font", Font.TRUETYPE_FONT, 15);

		// 메인 Panel 생성
		this.setLayout(new BorderLayout());

		// 상단 North 카테고리 메뉴아이템
		// 상단 보더레이아웃
		Panel p_title_north = new Panel();
		p_title_north.setLayout(new BorderLayout());

		// 상단의 상단 그리드레이아웃
		Panel p_title_north_north = new Panel(); // 상단메뉴바의 상단의 상단 패널 생성
		p_title_north.add(p_title_north_north, BorderLayout.NORTH); // 상단메뉴바의 상단의 상단 추가
		p_title_north_north.setLayout(new GridLayout(0, 7, 0, 0)); // 상단메뉴바의 상단에 상단에 그리드 레이아웃 적용

		// 상단의 상단 버튼 생성 및 부착
		bt_salesstatus = new Button("매출총괄표");
		p_title_north_north.add(bt_salesstatus);
		bt_salesbymenu = new Button("메뉴별매출");
		p_title_north_north.add(bt_salesbymenu);
		bt_cancellationdetails = new Button("취소내역");
		p_title_north_north.add(bt_cancellationdetails);
		bt_internet_banking = new Button("인터넷뱅킹");
		p_title_north_north.add(bt_internet_banking);
		Label lb_mtt1 = new Label("");
		p_title_north_north.add(lb_mtt1);
		Choice c_choice2 = new Choice();
		p_title_north_north.add(c_choice2);
		bt_inquiry = new Button("조회");
		p_title_north_north.add(bt_inquiry);

		// 상단의 상단 하단의 라벨 네임
		Panel p_title_north_south = new Panel();
		p_title_north.add(p_title_north_south, BorderLayout.SOUTH);
		p_title_north_south.setLayout(new BorderLayout());

		// 상단의 상단 하단의라벨 부착
		Label lb_SalesStatus_title = new Label("매출총괄표", Label.CENTER);
		p_title_north_south.add(lb_SalesStatus_title);
		lb_SalesStatus_title.setFont(f_title);
		/** 상단 부분 완성 */

		// 가상메인에 보더상단 부분 패녈 장착
		this.add(p_title_north, BorderLayout.NORTH);

		// ======================================================//

		/** 센터 보더레이아웃 */
		Panel p_table_center = new Panel(); // 센터 표생성을위한 Panel 생성
		p_table_center.setLayout(new BorderLayout(50, 50)); // 센터에 보더레이아웃 부착

		// 센터 상단의 보더레이아웃
		Panel p_table_center_north = new Panel();
		p_table_center.add(p_table_center_north, BorderLayout.NORTH);
		p_table_center_north.setLayout(new BorderLayout());

		// 센터 상단의 상단 JSeparator
		JSeparator separator = new JSeparator();
		p_table_center_north.add(separator, BorderLayout.NORTH);
		separator.setForeground(Color.pink);

		// 센터 상단의 중단의 중단 보더 레이아웃
		Panel p_table_center_north_center_center = new Panel();
		p_table_center_north.add(p_table_center_north_center_center, BorderLayout.CENTER);
		p_table_center_north_center_center.setLayout(new BorderLayout());
		// 센터 상단의 중단의 중단의 상단 라벨 (구분 총매출액 할인금액 매출금액 부가세 순매출 결제건 결제단가
		Panel p_table_center_north_center_center_north = new Panel();
		p_table_center_north_center_center.add(p_table_center_north_center_center_north, BorderLayout.NORTH);
		p_table_center_north_center_center_north.setLayout(new GridLayout(0, 8, 0, 0));

		Label lb_sortation = new Label("             구분");
		p_table_center_north_center_center_north.add(lb_sortation);
		Label lb_totalsales = new Label(" |      총매출액");
		p_table_center_north_center_center_north.add(lb_totalsales);
		Label lb_discountamount = new Label("|      할인금액");
		p_table_center_north_center_center_north.add(lb_discountamount);
		Label lb_salesamount = new Label("|      매출금액");
		p_table_center_north_center_center_north.add(lb_salesamount);
		Label lb_surtax = new Label("|         부가세");
		p_table_center_north_center_center_north.add(lb_surtax);
		Label lb_netsales = new Label("|        순매출");
		p_table_center_north_center_center_north.add(lb_netsales);
		Label lb_paymentcase = new Label("|         결제건");
		p_table_center_north_center_center_north.add(lb_paymentcase);
		Label lb_paymentunitprice = new Label("|      결제단가");
		p_table_center_north_center_center_north.add(lb_paymentunitprice);

		// 센터 상단의 중단의 중단의 상단의 중앙 보더레이아웃
		Panel p_table_center_north_center_center_north_center = new Panel();
		p_table_center_north_center_center.add(p_table_center_north_center_center_north_center, BorderLayout.CENTER);
		p_table_center_north_center_center_north_center.setLayout(new BorderLayout());

		// 센터 상단의 중단의 중단의 상단의 중앙의 좌측 (Label 전월 전주 전일 금일 그리드 4행0열
		Panel p_table_center_north_center_center_north_center_west = new Panel();
		p_table_center_north_center_center_north_center.add(p_table_center_north_center_center_north_center_west,
				BorderLayout.WEST);
		p_table_center_north_center_center_north_center_west.setLayout(new GridLayout(4, 0));
		// Label 생성 및 부착

		// 날짜 패턴 설정
		String pattern1 = "yyyy-MM-dd";
		SimpleDateFormat sdf1 = new SimpleDateFormat(pattern1);
		// 현재 날짜 가져오기
		Date now = new Date();
		String nowString = sdf1.format(now);

		// 전일
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DATE, -1);
		String yesterdayString = sdf1.format(cal.getTime());
		
		// 전주
		cal.setTime(now);
		cal.add(Calendar.DATE, -7);
		String lastWeekString = sdf1.format(cal.getTime());

		// 전월
		cal.setTime(now);
		cal.add(Calendar.MONTH, -1);
		String lastMonthString = sdf1.format(cal.getTime());

		Label lb_thepreviousmonth = new Label("전월" + lastMonthString);
		Label lb_lastweek = new Label("전주" + lastWeekString);
		Label lb_yesterday = new Label("전일" + yesterdayString);
		Label lb_today = new Label("금일" + nowString);

		lb_today.setFont(f_subtitle);
		p_table_center_north_center_center_north_center_west.add(lb_thepreviousmonth);
		p_table_center_north_center_center_north_center_west.add(lb_lastweek);
		p_table_center_north_center_center_north_center_west.add(lb_yesterday);
		p_table_center_north_center_center_north_center_west.add(lb_today);

		// 센터 상단의 중단의 중단의 상단의 중앙의 중앙 (Textfield 전월 전주 전일 금일 그리드 4행 0열
		Panel p_table_center_north_center_center_north_center_center = new Panel();
		p_table_center_north_center_center_north_center.add(p_table_center_north_center_center_north_center_center,
				BorderLayout.CENTER);
		p_table_center_north_center_center_north_center_center.setLayout(new GridLayout(4, 0, 3, 3));
		p_table_center_north_center_center_north_center_center.setPreferredSize(new Dimension());

		TextArea ta_thepreviousmonth = new TextArea(" ", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea ta_lastweek = new TextArea(" ", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea ta_yesterday = new TextArea(" ", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea ta_today = new TextArea(" ", 1, 1, TextArea.SCROLLBARS_NONE);
		ta_thepreviousmonth.setEditable(false);
		ta_lastweek.setEditable(false);
		ta_yesterday.setEditable(false);
		ta_today.setEditable(false);
		p_table_center_north_center_center_north_center_center.add(ta_thepreviousmonth);
		p_table_center_north_center_center_north_center_center.add(ta_lastweek);
		p_table_center_north_center_center_north_center_center.add(ta_yesterday);
		p_table_center_north_center_center_north_center_center.add(ta_today);

		// 센터 상단의 중단의 중단의 상단의 중앙 보더레이아웃의 하단 ((띄우기 빈 라벨
		Panel p_table_center_north_center_center_north_center_north = new Panel();
		p_table_center_north_center_center_north_center.add(p_table_center_north_center_center_north_center_north,
				BorderLayout.SOUTH);
		p_table_center_north_center_center_north_center_north.setLayout(new GridLayout(0, 1));

		Label lb_empty1 = new Label(" ");
		p_table_center_north_center_center_north_center_north.add(lb_empty1);
		// 센터 상단의 하단 보더레이아웃
		Panel p_table_center_south = new Panel();
		p_table_center_south.setLayout(new BorderLayout());

		// 센터 상단의 하단의 상단 보더레이아웃
		Panel p_table_center_south_north = new Panel();
		p_table_center_north.add(p_table_center_south_north, BorderLayout.SOUTH);
		p_table_center_south_north.setLayout(new BorderLayout(10, 10));

		// 센터 상단의 하단의 상단의 좌측 그리드레이아웃 1행 2열 ( Label 금일영업현황
		Panel p_table_center_south_north_west = new Panel();
		p_table_center_south_north.add(p_table_center_south_north_west, BorderLayout.WEST);
		p_table_center_south_north_west.setLayout(new GridLayout(1, 2));

		Label lb_todaysbusinessstatus = new Label("                      조회당일현황");
		p_table_center_south_north_west.add(lb_todaysbusinessstatus);
		lb_todaysbusinessstatus.setFont(f_subtitle);

		// 센터 상단의 하단의 상단의 센터 보더레이아웃 센터 ( label 주간매출현황
		Panel p_table_center_south_north_center = new Panel();
		p_table_center_south_north.add(p_table_center_south_north_center, BorderLayout.CENTER);
		p_table_center_south_north_center.setLayout(new BorderLayout());

		Label lb_weeklysalesstatus = new Label("주간매출현황   ", Label.CENTER);
		p_table_center_south_north_center.add(lb_weeklysalesstatus);
		lb_weeklysalesstatus.setFont(f_subtitle);

		// 센터 상단의 하단의 상단의 하단 보더레이아웃
		Panel p_table_center_south_north_south = new Panel();
		p_table_center_south_north.add(p_table_center_south_north_south, BorderLayout.SOUTH);
		p_table_center_south_north_south.setLayout(new BorderLayout());

		// 센터 상단의 하단의 상단의 하단의 좌측 그리드레이아웃 2행 0열 라벨 ( 총금액 결제건
		Panel p_table_center_south_north_south_west = new Panel();
		p_table_center_south_north_south.add(p_table_center_south_north_south_west, BorderLayout.WEST);
		p_table_center_south_north_south_west.setLayout(new GridLayout(4, 0));

		Label lb_total = new Label("총 금액");
		Label lb_payment = new Label("결제건수");
		Label lb_mmmt1 = new Label();
		Label lb_mmmt2 = new Label();

		p_table_center_south_north_south_west.add(lb_total);
		p_table_center_south_north_south_west.add(lb_payment);
		p_table_center_south_north_south_west.add(lb_mmmt1);
		p_table_center_south_north_south_west.add(lb_mmmt2);
		// 센터 상단의 하단의 상단의 하단의 좌측의 센터 보더레이아웃
		Panel p_table_center_south_north_south_west_center = new Panel();
		p_table_center_south_north_south.add(p_table_center_south_north_south_west_center, BorderLayout.CENTER);
		p_table_center_south_north_south_west_center.setLayout(new BorderLayout());

		// 센터 상단의 하단의 상단의 하단의 좌측의 센터의 좌측 그리드레이아웃 2행 0열 ( Textfield 총금액 고객
		Panel p_table_center_south_north_south_west_center_west = new Panel();
		p_table_center_south_north_south_west_center.add(p_table_center_south_north_south_west_center_west,
				BorderLayout.WEST);
		p_table_center_south_north_south_west_center_west.setLayout(new GridLayout(4, 0, 3, 3));
		p_table_center_south_north_south_west_center_west.setPreferredSize(new Dimension(100, 10));

		Label mmmt3 = new Label();
		Label mmmt4 = new Label();
		
		TextArea tf_total = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea tf_payment = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		p_table_center_south_north_south_west_center_west.add(tf_total);
		p_table_center_south_north_south_west_center_west.add(tf_payment);
		tf_total.setEditable(false);
		tf_payment.setEditable(false);

		p_table_center_south_north_south_west_center_west.add(mmmt3);
		p_table_center_south_north_south_west_center_west.add(mmmt4);

		// 센터 상단의 하단의 상단의 하단의 좌측의 센터의 좌측의 센터의 보더레이아웃
		Panel p_table_center_south_north_south_west_center_west_center = new Panel();
		p_table_center_south_north_south_west_center.add(p_table_center_south_north_south_west_center_west_center,
				BorderLayout.CENTER);
		p_table_center_south_north_south_west_center_west_center.setLayout(new BorderLayout());

		// 센터 상단의 하단의 상단의 하단의 화측의 센터의 좌측의 센터의 상단 그리드레이아웃 0행 7열 (Label 주간 날짜
		Panel p_table_center_south_north_south_west_center_west_center_north = new Panel();
		p_table_center_south_north_south_west_center_west_center
				.add(p_table_center_south_north_south_west_center_west_center_north, BorderLayout.NORTH);
		p_table_center_south_north_south_west_center_west_center_north.setLayout(new GridLayout(0, 7, 0, 0));
		// 현제 날자를 기준으로 모든 주의 날짜 구하기
		Calendar cld = Calendar.getInstance(Locale.KOREA);
		// 이번주의 월요일 구하기
		cld.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// 날짜형식 지정
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd (E)", Locale.KOREA);

		String monday = sdf2.format(cld.getTime());
		cld.add(Calendar.DATE, 1);
		String tuesday = sdf2.format(cld.getTime());
		cld.add(Calendar.DATE, 1);
		String wednesday = sdf2.format(cld.getTime());
		cld.add(Calendar.DATE, 1);
		String thursday = sdf2.format(cld.getTime());
		cld.add(Calendar.DATE, 1);
		String friday = sdf2.format(cld.getTime());
		cld.add(Calendar.DATE, 1);
		String saturday = sdf2.format(cld.getTime());
		cld.add(Calendar.DATE, 1);
		String sunday = sdf2.format(cld.getTime());

		Label lb_sunday = new Label(sunday, Label.CENTER);
		Label lb_monday = new Label(monday, Label.CENTER);
		Label lb_tuesday = new Label(tuesday, Label.CENTER);
		Label lb_wednesday = new Label(wednesday, Label.CENTER);
		Label lb_thursday = new Label(thursday, Label.CENTER);
		Label lb_friday = new Label(friday, Label.CENTER);
		Label lb_saturday = new Label(saturday, Label.CENTER);

		p_table_center_south_north_south_west_center_west_center_north.add(lb_sunday);
		p_table_center_south_north_south_west_center_west_center_north.add(lb_monday);
		p_table_center_south_north_south_west_center_west_center_north.add(lb_tuesday);
		p_table_center_south_north_south_west_center_west_center_north.add(lb_wednesday);
		p_table_center_south_north_south_west_center_west_center_north.add(lb_thursday);
		p_table_center_south_north_south_west_center_west_center_north.add(lb_friday);
		p_table_center_south_north_south_west_center_west_center_north.add(lb_saturday);

		// 센터 상단의 하단의 상단의 하단의 좌측의 센터의 좌측의 센터의 상단의 센터 보더레이아웃
		Panel p_table_center_south_north_south_west_center_west_center_north_center = new Panel();
		p_table_center_south_north_south_west_center_west_center
				.add(p_table_center_south_north_south_west_center_west_center_north_center, BorderLayout.CENTER);
		p_table_center_south_north_south_west_center_west_center_north_center.setLayout(new BorderLayout());

		// 센터 상단의 하단의 상단의 하단의 좌측의 센터의 좌측의 센터의 상단의 센터의 상단 그리드레이아웃 0행 7열 (Textfield 주간날짜
		Panel p_table_center_south_north_south_west_center_west_center_north_center_north = new Panel();
		p_table_center_south_north_south_west_center_west_center_north_center
				.add(p_table_center_south_north_south_west_center_west_center_north_center_north, BorderLayout.NORTH);
		p_table_center_south_north_south_west_center_west_center_north_center_north.setLayout(new GridLayout(2, 7));
		p_table_center_south_north_south_west_center_west_center_north_center_north
				.setPreferredSize(new Dimension(10, 40));

		Label lb_mmt1 = new Label();
		Label lb_mmt2 = new Label();
		Label lb_mmt3 = new Label();
		Label lb_mmt4 = new Label();
		Label lb_mmt5 = new Label();
		Label lb_mmt6 = new Label();
		Label lb_mmt7 = new Label();

		TextArea tf_sunday = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea tf_monday = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea tf_tuesday = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea tf_wednesday = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea tf_thursday = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea tf_friday = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea tf_saturday = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		tf_sunday.setEditable(false);
		tf_monday.setEditable(false);
		tf_tuesday.setEditable(false);
		tf_wednesday.setEditable(false);
		tf_thursday.setEditable(false);
		tf_friday.setEditable(false);
		tf_saturday.setEditable(false);
		p_table_center_south_north_south_west_center_west_center_north_center_north.add(tf_sunday);
		p_table_center_south_north_south_west_center_west_center_north_center_north.add(tf_monday);
		p_table_center_south_north_south_west_center_west_center_north_center_north.add(tf_tuesday);
		p_table_center_south_north_south_west_center_west_center_north_center_north.add(tf_wednesday);
		p_table_center_south_north_south_west_center_west_center_north_center_north.add(tf_thursday);
		p_table_center_south_north_south_west_center_west_center_north_center_north.add(tf_friday);
		p_table_center_south_north_south_west_center_west_center_north_center_north.add(tf_saturday);

		p_table_center_south_north_south_west_center_west_center_north_center_north.add(lb_mmt1);
		p_table_center_south_north_south_west_center_west_center_north_center_north.add(lb_mmt2);
		p_table_center_south_north_south_west_center_west_center_north_center_north.add(lb_mmt3);
		p_table_center_south_north_south_west_center_west_center_north_center_north.add(lb_mmt4);
		p_table_center_south_north_south_west_center_west_center_north_center_north.add(lb_mmt5);
		p_table_center_south_north_south_west_center_west_center_north_center_north.add(lb_mmt6);
		p_table_center_south_north_south_west_center_west_center_north_center_north.add(lb_mmt7);
		// 메인 에 센터 Panel 부착
		this.add(p_table_center, BorderLayout.CENTER);

		/** 중단 끝 */

		// ==================================================================================

		// 하단
		Panel p_table_south = new Panel();
		p_table_south.setLayout(new BorderLayout(10, 10));
		p_table_south.setPreferredSize(new Dimension(700, 230));

		// 하단의 상단 라벨 ( Label 최근결제내역 금액대별 메뉴별매출
		Panel p_table_south_north = new Panel();
		p_table_south.add(p_table_south_north, BorderLayout.NORTH);
		p_table_south_north.setLayout(new GridLayout(0, 3));

		Label lb_recentpaymentdetails = new Label("                                             최근결제내역");
		Label lb_byamount = new Label("                                                     금액대별매출");
		Label lb_salesbymenu = new Label("                                 메뉴별매출");

		lb_recentpaymentdetails.setFont(f_subtitle);
		lb_byamount.setFont(f_subtitle);
		lb_salesbymenu.setFont(f_subtitle);

		p_table_south_north.add(lb_recentpaymentdetails);
		p_table_south_north.add(lb_byamount);
		p_table_south_north.add(lb_salesbymenu);

		// 하단의 중단의 0행2열 그리드 레이아웃
		Panel p_table_south_center = new Panel();
		p_table_south.add(p_table_south_center, BorderLayout.CENTER);
		p_table_south_center.setLayout(new GridLayout(0, 2, 10, 10));

		// 하단의 중단의 왼쪽 1열 보더 레이아웃
		Panel p_table_south_center_west = new Panel();
		p_table_south_center.add(p_table_south_center_west);
		p_table_south_center_west.setLayout(new BorderLayout());

		// 하단의 중단의 왼쪽 1열 보더레이아웃 상단 라벨 ( 회원명 주문내역 결제금액 결제시간
		Panel p_table_south_center_west_north = new Panel();
		p_table_south_center_west.add(p_table_south_center_west_north, BorderLayout.NORTH);
		p_table_south_center_west_north.setLayout(new GridLayout(0, 1));

		Label lb_guestnumber = new Label("  번호          회원명          주문내역          결제금액          사용포인트");
		
		p_table_south_center_west_north.add(lb_guestnumber);

		// 하단의 중단의 왼쪽의 1열 보더레이아웃의 센터 List
		Panel p_table_south_center_west_center = new Panel();
		p_table_south_center_west.add(p_table_south_center_west_center, BorderLayout.CENTER);
		p_table_south_center_west_center.setLayout(new BorderLayout());

		li_employee_list = new List(20, false);
		p_table_south_center_west_center.add(li_employee_list);

		// =======================================================================================//

		// 하단의 중단의 오른쪽 2열의 그리드 레이아웃 5행 5열
		Panel p_table_south_center_east = new Panel();
		p_table_south_center.add(p_table_south_center_east);
		p_table_south_center_east.setLayout(new GridLayout(8, 4, 3, 5));

		Label lb_mt7 = new Label();
		Label lb_mt8 = new Label();
		Label lb_mt12 = new Label();

		Label lb_amountrange = new Label("금액대");
		Label lb_notmorethan5000won = new Label("5천원이하");
		Label lb_notmorethan10000won = new Label("1만원 이하");
		Label lb_notmorethan30000won = new Label("3만원 이하");
		Label lb_notmorethan50000won = new Label("5만원 이하");
		Label lb_notmorethan00000won = new Label("5만원 초과");
		

		Label lb_numberofpayments = new Label("결제건수");
		TextArea ta_notmorethan5000won = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea ta_notmorethan10000won = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea ta_notmorethan30000won = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea ta_notmorethan50000won = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea ta_notmorethan00000won = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		ta_notmorethan5000won.setEditable(false);
		ta_notmorethan10000won.setEditable(false);
		ta_notmorethan30000won.setEditable(false);
		ta_notmorethan50000won.setEditable(false);
		ta_notmorethan00000won.setEditable(false);

		Label lb_menuname = new Label("메뉴명");
		Label lb_menu1 = new Label("아메리카노");
		Label lb_menu2 = new Label("카페라떼");
		Label lb_menu3 = new Label("카푸치노");
		Label lb_menu4 = new Label("바닐라라떼");
		Label lb_menu5 = new Label("카페모카");
		Label lb_menu6 = new Label("카라멜마끼아토");

		Button lb_cancellation_of_payment = new Button("결제취소");

		Label lb_salesamount2 = new Label("매출금액");
		TextArea ta_lb_menu1 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea ta_lb_menu2 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea ta_lb_menu3 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea ta_lb_menu4 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea ta_lb_menu5 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea ta_lb_menu6 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		ta_lb_menu1.setEditable(false);
		ta_lb_menu2.setEditable(false);
		ta_lb_menu3.setEditable(false);
		ta_lb_menu4.setEditable(false);
		ta_lb_menu5.setEditable(false);
		ta_lb_menu6.setEditable(false);

		Label lb_treasurybalance = new Label("    금고잔액");
		lb_treasurybalance.setFont(f_subtitle);

		TextArea ta_treasurybalance = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE); // 금고잔액 표시
		ta_treasurybalance.setEditable(false);
		p_table_south_center_east.add(lb_amountrange);
		p_table_south_center_east.add(lb_numberofpayments);
		p_table_south_center_east.add(lb_menuname);
		p_table_south_center_east.add(lb_salesamount2);

		p_table_south_center_east.add(lb_notmorethan5000won);
		p_table_south_center_east.add(ta_notmorethan5000won);
		p_table_south_center_east.add(lb_menu1);
		p_table_south_center_east.add(ta_lb_menu1);

		p_table_south_center_east.add(lb_notmorethan10000won);
		p_table_south_center_east.add(ta_notmorethan10000won);
		p_table_south_center_east.add(lb_menu2);
		p_table_south_center_east.add(ta_lb_menu2);

		p_table_south_center_east.add(lb_notmorethan30000won);
		p_table_south_center_east.add(ta_notmorethan30000won);
		p_table_south_center_east.add(lb_menu3);
		p_table_south_center_east.add(ta_lb_menu3);

		p_table_south_center_east.add(lb_notmorethan50000won);
		p_table_south_center_east.add(ta_notmorethan50000won);
		p_table_south_center_east.add(lb_menu4);
		p_table_south_center_east.add(ta_lb_menu4);

		p_table_south_center_east.add(lb_notmorethan00000won);
		p_table_south_center_east.add(ta_notmorethan00000won);
		p_table_south_center_east.add(lb_menu5);
		p_table_south_center_east.add(ta_lb_menu5);

		p_table_south_center_east.add(lb_mt7);
		p_table_south_center_east.add(lb_mt8);
		p_table_south_center_east.add(lb_menu6);
		p_table_south_center_east.add(ta_lb_menu6);

		p_table_south_center_east.add(lb_cancellation_of_payment);
		p_table_south_center_east.add(lb_mt12);
		p_table_south_center_east.add(lb_treasurybalance);
		p_table_south_center_east.add(ta_treasurybalance);
		// =======================================================================================//

		// 메인에 하단 Panel 부착
		this.add(p_table_south, BorderLayout.SOUTH);
		
		menuTransitionAction();
//		============================================================================
		
		// 메뉴총괄표 조회 날짜 칸 
		try{
			sql = "select distinct\r\n"
					+ "    to_char(odate,'YYYY-MM-DD')as day\r\n"
					+ "from sales\r\n"
					+ "order by day desc";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
					String ymd = rs.getString("day");
					c_choice2.add(ymd);
			}
		//매출총괄표 출력 
			sql = "SELECT    \r\n"
					+ "    CASE WHEN money IS NULL THEN 0 ELSE money END AS money, \r\n"
					+ "    CASE WHEN dc IS NULL THEN 0 ELSE dc END AS dc,  \r\n"
					+ "    CASE WHEN ofs IS NULL THEN 0 ELSE ofs END AS ofs,\r\n"
					+ "    CASE WHEN st IS NULL THEN 0 ELSE st END AS st,\r\n"
					+ "    CASE WHEN toa IS NULL THEN 0 ELSE toa END AS toa,\r\n"
					+ "    CASE WHEN cnt IS NULL THEN 0 ELSE cnt END AS cnt,\r\n"
					+ "    CASE \r\n"
					+ "        WHEN cnt > 0 THEN ROUND (money / cnt)   -- 결제단가  \r\n"
					+ "        ELSE 0 \r\n"
					+ "    END AS avgcnt \r\n"
					+ "FROM (\r\n"
					+ "    SELECT \r\n"
					+ "        SUM(ocash) AS money, -- 총매출액   \r\n"
					+ "        SUM(opoint) AS dc,  -- 할인금액 (포인트 사용 금액)  \r\n"
					+ "        SUM(ocash) - SUM(opoint) AS ofs, -- 매출금액 (총매출액 - 할인금액)   \r\n"
					+ "        SUM(ocash) * 0.1 AS st, -- 부가세  결제금액10%  \r\n"
					+ "        SUM(ocash) - SUM(opoint) - (SUM(ocash) * 0.1) AS toa, -- 순매출  \r\n"
					+ "        COUNT(DISTINCT TO_CHAR(odate, 'YYYY-MM-DD HH24:MI:SS')) AS cnt  -- 주문 건수 (날짜, 시, 분, 초까지 동일한 주문은 1건으로 처리)  \r\n"
					+ "    FROM sales\r\n"
					+ "    WHERE TRUNC(sales.odate) = TO_DATE(?, 'YYYY-MM-DD')\r\n"
					+ "        AND ocash >= 0  -- 해당 날짜 검색 (년-월-일 기준)  \r\n"
					+ ")";
			
			//금일 
			ps = conn.prepareStatement(sql);
			ps.setString(1, nowString);
			rs = ps.executeQuery();
			while(rs.next()) {
				String total = rs.getString("money");
				String dc = rs.getString("dc");
				String ofs = rs.getString("ofs");
				String st = rs.getString("st");
				String toa = rs.getString("toa");
				String cnt = rs.getString("cnt");
				String avgcnt = rs.getString("avgcnt");
				ta_today.setText(total + "\t\t" + dc + "\t\t" + ofs + "\t\t" + st + "\t\t" + toa
						+ "\t\t" + cnt + "\t\t" + avgcnt);
			}
			
			//전일
			ps = conn.prepareStatement(sql);
			ps.setString(1, yesterdayString);
			rs = ps.executeQuery();
			while(rs.next()) {
				String total = rs.getString("money");
				String dc = rs.getString("dc");
				String ofs = rs.getString("ofs");
				String st = rs.getString("st");
				String toa = rs.getString("toa");
				String cnt = rs.getString("cnt");
				String avgcnt = rs.getString("avgcnt");
				ta_yesterday.setText(total + "\t\t" + dc + "\t\t" + ofs + "\t\t" + st + "\t\t" + toa
						+ "\t\t" + cnt + "\t\t" + avgcnt);
			}
			//전주
			ps = conn.prepareStatement(sql);
			ps.setString(1, lastWeekString);
			rs = ps.executeQuery();
			while(rs.next()) {
				String total = rs.getString("money");
				String dc = rs.getString("dc");
				String ofs = rs.getString("ofs");
				String st = rs.getString("st");
				String toa = rs.getString("toa");
				String cnt = rs.getString("cnt");
				String avgcnt = rs.getString("avgcnt");
				ta_lastweek.setText(total + "\t\t" + dc + "\t\t" + ofs + "\t\t" + st + "\t\t" + toa
						+ "\t\t" + cnt + "\t\t" + avgcnt);
			}
			//전월
			ps = conn.prepareStatement(sql);
			ps.setString(1, lastMonthString);
			rs = ps.executeQuery();
			while(rs.next()) {
				String total = rs.getString("money");
				String dc = rs.getString("dc");
				String ofs = rs.getString("ofs");
				String st = rs.getString("st");
				String toa = rs.getString("toa");
				String cnt = rs.getString("cnt");
				String avgcnt = rs.getString("avgcnt");
				ta_thepreviousmonth.setText(total + "\t\t" + dc + "\t\t" + ofs + "\t\t" + st + "\t\t" + toa
						+ "\t\t" + cnt + "\t\t" + avgcnt);
			}
			
			//=========================
			//조회당일현황 ((기본 시스템날짜 금일 
			ps = conn.prepareStatement(sql);
			ps.setString(1, nowString);
			rs = ps.executeQuery();
			while(rs.next()) {
				String total = rs.getString("money");
				String cnt = rs.getString("cnt");
				tf_total.setText(total);
				tf_payment.setText(cnt);
			}
				
			//주간매출현황 
			
			// 한국 로케일 설정
			Calendar cld2 = Calendar.getInstance(Locale.KOREA);

			// 현재 날짜 기준으로 해당 주의 월요일 구하기 (주의 시작)
			cld2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

			// 이번 주의 모든 날짜를 저장할 배열
			String[] weekend = new String[7];

			// 날짜 패턴 설정
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		
			
			// 주간 날짜 설정
			for (int i = 0; i < 7; i++) {
				weekend[i] = sdf3.format(cld2.getTime()); // 현재 날짜 저장
				cld2.add(Calendar.DATE, 1); // 하루 증가
			}
			// 주간매출현황 월
			ps.setString(1, weekend[0]);
			rs = ps.executeQuery();
			while(rs.next()) {
					String total = rs.getString("money");
					tf_monday.setText(total);
			}
			// 주간매출현황 화
			ps.setString(1, weekend[1]);
			rs = ps.executeQuery();
			while(rs.next()) {
					String total = rs.getString("money");
					tf_tuesday.setText(total);
			}
			// 주간매출현황 수
			ps.setString(1, weekend[2]);
			rs = ps.executeQuery();
			while(rs.next()) {
					String total = rs.getString("money");
					tf_wednesday.setText(total);
			}
			// 주간매출현황 목
			ps.setString(1, weekend[3]);
			rs = ps.executeQuery();
			while(rs.next()) {
					String total = rs.getString("money");
					tf_thursday.setText(total);
			}
			// 주간매출현황 금
			ps.setString(1, weekend[4]);
			rs = ps.executeQuery();
			while(rs.next()) {
					String total = rs.getString("money");
					tf_friday.setText(total);
			}
			// 주간매출현황 토
			ps.setString(1, weekend[5]);
			rs = ps.executeQuery();
			while(rs.next()) {
					String total = rs.getString("money");
					tf_saturday.setText(total);
			}
			// 주간매출현황 일
			ps.setString(1, weekend[6]);
			rs = ps.executeQuery();
			while(rs.next()) {
					String total = rs.getString("money");
					tf_sunday.setText(total);
			}
			
			//금액대별매출 ((기본 금일
				sql = "SELECT\r\n"
						+ "    NVL(SUM(CASE WHEN sales.ocash > 0 AND sales.ocash <= 5000 THEN 1 ELSE 0 END),0) AS cnt_5000,  --5천원이하 만 \r\n"
						+ "    NVL(SUM(CASE WHEN sales.ocash > 5000 AND sales.ocash <= 10000 THEN 1 ELSE 0 END),0) AS cnt_10000, --5이상만원이하 \r\n"
						+ "    NVL(SUM(CASE WHEN sales.ocash > 10000 AND sales.ocash <= 30000 THEN 1 ELSE 0 END),0) AS cnt_30000, -- 만원이상 - 3만원이하 \r\n"
						+ "    NVL(SUM(CASE WHEN sales.ocash > 30000 AND sales.ocash <= 50000 THEN 1 ELSE 0 END),0) AS cnt_50000, -- 3만원이상 5만원이하 \r\n"
						+ "    NVL(SUM(CASE WHEN sales.ocash > 50000 THEN 1 ELSE 0 END),0) AS cnt_00000 --5만원 초과  \r\n"
						+ "FROM sales\r\n"
						+ "WHERE TRUNC(odate) = TO_DATE(?, 'YYYY-MM-DD')";
				ps = conn.prepareStatement(sql);
				ps.setString(1, nowString);
				rs = ps.executeQuery();
				while(rs.next()) {
					String cnt_5000 = rs.getString("cnt_5000");
					String cnt_10000 = rs.getString("cnt_10000");
					String cnt_30000 = rs.getString("cnt_30000");
					String cnt_50000 = rs.getString("cnt_50000");
					String cnt_00000 = rs.getString("cnt_00000");
					ta_notmorethan5000won.setText(cnt_5000);
					ta_notmorethan10000won.setText(cnt_10000);
					ta_notmorethan30000won.setText(cnt_30000);
					ta_notmorethan50000won.setText(cnt_50000);
					ta_notmorethan00000won.setText(cnt_00000);
				}
				
			//메뉴별매출 (( 기본 금일 
				sql = "SELECT \r\n"
						+ "    menu.mno, \r\n"
						+ "    menu.mname,         -- 메뉴 이름 \r\n"
						+ "    NVL(SUM(sales.ocash), 0) AS sc   -- NULL 값이면 0으로 변환  \r\n"
						+ "FROM menu   \r\n"
						+ "LEFT JOIN sales ON menu.mno = sales.mno  \r\n"
						+ "  AND TRUNC(sales.odate) = TO_DATE(?, 'YYYY-MM-DD')  --  특정 날짜 기준 매출 조회 \r\n"
						+ "  AND sales.ocash >= 0  --  음수 결제 금액 제외  \r\n"
						+ "GROUP BY menu.mno, menu.mname  ";
				ps = conn.prepareStatement(sql);
				ps.setString(1, nowString);
				rs = ps.executeQuery();
				while (rs.next()) { // 여러 개의 메뉴 데이터를 확인해야 함
					String mno = rs.getString("mno");
					String mname = rs.getString("mname");
					String sc = rs.getString("sc"); // 매출 금액 가져오기

					if (mno.equals("1")) {
						ta_lb_menu1.setText(sc);
					} else if (mno.equals("2")) {
						ta_lb_menu2.setText(sc);
					} else if (mno.equals("3")) {
						ta_lb_menu3.setText(sc);
					} else if (mno.equals("4")) {
						ta_lb_menu4.setText(sc);
					} else if (mno.equals("5")) {
						ta_lb_menu5.setText(sc);
					} else if (mno.equals("6")) {
						ta_lb_menu6.setText(sc);
					}
				}
				
				//금고현황
				sql  = "select * from bank";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()) {
					String moneyy = rs.getString("money");
					ta_treasurybalance.setText(moneyy);
				}
				
				//최근결제내역 
				try {
					listset();
					
				}catch(Exception e10) {
					e10.printStackTrace();
				}
			//========================================================================//
				//결제취소 ActionLisner 
				lb_cancellation_of_payment.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							String	sql20 = "UPDATE sales\r\n"
									+ "SET ocash = -ABS(ocash)  -- 결제 금액을 음수로 변환\r\n"
									+ "WHERE mno = (SELECT mno FROM menu WHERE mname = ?)\r\n"
									+ "AND odate = TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS')\r\n"
									+ "AND gusno = ?  -- 회원번호 동일한 경우만 업데이트\r\n"
									+ "AND ocash > 0  -- 이미 음수인 경우 중복 업데이트 방지\r\n";
							
							String ama = li_employee_list.getSelectedItem();
							System.out.println(ama);
							String [] part = ama.trim().split("\\s+");
							
							String gusno1 = part [0];   //회원번호
							if(gusno1.equals("0")) {	
								String guname1 = "비회원";
							}else { String guname1 = part [1]; }//회원이름
							String menuname1 = part [2]; //메뉴명 
							String ocount1 = part [3];    //카운트개수
							String ocash1 = part [4];	//결제한금액
							String opoint1 = part [5];	//사용포인트
							String orderdate = part[6] +" "+part[7];  //시간
							
							
							int ocash11 = Integer.parseInt(ocash1);
							//현재금고잔액 불러오기 
							int monnnney =0;
							sql  ="SELECT * FROM bank";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							while(rs.next()) {
								monnnney = rs.getInt("money");
							}
							
							ocash11 =Integer.parseInt(ocash1);
							
							if(monnnney > ocash11) {
							
								//결제 취소 업데이트 
								PreparedStatement	ps11 = conn.prepareStatement(sql20);
								ps11.setString(1, menuname1);
								ps11.setString(2, orderdate);
								ps11.setString(3, gusno1);
								int update = ps11.executeUpdate();
								puw.showPopUp("결제취소", "결제취소 완료 되었습니다","취소금액:"+ocash11);
								System.out.println("결제 "+update+"건 취소 완료");
								
								// 금고 결제취소 한건에 대한 결제값 빼주기 회원일때 
								if(!gusno1.equals("0")) {
									
									String sql8 = "UPDATE bank\r\n"
											+ "SET money = money + (\r\n"
											+ "    SELECT ocash  -- 이미 음수 값이므로 더하기로 차감 효과\r\n"
											+ "    FROM sales\r\n"
											+ "    WHERE mno = (SELECT mno FROM menu WHERE mname = ?)  -- 메뉴명으로 검색\r\n"
											+ "    AND odate = TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS')  -- 주문 날짜/시간 일치\r\n"
											+ "    AND gusno = ?  -- 회원번호 동일\r\n"
											+ "    AND ocash < 0  -- 취소된 결제만 반영\r\n"
											+ ")\r\n"
											+ "WHERE ROWNUM = 1";
									PreparedStatement ps8 = conn.prepareStatement(sql8);
									ps8.setString(1, menuname1);
									ps8.setString(2, orderdate);
									ps8.setString(3, gusno1);
									int update2 = ps8.executeUpdate();
									System.out.println(update2+"건 취소완료 금고"+ocash1+"원 차감 완료");
									// 회원 누적금액 차감
									String sql10 = "UPDATE guest\r\n"
											+ "SET gussale = gussale - (\r\n"
											+ "    SELECT ABS(ocash)  -- 사용한 포인트만큼 차감\r\n"
											+ "    FROM sales\r\n"
											+ "    WHERE mno = (SELECT mno FROM menu WHERE mname = ?)  -- 특정 메뉴명\r\n"
											+ "    AND odate = TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS')  -- 특정 주문 시간\r\n"
											+ "    AND gusno = ?  -- 특정 회원번호          \r\n"
											+ "    AND ocash < 0  -- 사용한 포인트가 있는 경우만\r\n"
											+ ")\r\n"
											+ "WHERE gusno = ?  -- 특정 회원의 누적 매출에서 차감";
									
									PreparedStatement ps10 = conn.prepareStatement(sql10);
									ps10.setString(1,menuname1 );
									ps10.setString(2,orderdate );
									ps10.setString(3, gusno1);
									ps10.setString(4, gusno1);
									int update3 = ps10.executeUpdate();
									System.out.println("회원 누적 금액"+ocash1+"차감 완료");		
									
									//인터넷뱅킹 insert 
									Calendar now = Calendar.getInstance();
									java.sql.Timestamp jst3 = new java.sql.Timestamp(now.getTimeInMillis());
									//현재금고잔액 불러오기 
									
									sql  ="SELECT * FROM bank";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									while(rs.next()) {
										monnnney = rs.getInt("money");
									}
									ocash11 =Integer.parseInt(ocash1);
									// 인터넷뱅킹 insert
									// 번호 "현재시각" "출금" "환불" ocash1 금고잔액 
									sql = "INSERT INTO banking VALUES(sq_banking_bno.nextval,?,?,?,?)";
									ps = conn.prepareStatement(sql);
									ps.setString(1, "결제취소");
									ps.setInt(2, -ocash11);
									ps.setInt(3, monnnney);
									ps.setTimestamp(4, jst3);
									int update4 = ps.executeUpdate();
									System.out.println(update4+"건 insert완료 ");
											
								// 비회원 일때 금고만 차감 
								}else if(gusno1.equals("0")) {
									
									String sql9 = "UPDATE bank\r\n"
											+ "SET money = money + (\r\n"
											+ "    SELECT ocash  -- 이미 음수 값이므로 더하기로 차감 효과\r\n"
											+ "    FROM sales\r\n"
											+ "    WHERE mno = (SELECT mno FROM menu WHERE mname = ?)  -- 메뉴명으로 검색\r\n"
											+ "    AND odate = TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS')  -- 주문 날짜/시간 일치\r\n"
											+ "    AND gusno = ?  -- 회원번호 동일\r\n"
											+ "    AND ocash < 0  -- 취소된 결제만 반영\r\n"
											+ ")\r\n"
											+ "WHERE ROWNUM = 1";
									PreparedStatement ps9 = conn.prepareStatement(sql9);
									ps9.setString(1, menuname1);
									ps9.setString(2, orderdate);
									ps9.setString(3, gusno1);
									int update2 = ps9.executeUpdate();
									puw.showPopUp("결제취소", "결제취소 완료 되었습니다","취소금액:"+ocash11);
									
									//인터넷뱅킹 insert 
									Calendar now = Calendar.getInstance();
									java.sql.Timestamp jst3 = new java.sql.Timestamp(now.getTimeInMillis());
									//현재금고잔액 불러오기 
									sql  ="SELECT * FROM bank";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									while(rs.next()) {
										monnnney = rs.getInt("money");
									}
									
									// 인터넷뱅킹 취소내역 출력 
									//현재금고잔액 불러오기 
									sql  ="SELECT * FROM bank";
									ps = conn.prepareStatement(sql);
									rs = ps.executeQuery();
									while(rs.next()) {
										monnnney = rs.getInt("money");
									}
									ocash11 =Integer.parseInt(ocash1);
									// 인터넷뱅킹 insert
									// 번호 "현재시각" "출금" "환불" ocash1 금고잔액 
									sql = "INSERT INTO banking VALUES(sq_banking_bno.nextval,?,?,?,?)";
									ps = conn.prepareStatement(sql);
									ps.setString(1, "결제취소");
									ps.setInt(2, -ocash11);
									ps.setInt(3, monnnney);
									ps.setString(4, nowString);
									int update3 = ps.executeUpdate();
									System.out.println(update3+"건 insert완료 ");
								}
								
						}else {
							puw.showPopUp("결제취소", "잔고가 부족합니다","현재잔고:"+monnnney);
						}	
							try {
								listset();
								
							}catch(Exception e10) {
								e10.printStackTrace();
							}
						
						}catch(SQLException e1) {
						
						}
					}
				
				});
				
			//========================================================================//
				//조회 ActionLisner
				bt_inquiry.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						//선택 날짜 String 으로변환해서 가져옴 
						//조회당일현황 조회시 시 해당날짜 정보 출력 \
						try{
							String si = c_choice2.getSelectedItem();
							String spl2 = "SELECT    \r\n"
									+ "    CASE WHEN money IS NULL THEN 0 ELSE money END AS money, \r\n"
									+ "    CASE WHEN dc IS NULL THEN 0 ELSE dc END AS dc,  \r\n"
									+ "    CASE WHEN ofs IS NULL THEN 0 ELSE ofs END AS ofs,\r\n"
									+ "    CASE WHEN st IS NULL THEN 0 ELSE st END AS st,\r\n"
									+ "    CASE WHEN toa IS NULL THEN 0 ELSE toa END AS toa,\r\n"
									+ "    CASE WHEN cnt IS NULL THEN 0 ELSE cnt END AS cnt,\r\n"
									+ "    CASE \r\n"
									+ "        WHEN cnt > 0 THEN ROUND (money / cnt)   -- 결제단가  \r\n"
									+ "        ELSE 0 \r\n"
									+ "    END AS avgcnt \r\n"
									+ "FROM (\r\n"
									+ "    SELECT \r\n"
									+ "        SUM(ocash) AS money, -- 총매출액 (부가세 제외한 순매출액)  \r\n"
									+ "        SUM(opoint) AS dc,  -- 할인금액 (포인트 사용 금액 포함)  \r\n"
									+ "        SUM(ocash) - SUM(opoint) AS ofs, -- 매출금액 (총매출액 - 할인금액)   \r\n"
									+ "        SUM(ocash) * 0.9 AS st, -- 부가세 (총매출액 * 0.9)  \r\n"
									+ "        SUM(ocash) - SUM(opoint) + (SUM(ocash) * 0.9) AS toa, -- 최종 결제금액  \r\n"
									+ "        COUNT(DISTINCT TO_CHAR(odate, 'YYYY-MM-DD HH24:MI:SS')) AS cnt  -- 주문 건수 (날짜, 시, 분, 초까지 동일한 주문은 1건으로 처리)  \r\n"
									+ "    FROM sales\r\n"
									+ "    WHERE TRUNC(sales.odate) = TO_DATE(?, 'YYYY-MM-DD')\r\n"
									+ "        AND ocash >= 0  -- 해당 날짜 검색 (년-월-일 기준)  \r\n"
									+ ")\r\n"
									+ "";
							
						PreparedStatement ps2 = conn.prepareStatement(spl2);
							ps2.setString(1, si);
						ResultSet	rs2 = ps2.executeQuery();
							while(rs2.next()) {
								String total = rs2.getString("money");
								String cnt = rs2.getString("cnt");
								tf_payment.setText(total);
								tf_total.setText(cnt);
							}
							
							//금액대별매출 조회시 
							sql = "SELECT\r\n"
									+ "    NVL(SUM(CASE WHEN sales.ocash > 0 AND sales.ocash <= 5000 THEN 1 ELSE 0 END),0) AS cnt_5000,  --5천원이하 만 \r\n"
									+ "    NVL(SUM(CASE WHEN sales.ocash > 5000 AND sales.ocash <= 10000 THEN 1 ELSE 0 END),0) AS cnt_10000, --5이상만원이하 \r\n"
									+ "    NVL(SUM(CASE WHEN sales.ocash > 10000 AND sales.ocash <= 30000 THEN 1 ELSE 0 END),0) AS cnt_30000, -- 만원이상 - 3만원이하 \r\n"
									+ "    NVL(SUM(CASE WHEN sales.ocash > 30000 AND sales.ocash <= 50000 THEN 1 ELSE 0 END),0) AS cnt_50000, -- 3만원이상 5만원이하 \r\n"
									+ "    NVL(SUM(CASE WHEN sales.ocash > 50000 THEN 1 ELSE 0 END),0) AS cnt_00000 --5만원 초과  \r\n"
									+ "FROM sales\r\n"
									+ "WHERE TRUNC(odate) = TO_DATE(?, 'YYYY-MM-DD')";
							ps = conn.prepareStatement(sql);
							ps.setString(1, si);
							rs = ps.executeQuery();
							while(rs.next()) {
								String cnt_5000 = rs.getString("cnt_5000");
								String cnt_10000 = rs.getString("cnt_10000");
								String cnt_30000 = rs.getString("cnt_30000");
								String cnt_50000 = rs.getString("cnt_50000");
								String cnt_00000 = rs.getString("cnt_00000");
								ta_notmorethan5000won.setText(cnt_5000);
								ta_notmorethan10000won.setText(cnt_10000);
								ta_notmorethan30000won.setText(cnt_30000);
								ta_notmorethan50000won.setText(cnt_50000);
								ta_notmorethan00000won.setText(cnt_00000);
							}
							
							//메뉴별매출  조회시 
							sql = "SELECT \r\n"
									+ "    menu.mno, \r\n"
									+ "    menu.mname,         -- 메뉴 이름 \r\n"
									+ "    NVL(SUM(sales.ocash), 0) AS sc   -- NULL 값이면 0으로 변환  \r\n"
									+ "FROM menu   \r\n"
									+ "LEFT JOIN sales ON menu.mno = sales.mno  \r\n"
									+ "  AND TRUNC(sales.odate) = TO_DATE(?, 'YYYY-MM-DD')  --  특정 날짜 기준 매출 조회 \r\n"
									+ "  AND sales.ocash >= 0  --  음수 결제 금액 제외  \r\n"
									+ "GROUP BY menu.mno, menu.mname  ";
							ps = conn.prepareStatement(sql);
							ps.setString(1, si);
							rs = ps.executeQuery();
							while (rs.next()) { // 여러 개의 메뉴 데이터를 확인해야 함
								String mno = rs.getString("mno");
								String mname = rs.getString("mname");
								String sc = rs.getString("sc"); // 매출 금액 가져오기

								if (mno.equals("1")) {
									ta_lb_menu1.setText(sc);
								} else if (mno.equals("2")) {
									ta_lb_menu2.setText(sc);
								} else if (mno.equals("3")) {
									ta_lb_menu3.setText(sc);
								} else if (mno.equals("4")) {
									ta_lb_menu4.setText(sc);
								} else if (mno.equals("5")) {
									ta_lb_menu5.setText(sc);
								} else if (mno.equals("6")) {
									ta_lb_menu6.setText(sc);
								}
							}
							
						}catch(SQLException e1) {
							
						}
						
					}
				});
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		// 메뉴총괄표  출력 
		

	}
	public void salesByMenu() {

		Font f_title = new Font("Default Font", Font.BOLD, 15);
		Font f_subtitle = new Font("Default Font", Font.BOLD, 12);
		Font f_menu = new Font("Default Font", Font.TRUETYPE_FONT, 15);

		// 메인에 부착
		this.setLayout(new BorderLayout());

		// 메인 상단 보더레이아웃
		Panel p_table_north = new Panel(new BorderLayout());
		this.add(p_table_north, BorderLayout.NORTH);

		// 메인 상단의 보더레이아웃의 상단 그리드로 부착 ((메뉴리스트 버튼 날짜스크롤 2개 조회 버튼

		Panel p_table_north_north = new Panel(new GridLayout(0, 7));
		p_table_north.add(p_table_north_north, BorderLayout.NORTH);

		bt_salesstatus = new Button("매출총괄표");
		bt_salesbymenu = new Button("메뉴별매출");
		bt_cancellationdetails = new Button("취소내역");
		bt_internet_banking = new Button("인터넷뱅킹");
		Choice ci_choice4 = new Choice();
		Choice ci_choice5 = new Choice();
		bt_inquiry = new Button("조회");

		p_table_north_north.add(bt_salesstatus);
		p_table_north_north.add(bt_salesbymenu);
		p_table_north_north.add(bt_cancellationdetails);
		p_table_north_north.add(bt_internet_banking);
		p_table_north_north.add(ci_choice4);
		p_table_north_north.add(ci_choice5);
		p_table_north_north.add(bt_inquiry);

		// 메인 상단의 상단 보더레이아웃의 중단 (( 라벨 센터 메뉴별매출
		Label lb_salesbymenu = new Label("메뉴별매출", Label.CENTER);
		p_table_north.add(lb_salesbymenu, BorderLayout.CENTER);
		lb_salesbymenu.setFont(f_title);

		// 메인 상단의 상단 보더레이아웃의 하단 (( JSeparator
		JSeparator separator = new JSeparator();
		p_table_north.add(separator, BorderLayout.SOUTH);
		separator.setForeground(Color.pink);

		// 메인 중단 그리드 레이아웃 Panel 0행7열 (( 메뉴번호 메뉴명 총매출금액 할인금액 매출금액 부가세 순매출
		Panel p_table_center = new Panel(new GridLayout(0, 7));
		this.add(p_table_center, BorderLayout.CENTER);

		Label lb_menunum = new Label("          메뉴번호");
		Label lb_menuname = new Label("|           메뉴명");
		Label lb_total_sales_amount = new Label("|      총매출금액");
		Label lb_discount_amount = new Label("|         할인금액");
		Label lb_sales_amount = new Label("|        매출금액");
		Label lb_surtax = new Label("|          부가세");
		Label lb_net_sales = new Label("|          순매출");

		p_table_center.add(lb_menunum);
		p_table_center.add(lb_menuname);
		p_table_center.add(lb_total_sales_amount);
		p_table_center.add(lb_discount_amount);
		p_table_center.add(lb_sales_amount);
		p_table_center.add(lb_surtax);
		p_table_center.add(lb_net_sales);

		// 메인 하단 보더레이아웃 Panel
		Panel p_table_south = new Panel(new BorderLayout());
		this.add(p_table_south, BorderLayout.SOUTH);

		// 메인 하단의 하단 보더레이아웃 센터 textArea
		TextArea ta_salesbymenu = new TextArea("", 26, 1, TextArea.SCROLLBARS_HORIZONTAL_ONLY);
		ta_salesbymenu.setEditable(false);
		p_table_south.add(ta_salesbymenu, BorderLayout.CENTER);

		
		menuTransitionAction();
		
		try{
			
			//왼쪽 오래된 날짜 우선 
			sql = "select distinct\r\n"
					+ "    to_char(odate,'YYYY-MM-DD')as day\r\n"
					+ "from sales\r\n"
					+ "order by day asc";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
					String ymd = rs.getString("day");
					ci_choice4.add(ymd);
			}
			
			//오른쪽 최근날짜 우선 
			sql = "select distinct\r\n"
					+ "    to_char(odate,'YYYY-MM-DD')as day\r\n"
					+ "from sales\r\n"
					+ "order by day desc";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
					String ymd = rs.getString("day");
					ci_choice5.add(ymd);
			}
			
			// 금일 날짜 불러 오기 
			// 날짜 패턴 설정
			String pattern1 = "yyyy-MM-dd";
			SimpleDateFormat sdf1 = new SimpleDateFormat(pattern1);
			// 현재 날짜 가져오기
			Date now = new Date();
			String nowString = sdf1.format(now);
			
			//메뉴별 판매 현황 1번 부터 ~
			sql = "SELECT \r\n"
					+ "    menu.mno AS mno, -- 메뉴번호  \r\n"
					+ "    menu.mname AS mname,  --메뉴명  \r\n"
					+ "    COALESCE(SUM(CASE WHEN sales.ocash > 0 THEN sales.ocash ELSE 0 END), 0) AS toa, --총매출액 \r\n"
					+ "    COALESCE(SUM(CASE WHEN sales.opoint > 0 THEN sales.opoint ELSE 0 END), 0) AS dis,  --할인금액 \r\n"
					+ "    COALESCE(SUM(CASE WHEN sales.ocash > 0 THEN sales.ocash ELSE 0 END) - \r\n"
					+ "             SUM(CASE WHEN sales.opoint > 0 THEN sales.opoint ELSE 0 END), 0) AS todi, --매출금액 \r\n"
					+ "    COALESCE((SUM(CASE WHEN sales.ocash > 0 THEN sales.ocash ELSE 0 END) - \r\n"
					+ "              SUM(CASE WHEN sales.opoint > 0 THEN sales.opoint ELSE 0 END)) * 0.1, 0) AS bu,  --부가세 \r\n"
					+ "    COALESCE((SUM(CASE WHEN sales.ocash > 0 THEN sales.ocash ELSE 0 END) - \r\n"
					+ "              SUM(CASE WHEN sales.opoint > 0 THEN sales.opoint ELSE 0 END)) * 0.9, 0) AS cash   -- 순매출 \r\n"
					+ "FROM menu  \r\n"
					+ "LEFT JOIN sales ON menu.mno = sales.mno  \r\n"
					+ "     AND TRUNC(sales.odate) = TO_DATE(?, 'YYYY-MM-DD')\r\n"
					+ "GROUP BY menu.mno, menu.mname  \r\n"
					+ "ORDER BY menu.mno ASC";
			ps = conn.prepareStatement(sql);
			ps.setString(1, nowString);
			rs = ps.executeQuery();
			while(rs.next()) {
				String mno = rs.getString("mno"); //메뉴번호
				String mname = rs.getString("mname");  //메뉴이름
				String toa = rs.getString("toa");  //총매출액
				String dis = rs.getString("dis");  // 할인금액
				String todi = rs.getString("todi"); //매출금액
				String bu = rs.getString("bu"); //부가세
				String cash = rs.getString("cash"); //순매출

				ta_salesbymenu.append(String.format("%-15s","")
						+mno+"\t\t"+mname+"\t   "+toa+"\t\t        "+dis+"\t\t     "+todi+"\t\t     "+bu+"\t\t     "+cash+"\n\n\n");
			}
			
			//조회버튼 눌렀을 떄
			bt_inquiry.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					try {
						ta_salesbymenu.setText("");
						String si3 = ci_choice4.getSelectedItem();
						String si4 = ci_choice5.getSelectedItem();
						sql = "SELECT \r\n"
								+ "    menu.mno AS mno, -- 메뉴번호 \r\n"
								+ "    menu.mname AS mname,  --메뉴명\r\n"
								+ "    COALESCE(SUM(CASE WHEN sales.ocash > 0 THEN sales.ocash ELSE 0 END), 0) AS toa, --총매출액  \r\n"
								+ "    COALESCE(SUM(CASE WHEN sales.opoint > 0 THEN sales.opoint ELSE 0 END), 0) AS dis,  --할인금액  \r\n"
								+ "    COALESCE(SUM(CASE WHEN sales.ocash > 0 THEN sales.ocash ELSE 0 END) - \r\n"
								+ "             SUM(CASE WHEN sales.opoint > 0 THEN sales.opoint ELSE 0 END), 0) AS todi, --매출금액  \r\n"
								+ "    COALESCE((SUM(CASE WHEN sales.ocash > 0 THEN sales.ocash ELSE 0 END) - \r\n"
								+ "              SUM(CASE WHEN sales.opoint > 0 THEN sales.opoint ELSE 0 END)) * 0.1, 0) AS bu,  --부가세  \r\n"
								+ "    COALESCE((SUM(CASE WHEN sales.ocash > 0 THEN sales.ocash ELSE 0 END) - \r\n"
								+ "              SUM(CASE WHEN sales.opoint > 0 THEN sales.opoint ELSE 0 END)) * 0.9, 0) AS cash   -- 순매출   \r\n"
								+ "FROM menu  \r\n"
								+ "LEFT JOIN sales ON menu.mno = sales.mno  \r\n"
								+ "    AND TRUNC(sales.odate) BETWEEN TO_DATE(?, 'YYYY-MM-DD') \r\n"
								+ "                               AND TO_DATE(?, 'YYYY-MM-DD')\r\n"
								+ "GROUP BY menu.mno, menu.mname  \r\n"
								+ "ORDER BY menu.mno ASC  ";
						ps = conn.prepareStatement(sql);
						ps.setString(1, si3);
						ps.setString(2, si4);
						rs = ps.executeQuery();
						while(rs.next()) {
							String mno = rs.getString("mno"); //메뉴번호
							String mname = rs.getString("mname");  //메뉴이름
							String toa = rs.getString("toa");  //총매출액
							String dis = rs.getString("dis");  // 할인금액
							String todi = rs.getString("todi"); //매출금액
							String bu = rs.getString("bu"); //부가세
							String cash = rs.getString("cash"); //순매출

							ta_salesbymenu.append(String.format("%-15s","")
									+mno+"\t\t"+mname+"\t\t"+toa+"\t\t"+dis+"\t\t"+todi+"\t\t"+bu+"\t\t"+cash+"\n\n\n");
						}
					}catch(SQLException e5) {
						e5.printStackTrace();
						
					}
					
				}
			});
			
		}catch(SQLException e) {
				e.printStackTrace();
		}
		
	}
	public void listset() throws Exception {
		//최근결제내역 
		li_employee_list.removeAll();
		sql = "SELECT    \r\n"
				+ "    sales.gusno AS gno,  --회원번호 \r\n"
				+ "    CASE \r\n"
				+ "        WHEN sales.gusno = 0 THEN '비회원'  \r\n"
				+ "        ELSE guest.gusname  \r\n"
				+ "    END AS gu,    -- 회원이름 \r\n"
				+ "    menu.mname AS mu,     -- 메뉴이름 \r\n"
				+ "    sales.ocount AS sc,   -- 해당매뉴 주문개수  \r\n"
				+ "    sales.ocash AS so,   -- 해당메뉴의 총결제금액  \r\n"
				+ "    sales.opoint AS op,   -- 해당메뉴의 사용 포인트  \r\n"
				+ "    sales.odate AS da   -- 결제 시간  \r\n"
				+ "FROM sales  \r\n"
				+ "JOIN menu ON sales.mno = menu.mno     --  메뉴 조인해서 메뉴명 가져오고  \r\n"
				+ "LEFT JOIN guest ON sales.gusno = guest.gusno  -- 회원정보가 없는 경우 대비 LEFT JOIN  \r\n"
				+ "WHERE sales.ocash >= 0  \r\n"
				+ "ORDER BY sales.odate DESC  --최근 내역이 제일 상단으로  \r\n"
				+ "";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while(rs.next()) {
			String gno = rs.getString("gno");
			String gu = rs.getString("gu");  //회워명
			String mu = rs.getString("mu");  //
			String sc = rs.getString("sc");
			String so = rs.getString("so");
			String op = rs.getString("op");
			String da = rs.getString("da");

			li_employee_list.add(String.format("%-5s","")+
					emptySet_String(gno,15)+emptySet_String(gu,18)+
					emptySet_String(mu,16)+emptySet_String("x"+sc,15)+
					emptySet_String(so,30)+emptySet_String(op,22)+da);
		}
	}

	public void cancellationDetails() {
		Font f_title = new Font("Default Font", Font.BOLD, 15);
		Font f_subtitle = new Font("Default Font", Font.BOLD, 12);
		Font f_menu = new Font("Default Font", Font.TRUETYPE_FONT, 15);

		// 메인에 부착
		this.setLayout(new BorderLayout());

		// 메인 상단 메뉴리스트 버튼 날짜스크롤2개 조회 버튼
		Panel p_table_north = new Panel(new GridLayout(0, 7));
		this.add(p_table_north, BorderLayout.NORTH);

		bt_salesstatus = new Button("매출총괄표");
		bt_salesbymenu = new Button("메뉴별매출");
		bt_cancellationdetails = new Button("취소내역");
		bt_internet_banking = new Button("인터넷뱅킹");
		Choice ci_choice1 = new Choice();
		Choice ci_choice2 = new Choice();
		bt_inquiry = new Button("조회");

		p_table_north.add(bt_salesstatus);
		p_table_north.add(bt_salesbymenu);
		p_table_north.add(bt_cancellationdetails);
		p_table_north.add(bt_internet_banking);
		p_table_north.add(ci_choice1);
		p_table_north.add(ci_choice2);
		p_table_north.add(bt_inquiry);

		// 메인 중단 라벨 취소내역 센터
		Label lb_bt_cancellationdetails = new Label("취소내역", Label.CENTER);
		this.add(lb_bt_cancellationdetails, BorderLayout.CENTER);
		lb_bt_cancellationdetails.setFont(f_title);

		// 메인 하단 보더레이아웃 Panel 생성
		Panel p_table_south = new Panel(new BorderLayout());
		this.add(p_table_south, BorderLayout.SOUTH);

		// 메인 하단의 보더레이아웃의 상단 JSeparator
		JSeparator separator = new JSeparator();
		p_table_south.add(separator, BorderLayout.NORTH);
		separator.setForeground(Color.pink);

		// 메인 하단의 보더레이아웃의 센터 그리드레이아웃 0행 7열 (( Label 메뉴번호 메뉴명 메뉴금액 회원명 회원등급 취소시간
		Panel p_table_south_center = new Panel(new GridLayout(0, 6));
		p_table_south.add(p_table_south_center, BorderLayout.CENTER);

		Label lb_menu_num = new Label("            메뉴번호");
		Label lb_menu_name = new Label("|              메뉴명");
		Label lb_menu_amount = new Label("|            취소금액");
		Label lb_member_name = new Label("|             회원명");
		Label lb_member_grade = new Label("|           회원등급");
		Label lb_cancellation_time = new Label("|            취소시간");

		p_table_south_center.add(lb_menu_num);
		p_table_south_center.add(lb_menu_name);
		p_table_south_center.add(lb_menu_amount);
		p_table_south_center.add(lb_member_name);
		p_table_south_center.add(lb_member_grade);
		p_table_south_center.add(lb_cancellation_time);

		// 메인 하단의 보더레이아웃의 하단 textArea
		TextArea ta_cancellationDetails = new TextArea("", 27, 1, TextArea.SCROLLBARS_HORIZONTAL_ONLY);
		ta_cancellationDetails.setEditable(false);
		p_table_south.add(ta_cancellationDetails, BorderLayout.SOUTH);

		menuTransitionAction();
		
		// 날짜 조회 칸
		try{
			
			//왼쪽 오래된 날짜 우선 
			sql = "select distinct\r\n"
					+ "    to_char(odate,'YYYY-MM-DD')as day\r\n"
					+ "from sales\r\n"
					+ "order by day asc";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
					String ymd = rs.getString("day");
					ci_choice1.add(ymd);
			}
			
			//오른쪽 최근날짜 우선 
			sql = "select distinct\r\n"
					+ "    to_char(odate,'YYYY-MM-DD')as day\r\n"
					+ "from sales\r\n"
					+ "order by day desc";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
					String ymd = rs.getString("day");
					ci_choice2.add(ymd);
			}
			
			// 기본 창 취소 내역 출력 금일 날짜 만 최신순 부터 
			// 금일 날짜 불러 오기 
			// 날짜 패턴 설정
			String pattern1 = "yyyy-MM-dd";
			SimpleDateFormat sdf1 = new SimpleDateFormat(pattern1);
			// 현재 날짜 가져오기
			Date now = new Date();
			String nowString = sdf1.format(now);
			sql = "SELECT\r\n"
					+ "    menu.mno AS mno,   --메뉴번호 \r\n"
					+ "    menu.mname AS mm,   --메뉴이름 \r\n"
					+ "    ABS(sales.ocash) as sc,   --취소금액 \r\n"
					+ "    sales.ocount AS snt,\r\n"
					+ "    CASE \r\n"
					+ "        WHEN sales.gusno = 0 THEN '비회원'  \r\n"
					+ "        ELSE guest.gusname \r\n"
					+ "    END AS gn,   -- 회원이름 \r\n"
					+ "    CASE \r\n"
					+ "        WHEN sales.gusno = 0 THEN 'Unrank'\r\n"
					+ "        ELSE rank.rname\r\n"
					+ "    END AS rr, -- 회원등급 \r\n"
					+ "    sales.odate AS so  --결제날짜 \r\n"
					+ "FROM sales\r\n"
					+ "LEFT JOIN menu ON sales.mno = menu.mno\r\n"
					+ "LEFT JOIN guest ON sales.gusno = guest.gusno\r\n"
					+ "LEFT JOIN rank ON guest.rname = rank.rname\r\n"
					+ "WHERE sales.ocash < 0\r\n"
					+ "AND TRUNC(odate) = TO_DATE(?, 'YYYY-MM-DD')";
			ps = conn.prepareStatement(sql);
			ps.setString(1, nowString);
			rs = ps.executeQuery();
			while(rs.next()) {
				String mno = rs.getString("mno"); //메뉴번호
				String mm = rs.getString("mm");  //메뉴이름
				String sc = rs.getString("sc");  //취소금액
				String snt = rs.getString("snt");  // 주문갯수
				String gn = rs.getString("gn"); //회원이름
				String rr = rs.getString("rr"); //회원등급
				String so = rs.getString("so"); //결제날짜

				ta_cancellationDetails.append(String.format("%-15s","")
						+mno+"\t\t"+mm+"x"+snt+"\t\t"+sc+"\t\t"+gn+"\t\t"+rr+"\t\t"+so+"\n");
				
			}
			
			//조회 버튼 눌렀을 때 
			bt_inquiry.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						ta_cancellationDetails.setText("");
						String si1 = ci_choice1.getSelectedItem();
						String si2 = ci_choice2.getSelectedItem();
						sql = "SELECT\r\n"
								+ "    menu.mno AS mno,   --메뉴번호 \r\n"
								+ "    menu.mname AS mm,   --메뉴이름 \r\n"
								+ "    ABS(sales.ocash) as sc,   --취소금액 \r\n"
								+ "    sales.ocount AS snt,\r\n"
								+ "    CASE \r\n"
								+ "        WHEN sales.gusno = 0 THEN '비회원'  \r\n"
								+ "        ELSE guest.gusname \r\n"
								+ "    END AS gn,   -- 회원이름 \r\n"
								+ "    CASE \r\n"
								+ "        WHEN sales.gusno = 0 THEN 'Unrank'\r\n"
								+ "        ELSE rank.rname\r\n"
								+ "    END AS rr, -- 회원등급 \r\n"
								+ "    sales.odate AS so  --결제날짜 \r\n"
								+ "FROM sales\r\n"
								+ "LEFT JOIN menu ON sales.mno = menu.mno\r\n"
								+ "LEFT JOIN guest ON sales.gusno = guest.gusno\r\n"
								+ "LEFT JOIN rank ON guest.rname = rank.rname\r\n"
								+ "WHERE sales.ocash < 0\r\n"
								+ "AND TRUNC(odate) BETWEEN TO_DATE(?, 'YYYY-MM-DD') \r\n"
								+ "                     AND TO_DATE(?, 'YYYY-MM-DD')\r\n"
								+ "";	
						ps = conn.prepareStatement(sql);
						ps.setString(1, si1);
						ps.setString(2, si2);
						rs = ps.executeQuery();
						while(rs.next()) {
							String mno = rs.getString("mno"); //메뉴번호
							String mm = rs.getString("mm");  //메뉴이름
							String sc = rs.getString("sc");  //취소금액
							String snt = rs.getString("snt");  // 주문갯수
							String gn = rs.getString("gn"); //회원이름
							String rr = rs.getString("rr"); //회원등급
							String so = rs.getString("so"); //결제날짜
							
							ta_cancellationDetails.append(String.format("%-15s","")
									+mno+"\t\t"+mm+"x"+snt+"\t\t"+sc+"\t\t"+gn+"\t\t"+rr+"\t\t"+so+"\n");
							
						}
					}catch(SQLException e3) {
						e3.printStackTrace();
					}
					
				}
			});
			
		}catch(SQLException e) {
				e.printStackTrace();
		}
		
	}

	public void internetbanking() {

		Font f_title = new Font("Default Font", Font.BOLD, 15);
		Font f_subtitle = new Font("Default Font", Font.BOLD, 12);
		Font f_menu = new Font("Default Font", Font.TRUETYPE_FONT, 15);

		// 메인에 부착
		this.setLayout(new BorderLayout());

		// 메인 상단 보더레이아웃 Panel
		Panel p_table_north = new Panel(new BorderLayout());
		this.add(p_table_north, BorderLayout.NORTH);

		// 메인 상단의 상단 보더레이아웃 상단 그리드 레이아웃 0행7열
		Panel p_table_north_north = new Panel(new GridLayout(0, 7));
		p_table_north.add(p_table_north_north, BorderLayout.NORTH);

		bt_salesstatus = new Button("매출총괄표");
		bt_salesbymenu = new Button("메뉴별매출");
		bt_cancellationdetails = new Button("취소내역");
		bt_internet_banking = new Button("인터넷뱅킹");
		Label lb_mt1 = new Label();
		Label lb_mt2 = new Label();
		bt_inquiry = new Button("조회");

		p_table_north_north.add(bt_salesstatus);
		p_table_north_north.add(bt_salesbymenu);
		p_table_north_north.add(bt_cancellationdetails);
		p_table_north_north.add(bt_internet_banking);
		p_table_north_north.add(lb_mt1);
		p_table_north_north.add(lb_mt2);
		p_table_north_north.add(bt_inquiry);

		// 메인 상단의 상단 보더레이아웃 센터 인터넷 뱅킹
		Label lb_internetbanking = new Label("인터넷뱅킹", Label.CENTER);
		p_table_north.add(lb_internetbanking, BorderLayout.CENTER);
		lb_internetbanking.setFont(f_title);

		// 메인 상단의 하단 JSeparator
		JSeparator separator = new JSeparator();
		p_table_north.add(separator, BorderLayout.SOUTH);
		separator.setForeground(Color.pink);


		// 메인 센터 보더레이아웃 Panel
		Panel p_table_center = new Panel(new BorderLayout());
		this.add(p_table_center, BorderLayout.CENTER);

		// 메인 센터 보더레이아웃 상단 그리드레이아웃 0행 2열 ( Label 직원 목록 , 금고내역
		Panel p_table_center_north = new Panel(new GridLayout(0, 2));
		p_table_center.add(p_table_center_north, BorderLayout.NORTH);

		Label lb_employee_list = new Label("직원목록", Label.CENTER);
		Label lb_asset_status = new Label("자산현황", Label.CENTER);

		p_table_center_north.add(lb_employee_list);
		p_table_center_north.add(lb_asset_status);

		// 메인 센터 보더 레이아웃 센터 그리드레이아웃 0해 2열
		Panel p_tabel_center_center = new Panel(new GridLayout(0, 2, 10, 10));
		p_table_center.add(p_tabel_center_center, BorderLayout.CENTER);

		// 메인 센터 보더 레이아웃 센터 그리드레이아웃 왼쪽 1열
		Panel p_tabel_center_center_west = new Panel(new BorderLayout());
		p_tabel_center_center.add(p_tabel_center_center_west);

		// ===================1열==============================//

		// 메인 센터 보더 레이아웃 센터 그리드레이아웃 1열 보더레이아웃 상단 ( List ((직원 목록 나오는 칸
		Panel p_tabel_center_center_west_north = new Panel(new BorderLayout());
		p_tabel_center_center_west.add(p_tabel_center_center_west_north, BorderLayout.NORTH);

		List li_employee_list = new List(20, false);
		p_tabel_center_center_west_north.add(li_employee_list);

		// 메인 센터 보더 레이아웃 센터 그리드레이아웃 1열 보더레이아웃 중단 그리드레이아웃 0행 3열 ((textfield 금액입력칸
		Panel p_tabel_center_center_west_center = new Panel(new GridLayout(4, 3));
		p_tabel_center_center_west.add(p_tabel_center_center_west_center, BorderLayout.CENTER);

		Label lb_enter_the_amount = new Label("금액입력:", Label.RIGHT);
		TextArea ta_enter_the_amount = new TextArea("", 1, 1, TextArea.SCROLLBARS_HORIZONTAL_ONLY);
		ta_enter_the_amount.setEditable(false);

		Label lb_mt4 = new Label();

		Label lb_mt5 = new Label();
		Label lb_mt6 = new Label();
		Label lb_mt7 = new Label();
		Label lb_mt8 = new Label();
		Label lb_mt9 = new Label();
		Label lb_mt10 = new Label();

		Label lb_mt11 = new Label();

		p_tabel_center_center_west_center.add(lb_mt5);
		p_tabel_center_center_west_center.add(lb_mt6);
		p_tabel_center_center_west_center.add(lb_mt7);

		p_tabel_center_center_west_center.add(lb_enter_the_amount);
		p_tabel_center_center_west_center.add(ta_enter_the_amount);
		p_tabel_center_center_west_center.add(lb_mt4);

		p_tabel_center_center_west_center.add(lb_mt8);
		p_tabel_center_center_west_center.add(lb_mt9);
		p_tabel_center_center_west_center.add(lb_mt10);

		p_tabel_center_center_west_center.add(lb_mt11);

		// 메인 센터 보더 레이아웃 센터 그리드레이아웃 1열 보더레이아웃 하단 보더레이아웃 Panel 생성
		Panel p_tabel_center_center_west_south = new Panel(new BorderLayout());
		p_tabel_center_center_west.add(p_tabel_center_center_west_south, BorderLayout.SOUTH);

		// 메인 센터 보더 레이아웃 센터 그리드레이아웃 1열 보더레이아웃 하단 보더레이아웃의 상단 그리드레이아웃 0행 3열 ((
		// CheckboxGruop 3개 전체지급 선택지급 랜덤지급
		Panel p_tabel_center_center_west_south_north = new Panel(new GridLayout(0, 4, 10, 0));
		p_tabel_center_center_west_south.add(p_tabel_center_center_west_south_north, BorderLayout.NORTH);

		CheckboxGroup cbg_main = new CheckboxGroup();

		Checkbox cb_full_payment = new Checkbox("전체지급", cbg_main, false);
		Checkbox cb_selective_payment = new Checkbox("선택지급", cbg_main, false);

		Label lb_cc1 = new Label();
		Label lb_cc2 = new Label();

		p_tabel_center_center_west_south_north.add(lb_cc1);
		p_tabel_center_center_west_south_north.add(cb_full_payment);
		p_tabel_center_center_west_south_north.add(cb_selective_payment);
		p_tabel_center_center_west_south_north.add(lb_cc2);

		// 메인 센터 보더 레이아웃 센터 그리드레이아웃 1열 보더레이아웃 하단 보더레이아웃의 하단 (( Button 직원에게송금
		Panel p_tabel_center_center_west_south_south = new Panel(new BorderLayout());
		p_tabel_center_center_west_south.add(p_tabel_center_center_west_south_south, BorderLayout.SOUTH);

		Button bt_remittance_to_employees = new Button("직원에게 송금");
		p_tabel_center_center_west_south_south.add(bt_remittance_to_employees);

		// ===================1열 끝==============================//

		// ===================2열 시작==============================//

		// 메인 센터 보더 레이아웃 센터 그리드레이아웃 오른쪽 2열
		Panel p_tabel_center_center_east = new Panel(new BorderLayout());
		p_tabel_center_center.add(p_tabel_center_center_east);

		// 메인 센터 보더 레이아웃 센터 그리드레이아웃 2열 보더레이아웃 상단 (( textArea ((금고자산현황
		Panel p_tabel_center_center_east_north = new Panel(new BorderLayout());
		p_tabel_center_center_east.add(p_tabel_center_center_east_north, BorderLayout.NORTH);

		TextArea ta_asset_status = new TextArea("", 1, 1, TextArea.SCROLLBARS_HORIZONTAL_ONLY);
		ta_asset_status.setEditable(false);
		p_tabel_center_center_east_north.add(ta_asset_status);
		// 메인 센터 보더 레이아웃 센터 그리드레이아웃 2열 보더레이아웃 센터 그리드레이아웃 0행4열 (( 라벨 금액입력 / 금액 입력 받을
		// textfield // Button 입금 출금
		Panel p_tabel_center_center_east_center = new Panel(new GridLayout(7, 2));
		p_tabel_center_center_east.add(p_tabel_center_center_east_center, BorderLayout.CENTER);

		Label lb_enter_the_amount2 = new Label("금액입력:", Label.RIGHT);
		TextField tf_enter_the_amount2 = new TextField();
		tf_enter_the_amount2.setEditable(false);
		Button bt_Deposit = new Button("입금");
		Button bt_Withdrawal = new Button("출금");

		Label lb_mmmt1 = new Label();
		Label lb_mmmt2 = new Label();
		Label lb_mmmt3 = new Label();
		Label lb_mmmt4 = new Label();
		Label lb_mmmt5 = new Label();
		Label lb_mmmt6 = new Label();
		Label lb_mmmt7 = new Label();
		Label lb_mmmt8 = new Label();
		Label lb_mmmt9 = new Label();
		Label lb_mmmt10 = new Label();
		p_tabel_center_center_east_center.add(lb_mmmt9);
		p_tabel_center_center_east_center.add(lb_mmmt10);

		p_tabel_center_center_east_center.add(lb_enter_the_amount2);
		p_tabel_center_center_east_center.add(tf_enter_the_amount2);

		p_tabel_center_center_east_center.add(lb_mmmt1);
		p_tabel_center_center_east_center.add(lb_mmmt2);

		p_tabel_center_center_east_center.add(bt_Deposit);
		p_tabel_center_center_east_center.add(bt_Withdrawal);

		p_tabel_center_center_east_center.add(lb_mmmt3);
		p_tabel_center_center_east_center.add(lb_mmmt4);

		p_tabel_center_center_east_center.add(lb_mmmt5);
		p_tabel_center_center_east_center.add(lb_mmmt6);

		p_tabel_center_center_east_center.add(lb_mmmt7);
		p_tabel_center_center_east_center.add(lb_mmmt8);

		// 메인 센터 보더 레이아웃 센터 그리드레이아웃 2열 보더레이아웃 센터 하단 보더레이아웃 Panel
		Panel p_tabel_center_center_east_south = new Panel(new BorderLayout());
		p_tabel_center_center_east.add(p_tabel_center_center_east_south, BorderLayout.SOUTH);

		// 메인 센터 보더 레이아웃 센터 그리드레이아웃 2열 보더레이아웃 센터 하단 보더레이아웃 상단의 보더레이아웃 Panel
		Panel p_tabel_center_center_east_south_north = new Panel(new BorderLayout());
		p_tabel_center_center_east_south.add(p_tabel_center_center_east_south_north, BorderLayout.NORTH);

		// 메인 센터 보더 레이아웃 센터 그리드레이아웃 2열 보더레이아웃 센터 하단 보더레이아웃 상단 보더레이아웃의 상단 보더레이아웃
		Panel p_tabel_center_center_east_south_north_north = new Panel(new BorderLayout());
		p_tabel_center_center_east_south_north.add(p_tabel_center_center_east_south_north_north, BorderLayout.NORTH);

		// 메인 센터 보더 레이아웃 센터 그리드레이아웃 2열 보더레이아웃 센터 하단 보더레이아웃 상단의 보더레이아웃의 상단 보더레이아웃의 상단의
		// 보더레이아웃
		Panel p_tabel_center_center_east_south_north_north_north = new Panel(new BorderLayout());
		p_tabel_center_center_east_south_north_north.add(p_tabel_center_center_east_south_north_north_north,
				BorderLayout.NORTH);

		// 메인 센터 보더 레이아웃 센터 그리드레이아웃 2열 보더레이아웃 센터 하단 보더레이아웃 상단의 보더레이아웃의 상단 보더레이아웃의 상단의
		// 보더레이아웃의 상단 라벨 (( 랜덤색깔게임
		Panel p_tabel_center_center_east_south_north_north_north_north = new Panel(new BorderLayout());
		p_tabel_center_center_east_south_north_north_north.add(p_tabel_center_center_east_south_north_north_north_north,
				BorderLayout.NORTH);

		Label lb_random_color_game = new Label("랜덤색상게임", Label.CENTER);
		p_tabel_center_center_east_south_north_north_north_north.add(lb_random_color_game);
		lb_random_color_game.setFont(f_subtitle);
		// 메인 센터 보더 레이아웃 센터 그리드레이아웃 2열 보더레이아웃 센터 하단 보더레이아웃 상단의 보더레이아웃의 상단 보더레이아웃의 상단의
		// 보더레이아웃의 센터 센터 (( JSeparator
		Panel p_tabel_center_center_east_south_north_north_north_center = new Panel(new BorderLayout(10, 10));
		p_tabel_center_center_east_south_north_north_north
				.add(p_tabel_center_center_east_south_north_north_north_center, BorderLayout.CENTER);

		JSeparator separator2 = new JSeparator();
		p_tabel_center_center_east_south_north_north_north_center.add(separator2, BorderLayout.CENTER);
		// 메인 센터 보더 레이아웃 센터 그리드레이아웃 2열 보더레이아웃 센터 하단 보더레이아웃 상단의 보더레이아웃의 상단 보더레이아웃의 센터
		// 그리드레이아웃 x행x열 (( 라벨 textArea 랜덤 박스
		Panel p_tabel_center_center_east_south_north_north_center = new Panel(new GridLayout(4, 5));
		p_tabel_center_center_east_south_north_north.add(p_tabel_center_center_east_south_north_north_center,
				BorderLayout.CENTER);

		TextArea bt_t1 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea bt_t2 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea bt_t3 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea bt_t4 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea bt_t5 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea bt_t6 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea bt_t7 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea bt_t8 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea bt_t9 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea bt_t10 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		bt_t1.setEditable(false);
		bt_t2.setEditable(false);
		bt_t3.setEditable(false);
		bt_t4.setEditable(false);
		bt_t5.setEditable(false);
		bt_t6.setEditable(false);
		bt_t7.setEditable(false);
		bt_t8.setEditable(false);
		bt_t9.setEditable(false);
		bt_t10.setEditable(false);
		

		TextArea bt_t11 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea bt_t12 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea bt_t13 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea bt_t14 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea bt_t15 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea bt_t16 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea bt_t17 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea bt_t18 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea bt_t19 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		TextArea bt_t20 = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);
		
		bt_t11.setEditable(false);
		bt_t12.setEditable(false);
		bt_t13.setEditable(false);
		bt_t14.setEditable(false);
		bt_t15.setEditable(false);
		bt_t16.setEditable(false);
		bt_t17.setEditable(false);
		bt_t18.setEditable(false);
		bt_t19.setEditable(false);
		bt_t20.setEditable(false);

		p_tabel_center_center_east_south_north_north_center.add(bt_t1);
		p_tabel_center_center_east_south_north_north_center.add(bt_t11);
		p_tabel_center_center_east_south_north_north_center.add(bt_t2);
		p_tabel_center_center_east_south_north_north_center.add(bt_t12);

		p_tabel_center_center_east_south_north_north_center.add(bt_t3);
		p_tabel_center_center_east_south_north_north_center.add(bt_t13);
		p_tabel_center_center_east_south_north_north_center.add(bt_t4);
		p_tabel_center_center_east_south_north_north_center.add(bt_t14);

		p_tabel_center_center_east_south_north_north_center.add(bt_t5);
		p_tabel_center_center_east_south_north_north_center.add(bt_t15);
		p_tabel_center_center_east_south_north_north_center.add(bt_t6);
		p_tabel_center_center_east_south_north_north_center.add(bt_t16);

		p_tabel_center_center_east_south_north_north_center.add(bt_t7);
		p_tabel_center_center_east_south_north_north_center.add(bt_t17);
		p_tabel_center_center_east_south_north_north_center.add(bt_t8);
		p_tabel_center_center_east_south_north_north_center.add(bt_t18);

		p_tabel_center_center_east_south_north_north_center.add(bt_t9);
		p_tabel_center_center_east_south_north_north_center.add(bt_t19);
		p_tabel_center_center_east_south_north_north_center.add(bt_t10);
		p_tabel_center_center_east_south_north_north_center.add(bt_t20);

		bt_t1.setBackground(Color.BLUE);
		bt_t2.setBackground(Color.BLUE);
		bt_t3.setBackground(Color.BLUE);
		bt_t4.setBackground(Color.BLUE);
		bt_t5.setBackground(Color.BLUE);
		bt_t6.setBackground(Color.BLUE);
		bt_t7.setBackground(Color.BLUE);
		bt_t8.setBackground(Color.BLUE);
		bt_t9.setBackground(Color.BLUE);
		bt_t10.setBackground(Color.BLUE);

		bt_t11.setBackground(Color.white);
		bt_t12.setBackground(Color.white);
		bt_t13.setBackground(Color.white);
		bt_t14.setBackground(Color.white);
		bt_t15.setBackground(Color.white);
		bt_t16.setBackground(Color.white);
		bt_t17.setBackground(Color.white);
		bt_t18.setBackground(Color.white);
		bt_t19.setBackground(Color.white);
		bt_t20.setBackground(Color.white);

		// 메인 센터 보더 레이아웃 센터 그리드레이아웃 2열 보더레이아웃 센터 하단 보더레이아웃 상단 센터 (( CheckboxGruop 2개?
		// 1개? 랜덤번호
		Panel p_tabel_center_center_east_south_center = new Panel(new GridLayout(0, 4));
		p_tabel_center_center_east_south.add(p_tabel_center_center_east_south_center, BorderLayout.CENTER);

		Checkbox cb_random_number = new Checkbox("랜덤번호", cbg_main, false);
		Checkbox cg_picking_random_staff = new Checkbox("랜덤직원뽑기", cbg_main, false);

		Label lb_rr1 = new Label();
		Label lb_rr2 = new Label();

		p_tabel_center_center_east_south_center.add(lb_rr1);
		p_tabel_center_center_east_south_center.add(cb_random_number);
		p_tabel_center_center_east_south_center.add(cg_picking_random_staff);
		p_tabel_center_center_east_south_center.add(lb_rr2);
		// 메인 센터 보더 레이아웃 센터 그리드레이아웃 2열 보더레이아웃 센터 하단 보더레이아웃 하단 (( Button 시작
		Panel p_tabel_center_center_east_south_south = new Panel(new BorderLayout());
		p_tabel_center_center_east_south.add(p_tabel_center_center_east_south_south, BorderLayout.SOUTH);

		Button bt_start = new Button("시작");

		p_tabel_center_center_east_south_south.add(bt_start);

		// ===================2열 끝==============================//

		menuTransitionAction();
	}

	// 화면 구성 크기 Method
	@Override
	public Insets insets() {
		Insets i = new Insets(0, 30, 30, 30);
		return i;
	}

	// 메뉴버튼 전환 액션 Method

	public void menuTransitionAction() {

		// 메뉴총괄표
		bt_salesstatus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				removeAll();
				salesStatus();
				validate();
			}
		});

		// 메뉴별매출
		bt_salesbymenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				removeAll();
				salesByMenu();
				validate();
			}
		});

		// 취소내역
		bt_cancellationdetails.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				removeAll();
				cancellationDetails();
				validate();
			}
		});

		// 인터넷뱅킹
		bt_internet_banking.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				removeAll();
				internetbanking();
				validate();
			}
		});
	
	}
	// 칸 띄워서 정렬 하기 
	// 문자열
		public static String emptySet_String(String text,int length) {
		
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
	  // 숫자형 
	   public static String emptySet_num(String text,int length) {
	      
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
