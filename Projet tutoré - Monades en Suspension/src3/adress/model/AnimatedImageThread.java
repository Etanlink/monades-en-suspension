package adress.model;

import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 * Thread responsible of the instantiation of one Image and defining its animation
 * @author Hugo
 *
 */

/*
 * caca */
public class AnimatedImageThread implements Runnable {
	
	private final ImageView monade;
	private Animation animation;
	
	private TranslateTransition trans;
	private int x;
	private int y;
	
	public int getX() { return x; }
	public int getY() { return y; }
	
	private Random r = new Random();
	private int compteur;
	
	/**
	 * Builder
	 */
	public AnimatedImageThread() {
		super();
		/* Instantiation of the ExtentedCircle */
		this.monade = new ImageView( new Image("res/monade.png") );
		this.monade.setPreserveRatio(true);
		this.monade.setFitWidth(this.r.nextInt(50)+50);
		// ((Shape)circ1).setFill(new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), 1 ) );
		this.monade.setX(400);
		this.monade.setY(300);

		/* DragListeners are added on the circle */
		setDragListeners((ImageView) this.monade);
	};

	public Node getNode() {
		return this.monade;
	}
	
	public Animation getAnimation() {
		return animation;
	}
	
	public boolean isOutOfFrame() {
		return (
				( ((ImageView) this.monade).getX() > WindowImpl.W_SCENE_SIZE + ((ImageView) this.monade).getFitWidth()/2*3) ||
				( ((ImageView) this.monade).getX() < 0 - ((ImageView) this.monade).getFitWidth()/2*3) ||
				( ((ImageView) this.monade).getY() > WindowImpl.H_SCENE_SIZE + ((ImageView) this.monade).getFitHeight()/2*3) ||
				( ((ImageView) this.monade).getY() < 0 - ((ImageView) this.monade).getFitHeight()/2*3)
				);
	}

	/**
	 * 
	 */
	@Override
	public void run() {

		shuffleXY(10);
		this.compteur = this.r.nextInt(10)+5;
		this.animation = buildTimeLine();
		this.animation.setCycleCount(Animation.INDEFINITE);
		this.animation.play();
	}
	
	/**
	 * @return the TimeLine of the thread
	 */
	private Timeline buildTimeLine() {
		return new Timeline(
				new KeyFrame(Duration.millis(100),

						new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						
						handleTimeLine();
					}			

				}) );
	}

	/**
	 * apply a Transition to the shape
	 */
	private void applyTranslation(double ms) {
		
		this.trans = new TranslateTransition(Duration.millis(/*r.nextInt(*/ms), this.monade );
		
		this.trans.setByX(this.x);
		this.trans.setByY(this.y);

		this.trans.setInterpolator(Interpolator.LINEAR);
		this.trans.play();

		/* Coordinates updated */
		((ImageView) this.monade).setX(((ImageView) this.monade).getX()+x);
		((ImageView) this.monade).setY(((ImageView) this.monade).getY()+y);
	
	}
	
	/**
	 * generates randomly an X for the Translation
	 */
	public void shuffleXY(int range) {

		this.x = r.nextInt(range);
		boolean p = r.nextBoolean();
		if(p==true) x = -x;

		this.y = range - this.x;
		p = r.nextBoolean();
		if(p==true) y = -y;
	}

	/**
	 * Adds dragListeners on ONE ImageView
	 * @param circ1 : the circle listened
	 */
	protected void setDragListeners(ImageView circ1) {
		final Delta dragDelta = new Delta();

		circ1.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent mouseEvent) {
				// record a delta distance for the drag and drop operation.
				dragDelta.setX((double)(circ1.getLayoutX() - mouseEvent.getSceneX()));
				dragDelta.setY((double)(circ1.getLayoutY() - mouseEvent.getSceneY()));
				circ1.setCursor(Cursor.NONE);
			}
		});

		circ1.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent mouseEvent) {
				circ1.setCursor(Cursor.HAND);
				circ1.setX(mouseEvent.getSceneX());
				circ1.setY(mouseEvent.getSceneY());
			}
		});

		circ1.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent mouseEvent) {
				circ1.setLayoutX(mouseEvent.getSceneX() + dragDelta.getX());
				circ1.setLayoutY(mouseEvent.getSceneY() + dragDelta.getY());

				circ1.setX(mouseEvent.getSceneX());
				circ1.setY(mouseEvent.getSceneY());
			}
		});

	}
	
	/**
	 * content of the TimeLine
	 */
	private void handleTimeLine() {
		if(this.compteur==0) {
			/*CubicCurveTo curve = new CubicCurveTo(380, 120, 10, 240, 380, 240);
			
			Path path = new Path();
			path.getElements().add(new MoveTo(x,y));
			//path.getElements().add(new CubicCurveTo(380, 0, 380, 120, 200, 120));
			shuffleXY(10);
			path.getElements().add(new CubicCurveTo(x+180, y-180, x+10, y+240, x, y));
			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(100));
			pathTransition.setPath(path);
			pathTransition.setNode(circ1);
			pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
			pathTransition.setInterpolator(Interpolator.LINEAR);
			pathTransition.play();*/
			shuffleXY(10);
			this.compteur = this.r.nextInt(10)+5;
		}

		if(this.monade instanceof ImageView){
			applyTranslation(100);
		}
		this.compteur--;
	}

}
