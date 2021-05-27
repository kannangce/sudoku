(ns app.utils)


(defn create-matrix
          ([default-value & dims]
           (loop [val default-value remaining dims]
                 (if (empty? remaining)
                   val
                   (recur (vec (repeat (first remaining) val))
                          (rest remaining)))
                 ))
          ([] nil))
