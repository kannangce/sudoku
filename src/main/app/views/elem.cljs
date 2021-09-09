(ns app.views.elem
  (:require ["@smooth-ui/core-sc" :refer [Normalize Grid Row Col FormGroup Label Input Box Button ModalDialog
                                          ModalContent ModalCloseButton ModalHeader Typography ModalBody]]
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
   :incorrect   incorrect?
   :grid-top    grid-top?
   :grid-bottom grid-bottom?
   :grid-right  grid-right?
   :grid-left   grid-left?
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
                (for [[k v] class-map] (if (v {:index         [row col]
                                               :actual-data   actual-data
                                               :expected-data expected-data}) k)))))

(defn cell
      [{:keys [id value on-change index]}]
      [(r/adapt-react-class Input) {:key              id
                                    :ml               2
                                    :content-editable (and (nil? value))
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

(defn solved-message
      []
      (let [solved? (r/atom @(rf/subscribe [:solved?]))]
           (do
             (.log js/console @solved?)
             (.log js/console "Solved flag")
             (if @solved?
               [:> ModalDialog
                [:> ModalContent]
                [:> ModalContent
                 [:> ModalCloseButton]
                 [:> ModalHeader
                  [:> Typography
                   "Modal title"]]
                 [:> ModalBody "You solved the grid"]]]
               ))))

(defn show-time
      [seconds]
      [:div
       [:div (str (int (/ seconds 3600)) ":" (int (/ seconds 60)) ":" (mod seconds 60))]])

(defn pause [time-paused?]
      [:input {:type     "button" :value (if @time-paused? "Resume" "Pause")
               :on-click #(swap! time-paused? not)
               :style {:width    "70px"}}])

(defn increment-timer
      [time paused?]
      (if (not @paused?)
        (swap! time inc)))

(defn countdown-component []
      (let [start-time (r/atom 0)
            time-paused? (r/atom false)]
           (if (not @time-paused?)
             (do
               (js/setInterval #(increment-timer start-time time-paused?) 1000)
               (fn []
                   (.log js/console (str "paused?" @time-paused?))
                   [:div.timer
                    [:div "Timer: " (show-time @start-time)]
                    [pause time-paused?]])))))

(defn selection-choice
      []
      )


(defn controls
      []
      [:> Grid
       [:> Row [solve]]
       [:> Row [countdown-component]]])