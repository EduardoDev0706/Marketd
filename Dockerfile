# Estágio 1: Build (Usa imagem do Maven pesada)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
# Copia apenas o pom.xml primeiro para usar o cache de dependências do Docker
COPY pom.xml .
RUN mvn dependency:go-offline
# Copia o código fonte e compila
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Run (Usa imagem levíssima apenas com a JRE)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copia o .jar compilado do Estágio 1
COPY --from=build /app/target/*.jar marketd.jar

# Define o volume para o banco de dados não ser apagado quando o container reiniciar
RUN mkdir -p /app/data
VOLUME /app/data

# Força o Spring a salvar o SQLite dentro do volume mapeado
ENTRYPOINT ["java", "-jar", "marketd.jar", "--spring.datasource.url=jdbc:sqlite:/app/data/marketd.db"]
