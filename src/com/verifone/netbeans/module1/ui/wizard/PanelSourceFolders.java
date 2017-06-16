/*
 * PanelSourceFolders.java
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

import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.ChangeSupport;
import org.openide.util.HelpCtx;

public class PanelSourceFolders implements WizardDescriptor.AsynchronousValidatingPanel,
		WizardDescriptor.FinishablePanel {

	private final ChangeSupport changeSupport = new ChangeSupport(this);
	private WizardDescriptor descriptor;
	/**
	 * The visual component that displays this panel. If you need to access the
	 * component from this class, just use getComponent().
	 */
	private PanelSourceFoldersVisual component;

	// Get the visual component for the panel. In this template, the component
	// is kept separate. This can be more efficient: if the wizard is created
	// but never displayed, or not all panels are displayed, it is better to
	// create only those which really need to be visible.
	@Override
	public PanelSourceFoldersVisual getComponent() {
		if (component == null) {
			component = new PanelSourceFoldersVisual(this);
		}
		return component;
	}

	@Override
	public HelpCtx getHelp() {
		// Show no Help button for this panel:
		return new HelpCtx(PanelSourceFoldersVisual.class.getName());
		// If you have context help:
		// return new HelpCtx("help.key.here");
	}

	@Override
	public boolean isValid() {
		// If it is always OK to press Next or Finish, then:
		return this.component.valid(this.descriptor);
		// If it depends on some condition (form filled out...) and
		// this condition changes (last form field filled in...) then
		// use ChangeSupport to implement add/removeChangeListener below.
		// WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
	}

	@Override
	public void addChangeListener(ChangeListener l) {
		changeSupport.addChangeListener(l);
	}

	@Override
	public void removeChangeListener(ChangeListener l) {
		changeSupport.removeChangeListener(l);
	}

	@Override
	public void prepareValidation() {
		this.component.prepareValidation();
	}

	@Override
	public void validate() throws WizardValidationException {
		this.component.validate(this.descriptor);
	}

	@Override
	public void readSettings(Object settings) {
		this.descriptor = (WizardDescriptor) settings;
		this.component.read(this.descriptor);
	}

	@Override
	public void storeSettings(Object settings) {
		this.component.store(this.descriptor);
	}

	@Override
	public boolean isFinishPanel() {
		return true;
	}

	protected final void fireChangeEvent() {
		changeSupport.fireChange();
	}
}
