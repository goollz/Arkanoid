package projetoarkanoid;

public class Score{
    
    private int pontos;

    public Score(int pontos){
        
        this.pontos = pontos;
    }
    
    public void zerar(){
        this.pontos = 0;
    }
    
    public void somar(int ptos){
        this.pontos += ptos; 
    }
    
    public int getPontos(){
        return pontos;
    }

    public void setPontos(int pontos){
        this.pontos = pontos;
    }
}