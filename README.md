# Lightweight ClojureScript, React.js, Figwheel skeleton

This repository is based on Bruce Hauman's Figwheel quick-start project [[GitHub](https://github.com/bhauman/lein-figwheel/wiki/Quick-Start)], but updated to use a recent Sablono and React and ReactDOM.

I intend to use it to demonstrate some ClojureScript-specific topics.

# Build instructions

## Development mode with Figwheel and auto-code-reload

```
$ lein clean
$ rlwrap lein figwheel dev
```

(`rlwrap` is recommended but not required: it gives you arrow-key support and history. Install with brew: `$ brew install rlwrap`.)

Then open [localhost:5309](http://localhost:5309).

## Deploy mode with advanced Google Closure compilation

```
$ lein clean
$ lein cljsbuild once min
```

Compiled assets placed in `resources/public`.

# Notes

## Rename
To rename the project from `fullstack-cljs-tutorial` to whatever else, find and replace all references to `fullstack-cljs-tutorial` with the new name in cljs and clj files (don't forget `project.clj`; handy Bash tip: `$ grep -rsn fullstack-cljs-tutorial *`), then rename the `src/fullstack-cljs_tutorial` directory appropriately (remember, dashes in Clojure namespaces must be replaced by underscores `_`).
