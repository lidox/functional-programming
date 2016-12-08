 (ns blatt01)

 ;; Let's get ready toooo RUMBLE!!!

 ;; Aufgabe 1 (Truthiness)

 0       ;; a)
 false   ;; b)
 :false  ;; c)
 nil
 []
(list)
{}
 #{}
 ""

(if 0 :truthy :falsey)

(if nil :truthy :flasey)

;; http://clojure.org/api/cheatsheet
(println
  (map (fn [x] (if x :truthy :falsey)) [0 false :false nil [] () {} #{} ""])) ;; fn ist anonym
