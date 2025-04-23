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
            [reagent.dom :as dom]
            [ajax.core :refer [GET POST]]))
      
(defn send-message! [fields]
  (POST "/message"
      {:format :json
       :headers {"Accept" "application/transit+json"
                 "x-csrf-token" (.-value(.getElementById js/document "token"))}
       :params @fields
       :handler #(.log js/console (str "response" %))
       :error-handler #(.error js/console (str "error:" %))}))

(defn message-form []
  (let [fields (r/atom {})]
    (fn []
      [:div
       [:div.field
        [:label.label {:for :name} "Name"]
        [:input.input
         {:type :text
          :name :name
          :on-change #(swap! fields
                             assoc :name (-> % .-target .-value))
          :value (:name @fields)}]]
       [:div.field
        [:label.label {:for :message} "Message"]
        [:textarea.textarea
         {:name :message
          :value (:message @fields)
          :on-change #(swap! fields
                             assoc :message (-> % .-target .-value))}]]
       [:input.button.is-primary
        {:type :submit 
         :on-click #(send-message! fields)
         :value "comment"}]])))
 

(defn home []
  [:div.content>div.columns.is-centered>div.column.is-two-thirds
   [:div.content>div.columns
    [message-form]]])

;; (defn home []
;;   [:div.content>div.columns.is-centered>div.column.is-two-thirds
;;    [:div.content>div.columns
;;     "Welcome to the Guestbook!"]])



;(dom/render [home] (.getElementById js/document "content"))
(dom/render
[home]
 (.getElementById js/document "content"))


