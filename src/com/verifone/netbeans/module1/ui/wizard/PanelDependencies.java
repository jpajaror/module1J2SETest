/*
 * PanelDependencies.java
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
import org.openide.util.ChangeSupport;
import org.openide.util.HelpCtx;

/**
 *
 * @author JoswillP1
 */
public class PanelDependencies implements WizardDescriptor.FinishablePanel {

	private PanelDependenciesVisual component;
	private WizardDescriptor descriptor;
	private final ChangeSupport changeSupport = new ChangeSupport(this);

	@Override
	public boolean isFinishPanel() {
		return true;
	}

	@Override
	public PanelDependenciesVisual getComponent() {
		if (component == null) {
			component = new PanelDependenciesVisual(this);
		}
		return component;
	}

	@Override
	public HelpCtx getHelp() {
		return new HelpCtx(PanelDependencies.class.getName());
	}

	@Override
	public void readSettings(Object settings) {
		descriptor = (WizardDescriptor) settings;
		component.read(descriptor);
	}

	@Override
	public void storeSettings(Object settings) {
		descriptor = (WizardDescriptor) settings;
		component.store(descriptor);
	}

	@Override
	public boolean isValid() {
		getComponent();
		return component.valid(descriptor);
	}

	@Override
	public void addChangeListener(ChangeListener l) {
		changeSupport.addChangeListener(l);
	}

	@Override
	public void removeChangeListener(ChangeListener l) {
		changeSupport.removeChangeListener(l);
	}
	
}
