find . -name "*.java" | xargs javac -cp .:numerical-method.jar
java -classpath .:numerical-method.jar demo.Demo1
java -classpath .:numerical-method.jar demo.Demo2
