FROM maven:latest

RUN mkdir --parents /usr/src/app
WORKDIR /usr/src/app

ENV message_broker_address="activemq:61616"

# selectively add the POM file
ADD pom.xml /usr/src/app/
# get all the downloads out of the way
RUN mvn verify clean --fail-never

ADD . /usr/src/app
RUN mvn verify

CMD java -cp ./target/classes:./target/lib/* ServiceImpl