(ns zero-one.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))


;; Aufgabe 1.4 - Duplikate entfernen
(def remove-duplicates distinct)
(remove-duplicates [1 2 3 "dd" 2 2 1 1 :a 3])

(defn remove-duplicates2 [c]
  (loop [r c
         seen #{}
         so-far []]
    (if (empty? r)
          so-far
          (recur (rest r)
          (conj seen (first r))
            (if (contains? seen (first r))
              so-far
              (conj so-far (first r)))))))
(remove-duplicates2 [1 2 3 "dd" 2 2 1 1 :a 3])

(defn remove-duplicates3 [v]
  (remove nil?
          (for [i (range 0 (count v))]
            (let [j (nth v i)]
              (if (some #(= j %)
                        (take (dec i) v))
                nil
                j)))))
(remove-duplicates3 [1 2 3 "dd" 2 2 1 1 :a 3])
        
    
;; Feature von seq: wenn uebergebene Liste leer ist --> gibt 'nil' zurueck 
(seq [1 2 3])
