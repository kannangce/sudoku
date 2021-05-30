(ns app.views.elem
  (:require ["@smooth-ui/core-sc" :refer [Normalize Grid Row Col FormGroup Label Input Box Button]]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [re-frame.core :as rf]))



(defn is-grid-top
      [index]
      (zero? (mod (inc (first index)) 3)))

(defn is-grid-bottom
      [index]
      (zero? (mod (inc (first index)) 3)))

(defn is-grid-right
      [index]
      (zero? (mod (inc (last index)) 3)))

(defn is-grid-left
      [index]
      (zero? (mod (inc (last index)) 3)))

(defn get-border-width
      [index]

      (let [border-fn #(if % "2px" "1px")]
           ;(.log js/console (str "calling width for " index))
           (let [border-val (mapv
                              border-fn
                              ((juxt is-grid-top
                                     is-grid-bottom
                                     is-grid-right
                                     is-grid-left) index))]
                (.log js/console border-val)
                border-val))
      )

(defn cell
      [{:keys [id value on-change index]}]
      [(r/adapt-react-class Box) {:key              id
                                  :as               "Input"
                                  :type             :number
                                  :ml               2
                                  :content-editable (nil? value)
                                  :on-change        on-change
                                  :border           "1px solid #10AF34"
                                  :style            {:margin-left  "0px"
                                                     ;; TODO For some reason width is not coming up
                                                     :border-width ["1px" "5px" "10px" "20px"] #_(get-border-width index)
                                                     :border-style "solid"}
                                  :width            "30px"
                                  :height           "30px"
                                  :align            "center"
                                  :value value
                                  :control false
                                  }
       value])



;; This version is not working
(defn grid
      []
      (let [grid-data @(rf/subscribe [:grid-data])]
           (.log js/console grid-data)
           [:<>
            [:> Grid {:fluid false}
             #_[:> Row
                ;[:> Col]
                (for [col (range 9)]
                     [cell {:id        (str col "-" 2)
                            :value     col
                            :on-change #(js/alert "in cell 1 1")}])]
             (for [row (range 9)]
                  [(r/adapt-react-class Row)
                   (for [col (range 9)]
                        ;[(r/adapt-react-class Col)]
                        [cell {:id        (str col "-" row)
                               :value     (get-in grid-data [row col])
                               :index     [row col]
                               :on-change #(js/alert "in cell 1 1")}])])
             ]]))

(defn solve
      []
      [:> Button {
                  :variant "light"
                  :on-click #(rf/dispatch [:solve])
                  } "Solve"])