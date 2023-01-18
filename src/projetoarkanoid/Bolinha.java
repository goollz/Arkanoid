package projetoarkanoid;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Bolinha extends GameObject{
    
    private int passox = 2;
    private int passoy = 2;
    private Circle bola;
    private Pane painel;
    private Image imagem;
    private ImagePattern imagemPattern;
    private Timer t;
    
    public Bolinha(int passox, int passoy, int cx, int cy, Pane painel) throws FileNotFoundException{
        
        super(cx, cy);
        this.passox = passox;
        this.passoy = passoy;
        bola = super.getCircle(cx, cy, 10, "#00FFFF");
        this.painel = painel;
        imagem = new Image(new FileInputStream("src/imagens/Bola.png"));
        imagemPattern = new ImagePattern(imagem);
        bola.setFill(imagemPattern);
    }
    
    public void normalizar(){
        
        passox = passox > 0?2:-2;
        passoy = passoy > 0?2:-2;
    }
    
    public void startTimer(){
        
        t = new Timer();
        t.schedule(
        new TimerTask(){

            @Override
            public void run(){
                normalizar();
                t.cancel();
            }
        }, 15000);
    }
    
    @Override
    public void Mover(){
        
        if(bola.getCenterX() + bola.getRadius() >= painel.getWidth() || bola.getCenterX() - bola.getRadius() <= 0)
            passox *= -1;
        
        if(bola.getCenterY() + bola.getRadius() >= painel.getHeight()-15 || bola.getCenterY() - bola.getRadius() <= 0)
            passoy *= -1;
        
        bola.setCenterX(bola.getCenterX() + passox);
        bola.setCenterY(bola.getCenterY() + passoy);
    }
    
    public boolean verColisao(Rectangle retangulo){
        return bola.intersects(retangulo.getLayoutBounds());
    }

    public Circle getBola(){
        return bola;
    }

    public int getPassox(){
        return passox;
    }

    public void setPassox(int passox){
        this.passox = passox;
    }

    public int getPassoy(){
        return passoy;
    }

    public void setPassoy(int passoy){
        this.passoy = passoy;
    }
    
    public void InvertePassoY(){
        this.passoy *= -1;
    }
    
    public void InvertePassoX(){
        this.passox *= -1;
    }
}