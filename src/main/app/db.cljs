(ns app.db
  (:require [re-frame.core :as rf]
            [app.sudoku.generator :refer [generate-data]]
            ))

(defn initial-app-db []
      (let [problem-data (generate-data)]
           (.log js/console (str "init-data" problem-data))
           {:generated problem-data
            :grid-data problem-data}))


(rf/reg-event-db
  :initialize-db
  (fn
    [_ _]
    (let [db-data (initial-app-db)]
         (.log js/console (str "init-data" db-data))
         db-data)))