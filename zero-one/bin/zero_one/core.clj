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

;; Vorlesung Woche 3

;; Collections: schneller zugriff vorne
(list 123)

;; Type
(type '(1 2 3))

;; Vector ~ so wie arrayList, gut zum Einfügen von daten von hinten
[1 2 3]
(type [1 2 3])


;;
(conj [1 2 3] :a)

;; Map
{:a 1 :b 2 [{}] 3}
(type {:a 1 :b 2 [{}] 3})

;; get value by key
(get {:a 1 :b 2 [{}] 3} :a)

;; map function: Wende eine funktion auf alle elemente der collection
(map inc [1 2 3 4])

;; def / defn ~ quasi globale variable, nur auf top level ebene benutzen!!! zum dinge definieren
(def a 1)

;; funktion definiert
(def square (fn [x] (* x x)))
(square 3)

;; if
(if :foo 1 2)
(if nil 1 2)

;; conditions
(defn disp [x]
  (cond
    (= x 1) :eins
    (< x 2) :einsh
    ))
(disp 1)

;; FizzBuzz Kata
(defn replace-fb [n]
  (cond
    (= 0 (mod n 15)) :fizzbuzz
    (= 0 (mod n 5)) :buzz
    (= 0 (mod n 3)) :fizz
    :sonst n))

(defn fizzbuzz [n]
  map( replace-fb (range 1 (inc n))))

(fizzbuzz 15)

;; higher order functions: zwei mal inc ausführen
(defn evaluate1 [f v] (f (f v)))

(evaluate1 inc 12)

;; filter
(filter (fn [x] (< 2 x 6 )) [1 2 3 4 5 6 7])

;; AUsgabetypen von map
(type (map second {:a 1 :b 2 :c 3}))


;; Was ist eine LazySeq? -> TUPEL



;; Praesenz05
;; 5.1. d) 
;; Lsg: 'Atome' haben bessere Performance als 'Refs'

;; 5.2 Invarianten: Welche Probleme beinhaltet der Codesnippet?
;; a) Lsg:

;; b) Differenzieren im Do-Sync-block sorg für Konsistenz im Block
;; Do Sync löst probleme bzgl. Refs

;; 5.3 Intervalle
;; LSG1 möglicherweise falsch, besser gucke LSG2
(defn  intervals [c]
   ((let [c (sort (set c))])
  (reduce (fn [[a interval-start last-seen] e]
            (if (= (inc last-seen) e)
            [a intreval-start e]
            [(conj a [interval-start last-seen]) e e]))
            [[] (first c) (first c)]    
         (rest c)))) 

;; LSG 2
(defn intervals [c]
  (rest
    (reverse
      (reduce
        (fn [[[a b] & r] e]
          (if (= b (dec e))
            (cons [a e] r)
            ( --> r
                  (cons [a b])
                  (cons [e e]))))
        []
        (sort (set c))))))

(intervals [10 9 8 8 1 2 3])
;; output: ([1 3] [8 19])
          
          
          
          
          
          
          
          
          
          
          

;; erst collection sortieren
;; dann REDUCE anwenden
  
(intervals [10 9 8 8 1 2 3])
 

;; 5.4 Elemente entfernen
(defn remove-all [c1 c2]
  (loop [c c1
         to-delete c2]
    (if (seq to-delete)
      (recur (remove #(= (first to-delete) %) c)
             (rest to-delete))
      c)))

(remove-all (range 10) [7 8 9 10 11])
      































































