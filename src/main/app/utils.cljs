(ns app.utils)


(defn create-matrix
      "Creates a multi-dimensional vector with dimensions given in dims
      with the given default value"
          ([default-value & dims]
           (loop [val default-value remaining dims]
                 (if (empty? remaining)
                   val
                   (recur (vec (repeat (first remaining) val))
                          (rest remaining)))
                 ))
          ([] nil))


(defn apply-fn-times [f p t]
      "Applies given function f for the given times t on the result of
      each application, starting with p.
      For ex (apply-fn-times inc 1 3) is equivalent to (inc(inc(inc 1)))"
      ((apply comp (repeat t f)) p))

(defn rotate
      "Rotates the given data  for the given number of times"
      [data count]
      ;; TODO Support for negative rotation
      (let [cnt (mod count 4)]
           (apply-fn-times #(apply mapv (comp vec reverse vector) %) data cnt)
           ))


(defn v-flip
      [data]
      (apply vector (reverse data)))

(defn h-flip
      [data]
      (mapv
        #(apply vector (reverse %))
        data))