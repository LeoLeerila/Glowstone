FROM mariadb:latest
WORKDIR /glowstone
COPY pom.xml .
COPY . /glowstone
COPY GlowstoneV2test.sql /glowstone/
RUN apt-get update && apt-get install -y openjdk-21-jdk maven
RUN mvn package
CMD ["java", "-jar", "target/glowstone-1.0-SNAPSHOT.jar" ]