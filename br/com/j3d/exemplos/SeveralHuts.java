package br.com.j3d.exemplos;

/*
 *  @(#)HelloUniverse.java 1.55 02/10/21 13:43:36
 *
 * Copyright (c) 1996-2002 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 */

///////////////////////////////////////////////////////////////////////////
// Isabel Harb Manssour
// Junho de 2003
// SeveralHuts.java ilustra como criar duas ou mais huts
// (cabanas) em um universo.
// Este código está baseado no demo HelloUniverse.java

import javax.swing.*;
import java.awt.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class SeveralHuts extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// /////////////////////////////////////////////////////////////////////
	// Atributo da classe SeveralHuts
	//
	private SimpleUniverse universe = null;

	// /////////////////////////////////////////////////////////////////////
	// Construtor da classe SeveralHuts
	//
	public SeveralHuts() {
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();

		Canvas3D canvas = new Canvas3D(config);
		container.add("Center", canvas);

		// Cria dois grafos e anexa-os no universo virtual
		BranchGroup cena1 = criaGrafoDeCena(0.2, 0.5, 0.0, 0.0,
				Math.toRadians(30), 0.0, 0.0);
		BranchGroup cena2 = criaGrafoDeCena(0.4, -0.5, 0.0, 0.0,
				Math.toRadians(-20), 0.0, 0.0);
		BranchGroup cena3 = criaGrafoDeCena(0.6, 0.0, 0.4, 0.0, 0.0, 0.0,
				Math.toRadians(45));
		BranchGroup cena4 = criaGrafoDeCena(0.1, 0.0, -0.4, -0.5,
				Math.toRadians(-20), 0.0, 0.0);

		universe = new SimpleUniverse(canvas);

		// O código abaixo faz com que a ViewPlatform seja movida
		// um pouco para trás, para que os objetos possam ser
		// visualizados.
		universe.getViewingPlatform().setNominalViewingTransform();

		universe.addBranchGraph(cena1);
		universe.addBranchGraph(cena2);
		universe.addBranchGraph(cena3);
		universe.addBranchGraph(cena4);

		setSize(350, 350);
		setVisible(true);
	}

	// /////////////////////////////////////////////////////////////////////
	// Método responsável pela criação do grafo de cena (ou sub-grafo)
	//
	public BranchGroup criaGrafoDeCena(double e, double tx, double ty,
			double tz, double rx, double ry, double rz) {

		// Cria o nó raiz
		BranchGroup objRaiz = new BranchGroup();

		// Cria o nodo TransformGroup e permite que ele possa
		// ser alterado em tempo de execução (TRANSFORM_WRITE).
		// Depois, adiciona-o na raiz do grafo de cena.
		TransformGroup objTrans = new TransformGroup();
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objRaiz.addChild(objTrans);

		// Cria o nodo TransformGroup para o cone e permite que ele
		// possa ser alterado em tempo de execução (TRANSFORM_WRITE).
		// Depois, adiciona-o no objeto objTrans.
		TransformGroup coneTrans = new TransformGroup();
		coneTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objTrans.addChild(coneTrans);

		// Cria o nodo TransformGroup para o cilindro e permite que ele
		// possa ser alterado em tempo de execução (TRANSFORM_WRITE).
		// Depois, adiciona-o no objeto objTrans.
		TransformGroup cilindroTrans = new TransformGroup();
		cilindroTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objTrans.addChild(cilindroTrans);

		// Cria um "bounds" para o background
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				100.0);

		// Especifica um background azul e adiciona-o no grafo
		Color3f bgColor = new Color3f(0.1f, 0.1f, 0.7f);
		Background bg = new Background(bgColor);
		bg.setApplicationBounds(bounds);
		objRaiz.addChild(bg);

		// Especifica as luzes do "ambiente"

		// Luz Ambiente
		Color3f corAmb = new Color3f(0.2f, 0.2f, 0.2f);
		AmbientLight luzAmb = new AmbientLight(corAmb);
		luzAmb.setInfluencingBounds(bounds);
		objRaiz.addChild(luzAmb);

		Appearance app = new Appearance();
		// Color3f ambientColor, Color3f emissiveColor, Color3f diffuseColor,
		// Color3f specularColor, float shininess
		Material material = new Material(new Color3f(0.8f, 0.8f, 0.1f),
				new Color3f(0.0f, 0.0f, 0.0f), new Color3f(0.8f, 0.8f, 0.1f),
				new Color3f(1.0f, 1.0f, 1.0f), 100.0f);
		app.setMaterial(material);

		// Cria um novo objeto que irá aplicar as transformações
		// geométricas sobre a "hut" (cone+cilindro) e o adicina
		// no grafo.
		Transform3D trans = new Transform3D();
		Transform3D t1 = new Transform3D();
		Transform3D t2 = new Transform3D();
		// Aplica rotação
		t1.rotX(rx);
		t2.rotY(ry);
		trans.rotZ(rz);
		trans.mul(t1);
		trans.mul(t2);
		// Aplica escala
		trans.setScale(e);
		// Aplica translação
		trans.setTranslation(new Vector3d(tx, ty, tz));
		objTrans.setTransform(trans);

		// Cria um novo objeto que irá aplicar as transformações
		// geométricas sobre o cone e o adicina no grafo.
		Transform3D coneT = new Transform3D();
		coneT.setTranslation(new Vector3d(0.0, 0.7, 0.0));
		coneTrans.setTransform(coneT);

		Cone cone = new Cone(0.5f, 0.6f);
		cone.setAppearance(app);
		coneTrans.addChild(cone);

		// Cria um novo objeto que irá aplicar as transformações
		// geométricas sobre o cilindro e o adicina no grafo.
		Transform3D cilindroT = new Transform3D();
		cilindroTrans.setTransform(cilindroT);

		Cylinder cilindro = new Cylinder(0.3f, 0.8f, 1, 20, 10, app);
		cilindroTrans.addChild(cilindro);

		// Para o Java 3D realizar otimizações no grafo de cena
		objRaiz.compile();

		return objRaiz;
	}

	// /////////////////////////////////////////////////////////////////////
	// Método principal que permite executar a aplicação
	//
	public static void main(String[] args) {
		new SeveralHuts();
	}

}
