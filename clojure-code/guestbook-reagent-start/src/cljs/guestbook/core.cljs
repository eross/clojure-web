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
            [ajax.core :refer [GET POST]]
            [clojure.string :as string]
            [guestbook.validation :refer [validate-message]]))
      
(defn send-message! [fields errors]
  (if-let [validate-errors (validate-message @fields)]
    (reset! errors validate-errors)
    (POST "/message"
      {:format :json
       :headers {"Accept" "application/transit+json"
                 "x-csrf-token" (.-value (.getElementById js/document "token"))}
       :params @fields
       :handler (fn [r]
                  (.log js/console (str "response:" r))
                  (reset! errors nil))
       :error-handler (fn [e]
                        (.log js/console (str e))
                        (reset! errors (-> e :response :errors)))})))


(defn errors-component [errors id]
  (when-let [error (id @errors)]
    [:div.notification.is-danger (string/join error)]))


(defn message-form []
  (let [fields (r/atom {})
        errors (r/atom {})]
    (fn []
      [:div
       [:p "Name: " (:name @fields)]
       [:p "Message: " (:message @fields)]
       [errors-component errors :server-error] 
       [:div.field
        [:label.label {:for :name} "Name"]
        [errors-component errors :name] 
        [:input.input
         {:type :text
          :name :name
          :on-change #(swap! fields
                             assoc :name (-> % .-target .-value))
          :value (:name @fields)}]] 
       [:div.field
        [:label.label {:for :message} "Message"]
        [errors-component errors :message]
        [:textarea.textarea
         {:name :message
          :value (:message @fields)
          :on-change #(swap! fields
                             assoc :message (-> % .-target .-value))}]]
       [:input.button.is-primary
        {:type :submit 
         :on-click #(send-message! fields errors)
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


