(ns app.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            ["@smooth-ui/core-sc"
             :refer
             [Normalize ThemeProvider Button Grid Row Col]]
            [app.views.elem :refer [cell grid]]
            [app.sudoku :refer [generate-data]]))

;; This version is not working
(defn app
      []
      (grid (generate-data)))

(defn app1
      []
      (cell {:id        "1-1"
             :value     nil
             :on-change #(js/alert "in cell 1 1")}))

(defn ^:dev/after-load start
      []
      (r/render [app]
                (.getElementById js/document "app")))

(defn ^:export init
      []
      (rf/dispatch-sync [:initialize-db])
      (start))