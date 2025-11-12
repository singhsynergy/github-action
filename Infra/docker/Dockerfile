#Build stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /
# Put war into docker
RUN mkdir -p /build/cert
# Add code into docker
COPY . /build/
RUN apt-get -y update
RUN apt-get install -y curl
RUN ls -la /build
#Install confd
RUN curl -s -L https://github.com/kelseyhightower/confd/releases/download/v0.16.0/confd-0.16.0-linux-amd64 -o /usr/local/bin/confd && chmod +x /usr/local/bin/confd
RUN /usr/local/bin/confd -confdir /build/Infra/etcd/etc/confd -onetime -backend vault -node VAULT_ADDR -client-ca-keys /build/Infra/vault-cert/client-cert.pem -client-cert /build/Infra/vault-cert/cert.pem  -client-key /build/Infra/vault-cert/key.pem -auth-type token -auth-token VAULT_TOKEN

RUN openssl pkcs12 -export -out /build/cert/cc.pfx -inkey /build/cert/key.pem -in /build/cert/cert.pem -passout pass:p0lo9ki8
# Put certificate into docker
RUN ls -la /build/cert

#Build the code
RUN ls -la
##RUN --mount=type=cache,mode=0777,target=/root/.m2,rw mvn  -f /build/pom.xml clean package
RUN mkdir -p /root/.m2 && \
    mvn -f /build/pom.xml clean package
##mvn -f /build/pom.xml clean package

#RUN mvn -s /build/settings.xml -f /build/pom.xml clean package
RUN ls -l  /build/target/
#Package stage
FROM azure-harbor-registry.cloudsmartz.com:8443/base-images/openjdk:17-ea-jdk-buster
COPY --from=build /build/target/ms-java-flynas-worker-1.0.0.jar /app/ms-java-flynas-worker1.0.0.jar
COPY --from=build /build/cert /app/cert

#Add user
RUN groupadd -r javauser && useradd -g javauser javauser
WORKDIR /app
RUN ls -la
RUN chown -R javauser:javauser /app
#USER javauser
#Run the jar
ENTRYPOINT ["java","-jar", "ms-java-flynas-worker1.0.0.jar"]

