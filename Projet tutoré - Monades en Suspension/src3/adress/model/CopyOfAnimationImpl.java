package adress.model;

import java.io.IOException;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 * USED FOR THREAD TESTING
 * Class generating and managing the animation
 * @author Hugo
 *
 */
public class CopyOfAnimationImpl {

	private Group root;

	private static final Random r = new Random();

	public CopyOfAnimationImpl(Group root) {
		super();
		this.root = root;
	}

	/**
	 * method responsible of the overall animation
	 * @throws IOException
	 */
	public void addCircles() throws IOException {
		final Group circles = new Group();
		this.root.getChildren().add(circles);

		final Timeline animation = new Timeline(
				new KeyFrame(Duration.millis(3000),

						new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent actionEvent) {
						
						int nbCircles = 0;
						/* Circles are created to suit the minimal parameter */
						while (circles.getChildren().size() <= 3){
							int radius = 10 * r.nextInt(10);
							final ExtentedCircle circ1 = new ExtentedCircle(400,400, radius);
							nbCircles++;
							circ1.setFill(new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), 1 ) );

							/* DragListeners are added on the circle */
							setDragListeners(circ1);

							circles.getChildren().add(circ1);
						}

						/* Control of the maximal parameter */
						if(circles.getChildren().size() <= 10){	
							int p = r.nextInt(100);
							int q = r.nextInt(100);
							if(q>=p)
							{
								int radius = 10 * r.nextInt(10);
								final ExtentedCircle circ1 = new ExtentedCircle(400,400, radius);
								circ1.setFill(new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), 0.8 ) );
								/* DragListeners are added on the circle */
								setDragListeners(circ1);
								circles.getChildren().add(circ1);
							}
						}

						/* Translation applied on each circle */
						for(Node circ1 : circles.getChildren()){
							
							int sceneSize = WindowImpl.SCENE_SIZE;
							
								if(circ1 instanceof ExtentedCircle){
									TranslateTransition trans = new TranslateTransition(Duration.millis(/*r.nextInt(*/3000), circ1 );

									/* Generation of the coordinates of the move */
									int x = r.nextInt(120);
									int y = 120-x;
									boolean p = r.nextBoolean();
									if(p==true) x = -x;
									p = r.nextBoolean();
									if(p==true) y = -y;

									trans.setByX(x);
									trans.setByY(y);
									/* Coordinates updated */
									((ExtentedCircle)circ1).setX(((ExtentedCircle) circ1).getX()+x);
									((ExtentedCircle)circ1).setY(((ExtentedCircle) circ1).getY()+y);
									
									trans.setInterpolator(Interpolator.LINEAR);
									trans.play();
								}
								if(
										( ((ExtentedCircle) circ1).getX() >= sceneSize + ((ExtentedCircle) circ1).getRadius()) ||
										( ((ExtentedCircle) circ1).getX( )<= 0 - ((ExtentedCircle) circ1).getRadius()) ||
										( ((ExtentedCircle) circ1).getY() >= sceneSize + ((ExtentedCircle) circ1).getRadius()) ||
										( ((ExtentedCircle) circ1).getY() <= 0 - ((ExtentedCircle) circ1).getRadius()) 
										)
								{
									System.out.println("Cercle " + circles.getChildren().indexOf(circ1) + "se casse" );
									circles.getChildren().remove(circ1);
								}
								System.out.println("Cercle " + circles.getChildren().indexOf(circ1) + " x:" + ((ExtentedCircle) circ1).getX() + " y:" + ((ExtentedCircle) circ1).getY() );
							}
						

					}
				})
				);
		animation.setCycleCount(Animation.INDEFINITE);
		animation.play();
	}
	
	/**
	 * Adds dragListeners on ONE circle
	 * @param circ1 : the circle listened
	 */
	protected void setDragListeners(ExtentedCircle circ1) {
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
	
}
