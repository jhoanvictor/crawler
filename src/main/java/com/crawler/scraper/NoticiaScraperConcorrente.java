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

    private static final Map<String, String> SITES = Map.of(
            "https://g1.globo.com/", "a.feed-post-link",
            "https://www.bbc.com/portuguese", "li.bbc-jw2yjd a",
            "https://www.cnnbrasil.com.br/", "figcaption.block--manchetes__caption a"
    );

    public void buscarNoticiasComThreads(){
        int numThreads = 3;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        List<Future<Void>> tasks = new CopyOnWriteArrayList<>();

        logger.info("🚀 Iniciando scraping concorrente...\n");
        for (Map.Entry<String, String> site : SITES.entrySet()) {
            String url = site.getKey();
            String seletor = site.getValue();

            Future<Void> task = executor.submit(() -> {
                processarSite(url, seletor);
                return null;
            });

            tasks.add(task);
        }


        for (Future<Void> task : tasks) {
            try {
                task.get();
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Erro ao processar um site: " + e.getMessage());
            }
        }

        executor.shutdown();
        logger.info("\n✅ Scraping finalizado!");
    }

    private static void processarSite(String url, String seletor) {

        String threadName = Thread.currentThread().getName();
        logger.debug("🔄 [{}] Iniciando scraping de: {}", threadName, url);

        try {
            Document doc = Jsoup.connect(url).get();
            Elements elementos = doc.select(seletor);

            StringBuilder textSaida = new StringBuilder();
            textSaida.append(String.format("🔹 [%s] Notícias de %s:%n", threadName, url));

            for (Element elemento : elementos) {
                String titulo = elemento.text();
                String link = elemento.absUrl("href");

                textSaida.append(String.format("📰 [%s] %s | 🔗 %s%n", threadName, titulo, link));
            }

            logger.info(textSaida.toString());
        } catch (IOException  e) {
            logger.error("❌ [{}] Erro ao acessar {}: {}", threadName, url, e.getMessage());
        }
    }

}
