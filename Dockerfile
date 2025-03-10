# Usa uma imagem oficial do Java 17
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o JAR do projeto para dentro do contêiner
COPY target/crawler.jar /app/crawler.jar

# Comando para rodar o scraper
CMD ["java", "-jar", "crawler.jar"]
