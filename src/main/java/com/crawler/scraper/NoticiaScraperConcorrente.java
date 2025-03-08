package com.crawler.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoticiaScraperConcorrente {

    private static final Logger logger = LoggerFactory.getLogger(NoticiaScraperConcorrente.class);

    // Definição dos sites e seus seletores
    private static final Map<String, String> SITES = Map.of(
            "https://g1.globo.com/", "a.feed-post-link",
            "https://www.bbc.com/portuguese", "li.bbc-jw2yjd a",
            "https://www.cnnbrasil.com.br/", "figcaption.block--manchetes__caption a"
    );

    public void buscarNoticiasComThreads(){
        int numThreads = 3; // Número de threads no pool
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Lista para armazenar as tarefas
        List<Future<Void>> tasks = new CopyOnWriteArrayList<>();

        logger.info("🚀 Iniciando scraping concorrente...\n");
        for (Map.Entry<String, String> site : SITES.entrySet()) {
            String url = site.getKey();
            String seletor = site.getValue();

            // Criamos uma tarefa assíncrona para cada site
            Future<Void> task = executor.submit(() -> {
                processarSite(url, seletor);
                return null;
            });

            tasks.add(task);
        }

        // Aguarda todas as tarefas terminarem
        for (Future<Void> task : tasks) {
            try {
                task.get(); // Bloqueia até a tarefa terminar
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Erro ao processar um site: " + e.getMessage());
            }
        }

        // Encerra o executor
        executor.shutdown();
        logger.info("\n✅ Scraping finalizado!");
    }

    private static void processarSite(String url, String seletor) {

        String threadName = Thread.currentThread().getName();
        logger.debug("🔄 [{}] Iniciando scraping de: {}", threadName, url);

        try {

            // Simula um tempo de resposta aleatório (1 a 3 segundos)
            //Thread.sleep(new Random().nextInt(2000) + 1000);

            Document doc = Jsoup.connect(url).get();
            Elements elementos = doc.select(seletor);

            //logger.info("🔹 [{}] Notícias de {}:", threadName, url);

            StringBuilder textSaida = new StringBuilder();
            textSaida.append(String.format("🔹 [%s] Notícias de %s:%n", threadName, url));

            for (Element elemento : elementos) {
                String titulo = elemento.text();
                String link = elemento.absUrl("href");

                textSaida.append(String.format("📰 [%s] %s | 🔗 %s%n", threadName, titulo, link));
                //logger.info("📰 [{}] {} | 🔗 {}", threadName, titulo, link);
            }

            logger.info(textSaida.toString());
        } catch (IOException  e) {
            logger.error("❌ [{}] Erro ao acessar {}: {}", threadName, url, e.getMessage());
        }
    }

}
