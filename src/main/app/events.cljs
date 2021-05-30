(ns app.events
  (:require [re-frame.core :refer [reg-event-db]]))

(reg-event-db
  :solve
  (fn [db [_ active-nav]]
      (assoc-in db
                [:grid-data]
                (get db :solution))))