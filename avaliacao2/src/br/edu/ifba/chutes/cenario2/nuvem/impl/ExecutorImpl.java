package br.edu.ifba.chutes.cenario2.nuvem.impl;

import br.edu.ifba.chutes.cenario2.borda.impl.CalcularMedia;
import br.edu.ifba.chutes.cenario2.borda.impl.DiagonosticoVelocidade;
import br.edu.ifba.chutes.cenario2.borda.sensores.SensoresImpl;
import br.edu.ifba.chutes.cenario2.modelos.Chute;
import br.edu.ifba.chutes.cenario2.modelos.Partida;
import br.edu.ifba.chutes.cenario2.nuvem.executor.Executor;

public class ExecutorImpl extends Executor{

    private Partida partida = null;
    private SensoresImpl sensores = null;

    CalcularMedia calculadorMedia;
    DiagonosticoVelocidade diagnostico = new DiagonosticoVelocidade();

    public ExecutorImpl(Partida partida, SensoresImpl sensores, int totalDeLeituras){
        super(totalDeLeituras);

        this.partida = partida;
        this.sensores = sensores;
        this.calculadorMedia = new CalcularMedia();
    }

    @Override
    public void processarLeitura(int leituraAtual){
        if(sensores.temLeitura()){
            Chute leitura = sensores.getLeitura();
            partida.onLeitura(leitura);

            double chute = calculadorMedia.atuar(partida.getLeituras());
            String verificarChutes = diagnostico.atuar(partida.getLeituras());

            System.out.println("Leitura registrada no chute " + leituraAtual + " da partida " + partida.getId() + ", " + verificarChutes + " Velocidade: " + chute + " km/h");
        }
    }
    
}
