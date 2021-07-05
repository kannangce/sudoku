(ns app.events
  (:require [re-frame.core :refer [reg-event-db]]
            [app.utils :refer [cleanse]]))

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
            solved? (= (get updated-data :grid-data) (get updated-data :solution))]
           (.log js/console solved?)
           (.log js/console updated-data)
           (.log js/console "Solved flag...")
           (assoc-in updated-data [:solved?] solved?))))
