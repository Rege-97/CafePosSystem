package cafePosSystem;

import java.awt.*;
import java.awt.event.*;

public class ViewChangeTest extends Frame implements ActionListener {

	MenuBar menubar;
	Menu m_menu;
	MenuItem mi_class_add, mi_student_add, mi_close;
	Panel p_main;

	// main용 컴포넌트
	Label lb_main_title;

	// 반 등록 컴포넌트
	Label lb_class_title, lb_class_name, lb_class_teacher, lb_class_msg;
	TextField tf_class_name, tf_class_teacher;
	Button bt_class_result;

	public ViewChangeTest() {

		menubar = new MenuBar();
		this.setMenuBar(menubar);

		m_menu = new Menu("메뉴");
		menubar.add(m_menu);

		mi_class_add = new MenuItem("반정보 등록하기");
		mi_student_add = new MenuItem("학생정보 등록하기");
		mi_close = new MenuItem("닫기");

		m_menu.add(mi_class_add);
		m_menu.add(mi_student_add);
		m_menu.addSeparator();
		m_menu.add(mi_close);

		p_main = new Panel(new BorderLayout());
		lb_main_title = new Label("학사 관리프로그램 v3.0", Label.CENTER);

		p_main.add(lb_main_title);

		this.add(p_main);

		mi_class_add.addActionListener(this);
		mi_student_add.addActionListener(this);
		mi_close.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();

		if (ob == mi_class_add) {
			this.remove(p_main);
			makeClassAddView();
			this.add(p_main);
			this.validate();
		} else if (ob == mi_student_add) {

		} else if (ob == mi_close) {
			System.exit(0);
		}
		
	}

	public void makeClassAddView() {
		p_main = new Panel(new BorderLayout(5, 5));
		// 상단
		lb_class_title = new Label("반 정보 등록하기", Label.CENTER);
		p_main.add(lb_class_title, "North");

		// 중단
		Panel p_center_temp = new Panel(new GridLayout(2, 2, 10, 10));

		lb_class_name = new Label("반 이름 : ");
		lb_class_teacher = new Label("담당 선생님 : ");
		tf_class_name = new TextField();
		tf_class_teacher = new TextField();

		p_center_temp.add(lb_class_name);
		p_center_temp.add(tf_class_name);
		p_center_temp.add(lb_class_teacher);
		p_center_temp.add(tf_class_teacher);

		p_main.add(p_center_temp, "Center");

		// 하단
		Panel p_south_temp = new Panel(new BorderLayout(5, 5));
		lb_class_msg = new Label("메세지 : ");
		bt_class_result = new Button("등록");
		p_south_temp.add(lb_class_msg, "Center");
		p_south_temp.add(bt_class_result);

		p_main.add(p_south_temp, "South");

	}

	public static void main(String[] args) {
		ViewChangeTest vct = new ViewChangeTest();
		vct.setSize(300, 300);
		vct.setVisible(true);

	}

}
