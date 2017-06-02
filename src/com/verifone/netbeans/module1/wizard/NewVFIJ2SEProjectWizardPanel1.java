/*
 * NewVFIJ2SEProjectWizardPanel1.java
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
package com.verifone.netbeans.module1.wizard;

import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class NewVFIJ2SEProjectWizardPanel1 implements WizardDescriptor.Panel<WizardDescriptor> {

	/**
	 * The visual component that displays this panel. If you need to access the
	 * component from this class, just use getComponent().
	 */
	private NewVFIJ2SEProjectVisualPanel1 component;

	// Get the visual component for the panel. In this template, the component
	// is kept separate. This can be more efficient: if the wizard is created
	// but never displayed, or not all panels are displayed, it is better to
	// create only those which really need to be visible.
	@Override
	public NewVFIJ2SEProjectVisualPanel1 getComponent() {
		if (component == null) {
			component = new NewVFIJ2SEProjectVisualPanel1();
		}
		return component;
	}

	@Override
	public HelpCtx getHelp() {
		// Show no Help button for this panel:
		return HelpCtx.DEFAULT_HELP;
		// If you have context help:
		// return new HelpCtx("help.key.here");
	}

	@Override
	public boolean isValid() {
		// If it is always OK to press Next or Finish, then:
		return true;
		// If it depends on some condition (form filled out...) and
		// this condition changes (last form field filled in...) then
		// use ChangeSupport to implement add/removeChangeListener below.
		// WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
	}

	@Override
	public void addChangeListener(ChangeListener l) {
	}

	@Override
	public void removeChangeListener(ChangeListener l) {
	}

	@Override
	public void readSettings(WizardDescriptor wiz) {
		// use wiz.getProperty to retrieve previous panel state
	}

	@Override
	public void storeSettings(WizardDescriptor wiz) {
		// use wiz.putProperty to remember current panel state
	}

}
