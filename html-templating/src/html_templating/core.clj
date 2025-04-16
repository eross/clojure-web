(ns html-templating.core
  (:require [selmer.parser :as selmer]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(selmer/render "<h1>Hello {{name}}!</h1>" {:name "World"})

(defn -main
  "Main entry point for the application."
  [& args]
  (foo "Hello, World!"))