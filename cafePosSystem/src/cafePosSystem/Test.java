package cafePosSystem;

import java.awt.*;
import java.awt.event.*;

public class Test extends Frame {
    List list;

    public Test() {
        setLayout(new FlowLayout());

        list = new List(12);

        // 데이터 항목 추가
        addMenuItem("01", "아메리카노", "4,000원");
        addMenuItem("02", "카페라떼", "4,500원");
        addMenuItem("03", "카푸치노", "4,500원");
        addMenuItem("04", "바닐라라떼", "5,000원");
        addMenuItem("05", "카페모카", "5,000원");
        addMenuItem("06", "카라멜 마끼아토", "5,500원");
        addMenuItem("07", "에스프레소", "5,500원");
        addMenuItem("08", "바닐라라떼", "5,000원");
        addMenuItem("09", "바닐라라떼", "5,000원");
        addMenuItem("10", "카페모카", "5,000원");
        addMenuItem("11", "카페모카", "5,000원");
        addMenuItem("12", "", "5,000원");

        add(list);

        setTitle("AWT List with Aligned Data");
        setSize(400, 400);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    private void addMenuItem(String index, String name, String price) {
        String formattedItem = String.format("%-4s - %-30s %10s", index, name, price);
        list.add(formattedItem);
    }

    public static void main(String[] args) {
        new Test();
    }
}
