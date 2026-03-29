# Stage 2: Runtime
FROM eclipse-temurin:17-jdk
WORKDIR /app

RUN apt-get update && \
    apt-get install -y tesseract-ocr wget && \
    rm -rf /var/lib/apt/lists/*

# ✅ Manually download trained data (guaranteed fix)
RUN mkdir -p /usr/share/tesseract-ocr/4.00/tessdata && \
    wget https://github.com/tesseract-ocr/tessdata_best/raw/main/eng.traineddata \
    -O /usr/share/tesseract-ocr/4.00/tessdata/eng.traineddata

# ✅ Set correct env
ENV TESSDATA_PREFIX=/usr/share/tesseract-ocr/4.00/tessdata

COPY --from=build /app/target/*.jar app.jar
COPY uploads /app/uploads

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]