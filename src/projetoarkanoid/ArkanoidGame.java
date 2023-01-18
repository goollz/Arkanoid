package projetoarkanoid;

import javafx.scene.control.Label;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;

public class ArkanoidGame extends Pane{
      
    private int vidasOff;
    private int estado;
    private MediaPlayer tocarMusica, tocarJogo, tocarCreditos, tocarMorte1, tocarMorte2, tocarGanhou, tocarVida, tocarColisao;
    private Pane painel;
    private int posX = 45;
    private int posY = 50;
    private Raquete raquete;
    private List<Bolinha> bola;
    private List<AbstractBloco> blocos;
    private int flag = 0;
    private Label pontos = new Label();
    private Label vidas = new Label();
    private boolean paused = false;
    private AnimationTimer timer;
    private boolean cont = false;
    
    public ArkanoidGame(int vidasOff, int estado){
        
        this.vidasOff = 5;
        this.estado = estado;
    }
    
    public ArkanoidGame(){
        
        Platform.runLater(() -> {
            TelaInicial();
        });
    }
    
    public void TelaInicial(){
        
        ResetarTela();
        vidasOff = 5;
        
        //TELA INICIAL
        Media som = new Media(new File("src/musicas/TelaInicial.mp3").toURI().toString());
        tocarMusica = new MediaPlayer(som);
        this.setStyle("-fx-background-image: url('imagens/telainicial.gif')");
        
        //INGAME
        Media somGame = new Media(new File("src/musicas/InGame.mp3").toURI().toString());
        tocarJogo = new MediaPlayer(somGame);
        
        //CREDITOS
        Media somCreditos = new Media(new File("src/musicas/Creditos.mp3").toURI().toString());
        tocarCreditos = new MediaPlayer(somCreditos);

        tocarMusica.play();
        Platform.runLater(() -> {
            getScene().setOnKeyPressed(e -> {
                //Jogar
                if(e.getCode() == KeyCode.J){
                    this.setStyle("-fx-background-image: url('imagens/teladefundo.gif')");
                    tocarMusica.stop();
                    tocarCreditos.stop();
                    tocarJogo.play();
                    try{
                        IniciarJogo();
                        JogarPausar();
                    }catch(FileNotFoundException ex){
                        Logger.getLogger(ArkanoidGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //Tutorial
                else if(e.getCode() == KeyCode.T){
                    this.setStyle("-fx-background-image: url('imagens/telaTutorial.gif')");
                }
                //Créditos
                else if(e.getCode() == KeyCode.C){
                    tocarMusica.stop();
                    tocarCreditos.play();
                    this.setStyle("-fx-background-image: url('imagens/telaCreditos.gif')");
                }
                //Sair
                else if(e.getCode() == KeyCode.S){
                    System.exit(0);
                }
                //Voltar
                else if(e.getCode() == KeyCode.V){
                    tocarMusica.play();
                    tocarCreditos.stop();
                    this.setStyle("-fx-background-image: url('imagens/telainicial.gif')");
                }
            });
        }); 
    }
    
    public void ResetarTela(){
        
        painel = null;
        posX = 45;
        posY = 50;
        raquete = null;
        bola = null;
        blocos = null;
        flag = 0;
        pontos = new Label();
        vidas = new Label();
        paused = false;
        timer = null;
        
        this.getChildren().clear();
    }
    
    public void IniciarJogo() throws FileNotFoundException{
 
        painel = this;
        blocos = new ArrayList<>();
        raquete = new Raquete(0, 550, 110, 15, painel);
        bola = new ArrayList<>();
        int random;
        
        for(int i = 0; i < 15; i++){
           random = (int)(Math.random() * 5);
           
            switch(random){
                case 0:
                    blocos.add(new BlocoAmarelo(posX, posY, 120, 20, 10, painel));
                        break;
                case 1:
                    blocos.add(new BlocoVerde(posX, posY, 120, 20, 15, painel));
                        break;
                case 2:
                    blocos.add(new BlocoAzul(posX, posY, 120, 20, 15, painel));
                        break;
                case 3:
                    blocos.add(new BlocoBranco(posX, posY, 120, 20, 15, painel));
                        break;
                default:
                    blocos.add(new BlocoAmarelo(posX, posY, 120, 20, 10, painel));
                        break;
            }
            getChildren().add(blocos.get(i).getBloco());
           
            posX += blocos.get(i).getBloco().getWidth() + 30;
            if(posX +  blocos.get(i).getBloco().getWidth() + 30 >= getWidth()){ 
                posX = 43;
                posY +=  blocos.get(i).getBloco().getHeight() + 20;
            }
        }
        
        //Pontos
        pontos.setLayoutX(120);
        pontos.setLayoutY(7);
        pontos.setText("0");
        pontos.setStyle("-fx-text-fill: #FFFFFF");
        pontos.setFont(new Font("Impact", 23));
        
        //Vidas
        vidas.setLayoutX(745);
        vidas.setLayoutY(7);
        vidas.setText(vidasOff+"");
        vidas.setStyle("-fx-text-fill: #FFFFFF");
        vidas.setFont(new Font("Impact", 23));
        
        bola.add(new Bolinha(3, 3, 400, 370, painel));
        
        getChildren().add(bola.get(0).getBola());
        getChildren().add(raquete.getRaquete());
        getChildren().add(pontos);
        getChildren().add(vidas);
    }
    
    public void JogarPausar(){
        
        Media somVida = new Media(new File("src/musicas/GanharVida.mp3").toURI().toString());
        tocarVida = new MediaPlayer(somVida);   
        
        AnimationTimer timerRaquete = new AnimationTimer(){
            @Override
            public void handle(long now){
                if(!paused)
                    raquete.Mover();
            }
        };
        
        timer = new AnimationTimer(){
            @Override
            public void handle(long now){               
                if(blocos.size() == 0 || vidasOff == 0){
                    
                    getScene().setOnMouseMoved(null);
                    timerRaquete.stop();
                    Finalizar(); 
                }
                else{
                    for(int k = 0; k < bola.size(); k++){
                        bola.get(k).Mover();
                        //Colidir com o chão
                        if(bola.get(k).getBola().getCenterY() + bola.get(k).getBola().getRadius() >= painel.getHeight()-15){
                            vidasOff--;
                            vidas.setText(vidasOff+"");
                        }
                        else if(bola.get(k).verColisao(raquete.getRaquete())){
                            bola.get(k).InvertePassoY();
                        }
                    }
                    
                    for(int u = bola.size()-1; u >= 0; u--){
                        
                        for(int j = blocos.size()-1; j >= 0; j--){
                            
                            if(bola.get(u).verColisao(blocos.get(j).getBloco())){
                                
                                Media somColisao = new Media(new File("src/musicas/Colisao.mp3").toURI().toString());
                                tocarColisao = new MediaPlayer(somColisao);
                                tocarColisao.play();
                                
                                if(blocos.get(j) instanceof BlocoAmarelo){

                                    int p = Integer.parseInt(pontos.getText());
                                    p = Integer.parseInt(pontos.getText());
                                    p += blocos.get(j).getPontos();
                                    if(p >= 100 && cont == false){
                                        
                                        vidasOff++;
                                        vidas.setText(vidasOff+"");
                                        tocarVida.play();
                                        cont = true;
                                    }
                                    pontos.setText(p+"");
                                    getChildren().remove(j);
                                    blocos.remove(j);
                                }
                                else if(blocos.get(j) instanceof BlocoVerde){

                                    blocos.get(j).DiminuiColisoes();
                                    if(blocos.get(j).getColisoes() <= 0){
                                        int p = Integer.parseInt(pontos.getText());
                                        p += blocos.get(j).getPontos();        
                                        if(p >= 100 && cont == false){

                                            vidasOff++;
                                            vidas.setText(vidasOff+"");
                                            tocarVida.play();
                                            cont = true;
                                        }
                                        pontos.setText(p+"");
                                        getChildren().remove(j);
                                        blocos.remove(j);
                                    }
                                    else
                                        blocos.get(j).Mover(); 
                                }
                                else if(blocos.get(j) instanceof BlocoAzul){
                                    try{
                                        bola.add(new Bolinha(-bola.get(u).getPassox(), -bola.get(u).getPassoy(), 400, 300, painel));
                                        getChildren().add( bola.get(bola.size()-1).getBola());
                                    }catch(FileNotFoundException ex){
                                        Logger.getLogger(ArkanoidGame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    int p = Integer.parseInt(pontos.getText());
                                    p += blocos.get(j).getPontos();                                   
                                    if(p >= 100 && cont == false){
                                        
                                        vidasOff++;
                                        vidas.setText(vidasOff+"");
                                        tocarVida.play();
                                        cont = true;
                                    }
                                    pontos.setText(p+"");
                                    getChildren().remove(j);
                                    blocos.remove(j);
                                    flag = 1;
                                }
                                else if(blocos.get(j) instanceof BlocoBranco){
                                    bola.get(u).setPassox(bola.get(u).getPassox() * 2);
                                    bola.get(u).setPassoy(bola.get(u).getPassoy() * 2);
                                    bola.get(u).startTimer();
                                    int p = Integer.parseInt(pontos.getText());
                                    p += blocos.get(j).getPontos();  
                                    if(p >= 100 && cont == false){
                                        
                                        vidasOff++;
                                        vidas.setText(vidasOff+"");
                                        tocarVida.play();
                                        cont = true;
                                    }
                                    pontos.setText(p+"");
                                    getChildren().remove(j);
                                    blocos.remove(j);
                                }
                                bola.get(u).InvertePassoY();
                                break;
                            }
                        }  
                    }
                }
            }
        };
        
        timer.start();
        getScene().setOnKeyPressed(e -> {
            if(!paused){
                timerRaquete.start();
                raquete.trocarDirecao(e);
            }
            if(e.getCode() == KeyCode.P){
                timer.stop();
                tocarJogo.stop();
                this.setStyle("-fx-background-image: url('imagens/pause.gif')");
                paused = true;
            }
            else if(e.getCode() == KeyCode.R){
                timer.start();
                tocarJogo.play();
                this.setStyle("-fx-background-image: url('imagens/teladefundo.gif')");
                paused = false;
            }
            else if(e.getCode() == KeyCode.S){
                System.exit(0);
            }
            else if(e.getCode() == KeyCode.V){
                timer.stop();
                getScene().setOnMouseMoved(null);
                timerRaquete.stop();
                tocarJogo.stop();
                TelaInicial();   
            }   
        });
        
        getScene().setOnKeyReleased(e -> {
            if(!paused)
                timerRaquete.stop();
        });
        
        getScene().setOnMouseMoved(e->{
            if(!paused){
                getScene().setCursor(Cursor.HAND);
                raquete.Mover((int)(e.getSceneX()-55));
            }           
        });
    }
    
    public void Finalizar(){
    
        tocarJogo.stop();
        timer.stop();
        ResetarTela();
        
        Media somMorte1 = new Media(new File("src/musicas/PerdeuI.mp3").toURI().toString());
        tocarMorte1 = new MediaPlayer(somMorte1);
        Media somMorte2 = new Media(new File("src/musicas/PerdeuII.mp3").toURI().toString());
        tocarMorte2 = new MediaPlayer(somMorte2);
        
        Media somGanhou = new Media(new File("src/musicas/Ganhou.mp3").toURI().toString());
        tocarGanhou = new MediaPlayer(somGanhou);
        
        if(vidasOff <= 0){
            this.setStyle("-fx-background-image: url('imagens/gameover.gif')");
            tocarMorte1.play();
            tocarMorte2.play();
            
            getScene().setOnKeyPressed(e ->{
            
                if(e.getCode() == KeyCode.S){
                    tocarMorte1.stop();
                    tocarMorte2.stop();
                    TelaInicial();
                }
                else if(e.getCode() == KeyCode.N)
                    System.exit(0);
            });    
        }
        else{
            this.setStyle("-fx-background-image: url('imagens/ganhou.gif')");
            tocarGanhou.play();
            
            getScene().setOnKeyPressed(e ->{
                    
                if(e.getCode() == KeyCode.S){
                    tocarGanhou.stop();
                    TelaInicial();
                }
                else if(e.getCode() == KeyCode.N)
                    System.exit(0);
            }); 
        }
    }
    
    public int getVidas(){
        return vidasOff;
    }

    public void setVidas(int vidas){
        this.vidasOff = vidas;
    }

    public int getEstado(){
        return estado;
    }

    public void setEstado(int estado){
        this.estado = estado;
    }
}