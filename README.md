# Lightweight ClojureScript, React.js, Figwheel skeleton

This repository is based on Bruce Hauman's Figwheel quick-start project [[GitHub](https://github.com/bhauman/lein-figwheel/wiki/Quick-Start)], but updated to use a recent Sablono and React and ReactDOM.

I intend to use it to demonstrate some ClojureScript-specific topics.

# Build instructions
Install [leiningen](http://leiningen.org/#install).

In the following, any commands starting with `$ ` indicates it should be run in a shell terminal.

## Development mode with Figwheel's auto-code-reload

### Step 1: start Figwheel
```
$ lein clean
$ rlwrap lein figwheel dev
```

(`rlwrap` is not required but recommended: it gives you arrow-key support and history. If on Mac with brew, install with: `$ brew install rlwrap`.)

Figwheel compiles your ClojureScript code and puts it into `resource/public/cljs/`. It also starts a Figwheel server that the compiled ClojureScript app knows to connect to as soon as its opened in a browser. As you edit and save ClojureScript files, Figwheel automatically recompiles them to JavaScript and tells your browser to reload them.

The above commands open a ClojureScript REPL connected to your browser session.

### Step 2: start webserver
In another terminal,
```
$ lein run
```

Then open [localhost:5300/index.html](http://localhost:5300/index.html).

This starts the webserver, the application that listens to connections from browsers and responds to their requests, including for files. This serves HTML and JavaScript and other ancillary assets to the browser when it asks for it. Note that this server is totally independent of the Figwheel server (which lives on a different port).

## Deploy mode with advanced Google Closure compilation

### Option/step 1: build minified and optimized JavaScript assets
```
$ lein clean
$ lein cljsbuild once min
```
Your ClojureScript code is compiled to JavaScript, which then the Google Closure compiler optimizes by eliminating dead code and minification to produce a single JavaScript file in `resources/public/cljs/main.js`. This, along with HTML and any CSS and static assets, can be served by a traditional reverse proxy like Nginx, Apache, etc. However, the Clojure webserver, which handles dynamic requests, can also be packaged into a deployable asset:

### Option/step 2: Java UberJAR
```
$ lein clean
$ lein uberjar
```
This will first automatically invoke `lein cljsbuild once min` as above to build your ClojureScript front-end app, and then package the Clojure/JVM webserver into a single UberJAR: `target/fullstack-cljs-tutorial-0.1.0-SNAPSHOT-standalone.jar` (it also produces a non-standalone JAR, `target/fullstack-cljs-tutorial-0.1.0-SNAPSHOT.jar`).

The former *standalone* JAR file, can be started with
```
$ java -jar target/fullstack-cljs-tutorial-0.1.0-SNAPSHOT-standalone.jar
```
This starts the webserver and will host your ClojureScript application when connected to [localhost:5300/index.html](http://localhost:5300/index.html). The HTML and JavaScript assets are stored inside the JAR file, so this JAR is all you need to deploy your entire app, frontend and backend.

(However, you can always run Nginx, etc., in front of this Clojure webserver, configured to serve static assets (built in step 1) itself, and to route dynamic requests to the Clojure webserver, running on port 5300.)

(For completeness, I'll note that the other, *non*-standalone JAR file does not include Clojure itself, or any of the Clojure dependencies like http-kit and Compojure. It's intended to be used with Maven (well beyond the scope of this tutorial).)

# Notes

## Rename
To rename the project from `fullstack-cljs-tutorial` to whatever else, find and replace all references to `fullstack-cljs-tutorial` with the new name in cljs and clj files (don't forget `project.clj`; handy shell tip: `$ grep -rsn fullstack-cljs-tutorial *`), then rename the `src/fullstack_cljs_tutorial` directory appropriately (remember, dashes in Clojure namespaces must be replaced by underscores `_`).
