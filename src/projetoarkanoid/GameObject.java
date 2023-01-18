package projetoarkanoid;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

class GameObject implements Game{
    
    private int cx;
    private int cy;
    private Rectangle retangulo;
    private Circle circulo;

    public GameObject(int cx, int cy){
        
        this.cx = cx;
        this.cy = cy;
    }

    public int getCx(){
        return cx;
    }

    public void setCx(int cx){
        this.cx = cx;
    }

    public int getCy(){
        return cy;
    }

    public void setCy(int cy){
        this.cy = cy;
    }
    
    public Rectangle getRetangulo(int cx, int cy, int tamX, int tamY, String cor){
        
        retangulo = new Rectangle();
        retangulo.setX(cx);
        retangulo.setY(cy);
        retangulo.setWidth(tamX);
        retangulo.setHeight(tamY);
        retangulo.setFill(Paint.valueOf(cor));
        
        return retangulo;
    }
    
    public Circle getCircle(int cx, int cy, int raio, String cor){
        
        circulo = new Circle(cx, cy, raio);
        circulo.setFill(Paint.valueOf(cor));
        
        return circulo;
    }

    @Override
    public void Mover(){} 
}