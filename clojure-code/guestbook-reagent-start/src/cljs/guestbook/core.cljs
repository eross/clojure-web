;---
; Excerpted from "Web Development with Clojure, Third Edition",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material,
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose.
; Visit http://www.pragmaticprogrammer.com/titles/dswdcloj3 for more book information.
;---
(ns guestbook.core
  (:require [reagent.core :as r]
            [reagent.dom :as dom]))


;(dom/render [home] (.getElementById js/document "content"))
(dom/render
 [:div
 [:div#hello.content [:h1 "Hello, Auto!"]]
  [:div#hello.content [:h1 "Hello, Auto 2!"]]
  ]
 (.getElementById js/document "content"))

