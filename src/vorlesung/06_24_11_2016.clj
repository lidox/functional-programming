(ns repl.core)

(comment

  ;; Concurrency

  ;; Atoms

  (def foo (atom {}))

  foo

  (= foo {})
  (type foo)

  ;; Atome ändern:
  ;; (swap! atom funktion ,,,, arg1 arg2 ...)

  (defn store [k v] (swap! foo assoc ,,,, k v))

  (store :x 101)
  (store :y 100)
  foo

  ;; (swap! a f x y z) führt die Funktion (f a x y z)
  ;; aus und speichert das Ergebnis in a

  (store :x 7)

  foo

  (:x foo)

  ;; Ein Atom ist nicht der gespeicherte Wert sondern
  ;; eine Verpackung für den Wert!

  (deref foo)
  @foo

  (def blah @foo)
  blah
  (store :x 1000)
  @foo
  blah


  ;; Atome müssen dereferenziert werden um auf den
  ;; aktuellen Wert zuzugreifen

  (:x @foo)

  ;; reset!

  (swap! foo (fn [_] {}))
  @foo

  (reset! foo 9)
  foo

  ;; Apropos ... constantly
  (swap! foo (constantly {:x 23}))

  (def always2 (constantly 2))
  (always2)
  (always2 15)
  (always2 1 2 3 4)

  ;; constantly nimmt einen Wert und generiert eine
  ;; Funktion, die beliebig viele Argumente akzeptiert
  ;; und immer den Wert zurückgibt

  (defn my-constantly [v] (fn [& whatever] v))
  ((my-constantly 42) :lol :trololo 1 4 2)

  ;; Es ist ein häufiges Muster, dass man sich nicht
  ;; für den aktuellen Wert des Atoms interessiert
  ;; und einen konstanten Wert setzen will.
  ;; Dafür kann man reset! benutzen

  (reset! foo (+ 899 1))
  (swap! foo (constantly (+ 899 1)))

  @foo


  ;; CAS Semantik

  (def counter (atom 0))

  (defn incer [] (swap! counter inc))

  (incer)
  counter

  (do
    (future (last (repeatedly 10000000 incer)))
    @counter)

  counter

  ;; Wenn man schnell genug ist, sieht man wie sich
  ;; der Wert im Atom ändert

  (defn sleepy-inc [n]
    (println "Zzz")
    (Thread/sleep 4000)
    (println "Whut?")
    (inc n))

  (sleepy-inc 6)

  (defn v-incer [] (swap! counter sleepy-inc))

  ;; v-incer ist so langsam, dass man von Hand race
  ;; conditions erzeugen kann

  (v-incer)

  ;; Achtung! Das ist nur zur Demonstration, es ist
  ;; ausgesprochen dumm, eine Funktion mit Seiteneffekt
  ;; in Kombination mit swap! zu benutzen!

  ;; Warum?

  (defn tease [] (let [c (rand-int 1000)] (reset! counter c)))

  (future  (v-incer)) ; repl
  (tease)             ;repl

  counter






  ;; watcher
  ;; (add-watch atom name vierstelligeFkt)
  ;; Funktion: name atom alt neu

  (def agent-x
    (add-watch counter
               :my-awesome-watcher
               (fn [k r old new]
                 (println (str k " "old " -> " new)))))

  (tease)


  ;; Dinge, bei denen man aufpassen muss!

  (def mouse-position (atom {:x 12 :y 100}))

  ;;Falsch:

  (println (:x @mouse-position) (:y @mouse-position))


  ;; Warum ist das falsch?









  ;; @mouse-position dereferenziert das Atom, wenn man das
  ;; mehrfach macht, kann man das Atom zu verschiedenen
  ;; (ggf. inkonsistenten) Zeiten sehen


  ;; Wie geht es richtig?
  (let [p @mouse-position]
    (println (:x p) (:y p)))




  ;; Wenn man schon nicht ein einziges Atom konsistent
  ;; mehrfach dereferenzieren kann, geht das erst recht
  ;; nicht mit mehreren!




  ;; Super-Falsch:
  (def x-pos (atom 12))
  (def y-pos (atom 212))

  (println @x-pos @y-pos)



  ;; SLIDES: Agents



  ;; Agents

  ;; agents

  (def log-file (atom []))

  (defn debug [& words]
    (swap! log-file
           (fn [x]
             (Thread/sleep 2000)
             (conj x (apply str (interpose " " words))))))

  (debug "Eine" "Ausgabe," "die" "an" "einem" "Stück" "erfolgen" "soll")
  (debug "Noch" "eine" "Ausgabe," "die" "an" "einem" "Stück" "erfolgen" "soll")


  (def log-file (atom []))

  (time
   (do
     (debug "Eine" "Ausgabe," "die" "an" "einem" "Stück" "erfolgen" "soll")
     (debug "Noch" "eine" "Ausgabe," "die" "an" "einem" "Stück" "erfolgen" "soll")
     ))

  log-file


  ;; Bei Atomen wird die Funktion im Aufrufer Thread ausgeführt


  (def log-file (agent []))

  (defn debug [& words]
    (send log-file
          (fn [x]
            (Thread/sleep 4000)
            (conj x (apply str (interpose " " words))))))

  (time
   (do
     (debug "Eine" "Ausgabe," "die" "an" "einem" "Stück" "erfolgen" "soll")
     (debug "Noch" "eine" "Ausgabe")))

  ;; Agents arbeiten asynchron, daher bekommen wir sofort die Kontrolle

  @log-file

  (def log-file (agent []))

  (time
   (do
     (debug "Eine" "Ausgabe," "die" "an" "einem" "Stück" "erfolgen" "soll")
     (debug "Noch" "eine" "Ausgabe")))


  (do  (await log-file) @log-file)

  ;; Mit await kann man blockieren, bis alle bisher submitteten Tasks
  ;; erledigt sind. await-for macht das Gleiche mit Time-out


  (def ag
    (for [x (range 20)] (agent nil)))

  (time
   (do
     (doseq [a ag] (send a (fn [_] (Thread/sleep 1000))))
     (doseq [a ag] (await a))))


  ;; send wird auf einem Threadpool ausgeführt (normalerweise #Cores + 2)


  (time
   (do
     (doseq [a ag] (send-off a (fn [_] (Thread/sleep 1000))))
     (doseq [a ag] (await a))))



  ;; send-off erzeugt einen neuen Thread für jeden Task





  ;; SLIDES





  ;; Refs

  (def konto1 (atom 100))
  (def konto2 (atom 0))

  ;; Überweisung
  (defn transfer-money [from to amount]
    ;; Es kann nur überwiesen werden, wenn das Konto gedeckt ist
    (when (<= amount @from)
      (do (swap! to #(+ % amount))
          (Thread/sleep 5000) ; Viel los im System
          (swap! from #(- % amount)))))

  ;; Auszahlung
  (defn withdraw [konto amount]
    (when (<= amount (deref konto))
      (println "Here is the money: " amount)
      (swap! konto #(- % amount))))

  (future (transfer-money konto1 konto2 10))
  [@konto1 @konto2]
  (withdraw konto1 100)
  [@konto1 @konto2]

  (future (transfer-money konto1 konto2 60))
  (withdraw konto1 60)

  [@konto1 @konto2]






  ;; Atome sind nicht koordiniert. Es ist nicht machbar
  ;; zwei Atome konsistent zu ändern!


  ;; Man kann ein atom verwenden und den gesamten
  ;; State als eine Art Datenbank ansehen:

  (def konten (atom {:konto1 100 :konto2 0}))

  (defn transfer-money2 [konten from-name to-name amount]
    (swap! konten (fn [k]
                    (let [from-wert (get k from-name)
                          to-wert (get k to-name)]
                      (if (<= amount from-wert)
                        (let [k' (assoc k to-name (+ to-wert amount))]
                          (Thread/sleep 5000)
                          (assoc k' from-name (- from-wert amount)))
                        (do (println "Rejected!")
                            k))))))

  (defn pay-bill2 [konten konto-name amount]
    (swap! konten (fn [k]
                    (let [konto-stand (get k konto-name)]
                      (if (<= amount konto-stand)
                        (assoc k konto-name (- konto-stand amount))
                        k)))))

  (future (transfer-money2 konten :konto1 :konto2 70))
  (pay-bill2 konten :konto1 70)
  @konten



  ;; Alternativ kann man refs benutzen

  (def konto1 (ref 100))
  (def konto2 (ref 0))

  (dosync (alter konto1 dec))

  konto1

  ;; Das hier ist korrekt bei refs (bei Atomen war das falsch!)

  (dosync
   (println @konto1 @konto2))

  ;; Lesezugriffe auf refs in einem dosync Block sind konsistent




  (defn transfer-money3 [from to amount]
    (dosync (when (<= amount (deref from))
              (do (alter to #(+ % amount))
                  (Thread/sleep 5000)
                  (alter from #(- % amount))))))

  (defn pay-bill3 [konto amount]
    (dosync  (when (<= amount (deref konto))
               (println "Payed" amount)
               (alter konto #(- % amount)))))

  (future (transfer-money3 konto1 konto2 10))

  (dosync [@konto1 @konto2])

  (pay-bill3 konto1 10)

  @konto1

  (future (transfer-money3 konto1 konto2 60))
  (pay-bill3 konto1 60)

  (dosync [@konto1 @konto2])



  ;; Kommutative Änderungen
  (def konto1 (ref 10000))
  (def konto2 (ref 10000))

  (def x-counter (ref 0))

  (defn pay-bill4 [id kto amount]
    (dosync  (when (<= amount (deref kto))
               (println "Payed" amount " transaction " id)
               (Thread/sleep 2000)
               (alter kto #(- % amount))
               (alter x-counter inc))))


  (pay-bill4 :t1 konto1 10)
  (dosync [@konto1 @konto2 @x-counter])

  (do (future (pay-bill4 :tA konto1 10))
      (future (pay-bill4 :tB konto2 10)))


  (defn pay-bill5 [id kto amount]
    (dosync  (when (<= amount (deref kto))
               (println "Payed" amount " transaction " id)
               (Thread/sleep 2000)
               (alter kto #(- % amount))
               (commute x-counter inc))))

  (dosync [@konto1 @konto2 @x-counter])

  (do (future (pay-bill5 :c1 konto1 10))
      (future (pay-bill5 :c2 konto2 10)))



  )
