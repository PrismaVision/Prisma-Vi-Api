FROM ghcr.io/graalvm/graalvm-community:21 AS builder
ENV LANG=en_US.UTF-8 \
    LANGUAGE=en_US:en \
    LC_ALL=en_US.UTF-8
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw && sed -i 's/\r$//' ./mvnw
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw package -Pnative -DskipTests

FROM gcr.io/distroless/cc-debian12 AS final
WORKDIR /app

COPY --from=builder /app/target/prisma-vi .

EXPOSE 8080
ENV PROFILE="prod"
USER nonroot:nonroot

CMD ["./prisma-vi"]