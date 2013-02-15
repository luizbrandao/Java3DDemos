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
// Outubro de 2003
// GeometryExample.java ilustra como criar geometria.
// Este código está baseado no demo HelloUniverse.java

import javax.swing.*;
import java.awt.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class GeometryExample extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// /////////////////////////////////////////////////////////////////////
	// Atributo da classe GeometryExample
	//
	private SimpleUniverse universe = null;

	// /////////////////////////////////////////////////////////////////////
	// Construtor da classe GeometryExample
	//
	public GeometryExample() {
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
		ViewingPlatform viewingPlatform = universe.getViewingPlatform();
		viewingPlatform.setNominalViewingTransform();

		// O código abaixo altera o field-of-view para
		// permitir a visualização de todos objetos
		View view = universe.getViewer().getView();
		view.setFieldOfView(view.getFieldOfView() * 1.4);

		// Adiciona "mouse behaviors" à "viewingPlatform"
		// (equivale a trocar a posição do "observador virtual")
		OrbitBehavior orbit = new OrbitBehavior(canvas,
				OrbitBehavior.REVERSE_ALL);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				100.0);
		orbit.setSchedulingBounds(bounds);
		viewingPlatform.setViewPlatformBehavior(orbit);

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
		objRaiz.setCapability(BranchGroup.ALLOW_BOUNDS_READ);

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
		Color3f bgColor = new Color3f(0.1f, 0.1f, 0.7f);
		Background bg = new Background(bgColor);
		bg.setApplicationBounds(bounds);
		objRaiz.addChild(bg);

		// Especifica as luzes do "ambiente" (ambiente e direcional)
		Color3f corAmb = new Color3f(0.2f, 0.2f, 0.2f);
		AmbientLight luzAmb = new AmbientLight(corAmb);
		luzAmb.setInfluencingBounds(bounds);
		objRaiz.addChild(luzAmb);

		Color3f corLuz = new Color3f(0.9f, 0.9f, 0.9f);
		Vector3f direcaoLuz1 = new Vector3f(-1.0f, -1.0f, -1.0f);
		Vector3f direcaoLuz2 = new Vector3f(1.0f, -1.0f, -1.0f);
		DirectionalLight luzDir1 = new DirectionalLight(corLuz, direcaoLuz1);
		DirectionalLight luzDir2 = new DirectionalLight(corLuz, direcaoLuz2);
		luzDir1.setInfluencingBounds(bounds);
		luzDir2.setInfluencingBounds(bounds);
		objRaiz.addChild(luzDir1);
		objRaiz.addChild(luzDir2);

		// Especifica a aparência do material
		Appearance app = new Appearance();
		Material material = new Material(new Color3f(0.7f, 0.1f, 0.7f),
				new Color3f(0.0f, 0.0f, 0.0f), new Color3f(0.7f, 0.1f, 0.7f),
				new Color3f(1.0f, 1.0f, 1.0f), 60.0f);
		app.setMaterial(material);

		Shape3D s3d = new Shape3D();
		s3d.setAppearance(app);
		s3d.setGeometry(cubeGeometry());
		objTrans.addChild(s3d);

		// Cria outro nodo TransformGroup node e permite que ele possa
		// ser alterado em tempo de execução (TRANSFORM_WRITE).
		// Depois, adiciona-o na raiz do grafo.
		TransformGroup textTrans = new TransformGroup();
		textTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objRaiz.addChild(textTrans);

		// Cria um novo objeto que irá aplicar as transformações
		// geométricas sobre texto e o adicina no grafo.
		Transform3D trans = new Transform3D();
		Transform3D t1 = new Transform3D();

		t1.rotX(Math.toRadians(-10.0));
		trans.mul(t1);
		trans.setScale(0.3);
		trans.setTranslation(new Vector3d(-0.5, 0.7, 0.0));

		textTrans.setTransform(trans);

		Font3D font3d = new Font3D(new Font("Helvetica", Font.PLAIN, 1),
				new FontExtrusion());
		Text3D textGeom = new Text3D(font3d, new String("Geometria!"),
				new Point3f(-1.0f, 0.0f, 0.0f));
		Shape3D textShape = new Shape3D(textGeom);
		textShape.setAppearance(app);

		textTrans.addChild(textShape);

		// Para o Java 3D realizar otimizações no grafo de cena
		objRaiz.compile();

		return objRaiz;
	}

	// /////////////////////////////////////////////////////////////////////
	// Método responsável pela criação de um cubo usando GeometryArray
	//
	private Geometry cubeGeometry() {
		GeometryInfo gi = new GeometryInfo(GeometryInfo.QUAD_ARRAY);
		Point3f[] pts = new Point3f[8];
		pts[0] = new Point3f(-0.5f, 0.5f, 0.5f);
		pts[1] = new Point3f(0.5f, 0.5f, 0.5f);
		pts[2] = new Point3f(0.5f, -0.5f, 0.5f);
		pts[3] = new Point3f(-0.5f, -0.5f, 0.5f);
		pts[4] = new Point3f(-0.5f, 0.5f, -0.5f);
		pts[5] = new Point3f(0.5f, 0.5f, -0.5f);
		pts[6] = new Point3f(0.5f, -0.5f, -0.5f);
		pts[7] = new Point3f(-0.5f, -0.5f, -0.5f);
		int[] indices = { 0, 4, 7, 3, // left face
				6, 2, 3, 7, // bottom face
				4, 5, 6, 7, // back face
				5, 1, 2, 6, // right face
				5, 4, 0, 1, // top face
				1, 0, 3, 2 // front face
		};
		gi.setCoordinates(pts);
		gi.setCoordinateIndices(indices);
		NormalGenerator ng = new NormalGenerator();
		// Passar 100 como parâmetro para a função abaixo, faz com que
		// as "dobras" (união das faces) fique mais suave do que se fosse
		// passado um valor mais baixo
		ng.setCreaseAngle((float) Math.toRadians(100));
		ng.generateNormals(gi);
		GeometryArray cube = gi.getGeometryArray();
		return cube;
	}

	// /////////////////////////////////////////////////////////////////////
	// Método principal que permite executar a aplicação
	//
	public static void main(String[] args) {
		new GeometryExample();
	}

}
