FROM openjdk:17

# Instalar OpenJDK 17 e JavaFX
RUN apt-get update && apt-get install -y openjdk-17-jdk openjfx

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o JAR gerado para o contêiner
COPY target/MundoAnimalFrontEnd-1.0-SNAPSHOT.jar app.jar

# Comando para rodar o aplicativo JavaFX
CMD ["java", "-jar", "app.jar"]
