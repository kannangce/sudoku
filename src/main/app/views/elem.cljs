(ns app.views.elem
  (:require ["@smooth-ui/core-sc" :refer [Row Col FormGroup Label Input Box Button]]
            [reagent.core :as r]))

(defn cell
      [{:keys [id value on-change]}]
      [(r/adapt-react-class Box) {:key       id
                                    :type      :number
                                    :ml        2
                                    :content-editable (nil? value)
                                    :on-change on-change
                                    :border    "2px solid #10AF34"
                                    :width     "30px"
                                    :height    "30px"
                                    :align "center"}
       value])


(defn inner-grid
      [{:keys [id value index dispatch active-nav]}]
      )


(defn grid
      []
      )