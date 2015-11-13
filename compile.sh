find . -name "*.java" | xargs javac -cp .:la4j.jar
java -cp .:la4j.jar Main
