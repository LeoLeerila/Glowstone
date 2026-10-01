FROM mariadb:latest
WORKDIR /glowstone
COPY pom.xml .
COPY . /glowstone
COPY GlowstoneV2test.sql /glowstone/

# download dependencies
RUN apt-get update && apt-get install -y openjdk-21-jdk maven libx11-6 libxext6 libxrender1 libxtst6 libxi6 libgtk-3-0 mesa-utils wget unzip \
    && rm -rf /var/lib/apt/lists/*

# JavaFX SDK (not bundled with the JDK)
RUN mkdir -p /javafx-sdk \
    && wget -O javafx.zip https://download2.gluonhq.com/openjfx/21/openjfx-21_linux-x64_bin-sdk.zip \
    && unzip javafx.zip -d /javafx-sdk \
    && mv /javafx-sdk/javafx-sdk-21/lib /javafx-sdk/lib \
    && rm -rf /javafx-sdk/javafx-sdk-21 javafx.zip

RUN mvn package



#ENV DISPLAY=:1
CMD ["java", \
     "--module-path", "/javafx-sdk/lib", \
     "--add-modules", "javafx.controls,javafx.fxml", \
     "-Dprism.order=sw", \
     "-jar", "target/glowstone-1.0-SNAPSHOT.jar" ]