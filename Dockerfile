FROM graalvm/graalvm-ce:ol9-java21-24.0.2 AS builder

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw -Pnative clean package


FROM gcr.io/distroless/base-debian12 AS runtime

ARG APP_NAME=prisma-vi

WORKDIR /app

COPY --from=builder /app/target/${APP_NAME} .

EXPOSE 8080

ENV PROFILE="prod"

USER nonroot:nonroot

ENTRYPOINT ["./${APP_NAME}"]