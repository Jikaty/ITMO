
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /laba5
COPY out/artifacts/Laba5_jar/Laba5.jar laba5.jar
COPY out/artifacts/Laba5_jar/Data.xml /laba5/data/Data.xml
COPY Commands.txt Commands.txt
ENTRYPOINT ["java", "-jar", "laba5.jar"]