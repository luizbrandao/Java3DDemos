package br.com.j3d.exemplos;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.image.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.Container;

public class PictureBall {
	public PictureBall() {
		SimpleUniverse universe = new SimpleUniverse();
		BranchGroup group = new BranchGroup();
		Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
		Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
		Color3f red = new Color3f(0.7f, .15f, .15f);
		TextureLoader loader = new TextureLoader(
				"C:\\Users\\Luiz\\Desktop\\JavaSimples\\earth.jpg",
				new Container());
		Texture texture = loader.getTexture();
		texture.setBoundaryModeS(Texture.WRAP);
		texture.setBoundaryModeT(Texture.WRAP);
		texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));

		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.MODULATE);
		Appearance ap = new Appearance();
		ap.setTexture(texture);
		ap.setTextureAttributes(texAttr);
		ap.setMaterial(new Material(red, black, white, black, 1.0f));

		int primflags = Primitive.GENERATE_NORMALS
				+ Primitive.GENERATE_TEXTURE_COORDS;
		Sphere sphere = new Sphere(0.5f, primflags, ap);
		group.addChild(sphere);

		Color3f light1Color = new Color3f(1f, 1f, 1f);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				100.0);

		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
		DirectionalLight light1 = new DirectionalLight(light1Color,
				light1Direction);
		light1.setInfluencingBounds(bounds);
		group.addChild(light1);

		AmbientLight ambientLight = new AmbientLight(new Color3f(.5f, .5f, .5f));
		ambientLight.setInfluencingBounds(bounds);
		group.addChild(ambientLight);

		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(group);
	}

	public static void main(String[] args) {
		new PictureBall();
	}
}