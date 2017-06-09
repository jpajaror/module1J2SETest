/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.verifone.netbeans.module1.ui.wizard;

import java.awt.Component;
import java.util.HashSet;
import java.util.Set;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

/**
 *
 * @author joswill
 */
public class PanelExistingComponent implements WizardDescriptor.Panel<WizardDescriptor>,
		WizardDescriptor.ValidatingPanel<WizardDescriptor> {

	private WizardDescriptor descriptor;
	PanelExistingComponentVisual component;
	private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1); // or can use ChangeSupport in NB 6.0

	@Override
	public Component getComponent() {
		if (component == null) {
			component = new PanelExistingComponentVisual(this);
		}
		return component;
	}

	@Override
	public HelpCtx getHelp() {
		return new HelpCtx(PanelExistingComponent.class.getName());
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

	@Override
	public boolean isValid() {
		getComponent();
		return component.valid(descriptor);
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
	public void validate() throws WizardValidationException {
		getComponent();
		component.validate(descriptor);
	}
}
