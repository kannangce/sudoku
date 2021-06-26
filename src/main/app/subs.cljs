(ns app.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
  :grid-data
  (fn [db _]
      (get-in db [:grid-data])))

(reg-sub
  :solution
  (fn [db _]
      (get-in db [:solution])))