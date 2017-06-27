/*
 * SettingsPanel.java
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

import javax.swing.JPanel;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;

/**
 *
 * @author JP1
 */
abstract class SettingsPanel extends JPanel {
	abstract void store (WizardDescriptor settings);

	abstract void read (WizardDescriptor settings);

	abstract boolean valid (WizardDescriptor settings);

	abstract void validate (WizardDescriptor settings) throws WizardValidationException;
}
