# LivelyGig Product

This is the skeleton of a web application which will use the LivelyGig protocol along with SpecialK. Currently, the front-end is written in Scala.js and routing is done with spray. For now, the front end is just a file browser for demonstration purposes.

## Documentation

LivelyGig docs are located in the `docs` folder. You can browse the docs as HTML by pointing your browser to the index file located at `docs/html`.

If you need to re-generate the docs, you can run the command `sbt leika:site`.

## Getting Started

To run the LivelyGig web application:

1.) Install sbt: http://www.scala-sbt.org/download.html

2.) In the Product directory, run:
```
sbt
```
This will open sbt's interactive mode.

3.) While in sbt's interactive mode:
```
re-start
```
and wait until the application is started. This will take a long time the first time it is run as it needs to download dependencies.

Lastly, go to `localhost:8080` in your browser.

To stop the application:
```
re-stop
```


 
