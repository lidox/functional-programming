# Functional Programming

## Installation
First check out [Leinigen](https://github.com/technomancy/leiningen) installation guide.

Than install eclipse plugin via market place: Counterclockwise 0.35.0STABLE001

## Eclipse
Best practice to use Eclipse IDE

### Create and run project
Create clojure project. Let default configuration.

Now go to core.clj and create run configuration --> give a name --> apply and run

Next right click on cire.clj --> clojure --> load file in RAPL

## Intellij

#### Installation
First check this [Plugin](https://cursive-ide.com/userguide) and get [free non-commercial license](https://cursive-ide.com/buy.html)

#### Run example code
Now make sure you can run your code.

Create a clojure project and create a clojure file to put in following code.
```clojure
(get {:a 1 :b 2 [{}] 3} :a)
```

Create run configuration: RUN --> Edit Configurations.. --> '+' --> Clojure REPL --> LOCAL --> Apply (let all default)
![image](https://cloud.githubusercontent.com/assets/7879175/21004332/06fb77ce-bd30-11e6-895a-ac8591ddf30a.png)

Now run your run-configuration.
The REPL-Sever should start now showing something like that:
```clojure
Starting nREPL server...
Connecting to local nREPL server...
Clojure 1.8.0
nREPL server started on port 38550 on host 127.0.0.1
```

Now mark a line or right-click on the file and select:

REPL --> Load file in REPL / send '...' to REPL to execute the code.

#### What is REPL?
It's "just" a Clojure-Interpreter!

#### What is CLOJURE? 
It's a programming language and predominantly a functional programming language.
