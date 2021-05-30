(ns app.sudoku.solver
  (:refer-clojure :exclude [==]) ;; Exclude clojure default == so that we'll use it from core.logic
  ;(:use clojure.core.logic) Not working with cljs yet
  (:require #_[clojure.core.logic.fd :as fd]
            [clojure.core.logic :refer [run lvar everyg]]))

(defn get-square [rows x y]
      "
      Gets the values of a 3X3 square starting from x y.
      The rows is expected to be 2D seq, where each entry in the rows represent a row of a sudoku grid.
      "
      (for [x (range x (+ x 3))
            y (range y (+ y 3))]
           (get-in rows [x y])))

(defn init [vars hints]
      "Returns the goals for each lvars based on the hints."
      (if (seq vars)
        (let [hint (first hints)]
             (all
               (if-not (zero? hint)
                       ;; If the hint is non-zero then return a goal that
                       ;; the specific lvar must be equal to the hint
                       (== (first vars) hint)

                       ;; If not just return succeed, we don't have any control here.
                       succeed)

               ;; recur for the next call
               (init (next vars) (next hints))))

        ;; if all the variables are done with just return succeed, we have nothing else to do.
        succeed))

#_(defn solve [hints]
      (let [vars (repeatedly 81 lvar) ;; Create a sequence of 81 logic variables representing the whole grid.
            rows (->> vars (partition 9) (map vec) (into [])) ;; Make 9 rows out of the lvars
            cols (apply map vector rows) ;; Make cols out of the lvars.

            ;; Create 9 3X3 squares of those lvars
            ;; This list iteration returns (x y) pair, where each of them are top-left of the
            ;; 9 squares in the sudoku grid
            sqs  (for [x (range 0 9 3)
                       y (range 0 9 3)]
                      (get-square rows x y))]
           (.log js/console 1)
           (run 1 [q] ;; Solve for the lvar q
                (== q vars) ;; q should unify with the sequence of the lvars
                (everyg #(fd/in % (fd/domain 1 2 3 4 5 6 7 8 9)) vars) ;; For every entry in vars the range must be in 1 to 9
                (init vars hints) ;; Creates the goals to match the vars with the known hints.
                (everyg fd/distinct rows) ;; Every entry in rows must be unique.
                (everyg fd/distinct cols) ;; Every entry in cols must be unique.
                (everyg fd/distinct sqs))))

(defn solve [hints]
      [[ 4 9 2 6 3 1 8 5 7]
       [ 7 6 3 5 8 9 1 2 4]
       [ 5 1 8 4 2 7 3 9 6]
       [ 6 5 1 2 9 8 4 7 3]
       [ 8 7 4 3 5 6 2 1 9]
       [ 3 2 9 7 1 4 5 6 8]
       [ 2 4 7 1 6 3 9 8 5]
       [ 9 3 5 8 7 2 6 4 1]
       [ 1 8 6 9 4 5 7 3 2]])