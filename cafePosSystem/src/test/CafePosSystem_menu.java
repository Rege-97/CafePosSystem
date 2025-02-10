package test;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.JSeparator;

public class CafePosSystem_menu extends Panel{
	
	//공통분모 컴포넌트
	Connection conn;
	String sql;
	PreparedStatement pstmt;
    ResultSet rs;
    Frame frame;
    PopUpWindow puw = new PopUpWindow(frame);
    
	/**메뉴 관련 컴포넌트*/ 
	Button bt_msearch, bt_mview, bt_minsert, bt_mupdate, bt_mdelete, bt_mreset;
	TextField tf_mno, tf_mname, tf_mprice, tf_search, tf_menufield;
	List li_menufield;
	CheckboxGroup cbg_menugroup;
    Checkbox cbg_mno, cbg_mname;
    
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
	
	/**메뉴관리**/
	public CafePosSystem_menu(Frame frame, Connection conn) {
		this.conn = conn;
		this.frame = frame;
		
		//폰트 지정
		Font f_title = new Font("Default Font", Font.BOLD, 15);
		Font f_subtitle = new Font("Default Font", Font.BOLD, 12);
		Font f_menu = new Font("Default Font", Font.TRUETYPE_FONT, 15);
		Font f_cat = new Font("Default Font", Font.TRUETYPE_FONT, 10);
		
		/**메뉴GUI**/ 
		//전체 패널
		
		this.setLayout(new BorderLayout(0,20));
		

		JSeparator js;
		
		//상단 
		Panel p_north = new Panel(new BorderLayout(10,10));
		this.add(p_north,"North");
		
		
		//상단의 상단
		Panel p_north_north = new Panel(new BorderLayout());
		p_north.add(p_north_north,"North");
		
		Label lb_title = new Label("메뉴",Label.CENTER);
		lb_title.setFont(f_title);
		js = new JSeparator();
		
		p_north.add(lb_title,"North");
		p_north.add(js,"Center");
		
		//상단의 하단
		Panel p_north_south = new Panel();
		p_north.add(p_north_south,"South");
		
		Label lb_search = new Label("검색", Label.LEFT);
		lb_search.setFont(f_subtitle);
		p_north_south.add(lb_search); 
		
		cbg_menugroup = new CheckboxGroup();
		cbg_mno = new Checkbox("메뉴번호",cbg_menugroup, false);
		p_north_south.add(cbg_mno); 
		  // (초기값이 true면 둘 다 true로 되지 않으므로 하나만 true)
		cbg_mname = new Checkbox("메뉴이름",cbg_menugroup, true); 
		p_north_south.add(cbg_mname);
		
		tf_search = new TextField(20);
		p_north_south.add(tf_search);
		
		bt_msearch = new Button("검색");
		p_north_south.add(bt_msearch);
		bt_mview = new Button("전체검색");
		p_north_south.add(bt_mview);
		
		//중단
		Panel p_center = new Panel(new BorderLayout(10,10));
		this.add(p_center,"Center");
		
		//중단의 상단
		Label lb_menufield_title = new Label("메뉴 조회",Label.CENTER);
		p_center.add(lb_menufield_title,"North");
		lb_menufield_title.setFont(f_subtitle);
		
		//중단의 중단
		Panel p_center_center = new Panel(new BorderLayout());
		p_center.add(p_center_center,"Center");
		
		tf_menufield = new TextField("메뉴번호 \t\t\t\t\t\t 메뉴명 \t\t\t\t\t 메뉴가격");
		tf_menufield.setEditable(false);
		p_center_center.add(tf_menufield,"North");
		tf_menufield.setFont(f_menu);
		
		li_menufield = new List();
		li_menufield.setFont(f_menu);
		p_center_center.add(li_menufield,"Center");
		
		//하단
		Panel p_south = new Panel(new BorderLayout(10,10));
		this.add(p_south,"South");
		
		//하단의 상단
		Panel p_south_north = new Panel(new BorderLayout(10,10));
		p_south.add(p_south_north,"North");
		
		js = new JSeparator();
		p_south_north.add(js,"North");
		
		Label lb_menuenroll = new Label("메뉴 등록", Label.CENTER);
		p_south_north.add(lb_menuenroll,"South");
		lb_menuenroll.setFont(f_subtitle);
		
		//하단의 중단
		Panel p_south_center = new Panel(new GridLayout(2,2));
		p_south.add(p_south_center,"Center");
		
		Label lb_mno = new Label("메뉴번호",Label.CENTER);
		p_south_center.add(lb_mno);
		Label lb_mname = new Label("메뉴명",Label.CENTER);
		p_south_center.add(lb_mname);
		Label lb_mprice = new Label("가격",Label.CENTER);
		p_south_center.add(lb_mprice);
		
		tf_mno = new TextField();
		p_south_center.add(tf_mno);
		tf_mno.setEditable(false);
		tf_mname = new TextField();
		p_south_center.add(tf_mname);
		tf_mprice = new TextField();
		p_south_center.add(tf_mprice);
		
		//하단의 하단
		Panel p_south_south = new Panel(new FlowLayout(1,10,10));
		p_south.add(p_south_south,"South");
		
		bt_minsert = new Button("등록");
		p_south_south.add(bt_minsert);
		bt_mupdate = new Button("수정");
		p_south_south.add(bt_mupdate);
		bt_mdelete = new Button("삭제");
		p_south_south.add(bt_mdelete);
		bt_mreset = new Button("초기화");
		p_south_south.add(bt_mreset);
		
		/**이벤트**/
		//키 입력
		//검색창
		tf_search.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				Checkbox selected = cbg_menugroup.getSelectedCheckbox();
	            //라디오버튼이 메뉴번호일 때
	            if (selected.getLabel().equals("메뉴번호")) {
	            	if (!(e.getKeyChar() >= 48 && e.getKeyChar() <= 57)) {
						e.consume();
					}
	            } else { //라디오버튼이 메뉴이름일 때
	            	if (e.getKeyChar() >= 48 && e.getKeyChar() <= 57) {
						e.consume();
					}
	            }
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//라디오버튼을 변경했을 때 초기화
		cbg_mno.addItemListener(new ItemListener() {
		    @Override
		    public void itemStateChanged(ItemEvent e) {
		        if (cbg_mno.getState()) { // "메뉴번호"가 선택되었을 때
		        	cbg_mno.setState(true);
		            cbg_mname.setState(false);
		        	tf_search.setText("");  
		        }
		    }
		});

		cbg_mname.addItemListener(new ItemListener() {
		    @Override
		    public void itemStateChanged(ItemEvent e) {
		        if (cbg_mname.getState()) { // "메뉴이름"이 선택되었을 때
		        	cbg_mno.setState(false);
		            cbg_mname.setState(true);
		        	tf_search.setText(""); 
		            
		        }
		    }
		});
		li_menufield.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				String selected = li_menufield.getSelectedItem();
				String[] tokens = selected.split("\\s+");
		        String strMno = tokens[0].trim();
		        String selectedMname = tokens[1].trim();
		        String selectedMprice = tokens[2].trim();
			     
                tf_mno.setText(strMno);
                tf_mname.setText(selectedMname);
                tf_mprice.setText(selectedMprice);
				
			}
		});
		//가격창
		tf_mprice.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				//숫자가 아니면 입력을 막음
				if (!(e.getKeyChar() >= 48 && e.getKeyChar() <= 57)) {
					e.consume(); 
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});
		
		//검색 버튼
		bt_msearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 searchMenu(); 
			}
		});
		
		//전체 검색 버튼
		bt_mview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllMenu(); 
            }
        });
		
		//등록 버튼
		bt_minsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertMenu();
            }
        });
		
		//수정 버튼
		bt_mupdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMenu();
            }
        });
		
		//삭제 버튼
		bt_mdelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMenu();
            }
        });
		
		//초기화 버튼
		bt_mreset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tf_mno.setText("");
				tf_mname.setText("");
	            tf_mprice.setText("");
			}
		});
	}
	 /**리스트 문자정렬**/
	private String formatMenuRow(Object col1, Object col2, Object col3) {
	    // 고정폭 설정
		int width1 = 80, width2 = 80, width3 = 30;
	    
	    // 데이터 -> 문자열 변환
	    String s1 = col1.toString(), s2 = col2.toString(), s3 = col3.toString();
	    
	    //공백 맞추는 알고리즘
	    String row = ""; //시작 
	    row += s1; //문자열 넣기
	    for (int i = s1.length(); i < width1-s1.length(); i++) {
	        row += " ";
	    }
	    row += " "; row += s2;
	    for (int i = s2.length(); i < (width2-s2.length() * 3); i++) {
	        row += " ";
	    }
	    row += " "; row += s3;
	    for (int i = s3.length(); i < width3-s3.length(); i++) {
	        row += " ";
	    }
	    return row;
	}
    
    /**메뉴목록 갱신**/
    private void refreshMenuList() {
        String sql = "SELECT mno, mname, mprice FROM menu ORDER BY mno ASC";
        try (PreparedStatement selectPstmt = conn.prepareStatement(sql);
             ResultSet rs = selectPstmt.executeQuery()) {
            li_menufield.removeAll(); // 리스트 초기화
            while (rs.next()) {
                String row = formatMenuRow(rs.getInt("mno"), rs.getString("mname"), rs.getInt("mprice")); //포맷팅
                li_menufield.add(row); // UI 리스트 추가
            }
        } catch (SQLException ex) {
            puw.showPopUp("메뉴목록 갱신 오류", "오류가 발생하였습니다.", "다시 실행해주세요.");
            ex.printStackTrace();
        }
    }
    
    /**검색**/
    private void searchMenu() {
        String keyword = tf_search.getText().trim();
       
        if (keyword.isEmpty()) {
            puw.showPopUp("입력 오류","검색어를 입력하세요.",null);
            return;
        }
        try {
        	Checkbox selected = cbg_menugroup.getSelectedCheckbox();
            //라디오버튼이 메뉴번호일 때
            if (selected.getLabel().equals("메뉴번호")) {
                int searchmno;
                
                //숫자 입력 오류 처리
                try { searchmno = Integer.parseInt(keyword);
                } catch (NumberFormatException nfe) {
                	puw.showPopUp("입력 오류","메뉴번호는 최대 9자리 이내의","숫자로 입력해주세요.");
                    return;
                }
                //DB 검색
                String sql = "SELECT * FROM menu WHERE mno = ? ORDER BY mno ASC";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, searchmno);
                
            } else { //라디오버튼이 메뉴이름일 때
            	//DB 검색
                String sql = "SELECT * FROM menu WHERE mname LIKE ? ORDER BY mno ASC";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "%" + keyword + "%");
            }
            
            //쿼리 실행
            rs = pstmt.executeQuery();
            li_menufield.removeAll(); // 리스트 초기화
            while (rs.next()) {
                String row = formatMenuRow(rs.getInt("mno"), rs.getString("mname"), rs.getInt("mprice"));
                li_menufield.add(row); 
            }
            rs.close();
            pstmt.close();
            
        } catch (Exception ex) {
        	puw.showPopUp("오류","오류가 발생하였습니다.","다시 실행해주세요.");
            ex.printStackTrace();
        }
    }
    
    /**전체 검색**/
    private void viewAllMenu() {
        try {
        	//DB 전체 검색
        	String sql = "SELECT * FROM menu ORDER BY mno ASC"; 
            pstmt = conn.prepareStatement(sql);
            
            //쿼리 실행
            rs = pstmt.executeQuery();
            li_menufield.removeAll(); // 리스트 초기화
            while (rs.next()) {
                String row = formatMenuRow(rs.getInt("mno"), rs.getString("mname"), rs.getInt("mprice"));
                li_menufield.add(row);
            }
            rs.close();
            pstmt.close();
            
        } catch (Exception ex) {
        	puw.showPopUp("오류","오류가 발생하였습니다.","다시 실행해주세요.");
            ex.printStackTrace();
        }
    }
    
    /**메뉴 등록**/ 
    private void insertMenu() {
        String mname = tf_mname.getText().trim(); // 공미포 문자열 저장
        int mprice;
        //숫자가 아닐 때 예외처리
        try {
            mprice = Integer.parseInt(tf_mprice.getText().trim()); //공미포 숫자 저장
        } catch (NumberFormatException nfe) { 
            puw.showPopUp("입력 오류","가격은 숫자로 입력해야 합니다.",null);
            return;
        }
        //DB 메뉴 등록
        String sql = "INSERT INTO menu (mno, mname, mprice) VALUES (sq_menu_mno.NEXTVAL, ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mname);
            pstmt.setInt(2, mprice);
            pstmt.executeUpdate();
            pstmt.close();
            refreshMenuList();
            puw.showPopUp("등록 완료","메뉴가 등록되었습니다.",null);
            tf_mname.setText("");
            tf_mprice.setText("");
            
        } catch (Exception ex) {
        	puw.showPopUp("오류","오류가 발생하였습니다.","다시 실행해주세요.");
            ex.printStackTrace();
        }
    }
    
    /**메뉴 수정**/
    private void updateMenu() {
    	
        String selected = li_menufield.getSelectedItem();
        if (selected == null) {
        	puw.showPopUp("선택 오류", "먼저 수정할 메뉴를 선택하세요.", null);
            return;
        }
        String[] tokens = selected.split(" ");
        if (tokens.length < 3) {
        	puw.showPopUp("형식 오류","선택된 메뉴 정보의","형식이 잘못되었습니다.");
            return;
        }
        String strMno = tokens[0].trim();
        String selectedMname = tokens[1].trim();
        String selectedMprice = tokens[2].trim();
        
        // 텍스트필드가 비어 있으면 선택된 정보를 먼저 채워 넣음
        if (tf_mname.getText().trim().isEmpty() && tf_mprice.getText().trim().isEmpty()) {
            tf_mname.setText(selectedMname);
            tf_mprice.setText(selectedMprice);
            puw.showPopUp("안내", "수정할 내용을 변경한 후,", "다시 '수정' 버튼을 눌러주세요.");
            return;
        }
        
        int mno;
        try {
            mno = Integer.parseInt(strMno);
        } catch (NumberFormatException nfe) {
            puw.showPopUp("변환 오류", "메뉴 번호 변환 오류가 발생하였습니다.", "번호는 숫자로 입력해주세요.");
            return;
        }
        String newMname = tf_mname.getText().trim();
        int newMprice;
        try {
            newMprice = Integer.parseInt(tf_mprice.getText().trim());
        } catch (NumberFormatException nfe) {
            puw.showPopUp("입력 오류", "가격은 숫자로 입력하세요.", null);
            return;
        }
        
        String sql = "UPDATE menu SET mname = ?, mprice = ? WHERE mno = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newMname);
            pstmt.setInt(2, newMprice);
            pstmt.setInt(3, mno);
            int rowsUpdated = pstmt.executeUpdate();
            pstmt.close();
            if (rowsUpdated > 0) {
                puw.showPopUp("수정 완료", "메뉴가 수정되었습니다.", null);
                refreshMenuList();
            } else {
                puw.showPopUp("수정 오류", "해당 메뉴가 없습니다.", null);
            }
        } catch (SQLException ex) {
            puw.showPopUp("DB 오류", "DB 오류가 발생했습니다.", null);
            ex.printStackTrace();
        }
    }
    
    /**메뉴 삭제**/
    private void deleteMenu() {
        String selected = li_menufield.getSelectedItem();
        if (selected == null) {
            puw.showPopUp("선택 오류", "먼저 삭제할 메뉴를 선택하세요.", null);
            return;
        }
        String[] tokens = selected.split(" ");
        if (tokens.length < 3) {
        	puw.showPopUp("형식 오류","선택된 메뉴 정보의","형식이 잘못되었습니다.");
            return;
        }
        int mno;
        try {
            mno = Integer.parseInt(tokens[0].trim());
        } catch (NumberFormatException nfe) {
        	puw.showPopUp("변환 오류", "메뉴 번호 변환 오류가 발생하였습니다.", "번호는 숫자로 입력해주세요.");
            return;
        }
        String sql = "DELETE FROM menu WHERE mno = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, mno);
            int rowsDeleted = pstmt.executeUpdate();
            pstmt.close();
            if (rowsDeleted > 0) {
                puw.showPopUp("삭제 완료", "메뉴가 삭제되었습니다.", null);
                refreshMenuList();
            } else {
                puw.showPopUp("삭제 오류", "해당 메뉴가 없습니다.", null);
            }
        } catch (SQLException ex) {
        	puw.showPopUp("DB 오류", "DB 오류가 발생했습니다.", null);
            ex.printStackTrace();
        }
    }

	@Override
	public Insets getInsets() {
		Insets i = new Insets(10,30,30,30);
		return i;
	}
	
	
}
