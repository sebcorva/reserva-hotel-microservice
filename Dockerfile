FROM eclipse-temurin:22-jdk AS buildstage 
 
RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .
COPY src /app/src
#cambiar la wallet
COPY Wallet_RI9ZRK174M80ADKH /app/wallet

ENV TNS_ADMIN=/app/wallet

RUN mvn clean package
#RUN mvn clean package -DskipTests
FROM eclipse-temurin:22-jdk 

WORKDIR /app
#cambiar la app.jar
COPY --from=buildstage /app/target/*.jar /app/app.jar
#cambiar wallet
COPY Wallet_RI9ZRK174M80ADKH /app/wallet

ENV TNS_ADMIN=/app/wallet
EXPOSE 8080

#cambiar la app.jar
ENTRYPOINT [ "java", "-jar","/app/app.jar" ]