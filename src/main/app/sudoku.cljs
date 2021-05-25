(ns app.sudoku
  (:require [app.utils :refer [create-matrix]]))

(defn generate-data
      []
      (assoc-in
        (create-matrix 9 9)
        [1 1] 9))