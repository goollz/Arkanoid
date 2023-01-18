package projetoarkanoid;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class BlocoAzul extends AbstractBloco{
    
    private Rectangle bloco;
    private Image imagem;
    private ImagePattern imagemPattern;

    public BlocoAzul(int cX, int cY, int tamX, int tamY, int pontos, Pane painel) throws FileNotFoundException{
        
        super(pontos, cX, cY);
        bloco = super.getBloco(tamX, tamY);
        super.setCor(bloco , "#0000FF");
        imagem = new Image(new FileInputStream("src/imagens/sabreAzul.png"));
        imagemPattern = new ImagePattern(imagem);
        bloco.setFill(imagemPattern);
    }
    
    @Override
    public Rectangle getBloco(){
        return bloco;
    }

    @Override
    public void Mover(){}

    @Override
    public int getColisoes(){
        return 0;
    }

    @Override
    public void DiminuiColisoes(){}
}