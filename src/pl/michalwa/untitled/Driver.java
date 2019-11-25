package pl.michalwa.untitled;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import pl.michalwa.untitled.engine.assets.Asset;
import pl.michalwa.untitled.engine.assets.AssetIndexParser;
import pl.michalwa.untitled.engine.assets.AssetStore;
import pl.michalwa.untitled.engine.assets.Assets;
import pl.michalwa.untitled.engine.component.Container;
import pl.michalwa.untitled.engine.config.Config;
import pl.michalwa.untitled.engine.config.ConfigLoader;
import pl.michalwa.untitled.engine.geom.Vector2i;
import pl.michalwa.untitled.engine.graphics.Color;
import pl.michalwa.untitled.engine.graphics.DefaultGraphicsDriver;
import pl.michalwa.untitled.engine.graphics.GraphicsDriver;
import pl.michalwa.untitled.engine.graphics.image.Image;
import pl.michalwa.untitled.engine.graphics.image.ImageLoader;
import pl.michalwa.untitled.engine.input.keyboard.Key;
import pl.michalwa.untitled.engine.input.keyboard.KeyInput;
import pl.michalwa.untitled.engine.input.keyboard.events.KeyEvent;
import pl.michalwa.untitled.engine.input.mouse.MouseInput;
import pl.michalwa.untitled.engine.loop.GameLoop;
import pl.michalwa.untitled.engine.loop.events.EverySecond;
import pl.michalwa.untitled.engine.loop.events.Frame;
import pl.michalwa.untitled.engine.runtime.Application;
import pl.michalwa.untitled.engine.window.Window;
import pl.michalwa.untitled.engine.xml.XMLLoader;

public class Driver
{
	public static void main(String[] args) throws Exception
	{
		// Initialize components
		Application    app        = new Application();
		GameLoop       gameLoop   = new GameLoop();
		Window         window     = new Window();
		GraphicsDriver graphics   = new DefaultGraphicsDriver();
		AssetStore     assetStore = new AssetStore();
		Assets         assets     = new Assets(
			new XMLLoader(),
			new AssetIndexParser(),
			"assets",
			"assets.xml");
		MouseInput     mouse      = new MouseInput();
		KeyInput       keyboard   = new KeyInput();
		
		Container.main.register(app, gameLoop, window, graphics, assetStore, assets, mouse, keyboard);
		Container.main.initialize();
		
		// Register asset loaders
		assets.registerLoader("image", new ImageLoader());
		assets.registerLoader("config", new ConfigLoader());
		
		// Set up window
		int width = 640, height = 480;
		window.setEventLogStream(System.out);
		window.setSize(width, height);
		Asset icon = assets.require("icon");
		if(icon instanceof Image) window.setIcon((Image) icon);
		
		// Load config
		Asset colorsAsset = assets.require("colors");
		Config colors = colorsAsset instanceof Config ? (Config) colorsAsset : new Config();
		Color background  = new Color(colors.get("background", "000"));
		Color foreground0 = new Color(colors.get("foreground0", "fff"));
		Color foreground1 = new Color(colors.get("foreground1", "fff"));
		Color foreground2 = new Color(colors.get("foreground2", "fff"));
		
		// Set up rendering
		gameLoop.events.subscribe(Frame.class, event -> {
			Graphics2D gfx = graphics.getGraphics();
			
			// Turn on antialiasing
			gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			// Fill canvas with background color
			gfx.setColor(background.toAWTColor());
			gfx.fillRect(0, 0, width, height);
			
			// Fill circle at cursor with foreground color
			gfx.setColor(foreground0.toAWTColor());
			Vector2i mousePos = mouse.position.get();
			gfx.fillOval(mousePos.x - 10, mousePos.y - 10, 20, 20);
			
			// Display
			graphics.display();
		});
		
		// Quit on exit
		keyboard.subscribe(KeyEvent.class,
			event -> event.getKey() == Key.ESCAPE,
			event -> Container.main.require(Application.class).quit());
		
		// Log TPS and FPS every second
		gameLoop.events.subscribe(EverySecond.class,
			event -> System.out.println("TPS: " + gameLoop.getTPS() + ", FPS: " + gameLoop.getFPS()));
		
		// Start the application
		app.setEventLogStream(System.out);
		if(app.start()) {
			window.setVisible(true);
			gameLoop.start();
		}
	}
}
