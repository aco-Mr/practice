FROM java:8
ADD acoApps.jar /data/apps/acoApps.jar
WORKDIR /data/apps/practice/
EXPOSE 8080
ENTRYPOINT ["java", "-Dloader.path=/data/apps/practice/lib", "-jar", "/data/apps/acoApps.jar", ""]