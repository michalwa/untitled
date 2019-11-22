package pl.michalwa.untitled;

import java.awt.Graphics;
import java.awt.Graphics2D;
import pl.michalwa.untitled.engine.assets.Asset;
import pl.michalwa.untitled.engine.assets.AssetIndexParser;
import pl.michalwa.untitled.engine.assets.AssetStore;
import pl.michalwa.untitled.engine.assets.Assets;
import pl.michalwa.untitled.engine.component.Container;
import pl.michalwa.untitled.engine.component.MissingComponentException;
import pl.michalwa.untitled.engine.graphics.Color;
import pl.michalwa.untitled.engine.graphics.DefaultGraphicsDriver;
import pl.michalwa.untitled.engine.graphics.GraphicsDriver;
import pl.michalwa.untitled.engine.graphics.image.Image;
import pl.michalwa.untitled.engine.graphics.image.ImageLoader;
import pl.michalwa.untitled.engine.loop.GameLoop;
import pl.michalwa.untitled.engine.loop.events.EverySecond;
import pl.michalwa.untitled.engine.loop.events.Frame;
import pl.michalwa.untitled.engine.window.Window;
import pl.michalwa.untitled.engine.window.events.WindowEvent;
import pl.michalwa.untitled.engine.xml.XMLLoader;

public class Driver
{
	public static void main(String[] args) throws Exception
	{
		// Initialize components
		GameLoop       gameLoop   = new GameLoop();
		Window         window     = new Window();
		GraphicsDriver graphics   = new DefaultGraphicsDriver();
		AssetStore     assetStore = new AssetStore();
		Assets         assets     = new Assets(
			new XMLLoader(),
			new AssetIndexParser(),
			"assets",
			"assets.xml");
		
		Container.main.register(gameLoop, window, graphics, assetStore, assets);
		Container.main.initialize();
		
		// Register asset loaders
		assets.registerLoader("image/png", new ImageLoader());
		
		// Set up window
		int width = 640, height = 480;
		window.subscribe(WindowEvent.class, System.out::println);  // log all window events
		window.setSize(width, height);
		Asset icon = assets.require("icon");
		if(icon instanceof Image) window.setIcon((Image) icon);
		
		// Set up rendering
		Color bgColor = new Color("2d1b00");
		gameLoop.events.subscribe(Frame.class, event -> {
			try {
				Graphics2D gfx = Container.main.require(GraphicsDriver.class).getGraphics();
				gfx.setColor(bgColor.toAWTColor());
				gfx.fillRect(0, 0, width, height);
				graphics.display();
			} catch(MissingComponentException e) {
				e.printStackTrace();
			}
		});
		
		// Log TPS and FPS every second
		gameLoop.events.subscribe(EverySecond.class,
			event -> System.out.println("TPS: " + gameLoop.getTPS() + ", FPS: " + gameLoop.getFPS()));
		
		// Start the application
		window.setVisible(true);
		gameLoop.start();
	}
}
