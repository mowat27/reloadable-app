(ns leiningen.new.reloadable-app
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "reloadable-app"))

(defn reloadable-app
  "FIXME: write documentation"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' reloadable-app project.")
    (->files data
             ["src/{{sanitized}}/foo.clj" (render "foo.clj" data)])))
