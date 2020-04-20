package skyvssea.view;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TilePane extends StackPane implements Observer {

    public static final String DEFAULT_LIGHT_BASE_COLOR = "#FCF5EF";
    public static final String DEFAULT_DARK_BASE_COLOR = "#264F73";
    public static final String HIGHLIGHED_COLOR = "#FF5733";

    private Rectangle base;
    private int x;
    private int y;
    private BoardPane boardPane;

    public TilePane(int x, int y, double tileSize, BoardPane boardPane) {
        this.x = x;
        this.y = y;
        this.boardPane = boardPane;
        this.base = new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize);
        base.setStroke(Color.valueOf(DEFAULT_DARK_BASE_COLOR));

        this.getChildren().add(base);
    }

    public void updateTileSize(double newTileSize, double mostLeftX, double mostTopY) {
        base.setWidth(newTileSize);
        base.setHeight(newTileSize);
        setTranslateX(mostLeftX + x * newTileSize);
        setTranslateY(mostTopY + y * newTileSize);
    }

    public Rectangle getBase() { return base; }
    public int getX() { return x; }
    public int getY() { return y; }

    private void updateBaseColor(String color) {
        base.setFill(Color.valueOf(color));
    }

	@Override
	public void update(Observable tile, Object stringInput) {
		//Jiang: Obviously this is not the most ideal design but this is all i can think of now
		if (((String) stringInput).charAt(0) == '#') {
			updateBaseColor((String) stringInput);
		} 
//		else if (((String) stringInput).equals("REMOVE_PIECEVIEW")) {
//			PieceView removedPieceView = (PieceView) getChildren().remove(1);
//			boardPane.removePieceView(removedPieceView.getName());
//		} 
		else {
			PieceView pieceView = boardPane.getPieceView((String) stringInput);
			
			//this condition is only true when initializing Piece and PieceView objects
			if (pieceView == null) {
				pieceView = new PieceView((String) stringInput);
				boardPane.addPieceView(pieceView);
//				boardPane.updatePieceSize(pieceView);
			}
			
			getChildren().add(pieceView);		
			
			
//			PieceView newPieceView = new PieceView((String) stringInput);
//			getChildren().add(newPieceView);		
//			boardPane.addPieceView(newPieceView);
//			boardPane.updatePieceSize(newPieceView);
		}
		
	}
}
