/*
 * NewVFIJ2SEProjectWizardIterator.java
 * Created by: JoswillP1
 *
 * COPYRIGHT (c) 2017 by VeriFone Inc., All Rights Reserved.
 *
 *                       N O T I C E
 *
 * Under Federal copyright law, neither the software nor accompanying
 * documentation may be copied, photocopied, reproduced, translated,
 * or reduced to any electronic medium or machine-readable form, in
 * whole or in part, without the prior written consent of VeriFone Inc.,
 * except in the manner described in the documentation.
 */
package com.verifone.netbeans.module1.ui.wizard;

import java.awt.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.templates.TemplateRegistration;
import org.openide.WizardDescriptor;
import org.openide.util.NbBundle;

@TemplateRegistration(folder = "Project/Verifone",
					  position=100,
					  displayName = "#template_existing",
					  description = "../resources/existingProject.html",
					  iconBase = "com/verifone/netbeans/module1/ui/resources/VFIIcon.png")
@NbBundle.Messages("template_existing=Verifone Project from existing component")
public final class NewVFIJ2SEProjectWizardIterator
		implements WizardDescriptor.ProgressInstantiatingIterator<WizardDescriptor> {

	// Example of invoking this wizard:
	// @ActionID(category="...", id="...")
	// @ActionRegistration(displayName="...")
	// @ActionReference(path="Menu/...")
	// public static ActionListener run() {
	//     return new ActionListener() {
	//         @Override public void actionPerformed(ActionEvent e) {
	//             WizardDescriptor wiz = new WizardDescriptor(new NewVFIJ2SEProjectWizardIterator());
	//             // {0} will be replaced by WizardDescriptor.Panel.getComponent().getName()
	//             // {1} will be replaced by WizardDescriptor.Iterator.name()
	//             wiz.setTitleFormat(new MessageFormat("{0} ({1})"));
	//             wiz.setTitle("...dialog title...");
	//             if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
	//                 ...do something...
	//             }
	//         }
	//     };
	// }
	private int index;

	private List<WizardDescriptor.Panel<WizardDescriptor>> panels;

	private List<WizardDescriptor.Panel<WizardDescriptor>> getPanels() {
		if (panels == null) {
			panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
			panels.add(new NewVFIJ2SEProjectWizardPanel1());
			panels.add(new NewVFIJ2SEProjectWizardPanel2());
			String[] steps = new String[panels.size()];
			for (int i = 0; i < panels.size(); i++) {
				Component c = panels.get(i).getComponent();
				// Default step name to component name of panel.
				steps[i] = c.getName();
				if (c instanceof JComponent) { // assume Swing components
					JComponent jc = (JComponent) c;
					jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
					jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
					jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
					jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
					jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
				}
			}
		}
		return panels;
	}

	@Override
	public WizardDescriptor.Panel<WizardDescriptor> current() {
		return getPanels().get(index);
	}

	@Override
	public String name() {
		return index + 1 + ". from " + getPanels().size();
	}

	@Override
	public boolean hasNext() {
		return index < getPanels().size() - 1;
	}

	@Override
	public boolean hasPrevious() {
		return index > 0;
	}

	@Override
	public void nextPanel() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		index++;
	}

	@Override
	public void previousPanel() {
		if (!hasPrevious()) {
			throw new NoSuchElementException();
		}
		index--;
	}

	// If nothing unusual changes in the middle of the wizard, simply:
	@Override
	public void addChangeListener(ChangeListener l) {
	}

	@Override
	public void removeChangeListener(ChangeListener l) {
	}
	// If something changes dynamically (besides moving between panels), e.g.
	// the number of panels changes in response to user input, then use
	// ChangeSupport to implement add/removeChangeListener and call fireChange
	// when needed

	@Override
	public Set instantiate(ProgressHandle handle) throws IOException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Set instantiate() throws IOException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void initialize(WizardDescriptor wizard) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void uninitialize(WizardDescriptor wizard) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
