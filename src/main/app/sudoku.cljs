(ns app.sudoku
  (:require [app.utils :refer [create-matrix]]))

(defn generate-data
      []
      [[4 9 nil 6 nil nil 8 nil nil]
       [nil nil nil nil 8 nil 1 nil nil]
       [nil 1 8 4 2 nil nil nil nil]
       [nil nil nil nil 9 8 nil nil nil]
       [nil 7 4 nil 5 nil 2 1 nil]
       [nil nil nil 7 1 nil nil nil nil]
       [nil nil nil nil 6 3 9 8 nil]
       [nil nil 5 nil 7 nil nil nil nil]
       [nil nil 6 nil nil 5 nil 3 2]])