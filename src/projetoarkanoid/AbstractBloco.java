package projetoarkanoid;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

abstract class AbstractBloco extends GameObject{
    
    private int pontos;
    private Rectangle bloco;

    public AbstractBloco(int pontos, int cx, int cy){
        
        super(cx, cy);
        this.pontos = pontos;
    }
    
    public Rectangle getBloco(int tamX, int tamY){
        
        bloco = new Rectangle();
        bloco.setX(super.getCx());
        bloco.setY(super.getCy());
        bloco.setHeight(tamY);
        bloco.setWidth(tamX);
        
        return bloco;
    }
    
    public Rectangle getBloco(){
        return bloco;
    }
    
    public abstract int getColisoes();
    public abstract void DiminuiColisoes();

    public void setCor(Rectangle figura,String valor){
        figura.setFill(Paint.valueOf(valor));
    }
    
    public int getPontos(){
        return pontos;
    }
}