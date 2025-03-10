package com.crawler.scraper;

import com.crawler.model.Noticia;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NoticiaScraper {

    public List<Noticia> buscarNoticias(String url, String seletorTitulo, String seletorLink) {
        List<Noticia> noticias = new ArrayList<Noticia>();

        try {
            Document document = Jsoup.connect(url).get();

            Elements elementos = document.select(seletorTitulo);

            for (Element elemento : elementos) {

                String titulo = elemento.text();
                String link = elemento.absUrl("href");
                LocalDate dataPublicacao = LocalDate.now();

                noticias.add(new Noticia(titulo, link, dataPublicacao));
            }

        } catch (IOException e) {
            System.err.println("Erro ao buscar notícias de " + url + ": " + e.getMessage());
        }

        return noticias;
    }



}
