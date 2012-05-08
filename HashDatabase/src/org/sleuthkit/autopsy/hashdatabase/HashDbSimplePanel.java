/*
 * Autopsy Forensic Browser
 * 
 * Copyright 2011 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * HashDbSimplePanel.java
 *
 * Created on May 7, 2012, 10:38:26 AM
 */
package org.sleuthkit.autopsy.hashdatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import org.sleuthkit.autopsy.hashdatabase.HashDb.DBType;

/**
 *
 * @author dfickling
 */
public class HashDbSimplePanel extends javax.swing.JPanel {
    
    private static final Logger logger = Logger.getLogger(HashDbSimplePanel.class.getName());
    private HashTableModel notableTableModel;
    private HashTableModel nsrlTableModel;
    private static HashDbSimplePanel instance;

    /** Creates new form HashDbSimplePanel */
    private HashDbSimplePanel() {
        notableTableModel = new HashTableModel();
        nsrlTableModel = new HashTableModel();
        initComponents();
        customizeComponents();
    }
    
    public static HashDbSimplePanel getDefault() {
        if(instance == null) {
            instance = new HashDbSimplePanel();
        }
        return instance;
    }
    
    private void customizeComponents() {
        notableHashTable.setModel(notableTableModel);
        
        notableHashTable.setTableHeader(null);
        notableHashTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //customize column witdhs
        final int width1 = jScrollPane1.getPreferredSize().width;
        TableColumn column1 = null;
        for (int i = 0; i < notableHashTable.getColumnCount(); i++) {
            column1 = notableHashTable.getColumnModel().getColumn(i);
            if (i == 0) {
                column1.setPreferredWidth(((int) (width1 * 0.15)));
            } else {
                column1.setPreferredWidth(((int) (width1 * 0.84)));
            }
        }
        
        nsrlHashTable.setModel(nsrlTableModel);
        
        nsrlHashTable.setTableHeader(null);
        nsrlHashTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //customize column witdhs
        final int width2 = jScrollPane1.getPreferredSize().width;
        TableColumn column2 = null;
        for (int i = 0; i < nsrlHashTable.getColumnCount(); i++) {
            column2 = nsrlHashTable.getColumnModel().getColumn(i);
            if (i == 0) {
                column2.setPreferredWidth(((int) (width2 * 0.15)));
            } else {
                column2.setPreferredWidth(((int) (width2 * 0.84)));
            }
        }
        reloadLists();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        notableHashTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        nsrlHashTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jScrollPane1.setViewportView(notableHashTable);

        jScrollPane2.setViewportView(nsrlHashTable);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(HashDbSimplePanel.class, "HashDbSimplePanel.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(HashDbSimplePanel.class, "HashDbSimplePanel.jLabel2.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(154, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(142, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable notableHashTable;
    private javax.swing.JTable nsrlHashTable;
    // End of variables declaration//GEN-END:variables

    private void reloadLists() {
        nsrlTableModel.resync(DBType.NSRL);
        notableTableModel.resync(DBType.NOTABLE);
    }

    private class HashTableModel extends AbstractTableModel {
        
        private List<HashDb> data = new ArrayList<HashDb>();
        
        private void resync(DBType type) {
            data.clear();
            data.addAll(HashDbXML.getCurrent().getSets(type));
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            HashDb db = data.get(rowIndex);
            if(columnIndex == 0) {
                return db.getUseForIngest();
            } else {
                return db.getName();
            }
        }
        
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 0;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if(columnIndex == 0){
                HashDb db = data.get(rowIndex);
                HashDbXML.getCurrent().addSet(new HashDb(db.getName(), db.getType(), db.getDatabasePaths(), (Boolean) aValue));
                reloadLists();
            }
        }
        
        @Override
        public Class<?> getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
        
    }
}
