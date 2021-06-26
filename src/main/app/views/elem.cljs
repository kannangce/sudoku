(ns app.views.elem
  (:require ["@smooth-ui/core-sc" :refer [Normalize Grid Row Col FormGroup Label Input Box Button]]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [app.utils :refer [debug]]))



(defn grid-top?
      [{:keys [index]}]
      (zero? (mod (first index) 3)))

(defn grid-bottom?
      [{:keys [index]}]
      (zero? (mod (inc (first index)) 3)))

(defn grid-right?
      [{:keys [index]}]
      (zero? (mod (inc (last index)) 3)))

(defn grid-left?
      [{:keys [index]}]
      (zero? (mod (last index) 3)))

(defn
  incorrect?
  [{:keys [actual-data expected-data]}]
  (and (some? actual-data) (not= actual-data expected-data)))

(def class-map
  {
   :incorrect  incorrect?
   :grid-top  grid-top?
   :grid-bottom  grid-bottom?
   :grid-right  grid-right?
   :grid-left  grid-left?
   })

#_(defn get-border-width
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

(defn get-cell-class
      [[row col]]
      (let [grid-data (r/atom @(rf/subscribe [:grid-data]))
            solution-data (r/atom @(rf/subscribe [:solution]))]
           (let [actual-data (get-in @grid-data [row col])
                 expected-data (get-in @solution-data [row col])]
                #_(debug [row col actual-data expected-data])
                (for [[k v] class-map] (if (v {:index [row col]
                                               :actual-data actual-data
                                               :expected-data expected-data}) k)))))

(defn cell
      [{:keys [id value on-change index]}]
      [(r/adapt-react-class Input) {:key              id
                                    ;:type             :number
                                    :ml               2
                                    :content-editable (nil? value)
                                    :on-change        on-change
                                    ;:border           "1px solid #10AF34"
                                    :style            {:margin-left  "0px"
                                                       ;; TODO For some reason width is not coming up
                                                       :border-style "solid"}
                                    :width            "30px"
                                    :height           "30px"
                                    :align            "center"
                                    :value            value
                                    :control          true
                                    :pattern          "[1-9]{1}"
                                    ;:validate         #(identity false)
                                    :class            (conj (get-cell-class index) :grid-cell)
                                    }])



;; This version is not working
(defn grid
      []
      (let [grid-data @(rf/subscribe [:grid-data])
            grid-values (r/atom grid-data)]
           #_[:<>]
           [:> Grid #_{:fluid false}
            #_[:> Row
               ;[:> Col]
               (for [col (range 9)]
                    [cell {:id        (str col "-" 2)
                           :value     col
                           :on-change #(js/alert "in cell 1 1")}])]
            (for [row (range 9)]
                 [(r/adapt-react-class Row)
                  (for [col (range 9)]
                       #_[(r/adapt-react-class Col)]
                       [cell {:id        (str row "-" col)
                              :key       (str row "-" col)
                              :value     (get-in @grid-values [row col])
                              :index     [row col]
                              :on-change #(rf/dispatch [:data-updated [row col] (js/parseInt (.. % -target -value))])}])])
            ]))

(defn solve
      []
      [:> Button {
                  ;:variant "light"
                  :style    {:margin-top "2px"}
                  :on-click #(rf/dispatch [:solve])
                  } "Solve"])

(defn selection-choice
      []
      )


(defn controls
      []
      [:> Grid
        [:Row [solve]]])