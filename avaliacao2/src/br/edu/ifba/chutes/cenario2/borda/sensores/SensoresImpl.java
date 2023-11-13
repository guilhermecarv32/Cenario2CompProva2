package br.edu.ifba.chutes.cenario2.borda.sensores;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import br.edu.ifba.chutes.cenario2.borda.impl.DiagonosticoVelocidade;
import br.edu.ifba.chutes.cenario2.modelos.Chute;

public class SensoresImpl implements Sensores<Chute>{
    
    private static final int VELOCIDADE_MEDIA = 80;
    private static final int RPM_MEDIA = 30;
    private static final int FORCA_MEDIA = 50;
    private static final int OSCILACAO_MAXIMA = 100;
    private static final int TOLERANCIA_DE_OSCILACAO = 20;
    private static final int LIMITE_LEITURAS = 10;

    private Queue<Chute> ultimasLeituras = new LinkedList<>();
    private Chute leituraRecente = new Chute(VELOCIDADE_MEDIA, RPM_MEDIA, FORCA_MEDIA);
    
    public SensoresImpl(){

    }

@Override
public boolean temLeitura(){
    boolean tem = false;

    Random randomizador = new Random();
    
    int guardarOscilacao = randomizador.nextInt(OSCILACAO_MAXIMA);
    boolean somar = randomizador.nextBoolean();
    
    int velocidade = (int) (somar? VELOCIDADE_MEDIA + (VELOCIDADE_MEDIA * guardarOscilacao / 100):
    VELOCIDADE_MEDIA - (VELOCIDADE_MEDIA * guardarOscilacao / 100));

    int rpm = (int) (somar? RPM_MEDIA + (RPM_MEDIA * guardarOscilacao / 100):
    RPM_MEDIA - (RPM_MEDIA * guardarOscilacao / 100));
    
    int forca = (int) (somar? FORCA_MEDIA + (FORCA_MEDIA * guardarOscilacao / 100):
    FORCA_MEDIA - (FORCA_MEDIA * guardarOscilacao / 100));

    if ((Math.abs(velocidade - leituraRecente.getVelocidade()) > TOLERANCIA_DE_OSCILACAO) && ((Math.abs(rpm - leituraRecente.getRPM()) > TOLERANCIA_DE_OSCILACAO)) && ((Math.abs(forca - leituraRecente.getForca()) > TOLERANCIA_DE_OSCILACAO))){
        tem = true;

        leituraRecente = new Chute(velocidade, rpm, forca);
        ultimasLeituras.add(leituraRecente);

        if(ultimasLeituras.size() > LIMITE_LEITURAS){
            ultimasLeituras.remove();
        }

        List<Chute> listaChutes = new ArrayList<>();
        listaChutes.addAll(ultimasLeituras);

        leituraRecente.setDiagnostico(new DiagonosticoVelocidade().atuar(listaChutes));
    }

    return tem;
}

@Override
public Chute getLeitura(){
    return leituraRecente;
}

}
