package test;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JSeparator;
import java.sql.*;

import java.util.StringTokenizer;

public class CafePosSystem_emp extends Panel{
   
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

   
   public CafePosSystem_emp(Frame frame,Connection conn) {      
	   this.frame=frame;
	   this.conn=conn;
	   
      //전체 레이아웃
      //패널 생성과 동시에 레이아웃 셋팅
      
      
      this.setLayout(new BorderLayout(0,20));
      
      
      Font f_title=new Font("Default Font",Font.BOLD,15);
      Font f_subtitle=new Font("Default Font",Font.BOLD,12);
      
      /////////////////////////////
      /////////////상단/////////////
      /////////////////////////////

      //패널 생성과 동시에 레이아웃 셋팅
      Panel p_north=new Panel(new BorderLayout(10,10));
      //p_north 패널을 전체 레이아웃(보더레이아웃)의 북쪽에 붙이기
      this.add(p_north,"North"); 
      //라벨 생성과 가운데 정렬후 라벨을 p_north 패널 북쪽에 라벨붙이기 
      Label lb_title=new Label("직원관리",Label.CENTER);
      lb_title.setFont(f_title);
      p_north.add(lb_title,"North");
      //줄 생성후 줄을 p_north 패널 센터에 줄 붙이기
      JSeparator js=new JSeparator();
      p_north.add(js,"Center");
      //패널 생성과 동시에 레이아웃 셋팅
      Panel p_north_south=new Panel(new FlowLayout());
      //p_north_south패널을 p_north패널 남쪽에 붙이기
      p_north.add(p_north_south,"South");
      //라벨,체크박스그룹,체크박스,텍스트필드,버튼생성
      Label lb_searchEmp=new Label("직원검색",Label.CENTER);
      lb_searchEmp.setFont(f_subtitle);
      CheckboxGroup cbg_searchEmp=new CheckboxGroup();
      Checkbox cb_searchName=new Checkbox("이름",cbg_searchEmp,true);
      Checkbox cb_searchGname=new Checkbox("직급명",cbg_searchEmp,false);
      Checkbox cb_searchTel=new Checkbox("전화번호",cbg_searchEmp,false);
      Checkbox cb_searchId=new Checkbox("아이디",cbg_searchEmp,false);
      TextField tf_searchEmp=new TextField(20);
      Button bt_searchEmp=new Button("검색");
      Button bt_searchEmpAll=new Button("전체검색");
      
      //컴포넌트 나란히 놓기 
      p_north_south.add(lb_searchEmp);
      p_north_south.add(cb_searchName);
      p_north_south.add(cb_searchGname);
      p_north_south.add(cb_searchTel);
      p_north_south.add(cb_searchId);
      p_north_south.add(tf_searchEmp);
      p_north_south.add(bt_searchEmp);
      p_north_south.add(bt_searchEmpAll);
                              
      /////////////////////////////
      /////////////중단/////////////
      /////////////////////////////
      
      //패널 생성과 동시에 레이아웃 셋팅
      Panel p_center=new Panel(new GridLayout(1,2));
      this.add(p_center,"Center");
      Panel p_center_west=new Panel(new BorderLayout(10,10));
      //p_center_west패널을 p_center패널 왼쪽열에 붙이기
      p_center.add(p_center_west);
      //라벨 생성과 가운데 정렬후 라벨을 p_center_west패널 북쪽에 붙이기
      Label lb_viewList=new Label("조회 목록",Label.CENTER);
      lb_viewList.setFont(f_subtitle);
      p_center_west.add(lb_viewList,"North");
      //스크롤패널과 리스트 생성후 스크롤패널+리스트를 p_center_west패널 센터에 붙이기
      List li_viewList=new List();
      p_center_west.add(li_viewList,"Center");
            
      //패널 생성과 동시에 레이아웃 셋팅
      Panel p_center_east=new Panel(new BorderLayout(10,10));
      //p_center_east를 p_center패널 오른쪽열에 붙이기
      p_center.add(p_center_east);
      //라벨 생성과 가운데 정렬후 라벨을 p_center_east패널 북쪽에 붙이기
      Label lb_empInfo=new Label("선택 직원 정보",Label.CENTER);
      lb_empInfo.setFont(f_subtitle);
      p_center_east.add(lb_empInfo,"North");
      //패널 생성과 동시에 레이아웃 셋팅
      Panel p_center_east_center=new Panel(new GridLayout(6,2,0,0));
      //p_center_east_center패널을 p_center_east 패널의 센터에 붙이기
      p_center_east.add(p_center_east_center,"Center");
      //라벨과 텍스트 필드 생성후 p_center_east_center패널에 라벨과 텍스트필드 붙이기
      Label lb_viewNo=new Label("직원 번호",Label.CENTER);
      TextField tf_viewNo=new TextField(20);
      Label lb_viewName=new Label("직원 이름",Label.CENTER);
      TextField tf_viewName=new TextField(20);
      Label lb_viewTel=new Label("직원 전화번호",Label.CENTER);
      TextField tf_viewTel=new TextField(20);
      Label lb_viewGrade=new Label("직원 직급",Label.CENTER);
      Choice c_viewGrade =new Choice();
      c_viewGrade.add("BOSS");
      c_viewGrade.add("MANAGER");
      c_viewGrade.add("STAFF");
      c_viewGrade.add("ALBAR");
      Label lb_viewId=new Label("아이디",Label.CENTER);
      TextField tf_viewId=new TextField(20);
      Label lb_viewPwd=new Label("비밀번호",Label.CENTER);
      TextField tf_viewPwd=new TextField(20);
      
      tf_viewNo.setEditable(false);
      
      //라벨과 텍스트필드를 p_center_east_center패널에 붙이기
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

      //패널 생성과 동시에 레이아웃 셋팅
      Panel p_center_east_south=new Panel(new FlowLayout(1,10,10));
      //p_center_east_south 패널을 p_center_east패널의 남쪽에 붙이기
      p_center_east.add(p_center_east_south,"South");
      //버튼 생성후 p_center_east_south 패널에 버튼 붙이기
      Button bt_changeInfo=new Button("   수정   ");
      Button bt_deleteInfo=new Button("   삭제   ");
      Button bt_showPwd=new Button("   비밀번호확인   ");
      //컴포넌트 나란히 놓기
      p_center_east_south.add(bt_changeInfo);
      p_center_east_south.add(bt_deleteInfo);
      p_center_east_south.add(bt_showPwd);
                        
      /////////////////////////////
      /////////////하단/////////////
      /////////////////////////////

      //패널 생성과 동시에 레이아웃 셋팅
      Panel p_south=new Panel(new BorderLayout(10,10));
      this.add(p_south,"South");
      //라벨 생성후 라벨을 p_south의 북쪽에 붙이기
      Panel p_south_north=new Panel(new BorderLayout(10,10));
      p_south.add(p_south_north,"North");
      
      JSeparator js2=new JSeparator();
      p_south_north.add(js2,"Center");
      Label lb_addEmp=new Label("직원 등록",Label.CENTER);
      lb_addEmp.setFont(f_subtitle);
      p_south_north.add(lb_addEmp,"South");
      
      //패널 생성과 동시에 레이아웃 셋팅
      Panel p_south_center=new Panel(new GridLayout(2,6));
      p_south.add(p_south_center,"Center");
      //라벨과 텍스트 필드 생성
      Label lb_addNo=new Label("직원 번호",Label.CENTER);
      TextField tf_addNo=new TextField(20);
      Label lb_addName=new Label("직원 이름",Label.CENTER);
      TextField tf_addName=new TextField(20);
      Label lb_addTel=new Label("직원 전화번호",Label.CENTER);
      TextField tf_addTel=new TextField(20);
      Label lb_addGrade=new Label("직원 직급",Label.CENTER);
      Choice c_addGrade =new Choice();
      c_addGrade.add("BOSS");
      c_addGrade.add("MANAGER");
      c_addGrade.add("STAFF");
      c_addGrade.add("ALBAR");
      Label lb_addId=new Label("아이디",Label.CENTER);
      TextField tf_addId=new TextField(20);
      Label lb_addPwd=new Label("비밀번호",Label.CENTER);
      TextField tf_addPwd=new TextField(20);
      
      tf_addNo.setEditable(false);
            
      //라벨,텍스트필드 붙이기
      p_south_center.add(lb_addNo);      
      p_south_center.add(lb_addName);
      p_south_center.add(lb_addTel);
      p_south_center.add(lb_addGrade);
      p_south_center.add(lb_addId);
      p_south_center.add(lb_addPwd);
      p_south_center.add(tf_addNo);      
      p_south_center.add(tf_addName);
      p_south_center.add(tf_addTel);
      p_south_center.add(c_addGrade);
      p_south_center.add(tf_addId);
      p_south_center.add(tf_addPwd);
            
      //패널 생성과 동시에 레이아웃 셋팅
      Panel p_south_south=new Panel(new FlowLayout(1,10,10));
      p_south.add(p_south_south,"South");
      //버튼 생성
      Button bt_addInfo=new Button("   등록   ");
      Button bt_reset=new Button("   입력 초기화   ");
      
      p_south_south.add(bt_addInfo);
      p_south_south.add(bt_reset);
            
      //이벤트    
      
      
      //검색
      bt_searchEmp.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try {
               String inputSearch=tf_searchEmp.getText();
               String temp="";
               //이름이 체크된 경우
               if(cb_searchName.getState()==true) {
                  li_viewList.removeAll();
                  sql="select * from employee where ename like upper(?) or ename like lower(?) order by eno asc";
                  PreparedStatement ps=conn.prepareStatement(sql);
                  ps.setString(1,"%"+inputSearch+"%");
                  ps.setString(2,"%"+inputSearch+"%");
                  ResultSet rs=ps.executeQuery();
                  
                  while(rs.next()) {
                     eno=rs.getInt("eno");
                     ename=rs.getString("ename");
                     etel=rs.getString("etel");
                     gname=rs.getString("gname");
                     eid=rs.getString("eid");
                     epwd=rs.getString("epwd");
                     for(int i=0;i<epwd.length();i++) {
                        temp+="*";
                     }
                     li_viewList.add(eno+"  /  "+ename+"  /  "+etel+"  /  "+gname+"  /  "+eid+"  /  "+temp);
                     temp="";
                  }
                  rs.close();
                  ps.close();
               //직급명이 체크된 경우
               }else if(cb_searchGname.getState()==true) {
                  li_viewList.removeAll();
                  sql="select * from employee where gname like upper(?) or ename like lower(?) order by eno asc"; 
                  PreparedStatement ps=conn.prepareStatement(sql);
                  ps.setString(1,"%"+inputSearch+"%");
                  ps.setString(2,"%"+inputSearch+"%");
                  ResultSet rs=ps.executeQuery();
                  while(rs.next()) {
                     eno=rs.getInt("eno");
                     ename=rs.getString("ename");
                     etel=rs.getString("etel");
                     gname=rs.getString("gname");
                     eid=rs.getString("eid");
                     epwd=rs.getString("epwd");
                     for(int i=0;i<epwd.length();i++) {
                        temp+="*";
                     }
                     li_viewList.add(eno+"  /  "+ename+"  /  "+etel+"  /  "+gname+"  /  "+eid+"  /  "+temp);
                     temp="";
                     
                  }
                  rs.close();
                  ps.close();
               //전화번호가 체크된 경우
               }else if(cb_searchTel.getState()==true) {
                  li_viewList.removeAll();
                  sql="select * from employee where etel like ? order by eno asc"; //-만 넣어도 다 뜨는데?
                  PreparedStatement ps=conn.prepareStatement(sql);
                  ps.setString(1,"%"+inputSearch+"%");
                  ResultSet rs=ps.executeQuery();
                  while(rs.next()) {
                     eno=rs.getInt("eno");
                     ename=rs.getString("ename");
                     etel=rs.getString("etel");
                     gname=rs.getString("gname");
                     eid=rs.getString("eid");
                     epwd=rs.getString("epwd");
                     for(int i=0;i<epwd.length();i++) {
                        temp+="*";
                     }
                     li_viewList.add(eno+"  /  "+ename+"  /  "+etel+"  /  "+gname+"  /  "+eid+"  /  "+temp);
                     temp="";
                  }
                  rs.close();
                  ps.close();
               //Id가 체크된 경우
               }else if(cb_searchId.getState()==true) {
                  li_viewList.removeAll();
                  sql="select * from employee where eid like ? order by eno asc";
                  PreparedStatement ps=conn.prepareStatement(sql);
                  ps.setString(1, "%"+inputSearch+"%");
                  ResultSet rs=ps.executeQuery();
                  while(rs.next()) {
                     eno=rs.getInt("eno");
                     ename=rs.getString("ename");
                     etel=rs.getString("etel");
                     gname=rs.getString("gname");
                     eid=rs.getString("eid");
                     epwd=rs.getString("epwd");
                     for(int i=0;i<epwd.length();i++) {
                        temp+="*";
                     }
                     li_viewList.add(eno+"  /  "+ename+"  /  "+etel+"  /  "+gname+"  /  "+eid+"  /  "+temp);
                     temp="";
                  }
                  rs.close();
                  ps.close();
               }            
            }catch(Exception e1) {
               e1.printStackTrace();
            }
         }                  
      });
      //전체 검색 
      bt_searchEmpAll.addActionListener(new ActionListener(){         
         public void actionPerformed(ActionEvent e) {
            try {
               String temp="";
               li_viewList.removeAll();
               sql="select * from employee order by eno asc";
               PreparedStatement ps=conn.prepareStatement(sql);
               ResultSet rs=ps.executeQuery();
               while(rs.next()) {
                  eno=rs.getInt("eno");
                  ename=rs.getString("ename");
                  etel=rs.getString("etel");
                  gname=rs.getString("gname");
                  eid=rs.getString("eid");
                  epwd=rs.getString("epwd");
                  for(int i=0;i<epwd.length();i++) {
                     temp+="*";
                  }
                  li_viewList.add(eno+"  /  "+ename+"  /  "+etel+"  /  "+gname+"  /  "+eid+"  /  "+temp);
                  temp="";
               }            
               rs.close();
               ps.close();
            }catch(Exception e1) {
               e1.printStackTrace();
            }                        
         }      
      });
            
      //리스트 선택시 [선택 직원 정보]란에 데이터 출력
      li_viewList.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               String empInfo=li_viewList.getSelectedItem();
               StringTokenizer str = new StringTokenizer(empInfo,"  /  ");
               for(int i=1;i<7;i++) {
                  switch(i) {
                     case 1:tf_viewNo.setText(str.nextToken());break;
                     case 2:tf_viewName.setText(str.nextToken());break;
                     case 3:tf_viewTel.setText(str.nextToken());break;
                     case 4:c_viewGrade.select(str.nextToken());break;
                     case 5:tf_viewId.setText(str.nextToken());break;
                     case 6:tf_viewPwd.setText(str.nextToken());break;
                  }                  
               }
            }catch(Exception e1) {
               e1.printStackTrace();
            }
         }
      });
            
      //직원 정보 수정 (여기 문제가 많다.체크박스에 따라 나누면 체크박스 바뀌면 창 갱신안됨)
      bt_changeInfo.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               sql="update employee set ename=?,etel=?,gname=?,eid=?,epwd=? where eno=?";
               PreparedStatement ps=conn.prepareStatement(sql);
               ps.setString(1,tf_viewName.getText());
               ps.setString(2,tf_viewTel.getText());
               ps.setString(3,c_viewGrade.getSelectedItem());
               ps.setString(4,tf_viewId.getText());
               ps.setString(5,tf_viewPwd.getText());
               ps.setString(6,tf_viewNo.getText());   
               ps.executeUpdate();
               ps.close();
               
               String inputSearch=tf_searchEmp.getText();
               String temp="";
               if(cb_searchName.getState()==true) {
                  li_viewList.removeAll();
                  sql="select * from employee where ename like upper(?) or ename like lower(?) order by eno asc";
                  ps=conn.prepareStatement(sql);
                  ps.setString(1,"%"+inputSearch+"%");
                  ps.setString(2,"%"+inputSearch+"%");
                  ResultSet rs=ps.executeQuery();
                  
                  while(rs.next()) {
                     eno=rs.getInt("eno");
                     ename=rs.getString("ename");
                     etel=rs.getString("etel");
                     gname=rs.getString("gname");
                     eid=rs.getString("eid");
                     epwd=rs.getString("epwd");
                     for(int i=0;i<epwd.length();i++) {
                        temp+="*";
                     }
                     li_viewList.add(eno+"  /  "+ename+"  /  "+etel+"  /  "+gname+"  /  "+eid+"  /  "+temp);
                     temp="";
                  }
                  rs.close();
                  ps.close();
                  
               }else if(cb_searchGname.getState()==true) {
                  li_viewList.removeAll();
                  sql="select * from employee where gname like upper(?) or ename like lower(?) order by eno asc"; 
                  ps=conn.prepareStatement(sql);
                  ps.setString(1,"%"+inputSearch+"%");
                  ps.setString(2,"%"+inputSearch+"%");
                  ResultSet rs=ps.executeQuery();
                  while(rs.next()) {
                     eno=rs.getInt("eno");
                     ename=rs.getString("ename");
                     etel=rs.getString("etel");
                     gname=rs.getString("gname");
                     eid=rs.getString("eid");
                     epwd=rs.getString("epwd");
                     for(int i=0;i<epwd.length();i++) {
                        temp+="*";
                     }
                     li_viewList.add(eno+"  /  "+ename+"  /  "+etel+"  /  "+gname+"  /  "+eid+"  /  "+temp);
                     temp="";
                     
                  }
                  rs.close();
                  ps.close();
               }else if(cb_searchTel.getState()==true) {
                  li_viewList.removeAll();
                  sql="select * from employee where etel like ? order by eno asc"; //-만 넣어도 다 뜨는데?
                  ps=conn.prepareStatement(sql);
                  ps.setString(1,"%"+inputSearch+"%");
                  ResultSet rs=ps.executeQuery();
                  while(rs.next()) {
                     eno=rs.getInt("eno");
                     ename=rs.getString("ename");
                     etel=rs.getString("etel");
                     gname=rs.getString("gname");
                     eid=rs.getString("eid");
                     epwd=rs.getString("epwd");
                     for(int i=0;i<epwd.length();i++) {
                        temp+="*";
                     }
                     li_viewList.add(eno+"  /  "+ename+"  /  "+etel+"  /  "+gname+"  /  "+eid+"  /  "+temp);
                     temp="";
                  }
                  rs.close();
                  ps.close();
               }else if(cb_searchId.getState()==true) {
                  li_viewList.removeAll();
                  sql="select * from employee where eid like ? order by eno asc";
                  ps=conn.prepareStatement(sql);
                  ps.setString(1, "%"+inputSearch+"%");
                  ResultSet rs=ps.executeQuery();
                  while(rs.next()) {
                     eno=rs.getInt("eno");
                     ename=rs.getString("ename");
                     etel=rs.getString("etel");
                     gname=rs.getString("gname");
                     eid=rs.getString("eid");
                     epwd=rs.getString("epwd");
                     for(int i=0;i<epwd.length();i++) {
                        temp+="*";
                     }
                     li_viewList.add(eno+"  /  "+ename+"  /  "+etel+"  /  "+gname+"  /  "+eid+"  /  "+temp);
                     temp="";
                  }
                  rs.close();
                  ps.close();
               }
            }catch(Exception e1) {
               e1.printStackTrace();
            }
         }
      });
      
      //직원 정보 삭제
      bt_deleteInfo.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               sql="delete from employee where eno=? and ename=? and etel=? and gname=? and eid=? and epwd=?";
               PreparedStatement ps=conn.prepareStatement(sql);
               ps.setString(1, tf_viewNo.getText());
               ps.setString(2,tf_viewName.getText());
               ps.setString(3,tf_viewTel.getText());
               ps.setString(4,c_viewGrade.getSelectedItem());
               ps.setString(5,tf_viewId.getText());
               ps.setString(6,tf_viewPwd.getText());
               ps.executeUpdate();
               ps.close();
            }catch(Exception e1) {
               e1.printStackTrace();
            }
         }
      });
      //비밀번호 확인
      bt_showPwd.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try{
               sql="select epwd from employee where eno=?";
               PreparedStatement ps=conn.prepareStatement(sql);
               ps.setString(1,tf_viewNo.getText());
               ResultSet rs=ps.executeQuery();
               while(rs.next()) {
                  tf_viewPwd.setText(rs.getString("epwd"));
               }
               ps.close();
            }catch(Exception e1) {
               e1.printStackTrace();
            }
         }
      });
      
      //직원 정보 등록
      bt_addInfo.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               sql="insert into employee(eno,ename,etel,gname,eid,epwd) values(sq_employee_eno.nextval,?,?,?,?,?)";
               PreparedStatement ps=conn.prepareStatement(sql);
               ps.setString(1,tf_addName.getText());
               ps.setString(2,tf_addTel.getText());
               ps.setString(3,c_addGrade.getSelectedItem());
               ps.setString(4,(tf_addId.getText()));
               ps.setString(5,tf_addPwd.getText());
               ps.executeUpdate();
            }catch(Exception e1) {
               e1.printStackTrace();
            }
         }
      });
      
      //입력 초기화
      bt_reset.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               tf_addName.setText("");
               tf_addTel.setText("");
               tf_addId.setText("");
               tf_addPwd.setText("");
            }catch(Exception e1){
               e1.printStackTrace();
            }
         }
      });
   }      
      
   @Override
   public Insets getInsets() { //컴퍼넌트와 경계선에 간격을 줄수 있다.
      Insets i=new Insets(10,30,30,30);
      return i;
   }
   
  

}
