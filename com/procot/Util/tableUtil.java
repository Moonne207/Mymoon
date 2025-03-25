package Util;

import java.util.ArrayList;
import java.util.function.Function;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class tableUtil {

    // Load cột vào JTable
    public static void loadTableColumn(JTable table, String[] columnNames) {
        DefaultTableModel tblModel = (DefaultTableModel) table.getModel();
        tblModel.setColumnIdentifiers(columnNames);
    }

    // Load dữ liệu lên bảng (hỗ trợ custom cột bằng Function)
    public static <T> void loadTableData(JTable table, ArrayList<T> dataList, Function<T, Object[]> rowMapper) {
        DefaultTableModel tblModel = (DefaultTableModel) table.getModel();
        tblModel.setRowCount(0);

        for (T item : dataList) {
            tblModel.addRow(rowMapper.apply(item));
        }
    }
    // Căn lề cho table
    public static void rendererTable(JTable table){
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            {
                setHorizontalAlignment(SwingConstants.CENTER); // Sửa ở (CENTER);
            }
        };
         // Căn lề cho header table
            //tblClass.getTableHeader().setDefaultRenderer(renderer);
        // Cản lề cho row table
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }
}
