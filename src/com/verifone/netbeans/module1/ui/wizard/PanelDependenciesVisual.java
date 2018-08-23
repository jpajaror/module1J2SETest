/*
 * PanelDependenciesVisual.java
 * Created by: JP1
 *
 * COPYRIGHT (c) 2017 by JPR, All Rights Reserved.
 *
 *                       N O T I C E
 *
 * Under Federal copyright law, neither the software nor accompanying
 * documentation may be copied, photocopied, reproduced, translated,
 * or reduced to any electronic medium or machine-readable form, in
 * whole or in part, without the prior written consent of JPR,
 * except in the manner described in the documentation.
 */
package com.verifone.netbeans.module1.ui.wizard;

import com.verifone.netbeans.module1.component.ComponentDefinition;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.xml.xpath.XPathExpressionException;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.api.project.ant.AntArtifact;
import org.netbeans.api.project.libraries.Library;
import org.netbeans.api.project.libraries.LibraryManager;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.xml.sax.SAXException;
import org.netbeans.modules.java.api.common.classpath.ClassPathSupport;
import org.netbeans.modules.java.api.common.project.ui.customizer.ClassPathListCellRenderer;
import org.netbeans.modules.java.j2seproject.J2SEProject;
import org.netbeans.modules.java.j2seproject.ui.customizer.J2SEProjectProperties;
import org.netbeans.spi.project.ant.AntArtifactProvider;

/**
 *
 * @author JP1
 */
public class PanelDependenciesVisual extends SettingsPanel {

	private ComponentDefinition panelCompDef;
	private PanelDependencies panel;
	private Map<String, ClassPathSupport.Item> depen;
	/**
	 * Creates new form PanelDependencies
	 */
	public PanelDependenciesVisual(PanelDependencies panel) {
		this.panel=panel;
		initComponents();
		//Fix this ☠
//		ProjectManager.getDefault()
//		TableCellRenderer renderer = ClassPathListCellRenderer
//				.createClassPathTableRenderer(evaluator, projectFolder);
//		jTable1.setDefaultRenderer(columnClass, renderer);
	}

	@Override
	public String getName() {
		return "Step #4";
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Dependent component", "Reference"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

	@Override
	void store(WizardDescriptor settings) {
		settings.putProperty(ComponentDefinition.PRJDEP, depen);
	}

	@Override
	void read(WizardDescriptor settings) {
		//Restart the wizard property if the component changes 😱 😵.
		depen = (Map) settings.getProperty(ComponentDefinition.PRJDEP);
		DefaultTableModel model = (DefaultTableModel)jTable1.getModel();

		//Clean the table ☠ ☢ 😵
		while (model.getRowCount() > 0) model.removeRow(0);

		//If dependency property already exists 😊.
		if (depen != null){
			for (String compDep:depen.keySet()) {
//				String depRef=depen.get(compDep);
//				model.addRow(new Object[]{depRef, compDep});
//
//				ClassPathSupport.Item item = depen.get(compDep);
//				
//				model.addRow(new Object[]{
//					(item.getType()==ClassPathSupport.Item.TYPE_LIBRARY)? "":"",
//					compDep});
			}
			return;
		}

		panelCompDef = (ComponentDefinition) settings.getProperty(ComponentDefinition.CMPDIR);
		File prjDir = (File) settings.getProperty(ComponentDefinition.PRJDIR);
		depen = new HashMap<>();
		try {
			List<String> otherComp=panelCompDef.readComponentDef();
			LibraryManager man = LibraryManager.getDefault();
			ProjectManager proMan=ProjectManager.getDefault();
			File rootDir=prjDir.getParentFile();
			for (String name:otherComp){
				Library lib=man.getLibrary(name);
				if (lib!=null) {
					model.addRow(new Object[]{name, "Library found"});
					depen.put(name, ClassPathSupport.Item.create(lib, null));
					continue;
				}
				File f=new File(rootDir.getAbsoluteFile() + File.separator
					+ name);
				if (f.exists()){
					FileObject projRef=FileUtil.toFileObject(f);
					Project prj=proMan.findProject(projRef);
					if (prj!=null) {
						model.addRow(new Object[]{name, f.getAbsolutePath()});
						AntArtifact aa=((J2SEProject)prj).getLookup().lookup(AntArtifactProvider.class).getBuildArtifacts()[0];
						depen.put(name, ClassPathSupport.Item.create(aa, aa.getArtifactLocations()[0], name));
						continue;
					}
				}
				model.addRow(new Object[]{name, ""});
			}
			
//			jList1.setListData((String[])foundDeps.toArray(new String[0]));
		} catch (IOException|SAXException|XPathExpressionException ex) {
//			Exceptions.printStackTrace(ex);
		}
	}

	@Override
	boolean valid(WizardDescriptor settings) {
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		return true;
	}

	@Override
	void validate(WizardDescriptor settings) throws WizardValidationException {
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
