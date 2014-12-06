/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlparser.view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Tom
 */
public class ComboBoxData implements ComboBoxModel {
    
    private List<String> itemList;
    private Object selected;
    
    public ComboBoxData(ArrayList<String> data) {
        
        this.itemList = data;
        
    }
    
    @Override
    public void setSelectedItem(Object anItem) {
        this.selected = anItem;
    }

    @Override
    public Object getSelectedItem() {
        return this.selected;
    }

    @Override
    public int getSize() {
        
        return this.itemList.size();
    }

    @Override
    public Object getElementAt(int index) {
        
        return this.itemList.get(index);
        
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
    }
    
}
