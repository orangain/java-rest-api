FROM openjdk:8u171-jdk

WORKDIR /work

COPY gradlew gradlew.bat ./
COPY gradle ./gradle
RUN ./gradlew -version

COPY *.gradle ./
COPY src ./src
RUN ./gradlew --no-daemon build -x test

CMD ["./gradlew", "--no-daemon", "run"]
