find . -name "*.java" | grep -v la4j | xargs javac -cp .:la4j.jar
java -cp .:la4j.jar demo/Demo2
java -cp .:la4j.jar demo/Demo3
java -cp .:la4j.jar demo/Demo4
java -cp .:la4j.jar demo/Demo5
