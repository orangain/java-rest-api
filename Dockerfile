# Build
FROM openjdk:8u171-jdk as builder

WORKDIR /work

COPY gradlew gradlew.bat ./
COPY gradle ./gradle
RUN ./gradlew -version

COPY *.gradle ./
COPY src ./src
RUN ./gradlew --no-daemon build -x test

RUN cd build/distributions && tar xvf *.tar

# Run
FROM openjdk:8u171-jre

COPY --from=builder /work/build/distributions/java-rest-api /opt/app/

CMD ["/opt/app/bin/java-rest-api"]