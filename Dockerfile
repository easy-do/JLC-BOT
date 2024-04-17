FROM registry.cn-hangzhou.aliyuncs.com/gebilaoyu/openjdk:18.0.1-oraclelinux8

LABEL org.opencontainers.image.authors=gebilaoyu

ENV JVM_OPTS = ""
ENV PARAMS = ""
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

#COPY be/target/app-encrypted.jar /app.jar
COPY be/target/app.jar /app.jar

#ENTRYPOINT ["sh","-c","java -javaagent:app.jar -jar $JAVA_OPTS /app.jar $PARAMS"]
#ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /app.jar $PARAMS"]
ENTRYPOINT ["sh","-c","java -jar --add-opens java.base/sun.reflect.annotation=ALL-UNNAMED -Dpolyglot.engine.WarnInterpreterOnly=false $JAVA_OPTS /app.jar $PARAMS"]
