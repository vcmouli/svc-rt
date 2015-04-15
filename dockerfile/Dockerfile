############################################################
# Dockerfile to build spring boot application container
# 1) get ubuntu
# 2) get java
# 3) get gradle
# 4) fetch from git
# 5) gradle build
# 6) run application
############################################################

# get ubuntu and java. Note: oracle-java is based on ubuntu
FROM dockerfile/java:oracle-java7

MAINTAINER Chandramouli Vaidyanathan

# In case someone loses the Dockerfile
RUN rm -rf /etc/Dockerfile
ADD Dockerfile /etc/Dockerfile

RUN apt-get update

# step 3) get Gradle and SpringBoot
RUN curl -s get.gvmtool.net | bash

RUN bash -c "source ~/.gvm/bin/gvm-init.sh && gvm install gradle; exit 0"
RUN bash -c "source ~/.gvm/bin/gvm-init.sh && gvm install springboot; exit 0"
    
# Set Appropriate Environmental Variables
#ENV GRADLE_HOME /usr/bin/gradle
ENV GRADLE_HOME /root/.gvm/gradle/current/bin
ENV PATH $PATH:$GRADLE_HOME/bin

# Add SSH keys
#ADD .ssh /user/deploy/.ssh

RUN git clone https://github.com/vcmouli/svc-rt.git

RUN cd svc-rt && chmod 744 gradlew

RUN cd svc-rt && ./gradlew build

EXPOSE 8080

#CMD ["java","-jar","svc-rt/build/libs/service-runtime-0.1.0.jar"]
#CMD ["/bin/sh", "-c", "'cd svc-rt;java -jar build/libs/service-runtime-0.1.0.jar'"]
CMD cd svc-rt;java -jar build/libs/service-runtime-0.1.0.jar