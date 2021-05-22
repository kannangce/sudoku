(ns app.utils)

(defn create-matrix
      ([default-val m n]
      ;; TODO Create a macro to create arbitrary dimension of matrix
      (->> default-val
           (repeat m)
           vec
           (repeat n)
           vec))
      ([m n]
       (create-matrix nil m n)))