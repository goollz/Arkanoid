package projetoarkanoid;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Raquete extends GameObject{
    
    private Pane painel;
    private Rectangle raquete;
    private Image imagem;
    private ImagePattern imagemPattern;
    private int passo = 0;

    public Raquete(int cx, int cy, int tamX, int tamY, Pane painel) throws FileNotFoundException{
        
        super(cx, cy);
        this.painel = painel;
        cx = (int)painel.getHeight() / 2 - tamX / 2;
        raquete = super.getRetangulo(cx, cy, tamX, tamY, "#FF00FF");
        imagem = new Image(new FileInputStream("src/imagens/raquete.png"));
        imagemPattern = new ImagePattern(imagem);
        raquete.setFill(imagemPattern);
    }

    public Rectangle getRaquete(){
        return raquete;
    }
    
    @Override
    public void Mover(){ 
        
        if((raquete.getX()-10+passo) > 0 && ((raquete.getX()+110)+10+passo) < painel.getWidth())
            raquete.setX(raquete.getX()+passo);
    }
    
    public void Mover(int direcao){
        
        if((direcao-10) > 0 && ((direcao+110)+10) < painel.getWidth())
            raquete.setX(direcao);
    }
    
    public void trocarDirecao(KeyEvent key){
        
        if(null != key.getCode()){
            
            switch(key.getCode()){
            
                case RIGHT:
                        if(passo <= 0)
                            passo = 6;
                        break;
                case LEFT:
                        if(passo >= 0)
                            passo = -6;
                        break;
                default:
                        break;
            }
        }    
    }
}