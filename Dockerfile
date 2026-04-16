# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests


# Stage 2: Runtime
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Install Tesseract + wget
RUN apt-get update && \
    apt-get install -y tesseract-ocr wget && \
    rm -rf /var/lib/apt/lists/*

# Download trained data
RUN mkdir -p /usr/share/tesseract-ocr/4.00/tessdata && \
    wget https://github.com/tesseract-ocr/tessdata_best/raw/main/eng.traineddata \
    -O /usr/share/tesseract-ocr/4.00/tessdata/eng.traineddata

# Set env
ENV TESSDATA_PREFIX=/usr/share/tesseract-ocr/4.00/tessdata

# ✅ IMPORTANT: stage name must match EXACTLY
COPY --from=build /app/target/*.jar app.jar

COPY uploads /app/uploads

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]