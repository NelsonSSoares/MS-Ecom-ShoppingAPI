FROM openjdk:17
WORKDIR /app
COPY ./build/libs/EcomShoppingApi-0.0.1-SNAPSHOT-plain.jar ms-ecom-shoppingapi.jar
EXPOSE 8761
ENTRYPOINT java -jar ms-ecom-shoppingapi.jar
