FROM maven:3.6.3-openjdk-11 AS app-build

COPY . /build
WORKDIR /build
RUN mvn test
