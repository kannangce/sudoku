(ns app.sudoku.generator
  (:require [app.utils :refer [create-matrix v-flip h-flip rotate]]))

;
;(def data
;  [{:data
;           [[4 9 nil 6 nil nil 8 nil nil]
;            [nil nil nil nil 8 nil 1 nil nil]
;            [nil 1 8 4 2 nil nil nil nil]
;            [nil nil nil nil 9 8 nil nil nil]
;            [nil 7 4 nil 5 nil 2 1 nil]
;            [nil nil nil 7 1 nil nil nil nil]
;            [nil nil nil nil 6 3 9 8 nil]
;            [nil nil 5 nil 7 nil nil nil nil]
;            [nil nil 6 nil nil 5 nil 3 2]]
;    :level :easy}])

(def
  test-data
  {
   :data     ['(4 9 0 6 0 0 8 0 0)
              '(0 0 0 0 8 0 1 0 0)
              '(0 1 8 4 2 0 0 0 0)
              '(0 0 0 0 9 8 0 0 0)
              '(0 7 4 0 5 0 2 1 0)
              '(0 0 0 7 1 0 0 0 0)
              '(0 0 0 0 6 3 9 8 0)
              '(0 0 5 0 7 0 0 0 0)
              '(0 0 6 0 0 5 0 3 2)]
   :solution [[4 9 2 6 3 1 8 5 7]
              [7 6 3 5 8 9 1 2 4]
              [5 1 8 4 2 7 3 9 6]
              [6 5 1 2 9 8 4 7 3]
              [8 7 4 3 5 6 2 1 9]
              [3 2 9 7 1 4 5 6 8]
              [2 4 7 1 6 3 9 8 5]
              [9 3 5 8 7 2 6 4 1]
              [1 8 6 9 4 5 7 3 2]]})


(def levels
  {0 :easy
   1 :medium
   2 :hard})


(def data
  {:medium [{:data     [0 0 4 3 0 0 2 0 9 0 0 5 0 0 9 0 0 1 0 7 0 0 6 0 0 4 3 0 0 6 0 0 2 0 8 7 1 9 0 0 0 7 4 0 0 0 5 0 0 8 3 0 0 0 6 0 0 0 0 0 1 0 5 0 0 3 5 0 8 6 9 0 0 4 2 9 1 0 3 0 0],
             :solution [8 6 4 3 7 1 2 5 9 3 2 5 8 4 9 7 6 1 9 7 1 2 6 5 8 4 3 4 3 6 1 9 2 5 8 7 1 9 8 6 5 7 4 3 2 2 5 7 4 8 3 9 1 6 6 8 9 7 3 4 1 2 5 7 1 3 5 2 8 6 9 4 5 4 2 9 1 6 3 7 8]}
            {:data     [0 0 0 0 0 0 6 5 7 7 0 2 4 0 0 1 0 0 3 5 0 0 0 6 0 0 0 5 0 0 0 2 0 0 0 9 2 1 0 3 0 0 5 0 0 0 4 7 1 0 9 0 0 8 0 0 8 7 6 0 0 9 0 9 0 0 5 0 2 0 3 0 0 3 0 0 1 8 2 0 6],
             :solution [8 9 4 2 3 1 6 5 7 7 6 2 4 9 5 1 8 3 3 5 1 8 7 6 9 4 2 5 8 3 6 2 4 7 1 9 2 1 9 3 8 7 5 6 4 6 4 7 1 5 9 3 2 8 1 2 8 7 6 3 4 9 5 9 7 6 5 4 2 8 3 1 4 3 5 9 1 8 2 7 6]}
            {:data     [5 0 3 0 7 0 1 9 0 0 0 0 0 0 6 7 5 0 0 4 7 1 9 0 6 0 0 4 0 0 0 3 8 0 0 0 9 5 0 2 0 0 3 0 0 0 0 0 0 1 0 0 7 2 0 0 0 8 0 4 0 0 1 3 0 0 0 0 1 8 6 0 0 8 6 7 2 0 0 0 5],
             :solution [5 6 3 4 7 2 1 9 8 2 1 9 3 8 6 7 5 4 8 4 7 1 9 5 6 2 3 4 7 2 6 3 8 5 1 9 9 5 1 2 4 7 3 8 6 6 3 8 5 1 9 4 7 2 7 9 5 8 6 4 2 3 1 3 2 4 9 5 1 8 6 7 1 8 6 7 2 3 9 4 5]}
            {:data     [0 0 1 4 0 9 0 3 0 0 0 0 3 0 6 0 5 2 0 0 7 0 0 8 1 9 0 0 6 0 0 2 0 8 0 0 0 0 0 0 0 3 0 6 5 8 9 4 5 0 7 0 0 0 4 0 3 0 9 1 0 8 0 0 7 9 0 4 0 0 2 6 0 0 0 7 0 0 9 0 0],
             :solution [2 5 1 4 7 9 6 3 8 9 4 8 3 1 6 7 5 2 6 3 7 2 5 8 1 9 4 3 6 5 1 2 4 8 7 9 7 1 2 9 8 3 4 6 5 8 9 4 5 6 7 2 1 3 4 2 3 6 9 1 5 8 7 1 7 9 8 4 5 3 2 6 5 8 6 7 3 2 9 4 1]}
            {:data     [1 0 0 0 0 0 0 9 0 2 0 8 9 7 0 6 0 5 0 0 0 5 3 2 0 0 0 0 0 6 0 5 0 4 0 0 7 0 0 8 0 6 0 0 2 0 8 3 7 0 0 0 1 0 6 0 4 0 8 0 1 2 0 8 9 0 6 0 0 0 5 0 0 1 5 0 4 0 0 0 7],
             :solution [1 5 7 4 6 8 2 9 3 2 3 8 9 7 1 6 4 5 4 6 9 5 3 2 7 8 1 9 2 6 1 5 3 4 7 8 7 4 1 8 9 6 5 3 2 5 8 3 7 2 4 9 1 6 6 7 4 3 8 5 1 2 9 8 9 2 6 1 7 3 5 4 3 1 5 2 4 9 8 6 7]}
            {:data     [3 0 8 0 5 6 0 0 7 0 0 6 9 0 0 2 5 3 0 1 2 0 4 0 0 0 0 0 0 0 0 0 0 3 2 0 9 0 4 8 0 0 0 0 0 7 6 0 1 0 9 8 0 5 0 0 0 0 0 1 9 0 4 8 3 1 0 0 0 5 0 6 0 4 0 0 0 7 0 3 0],
             :solution [3 9 8 2 5 6 4 1 7 4 7 6 9 1 8 2 5 3 5 1 2 7 4 3 6 9 8 1 8 5 6 7 4 3 2 9 9 2 4 8 3 5 7 6 1 7 6 3 1 2 9 8 4 5 2 5 7 3 6 1 9 8 4 8 3 1 4 9 2 5 7 6 6 4 9 5 8 7 1 3 2]}
            {:data     [8 0 3 0 0 0 2 7 0 4 0 9 0 0 8 0 0 0 7 0 0 0 2 4 0 9 6 0 0 0 0 0 6 9 1 5 0 0 1 8 0 2 0 0 0 0 3 0 7 5 0 0 0 0 0 5 4 0 0 0 0 6 0 6 0 8 1 0 0 0 0 3 3 7 2 0 0 9 1 4 0],
             :solution [8 6 3 9 1 5 2 7 4 4 2 9 6 7 8 3 5 1 7 1 5 3 2 4 8 9 6 2 8 7 4 3 6 9 1 5 5 4 1 8 9 2 6 3 7 9 3 6 7 5 1 4 8 2 1 5 4 2 8 3 7 6 9 6 9 8 1 4 7 5 2 3 3 7 2 5 6 9 1 4 8]}
            {:data     [0 7 8 0 1 0 6 0 9 2 0 3 0 0 9 0 0 8 4 1 0 0 6 0 0 5 2 7 2 0 1 0 6 0 3 0 0 0 0 4 0 0 7 0 0 0 9 1 3 0 5 0 0 0 9 3 2 0 0 0 4 0 0 0 0 5 7 2 0 0 1 0 0 0 0 0 0 8 0 0 6],
             :solution [5 7 8 2 1 3 6 4 9 2 6 3 5 4 9 1 7 8 4 1 9 8 6 7 3 5 2 7 2 4 1 9 6 8 3 5 3 5 6 4 8 2 7 9 1 8 9 1 3 7 5 2 6 4 9 3 2 6 5 1 4 8 7 6 8 5 7 2 4 9 1 3 1 4 7 9 3 8 5 2 6]}
            {:data     [0 0 0 0 0 1 8 4 7 0 1 0 0 0 0 0 0 0 0 5 9 3 4 8 0 0 6 3 0 0 0 2 0 0 0 4 0 7 6 1 0 0 5 0 0 2 0 0 0 0 6 7 9 0 0 4 0 2 0 7 0 6 9 0 0 7 8 0 0 4 1 5 0 0 3 5 9 0 0 0 0],
             :solution [6 3 2 9 5 1 8 4 7 8 1 4 6 7 2 9 5 3 7 5 9 3 4 8 1 2 6 3 9 1 7 2 5 6 8 4 4 7 6 1 8 9 5 3 2 2 8 5 4 3 6 7 9 1 5 4 8 2 1 7 3 6 9 9 2 7 8 6 3 4 1 5 1 6 3 5 9 4 2 7 8]}
            {:data     [0 0 8 0 7 0 6 0 0 9 6 0 0 0 1 4 0 5 4 0 2 0 0 0 0 1 0 2 0 0 8 3 0 0 9 0 6 0 0 7 9 0 1 0 3 0 0 7 0 0 4 0 2 6 5 0 0 9 0 0 3 0 7 0 3 0 0 2 0 5 0 0 0 0 0 3 1 0 0 8 9],
             :solution [1 5 8 4 7 9 6 3 2 9 6 3 2 8 1 4 7 5 4 7 2 5 6 3 9 1 8 2 1 5 8 3 6 7 9 4 6 8 4 7 9 2 1 5 3 3 9 7 1 5 4 8 2 6 5 2 1 9 4 8 3 6 7 8 3 9 6 2 7 5 4 1 7 4 6 3 1 5 2 8 9]}],
   :hard   [{:data     [0 9 0 6 0 4 0 2 5 0 0 0 5 0 0 9 0 4 5 0 3 7 0 0 1 0 0 0 0 2 0 5 9 0 0 1 0 1 0 0 0 0 0 0 0 0 0 6 0 0 8 4 3 7 0 8 0 0 0 3 6 0 0 4 0 0 0 2 0 0 0 0 7 0 0 0 0 0 0 5 8],
             :solution [1 9 7 6 8 4 3 2 5 6 2 8 5 3 1 9 7 4 5 4 3 7 9 2 1 8 6 3 7 2 4 5 9 8 6 1 8 1 4 3 6 7 5 9 2 9 5 6 2 1 8 4 3 7 2 8 5 1 7 3 6 4 9 4 6 9 8 2 5 7 1 3 7 3 1 9 4 6 2 5 8]}
            {:data     [0 0 0 6 0 0 0 0 2 5 0 0 0 0 2 0 3 9 8 0 7 0 9 0 1 0 0 0 0 0 7 0 0 4 9 0 0 0 0 0 4 0 0 5 3 3 0 6 2 0 0 0 0 0 0 0 4 0 0 8 6 2 1 1 0 2 0 0 0 0 0 0 0 7 5 0 0 3 0 0 8],
             :solution [4 9 3 6 1 7 5 8 2 5 6 1 4 8 2 7 3 9 8 2 7 3 9 5 1 6 4 2 5 8 7 3 1 4 9 6 7 1 9 8 4 6 2 5 3 3 4 6 2 5 9 8 1 7 9 3 4 5 7 8 6 2 1 1 8 2 9 6 4 3 7 5 6 7 5 1 2 3 9 4 8]}
            {:data     [0 0 0 2 0 0 0 6 0 0 0 0 0 0 8 9 0 4 2 4 0 7 0 3 5 0 0 0 7 1 0 4 0 0 5 0 0 0 0 5 0 0 0 0 6 9 0 8 0 0 0 1 0 2 8 0 6 0 0 9 0 7 0 5 3 0 0 2 0 0 0 0 0 0 0 0 1 0 4 0 8],
             :solution [1 8 5 2 9 4 3 6 7 3 6 7 1 5 8 9 2 4 2 4 9 7 6 3 5 8 1 6 7 1 9 4 2 8 5 3 4 2 3 5 8 1 7 9 6 9 5 8 3 7 6 1 4 2 8 1 6 4 3 9 2 7 5 5 3 4 8 2 7 6 1 9 7 9 2 6 1 5 4 3 8]}
            {:data     [0 1 0 5 0 4 9 0 0 3 0 0 0 8 6 0 0 2 6 5 0 0 1 0 0 7 0 0 0 0 0 0 9 0 0 0 0 4 7 0 0 5 0 0 0 0 0 0 2 3 0 1 0 6 0 7 3 0 0 0 0 0 0 0 0 0 0 0 8 0 0 5 9 6 0 3 7 0 4 0 8],
             :solution [7 1 8 5 2 4 9 6 3 3 9 4 7 8 6 5 1 2 6 5 2 9 1 3 8 7 4 1 3 6 8 4 9 2 5 7 2 4 7 1 6 5 3 8 9 5 8 9 2 3 7 1 4 6 8 7 3 4 5 2 6 9 1 4 2 1 6 9 8 7 3 5 9 6 5 3 7 1 4 2 8]}
            {:data     [6 0 3 0 9 5 0 2 0 0 0 0 2 0 0 8 0 0 0 0 7 0 0 0 0 1 0 8 0 0 0 4 3 1 0 0 0 0 9 0 0 7 3 0 0 0 0 0 6 1 0 0 9 5 3 9 0 4 8 0 0 0 0 0 5 1 0 0 9 0 0 4 2 0 0 0 0 0 0 0 6],
             :solution [6 8 3 1 9 5 4 2 7 9 1 4 2 7 6 8 5 3 5 2 7 8 3 4 6 1 9 8 7 5 9 4 3 1 6 2 1 6 9 5 2 7 3 4 8 4 3 2 6 1 8 7 9 5 3 9 6 4 8 2 5 7 1 7 5 1 3 6 9 2 8 4 2 4 8 7 5 1 9 3 6]}
            {:data     [0 7 8 0 1 0 0 6 0 0 0 0 4 0 0 0 0 3 3 9 0 0 0 5 0 0 0 0 0 6 0 4 7 0 0 0 0 0 0 0 0 2 5 0 6 5 0 0 0 3 0 8 2 0 0 6 2 0 0 0 0 3 1 0 0 4 1 8 0 7 0 0 0 0 7 9 0 0 0 0 4],
             :solution [4 7 8 3 1 9 2 6 5 6 2 5 4 7 8 1 9 3 3 9 1 2 6 5 4 7 8 2 8 6 5 4 7 3 1 9 7 1 3 8 9 2 5 4 6 5 4 9 6 3 1 8 2 7 8 6 2 7 5 4 9 3 1 9 3 4 1 8 6 7 5 2 1 5 7 9 2 3 6 8 4]}
            {:data     [0 0 0 9 0 7 4 0 2 8 0 4 3 0 0 0 0 0 0 2 0 0 0 1 0 0 6 0 8 5 0 0 0 2 6 0 0 1 0 7 0 0 5 0 0 0 0 9 0 6 0 7 0 0 0 7 0 1 0 0 0 0 0 0 0 2 0 8 4 3 0 9 0 5 0 0 0 2 0 0 1],
             :solution [6 3 1 9 5 7 4 8 2 8 9 4 3 2 6 1 5 7 5 2 7 8 4 1 9 3 6 7 8 5 4 1 9 2 6 3 2 1 6 7 3 8 5 9 4 3 4 9 2 6 5 7 1 8 4 7 8 1 9 3 6 2 5 1 6 2 5 8 4 3 7 9 9 5 3 6 7 2 8 4 1]}
            {:data     [2 0 0 6 3 0 0 0 8 0 5 0 4 1 0 0 0 0 0 7 6 0 0 9 0 2 0 4 0 0 1 0 0 2 6 0 0 9 0 3 4 0 0 0 1 8 0 7 0 0 0 0 0 5 5 0 0 0 9 0 0 0 0 1 0 0 8 0 3 0 0 6 0 0 0 2 0 0 0 9 0],
             :solution [2 4 1 6 3 7 9 5 8 9 5 8 4 1 2 6 3 7 3 7 6 5 8 9 1 2 4 4 3 5 1 7 8 2 6 9 6 9 2 3 4 5 7 8 1 8 1 7 9 2 6 3 4 5 5 6 3 7 9 4 8 1 2 1 2 9 8 5 3 4 7 6 7 8 4 2 6 1 5 9 3]}
            {:data     [0 0 0 2 0 0 0 0 0 2 0 7 0 8 0 0 6 0 0 4 1 5 0 3 0 9 0 0 2 0 3 0 0 7 0 0 0 0 0 0 0 9 8 0 4 8 0 0 0 0 1 0 0 0 0 7 0 0 0 0 0 0 6 0 0 3 8 9 0 0 0 1 0 0 4 1 6 0 0 2 5],
             :solution [3 9 8 2 1 6 5 4 7 2 5 7 9 8 4 1 6 3 6 4 1 5 7 3 2 9 8 4 2 6 3 5 8 7 1 9 7 1 5 6 2 9 8 3 4 8 3 9 7 4 1 6 5 2 1 7 2 4 3 5 9 8 6 5 6 3 8 9 2 4 7 1 9 8 4 1 6 7 3 2 5]}
            {:data     [0 0 4 0 0 1 6 9 0 0 0 0 5 3 0 0 0 0 0 1 7 0 0 0 0 4 5 6 0 0 0 7 0 0 0 0 8 0 0 3 0 0 0 2 7 3 0 0 0 8 2 0 0 0 0 5 6 0 0 0 7 8 0 0 9 0 0 0 5 0 0 1 0 0 2 9 6 0 0 0 3],
             :solution [5 3 4 7 2 1 6 9 8 9 6 8 5 3 4 1 7 2 2 1 7 6 9 8 3 4 5 6 2 5 1 7 9 8 3 4 8 4 1 3 5 6 9 2 7 3 7 9 4 8 2 5 1 6 4 5 6 2 1 3 7 8 9 7 9 3 8 4 5 2 6 1 1 8 2 9 6 7 4 5 3]}],
   :easy   [{:data     [0 0 0 0 0 4 8 0 0 0 1 0 2 3 6 7 0 0 5 0 3 0 1 7 6 0 9 3 0 0 5 0 0 0 0 0 1 0 9 4 2 0 0 0 6 0 7 5 1 0 8 0 0 2 0 9 0 0 0 5 0 0 7 0 0 2 0 0 0 4 3 0 7 0 0 6 8 0 9 0 1],
             :solution [2 6 7 9 5 4 8 1 3 9 1 8 2 3 6 7 4 5 5 4 3 8 1 7 6 2 9 3 2 6 5 7 9 1 8 4 1 8 9 4 2 3 5 7 6 4 7 5 1 6 8 3 9 2 8 9 1 3 4 5 2 6 7 6 5 2 7 9 1 4 3 8 7 3 4 6 8 2 9 5 1]}
            {:data     [3 0 0 7 0 9 0 8 0 0 2 0 4 0 6 0 3 5 5 1 0 0 2 0 0 0 7 9 0 1 0 0 4 5 2 0 0 5 0 0 0 7 0 9 0 8 0 4 0 0 0 0 0 6 0 0 3 0 0 5 1 0 8 2 0 7 8 0 0 3 0 0 0 0 0 9 0 3 6 4 0],
             :solution [3 4 6 7 5 9 2 8 1 7 2 8 4 1 6 9 3 5 5 1 9 3 2 8 4 6 7 9 7 1 6 8 4 5 2 3 6 5 2 1 3 7 8 9 4 8 3 4 5 9 2 7 1 6 4 9 3 2 6 5 1 7 8 2 6 7 8 4 1 3 5 9 1 8 5 9 7 3 6 4 2]}
            {:data     [0 0 3 0 0 0 0 4 1 0 2 0 5 9 0 7 0 0 6 8 0 2 0 4 0 0 5 0 1 9 3 8 0 0 0 6 3 0 0 0 2 9 0 0 0 4 5 0 0 0 7 9 8 0 0 0 0 6 0 1 0 7 0 0 7 0 0 3 0 5 1 0 2 0 0 0 5 8 0 3 0],
             :solution [5 9 3 8 7 6 2 4 1 1 2 4 5 9 3 7 6 8 6 8 7 2 1 4 3 9 5 7 1 9 3 8 5 4 2 6 3 6 8 4 2 9 1 5 7 4 5 2 1 6 7 9 8 3 9 3 5 6 4 1 8 7 2 8 7 6 9 3 2 5 1 4 2 4 1 7 5 8 6 3 9]}
            {:data     [0 6 0 0 7 0 0 0 5 2 8 0 0 0 6 4 0 3 0 0 1 4 0 2 7 0 9 0 0 0 8 0 1 3 0 0 0 2 6 0 0 4 0 8 0 3 0 0 0 5 0 0 0 4 0 5 0 0 0 9 1 0 0 0 4 9 3 0 8 0 5 0 0 7 3 6 2 0 9 0 0],
             :solution [9 6 4 1 7 3 8 2 5 2 8 7 5 9 6 4 1 3 5 3 1 4 8 2 7 6 9 4 9 5 8 6 1 3 7 2 7 2 6 9 3 4 5 8 1 3 1 8 2 5 7 6 9 4 8 5 2 7 4 9 1 3 6 6 4 9 3 1 8 2 5 7 1 7 3 6 2 5 9 4 8]}
            {:data     [3 0 1 6 4 0 0 0 0 0 0 8 9 0 2 6 7 3 0 0 0 0 0 0 0 1 5 0 8 0 5 0 0 7 0 4 2 0 0 7 9 1 8 0 0 0 5 9 0 6 0 2 0 0 0 0 7 0 0 4 3 6 0 0 9 0 1 8 0 0 0 0 6 2 0 3 0 0 0 4 0],
             :solution [3 7 1 6 4 5 9 8 2 5 4 8 9 1 2 6 7 3 9 6 2 8 3 7 4 1 5 1 8 6 5 2 3 7 9 4 2 3 4 7 9 1 8 5 6 7 5 9 4 6 8 2 3 1 8 1 7 2 5 4 3 6 9 4 9 3 1 8 6 5 2 7 6 2 5 3 7 9 1 4 8]}
            {:data     [0 0 7 0 0 4 6 0 0 8 0 0 9 1 0 0 0 0 9 4 0 0 0 8 0 3 5 0 0 2 3 0 0 8 4 0 0 0 3 0 6 5 7 0 0 0 6 0 4 8 1 0 0 2 0 0 0 0 5 0 4 0 0 0 9 1 6 0 2 0 0 7 6 5 0 0 7 0 9 0 1],
             :solution [1 2 7 5 3 4 6 9 8 8 3 5 9 1 6 2 7 4 9 4 6 7 2 8 1 3 5 5 1 2 3 9 7 8 4 6 4 8 3 2 6 5 7 1 9 7 6 9 4 8 1 3 5 2 2 7 8 1 5 9 4 6 3 3 9 1 6 4 2 5 8 7 6 5 4 8 7 3 9 2 1]}
            {:data     [9 6 0 0 0 7 4 1 3 4 0 0 0 0 6 9 7 0 0 7 0 2 0 0 0 8 0 0 0 5 0 1 0 2 0 0 1 8 3 5 0 0 6 9 0 0 0 0 0 4 8 0 0 0 6 2 0 0 8 0 0 4 5 0 3 0 7 0 0 0 0 0 8 1 9 0 0 0 3 2 0],
             :solution [9 6 2 8 5 7 4 1 3 4 5 8 1 3 6 9 7 2 3 7 1 2 9 4 5 8 6 7 4 5 6 1 9 2 3 8 1 8 3 5 7 2 6 9 4 2 9 6 3 4 8 7 5 1 6 2 7 9 8 3 1 4 5 5 3 4 7 2 1 8 6 9 8 1 9 4 6 5 3 2 7]}
            {:data     [7 0 0 5 1 0 0 9 0 6 0 4 0 2 9 8 0 0 0 0 0 6 0 0 4 5 7 0 7 0 0 3 0 1 8 0 4 1 8 0 0 0 0 0 6 5 3 2 0 6 0 7 0 0 0 0 0 8 0 5 0 3 4 8 2 0 0 0 0 9 0 0 0 0 9 0 7 1 0 0 0],
             :solution [7 8 3 5 1 4 6 9 2 6 5 4 7 2 9 8 1 3 2 9 1 6 8 3 4 5 7 9 7 6 4 3 2 1 8 5 4 1 8 9 5 7 3 2 6 5 3 2 1 6 8 7 4 9 1 6 7 8 9 5 2 3 4 8 2 5 3 4 6 9 7 1 3 4 9 2 7 1 5 6 8]}
            {:data     [5 0 0 1 9 0 2 4 0 0 0 3 0 7 4 1 0 0 0 0 1 6 5 3 0 8 0 0 6 0 8 1 0 5 3 0 0 0 0 0 0 9 0 0 0 0 8 2 0 0 0 0 0 7 0 3 9 5 0 0 4 0 0 7 5 0 3 0 6 0 0 2 2 0 0 0 4 7 0 0 6],
             :solution [5 7 6 1 9 8 2 4 3 8 9 3 2 7 4 1 6 5 4 2 1 6 5 3 7 8 9 9 6 7 8 1 2 5 3 4 3 4 5 7 6 9 8 2 1 1 8 2 4 3 5 6 9 7 6 3 9 5 2 1 4 7 8 7 5 4 3 8 6 9 1 2 2 1 8 9 4 7 3 5 6]}
            {:data     [0 2 1 0 0 0 0 0 0 6 0 0 3 1 0 7 0 0 7 5 3 8 9 0 0 1 2 3 9 0 7 0 4 5 6 0 0 1 0 0 0 9 2 0 8 8 0 7 0 0 0 0 0 0 0 0 0 0 0 0 8 2 0 0 0 9 4 6 0 0 3 0 5 0 4 1 0 3 0 0 7],
             :solution [9 2 1 5 4 7 3 8 6 6 4 8 3 1 2 7 5 9 7 5 3 8 9 6 4 1 2 3 9 2 7 8 4 5 6 1 4 1 5 6 3 9 2 7 8 8 6 7 2 5 1 9 4 3 1 3 6 9 7 5 8 2 4 2 7 9 4 6 8 1 3 5 5 8 4 1 2 3 6 9 7]}]
   })


;<index-all-other-digits><level-1><flip-1><rotation-1>

(defn to-nested-vec
      [v]
      (mapv #(apply vector %)
            (partition 9 v)))

(defn flip
      [data flip-index]
      (cond (= flip-index 1) (v-flip data)
            (= flip-index 2) (h-flip data)
            :default data))

(defn generate-grid-id
      []
      (let [level (rand-int 3)
            flip (rand-int 3)
            rot (rand-int 4)
            index (rand-int 1000)]
           (println index level flip rot)
           (reduce +  (map * [1000 100 10 1] [index level flip rot]))))


(defn debug
      [x]
      (.log js/console "debugging")
      (.log js/console x)
      x)

(defn xform
      [data fun]
      {:data (fun (:data data))
       :solution (fun (:solution data))})

(defn generate-data
      []
      (let [level (get levels (rand-int 3))
            flip-index (rand-int 3)
            rot (rand-int 4)
            index (rand-int 1000)]
           (-> (get data level)
               (#(get % (mod index (count %))))             ;; Choose a random one from the level
               (xform #(mapv (fn [x] (if (zero? x) nil x)) %)) ;; replace 0 with nil
               (xform to-nested-vec)                        ;; txm to nested vector
               (xform #(flip % flip-index))
               (xform #(rotate % rot))
               )))