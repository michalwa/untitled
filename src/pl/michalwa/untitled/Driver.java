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
import pl.michalwa.untitled.engine.events.Event;
import pl.michalwa.untitled.engine.geom.Vector2i;
import pl.michalwa.untitled.engine.graphics.Color;
import pl.michalwa.untitled.engine.graphics.DefaultGraphicsDriver;
import pl.michalwa.untitled.engine.graphics.GraphicsDriver;
import pl.michalwa.untitled.engine.graphics.image.Image;
import pl.michalwa.untitled.engine.graphics.image.ImageLoader;
import pl.michalwa.untitled.engine.input.keyboard.Key;
import pl.michalwa.untitled.engine.input.keyboard.KeyInput;
import pl.michalwa.untitled.engine.input.keyboard.events.KeyPressedEvent;
import pl.michalwa.untitled.engine.input.mouse.MouseInput;
import pl.michalwa.untitled.engine.loop.GameLoop;
import pl.michalwa.untitled.engine.loop.events.EverySecond;
import pl.michalwa.untitled.engine.loop.events.Frame;
import pl.michalwa.untitled.engine.runtime.Application;
import pl.michalwa.untitled.engine.window.Window;
import pl.michalwa.untitled.engine.window.cursor.Cursor;
import pl.michalwa.untitled.engine.window.cursor.CursorLoader;
import pl.michalwa.untitled.engine.window.events.WindowEvent;
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
		assets.registerLoader("cursor", new CursorLoader());
		
		// Set up window
		int width = 640, height = 480;
		window.subscribe(WindowEvent.class, System.out::println);
		window.setSize(width, height);
		
		Asset icon = assets.require("icon");
		if(icon instanceof Image) window.setIcon((Image) icon);
		
		Asset cursor = assets.require("cursors/pointer");
		if(cursor instanceof Cursor) window.setCursor((Cursor) cursor);
		
		// Load config
		Asset colorsAsset = assets.require("colors");
		Config colors = colorsAsset instanceof Config ? (Config) colorsAsset : new Config();
		Color background  = new Color(colors.get("background", "000"));
		Color foreground = new Color(colors.get("foreground0", "fff"));
		
		// Set up rendering
		gameLoop.events.subscribe(Frame.class, event -> {
			Graphics2D gfx = graphics.getGraphics();
			
			// Turn on antialiasing
			gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			// Fill canvas with background color
			gfx.setColor(background.toAWTColor());
			gfx.fillRect(0, 0, width, height);
			
			// Fill circle at cursor with foreground color
			gfx.setColor(foreground.toAWTColor());
			Vector2i mousePos = mouse.position.get();
			gfx.fillOval(mousePos.x - 10, mousePos.y - 10, 20, 20);
			
			// Display
			graphics.display();
		});
		
		// Log key presses
		keyboard.subscribe(KeyPressedEvent.class, System.out::println);
		
		// Quit on escape
		keyboard.subscribe(KeyPressedEvent.class,
			event -> event.getKey() == Key.ESCAPE,
			event -> Container.main.require(Application.class).quit());
		
		// Start the application
		app.subscribe(Event.class, System.out::println);
		if(app.start()) {
			window.setVisible(true);
			gameLoop.start();
		}
	}
}
