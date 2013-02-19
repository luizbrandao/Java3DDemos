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
// TextureExample.java ilustra como trabalhar com textura.
// Este código está baseado no demo HelloUniverse.java

import java.awt.*;
import java.applet.Applet;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class TextureExample extends Applet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// /////////////////////////////////////////////////////////////////////
	// Atributo da classe TextureExample
	//
	private SimpleUniverse universe = null;

	// /////////////////////////////////////////////////////////////////////
	// Método init da applet
	//
	public void init() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();

		Canvas3D canvas = new Canvas3D(config);
		add("Center", canvas);

		// Cria um sub-grafo de conteúdo
		BranchGroup scene = criaGrafoDeCena();
		universe = new SimpleUniverse(canvas);

		// O código abaixo faz com que a ViewPlatform seja movida
		// um pouco para trás, para que os objetos possam ser
		// visualizados
		universe.getViewingPlatform().setNominalViewingTransform();

		// Anexa o sub-grafo no universo virtual
		universe.addBranchGraph(scene);

		setSize(350, 350);
		setVisible(true);
	}

	// /////////////////////////////////////////////////////////////////////
	// Método responsável pela criação do grafo de cena (ou sub-grafo)
	//
	public BranchGroup criaGrafoDeCena() {

		// Cria o nodo raiz
		BranchGroup objRaiz = new BranchGroup();

		// Cria o nodo TransformGroup e permite que ele possa
		// ser alterado em tempo de execução (TRANSFORM_WRITE).
		// Depois, adiciona-o na raiz do grafo de cena.
		TransformGroup objTrans = new TransformGroup();
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objRaiz.addChild(objTrans);

		// Cria um "bounds" para o background
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				100.0);

		// Especifica um background azul e adiciona-o no grafo
		Color3f bgColor = new Color3f(0.8f, 0.8f, 0.8f);
		Background bg = new Background(bgColor);
		bg.setApplicationBounds(bounds);
		objRaiz.addChild(bg);

		// Especifica as luzes do "ambiente"
		Color3f corLuz = new Color3f(0.9f, 0.9f, 0.9f);
		Vector3f direcaoLuz = new Vector3f(-1.0f, -1.0f, -1.0f);
		Color3f corAmb = new Color3f(0.2f, 0.2f, 0.2f);

		AmbientLight luzAmb = new AmbientLight(corAmb);
		luzAmb.setInfluencingBounds(bounds);
		DirectionalLight luzDir = new DirectionalLight(corLuz, direcaoLuz);
		luzDir.setInfluencingBounds(bounds);
		objRaiz.addChild(luzAmb);
		objRaiz.addChild(luzDir);

		Appearance app = new Appearance();

		// Carrega a textura
		java.net.URL texImage = null;
		try {
			texImage = new java.net.URL(getCodeBase().toString()+"br\\com\\j3d\\exemplos\\stone.jpg");
		} catch (java.net.MalformedURLException ex) {
			System.out.println(ex.getMessage());
			System.exit(1);
		}
		TextureLoader loader = new TextureLoader(texImage, this);

		app.setTexture(loader.getTexture());

		Material material = new Material(new Color3f(0.2f, 0.2f, 0.2f),
				new Color3f(0.0f, 0.0f, 0.0f), new Color3f(1.0f, 1.0f, 1.0f),
				new Color3f(0.5f, 0.5f, 0.5f), 100.0f);
		app.setMaterial(material);

		Cone cone = new Cone(0.4f, 0.8f, Cone.GENERATE_NORMALS
				| Cone.GENERATE_TEXTURE_COORDS, 14, 14, app);
		cone.setAppearance(app);

		Cylinder cilindro = new Cylinder(0.4f, 0.7f, Cylinder.GENERATE_NORMALS
				| Cylinder.GENERATE_TEXTURE_COORDS, 20, 10, app);
		cilindro.setAppearance(app);

		objTrans.addChild(cone);

		// Cria um novo objeto Behaviour que irá executar as
		// operações desejadas no "transform" especificado
		// e adiciona-o no grafo.
		Transform3D trans = new Transform3D();
		trans.rotZ(Math.toRadians(60));

		Alpha rotacaoAlpha = new Alpha(-1, 4000);

		RotationInterpolator rotator = new RotationInterpolator(rotacaoAlpha,
				objTrans, trans, 0.0f, (float) Math.PI * 2.0f);
		rotator.setSchedulingBounds(bounds);

		objRaiz.addChild(rotator);

		// Para o Java 3D realizar otimizações no grafo de cena
		objRaiz.compile();

		return objRaiz;
	}

}
