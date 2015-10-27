(ns bin-dfa.core)

(defn parse-input
  "Convert a string of 0's and 1's into a list of 0's and 1's"
  [input]
  (if (zero? (count input))
    nil
    (cons (- (int (first input)) (int \0)) (parse-input (rest input)))))

(defn process-machine
  "Turn a list of lists into a map-defined machine"
  [machine]
  (if (zero? (count machine))
    {}
    (let [node (first machine)]
      (assoc
        (process-machine (rest machine))
        (str (first node)) (map str (rest node))))))

(defn run-machine
  "Run a machine (defined as a map of pairs) on a list input"
  [machine, state, input]
  (if (= 0 (count input))
    state
    (let [nextState (nth (get machine state) (first input))]
      (run-machine machine nextState (rest input)))))

(defn -main
  []
  (println "Please run the tests"))
