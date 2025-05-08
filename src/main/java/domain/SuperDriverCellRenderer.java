package domain;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;


public class SuperDriverCellRenderer extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        TableModel tableModelRides = table.getModel();
        
        Ride ride = (Ride) tableModelRides.getValueAt(row, 3);
        Driver driver = ride.getDriver();
        
        if (driver.getPuntuazioa() >= 4) { 
            cellComponent.setBackground(Color.YELLOW);
        } else {
            cellComponent.setBackground(table.getBackground());
        }
        
        return cellComponent;
    }
}
