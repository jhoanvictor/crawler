package com.crawler.model;

import java.time.LocalDate;

public record Noticia(String titulo, String link, LocalDate data) {}
