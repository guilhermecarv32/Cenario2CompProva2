package br.edu.ifba.chutes.cenario2.borda.impl;

import java.util.List;

import br.edu.ifba.chutes.cenario2.borda.atuador.Atuador;
import br.edu.ifba.chutes.cenario2.modelos.Chute;

public class CalcularMedia implements Atuador<Chute, Double>{
    
    @Override
	public Double atuar(List<Chute> leituras) {
		
		double somaVelocidades = 0;
        int contador = 0;

        for (Chute chute : leituras) {
            for (Chute outroChute : leituras) {
                somaVelocidades += (chute.getVelocidade() + outroChute.getVelocidade()) / 2;
                contador++;
            }
        }

        if (contador == 0) {
            return 0.0;
        }

        return (somaVelocidades / contador);
	}
}
