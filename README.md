# finite-element-method
Impelement the finite element method API using Java
## Demo code in demo directory

> init and compile la4j
```bash
git submodule init && git submodule update
find la4j/src/main/java/ -name "*.java" | xargs javac
jar cvf la4j.jar `find la4j/src/main/java/ -name "*.class"`
jar cvf la4j.jar -C la4j/src/main/java/
```

> run the demos
```
sh compile.sh
```

> Demo

![Alt text](http://78rdg5.com1.z0.glb.clouddn.com/fem3.png)
![Alt text](http://78rdg5.com1.z0.glb.clouddn.com/fem4.png)
![Alt text](http://78rdg5.com1.z0.glb.clouddn.com/fem5.png)
