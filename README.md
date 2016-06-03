# Lightweight ClojureScript, React.js, Figwheel skeleton

This repository is based on Bruce Hauman's Figwheel quick-start project [[GitHub](https://github.com/bhauman/lein-figwheel/wiki/Quick-Start)], but updated to use a recent Sablono and React and ReactDOM.

I intend to use it to demonstrate some ClojureScript-specific topics.

# Build instructions

## Development mode with Figwheel's auto-code-reload

### Step 1: start Figwheel
```
$ lein clean
$ rlwrap lein figwheel dev
```

(`rlwrap` is recommended but not required: it gives you arrow-key support and history. Install with brew: `$ brew install rlwrap`.)

Figwheel compiles your ClojureScript code and puts it into `resource/public/cljs/`. It also starts a Figwheel server that the compiled ClojureScript app knows to connect to as soon as its opened in a browser. As you edit and save ClojureScript files, Figwheel automatically recompiles them to JavaScript and tells your browser to reload them.

### Step 2: start webserver
```
$ lein run
```

Then open [localhost:5309/index.html](http://localhost:5309/index.html).

This starts the traditional webserver (on a totally different port than the Figwheel server): this is the application that listens to connections from browsers and responds to their requests, including for files. This serves HTML and JavaScript and other ancillary assets to the browser when it asks for it.

## Deploy mode with advanced Google Closure compilation

```
$ lein clean
$ lein cljsbuild once min
```

Compiled assets placed in `resources/public`.

# Notes

## Rename
To rename the project from `fullstack-cljs-tutorial` to whatever else, find and replace all references to `fullstack-cljs-tutorial` with the new name in cljs and clj files (don't forget `project.clj`; handy Bash tip: `$ grep -rsn fullstack-cljs-tutorial *`), then rename the `src/fullstack-cljs_tutorial` directory appropriately (remember, dashes in Clojure namespaces must be replaced by underscores `_`).
