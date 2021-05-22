(ns app.db
  (:require [re-frame.core :as rf]
            [app.utils :as util]
            ))

(def initial-app-db []
  {:vals (util/create-matrix 9 9)})


(rf/reg-event-db
  :initialize-db
  (fn
    [_ _]
    initial-app-db))