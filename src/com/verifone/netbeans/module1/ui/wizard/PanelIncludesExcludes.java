/*
 * PanelIncludesExcludes.java
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

import java.awt.Component;
import java.io.File;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.java.api.common.project.ProjectProperties;
import org.netbeans.spi.java.project.support.ui.IncludeExcludeVisualizer;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

/**
 *
 * @author JP1
 */
public class PanelIncludesExcludes implements WizardDescriptor.Panel {
	private final IncludeExcludeVisualizer viz;

	public PanelIncludesExcludes() {
		viz = new IncludeExcludeVisualizer();
	}

	@Override
	public Component getComponent() {
		return viz.getVisualizerPanel();
	}

	@Override
	public HelpCtx getHelp() {
		return new HelpCtx(PanelIncludesExcludes.class);
	}

	@Override
	public void readSettings(Object settings) {
		WizardDescriptor w = (WizardDescriptor) settings;
		String includes = (String) w.getProperty(ProjectProperties.INCLUDES);
		if (includes == null) {
			includes = "**"; // NOI18N
		}
		viz.setIncludePattern(includes);

		String excludes = (String) w.getProperty(ProjectProperties.EXCLUDES);
		if (excludes == null) {
			excludes = ""; // NOI18N
		}
		viz.setExcludePattern(excludes);

		File[] sourceRoots = (File[]) w.getProperty("sourceRoot");
		File[] testRoots = (File[]) w.getProperty("testRoot");
		File[] roots = new File[sourceRoots.length + testRoots.length];
		System.arraycopy(sourceRoots, 0, roots, 0, sourceRoots.length);
		System.arraycopy(testRoots, 0, roots, sourceRoots.length, testRoots.length);
		viz.setRoots(roots);
	}

	@Override
	public void storeSettings(Object settings) {
		WizardDescriptor w = (WizardDescriptor) settings;
		w.putProperty(ProjectProperties.INCLUDES, viz.getIncludePattern());
		w.putProperty(ProjectProperties.EXCLUDES, viz.getExcludePattern());
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void addChangeListener(ChangeListener l) { }

	@Override
	public void removeChangeListener(ChangeListener l) { }
}
