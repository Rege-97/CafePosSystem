package cafePosSystem;

import java.awt.*;
import java.awt.event.*;

public class Test extends Frame {
    List list;

    public Test() {
   
    	TextArea ta=new TextArea("",1,1,TextArea.SCROLLBARS_NONE);
    	
    	this.add(ta);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }



    public static void main(String[] args) {
        Test t=new Test();
        t.setSize(300,300);
        t.setVisible(true);
    }
}
