package cafePosSystem;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JSeparator;
import java.sql.*;

import java.util.StringTokenizer;

public class CafePosSystem_emp2 extends   Panel {

   Font f_title, f_subtitle;
   Panel p_north, p_north_south, p_center, p_center_west, p_center_east, p_center_east_center, p_center_east_south,
         p_south, p_south_north;
   Label lb_title, lb_searchEmp, lb_viewList, lb_empInfo, lb_viewNo, lb_viewName, lb_viewTel, lb_viewGrade, lb_viewId,
         lb_viewPwd;
   JSeparator js, js2;
   Checkbox cb_searchName, cb_searchGname, cb_searchTel, cb_searchId;
   CheckboxGroup cbg_searchEmp;
   TextField tf_searchEmp, tf_viewNo, tf_viewName, tf_viewTel, tf_viewId, tf_viewPwd ;
   Button bt_searchEmp, bt_searchEmpAll;
   Choice c_viewGrade;
   List li_viewList;
   boolean sw;
   
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
   PopUpWindow puw = new PopUpWindow(frame);
   
   public CafePosSystem_emp2(Frame frame, Connection conn) {
      this.conn=conn;
      this.frame=frame;
      page();
   }

   // 메서드
   // 검색(메서드 ver)
   public void searchEmp() {
      try {
         String inputSearch = tf_searchEmp.getText();
         String temp = "";
         if (!inputSearch.equals("")) {
            // 이름이 체크된 경우
            if (cb_searchName.getState() == true) {
               li_viewList.removeAll();
               sql = "select * from employee where ename like upper(?) or ename like lower(?) order by eno asc";
               PreparedStatement ps = conn.prepareStatement(sql);
               ps.setString(1, "%" + inputSearch + "%");
               ps.setString(2, "%" + inputSearch + "%");
               ResultSet rs = ps.executeQuery();

               while (rs.next()) {
                  eno = rs.getInt("eno");
                  ename = rs.getString("ename");
                  etel = rs.getString("etel");
                  gname = rs.getString("gname");
                  eid = rs.getString("eid");
                  epwd = rs.getString("epwd");
                  for (int i = 0; i < epwd.length(); i++) {
                     temp += "*";
                  }
                  li_viewList.add(emptySet(eno + "", 10) + emptySet_Kor(ename, 15) + emptySet(etel, 30)
                        + emptySet(gname, 20) + emptySet(eid, 25) + temp);
                  temp = "";
               }
               
               sw = false;
               rs.close();
               ps.close();
               // 직급명이 체크된 경우
            } else if (cb_searchGname.getState() == true) {
               li_viewList.removeAll();
               sql = "select * from employee where gname like upper(?) or ename like lower(?) order by eno asc";
               PreparedStatement ps = conn.prepareStatement(sql);
               ps.setString(1, "%" + inputSearch + "%");
               ps.setString(2, "%" + inputSearch + "%");
               ResultSet rs = ps.executeQuery();
               while (rs.next()) {
                  eno = rs.getInt("eno");
                  ename = rs.getString("ename");
                  etel = rs.getString("etel");
                  gname = rs.getString("gname");
                  eid = rs.getString("eid");
                  epwd = rs.getString("epwd");
                  for (int i = 0; i < epwd.length(); i++) {
                     temp += "*";
                  }
                  li_viewList.add(emptySet(eno + "", 10) + emptySet_Kor(ename, 15) + emptySet(etel, 30)
                        + emptySet(gname, 20) + emptySet(eid, 25) + temp);
                  temp = "";
               }
               sw = false;
               rs.close();
               ps.close();
               // 전화번호가 체크된 경우
            } else if (cb_searchTel.getState() == true) {
               li_viewList.removeAll();
               sql = "select * from employee where etel like ? order by eno asc"; 
               PreparedStatement ps = conn.prepareStatement(sql);
               ps.setString(1, "%" + inputSearch + "%");
               ResultSet rs = ps.executeQuery();
               while (rs.next()) {
                  eno = rs.getInt("eno");
                  ename = rs.getString("ename");
                  etel = rs.getString("etel");
                  gname = rs.getString("gname");
                  eid = rs.getString("eid");
                  epwd = rs.getString("epwd");
                  for (int i = 0; i < epwd.length(); i++) {
                     temp += "*";
                  }
                  li_viewList.add(emptySet(eno + "", 10) + emptySet_Kor(ename, 15) + emptySet(etel, 30)
                        + emptySet(gname, 20) + emptySet(eid, 25) + temp);
                  temp = "";
               }
               sw = false;
               rs.close();
               ps.close();
               // Id가 체크된 경우
            } else if (cb_searchId.getState() == true) {
               li_viewList.removeAll();
               sql = "select * from employee where eid like ? order by eno asc";
               PreparedStatement ps = conn.prepareStatement(sql);
               ps.setString(1, "%" + inputSearch + "%");
               ResultSet rs = ps.executeQuery();
               while (rs.next()) {
                  eno = rs.getInt("eno");
                  ename = rs.getString("ename");
                  etel = rs.getString("etel");
                  gname = rs.getString("gname");
                  eid = rs.getString("eid");
                  epwd = rs.getString("epwd");
                  for (int i = 0; i < epwd.length(); i++) {
                     temp += "*";
                  }
                  li_viewList.add(emptySet(eno + "", 10) + emptySet_Kor(ename, 15) + emptySet(etel, 30)
                        + emptySet(gname, 20) + emptySet(eid, 25) + temp);

                  temp = "";
               }
               sw = false;
               rs.close();
               ps.close();
            }
         } else {
            puw.showPopUp("안내메세지", "검색어를 ", "입력해주세요.");
         }

      } catch (Exception e1) {
         e1.printStackTrace();
      }
   }

   // 전체 검색(메서드 ver)
   public void searchEmpAll() {
      try {
         String temp = "";
         li_viewList.removeAll();    
         sql = "select * from employee order by eno asc";
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery();
         while (rs.next()) {
            eno = rs.getInt("eno");
            ename = rs.getString("ename");
            etel = rs.getString("etel");
            gname = rs.getString("gname");
            eid = rs.getString("eid");
            epwd = rs.getString("epwd");
            for (int i = 0; i < epwd.length(); i++) {
               temp += "*";
            }
            li_viewList.add(emptySet(eno + "", 10) + emptySet_Kor(ename, 15) + emptySet(etel, 30)
                  + emptySet(gname, 20) + emptySet(eid, 25) + temp);

            temp = "";
         }
         sw= true;

         rs.close();
         ps.close();
      } catch (Exception e1) {
         e1.printStackTrace();
      }
   }

   // 입력 초기화(메서드 ver)
   public void reset() {
      try {
         this.removeAll();
         page();
         this.validate();
      } catch (Exception e1) {
         e1.printStackTrace();
      }
   }

   // 선택 직원 정보 텍스트 필드 초기화(메서드 ver)
   public void reset2() {
      try {
         tf_viewNo.setText("");
         tf_viewName.setText("");
         tf_viewTel.setText("");
         tf_viewId.setText("");
         tf_viewPwd.setText("");
      } catch (Exception e1) {
         e1.printStackTrace();
      }
   }


   //GUI셋팅(메서드ver)
   public void page() {
         
         // 전체 레이아웃
         // 패널 생성과 동시에 레이아웃 셋팅
         this.setLayout(new BorderLayout(0, 20));
         
          // 프레임위에 패널 덧붙이기

         f_title = new Font("Default Font", Font.BOLD, 15);
         f_subtitle = new Font("Default Font", Font.BOLD, 12);

         /////////////////////////////
         ///////////// 상단/////////////
         /////////////////////////////

         // 패널 생성과 동시에 레이아웃 셋팅
         p_north = new Panel(new BorderLayout(10, 10));
         // p_north 패널을 전체 레이아웃(보더레이아웃)의 북쪽에 붙이기
         this.add(p_north, "North");
         // 라벨 생성과 가운데 정렬후 라벨을 p_north 패널 북쪽에 라벨붙이기
         lb_title = new Label("직원관리", Label.CENTER);
         lb_title.setFont(f_title);
         p_north.add(lb_title, "North");
         // 줄 생성후 줄을 p_north 패널 센터에 줄 붙이기
         js = new JSeparator();
         js.setForeground(Color.PINK);
         p_north.add(js, "Center");
         // 패널 생성과 동시에 레이아웃 셋팅
         p_north_south = new Panel(new FlowLayout());
         // p_north_south패널을 p_north패널 남쪽에 붙이기
         p_north.add(p_north_south, "South");
         // 라벨,체크박스그룹,체크박스,텍스트필드,버튼생성
         lb_searchEmp = new Label("직원검색", Label.CENTER);
         lb_searchEmp.setFont(f_subtitle);
         cbg_searchEmp = new CheckboxGroup();
         cb_searchName = new Checkbox("이름", cbg_searchEmp, true);
         cb_searchGname = new Checkbox("직급명", cbg_searchEmp, false);
         cb_searchTel = new Checkbox("전화번호", cbg_searchEmp, false);
         cb_searchId = new Checkbox("아이디", cbg_searchEmp, false);
         tf_searchEmp = new TextField(20);
         bt_searchEmp = new Button("검색");
         bt_searchEmpAll = new Button("전체검색");

         // 컴포넌트 나란히 놓기
         p_north_south.add(lb_searchEmp);
         p_north_south.add(cb_searchName);
         p_north_south.add(cb_searchGname);
         p_north_south.add(cb_searchTel);
         p_north_south.add(cb_searchId);
         p_north_south.add(tf_searchEmp);
         p_north_south.add(bt_searchEmp);
         p_north_south.add(bt_searchEmpAll);

         /////////////////////////////
         ///////////// 중단/////////////
         /////////////////////////////

         // 패널 생성과 동시에 레이아웃 셋팅
         p_center = new Panel(new GridLayout(1, 2));
         this.add(p_center, "Center");
         p_center_west = new Panel(new BorderLayout(10, 10));
         // p_center_west패널을 p_center패널 왼쪽열에 붙이기
         p_center.add(p_center_west);
         // 라벨 생성과 가운데 정렬후 라벨을 p_center_west패널 북쪽에 붙이기
         lb_viewList = new Label("조회 목록", Label.CENTER);
         lb_viewList.setFont(f_subtitle);
         p_center_west.add(lb_viewList, "North");
         // 스크롤패널과 리스트 생성후 스크롤패널+리스트를 p_center_west패널 센터에 붙이기
         li_viewList = new List();
         p_center_west.add(li_viewList, "Center");

         // 패널 생성과 동시에 레이아웃 셋팅
         p_center_east = new Panel(new BorderLayout(10, 10));
         // p_center_east를 p_center패널 오른쪽열에 붙이기
         p_center.add(p_center_east);
         // 라벨 생성과 가운데 정렬후 라벨을 p_center_east패널 북쪽에 붙이기
         lb_empInfo = new Label("선택 직원 정보", Label.CENTER);
         lb_empInfo.setFont(f_subtitle);
         p_center_east.add(lb_empInfo, "North");
         // 패널 생성과 동시에 레이아웃 셋팅
         p_center_east_center = new Panel(new GridLayout(8, 2, 0, 0));
         // p_center_east_center패널을 p_center_east 패널의 센터에 붙이기
         p_center_east.add(p_center_east_center, "Center");
         // 라벨과 텍스트 필드 생성후 p_center_east_center패널에 라벨과 텍스트필드 붙이기
         lb_viewNo = new Label("직원 번호", Label.CENTER);
         tf_viewNo = new TextField(20);
         lb_viewName = new Label("직원 이름", Label.CENTER);
         tf_viewName = new TextField(20);
         lb_viewTel = new Label("직원 전화번호", Label.CENTER);
         tf_viewTel = new TextField(20);
         lb_viewGrade = new Label("직원 직급", Label.CENTER);
         c_viewGrade = new Choice();
         c_viewGrade.add("BOSS");
         c_viewGrade.add("MANAGER");
         c_viewGrade.add("STAFF");
         c_viewGrade.add("ALBAR");
         lb_viewId = new Label("아이디", Label.CENTER);
         tf_viewId = new TextField(20);
         lb_viewPwd = new Label("비밀번호", Label.CENTER);
         tf_viewPwd = new TextField(20);
         Label lb_temp1=new Label("",Label.CENTER);
         Label lb_temp2=new Label("",Label.CENTER);
         Label lb_temp3=new Label("",Label.CENTER);
         Label lb_temp4=new Label("",Label.CENTER);
         
         tf_viewNo.setEditable(false);
         tf_viewName.setEditable(false);
         tf_viewTel.setEditable(false);
         c_viewGrade.setEnabled(false);
         tf_viewId.setEditable(false);
         tf_viewPwd.setEditable(false);
         
         // 라벨과 텍스트필드를 p_center_east_center패널에 붙이기
         p_center_east_center.add(lb_viewNo);
         p_center_east_center.add(tf_viewNo);
         p_center_east_center.add(lb_viewName);
         p_center_east_center.add(tf_viewName);
         p_center_east_center.add(lb_viewTel);
         p_center_east_center.add(tf_viewTel);
         p_center_east_center.add(lb_viewGrade);
         p_center_east_center.add(c_viewGrade);
         p_center_east_center.add(lb_viewId);
         p_center_east_center.add(tf_viewId);
         p_center_east_center.add(lb_viewPwd);
         p_center_east_center.add(tf_viewPwd);
         p_center_east_center.add(lb_temp1);
         p_center_east_center.add(lb_temp2);
         p_center_east_center.add(lb_temp3);
         p_center_east_center.add(lb_temp4);
        

         // 패널 생성과 동시에 레이아웃 셋팅
         p_center_east_south = new Panel(new FlowLayout(1, 10, 10));
         // p_center_east_south 패널을 p_center_east패널의 남쪽에 붙이기
         p_center_east.add(p_center_east_south, "South");
         // 버튼 생성후 p_center_east_south 패널에 버튼 붙이기


         /////////////////////////////
         ///////////// 하단/////////////
         /////////////////////////////

//         // 패널 생성과 동시에 레이아웃 셋팅
         p_south = new Panel(new BorderLayout(10, 10));
         this.add(p_south, "South");
         // 라벨 생성후 라벨을 p_south의 북쪽에 붙이기
         p_south_north = new Panel(new BorderLayout(10, 10));
         p_south.add(p_south_north, "North");

         js2 = new JSeparator();
         js2.setForeground(Color.PINK);
         p_south_north.add(js2, "Center");
         Label lb_temp5=new Label("",Label.CENTER);
         p_south.add(lb_temp5,"Center");
         Label lb_temp6=new Label("",Label.CENTER);
         p_south.add(lb_temp6,"South");
         
         
         // 이벤트
         // 검색
         bt_searchEmp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               searchEmp();
               reset2();
            }
         });

         // 전체 검색
         bt_searchEmpAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               searchEmpAll();
               reset2();
            }
         });

         // 리스트 선택시 [선택 직원 정보]란에 데이터 출력
         li_viewList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               try {
                  tf_viewName.setEditable(false);
                  tf_viewTel.setEditable(false);
                  c_viewGrade.setEnabled(false);
                  tf_viewId.setEditable(false);
                  tf_viewPwd.setEditable(false);
                  String empInfo = li_viewList.getSelectedItem();
                  empInfo=empInfo.substring(0,4).trim();
                  sql = "select eno,ename,gname,etel,eid,epwd from employee where eno=?";
                  PreparedStatement ps = conn.prepareStatement(sql);
                  ps.setString(1, empInfo);
                  ResultSet rs = ps.executeQuery();
                  while (rs.next()) {
                     tf_viewNo.setText(rs.getString("eno"));                     
                     tf_viewName.setText(rs.getString("ename"));
                     c_viewGrade.select(rs.getString("gname"));                        
                     tf_viewTel.setText(rs.getString("etel"));
                     tf_viewId.setText(rs.getString("eid"));
                     tf_viewPwd.setText(rs.getString("epwd"));
                     tf_viewPwd.setEchoChar('*');
                  }
                                    
                  rs.close();
                  ps.close();

               } catch (Exception e1) {
                  e1.printStackTrace();
               }
            }
         });


//       //체크박스 다른거 선택시 검색창 초기화


         cb_searchName.addItemListener(new ItemListener() {
                  
            @Override
             public void itemStateChanged(ItemEvent e) {
                tf_searchEmp.setText("");
          }
        });
         cb_searchGname.addItemListener(new ItemListener() {
                   
           @Override
             public void itemStateChanged(ItemEvent e) {
                   tf_searchEmp.setText("");
             }
          });
                     
          cb_searchTel.addItemListener(new ItemListener() {
                   
             @Override
             public void itemStateChanged(ItemEvent e) {
                   tf_searchEmp.setText("");
             }
          });
                     
          cb_searchId.addItemListener(new ItemListener() {
                   
             @Override
             public void itemStateChanged(ItemEvent e) {
                   tf_searchEmp.setText("");
             }
          });
         
   }

   // 리스트 구분하는 메서드
   // 문자열
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

   // 숫자,영어
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
   public Insets getInsets() { // 컴퍼넌트와 경계선에 간격을 줄수 있다.
      Insets i = new Insets(10, 30, 30, 30);
      return i;
   }

}
