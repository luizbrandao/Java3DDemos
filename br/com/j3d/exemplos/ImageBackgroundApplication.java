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
// ImageBackgroundApplication.java ilustra como criar backgrounds.
// Este código está baseado no demo HelloUniverse.java

import javax.swing.*;
import java.awt.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.*;
import javax.vecmath.*;

public class ImageBackgroundApplication extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// /////////////////////////////////////////////////////////////////////
	// Atributo da classe ImageBackgroundApplication
	//
	private SimpleUniverse universe = null;

	// /////////////////////////////////////////////////////////////////////
	// Construtor da classe ImageBackgroundApplication
	//
	public ImageBackgroundApplication() {
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();

		Canvas3D canvas = new Canvas3D(config);
		container.add("Center", canvas);

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
		// Depois, adiciona-o na raiz do grafo.
		TransformGroup objTrans = new TransformGroup();
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objRaiz.addChild(objTrans);

		// Cria um "bounds" para o background
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				100.0);

		// objeto para abrir imagens
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		TextureLoader texturaBg = new TextureLoader(toolkit.getImage("C:\\Users\\Luiz Brandão\\workspace\\JavaSimples\\br\\com\\j3d\\exemplos\\stone.jpg"), this);
		Background bg = new Background(texturaBg.getImage());
		bg.setApplicationBounds(bounds);
		bg.setImageScaleMode(Background.SCALE_REPEAT);
		objRaiz.addChild(bg);

		// Especifica as luzes do "ambiente"
		Color3f corLuz = new Color3f(0.9f, 0.9f, 0.9f);
		Vector3f direcaoLuz = new Vector3f(0.0f, -0.8f, -1.0f);
		Color3f corAmb = new Color3f(0.2f, 0.2f, 0.2f);

		AmbientLight luzAmb = new AmbientLight(corAmb);
		luzAmb.setInfluencingBounds(bounds);
		DirectionalLight luzDir = new DirectionalLight(corLuz, direcaoLuz);
		luzDir.setInfluencingBounds(bounds);
		objRaiz.addChild(luzAmb);
		objRaiz.addChild(luzDir);

		Appearance app = new Appearance();
		Material material = new Material(new Color3f(0.9f, 0.1f, 0.1f),
				new Color3f(0.0f, 0.0f, 0.0f), new Color3f(0.9f, 0.1f, 0.1f),
				new Color3f(1.0f, 1.0f, 1.0f), 60.0f);
		app.setMaterial(material);

		Sphere esfera = new Sphere(0.4f, 1, 40);
		esfera.setAppearance(app);

		objTrans.addChild(esfera);

		// Cria outro nodo TransformGroup node e permite que ele possa
		// ser alterado em tempo de execução (TRANSFORM_WRITE).
		// Depois, adiciona-o na raiz do grafo.
		TransformGroup textTrans = new TransformGroup();
		textTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objRaiz.addChild(textTrans);

		// Cria um novo objeto que irá aplicar as transformações
		// geométricas sobre texto e o adicina no grafo.
		Transform3D trans = new Transform3D();
		trans.setTranslation(new Vector3d(-0.4, 0.6, 0.0));
		textTrans.setTransform(trans);

		// Text2D (java.lang.String text, Color3f color,
		// java.lang.String fontName, int fontSize, int fontStyle)
		Text2D text2D = new Text2D("E S F E R A !!", new Color3f(0.9f, 0.1f,
				0.1f), "Helvetica", 30, Font.BOLD);
		textTrans.addChild(text2D);

		// Para o Java 3D realizar otimizações no grafo de cena
		objRaiz.compile();

		return objRaiz;
	}

	// /////////////////////////////////////////////////////////////////////
	// Método principal que permite executar a aplicação
	//
	public static void main(String[] args) {
		new ImageBackgroundApplication();
	}
}
