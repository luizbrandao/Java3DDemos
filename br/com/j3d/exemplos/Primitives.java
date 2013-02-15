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

/////////////////////////////////////////////////////////////////////
// Isabel Harb Manssour
// Junho de 2003
// Primitives.java ilustra como criar instâncias de modelos 
// pré-definidos.
// Este código está baseado no demo HelloUniverse.java

import javax.swing.*;
import java.awt.*;
//import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class Primitives extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ///////////////////////////////////////////////////////////////
	// Atributo da classe Primitives
	//
	private SimpleUniverse universe = null;

	// ///////////////////////////////////////////////////////////////
	// Construtor da classe HelloUniversePrimitives
	//
	public Primitives() {
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();

		Canvas3D canvas = new Canvas3D(config);
		container.add("Center", canvas);

		// Cria um sub-grafo de conteudo
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

	// ///////////////////////////////////////////////////////////////
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

		// Cria um "bounds"
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				100.0);

		Appearance app = new Appearance();
		/*
		 * ColoringAttributes ca = new ColoringAttributes(); ca.setColor(new
		 * Color3f(0.0f, 1.0f, 0.0f)); app.setColoringAttributes(ca);
		 */
		// Parâmetros: Color3f ambientColor, Color3f emissiveColor,
		// Color3f diffuseColor, Color3f specularColor,
		// float shininess
		Material material = new Material(new Color3f(0.8f, 0.8f, 0.1f),
				new Color3f(0.0f, 1.0f, 0.0f), new Color3f(0.8f, 0.8f, 0.1f),
				new Color3f(1.0f, 1.0f, 1.0f), 100.0f);
		app.setMaterial(material);

		com.sun.j3d.utils.geometry.Box cubo = new com.sun.j3d.utils.geometry.Box(
				0.2f, 0.2f, 0.2f, app);

		cubo.setAppearance(app);

		objTrans.addChild(cubo);

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

	// ///////////////////////////////////////////////////////////////
	// Método principal que permite executar a aplicação
	//
	public static void main(String[] args) {
		new Primitives();
	}

}