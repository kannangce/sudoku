(ns app.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [app.db]
            ["@smooth-ui/core-sc" :refer
             [Normalize ThemeProvider Button Grid Row Col]]
            [app.views.elem :refer [cell grid controls solved-message]]
            [app.sudoku.generator :refer [generate-data]]
            [app.subs]
            [app.events]
            ))

;; This version is not working
(defn app
      []
      [:<>
       [:> Grid
        [:> Row
         [:> Col
          [grid]]
         [:> Col
          [controls]]
         ]]
       [:> Col
        [solved-message]]])

(defn ^:dev/after-load start
      []
      (r/render [app]
                (.getElementById js/document "app")))

(defn ^:export init
      []
      (rf/dispatch-sync [:initialize-db])
      (start))