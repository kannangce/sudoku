(ns app.db
  (:require [re-frame.core :as rf]
            [app.sudoku.generator :refer [generate-data]]
            [app.sudoku.solver-new :refer [solve]]
            [app.utils :refer [get-current-time-in-secs]]
            ))

(defn- initial-app-db []
       (let [value (generate-data)
             problem-data (:data value)
             solution (:solution value)]
            (.log js/console (str "init-data" problem-data))
            {:generated   problem-data
             :grid-data   problem-data
             :solution    solution
             :time-offset 0
             :start-time  (get-current-time-in-secs)
             :solved?     false
             :solved-at   0
             :paused?     false}))


(rf/reg-event-db
  :initialize-db
  (fn
    [_ _]
    (let [db-data (initial-app-db)]
         (.log js/console (str "init-data" db-data))
         db-data)))