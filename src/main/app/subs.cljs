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

(reg-sub
  :solved?
  (fn [db _]
      (get-in db [:solved?])))


(reg-sub
  :paused?
  (fn [db _]
      (get-in db [:paused?])))

(reg-sub
  :time
  (fn [db _]
      (get-in db [:time])))

(reg-sub
  :start-time
  (fn [db _]
      (get-in db [:start-time])))

(reg-sub
  :time-offset
  (fn [db _]
      (get-in db [:time-offset])))

(reg-sub
  :solved-at
  (fn [db _]
      (get-in db [:solved-at])))