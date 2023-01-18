package projetoarkanoid;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class BlocoVerde extends AbstractBloco{

    private Rectangle bloco;
    private Pane painel;
    private int passo = 2;
    private int colisoes = 2;
    private Image imagem;
    private ImagePattern imagemPattern;
    AnimationTimer timer;
    
    public BlocoVerde(int cx, int cy, int tamX, int tamY, int pontos, Pane painel) throws FileNotFoundException{

        super(pontos, cx, cy);
        bloco = super.getBloco(tamX, tamY);
        super.setCor(bloco, "#00FF00");
        this.painel = painel;
        imagem = new Image(new FileInputStream("src/imagens/sabreVerde.png"));
        imagemPattern = new ImagePattern(imagem);
        bloco.setFill(imagemPattern);
    }

    @Override
    public Rectangle getBloco(){
        return bloco;
    }

    @Override
    public int getColisoes(){
        return colisoes;
    }

    @Override
    public void DiminuiColisoes(){
        colisoes--;
    }

    @Override
    public void Mover(){
        timer = new AnimationTimer(){
            @Override
            public void handle(long now){
                if(bloco.getY() + bloco.getWidth() < 400){
                    bloco.setY(bloco.getY() + 1);
                }
                else if(bloco.getY() + bloco.getWidth() == 400){
                    if(bloco.getX() + bloco.getWidth() >= painel.getWidth() || bloco.getX() <= 0){
                        passo *= -1;
                    }
                    bloco.setX(bloco.getX() + passo);
                }
                if(getColisoes() <= 0){
                    timer.stop();
                }
            }
        };
        timer.start();
    }
}