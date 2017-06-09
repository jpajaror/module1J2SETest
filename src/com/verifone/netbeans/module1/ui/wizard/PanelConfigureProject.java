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
package com.verifone.netbeans.module1.ui.wizard;

import java.util.HashSet;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class PanelConfigureProject implements WizardDescriptor.Panel<WizardDescriptor>,
		WizardDescriptor.ValidatingPanel<WizardDescriptor> {

	/**
	 * The visual component that displays this panel. If you need to access the
	 * component from this class, just use getComponent().
	 */
	private WizardDescriptor descriptor;
	private PanelConfigureProjectVisual component;
	private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1); // or can use ChangeSupport in NB 6.0

	// Get the visual component for the panel. In this template, the component
	// is kept separate. This can be more efficient: if the wizard is created
	// but never displayed, or not all panels are displayed, it is better to
	// create only those which really need to be visible.
	@Override
	public PanelConfigureProjectVisual getComponent() {
		if (component == null) {
			component = new PanelConfigureProjectVisual(this);
		}
		return component;
	}

	@Override
	public HelpCtx getHelp() {
		// Show no Help button for this panel:
		return new HelpCtx(PanelConfigureProject.class.getName());
		// If you have context help:
		// return new HelpCtx("help.key.here");
	}

	@Override
	public boolean isValid() {
		// If it is always OK to press Next or Finish, then:
		getComponent();
		return component.valid(descriptor);
		// If it depends on some condition (form filled out...) and
		// this condition changes (last form field filled in...) then
		// use ChangeSupport to implement add/removeChangeListener below.
		// WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
	}

	@Override
	public void addChangeListener(ChangeListener l) {
		synchronized (listeners) {
			listeners.add(l);
		}
	}

	@Override
	public void removeChangeListener(ChangeListener l) {
		synchronized (listeners) {
			listeners.remove(l);
		}
	}

	@Override
	public void readSettings(WizardDescriptor settings) {
		descriptor = (WizardDescriptor) settings;
		component.read(descriptor);
	}

	@Override
	public void storeSettings(WizardDescriptor settings) {
		WizardDescriptor d = (WizardDescriptor) settings;
		component.store(d);
	}

	protected final void fireChangeEvent() {
		Set<ChangeListener> ls;
		synchronized (listeners) {
			ls = new HashSet<ChangeListener>(listeners);
		}
		ChangeEvent ev = new ChangeEvent(this);
		for (ChangeListener l : ls) {
			l.stateChanged(ev);
		}
	}

	@Override
	public void validate() throws WizardValidationException {
		getComponent();
		component.validate(descriptor);
	}
}
