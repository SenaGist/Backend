# Usa una imagen base con Java 17
FROM openjdk:17-jdk-slim

# Crea un directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR construido desde Maven
COPY target/*.jar app.jar

# Expone el puerto en el que corre tu app (si usas 8080 por defecto)
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
