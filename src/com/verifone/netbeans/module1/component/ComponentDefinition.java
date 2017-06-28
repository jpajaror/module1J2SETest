/*
 * ComponentDefinition.java
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
package com.verifone.netbeans.module1.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.openide.filesystems.FileUtil;
import org.openide.xml.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author JP1
 */
public class ComponentDefinition {
	public static String PRJDIR="projdir";			//NOI18N
	public static String CMPDIR="compdir";			//NOI18N
	public static String NAME="name";				//NOI18N
	public static String SRCROT="sourceRoot";		//NOI18N
	public static String TSTROT="testRoot";			//NOI18N
	public static String BLDJAR="buildJAR";			//NOI18N

	private String compDirStr;
	private File compDirF;
	private File compDefF;
	private File srcDir, unitDir;
	private Document xmlDoc;

	public ComponentDefinition(String dir) throws IOException {
		compDirF=new File(dir);
		if (!compDirF.exists() || !compDirF.isDirectory()){
			throw new IOException("Invalid component directory");
		}
		compDirStr=compDirF.getAbsolutePath();
	}

	public ComponentDefinition(File dir) throws IOException {
		compDirF = dir;
		if (!compDirF.exists() || !compDirF.isDirectory()){
			throw new IOException("Invalid component directory");
		}
		compDirStr=compDirF.getAbsolutePath();
	}

	public boolean validateComponentDef(){
		String compDefFileStr = compDirStr + File.separator + "componentDef.xml";//NOI18N
		compDefF = new File(compDefFileStr).getAbsoluteFile();
		compDefF = FileUtil.normalizeFile(compDefF);
		if (compDefF.length() == 0 || compDefF.isDirectory()) {
			return false;
		}
		return true;
	}

	public List<String> readComponentDef() throws IOException, SAXException,
			XPathExpressionException {
		List<String> dependencies= new ArrayList<>();

		if (xmlDoc == null) {
			xmlDoc = XMLUtil.parse(new InputSource(new FileInputStream(compDefF)),
					false, false, null, null);
		}
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodes = (NodeList) xPath.evaluate("/componentDef/componentDef",//NOI18N
				xmlDoc, XPathConstants.NODESET);
		for (int i = 0; i < nodes.getLength(); i++) {
			Element elem = (Element) nodes.item(i);
			String name=elem.getAttribute("name").substring(1)
					.replaceAll("/", ".");
			dependencies.add(name);
		}

		return dependencies;
	}

	public String getComponentName() throws IOException, SAXException,
			XPathExpressionException {
		String name="";

		if (xmlDoc == null) {
			xmlDoc = XMLUtil.parse(new InputSource(new FileInputStream(compDefF)),
					false, false, null, null);
		}
		XPath xPath = XPathFactory.newInstance().newXPath();
		name = (String) xPath.evaluate("/componentDef/@name", xmlDoc,//NOI18N
				XPathConstants.STRING);
		if ((null == name) || (name.isEmpty())){
			return null;
		} else {
			name = name.substring(1).replaceAll("/", ".");
		}

		return name;
	}

	public boolean hasSrcDir() {
		String compDirSrc = compDirStr + File.separator + "src";
		srcDir = new File(compDirSrc);
		return !(!srcDir.exists() || !srcDir.isDirectory());
	}

	public File getSrcDir() {
		if (srcDir == null){
			String compDirSrc = compDirStr + File.separator + "src";
			srcDir = new File(compDirSrc);
		}
		if (!srcDir.exists() || !srcDir.isDirectory()){
			return null;
		} else {
			return srcDir;
		}
	}

	public boolean hasUnitDir() {
		String compDirUnit = compDirStr + File.separator + "unit";
		File unitDir = new File(compDirUnit);
		if (!unitDir.exists() || !unitDir.isDirectory()){
			return false;
		} else {
			return true;
		}
	}

	public File getUnitDir() {
		if (unitDir == null){
			String compDirUnit = compDirStr + File.separator + "unit";
			unitDir = new File(compDirUnit);
		}
		if (!unitDir.exists() || !unitDir.isDirectory()){
			return null;
		} else {
			return unitDir;
		}
	}

	public boolean hasMockDir() {
		String compDirMock = compDirStr + File.separator + "mock";
		File mockDir = new File(compDirMock);
		if (!mockDir.exists() || !mockDir.isDirectory()){
			return false;
		} else {
			return true;
		}
	}

	public String getDirectoryString() {
		return compDirStr;
	}
}
