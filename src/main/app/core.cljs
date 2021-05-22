(ns app.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            ["@smooth-ui/core-sc"
             :refer
             [Normalize ThemeProvider Button Grid Row Col]]
            [app.views.elem :refer [cell]]))


(defn app
      []
      [:<>
       [:> Normalize]
       [:> Grid {:fluid false}
        [:> Row
         [:> Col (cell {:id 1 :value 1 :on-change #(js/alert (str "changed" %))})]]]])


(defn ^:dev/after-load start
      []
      (r/render [app]
                (.getElementById js/document "app")))

(defn ^:export init
      []
      (rf/dispatch-sync [:initialize-db])
      (start))