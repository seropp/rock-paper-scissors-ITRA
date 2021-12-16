import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.u8.U8_Grids;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public final class Table {


    public final void createTable(String[] movements, Rules rules) {
        int[][] currentRules = rules.positionsForTable(movements.length);
        List<String> list = new ArrayList<>();
        list.add("");
        for (int i = 0; i < movements.length; i++) {
            list.add("PC move: " + movements[i]);
        }
        AsciiTable a = new AsciiTable();

        a.addRule();
        a.addRow(list);
        a.addRule();

        for (int i = 0; i < movements.length; i++) {

            List<String> list1 = new ArrayList<>();
            list1.add("User move: " + movements[i]);
            for (int j = 0; j < movements.length; j++) {
                if (currentRules[i][j] == 0) list1.add("Draw");
                else if (currentRules[i][j] == -1) list1.add("User lose");
                else if (currentRules[i][j] == 1) list1.add("User win");
            }
            a.addRow(list1);

            a.addRule();
        }
        a.getContext().setGrid(U8_Grids.borderDouble());

        a.setTextAlignment(TextAlignment.CENTER);
        a.getContext().setWidth(150);
        String rend = a.render();
        System.out.println(rend);

    }
}
