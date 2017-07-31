/*
 * NewVFIJ2SEProjectWizardIterator.java
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
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.api.project.libraries.Library;
import org.netbeans.api.templates.TemplateRegistration;
import org.netbeans.modules.java.api.common.classpath.ClassPathSupport;
import org.netbeans.modules.java.api.common.project.ProjectProperties;
import org.netbeans.modules.java.j2seproject.J2SEProject;
import org.netbeans.modules.java.j2seproject.api.J2SEProjectBuilder;
import org.netbeans.modules.java.j2seproject.ui.customizer.J2SEProjectProperties;
import org.netbeans.spi.java.project.support.ui.SharableLibrariesUtils;
import org.netbeans.spi.project.support.ant.AntProjectHelper;
import org.netbeans.spi.project.support.ant.EditableProperties;
import org.netbeans.spi.project.support.ant.ReferenceHelper;
import org.netbeans.spi.project.ui.support.ProjectChooser;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle;

@TemplateRegistration(folder = "Project/VF",
//					  position=10,
					  displayName = "#newVFI_existing",
					  description = "../resources/existingProject.html",
					  iconBase = "com/verifone/netbeans/module1/ui/resources/VFIIcon.png")
@NbBundle.Messages("newVFI_existing=VF Project from existing component")
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
	private transient volatile WizardDescriptor wiz;

	static final String PROP_DIST_FOLDER = "distFolder";				//NOI18N
	private static final String MANIFEST_FILE = "manifest.mf";			// NOI18N

	public NewVFIJ2SEProjectWizardIterator(){
	}

	@Override
	public void initialize(WizardDescriptor wizard) {
		this.wiz=wizard;
		index=0;
		panels = createPanels();
		String[] steps = createSteps();
		for (int i = 0; i < panels.size(); i++) {
			Component c = panels.get(i).getComponent();
			if (steps[i] == null){
				steps[i] = c.getName();
			}
			//Check if is really needed if no swing components delete
			if (c instanceof JComponent) {
				JComponent jc = (JComponent)c;
				jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
				jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
			}
		}
		//set the default values of the sourceRoot and the testRoot properties
		this.wiz.putProperty(ComponentDefinition.SRCROT, new File[0]);
		this.wiz.putProperty(ComponentDefinition.TSTROT, new File[0]);
	}

	private List<WizardDescriptor.Panel<WizardDescriptor>> createPanels() {
		panels = new ArrayList<>();
		panels.add(new PanelConfigureProject());
		panels.add(new PanelSourceFolders());
		panels.add(new PanelIncludesExcludes());
		panels.add(new PanelDependencies());

		return panels;
	}

	private String[] createSteps() {
		return new String[] {
			NbBundle.getMessage(NewVFIJ2SEProjectWizardIterator.class,"LBL.ConfigureProject"),
			NbBundle.getMessage(NewVFIJ2SEProjectWizardIterator.class,"LBL.ConfigureSourceRoots"),
			NbBundle.getMessage(NewVFIJ2SEProjectWizardIterator.class,"LBL.PanelIncludeExcludes"),
			NbBundle.getMessage(NewVFIJ2SEProjectWizardIterator.class,"LBL.PanelDependencies"),
		};
	}

	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	@Override
	public WizardDescriptor.Panel<WizardDescriptor> current() {
		return panels.get(index);
	}

	@Override
	public String name() {
		return NbBundle.getMessage(NewVFIJ2SEProjectWizardIterator.class, "LBL.M_OF_N",
				index + 1, panels.size());
	}

	@Override
	public boolean hasNext() {
		return index < panels.size() - 1;
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

	@Override
	public void addChangeListener(ChangeListener l) {
	}

	@Override
	public void removeChangeListener(ChangeListener l) {
	}

	@Override
	public void uninitialize(WizardDescriptor wizard) {
		this.wiz = null;
		this.panels = null;
	}

	@Override
	public Set instantiate() throws IOException {
		assert false : "Cannot call this method if implements WizardDescriptor.ProgressInstantiatingIterator.";
		return null;
	}//</editor-fold>

	@Override
	public Set<FileObject> instantiate(ProgressHandle handle) throws IOException {
		final WizardDescriptor myWiz = this.wiz;
		if (myWiz == null) {
			return Collections.emptySet();
		}
		handle.start(4);

		Set<FileObject> resultSet = new HashSet<>();
		File dirF = (File)myWiz.getProperty(ComponentDefinition.PRJDIR);
		if (dirF == null) {
			throw new NullPointerException ("projdir == null, props:" + myWiz.getProperties());
		}
		dirF = FileUtil.normalizeFile(dirF);
		String name = (String)myWiz.getProperty(ComponentDefinition.NAME);
		handle.progress(NbBundle.getMessage (NewVFIJ2SEProjectWizardIterator.class,
				"LBL.NewJ2SEProjectWizardIterator_WizardProgress_CreatingProject"), 1);

		//Creating the project with the dirs
		File[] sourceFolders = (File[])myWiz.getProperty(ComponentDefinition.SRCROT);
		File[] testFolders = (File[])myWiz.getProperty(ComponentDefinition.TSTROT);
		String distFolder = (String) myWiz.getProperty(PROP_DIST_FOLDER);
		Map<String, ClassPathSupport.Item> depen = (Map) myWiz.getProperty(ComponentDefinition.PRJDEP);
		List<Library> libs = new ArrayList<>();
		List<ClassPathSupport.Item> prjs = new ArrayList<>();

		ProjectManager proMan=ProjectManager.getDefault();
		if (depen != null) depen.keySet().forEach((compDep) -> {
			ClassPathSupport.Item item=depen.get(compDep);
			switch(item.getType()) {
				case ClassPathSupport.Item.TYPE_LIBRARY:
					libs.add(item.getLibrary());
					break;
				case ClassPathSupport.Item.TYPE_ARTIFACT:
					prjs.add(item);
					break;
				default:
					break;
			}
		});

		AntProjectHelper h = createProject(dirF, name, sourceFolders,
				testFolders, distFolder, libs.toArray(new Library[0]));

		handle.progress(NbBundle.getMessage (NewVFIJ2SEProjectWizardIterator.class,
				"LBL.NewJ2SEProjectWizardIterator_WizardProgress_SettingProps"),2);

		//classpath
		List<String> refs = new ArrayList<>();
		J2SEProject compProj = (J2SEProject) proMan.findProject(h.getProjectDirectory());
		for (ClassPathSupport.Item item:prjs){
			if (item != null) {
				ReferenceHelper rh = compProj.getReferenceHelper();
				String refStr = rh.addReference(item.getArtifact(), item.getArtifactURI());
				if (refStr!=null){
					refs.add(refStr + ":");
				}
			}
		}

		writeProperties(h, myWiz);
		writePrivateProperties(h, myWiz);

		handle.progress (3);
		for (File f : sourceFolders) {
			FileObject srcFo = FileUtil.toFileObject(f);
			if (srcFo != null) {
				resultSet.add (srcFo);
			}
		}

		EditableProperties ep = h.getProperties(AntProjectHelper.PROJECT_PROPERTIES_PATH);
		ep.setProperty("run.test.classpath", new String[] { // NOI18N
			ref(ProjectProperties.BUILD_TEST_CLASSES_DIR, false),
			ref(ProjectProperties.JAVAC_TEST_CLASSPATH, true)
		});
		
		ep.setProperty("javac.test.classpath", new String[] { // NOI18N
			ref(ProjectProperties.BUILD_CLASSES_DIR, false),
			ref(ProjectProperties.JAVAC_CLASSPATH, false),
			ref("libs.isdMWare.java.apis.apache.junit.classpath", false),// NOI18N
			ref("libs.hamcrest.classpath", true)// NOI18N
		});

		String oldJavaCp=ep.getProperty(ProjectProperties.JAVAC_CLASSPATH);
		if (oldJavaCp == null || oldJavaCp.isEmpty()) {
			String t = refs.remove(refs.size() - 1);
			t = t.substring(0, t.length() - 1);
			refs.add(t);
		} else {
			refs.add(oldJavaCp);
		}
		ep.setProperty(ProjectProperties.JAVAC_CLASSPATH, refs.toArray(new String[0]));

		h.putProperties(AntProjectHelper.PROJECT_PROPERTIES_PATH, ep);

		handle.progress (NbBundle.getMessage (NewVFIJ2SEProjectWizardIterator.class,
				"LBL.NewJ2SEProjectWizardIterator_WizardProgress_PreparingToOpen"), 4);
		resultSet.add(FileUtil.toFileObject(dirF)); //Open project

		File parent = (dirF != null) ? dirF.getParentFile() : null;
		if (parent != null && parent.exists()) {
			ProjectChooser.setProjectsFolder (parent);
		}

		SharableLibrariesUtils.setLastProjectSharable(false);
		return resultSet;
	}

	/**
	 * Creates reference to property.
	 * @param propertyName the name of property
	 * @param lastEntry if true, the path separator is not added
	 * @return the reference
	 */
	public static String ref(@NonNull final String propertyName,
			final boolean lastEntry) {
		return String.format(
				"${%s}%s",  //NOI18N
				propertyName,
				lastEntry ? "" : ":");  //NOI18N
	}

	private AntProjectHelper createProject(File dirF, String name, File[] sourceFolders,
			File[] testFolders, String distFolder, Library... libs) throws IOException{
		return new J2SEProjectBuilder(dirF, name)
			.skipTests(true)
			.addSourceRoots(sourceFolders)
			.addTestRoots(testFolders)
			.setManifest(MANIFEST_FILE)
			.addCompileLibraries(libs)
			.setDistFolder(distFolder)
			.build();
	}

	private void writeProperties(AntProjectHelper h, WizardDescriptor wiz) {
		EditableProperties ep = h.getProperties(AntProjectHelper.PROJECT_PROPERTIES_PATH);
		ep.setProperty(ProjectProperties.DO_DEPEND, "false");// NOI18N

		ep.setProperty(ProjectProperties.NO_DEPENDENCIES, "true");// NOI18N
		ep.setProperty(J2SEProjectProperties.MKDIST_DISABLED, "true");// NOI18N

		//Include & excludes
		String includes = (String) wiz.getProperty(ProjectProperties.INCLUDES);
		if (includes == null) {
			includes = "**"; // NOI18N
		}
		ep.setProperty(ProjectProperties.INCLUDES, includes);
		String excludes = (String) wiz.getProperty(ProjectProperties.EXCLUDES);
		if (excludes == null) {
			excludes = ""; // NOI18N
		}
		ep.setProperty(ProjectProperties.EXCLUDES, excludes);

		ep.setProperty(ProjectProperties.SOURCE_ENCODING, "UTF-8");// NOI18N

		h.putProperties(AntProjectHelper.PROJECT_PROPERTIES_PATH, ep);
	}

	private void writePrivateProperties(AntProjectHelper h, WizardDescriptor wiz) {
		EditableProperties ep = h.getProperties(AntProjectHelper.PRIVATE_PROPERTIES_PATH);
		//write private properties here
		Boolean buildJar = (Boolean) wiz.getProperty(ComponentDefinition.BLDJAR);
		ep.setProperty(ProjectProperties.DO_JAR, buildJar.toString());

		h.putProperties(AntProjectHelper.PRIVATE_PROPERTIES_PATH, ep);
	}
}
