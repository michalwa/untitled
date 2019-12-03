package pl.michalwa.untitled;

import pl.michalwa.untitled.engine.assets.Assets;
import pl.michalwa.untitled.engine.component.Container;
import pl.michalwa.untitled.engine.config.Config;
import pl.michalwa.untitled.engine.config.ConfigLoader;
import pl.michalwa.untitled.engine.events.Event;
import pl.michalwa.untitled.engine.graphics.Color;
import pl.michalwa.untitled.engine.graphics.DefaultGraphicsDriver;
import pl.michalwa.untitled.engine.graphics.GraphicsDriver;
import pl.michalwa.untitled.engine.graphics.image.Image;
import pl.michalwa.untitled.engine.graphics.image.ImageLoader;
import pl.michalwa.untitled.engine.graphics.render.AnchorMode;
import pl.michalwa.untitled.engine.graphics.render.Renderer;
import pl.michalwa.untitled.engine.graphics.render.RenderingContext;
import pl.michalwa.untitled.engine.graphics.render.layer.Background;
import pl.michalwa.untitled.engine.graphics.render.layer.Layer;
import pl.michalwa.untitled.engine.input.keyboard.Key;
import pl.michalwa.untitled.engine.input.keyboard.KeyInput;
import pl.michalwa.untitled.engine.input.keyboard.events.KeyPressedEvent;
import pl.michalwa.untitled.engine.input.mouse.MouseInput;
import pl.michalwa.untitled.engine.loop.GameLoop;
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
		Renderer       renderer   = new Renderer();
		Assets         assets     = new Assets(new XMLLoader(), "assets", "assets.xml");
		MouseInput     mouse      = new MouseInput();
		KeyInput       keyboard   = new KeyInput();
		
		Container.main.register(
			app,
			gameLoop,
			window,
			graphics,
			renderer,
			assets,
			mouse,
			keyboard
		).initialize();
		
		// Register asset loaders
		assets.registerLoader(new ImageLoader())
			.registerLoader(new ConfigLoader())
			.registerLoader(new CursorLoader());
		
		// Set up window
		int width = 640, height = 480;
		window.subscribe(WindowEvent.class, System.out::println);
		window.setSize(width, height);
		window.setIcon(assets.require(Image.class, "icon"));
		window.setCursor(assets.require(Cursor.class, "cursors/pointer"));
		
		// Load config
		Config colors = assets.require(Config.class, "colors");
		Color background = new Color(colors.get("background", "000"));
		Color foreground0 = new Color(colors.get("foreground0", "fff"));
		Color foreground1 = new Color(colors.get("foreground1", "fff"));
		
		// Set up rendering
		renderer.setAntialiasingEnabled(true);
		renderer.addLayer(new Background(background));
		renderer.addLayer(new Layer()
		{
			@Override
			public void render(RenderingContext ctx)
			{
				ctx.setFillColor(foreground0);
				ctx.setStrokeColor(foreground1);
				ctx.setStrokeWidth(10);
				ctx.setAnchorMode(AnchorMode.CENTER);
				ctx.drawCircle((float) width / 2, (float) height / 2, mouse.wheel.get() * 20);
			}
		});
		
		// Quit on escape
		keyboard.subscribe(KeyPressedEvent.class,
			event -> event.getKey() == Key.ESCAPE,
			event -> app.quit());
		
		// Log key presses
		keyboard.subscribe(KeyPressedEvent.class, System.out::println);
		
		mouse.wheel.set(5);
		
		// Start the application
		app.subscribe(Event.class, System.out::println);
		if(app.start()) {
			window.setVisible(true);
			gameLoop.start();
		}
	}
}
