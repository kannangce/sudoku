(ns app.views.elem
  (:require ["@smooth-ui/core-sc" :refer [Normalize Grid Row Col FormGroup Label Input Box Button]]
            [reagent.core :as r]))

(defn cell
      [{:keys [id value on-change]}]
      [(r/adapt-react-class Box) {:key              id
                                  :type             :number
                                  :ml               2
                                  :content-editable (nil? value)
                                  :on-change        on-change
                                  :border           "2px solid #10AF34"
                                  :width            "30px"
                                  :height           "30px"
                                  :align            "center"}
       value])



;; This version is not working
(defn grid
      [data]
      [:<>
       [:> Grid {:fluid false}
        ;(for [row (range 9)])
        [:> Row
         ;[:> Col]
         (for [col (range 9)]
              [cell {:id        (str col "-" 2)
                     :value     col
                     :on-change #(js/alert "in cell 1 1")}])]
        [:> Row
         ;[:> Col]
         (for [col (range 9)]
              [cell {:id        (str col "-" 2)
                     :value     col
                     :on-change #(js/alert "in cell 1 1")}])]
        ]])


;; This version is not working
(defn grid2
      [data]
      [:<>
       [:> Grid {:fluid false}
        [:> Row
         [:> Col
          [cell {:id        "1-1"
                 :value     1
                 :on-change #(js/alert "in cell 1 1")}]]
         [:> Col
          [cell {:id        "1-1"
                 :value     1
                 :on-change #(js/alert "in cell 1 1")}]]]]])


(defn grid1
      [data]
       [cell {:id        "1-1"
                :value     1
                :on-change #(js/alert "in cell 1 1")}])