import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.*;

import javax.swing.JFrame;

public class SolarSystem3d extends JFrame{
	//The canvas to be drawn upon.
	  public Canvas3D myCanvas3D;

	public SolarSystem3d(){
	    //Mechanism for closing the window and ending the program.
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //Default settings for the viewer parameters.
	    myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
	    //Construct the SimpleUniverse by using the Canvas.
	    SimpleUniverse simpUniv = new SimpleUniverse(myCanvas3D);
	    // The position of the viewer - default position
	    simpUniv.getViewingPlatform().setNominalViewingTransform();	   
	    // The new position
	    Transform3D trNew = new Transform3D();
	    //trNew.setTranslation(new Vector3d(1.f,0.f,0.f));
	    trNew.setTranslation(new Vector3d(0.0f,0.0f,40.0f));
	    //trNew.setTranslation(new Vector3d(-1.f,0.f,1.f));
	    //trNew.setTranslation(new Vector3d(0.f,0.5f,1.f));
	    //trNew.setTranslation(new Vector3d(0.f,-0.5f,1.f));
	    //trNew.setTranslation(new Vector3d(0.0f,0.35f,0.7f));
	    simpUniv.getViewingPlatform().getViewPlatformTransform().setTransform(trNew);
	    
	    //The following three lines enable navigation through the scene using the mouse.
	    OrbitBehavior ob = new OrbitBehavior(myCanvas3D);
	    ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE));
	    simpUniv.getViewingPlatform().setViewPlatformBehavior(ob);
	    
	    //The scene is generated from this call.
	    createSceneGraph(simpUniv);
            //Add some light to the scene.
	    addLight(simpUniv);
	    //Show the canvas/window.
	    setTitle("3D Solar System");
	    setSize(700,700);
	    getContentPane().add("Center", myCanvas3D);
	    setVisible(true);
	}
	//In this method, the objects for the scene are generated and added to 
	  //the SimpleUniverse.
	public void createSceneGraph(SimpleUniverse su){
            //*** The root of the graph containing the scene. ***
		BranchGroup branch = new BranchGroup();
		Color3f white = new Color3f(0.0f,0.0f,0.0f);
		
		//Sun
		TransformGroup trans = new TransformGroup();
		trans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		branch.addChild(trans);
		//Background of the scene
		TextureLoader myLoader = new TextureLoader("image.jpg", this);
		ImageComponent2D myImage = myLoader.getImage();
		Background myBack = new Background();
		myBack.setCapability(Background.ALLOW_IMAGE_READ);
		myBack.setImage(myImage);
		BoundingSphere myBounds = new BoundingSphere(new Point3d(), 1000.0);
		myBack.setApplicationBounds(myBounds);
		trans.addChild(myBack);
		
		Appearance sunApp = new Appearance();
		Color3f sunCol = new Color3f(1.0f,1.0f,0.0f);
		sunApp.setMaterial(new Material(sunCol,white,sunCol,white,150.0f));
		float sunRadius = 3.0f;
		Sphere sun = new Sphere(sunRadius,sunApp);
		
		Transform3D tfsun = new Transform3D();
		tfsun.setTranslation(new Vector3f(0.0f,0.0f,1.0f));
		TransformGroup tgsun = new TransformGroup(tfsun);
		tgsun.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tgsun.addChild(sun);
		trans.addChild(tgsun);
		
		Alpha spinsun = new Alpha(-1, 10000);
		RotationInterpolator spinnerSun = new RotationInterpolator(spinsun, tgsun);
		spinnerSun.setSchedulingBounds(new BoundingSphere(new Point3d(), 0.0));
		tgsun.addChild(spinnerSun);
		
		//Mercury
		TransformGroup transmercury = new TransformGroup();
		transmercury.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		branch.addChild(transmercury);
		
		Appearance mercuryApp = new Appearance();
		Color3f mercuryCol = new Color3f(0.3f,0.3f,0.3f);
		mercuryApp.setMaterial(new Material(mercuryCol,white,mercuryCol,white,145.0f));
		float mercuryRadius = 0.14f;
		Sphere mercury = new Sphere(mercuryRadius,mercuryApp);
		
		Transform3D tfmercury = new Transform3D();
		tfmercury.setTranslation(new Vector3f(4.5f,0.0f,1.0f));
		TransformGroup tgmercury = new TransformGroup(tfmercury);
		tgmercury.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tgmercury.addChild(mercury);
		
		Alpha spinMercury = new Alpha(-1, 3000);
		RotationInterpolator spinnerMercury = new RotationInterpolator(spinMercury, tgmercury);
		spinnerMercury.setSchedulingBounds(new BoundingSphere(new Point3d(), 0.0));
		tgmercury.addChild(spinnerMercury);
		TransformGroup tgmercuryspin = new TransformGroup(tfmercury);
		tgmercuryspin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tgmercuryspin.addChild(tgmercury);
		transmercury.addChild(tgmercuryspin);
		
		Alpha orbitmercury = new Alpha(-1, 4000);
		RotationInterpolator movemercury = new RotationInterpolator(orbitmercury, transmercury);
		movemercury.setSchedulingBounds(new BoundingSphere(new Point3d(), 0.0));
		transmercury.addChild(movemercury);
		
		//Venus
		TransformGroup transvenus = new TransformGroup();
		transvenus.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		branch.addChild(transvenus);
		
		Appearance venusApp = new Appearance();
		Color3f venusCol = new Color3f(1.0f,1.0f,0.5f);
		venusApp.setMaterial(new Material(venusCol,white,venusCol,white,140.0f));
		float venusRadius = 0.4f;
		Sphere venus = new Sphere(venusRadius,venusApp);
		
		Transform3D tfvenus = new Transform3D();
		tfvenus.setTranslation(new Vector3f(5.5f,0.0f,1.0f));
		TransformGroup tgvenus = new TransformGroup(tfvenus);
		tgvenus.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tgvenus.addChild(venus);
		
		Alpha spinVenus = new Alpha(-1, 5000);
		RotationInterpolator spinnerVenus = new RotationInterpolator(spinVenus, tgvenus);
		spinnerVenus.setSchedulingBounds(new BoundingSphere(new Point3d(), 0.0));
		tgvenus.addChild(spinnerVenus);
		TransformGroup tgvenusspin = new TransformGroup(tfvenus);
		tgvenusspin.addChild(tgvenus);
		transvenus.addChild(tgvenusspin);
		
		Alpha orbitvenus = new Alpha(-1, 5000);
		RotationInterpolator movevenus = new RotationInterpolator(orbitvenus, transvenus);
		movevenus.setSchedulingBounds(new BoundingSphere(new Point3d(), 0.0));
		transvenus.addChild(movevenus);

		//Earth
		TransformGroup transearth = new TransformGroup();
		transearth.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		branch.addChild(transearth);
		
		Appearance earthApp = new Appearance();
		Color3f earthCol = new Color3f(0.0f,0.7f,1.0f);
		earthApp.setMaterial(new Material(earthCol,white,earthCol,white,135.0f));
		float earthRadius = 0.5f;
		Sphere earth = new Sphere(earthRadius,earthApp);
		
		Appearance moonApp = new Appearance();
		Color3f moonCol = new Color3f(0.5f,0.5f,0.5f);
		moonApp.setMaterial(new Material(moonCol,white,moonCol,white,135.0f));
		float moonRadius = 0.125f;
		Sphere moon = new Sphere(moonRadius,moonApp);
		
		Transform3D tfmoon = new Transform3D();
		tfmoon.setTranslation(new Vector3f(-0.5f,-0.0f,0.7f));
		TransformGroup tgmoon = new TransformGroup();
		tgmoon.setTransform(tfmoon);
		tgmoon.addChild(moon);
		BoundingSphere boundingSphere = new BoundingSphere(new Point3d(0.0,0.0,0.0),1.0);
		
		TransformGroup tgspin = new TransformGroup();
		tgspin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Alpha spin = new Alpha(-1,6000);
		RotationInterpolator spinner = new RotationInterpolator(spin,tgspin);
		spinner.setSchedulingBounds(boundingSphere);
		tgspin.addChild(spinner);
		tgspin.addChild(earth);
		tgspin.addChild(tgmoon);
		
		Transform3D tf = new Transform3D();
		tf.setTranslation(new Vector3f(6.9f,0.0f,1.0f));
		TransformGroup tg = new TransformGroup();
		tg.setTransform(tf);
		tg.addChild(tgspin);
		transearth.addChild(tg);
		
		Alpha orbitearth = new Alpha(-1, 5000);
		RotationInterpolator moveearth = new RotationInterpolator(orbitearth, transearth);
		moveearth.setSchedulingBounds(new BoundingSphere(new Point3d(), 0.0));
		transearth.addChild(moveearth);
		
		//Mars
		TransformGroup transmars = new TransformGroup();
		transmars.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		branch.addChild(transmars);
		
		Appearance marsApp = new Appearance();
		Color3f marsCol = new Color3f(1.5f,0.2f,0.0f);
		marsApp.setMaterial(new Material(marsCol,white,marsCol,white,130.0f));
		float marsRadius = 0.26f;
		Sphere mars = new Sphere(marsRadius,marsApp);
		
		Transform3D tfmars = new Transform3D();
		tfmars.setTranslation(new Vector3f(8.1f,0.0f,1.0f));
		TransformGroup tgmars = new TransformGroup(tfmars);
		tgmars.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tgmars.addChild(mars);
		
		Alpha spinMars = new Alpha(-1, 4000);
		RotationInterpolator spinnerMars = new RotationInterpolator(spinMars, tgmars);
		spinnerMars.setSchedulingBounds(new BoundingSphere(new Point3d(), 0.0));
		tgmars.addChild(spinnerMars);
		TransformGroup tgmarsspin = new TransformGroup(tfmars);
		tgmarsspin.addChild(tgmars);
		transmars.addChild(tgmarsspin);
		
		Alpha orbitmars = new Alpha(-1, 6000);
		RotationInterpolator movemars = new RotationInterpolator(orbitmars, transmars);
		movemars.setSchedulingBounds(new BoundingSphere(new Point3d(), 0.0));
		transmercury.addChild(movemars);
		
		//Jupiter
		TransformGroup transjupiter = new TransformGroup();
		transjupiter.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		branch.addChild(transjupiter);
		
		Appearance jupiterApp = new Appearance();
		Color3f jupiterCol = new Color3f(1.0f,0.4f,0.0f);
		jupiterApp.setMaterial(new Material(jupiterCol,white,jupiterCol,white,125.0f));
		float jupiterRadius = 0.94f;
		Sphere jupiter = new Sphere(jupiterRadius,jupiterApp);
		
		Transform3D tfjupiter = new Transform3D();
		tfjupiter.setTranslation(new Vector3f(9.7f,0.0f,1.0f));
		TransformGroup tgjupiter = new TransformGroup(tfjupiter);
		tgjupiter.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tgjupiter.addChild(jupiter);
		
		Alpha spinJupiter = new Alpha(-1, 8000);
		RotationInterpolator spinnerJupiter = new RotationInterpolator(spinJupiter, tgjupiter);
		spinnerJupiter.setSchedulingBounds(new BoundingSphere(new Point3d(), 0.0));
		tgjupiter.addChild(spinnerJupiter);
		TransformGroup tgjupiterspin = new TransformGroup(tfjupiter);
		tgjupiterspin.addChild(tgjupiter);
		transjupiter.addChild(tgjupiterspin);
		
		Alpha orbitjupiter = new Alpha(-1, 7000);
		RotationInterpolator movejupiter = new RotationInterpolator(orbitjupiter, transjupiter);
		movejupiter.setSchedulingBounds(new BoundingSphere(new Point3d(), 0.0));
		transmercury.addChild(movejupiter);
		
		//Saturn
		TransformGroup transsaturn = new TransformGroup();
		transsaturn.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		branch.addChild(transsaturn);
		
		Appearance saturnApp = new Appearance();
		Color3f saturnCol = new Color3f(1.0f,1.0f,0.5f);
		saturnApp.setMaterial(new Material(saturnCol,white,saturnCol,white,120.0f));
		float saturnRadius = 0.86f;
		Sphere saturn = new Sphere(saturnRadius,saturnApp);
		
		Transform3D tfsaturn = new Transform3D();
		tfsaturn.setTranslation(new Vector3f(12.0f,0.0f,1.0f));
		TransformGroup tgsaturn = new TransformGroup(tfsaturn);
		tgsaturn.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tgsaturn.addChild(saturn);
		
		Alpha spinSaturn = new Alpha(-1, 6500);
		RotationInterpolator spinnerSaturn = new RotationInterpolator(spinSaturn, tgsaturn);
		spinnerSaturn.setSchedulingBounds(new BoundingSphere(new Point3d(), 0.0));
		tgsaturn.addChild(spinnerSaturn);
		TransformGroup tgsaturnspin = new TransformGroup(tfsaturn);
		tgsaturnspin.addChild(tgsaturn);
		transsaturn.addChild(tgsaturnspin);
		
		Alpha orbitsaturn = new Alpha(-1, 4000);
		RotationInterpolator movesaturn = new RotationInterpolator(orbitsaturn, transsaturn);
		movesaturn.setSchedulingBounds(new BoundingSphere(new Point3d(), 0.0));
		transmercury.addChild(movesaturn);
		
		//Uranus
		TransformGroup transuranus = new TransformGroup();
		transuranus.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		branch.addChild(transuranus);
		
		Appearance uranusApp = new Appearance();
		Color3f uranusCol = new Color3f(0.6f,1.0f,1.0f);
		uranusApp.setMaterial(new Material(uranusCol,white,uranusCol,white,115.0f));
		float uranusRadius = 0.74f;
		Sphere uranus = new Sphere(uranusRadius,uranusApp);
		
		Transform3D tfuranus = new Transform3D();
		tfuranus.setTranslation(new Vector3f(14.2f,0.0f,1.0f));
		TransformGroup tguranus = new TransformGroup(tfuranus);
		tguranus.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tguranus.addChild(uranus);
		
		Alpha spinUranus = new Alpha(-1, 7000);
		RotationInterpolator spinnerUranus = new RotationInterpolator(spinUranus, tguranus);
		spinnerUranus.setSchedulingBounds(new BoundingSphere(new Point3d(), 0.0));
		tguranus.addChild(spinnerUranus);
		TransformGroup tguranusspin = new TransformGroup(tfuranus);
		tguranusspin.addChild(tguranus);
		transuranus.addChild(tguranusspin);
		
		Alpha orbituranus = new Alpha(-1, 8000);
		RotationInterpolator moveuranus = new RotationInterpolator(orbituranus, transuranus);
		moveuranus.setSchedulingBounds(new BoundingSphere(new Point3d(), 0.0));
		transmercury.addChild(moveuranus);
		
		//Neptune
		TransformGroup transneptune = new TransformGroup();
		transneptune.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		branch.addChild(transneptune);
		
		Appearance neptuneApp = new Appearance();
		Color3f neptuneCol = new Color3f(0.6f,1.0f,1.0f);
		neptuneApp.setMaterial(new Material(neptuneCol,white,neptuneCol,white,110.0f));
		float neptuneRadius = 0.7f;
		Sphere neptune = new Sphere(neptuneRadius,neptuneApp);
		
		Transform3D tfneptune = new Transform3D();
		tfneptune.setTranslation(new Vector3f(16.2f,0.0f,1.0f));
		TransformGroup tgneptune = new TransformGroup(tfneptune);
		tgneptune.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tgneptune.addChild(neptune);
		
		Alpha spinNeptune = new Alpha(-1, 7000);
		RotationInterpolator spinnerNeptune = new RotationInterpolator(spinNeptune, tgneptune);
		spinnerNeptune.setSchedulingBounds(new BoundingSphere(new Point3d(), 0.0));
		tgneptune.addChild(spinnerNeptune);
		TransformGroup tgneptunespin = new TransformGroup(tfneptune);
		tgneptunespin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tgneptunespin.addChild(tgneptune);
		transneptune.addChild(tgneptunespin);
		
		Alpha orbitneptune = new Alpha(-1, 9000);
		RotationInterpolator moveneptune = new RotationInterpolator(orbitneptune, transneptune);
		moveneptune.setSchedulingBounds(new BoundingSphere(new Point3d(), 0.0));
		transmercury.addChild(moveneptune);
		
		//Pluto
		TransformGroup transpluto = new TransformGroup();
		transpluto.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		branch.addChild(transpluto);
		
		Appearance plutoApp = new Appearance();
		Color3f plutoCol = new Color3f(0.3f,0.2f,0.0f);
		plutoApp.setMaterial(new Material(plutoCol,white,plutoCol,white,1.0f));
		float plutoRadius = 0.2f;
		Sphere pluto = new Sphere(plutoRadius,plutoApp);
		
		Transform3D tfpluto = new Transform3D();
		tfpluto.setTranslation(new Vector3f(17.6f,0.0f,1.0f));
		TransformGroup tgpluto = new TransformGroup(tfpluto);
		tgpluto.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tgpluto.addChild(pluto);
		
		Alpha spinPluto = new Alpha(-1,3000);
		RotationInterpolator spinnerPluto = new RotationInterpolator(spinPluto, tgpluto);
		spinnerPluto.setSchedulingBounds(new BoundingSphere(new Point3d(), 0.0));
		tgpluto.addChild(spinnerPluto);
		TransformGroup tgplutospin = new TransformGroup(tfpluto);
		tgplutospin.addChild(tgpluto);
		transpluto.addChild(tgplutospin);
		
		Alpha orbitpluto = new Alpha(-1, 10000);
		RotationInterpolator movepluto = new RotationInterpolator(orbitpluto, transpluto);
		movepluto.setSchedulingBounds(new BoundingSphere(new Point3d(), 0.0));
		transmercury.addChild(movepluto);

		//Add the scene (BranchGroup) to the universe.
		su.addBranchGraph(branch);
	}
	//The light to be added.
	public void addLight(SimpleUniverse su){
		BranchGroup bgLight = new BranchGroup();

		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0f,0.0f,0.0f), 100.0);
		Color3f lightColour1 = new Color3f(1.0f,1.0f,1.0f);
		//Vector3f lightDir1  = new Vector3f(-1.0f,0.0f,-0.5f);
		Vector3f lightDir1  = new Vector3f(2.0f,0.5f,-5.f);
		DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
		light1.setInfluencingBounds(bounds);
		bgLight.addChild(light1);
		su.addBranchGraph(bgLight);
	}
	public static void main(String[] args){
		// TODO Auto-generated method stub
		SolarSystem3d ss = new SolarSystem3d();
	}

}