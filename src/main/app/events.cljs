(ns app.events
  (:require [re-frame.core :refer [reg-event-db]]
            [app.utils :refer [cleanse get-current-time-in-secs millis-to-secs]]
            [app.db :refer [initial-app-db]]))

(reg-event-db
  :solve
  (fn [db [_ _]]
      (assoc-in db
                [:grid-data]
                (get db :solution))))

(reg-event-db
  :data-updated
  (fn [db [_ [row col] data]]
      (let [updated-data (assoc-in db
                                   [:grid-data row col]
                                   (cleanse data))
            solved? (= (get updated-data :grid-data) (get updated-data :solution))
            solved-time (if solved? (.now js/Date) 0)]
           (-> updated-data
               (assoc-in [:solved?] solved?)
               (assoc-in [:solved-at] (millis-to-secs solved-time))))))


(reg-event-db
  :pause
  (fn [db [_ _]]
      (let [start-time (get db :start-time)
            curr-time (get-current-time-in-secs)
            curr-offset (get db :time-offset)]
           (-> db
               (assoc-in [:paused?]
                         (not (get db :paused)))
               (assoc-in [:time-offset]
                         (+ (- curr-time start-time) curr-offset))
               (assoc-in [:start-time]
                         curr-time)))))

(reg-event-db
  :level-selected
  (fn [db [_ selected-level]]
      (merge db (initial-app-db))))