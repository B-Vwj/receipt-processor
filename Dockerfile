FROM eclipse-temurin:21
RUN mkdir /opt/app
COPY build/libs/receipt-processor-0.0.1.jar /opt/app
EXPOSE 8080
CMD ["java", "-jar", "/opt/app/receipt-processor-0.0.1.jar"]