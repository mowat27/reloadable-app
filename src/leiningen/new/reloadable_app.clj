(ns leiningen.new.reloadable-app
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "reloadable-app"))

(defn reloadable-app
  "Create a new reloadable project"
  [name]
  (let [data {:name                    name
              :sanitized               (name-to-path name)
              :snake-cased-name        (clojure.string/replace name #"_" "-")
              :clojure-version         "1.5.1"
              :tools-namespace-version "0.2.4"
              :component-lib-version   "0.2.1"}]
    (main/info "Generating fresh 'lein new' reloadable-app project.")
    (->files data
             ["project.clj"                  (render "project.clj" data)]
             ["dev/user.clj"                 (render "dev/user.clj" data)]
             ["src/{{sanitized}}/system.clj" (render "app/system.clj" data)])))
