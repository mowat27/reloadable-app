# reloadable-app

A Leiningen template for a component based app using Stuart Sierra's [component](https://github.com/stuartsierra/component) library and reloaded workflow. 

For an introduction to component based systems, please see the documentation for [component](https://github.com/stuartsierra/component) and Stuart's talk from Clojure West 2014.

<a href="http://www.youtube.com/watch?feature=player_embedded&v=13cmHf_kt-Q
" target="_blank"><img src="http://img.youtube.com/vi/13cmHf_kt-Q/0.jpg" 
alt="Components- Just Enough Structure" width="240" height="180" border="10" /></a>

## Usage

This is a very simple template with no special options.  Simply run

```shell
lein new reloadable-app my_project
```

This will create a new Leiningen project with 2 files:

**src/my_project/system.clj**

```clojure
(ns my-project.system
  (:require [com.stuartsierra.component :as component]))

(defn new-system []
  (component/system-map 
   :a-component "Add your components here"))
```

An empty system with no components and a placeholder for the system map to get you started.


**dev/user.clj**

```clojure 
(ns user
  (:require 
   [com.stuartsierra.component :as component]
   [clojure.tools.namespace.repl :refer (refresh refresh-all)]
   [my-project.system :refer (new-system)]))

(def system nil)

(defn init
  "Constructs the current development system."
  []
  (alter-var-root #'system
    (constantly (new-system))))

(defn start
  "Starts the current development system."
  []
  (alter-var-root #'system component/start))

(defn stop
  "Shuts down and destroys the current development system."
  []
  (alter-var-root #'system
                  (fn [s] (when s (component/stop s)))))

(defn go
  "Initializes the current development system and starts it running."
  []
  (init)
  (start))

(defn reset []
  (stop)
  (refresh :after 'user/go))
```

This file contains REPL functions so you can use Stuart Sierra's reloaded workflow as described in the documentation for the component library.

user.clj is ready loaded for you in the development profile.

```clojure
(defproject my_project "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [com.stuartsierra/component "0.2.1"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.4"]]
                   :source-paths ["dev"]}})
```




## Emacs Users

*Note that this is copied directly from Malcolm Sparks' [jig](https://github.com/juxt/jig) library documentation*

You should find yourself typing `(reset)` rather a lot, and soon
even that becomes burdensome. Here's some Emacs code you can paste into
your `$HOME/.emacs.d/init.el` to provide a shortcut.

```clojure
(defun nrepl-reset ()
  (interactive)
  (save-some-buffers)
  (set-buffer "*nrepl*")
  (goto-char (point-max))
  (insert "(user/reset)")
  (nrepl-return))

(global-set-key (kbd "C-c r") 'nrepl-reset)
```

or, if you are using [cider]("http://github.com/clojure-emacs/cider"):

```clojure
(defun cider-repl-reset ()
  (interactive)
  (save-some-buffers)
  (with-current-buffer (cider-current-repl-buffer)
    (goto-char (point-max))
    (insert "(user/reset)")
    (cider-repl-return)))

(global-set-key (kbd "C-c r") 'cider-repl-reset)
```



## License

Copyright © 2014 Adrian Mowat

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
