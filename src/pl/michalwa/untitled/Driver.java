package pl.michalwa.untitled;

import pl.michalwa.untitled.engine.actor.Actor;
import pl.michalwa.untitled.engine.actor.Scene;
import pl.michalwa.untitled.engine.actor.trait.Transform;
import pl.michalwa.untitled.engine.assets.Assets;
import pl.michalwa.untitled.engine.component.Container;
import pl.michalwa.untitled.engine.config.ConfigLoader;
import pl.michalwa.untitled.engine.events.Event;
import pl.michalwa.untitled.engine.geom.Vector2i;
import pl.michalwa.untitled.engine.graphics.Color;
import pl.michalwa.untitled.engine.graphics.DefaultGraphicsDriver;
import pl.michalwa.untitled.engine.graphics.GraphicsDriver;
import pl.michalwa.untitled.engine.graphics.font.FontLoader;
import pl.michalwa.untitled.engine.graphics.image.ImageLoader;
import pl.michalwa.untitled.engine.graphics.render.AnchorMode;
import pl.michalwa.untitled.engine.graphics.render.Renderer;
import pl.michalwa.untitled.engine.graphics.render.RenderingContext;
import pl.michalwa.untitled.engine.graphics.render.layer.Background;
import pl.michalwa.untitled.engine.input.keyboard.Key;
import pl.michalwa.untitled.engine.input.keyboard.KeyInput;
import pl.michalwa.untitled.engine.input.keyboard.events.KeyPressedEvent;
import pl.michalwa.untitled.engine.input.mouse.MouseInput;
import pl.michalwa.untitled.engine.loop.GameLoop;
import pl.michalwa.untitled.engine.runtime.Application;
import pl.michalwa.untitled.engine.window.Window;
import pl.michalwa.untitled.engine.window.cursor.CursorLoader;
import pl.michalwa.untitled.engine.window.events.WindowEvent;
import pl.michalwa.untitled.engine.xml.XMLLoader;

public class Driver
{
	public static void main(String[] args) throws Exception
	{
		// Initialize components
		Application    app      = new Application();
		GameLoop       gameLoop = new GameLoop();
		Window         window   = new Window();
		GraphicsDriver graphics = new DefaultGraphicsDriver();
		Renderer       renderer = new Renderer();
		Assets         assets   = new Assets(new XMLLoader(), "assets", "assets.xml");
		MouseInput     mouse    = new MouseInput();
		KeyInput       keyboard = new KeyInput();
		
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
			.registerLoader(new CursorLoader())
			.registerLoader(new FontLoader());
		
		// Set up window
		window.subscribe(WindowEvent.class, System.out::println);
		window.setSize(640, 480);
		window.setTitle("untitled");
		
		// Set up scene
		Scene scene = new Scene(1);
		
		Actor actor = new Actor();
		actor.attach(new Transform());
		actor.attach(new pl.michalwa.untitled.engine.actor.trait.Renderer()
		{
			@Override
			protected void onAttached(Actor actor) {}
			
			@Override
			public void render(RenderingContext ctx)
			{
				getActor().getTrait(Transform.class).ifPresent(transform -> {
					ctx.setStrokeColor(Color.WHITE);
					ctx.setAnchorMode(AnchorMode.Vertical.CENTER, AnchorMode.Horizontal.CENTER);
					ctx.drawCircle(transform.position, 10);
				});
			}
		});
		actor.getTrait(Transform.class)
			.ifPresent(transform -> transform.absolutePosition.bindTo(mouse.position, Vector2i::asVector2f));
		scene.addActor(actor);
		
		// Set up rendering
		renderer.setAntialiasingEnabled(true);
		renderer.addLayer(new Background(0, Color.BLACK));
		renderer.addLayer(scene);
		
		// Quit on escape
		keyboard.subscribe(KeyPressedEvent.class, System.out::println);
		keyboard.subscribe(KeyPressedEvent.class,
			event -> event.getKey() == Key.ESCAPE,
			event -> app.quit());
		
		// Start the application
		app.subscribe(Event.class, System.out::println);
		if(app.start()) {
			window.setVisible(true);
			gameLoop.start();
		}
	}
}
