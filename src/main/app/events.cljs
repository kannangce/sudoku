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
      (assoc-in db
                [:grid-data row col]
                (cleanse data))))
