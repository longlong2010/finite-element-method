find . -name "*.java" | xargs javac -cp .:numerical-method.jar
java -classpath .:numerical-method.jar Main
