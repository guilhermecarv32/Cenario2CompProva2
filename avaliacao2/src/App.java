import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.github.javafaker.Faker;

import br.edu.ifba.chutes.cenario2.borda.sensores.SensoresImpl;
import br.edu.ifba.chutes.cenario2.modelos.Partida;
import br.edu.ifba.chutes.cenario2.nuvem.impl.ExecutorImpl;

public class App {
    private static final int TOTAL_DE_PARTIDAS = 10;
    private static final int TOTAL_DE_LEITURAS = 3;

    private static List<Thread> executores = new ArrayList<>();

    public static void iniciarProcessamentoDeLeituras(Map<Partida, SensoresImpl> partidas){
        for(Entry<Partida, SensoresImpl> item : partidas.entrySet()){
            Partida partida = item.getKey();
            SensoresImpl sensores = item.getValue();

            Thread executor = new Thread(new ExecutorImpl(partida, sensores, TOTAL_DE_LEITURAS));
            executores.add(executor);

            executor.start();
        }
    }

    public static void esperarFinalizacao() throws InterruptedException{
        for(Thread executor : executores){
            executor.join();
        }
    }

    public static Map<Partida, SensoresImpl> gerarPartidas(){
        Faker faker = new Faker(Locale.forLanguageTag("pt-BR"));

        Map<Partida, SensoresImpl> partidas = new TreeMap<>();
        for (int i = 0; i < TOTAL_DE_PARTIDAS; i++){
            int idPartida = faker.number().numberBetween(1, 10);
            Partida partida = new Partida("Partida #" + idPartida);

            partidas.put(partida, new SensoresImpl());
        }

        return partidas;
    }
    public static void main(String[] args) throws Exception {
        Map<Partida, SensoresImpl> partidas = gerarPartidas();

        System.out.println("Iniciando Processamento...");
        iniciarProcessamentoDeLeituras(partidas);
        esperarFinalizacao();

        System.out.println("Processamento Finalizado.");
    }
}
